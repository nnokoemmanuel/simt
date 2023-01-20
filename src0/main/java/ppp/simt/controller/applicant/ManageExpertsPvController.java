
/**
* Cette classe permet de manipuler les pv (entance eligibles centers)
 */

package ppp.simt.controller.applicant;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.DocumentException;


import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.applicant.TrainingCenterChoice;
import ppp.simt.entity.core.Country;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.pv.EligibleSpeciality;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.user.User;
import ppp.simt.service.core.CountryService;
import ppp.simt.service.core.RegionService;
import ppp.simt.service.core.SpecialityService;
import ppp.simt.service.engines.PrinterService;
import ppp.simt.service.engines.StateCheckerService;
import ppp.simt.service.applicant.ApplicantService;
import ppp.simt.service.applicant.EntranceEligibleCenterService;
import ppp.simt.service.applicant.EntranceExamCenterService;
import ppp.simt.service.applicant.EntranceExamSessionService;
import ppp.simt.service.applicant.TrainingCenterChoiceService;
import ppp.simt.service.pv.EligibleSpecialityService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.service.pv.TrainingCenterService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Controller
@RequestMapping("/expertPv")
public class ManageExpertsPvController {
	@Autowired
    ServletContext context;
	
	@Autowired
	private Environment env;
	
	@Autowired 
	private Logger_ logger_;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StateCheckerService stateCheckerService;
	
	@Autowired
	private EntranceEligibleCenterService eligibleCenterService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private EntranceExamSessionService examSessionService;
	
	@Autowired
	private EntranceEligibleCenterService entranceEligibleCenterService;

	@Autowired
	private ApplicantService applicantService;
	
	@Autowired
	protected CountryService countryService;

	@Autowired
	private PrinterService printerService;
	

	@Autowired
	private EntranceExamCenterService examCenterService;
	
	@Autowired
	private ApplicantService studentSessionService;
	
	@Autowired 
	private Tracker tracker;

	@Autowired
	private TrainingCenterService trainingCenterService;
	
	@Autowired
	private TrainingCenterChoiceService trainingCenterChoiceService;
	
	@Autowired
	private StudentService studentService;

	@Autowired
	private SpecialityService specialityService;
	
	@Autowired
	private EligibleSpecialityService eligibleSpecialityService;
	

	
	/*
	 *@uthor MPA
	 *this function helps to manage all pv Statuses
	 */
	
	
	@ResponseBody
	@RequestMapping(value="/update/{action}/{id}", method=RequestMethod.GET)
	public String managePvStates(@PathVariable(value="action") String action ,@PathVariable(value="id") int id ,HttpServletRequest httpServletRequest, Model model){
		User user = userService.getLogedUser(httpServletRequest);
		EntranceEligibleCenter eligibleCenter = eligibleCenterService.findEntranceEligibleCenterById(id);
		if(action.equals("reset")){
	        //instructions to reset Pv
			
			if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"reset").equals("ok")){
				resetpvTrackAndLog( eligibleCenter , user,httpServletRequest );
				
				tracker.track(eligibleCenter, "PV WAS RESETTED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
				logger_.log(Constants.NORMAL_LOG_DIR, "Created the pv: "+user);
				
				return "ok";
	        }else if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"reset").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
		}else if(action.equals("close")){
			
			if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"close").equals("ok")){
			closepvTrackAndLog( eligibleCenter , user,httpServletRequest );
			
			tracker.track(eligibleCenter, "PV WAS CLOSED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Created the pv: "+user);
			
	        return "ok";
	        }else if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"close").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
			
		}else if(action.equals("validate")){
			
			if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"validate").equals("ok")){
			vaidatepvTrackAndLog( eligibleCenter , user,httpServletRequest );
			
			tracker.track(eligibleCenter, "PV WAS VALIDATED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Created the pv: "+user);
			
	        return "ok";
	        }else if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"validate").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
			
		}else if(action.equals("open")){
			
			if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"open").equals("ok")){
			openpvTrackAndLog( eligibleCenter , user,httpServletRequest );
			
			tracker.track(eligibleCenter, "PV WAS OPEimport java.util.Collection;N BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Created the pv: "+user);
			
	        return "ok";
	        }else if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"open").equals("koBadRole")){
				return "koBadRole";
	        }else{
			    return "koBadState";
		    }
			
		}else if(action.equals("generate")){
			if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"generate").equals("ok")){
			 boolean isPvGenerable = checkPVGenerable(eligibleCenter);
			 System.out.println("is generable "+isPvGenerable);
			 if (isPvGenerable == true) {
				 
				 List<Applicant> students = studentSessionService.findByEntranceEligibleCenterAndResult(eligibleCenter, "PASSED PRACTICALS");
				 for (int i=0; i<students.size(); i++){
					 Applicant studentSession = students.get(i);
					 studentSession.setPvNumber(i + 1);
					 studentSessionService.updateApplicant(studentSession);
					 
				 }
				 generatePvTrackAndLog( eligibleCenter , user,httpServletRequest );
					
					tracker.track(eligibleCenter, "PV GENERATED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
					logger_.log(Constants.NORMAL_LOG_DIR, "Generated the PV: "+user);
					 
			        return "ok"; 
			 }else{
				 return "koNotReady";
			 }
			
	        }else if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"generate").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
			
		}else if(action.equals("reset_pv")){
			
			if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"reset_pv").equals("ok")){
			resetPvTrackAndLog( eligibleCenter , user,httpServletRequest );
			List<Applicant> students = studentSessionService.findByEntranceEligibleCenterAndResultOrderedByPerson((EntranceEligibleCenter)eligibleCenter,"SUCCEEDED");
			 for (int i=0; i<students.size(); i++){

				 Applicant studentSession = students.get(i);
				 studentSession.setPvNumber(0);
				 studentSessionService.updateApplicant(studentSession);
				
			 }
			tracker.track(eligibleCenter, "PV WAS RESETTED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Resetted the pv: "+user);
			
	        return "ok";
	        }else if(stateCheckerService.stateEmbedded((EntranceEligibleCenter)eligibleCenter, user,"reset_pv").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
		
			
		}

		return "ok";
	}
	
	private void resetPvTrackAndLog(EntranceEligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		eligibleCenterService.resetGeneratedPV(eligibleCenter, user);
		tracker.track(eligibleCenter, "GENERATED PV", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "PV RESETTED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ELIGIBLE CENTER ID IS "+eligibleCenter.getId(),httpServletRequest);
		
	}

	/*
	 *@Author MPA
	 *this function determines the indicator if all candidates meet requirements for the PV of that center to be generated 
	 */
	private boolean checkPVGenerable(EntranceEligibleCenter eligibleCenter) {
		
		List<Applicant> candidates = studentSessionService.findByPvResults(eligibleCenter);
		List<Applicant> allCandidates = studentSessionService.findByEntranceEligibleCenter(eligibleCenter);
		if (candidates.size()>0 || allCandidates.size()<=0){
			return false;
		}else{
			return true;
		}
		
	}

	/*
	 *@Author MPA
	 *this function helps to generate PV
	 */
	private void generatePvTrackAndLog(EntranceEligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stubpv_list
		eligibleCenterService.generatePV(eligibleCenter, user);
		tracker.track(eligibleCenter, "GENERATED PV", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "PV RESETTED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ELIGIBLE CENTER ID IS "+eligibleCenter.getId(),httpServletRequest);
		
	}

	

	/*
	 *@Author MPA
	 *this function helps to load list of EligibleCenters for Experts national concours
	 */

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String expertsPvlist(Model model){
		int year = Calendar.getInstance().get(Calendar.YEAR);	 
		model.addAttribute("sessions", examSessionService.findByYear(year));
	    model.addAttribute("entranceEligibleCenters", eligibleCenterService.findAll());
	    model.addAttribute("regions", regionService.findAll());
		return "applicant/pv_list_experts";
	}
	

	@RequestMapping(value="/manage_applicants", method=RequestMethod.GET)
	public String manage_pv_list(Model model, @RequestParam("entranceEligibleCenterId") int entranceEligibleCenterId, HttpServletRequest httpServletRequest){

		User currentUser = userService.getLogedUser(httpServletRequest);
		List<Speciality> eligibleSpecialities = specialityService.findAll() ;
		Speciality specialityInspecteur = specialityService.findByAbbreviation("IPCSR");
		Speciality specialityDelegue = specialityService.findByAbbreviation("DPCSR");
		List<TrainingCenter> listTrainingCentersInsp =trainingCenterService.findTrainingCenterByEligibleSpeciality(specialityInspecteur);
		List<TrainingCenter> listTrainingCentersDel =trainingCenterService.findTrainingCenterByEligibleSpeciality(specialityDelegue);

			for (TrainingCenter trainingCenter : listTrainingCentersInsp){
				if(!listTrainingCentersDel.contains(trainingCenter)){
					listTrainingCentersDel.add(trainingCenter);
				}
			}

		EntranceEligibleCenter entranceEligibleCenter= entranceEligibleCenterService.findById(entranceEligibleCenterId);
		List<Applicant> applicants;
		if(entranceEligibleCenter.getEntranceEligibleCenterStatus().getId() == 4){
			applicants = applicantService.findByEntranceEligibleCenterAndResult(entranceEligibleCenter, "PASSED PRACTICALS");
		}else{
			applicants = applicantService.findByEntranceEligibleCenterAndResult(entranceEligibleCenter, "REGISTERED");
		}

		List<Country> countries = countryService.findAll();
		model.addAttribute("countries", countries);
		model.addAttribute("eligibleSpecialities", eligibleSpecialities);
		model.addAttribute("applicants", applicants);
		model.addAttribute("entranceEligibleCenter", entranceEligibleCenter);
		model.addAttribute("allTrainingCenters", listTrainingCentersDel);

		return "applicant/manage_applicant_result";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String findPV(@RequestParam("region") int region,@RequestParam("session") int session, Model model, HttpServletRequest httpServletRequest){
		
		List<EntranceEligibleCenter> eligibleCenters = eligibleCenterService.findByRegionAndSessionDate(region, session);
		model.addAttribute("eligibleCenters", eligibleCenters);
		System.out.println("size is "+eligibleCenters.size());
		return "applicant/search-results"; 
		
	}
	
	@RequestMapping(value="/get/sessions", method=RequestMethod.GET)
	public String getSessions(@RequestParam("year") int year, Model model, HttpServletRequest httpServletRequest) {
		model.addAttribute("sessions", examSessionService.findByYear(year));
		return "pv/session-dropdown";
	}
	
	
	/*
	 *@uthor MPA
	 *this function helps to close a pv 
	 *
	 */
	
	
	private void resetpvTrackAndLog(EntranceEligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest ){
		eligibleCenterService.managePvReset( eligibleCenter,user);
		tracker.track(eligibleCenter, "RESET ELIGIBLE CENTER", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "PV RESETTED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ELIGIBLE CENTER ID IS "+eligibleCenter.getId(),httpServletRequest);
				
	}
	
	/*
	 *@uthor MPA
	 *this function helps to close a pv 
	 *
	 */
	
	private void closepvTrackAndLog(EntranceEligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest ){
		eligibleCenterService.managePvClose( eligibleCenter,user);
		tracker.track(eligibleCenter, "CLOSE ELIGIBLE CENTER", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "PV CLOSED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ELIGIBLE CENTER ID IS "+eligibleCenter.getId(),httpServletRequest);
				
	}
	
	/*
	 *@uthor MPA
	 *this function helps to validate a pv 
	 */
	private void vaidatepvTrackAndLog(EntranceEligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest ){
		eligibleCenterService.managePvValidate( eligibleCenter,user);
		tracker.track(eligibleCenter, "VALIDATE ELIGIBLE CENTER", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "PV VALIDATED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ELIGIBLE CENTER ID IS "+eligibleCenter.getId(),httpServletRequest);
				
	}
	
	
	/*
	 *@uthor MPA
	 *this function helps to open a pv 
	 */
	private void openpvTrackAndLog(EntranceEligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest ){
		eligibleCenterService.managePvOpen( eligibleCenter,user);
		tracker.track(eligibleCenter, "OPEN ELIGIBLE CENTER", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "PV OPENED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ELIGIBLE CENTER ID IS "+eligibleCenter.getId(),httpServletRequest);
				
	}
	 
	/*
	 *@uthor Paule
	 *this function helps to upload a file for pv validation
	 */
		
	@RequestMapping(value="/get/validationForm/{eligibleCenterId}", method=RequestMethod.GET)
	public String getValidationForm(Model model,@PathVariable(value="eligibleCenterId") int eligibleCenterId){
		 model.addAttribute("eligibleCenterId",  eligibleCenterId);
		 return "pv/change";
	}	

	

	@RequestMapping(value = "/print", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> pvApplicantPrintedPdf(@RequestParam("id") int id, @RequestParam("typeList") String typeList, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, DocumentException{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		printerService.printListApplicant(outStream, headers, id, typeList);

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(outStream.toByteArray());
	}
	
	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	@Secured({"ROLE_VIEW_PV"})
	public String create(HttpServletRequest request, Model model) {
		model.addAttribute("centers", examCenterService.findAll());
		return "pv/create";
	}

	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
	public String createPost(@RequestParam("centers") String centers, @RequestParam("sessionDate") String sessionDate,  HttpServletRequest request, Model model) throws ParseException {
		return eligibleCenterService.saveAllCenter(centers, sessionDate, request);	
	}
	
	//@ResponseBody
	@RequestMapping(value="/orderApplicants", method=RequestMethod.GET)
	public String orderPvList(Model model, @RequestParam("entranceEligibleCenterId") int entranceEligibleCenterId, HttpServletRequest httpServletRequest){
		EntranceEligibleCenter entranceEligibleCenter= entranceEligibleCenterService.findById(entranceEligibleCenterId);
		List<TrainingCenter> trainingCenters = trainingCenterService.findAll() ;
		List<Applicant> applicants;
		int count = 0;
		if(entranceEligibleCenter.getEntranceEligibleCenterStatus().getId() == 4){
			applicants = applicantService.findByEntranceEligibleCenterAndResultOrderedByNote(entranceEligibleCenter, "PASSED PRACTICALS");
		}else{
			applicants = applicantService.findByEntranceEligibleCenterAndResult(entranceEligibleCenter, "REGISTERED");
		}
		
			for(int i=0;i<applicants.size();i++) {
				int applicantId = applicants.get(i).getId();
				List<TrainingCenterChoice> choices = new ArrayList<>();
				TrainingCenterChoice firstTC = trainingCenterChoiceService.findTrainingCenterIdByApplicantIdAndFirstChoice(applicants.get(i), "First") ;
				TrainingCenterChoice secondTC = trainingCenterChoiceService.findTrainingCenterIdByApplicantIdAndFirstChoice(applicants.get(i), "Second") ;
				TrainingCenterChoice thirdTC = trainingCenterChoiceService.findTrainingCenterIdByApplicantIdAndFirstChoice(applicants.get(i), "Third") ;
				if(firstTC != null && firstTC.getTrainingCenter()!= null){
					choices.add(firstTC);
				}
				if(secondTC != null && secondTC.getTrainingCenter()!= null){
					choices.add(secondTC);
				}
				if(thirdTC != null && thirdTC.getTrainingCenter()!= null){
					choices.add(thirdTC);
				}
				boolean reValue = true;
				if(choices.size()!=0) {
					
					for(int n=0;n<choices.size();n++) {
						int trainingCenterId = choices.get(n).getTrainingCenter().getId();
						int maxStudent = choices.get(n).getTrainingCenter().getMaxStudent();
						TrainingCenter  center =trainingCenterService.findById(trainingCenterId);
						
						if (reValue && (firstTC.getTrainingCenter().getId() ==trainingCenterId) && (maxStudent!=0)){
						int maxStudentFinal = maxStudent-1 ;
						center.setMaxStudent(maxStudentFinal);
						trainingCenterService.save(center);
						createStudentFromApplicant1(applicantId,trainingCenterId, model, httpServletRequest);
						reValue = false;						
						
					}else if (reValue && (secondTC.getTrainingCenter().getId() ==trainingCenterId) && (maxStudent!=0)) {
						
						createStudentFromApplicant1(applicantId,trainingCenterId, model, httpServletRequest);
						int maxStudentFinal = maxStudent-1 ;
						center.setMaxStudent(maxStudentFinal);
						trainingCenterService.save(center);
						reValue =false;
						
					}else if (reValue && (thirdTC.getTrainingCenter().getId() ==trainingCenterId) && (maxStudent!=0)) {
						
						createStudentFromApplicant1(applicantId,trainingCenterId, model, httpServletRequest);
						int maxStudentFinal = maxStudent-1 ;
						center.setMaxStudent(maxStudentFinal);
						trainingCenterService.save(center);
						reValue= false;
						
					}else {
						count++;
					}
					}
				}else {
					return "training.center.max.student.zero";
				}
			
			}
			
		List<EligibleSpeciality> eligibleSpecialities = null;	
		User currentUser = userService.getLogedUser(httpServletRequest);
		if(currentUser.getTrainingCenter()!=null)
			eligibleSpecialities = currentUser.getTrainingCenter().getEligibleSpeciality() ;
		if(currentUser.getTrainingCenter()==null)
			eligibleSpecialities = eligibleSpecialityService.findAll() ;
		List<Country> countries = countryService.findAll();
		model.addAttribute("countries", countries);
		model.addAttribute("eligibleSpecialities", eligibleSpecialities);
		model.addAttribute("applicants", applicants);
		model.addAttribute("entranceEligibleCenter", entranceEligibleCenter);


		return "applicant/manage_applicant_result";
	}
	
	
	
	@RequestMapping(value="/createApplicantStudent", method=RequestMethod.POST)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String createStudentFromApplicant1(@RequestParam("applicantId") int applicantId,@RequestParam("trainingCenterId") int trainingCenterId , Model model, HttpServletRequest httpServletRequest){
		Applicant applicant = applicantService.findById(applicantId);
		Person person = applicant.getPerson();
		TrainingCenter trainingCenter = trainingCenterService.findById(trainingCenterId);
		List<Student> studentsPerCenter = studentService.findByTrainingCenterAndSpeciality(trainingCenter, applicant.getSpeciality());
		for (int i = 0; i < studentsPerCenter.size(); i++) {
			if (person!=null && (studentsPerCenter.get(i).getPerson().getId() == person.getId())) {
				return "dupilcation.of.student";
			}
			
		}
		
		Student student = new Student();
		student.setPerson(applicant.getPerson());
		student.setSpeciality(applicant.getSpeciality());
		student.setComputerizationDate(new Date());
		student.setTrainingCenter(trainingCenter);
		String entry=applicant.getDiplome();
	
		if(applicant.getPerson().getArchive() != null && applicant.getPerson().getArchive().getStatus().equals("VALIDATED") ){
			entry="CAPEC_MAE";
		}
		for(Student studentNew : applicant.getPerson().getStudents()){
			for(StudentSession studentSessionNew :studentNew.getStudentSessions()){
				if(studentSessionNew.getStudentSessionStatus().getId() == 4 && studentSessionNew.getSpeciality().getId() == 2 && studentSessionNew.getEligibleCenter().getEligibleCenterStatus().getId()==4)
				 entry ="CAPEC_IPCSR"; 
			}
		}
		student.setDiplome(entry);
		studentService.createStudent(student);
		
		String studentReferenceNumber = studentService.generateReferenceNum(student.getId(), trainingCenter);
		student.setReferenceNum(studentReferenceNumber);
		
		studentService.updateStudent(student);
		
		applicant.setStudent(student);
		applicantService.updateApplicant(applicant);
		
		model.addAttribute("trainingCenters",trainingCenter);
		model.addAttribute("applicant",applicant);	
		return "applicant/manage_applicant_result";
	}

	
}

