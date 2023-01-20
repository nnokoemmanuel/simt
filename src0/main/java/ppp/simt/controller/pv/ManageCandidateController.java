package ppp.simt.controller.pv;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.EligibleSpeciality;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.Role;
import ppp.simt.entity.user.User;
import ppp.simt.repository.pv.StudentSessionRepository;
import ppp.simt.service.applicant.ApplicantService;
import ppp.simt.service.archive.ArchiveService;
import ppp.simt.service.core.*;
import ppp.simt.service.pv.StudentSessionFileService;
import ppp.simt.service.pv.StudentSessionService;
import ppp.simt.service.pv.StudentSessionStatusService;
import ppp.simt.service.pv.EligibleCenterService;
import ppp.simt.service.pv.EligibleCenterStatusService;
import ppp.simt.service.pv.EligibleSpecialityService;
import ppp.simt.service.pv.ExamCenterService;
import ppp.simt.service.pv.ExamSessionService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.service.user.GroupService;
import ppp.simt.service.user.RoleService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/manageCandidate")
public class ManageCandidateController {
	
	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentSessionStatusService studentSessionStatusService;
	
	@Autowired
	private CoreService coreService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;
	

	
	@Autowired
	private ArchiveService archiveService;
    
	@Autowired
	private ExamSessionService examSessionService;
	
	@Autowired
	private EligibleCenterService eligibleCenterService;
	
	@Autowired
	private ExamCenterService examCenterService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private StudentSessionFileService studentSessionFileService;
	
	@Autowired
	private StudentSessionService studentSessionService;
	
	@Autowired
	private EligibleCenterStatusService eligibleCenterStatusService;
	
	@Autowired
	protected EligibilityChecher eligibilityChecher;
	
	@Autowired
	private EligibleSpecialityService eligibleSpecialityService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private GroupService groupService;
	
	
	@Autowired
    ServletContext context;
	
	@Autowired
	private StudentSessionRepository studentSessionRepository;

	@Autowired
	private SpecialityService specialityService;

	@Autowired
	private ApplicantService applicantService;
	
	@Value("${capacity.preview.folder}")
	private String capacityImageFolder;
    
	
	/*
	 *@uthor MPA
	 *this controller load default list of students for a training center 
	 */ 

	@Secured({"ROLE_MANAGE_STUDENTS_UNDER_TRAINING"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String candidatesList(HttpServletRequest httpServletRequest, Model model) {

		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		User user = userService.getLogedUser(httpServletRequest);
		
		Role roleViewAllTrainingCenters = roleService.findByName("ROLE_VIEW_TRAINNING_CENTERS"); 
		Role roleManageStudents = roleService.findByName("ROLE_MANAGE_STUDENTS_UNDER_TRAINING");
		List<Student> listOfStudents =new ArrayList<>() ;

		List<Student> listOfStudentWIthNoDuplicates =new ArrayList<>();

		if(user.getRoles().contains(roleManageStudents)){
			if(user.getTrainingCenter() != null){
				listOfStudents = studentService.findByComputerizationDateBetweenAndTrainingCenter(cal.getTime(), today, user.getTrainingCenter());

			}else{
				listOfStudents = studentService.findByComputerizationDateBetween(cal.getTime(), today);

			}
		}
		Group groupAdmin = groupService.findByName("GROUP ADMIN");
		if(user.getRoles().contains(roleViewAllTrainingCenters)){
			if(!user.getGroup().equals(groupAdmin))
				listOfStudents = studentService.findByComputerizationDateBetween(cal.getTime(), today);

		}
		
		for(Student student : listOfStudents){
			if(!listOfStudentWIthNoDuplicates.contains(student))
				listOfStudentWIthNoDuplicates.add(student);
		}
		
		List<EligibleCenter>  listEligibleCentersOpened = eligibleCenterService.findAll();
		model.addAttribute("listType", "Student");
		model.addAttribute("listOpenEligibleCenters", listEligibleCentersOpened);

		model.addAttribute("listToDisplay", listOfStudentWIthNoDuplicates);
		
		
		return "pv/training_center_manage_list";
	}
	

	@Secured({"ROLE_MANAGE_STUDENTS_UNDER_TRAINING"})
	@RequestMapping(value = "/listeSearchCandidate", method = RequestMethod.GET)
	public String listeSearchApplication(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
									 @RequestParam("status") int status,@RequestParam("eligibleCenterId") int eligibleCenterId, @RequestParam("licenseNumber") String licenseNumber,
									 @RequestParam("matricule") String matricule,  @RequestParam("speciality") int speciality, HttpServletRequest httpServletRequest, Model model) throws ParseException {
		List<Student> listOfStudents =new ArrayList<>();
		List<Student> listOfStudent =new ArrayList<>();

		List<StudentSession> listOfStudentSessions =new ArrayList<>();
		java.sql.Date sqlStartDate = null;
		java.sql.Date sqlendDate = null;
		User user = userService.getLogedUser(httpServletRequest);
		List<EligibleSpeciality> trainingCenterSpecialities = eligibleSpecialityService.findByTrainingCenter(user.getTrainingCenter());
		Role roleViewAllTrainingCenters = roleService.findByName("ROLE_VIEW_TRAINNING_CENTERS"); 
		Role roleManageStudents = roleService.findByName("ROLE_MANAGE_STUDENTS_UNDER_TRAINING");
		Group groupAdmin = groupService.findByName("GROUP ADMIN");

		if(!(licenseNumber.equals("undefined") && matricule.equals("undefined"))){
			if(!licenseNumber.equals("") || !matricule.equals("") ){
				if(!licenseNumber.equals("") && !licenseNumber.equals("undefined")){
					Person personsFound = personService.findPersonByLicenseNum(licenseNumber);
					if(personsFound != null){
						for(Student student : personsFound.getStudents()){
							if(user.getRoles().contains(roleManageStudents)){
								if(student.getTrainingCenter().getId() == user.getTrainingCenter().getId())
									listOfStudents.add(student);
							}else{
								if(user.getRoles().contains(roleViewAllTrainingCenters)){
									listOfStudents.add(student);
								}
								
							}
						
						}
						
					}
				}
				if(!matricule.equals("") && !matricule.equals("undefined")){
					Student student = studentService.findByReferenceNum(matricule);
					
					if(user.getRoles().contains(roleManageStudents)){
						if(student != null &&student.getTrainingCenter().getId() == user.getTrainingCenter().getId())
							listOfStudents.add(student);
					}else{
						if(student != null &&user.getRoles().contains(roleViewAllTrainingCenters)){
							listOfStudents.add(student);
						}
						
					}
				}
				model.addAttribute("listType", "Student");
				model.addAttribute("listToDisplay", listOfStudents);
		   }
		}else if(licenseNumber.equals("undefined") && matricule.equals("undefined")){
			
			if(!startDate.isEmpty()){
			java.util.Date startDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
			java.util.Date endDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
			sqlStartDate = new java.sql.Date(startDateConverted.getTime());
			sqlendDate = new java.sql.Date(endDateConverted.getTime());
				if(status == 0){
					if(user.getRoles().contains(roleManageStudents)){
						if(user.getTrainingCenter() != null){
							listOfStudents = studentService.findByComputerizationDateBetweenAndTrainingCenter(startDateConverted, endDateConverted, user.getTrainingCenter());
							
						}else{
							listOfStudents = studentService.findByComputerizationDateBetween(startDateConverted, endDateConverted);
							
						}
					}
					
					if(user.getRoles().contains(roleViewAllTrainingCenters)){
						if(!user.getGroup().equals(groupAdmin))
							listOfStudents = studentService.findByComputerizationDateBetween(startDateConverted, endDateConverted);
					}
				    
				    model.addAttribute("listType", "Student");
					model.addAttribute("listToDisplay", listOfStudents);
				}
				
		    }else if (speciality == 0){

				
					if(eligibleCenterId > 0){
						for(EligibleSpeciality elligilbeSpeciality : trainingCenterSpecialities){
							EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
								if(user.getRoles().contains(roleManageStudents)){
									if(user.getTrainingCenter() != null)
										listOfStudentSessions.addAll(studentSessionService.findByEligibleCenterAndResultAndSpecialityAndTrainingCenter(eligibleCenter, status, elligilbeSpeciality.getSpeciality(), user.getTrainingCenter())) ;	
									else
										listOfStudentSessions.addAll(studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, status, elligilbeSpeciality.getSpeciality())) ;	

								}else{
									if(user.getRoles().contains(roleViewAllTrainingCenters)){
										listOfStudentSessions.addAll(studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, status, elligilbeSpeciality.getSpeciality())) ;	
									}
									
								}
						}
					}else{
						for(EligibleSpeciality elligilbeSpeciality : trainingCenterSpecialities){
							if(user.getRoles().contains(roleManageStudents)){
								if(user.getTrainingCenter() != null)
									listOfStudentSessions.addAll(studentSessionService.findByResultAndSpecialityAndTrainingCenter(status, elligilbeSpeciality.getSpeciality(), user.getTrainingCenter())) ;	
								else
									listOfStudentSessions.addAll(studentSessionService.findByResultAndSpeciality(status, elligilbeSpeciality.getSpeciality())) ;	

							}else{
								if(user.getRoles().contains(roleViewAllTrainingCenters)){
									listOfStudentSessions.addAll(studentSessionService.findByResultAndSpeciality(status, elligilbeSpeciality.getSpeciality())) ;	
								}
								
							}
						
						
						
						}
					}
				
				model.addAttribute("listType", "StudentSession");
				model.addAttribute("listToDisplay", listOfStudentSessions);
				
				
			}else {
				Speciality specialite = specialityService.findById((long) speciality);
				if(eligibleCenterId > 0){

						EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
						if(user.getRoles().contains(roleManageStudents)){
							if(user.getTrainingCenter() != null)
								listOfStudentSessions = studentSessionService.findByEligibleCenterAndResultAndSpecialityAndTrainingCenter(eligibleCenter, status, specialite, user.getTrainingCenter()) ;
							else
								listOfStudentSessions=studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, status, specialite) ;

						}else{
							if(user.getRoles().contains(roleViewAllTrainingCenters)){
								listOfStudentSessions= studentSessionService.findByEligibleCenterAndResultAndSpeciality(eligibleCenter, status, specialite) ;
							}

						}

				}else{

						if(user.getRoles().contains(roleManageStudents)){
							if(user.getTrainingCenter() != null)
								listOfStudentSessions= studentSessionService.findByResultAndSpecialityAndTrainingCenter(status, specialite, user.getTrainingCenter()) ;
							else
								listOfStudentSessions= studentSessionService.findByResultAndSpeciality(status,specialite) ;

						}else{
							if(user.getRoles().contains(roleViewAllTrainingCenters)){
								listOfStudentSessions= studentSessionService.findByResultAndSpeciality(status, specialite) ;
							}

						}

				}

				model.addAttribute("listType", "StudentSession");
				model.addAttribute("listToDisplay", listOfStudentSessions);
				
			
			}
	
		}
		return "pv/training_center_candidates_list_search";
	}
	
 
	
	@Secured({"ROLE_MANAGE_STUDENTS_UNDER_TRAINING"})
	@RequestMapping(value = "/filterSpeciality", method = RequestMethod.GET)
	public String listSearchOrigin( @RequestParam("specialityId") long specialityId, HttpServletRequest httpServletRequest, Model model) throws ParseException {
		
		Speciality speciality = specialityService.findById((long)specialityId);  
		//System.out.println("speciality  ="+speciality.getId());
		 
		List<Student> student = studentService.findAll();
		
        List<Student> search_EmptyList_MAE = new ArrayList<Student>();
         
        List<Applicant> applicant = applicantService.findAll();
        
        List<Applicant> applicant_EmptyList_IPCSR_DPCSR = new ArrayList<Applicant>();
		
        StudentSession studentSession = null;
         
         
         
         
         if((student != null || studentSession != null) && speciality.getId()== (long)1 ){
        	 
         
         for(Student studentOrigin : student){
        	 
        	 if(studentOrigin.getSpeciality().getAbbreviation() == "MAE" || studentOrigin.getSpeciality().getId() == (long)1){
        		 
        		 search_EmptyList_MAE.add(studentOrigin);
        		 //System.out.println("search_EmptyList_MAE == "+search_EmptyList_MAE);
            	 }
        	 
        	 model.addAttribute("student_from_studentSession", search_EmptyList_MAE);
        	 
            } 
         
         //model.addAttribute("listType", "Student");
    	 //model.addAttribute("listType", "StudentSession");
       
            return "pv/training_center_candidates_list_search";
         } 
         
         
         
         
         if((applicant != null || studentSession != null) && speciality.getId() == (long)2 ){ 
        	 
        	 
           for(Applicant applicantOrigin : applicant){
        	   
        	 if(applicantOrigin.getSpeciality().getAbbreviation() == "IPCSR" || applicantOrigin.getStudent().getSpeciality().getAbbreviation() == "IPCSR" || 
        			 applicantOrigin.getSpeciality().getAbbreviation() == "DPCSR" || applicantOrigin.getStudent().getSpeciality().getAbbreviation() == "DPCSR" ||
        			               applicantOrigin.getSpeciality().getId() == (long)2 || applicantOrigin.getSpeciality().getId() != (long)1){
        		 
        		 
		        		 applicant_EmptyList_IPCSR_DPCSR.add(applicantOrigin);
		        		 System.out.println("SEARCHLIST IPCSR or DPCSR ="+applicant_EmptyList_IPCSR_DPCSR);
        		
            	 }
        	
        	
        	    model.addAttribute("student_from_concour", applicant_EmptyList_IPCSR_DPCSR);
              }
           
           
           //model.addAttribute("listType", "Student");
      	   model.addAttribute("listType", "StudentSession");
        	
        	 
             return "pv/training_center_candidates_list_search";

         }	 
         
	
	 
	return "ok";
	}
     
	
	    
}


