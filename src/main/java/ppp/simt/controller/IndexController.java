package ppp.simt.controller;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Module;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.tracking.AgreementTracking;
import ppp.simt.entity.tracking.ApplicantTracking;
import ppp.simt.entity.tracking.ApplicationTracking;
import ppp.simt.entity.tracking.ArchiveFileTracking;
import ppp.simt.entity.tracking.ArchiveTracking;
import ppp.simt.entity.tracking.StudentSessionTracking;
import ppp.simt.entity.tracking.StudentTracking;
import ppp.simt.entity.tracking.TrainingCenterTracking;
import ppp.simt.entity.tracking.EligibleCenterTracking;
import ppp.simt.entity.tracking.EntranceEligibleCenterTracking;
import ppp.simt.entity.tracking.InSlipTracking;
import ppp.simt.entity.tracking.OutSlipTracking;
import ppp.simt.entity.tracking.UserTracking;
import ppp.simt.entity.tracking.evaluation.CourseTracking;
import ppp.simt.entity.tracking.evaluation.ModuleTracking;
import ppp.simt.entity.tracking.evaluation.TranscriptTracking;
import ppp.simt.entity.user.GroupRole;
import ppp.simt.entity.user.Role;
import ppp.simt.entity.user.User;
import ppp.simt.form.CategoryForm;
import ppp.simt.service.applicant.ApplicantTrackingService;
import ppp.simt.service.applicant.EntranceEligibleCenterTrackingService;
import ppp.simt.service.archive.ArchiveFileTrackingService;
import ppp.simt.service.archive.ArchiveTrackingService;
import ppp.simt.service.core.ApplicationTrackingService;
import ppp.simt.service.core.CategoryRetrivalService;
import ppp.simt.service.core.InSlipTrackingService;
import ppp.simt.service.core.OutSlipTrackingService;
import ppp.simt.service.evaluation.CourseService;
import ppp.simt.service.evaluation.CourseTrackingService;
import ppp.simt.service.evaluation.ModuleService;
import ppp.simt.service.evaluation.ModuleTrackingService;
import ppp.simt.service.evaluation.TranscriptService;
import ppp.simt.service.evaluation.TranscriptTrackingService;
import ppp.simt.service.pv.StudentSessionTrackingService;
import ppp.simt.service.pv.StudentTrackingService;
import ppp.simt.service.pv.TrainingCenterService;
import ppp.simt.service.pv.TrainingCenterTrackingService;
import ppp.simt.service.pv.AgreementService;
import ppp.simt.service.pv.AgreementTrackingService;
import ppp.simt.service.pv.EligibleCenterTrackingService;
import ppp.simt.service.user.UserDetail;
import ppp.simt.service.user.UserTrackingService;

@Controller
public class IndexController {
	
	@Autowired
	private UserDetail userService ;
	
	@Autowired
	private UserTrackingService userTrackingService;
	
	@Autowired(required=true)
	private ApplicationTrackingService applicationTrackingService;
	
	@Autowired
	private ArchiveTrackingService archiveTrackingService; 
	
	@Autowired
	private ArchiveFileTrackingService archiveFileTrackingService;
	
	@Autowired
	private InSlipTrackingService inSlipTrackingService;
	
	@Autowired
	private OutSlipTrackingService outSlipTrackingService;
	
	@Autowired
	private EligibleCenterTrackingService eligibleCenterTrackingService;
	
	@Autowired
	private StudentSessionTrackingService studentSessionTrackingService;
	
	@Autowired
	private StudentTrackingService studentTrackingService;

	@Autowired
	private EntranceEligibleCenterTrackingService entranceEligibleCenterTrackingService;
	
	@Autowired
	private ApplicantTrackingService  applicantTrackingService;
	
	@Autowired
	private CourseTrackingService  courseTrackingService;
	
	@Autowired
	private ModuleTrackingService  moduleTrackingService;
	
	@Autowired
	private ModuleService  moduleService;
	
	@Autowired
	private CourseService  courseService;
	
	@Autowired
	private TrainingCenterService  trainingCenterService;
	
	@Autowired
	private AgreementService  agreementService;
	
	@Autowired
	private TrainingCenterTrackingService  trainingCenterTrackingService;
	
	@Autowired
	private AgreementTrackingService  agreementTrackingService;

	@Autowired
	private TranscriptService  transcriptService;
	
	@Autowired
	private TranscriptTrackingService  transcriptTrackingService;
	
	@Value("${software.version}")
	private String version;
	
	@Autowired
	private CategoryRetrivalService categoryRetrival;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLoginPage(@RequestParam(value="error", required=false) Boolean error,Model model,HttpServletRequest httpServletRequest){
        User user = userService.getLogedUser(httpServletRequest);
		if(user != null) {
			model.addAttribute("version", version);
			model.addAttribute("user", user);
			return "home";
			
		}
		
		return "login";
	}
	
	@RequestMapping(value={"/home","/"})
	public String test(@RequestParam(value="login", required=false) Boolean logout, Model model,HttpServletRequest httpServletRequest){
      User user = userService.getLogedUser(httpServletRequest);
      
     
      User connectedUser = userService.getLogedUser(httpServletRequest);
		
		Set <GroupRole> assignedGroupRoles = connectedUser.getGroup().getGroupRoles();
		Set<Role> assignedUserRoles = connectedUser.getRoles();
		Set<Role> actualRolesInGroup = new HashSet<Role>();
		
		for (GroupRole role: assignedGroupRoles) {
			actualRolesInGroup.add(role.getRoleId());
		}

		actualRolesInGroup.removeAll(assignedUserRoles);
		model.addAttribute("connectedUser", connectedUser);
		model.addAttribute("groupRoles", actualRolesInGroup);
      
     
		
      
	  if(user != null) {
	      model.addAttribute("user", user);
	      model.addAttribute("version", version);
	   }
		return "home";
	}

	
    @RequestMapping(value="/sw.js", method=RequestMethod.GET)
		
	public String serviceWorkerPage(HttpServletRequest httpServletRequest){	
		return "sw";
	}
		

    @RequestMapping(value="/toto", method=RequestMethod.GET)
		
    @ResponseBody
	public String toto(HttpServletRequest httpServletRequest){	
		return "hello world";
    	
		
	}
   
    
    @RequestMapping(value="/simt/tracking", method=RequestMethod.GET)
    	public String displayTracking(@RequestParam("entity")String entity, @RequestParam("id") int id, Model model){	 
    	System.out.println("tracking");
		 switch (entity) {
		  
				case "user":
							
						ArrayList <UserTracking> userListTracking=(ArrayList<UserTracking>) userTrackingService.findByUserId(id);
						model.addAttribute("data", userListTracking);
						return "tracking";
					
				case "application":
						
					    System.out.println(" case application");
						ArrayList <ApplicationTracking> applicationListTracking=(ArrayList<ApplicationTracking>) applicationTrackingService.findByApplicationId(id);
						model.addAttribute("data", applicationListTracking);
						return "tracking";
		
				case "archive": 
					
					    System.out.println(" case archive");
						ArrayList <ArchiveTracking> archiveListTracking=(ArrayList<ArchiveTracking>) archiveTrackingService.findByArchiveId(id);
						model.addAttribute("data", archiveListTracking);
						return "tracking";
					
				case "archiveFile":
					
					    System.out.println("case archiveFile");
						ArrayList <ArchiveFileTracking> archiveFileListTracking=(ArrayList<ArchiveFileTracking>) archiveFileTrackingService.findByArchiveFileId(id);
						model.addAttribute("data", archiveFileListTracking);
						return "tracking";
				
				case "inSlip":
					
					    System.out.println(" case inslip");
						ArrayList <InSlipTracking> inSlipListTracking=(ArrayList<InSlipTracking>) inSlipTrackingService.findByInSlipId(id);						
						model.addAttribute("data", inSlipListTracking);
						return "tracking";	
					
                case "outSlip":
					
						ArrayList <OutSlipTracking> outSlipListTracking=(ArrayList<OutSlipTracking>) outSlipTrackingService.findByOutSlipId(id);
						model.addAttribute("data", outSlipListTracking);
						return "tracking";
				
				case "eligibleCenter":
					
					    System.out.println("Archive -PV Tracking");	
						ArrayList <EligibleCenterTracking> eligibleCenterListTracking=(ArrayList<EligibleCenterTracking>) eligibleCenterTrackingService.findByEligibleCenterId(id);
						model.addAttribute("data", eligibleCenterListTracking);
						return "tracking";
				
				case "entranceEligibleCenter":
					
				    System.out.println("EntranceEligibleCenter EXAM-PV Tracking");	
					ArrayList <EntranceEligibleCenterTracking> entranceEligibleCenterListTracking=(ArrayList<EntranceEligibleCenterTracking>) entranceEligibleCenterTrackingService.findByEntranceEligibleCenterId(id);
					model.addAttribute("data", entranceEligibleCenterListTracking);
					return "tracking";
					
				//case "candidateSession":
				case "studentSession":
					
				    System.out.println("studentSession Tracking");	
					ArrayList <StudentSessionTracking> candidateSessionListTracking=(ArrayList<StudentSessionTracking>) studentSessionTrackingService.findByStudentSessionId(id);
					model.addAttribute("data", candidateSessionListTracking);
					return "tracking";
					
                  case "student":
					
				    System.out.println("student Tracking");	
					ArrayList <StudentTracking> studentListTracking=(ArrayList<StudentTracking>) studentTrackingService.findByStudentId(id);
					model.addAttribute("data", studentListTracking);
					return "tracking";
					
                  case "applicant":
						
					    System.out.println(" Applicant Tracking");
						ArrayList <ApplicantTracking> applicantListTracking=(ArrayList<ApplicantTracking>) applicantTrackingService.findByApplicantId(id);
						model.addAttribute("data", applicantListTracking);
						return "tracking";
                  case "course":
						
					    System.out.println(" course Tracking");
					    Course course = courseService.findById(id);
						ArrayList <CourseTracking> courseTracking=(ArrayList<CourseTracking>) courseTrackingService.findByCourse(course);
						model.addAttribute("data", courseTracking);
						return "tracking";
						
                  case "module":
						
					    System.out.println(" module Tracking");
					    Module module = moduleService.findModuleById(id);
						ArrayList <ModuleTracking> moduleTracking=(ArrayList<ModuleTracking>) moduleTrackingService.findByModule(module);
						model.addAttribute("data", moduleTracking);
						return "tracking";
						
                  case "trainingCenter":
						
					    System.out.println(" training Center Tracking");
					    TrainingCenter trainingCenter = trainingCenterService.findById(id);
						ArrayList <TrainingCenterTracking> trainingCenterTracking=(ArrayList<TrainingCenterTracking>) trainingCenterTrackingService.findByTrainingCenter(trainingCenter);
						model.addAttribute("data",trainingCenterTracking);
						return "tracking";
						
                  case "agreement":
						
					    System.out.println(" agreement Tracking");
					    Agreement agreement = agreementService.findById(id);
						ArrayList <AgreementTracking> agreementTracking=(ArrayList<AgreementTracking>) agreementTrackingService.findByAgreement(agreement);
						model.addAttribute("data",agreementTracking);
						return "tracking";
						
                  case "transcript": 
						
					    System.out.println(" transcript Tracking");
					    Transcript transcript = transcriptService.findById(id);
						ArrayList <TranscriptTracking> transcriptTracking=(ArrayList<TranscriptTracking>) transcriptTrackingService.findByTranscript(transcript);
						model.addAttribute("data",transcriptTracking);
						return "tracking";
				
				default:
					
						System.out.println("do nothing"); 
					}
		 
			
    	 return "ok";  
    		
    }	
	
    
	@RequestMapping("/test/geeting")
	@ResponseBody
	public List<CategoryForm>test(@RequestParam("license") String license ) {
		return categoryRetrival.findAll(license);
	}
	
}
