
/**
* Cette classe permet de manipuler les pv (eligibles centers)
 */

package ppp.simt.controller.pv;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.env.Environment;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.DocumentException;

import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.ExamSession;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionStatus;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.user.User;
import ppp.simt.form.CandidateChekerForm;
import ppp.simt.form.StudentForm;
import ppp.simt.form.validator.StudentFormValidator;
import ppp.simt.repository.pv.StudentSessionRepository;
import ppp.simt.service.core.CategoryService;
import ppp.simt.service.core.CountryService;
import ppp.simt.service.engines.PrinterService;
import ppp.simt.service.evaluation.CourseService;
import ppp.simt.service.evaluation.TranscriptService;
import ppp.simt.service.pv.EligibleCenterService;
import ppp.simt.service.pv.EligibleCenterStatusService;
import ppp.simt.service.pv.ExamSessionService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.service.pv.StudentSessionService;
import ppp.simt.service.pv.StudentSessionStatusService;
import ppp.simt.service.pv.TrainingCenterService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Controller
@RequestMapping("/studentSession")
public class StudentSessionController {
	@Autowired
    ServletContext context;
	@Autowired
	private Environment env;
	
	@Autowired 
	private Logger_ logger_;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TrainingCenterService trainingCenterService;
	
	@Autowired
	private EligibleCenterService eligibleCenterService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private StudentSessionService studentSessionService;
	
	@Autowired
	private StudentSessionStatusService studentSessionStatusService;
	
	@Autowired
	protected StudentFormValidator studentFormValidator;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private ExamSessionService examSessionService;
	
	@Autowired
	private TranscriptService transcriptService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentSessionRepository studentSessionRepository;
	
	@Autowired
	private EligibleCenterStatusService eligibleCenterStatusService;

	@Autowired
	private PrinterService printerService;

	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    binder.setValidator(studentFormValidator);
	}
	

	
	/*
	 *@uthor Nguesuh
	 *this function helps to load candidate creation form 
	 */
	@RequestMapping(value="/createGet", method=RequestMethod.GET)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String createCandate(Model model, HttpServletRequest httpServletRequest){
		User currentUser = userService.getLogedUser(httpServletRequest);
		List<TrainingCenter> trainingCenters = trainingCenterService.findAll();
		model.addAttribute("countries", countryService.findAll());
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("trainingCenters", trainingCenters);
		//model.addAttribute("candidateSessions", canSessionByDs);
		return "pv/create_candidate";
	}
	
	/*
	 *@uthor Nguesuh
	 *this function helps to persist candidate during creation 
	 */
	@RequestMapping(value="/createPost", method=RequestMethod.POST)
	@ResponseBody
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String createCandatePost(@ModelAttribute("candidateForm") @Validated StudentForm studentForm,  BindingResult result,HttpServletRequest httpServletRequest, Model model){
		User currentUser = userService.getLogedUser(httpServletRequest);
		if(result.hasErrors()){
			if(result.getFieldError("pob")!= null){

				logger_.log(Constants.NORMAL_LOG_DIR, "CANDIDATE ALREADY IN SYSTEM"+result.getFieldError("capacities").getCode(),httpServletRequest);
				return result.getFieldError("capacities").getCode();
				
			} 
			
		}
		CandidateChekerForm candidateChekerForm = studentSessionService.checkPrerequis(studentForm);
			
		StudentSession studentSession = studentSessionService.persistStudent(studentForm, httpServletRequest, candidateChekerForm.getPerson());
		return String.valueOf(studentSession.getId());
		
		
	} 
	
	
	/*
	 *@uthor Nguesuh
	 *this function helps to persist candidate during creation 
	 */
	@RequestMapping(value="/createDupPost", method=RequestMethod.POST)
	@ResponseBody
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String createDupCandatePost(@ModelAttribute("candidateForm") @Validated StudentForm candidateForm,  BindingResult result ,@RequestParam("eligibleCenterId") int eligibleCenterId,HttpServletRequest httpServletRequest, Model model){
		User currentUser = userService.getLogedUser(httpServletRequest);

		StudentSession candidateSession = studentSessionService.persistDup(candidateForm, httpServletRequest, eligibleCenterId);

		return String.valueOf(candidateSession.getId()); 
	}
	

	//emms
	 @RequestMapping(value="/upload_additional_backing_document", method=RequestMethod.POST) 
	    public String applicationNewFilesView(HttpServletRequest httpServletRequest) {
	        return "/pv/new-files"; 
	    }    
	 
	 @RequestMapping(value="/upload_additional_backing_document_file_preview", method=RequestMethod.POST)
	    public String previewFilesView(HttpServletRequest httpServletRequest) {
	        return "/pv/new-files-preview";  
	    }

	 
	 
	 
	 
	 /**
     Reason for Reject Student in a StudentSession :: GET
     */
	 @RequestMapping(value="/get/validationForm/reason_for_reject/get/{studentSessionId}", method=RequestMethod.GET)
		public String GET_reason_for_reject_ValidationForm(Model model,
				                                           @PathVariable(value="studentSessionId") int studentSessionId){
		    
			model.addAttribute("studentSessionId",  studentSessionId);
			return "pv/reason_for_reject";
		} 
	 
	 
	 
     /**
      (Alt Solution) Reason for Reject Student in a StudentSession :: GET
      */
	 /*
	 @RequestMapping(value="/manage/reason_for_reject/get", method=RequestMethod.GET)
	    public String GET_reason_for_reject(
	    		                            @RequestParam("id") int id, 
	    		                            @RequestParam ("id") String reasonForReject,
	    		                            HttpServletRequest httpServletRequest,
	    		                            Model model) {
		 
		 System.out.println("GET reason_for_reject FORM");
		 
		 User currentUser= userService.getLogedUser(httpServletRequest);
		 StudentSession one_StudentSession_Reason_for_reject_id = studentSessionService.findById(id);
		
		 model.addAttribute("studentSessionId", id ); 
		 model.addAttribute("studentSessionjsp", one_StudentSession_Reason_for_reject_id);
		
			
	        return "pv/reason_for_reject";  
	    }
	 */
	 
	 
	 /**
     Reason for Reject Student in a StudentSession :: POST
     this function works in-hand with   :: @RequestMapping(value="/manage", method=RequestMethod.POST)
     */
	 @RequestMapping(value="/manage/reason_for_reject/post/{id}/{reasonForReject}", method=RequestMethod.GET)
	    public String POST_reason_for_reject(@PathVariable int id,
	    		                             @PathVariable String reasonForReject,
	    		                            /* @RequestParam ("result") int result,
			    		                     @RequestParam("type") String type,  */
			    		                     HttpServletRequest httpServletRequest,
			    		                     Model model) {
		 
		// System.out.println("POST reason_for_reject FORM");
		 
//FindById (1 student)
		 
		 StudentSession one_StudentSession_Reason_for_reject_id = studentSessionService.findById(id);
		 
		 one_StudentSession_Reason_for_reject_id.getReasonForReject();
		 String reasonForReject1 =  one_StudentSession_Reason_for_reject_id.getReasonForReject();

		 
		// StudentSessionStatus studentSessionStatus = studentSessionStatusService.findById(result);
		 //System.out.println("studentSessionStatus =="+studentSessionStatus);
		 
		 one_StudentSession_Reason_for_reject_id.setReasonForReject(reasonForReject);
		 //System.out.println("setReasonForReject() =="+reasonForReject);
		 
		 studentSessionService.updateStudentSession(one_StudentSession_Reason_for_reject_id);
		 
//ListAll		              
		  List<StudentSession> listall_StudentSession_Reason_for_reject_java = studentSessionService.findAll();
		  model.addAttribute("students", listall_StudentSession_Reason_for_reject_java);
								 
            //If size of the  List<StudentSession> = 1
		    if(listall_StudentSession_Reason_for_reject_java.size()> 0 ){
				 
				 System.out.println("test ok");
			}
		 
								
//Tracking								 
		    User user = userService.getLogedUser(httpServletRequest); 
			tracker.track(one_StudentSession_Reason_for_reject_id, "Reject the Student for this STUDENT SESSION"+"\n "+"[ REASON ::]"+reasonForReject , httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Reject the Student for this STUDENT SESSION: "+user); 
		 
		 
	        return "pv/list_one_student";  
	    }
	
	

	@RequestMapping(value="/manage", method=RequestMethod.POST)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String updateCandidateResults(@RequestParam("id") int id,@RequestParam("result") int result, @RequestParam("type") String type,Model model, HttpServletRequest httpServletRequest){
		if(!type.equals("upload")) {

			StudentSession studentSession = studentSessionService.findById(id);
			StudentSessionStatus studentSessionStatus = studentSessionStatusService.findById(result);

			studentSession.setStudentSessionStatus(studentSessionStatus);
			studentSessionService.updateStudentSession(studentSession);
			List<Course> remainingCourses = new ArrayList<Course>();

			remainingCourses = courseService.findBySpeciality(studentSession.getStudent().getSpeciality());
			List<Course> coursesTokeep = new ArrayList<Course>();

			if ((result == 4 || result == 5) && type.equals("enter-notes")) {
				List<Transcript> listeStudentSessionNotes = new ArrayList<Transcript>();
				Speciality studentSessionSpeciality = studentSession.getSpeciality();
				if(studentSessionSpeciality == null)
					studentSessionSpeciality = studentSession.getStudent().getSpeciality();
				model.addAttribute("courses", courseService.findBySpeciality(studentSessionSpeciality));
				System.out.println("yes it here num courses "+courseService.findBySpeciality(studentSessionSpeciality).size());
				List<Course> coursPratiques = courseService.findByCompleteName("Epreuve facultative / Optional paper");
				List<Course> coursesRemaining = courseService.findBySpeciality(studentSessionSpeciality);
				Course coursPratique;
				for(Course course :coursPratiques){
					if(studentSessionSpeciality.getId()==course.getModule().getSpeciality().getId()){
						coursPratique = course;
						if(transcriptService.findByStudentSessionAndCourse(studentSession, course)==null){
							Transcript notePratiqueInit = new Transcript();
							notePratiqueInit.setCourseNote(0);
							notePratiqueInit.setStudentSession(studentSession);
							notePratiqueInit.setCourse(course);
							transcriptService.createTranscript(notePratiqueInit);
						}
					}
				}
				
				listeStudentSessionNotes = transcriptService.findByStudentSession(studentSession);
				List<Course> coursAyantDejaDesNotes = new ArrayList<Course>();
				
				for(Transcript transcript : listeStudentSessionNotes)
					coursAyantDejaDesNotes.add(transcript.getCourse());
				
				if(coursAyantDejaDesNotes.size() > 0){
					coursesRemaining.clear();
					for(Course course : courseService.findBySpeciality(studentSessionSpeciality)){
						boolean foundTheCourse = false;
						for(Course courseAvecNote : coursAyantDejaDesNotes){
							if(course.getId()== courseAvecNote.getId()){
								foundTheCourse = true;
							}
						}
						if(!foundTheCourse)
							coursesRemaining.add(course);
					}
				}
				
				
				model.addAttribute("listeStudentSessionNotes", listeStudentSessionNotes);
				model.addAttribute("studentSession", studentSession);
				model.addAttribute("coursesRemaining", coursesRemaining);
			
				/*if (listeStudentSessionNotes.size() > 0) {
					coursesTokeep = courseService.findBySpecialityWithoutTranscriptsLink(studentSession.getStudent().getSpeciality());
					model.addAttribute("coursesRemaining", coursesTokeep);
				}*/
				
				model.addAttribute("studentSession", studentSession);
				return "evaluation/enter_notes";
			}


			studentSession.setStudentSessionStatus(studentSessionStatus);


			studentSession.getReasonForReject();
			if (studentSession.getReasonForReject() == null || studentSession.getReasonForReject() == "") {

				System.out.println("Reason for Reject is NULL = " + studentSession.getReasonForReject());

			} else {


				System.out.println("Reason for Reject is NOT NULL = " + studentSession.getReasonForReject());
				studentSessionService.updateStudentSession(studentSession);

			}


			String messageStudent = getMessageFromStudentExamResult(type);
			if (messageStudent != null) {
				tracker.track(studentSession, messageStudent, httpServletRequest);
				System.out.println("list_one_student");

				return "pv/list_one_student";
			}


			String message = getMessageFromExamResult(type);
			if (message != null)
				tracker.track(studentSession, message, httpServletRequest);



		}else {
			StudentSession studentSession = studentSessionService.findById(id);
			model.addAttribute("studentSession", studentSession);
			return "evaluation/list_one_student_documents_upload";
		}
		return "pv/manage_candidate_result";
	}
	
	
	
	private String getMessageFromExamResult(String type) {
		String message ="";
		switch(type){
		case "send-back-to-admit":
			message = "SENT BACK TO ADMITTED";
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
		case "send-back-theory":
			message = "SENT BACK TO PASS THEORY";
			break;
		default:
			//message=" Not Implemented_" + type;
			message=" STUDENT is : " + type;
		}
		return message;
	}

	
	private String getMessageFromStudentExamResult(String type) {
		String messageStudent ="";
		switch(type){

		/*case "id -> status"
		      message = "msg"
		   */ 
		case "presented":
			messageStudent = "PRESENTED";
			break;
		case "approved":
			messageStudent = "APPROVED";
			break;
		case "mark-as-pass":
			messageStudent = "MARK AS PASS";
			break;
		case "mark-as-fail":
			messageStudent = "MARKED AS FAIL";
			break;
		case "rejected ":
			messageStudent = "REJECTED";
			break;
			
			
			
			
		case "send-from-approved":
			messageStudent = "SEND FROM APPROVED";
			break;
			
		case "send-from-reject":
			messageStudent = "SEND FROM REJECT";
			break;
			
		case "send-from-pass-to-approved":
			messageStudent = "SEND FROM PASS TO APPROVED";
			break;	
			
		case "send-from-fail-to-approved":
			messageStudent = "SEND FROM FAIL TO APPROVED";
			break;	
			
			
		default:
			//messageStudent=" Not Implemented_" + type;
			messageStudent="STUDENT is " + type;
		}
		return messageStudent;
	}


	@RequestMapping(value="/load", method=RequestMethod.GET)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String loadCandidates(@RequestParam("eligibleCenter") int eligibleCenterId,@RequestParam("result") String result, Model model, HttpServletRequest httpServletRequest){
		int examResult = Integer.parseInt(result);

		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		List<StudentSession> studentSessions = studentSessionService.findByEligibleCenterAndResult(eligibleCenter, examResult);
		model.addAttribute("students",studentSessions);
		model.addAttribute("indicator", result);
		model.addAttribute("eligibleCenterId", eligibleCenterId);
		//return "pv/list_candidate";
		return "pv/list_student_result";
	}
	
	@RequestMapping(value="/manage/batch", method=RequestMethod.POST)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String updateCandidateResultsBatch(@RequestParam("eligibleCenterId") int eligilbleCenterId, @RequestParam("candidates") String candidates,@RequestParam("result") int result, @RequestParam("indicator") String indicator, @RequestParam("action") String action, Model model, HttpServletRequest httpServletRequest){
		String[] candidateList = candidates.split("@");
		List<Integer> candidateIds = new ArrayList<Integer>();
		List<StudentSession> candidateSessions;
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligilbleCenterId);
		for (String candidate: candidateList){
			int candidateId = Integer.valueOf(candidate);
			candidateIds.add(candidateId);
		}
		for (int candidateId: candidateIds ) {
			StudentSession candidateSession = studentSessionService.findById(candidateId);
			//candidateSession.setExamResult(result);
			studentSessionService.updateStudentSession(candidateSession);
			String message = getMessageFromExamResult(action);
			String messageStudent =getMessageFromStudentExamResult(action);
			tracker.track(candidateSession, message, httpServletRequest);
			tracker.track(candidateSession, messageStudent, httpServletRequest);
		}
		
		
		if(indicator != "all"){
			int index = Integer.valueOf(indicator);
			//candidateSessions = studentSessionService.findByEligibleCenterAndResultOrderedByPerson(eligibleCenter, index);
		}else{
			candidateSessions = studentSessionService.findByEligibleCenter(eligibleCenter);
		}
		model.addAttribute("indicator", indicator);
		//model.addAttribute("candidateSessions",candidateSessions);
		
		//return "pv/list_candidate";
		return "pv/list_student";
	}

	/*
	 *@uthor Paule
	 *this function helps to upload a file for pv validation
	 */

	@RequestMapping(value="/get/validationForm/{studentSessionId}", method=RequestMethod.GET)
	public String getValidationForm(Model model,@PathVariable(value="studentSessionId") int studentSessionId){
		model.addAttribute("studentSessionId",  studentSessionId);
		model.addAttribute("upload",  0);
		return "pv/validation_student_file";
	}

	@ResponseBody
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String managePvStates(@PathVariable(value="id") int id ,HttpServletRequest httpServletRequest, Model model){
		return "ok";
	}
	
	@ResponseBody
	@RequestMapping("/manageStatus")
	public String changeStatus(@RequestParam("id") int id, @RequestParam("action") String action) {
		ExamSession session= examSessionService.findById(id);
			Set<EligibleCenter> centers=session.getEligibleCenters();
			ArrayList<EligibleCenter> eligibleCenters = new ArrayList<>();
			eligibleCenters.addAll(centers);
		if(action.equals("Open") || action.equals("Reopen")) {
			session.setStatus("OPEN");
			for(int i=0; i<eligibleCenters.size();i++) {
				EligibleCenter elCenter=eligibleCenters.get(i);
				elCenter.setEligibleCenterStatus(eligibleCenterStatusService.findById(2));
				eligibleCenterService.save(elCenter);
			}
			
			}
		else session.setStatus("CLOSE");
		examSessionService.save(session);
		return "accepted";
	}
	
	
	@RequestMapping(value="/examenResult", method=RequestMethod.GET)
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String ApplicantExamenResult(@RequestParam("studentSessionId") int studentSessionId,Model model, HttpServletRequest httpServletRequest){
		StudentSession studentSession = studentSessionService.findById(studentSessionId);
		model.addAttribute("studentSession",studentSession);
		return "pv/studentSession_examen_result";
	}
    
	@ResponseBody
	@RequestMapping(value="/saveFinalResult")
	@Secured({"ROLE_MANAGE_CANDIDATE"})
	public String saveFinalResultFromStudentSession(@RequestParam("studentSessionId") int studentSessionId, @RequestParam("note") float note , Model model, HttpServletRequest httpServletRequest){
		StudentSession studentSession = studentSessionService.findById(studentSessionId);
		studentSession.setNote(note);
		studentSessionService.updateStudentSession(studentSession);
		return "ok";

	}

	@RequestMapping(value="/get/validationForms/{studentSessionId}", method=RequestMethod.GET)
	public String getValidationFormDocuments(Model model,@PathVariable(value="studentSessionId") int studentSessionId){
		model.addAttribute("studentSessionId",  studentSessionId);
		model.addAttribute("upload",  1);
		return "pv/validation_student_file";
	}


	
	@RequestMapping(value = "/printSubscription", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> transcriptPrintedPdf(@RequestParam("studentSessionId") int studentSessionId,@RequestParam("language") String language, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, DocumentException{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		if(language.equals("FR"))
			printerService.printRegistrationFr( outStream,  headers,  studentSessionId);
		else
			printerService.printRegistrationEn( outStream,  headers,  studentSessionId);

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(outStream.toByteArray());
	}
	
	
}

