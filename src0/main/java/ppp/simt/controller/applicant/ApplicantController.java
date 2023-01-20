package ppp.simt.controller.applicant;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


import java.util.HashMap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.user.User;
import ppp.simt.service.applicant.ApplicantService;
import ppp.simt.service.applicant.EntranceEligibleCenterService;
import ppp.simt.service.core.CoreService;
import ppp.simt.service.core.CountryService;
import ppp.simt.service.core.EligibilityChecher;
import ppp.simt.service.core.PersonService;
import ppp.simt.service.core.SpecialityService;
import ppp.simt.service.engines.ApplicantCheckerService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.service.pv.TrainingCenterService;
import ppp.simt.service.user.UserService;
import ppp.simt.form.ApplicantForm;
import ppp.simt.form.SimtLicenseChecker;
import ppp.simt.form.validator.ApplicantFormValidator;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {
	
	@Autowired
	protected ApplicantFormValidator applicantFormValidator;
	
	@Autowired
	protected UserService userService;
	
	@Autowired
	protected CountryService countryService;
	
	@Autowired
	protected EligibilityChecher eligibilityChecher;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	protected PersonService personService;
	
	@Autowired
	protected ApplicantService applicantService;

	@Autowired
	protected EntranceEligibleCenterService entranceEligibleCenterService;
	
	@Autowired
	protected TrainingCenterService trainingCenterService;
	
	@Autowired
	protected StudentService studentService;
	
	@Autowired
	protected SpecialityService specialityService;
	
	@Autowired
	protected ApplicantCheckerService applicantCheckerService;
	
	@Autowired
	protected CoreService coreService;
	

	@Autowired
	private Tracker tracker;

	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    binder.setValidator(applicantFormValidator);
	}


@RequestMapping(value="/verify/applicant/license", method=RequestMethod.GET)
@ResponseBody
public  HashMap<String, String> searchStudentLicense(@RequestParam("license") String license,@RequestParam("speciality") int speciality,@RequestParam("diplome") String diplome, HttpServletRequest http, HttpServletResponse response ) {			
		HashMap<String, String> map = new HashMap<String, String>();
		String specialty;
		if(speciality==1);
		specialty = "moniteur";
		if(speciality==2 || speciality==3) {
		specialty = "expert";
		}
		SimtLicenseChecker result = eligibilityChecher.check(license, specialty,diplome);
		System.out.println(result.getCan_be_register());

		if(result.getCan_be_register().equals("no")) {
			map.put("can_be_register", result.getCan_be_register());
			return map;
		}else{
		   String userSpeciality = ((Speciality) specialityService.findById(Long.valueOf(speciality))).getAbbreviation();
		   String gN =result.getPerson().getGn();
		   String sN =result.getPerson().getSn();
		   String pob =result.getPerson().getPob();
		   java.util.Date dob =result.getPerson().getDob();
		   Person appropriatePerson = personService.findPersonByLicenseNum(license.trim());
		   if(appropriatePerson != null && diplome.trim().equals("CAPEC") ){
			   if(applicantCheckerService.capecExistance(appropriatePerson, userSpeciality)){
				   map = setApplicantMap( result);
				   return map;
			   }else{
				   map.put("can_be_register", "KO.DO.NOT.HAVE.CAPEC");
				   return map;
			   }
		   }else{
			    if(appropriatePerson == null && diplome.trim().equals("CAPEC")) {
			    	map.put("can_be_register", "KO.DO.NOT.HAVE.CAPEC");
					return map;
			    	
			    }
				map = setApplicantMap( result);
			    return map;
			   
		   }
		}
}
/*
 *@uthor Nguesuh
 *this function helps to persist applicant during creation 
 */
@RequestMapping(value="/createPost", method=RequestMethod.POST)
@ResponseBody
public String createStudentPost(@ModelAttribute("applicantForm") @Validated ApplicantForm applicantForm,  BindingResult result,@RequestParam("entranceEligibleCenterId") int entranceEligibleCenterId,HttpServletRequest httpServletRequest, Model model){
	User currentUser = userService.getLogedUser(httpServletRequest);
	
	if(result.hasErrors()){
		if(result.getFieldError("licenseNum")!=null){
			logger_.log(Constants.NORMAL_LOG_DIR, "FRAUD ATTEMP ON APPLICANT COMPUTERIZATION "+result.getFieldError("licenseNum").getCode(),httpServletRequest);
			return result.getFieldError("licenseNum").getCode();
		}
	}
	
	
	EntranceEligibleCenter entranceEligibleCenter = entranceEligibleCenterService.findById(entranceEligibleCenterId);
	Person person = personService.findPersonByLicenseNum(applicantForm.getLicenseNum().trim());
	Speciality speciality = specialityService.findById(applicantForm.getSpeciality());

	if (person!=null) {
		for (Student student : person.getStudents()) {
			for (StudentSession studentSession : student.getStudentSessions()) {
				if (studentSession.getStudentSessionStatus().getId() == 4 && studentSession.getEligibleCenter().getEligibleCenterStatus().getId() == 4 && studentSession.getSpeciality().getId() == speciality.getId()) {
					return "dupilcation.of.applicant";
				}

			}
		}
	}
	
	for(EntranceEligibleCenter entranceEligibleCenternew : entranceEligibleCenterService.findByEntranceExamSession(entranceEligibleCenter.getEntranceExamSession())){
	List<Applicant> applicantsPerCenter = applicantService.findByEntranceEligibleCenter(entranceEligibleCenternew);
	
	for (int i = 0; i < applicantsPerCenter.size(); i++) {
		if (person!=null && (applicantsPerCenter.get(i).getPerson().getId() == person.getId())) {
			return "dupilcation.of.applicant";
			
		}
		
	}
    }
	Applicant applicant = applicantService.persistApplicant(applicantForm, httpServletRequest, person,entranceEligibleCenter);
	List<Person> persons = personService.findAll();
	model.addAttribute("persons", persons);
	return "applicant/manage_applicant_result";
	
	
}

	@RequestMapping(value="/load", method=RequestMethod.GET)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String loadCandidates(@RequestParam("entranceEligibleCenter") int entranceEligibleCenterId,@RequestParam("result") String result, Model model, HttpServletRequest httpServletRequest){
		//int examResult = Integer.parseInt(result);
		EntranceEligibleCenter entranceEligibleCenter = entranceEligibleCenterService.findById(entranceEligibleCenterId);
		List<Applicant> applicants;

		if(result=="ADVANCED TO TRAINING"){
			 applicants = applicantService.findByEntranceEligibleCenterAndResultAndStudentExists(entranceEligibleCenter, "PASSED PRACTICALS");

		}else{
			 applicants = applicantService.findByEntranceEligibleCenterAndResult(entranceEligibleCenter, result);
		}

		
		
		model.addAttribute("applicants",applicants);
		model.addAttribute("entranceEligibleCenter",entranceEligibleCenter);
		model.addAttribute("indicator", result);

		return "applicant/list_applicant";
	}
	
	@RequestMapping(value="/manage", method=RequestMethod.POST)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String updateCandidateResults(@RequestParam("id") int applicantId,@RequestParam("result") String result, @RequestParam("type") String type,Model model, HttpServletRequest httpServletRequest){
		Applicant applicant = applicantService.findById(applicantId);
		applicant.setResult(result);

		applicantService.updateApplicant(applicant);
		String messageApplicant =getMessageFromApplicantExamResult(type);
		
		
		tracker.track(applicant, messageApplicant, httpServletRequest);
		
		return "applicant/manage_applicant_result";
	}


	private String getMessageFromApplicantExamResult(String type) {
		String message ="";
		switch(type){
		case "register-applicant":
			message = "REGISTERED THE APPLICANT";
			break;
		case "fail-theory":
			message = "MARKED AS FAILED THEORY";
			break;
		case "pass-theory":
			message = "MARKED AS PASSED THEORY";
			break;
		case "fail-practical":
			message = "MARKED AS FAILED PRACTICAL";
			break;
		case "pass-practical":
			message = "MARKED AS PASSED PRACTICAL";
			break;
		case "send-back-from-theory":
			message = "SENT BACK TO REGISTERED QUEUE";
			break;
		case "send-back-from-practical":
			message = "SENT BACK TO PASSED THEORY QUEUE";
			break;
		default:
			message=" Not Implemented_" + type;
		}
		return message;
	}
	
	@RequestMapping(value="/advanceToTraining", method=RequestMethod.GET)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String displayApplicantToTrain(@RequestParam("applicantId") int applicantId,Model model, HttpServletRequest httpServletRequest){
		Applicant applicant = applicantService.findById(applicantId);
		List<TrainingCenter> trainingCenters = trainingCenterService.findTrainingCenterByEligibleSpeciality(applicant.getSpeciality());		
		model.addAttribute("trainingCenters",trainingCenters);
		model.addAttribute("applicant",applicant);	
		return "applicant/applicant_to_train";
	}
	
	@RequestMapping(value="/createStudent", method=RequestMethod.POST)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String createStudentFromApplicant(@RequestParam("applicantId") int applicantId,@RequestParam("trainingCenterId") int trainingCenterId , Model model, HttpServletRequest httpServletRequest){
		Applicant applicant = applicantService.findById(applicantId);
		Person person = applicant.getPerson();
		Student student = applicant.getStudent();
		TrainingCenter trainingCenter = trainingCenterService.findById(trainingCenterId);
		
		if(trainingCenter.getMaxStudent()!=0) {
			
			student.setTrainingCenter(trainingCenter);
			studentService.updateStudent(student);

			tracker.track(student, "STUDENT TRAINING CENTER UPDATED SUCCESSFULLY", httpServletRequest);
		}else {
			System.out.println("right path");
			return "training.center.max.student.zero";
		}
		
		model.addAttribute("trainingCenters",trainingCenter);
		model.addAttribute("applicant",applicant);	
		return "applicant/manage_applicant_result";
	}

	@RequestMapping(value="/examenResult", method=RequestMethod.GET)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String ApplicantExamenResult(@RequestParam("applicantId") int applicantId,Model model, HttpServletRequest httpServletRequest){
		Applicant applicant = applicantService.findById(applicantId);
		model.addAttribute("applicant",applicant);
		return "applicant/applicant_examen_result";
	}

	@RequestMapping(value="/saveFinalResult", method=RequestMethod.POST)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String saveFinalResultFromApplicant(@RequestParam("applicantId") int applicantId, @RequestParam("note") float note , Model model, HttpServletRequest httpServletRequest){
		Applicant applicant = applicantService.findById(applicantId);
		applicant.setNote(note);
		applicantService.updateApplicant(applicant);
		model.addAttribute("applicant",applicant);



		EntranceEligibleCenter entranceEligibleCenter = entranceEligibleCenterService.findById(applicant.getEntranceEligibleCenter().getId());
		List<Applicant> applicants;
		applicants = applicantService.findByEntranceEligibleCenterAndResult(entranceEligibleCenter, "PASSED PRACTICALS");

		model.addAttribute("applicants",applicants);
		model.addAttribute("entranceEligibleCenter",entranceEligibleCenter);
		model.addAttribute("indicator", "PASSED PRACTICALS");

		return "applicant/list_applicant";

	}
	
	
	private HashMap<String, String> setApplicantMap(SimtLicenseChecker result){
		HashMap<String, String> map = new HashMap<String, String>();
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
	    String dobString = formatter.format(result.getPerson().getDob());
	    String catBDate = formatter.format(result.getCatB_Date());
	  
	    map.put("gn", result.getPerson().getGn());
	    map.put("sn", result.getPerson().getSn());
	    map.put("dob", dobString);
	    map.put("pob", result.getPerson().getPob());	
	    map.put("gender", result.getPerson().getGender());	
	    map.put("catB_Date", catBDate);
	    return map;
		
	}
}
