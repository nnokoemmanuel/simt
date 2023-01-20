package ppp.simt.controller.pv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import com.itextpdf.text.DocumentException;

import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.configuration.SpecialityPrerequisite;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.EligibleSpeciality;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.ExamSession;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentQualification;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionStatus;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.User;
import ppp.simt.form.CNIForm;
import ppp.simt.form.SimtLicenseChecker;
import ppp.simt.form.StudentForm;
import ppp.simt.form.validator.CniFormValidator;
import ppp.simt.form.validator.StudentFormValidator;
import ppp.simt.service.core.*;
import ppp.simt.service.engines.PrinterService;
import ppp.simt.service.pv.EligibleCenterService;
import ppp.simt.service.pv.EligibleSpecialityService;
import ppp.simt.service.pv.ExamCenterService;
import ppp.simt.service.pv.ExamSessionService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.service.pv.StudentSessionService;
import ppp.simt.service.pv.StudentSessionStatusService;
import ppp.simt.service.pv.TrainingCenterService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Helper;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;

@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	protected StudentFormValidator studentFormValidator;
	
	@Autowired
	protected CniFormValidator cniFormValidator;
	
	@Autowired
	protected UserService userService;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	protected StudentService studentService;
	
	@Autowired
	protected PersonService personService;
	
	@Autowired
	protected EligibilityChecher eligibilityChecher;
	
	@Autowired
	protected CountryService countryService;
	
	@Autowired
	protected TrainingCenterService trainingCenterService;
	
	@Autowired
	protected SpecialityService specialityService;
	
	@Autowired
	protected EligibleSpecialityService eligibleSpecialityService;
	
	@Autowired
	private ExamSessionService examSessionService;
	
	@Autowired
	private ExamCenterService examCenterService;
	
	@Autowired
	private EligibleCenterService eligibleCenterService;
	
	@Autowired
	private StudentSessionStatusService studentSessionStatusService;
	
	@Autowired
	private StudentSessionService studentSessionService;
	
	@Autowired
	private EligibilityChecher eligibilityChecker;
	
	@Autowired
	private PrinterService printerService;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired
	private CoreService coreService;

	@Autowired
	private SpecialityPrerequisiteService specialityPrerequisiteService;

	
	@InitBinder("studentForm")
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    binder.setValidator(studentFormValidator);
	}
	
	@InitBinder("cniForm")
    protected void initBinders(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    binder.setValidator(cniFormValidator);
	}
	/*
	 *@uthor Nguesuh
	 *this function helps to load student creation form 
	 */
@RequestMapping(value="/createGet", method=RequestMethod.GET)
	public String createStudent(Model model, HttpServletRequest httpServletRequest){
		User currentUser = userService.getLogedUser(httpServletRequest);
		List<Student> students = studentService.findAll();
		List<Person> persons = personService.findAll();
		List<EligibleSpeciality> eligibleSpecialities = currentUser.getTrainingCenter().getEligibleSpeciality() ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
		java.util.Date now = new java.util.Date();
		List<Student> finalListStudents = new ArrayList<Student>();
		for (int i = 0; i < students.size(); i++) {
			java.util.Date studentComputeDate = students.get(i).getComputerizationDate();
			int numDays = (int) Helper.daysBetween(now, students.get(i).getComputerizationDate());
			if(numDays<=31 && students.get(i).getStudentSessions().size()==0) {
				if(students.get(i).getTrainingCenter().getId() == currentUser.getTrainingCenter().getId())
					finalListStudents.add(students.get(i));
			}
		}
		
		//countryService.findCountryById(31);
		//model.addAttribute("defaultSelectedCountry",countryService.findCountryById(31));
		
		model.addAttribute("finalListStudents", finalListStudents);
		model.addAttribute("persons", persons);
		model.addAttribute("countries", countryService.findAll());
		model.addAttribute("eligibleSpecialities", eligibleSpecialities);
		return "pv/create_student";
	}
	
	
	
	
	/*
	 *@uthor Nguesuh
	 *this function helps to persist candidate during creation 
	 */
	@RequestMapping(value="/createPost", method=RequestMethod.POST)
	@ResponseBody
	public String createStudentPost(@ModelAttribute("studentForm") @Validated StudentForm studentForm,  BindingResult result,HttpServletRequest httpServletRequest, Model model){
		User currentUser = userService.getLogedUser(httpServletRequest);
		if(result.hasErrors()){
			if(result.getFieldError("capacities")!= null){

				logger_.log(Constants.NORMAL_LOG_DIR, "CANDIDATE ALREADY IN SYSTEM"+result.getFieldError("capacities").getCode(),httpServletRequest);
				return result.getFieldError("capacities").getCode();
				
			} 
			
		}
		Person person = personService.findPersonByLicenseNum(studentForm.getLicenseNum());
		Speciality speciality = specialityService.findById(studentForm.getSpeciality());
		List<Student> studentsPerCenter = studentService.findByTrainingCenterAndSpeciality(currentUser.getTrainingCenter(), speciality);
		if (person!=null ) {
			if(person.getArchive() != null)
				return "student.has.archive";
			else{
				for (Student student : person.getStudents()){
					for(StudentSession studentSession : student.getStudentSessions()){
						if(studentSession.getStudentSessionStatus().getId() == 4 )
							return "student.has.archive";
					}
				}
			}
		
		}
		for (int i = 0; i < studentsPerCenter.size(); i++) {
			if (person!=null && studentsPerCenter.get(i).getPerson().getId() == person.getId()) {
				return "dupilcation.of.student";
			}
			
		}
		Student student = studentService.persistStudent(studentForm, httpServletRequest, person);
		TrainingCenter trainingCenter = currentUser.getTrainingCenter();
		List<Student> students = null;
		if(trainingCenter != null)
			students = studentService.findByTrainingCenter(trainingCenter);
		
		List<Person> persons = personService.findAll();
		model.addAttribute("students", students);
		model.addAttribute("persons", persons);
		return "pv/list_candidate";
		
		
	} 
	
	@RequestMapping(value="/verify/applicant/license", method=RequestMethod.GET)
	@ResponseBody
	public  HashMap<String, String> searchStudentLicense(@RequestParam("license") String license,@RequestParam("speciality") int speciality,@RequestParam("diplome") String diplome, HttpServletRequest http, HttpServletResponse response ) {			
			HashMap<String, String> map = new HashMap<>();
			String specialty;
			if(speciality==1);
			specialty = "moniteur";
			if(speciality==2 || speciality==3) {
			specialty = "expert";
			}
			SimtLicenseChecker result = eligibilityChecher.check(license, specialty, diplome);
			//System.out.println("yoooooooooooo");
			if(result.getCan_be_register().equals("no")) {
				//System.out.println("can't continueeee");
				map.put("can_be_register", result.getCan_be_register());
				return map;
			}
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
	
	
	@RequestMapping("/detail")
	public String show(@RequestParam("id") int id,Model model) {

		Student student= studentService.findById(id);
		model.addAttribute("student", student);
		String Speciality = student.getSpeciality().getAbbreviation();
		if(Speciality.equals("MAE")) Speciality="moniteur";
		else Speciality = "expert";

		String diplome = student.getDiplome().trim();
		SimtLicenseChecker result= eligibilityChecher.check(student.getPerson().getLicenseNum().trim(), Speciality,diplome);
		String date = String.valueOf(result.getCatB_Date());
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String catBDate= "null";
		if(!date.equals("null")){
			catBDate = formatter.format(result.getCatB_Date());
		}

		model.addAttribute("student", student);
		//Test for cat G :: MAE or Experts[IPCSR, DPCSR]
		String all_categories = result.getCategories();
		 if(!all_categories.contains("G")) {
				model.addAttribute("all_categories",all_categories);
			}
		//
		String actifSession ="yes";
		List<ExamSession> session = examSessionService.findByStatusOrderByIdDesc("OPEN");

		if(session.isEmpty()){

			actifSession ="no";
		}else {

			model.addAttribute("session", session.get(0));
			model.addAttribute("eligibleCenters", eligibleCenterService.findByExamSession(session.get(0)));
		}


		List<StudentSession> studentSessions = new ArrayList<StudentSession>();
		for (Student studentS: student.getPerson().getStudents() ) {
		List<StudentSession> studentSession = studentSessionService.findByStudentOrderByEligibleCenterAsc(studentS);
			if(studentSession.size()>0){
				studentSessions.addAll(studentSession);
			}
		}
		String messageResult ="";
		if(student.getSpeciality().getAbbreviation().equals("MAE")){
			 messageResult = testElligibilityMae(studentSessions ,student);
		}else if(student.getSpeciality().getAbbreviation().equals("IPCSR")){
			 messageResult = testElligibilityIpcsr(studentSessions,student ,result);
		}else if(student.getSpeciality().getAbbreviation().equals("DPCSR")){
			 messageResult = testElligibilityDpcsr( studentSessions, student, result);
		}

		model.addAttribute("messageResult", messageResult);

		model.addAttribute("isActifSession",actifSession);
		model.addAttribute("categories", result.getCategories());
		model.addAttribute("examCenters", examCenterService.findAll());
		model.addAttribute("CatB_Date",catBDate);
		return "pv/candidate_details";
	}



	public String testElligibilityDpcsr(List<StudentSession> studentSessions, Student student, SimtLicenseChecker result) {

		String resultIpcsr = testElligibilityIpcsr(studentSessions , student,result );
		String message =resultIpcsr;

		if(resultIpcsr.equals("already_has_ipcsr") ){
			if( result.getCan_be_presented().equals("yes")){
				boolean test1 = false;
				boolean test2 = false;
				for (StudentSession studentSession:studentSessions) {
					if(studentSession.getSpeciality().getAbbreviation().equals("DPCSR")){
						test1 = true;
						if(studentSession.getStudentSessionStatus().getId() == 4 && studentSession.getEligibleCenter().getEligibleCenterStatus().getId()==4){
							test2 =true;
						}
					}
				}
				if(test1){
					if(test2){
						message= "already_has_dpcsr";
					}else {
						if(studentSessions.get(studentSessions.size()-1).getEligibleCenter().getEligibleCenterStatus().getId()!= 4){
							message= "pending_session";
						}else {

							String elegibilityOfYears = checkEligibilityDurationAtTraining( "DPCSR" ,student);

							if(elegibilityOfYears.equals("eligible")) {

								message ="to_presented_dpcsr";
							}else {
								message = "number_of_years_not_reach";
							}
							//message = "to_presented_dpcsr";
						}
					}

				}else {
					String elegibilityOfYears = checkEligibilityDurationAtTraining( "DPCSR" ,student);

					if(elegibilityOfYears.equals("eligible")) {

						message ="to_presented_dpcsr";
					}else {
						message = "number_of_years_not_reach";
					}
					//message ="to_presented_dpcsr";
				}
			}else {
				message ="experts_cat_required";
			}
		}

		return message;
	}


	public String testElligibilityIpcsr(List<StudentSession> studentSessions, Student student, SimtLicenseChecker result) {

		String resultMae = testElligibilityMae(studentSessions , student);
		String message =resultMae;

			if(resultMae.equals("already_has_mae")){
				 if(  result.getCan_be_presented().equals("yes")){
					boolean test1 = false;
					boolean test2 = false;
					for (StudentSession studentSession:studentSessions) {
						if(studentSession.getSpeciality().getAbbreviation().equals("IPCSR")){
							test1 = true;
							if(studentSession.getStudentSessionStatus().getId() == 4 && studentSession.getEligibleCenter().getEligibleCenterStatus().getId()==4){
								test2 =true;
							}
						}
					}
					if(test1){
						if(test2){
							message= "already_has_ipcsr";
						}else {
							if(studentSessions.get(studentSessions.size()-1).getEligibleCenter().getEligibleCenterStatus().getId()!= 4){
								message= "pending_session";
							}else {

								String elegibilityOfYears = checkEligibilityDurationAtTraining( "IPCSR" ,student);
								if(elegibilityOfYears.equals("eligible")) {
									message ="to_presented_ipcsr";
								}else {
									message = "number_of_years_not_reach";
								}
								//message = "to_presented_ipcsr";
							}
						}
					}else {

						String elegibilityOfYears = checkEligibilityDurationAtTraining( "IPCSR" ,student);
						if(elegibilityOfYears.equals("eligible")) {
							message ="to_presented_ipcsr";
						}else {
							message = "number_of_years_not_reach";
						}
						//message ="to_presented_ipcsr";
					}

				}else{
					message ="experts_cat_required";

				}
			}

		return message;
	}


	public String testElligibilityMae(List<StudentSession> studentSessions , Student student) {
		String message ="";

		if(student.getPerson().getArchive()!= null){
			message= "already_has_mae";
		}else {
			if ( studentSessions.isEmpty() ){

				String elegibilityOfYears = checkEligibilityDurationAtTraining( "MAE" ,student);
				if(elegibilityOfYears.equals("eligible")) {

					message = "to_presented_mae";
				}else {
					message = "number_of_years_not_reach";
				}
				//message = "to_presented_mae";
			}else {
				boolean test = false;
				for (StudentSession studentSession: studentSessions) {
					if(studentSession.getSpeciality().getAbbreviation().equals("MAE") && studentSession.getStudentSessionStatus().getId() == 4 && studentSession.getEligibleCenter().getEligibleCenterStatus().getId()==4){
						test =true;
					}
				}
				if(test){
					message= "already_has_mae";
				}else {
					if(studentSessions.get(studentSessions.size()-1).getEligibleCenter().getEligibleCenterStatus().getId()!= 4){
						message= "pending_session";
					}else {

						String elegibilityOfYears = checkEligibilityDurationAtTraining( "MAE" ,student);
						if(elegibilityOfYears.equals("eligible")) {

							message = "to_presented_mae";
						}else {
							message = "number_of_years_not_reach";
						}
						//message = "to_presented_mae";
					}
				}

			}
		}
		return message;
	}

	@RequestMapping(value="/idCardForm", method=RequestMethod.GET) 
	public String idCardFormGET(@RequestParam("id") int studentSessionId,Model model) {
		
		StudentSession studentSession = studentSessionService.findById(studentSessionId);
		model.addAttribute("studentSession",studentSession);
		return "pv/candidate_details_idCardForm";
	}
	

	
	@RequestMapping("/present")
	@ResponseBody
	public String registers(@RequestParam("eligible") int eligible, @RequestParam("candidate") int candidate, @RequestParam("messageResult") String messageResult, HttpServletRequest httpServletRequest) {
		Student student= studentService.findById(candidate);
		String retour ="";
		
		/**
		 * checking eligibility
		 */


		EligibleCenter center = eligibleCenterService.findById(eligible);
		StudentSessionStatus status = studentSessionStatusService.findById(1);
		StudentSession newStudentSession = new StudentSession();
		newStudentSession.setCountFile(0);
		newStudentSession.setEligibleCenter(center);
		newStudentSession.setProcessed(0);
		newStudentSession.setStudentSessionStatus(status);
		newStudentSession.setStudent(student);

		if(messageResult.equals("to_presented_mae")) {

			newStudentSession.setSpeciality(specialityService.findById((long) 1));
			studentSessionService.save(newStudentSession, httpServletRequest);
			retour = "accepted_mae";

		}else  if(messageResult.equals("to_presented_ipcsr")) {

			newStudentSession.setSpeciality(specialityService.findById((long) 2));
			studentSessionService.save(newStudentSession, httpServletRequest);
			retour = "accepted_ipcsr";

		}else if(messageResult.equals("to_presented_dpcsr")) {

			newStudentSession.setSpeciality(specialityService.findById((long) 3));
			studentSessionService.save(newStudentSession, httpServletRequest);
			retour = "accepted_dpcsr";

		}

        User user = userService.getLogedUser(httpServletRequest);
		tracker.track(newStudentSession, "STUDENT is Presented in this STUDENT SESSION" , httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "Created the Student Session: "+user); 

		return retour;
	}
	
	@RequestMapping("/view")
	public String view(@RequestParam("id") int id,Model model) {
		
		Student student= studentService.findById(id);
		model.addAttribute("student", student);
		   String speciality="expert";
		   if(student.getSpeciality().getAbbreviation()=="MAE")
			   speciality="moniteur";
		String diplome = student.getDiplome().trim();
		SimtLicenseChecker result= eligibilityChecher.check(student.getPerson().getLicenseNum().trim(),speciality,diplome);

		String date = String.valueOf(result.getCatB_Date());
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String catBDate= "null";
		if(!date.equals("null")){
			catBDate = formatter.format(result.getCatB_Date());
		}


		 Set<StudentSession> studentSessions = student.getStudentSessions();

		
		String status ="no";
		String photo = "";
		int countFile  ;
		if(student.getStudentSessions().size()>0) {
			for(StudentSession studentSession : studentSessions){
				if(!(studentSession.getStudentSessionStatus().getId() == 1 || studentSession.getStudentSessionStatus().getId() == 3)) {
					status = "OK";
					photo = studentSession.getPhotoAndSignature();
					countFile = studentSession.getCountFile();
					model.addAttribute("countFile", countFile);
					
					break;
				}
				
			}
			model.addAttribute("photo", photo);
			
		}
		
		model.addAttribute("studentSession_Status", status);
		
		String actifSession ="yes";
		List<ExamSession> session = examSessionService.findByStatusOrderByIdDesc("OPEN");
		if(session.size()==0 || student.getStudentSessions().size()!=0 || result.getCan_be_presented().equals("no")) {
			
			actifSession ="no";
		}else {
			
			model.addAttribute("session", session.get(0));
			model.addAttribute("eligibleCenters", eligibleCenterService.findByExamSession(session.get(0)));
		}
		 
		//Test for cat G :: MAE or Experts[IPCSR, DPCSR]
		String all_categories = result.getCategories();
		 if(!all_categories.contains("G")) {
				model.addAttribute("all_categories",all_categories);
				
			} 
		 


		model.addAttribute("isActifSession","no");
		model.addAttribute("categories", result.getCategories());
		model.addAttribute("examCenters", examCenterService.findAll());
		model.addAttribute("CatB_Date",catBDate);
		return "pv/candidate_details"; 
	}
	

    @ResponseBody
	@RequestMapping(value="/save/idCardInfo", method=RequestMethod.POST) 
	public String saveStudentIdCard(@RequestParam("studentSessionId") int studentSessionId, @ModelAttribute("cniForm") @Validated CNIForm cniForm, BindingResult result, Model model,    HttpServletRequest httpServletRequest) throws ParseException {
		StudentSession studentSession = studentSessionService.findById(studentSessionId);
		Person person = studentSession.getStudent().getPerson();
		person = personService.addPersonIdInfoToPerson(person, cniForm);
		StudentQualification studentQualification = studentSessionService.addStudentHighestQualificationInformation(studentSession, cniForm, httpServletRequest);
		studentSession.setStudentQualification(studentQualification);
		studentSessionService.updateStudentSession(studentSession);
		System.out.println(person);
		System.out.println(studentQualification);
		return "ok";
	}

	public String checkEligibilityDurationAtTraining ( String specialityEligible, Student student) {

		String message = "not_eligible";
		List<ExamSession> session = examSessionService.findByStatusOrderByIdDesc("OPEN");
		if(session.isEmpty()){
			message = "not_eligible";
		}else{
			Date currentSessionDate = session.get(0).getSessionDate();
			Calendar cal = Calendar.getInstance();
			Date registrationDate = student.getComputerizationDate();
			cal.setTime(registrationDate);
			int durationInMonths;

			if (specialityEligible.equals("MAE")) {
				SpecialityPrerequisite specialityPrerequisite = specialityPrerequisiteService.findById(1);
				 durationInMonths = specialityPrerequisite.getDuration();
			} else {
				Speciality speciality = specialityService.findByAbbreviation(specialityEligible);
				List<SpecialityPrerequisite> specialityPrerequisite = specialityPrerequisiteService.findBySpecialityAndDIplomaStatus(speciality, student.getDiplome());
				 durationInMonths = specialityPrerequisite.get(0).getDuration();
			}

			cal.add(Calendar.MONTH, durationInMonths);
			Date eligibleDateTobePresented = cal.getTime();
			System.out.println("date candidate "+eligibleDateTobePresented);
			System.out.println("date session "+currentSessionDate);
			if (eligibleDateTobePresented.compareTo(currentSessionDate) <= 0) {
				message = "eligible";
			}
		}
		return message;
	}
	
	@RequestMapping(value="/admittedList", method=RequestMethod.GET)
	public String listOfAdmitted(Model model){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		model.addAttribute("currentYear", year);
		model.addAttribute("sessions", examSessionService.findByYear(year));
		model.addAttribute("examCenters", examCenterService.findAll());
	    model.addAttribute("trainingCenters", trainingCenterService.findAll());
		return "student/admitted_list";
	}
	
	@RequestMapping(value="/getAdmitted", method=RequestMethod.GET)
	public String searchAdmitted(Model model,@RequestParam("sessionId") int sessionId,@RequestParam("examCenterId") int examCenterId,@RequestParam("trainingCenterId") int trainingCenterId){
        ExamSession session = examSessionService.findById(sessionId);
        ExamCenter examCenter = examCenterService.findById(examCenterId);
        EligibleCenter eligibleCenter = null;
        if(eligibleCenterService.findByExamSessionAndExamCenter(session, examCenter).size() > 0)
        	eligibleCenter=eligibleCenterService.findByExamSessionAndExamCenter(session, examCenter).get(0);
        TrainingCenter trainingCenter = trainingCenterService.findById(trainingCenterId);
        List<StudentSession> listOfStudentSessions = new ArrayList<StudentSession>();
        List<StudentSession> listOfStudentSessionsOfTrainingCenter = new ArrayList<StudentSession>();
        if(eligibleCenter != null && eligibleCenter.getEligibleCenterStatus().getDescription().equals("VALIDATED")){
        	listOfStudentSessions = studentSessionService.findByStudentSessionStatusAndEligibleCenter(studentSessionStatusService.findById(4), eligibleCenter);
        	listOfStudentSessions.forEach((studentSession)->{
        		if(studentSession.getStudent().getTrainingCenter().getId() == trainingCenter.getId())
        			listOfStudentSessionsOfTrainingCenter.add(studentSession);
        		
        	});
        }
        model.addAttribute("studentSessions", listOfStudentSessionsOfTrainingCenter);
		return "student/admitted_student_table";
	}
	
	@RequestMapping(value = "/printAdmittedPdfList", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> outSlipApplicationPrintedPdf(@RequestParam("sessionId") int sessionId,@RequestParam("examCenterId") int examCenterId,@RequestParam("trainingCenterId") int trainingCenterId, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, DocumentException{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		ExamSession session = examSessionService.findById(sessionId);
	    ExamCenter examCenter = examCenterService.findById(examCenterId);
	    EligibleCenter eligibleCenter = null;
	    if(eligibleCenterService.findByExamSessionAndExamCenter(session, examCenter) != null && eligibleCenterService.findByExamSessionAndExamCenter(session, examCenter).size() > 0)
	       eligibleCenter=eligibleCenterService.findByExamSessionAndExamCenter(session, examCenter).get(0);
	    TrainingCenter trainingCenter = trainingCenterService.findById(trainingCenterId);
		printerService.printAdmittedPdf(outStream, headers, eligibleCenter, trainingCenter);
		

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(outStream.toByteArray());
	}

	
}
