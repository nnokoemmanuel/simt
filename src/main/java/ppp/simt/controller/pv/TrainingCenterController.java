/**
* @author MPA
* Cette classe permet d effectuer le CRUD pour les Cours
* 
*/
package ppp.simt.controller.pv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.DocumentException;

import ppp.simt.entity.core.Division;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.EligibleSpeciality;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.ExamSession;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.tracking.TrainingCenterTracking;
import ppp.simt.entity.tracking.evaluation.CourseTracking;
import ppp.simt.entity.user.User;
import ppp.simt.form.TrainingCenterForm;
import ppp.simt.form.evaluation.CourseForm;
import ppp.simt.service.core.DivisionService;
import ppp.simt.service.core.PersonService;
import ppp.simt.service.core.SpecialityService;
import ppp.simt.service.engines.PrinterService;
import ppp.simt.service.evaluation.CourseService;
import ppp.simt.service.evaluation.CourseTrackingService;
import ppp.simt.service.evaluation.ModuleService;
import ppp.simt.service.evaluation.TranscriptService;
import ppp.simt.service.pv.AgreementService;
import ppp.simt.service.pv.AgreementTrackingService;
import ppp.simt.service.pv.EligibleCenterService;
import ppp.simt.service.pv.EligibleCenterStatusService;
import ppp.simt.service.pv.EligibleSpecialityService;
import ppp.simt.service.pv.ExamCenterService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.service.pv.StudentSessionService;
import ppp.simt.service.pv.TrainingCenterService;
import ppp.simt.service.pv.TrainingCenterTrackingService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;
import ppp.simt.entity.tracking.AgreementTracking;


@Controller
@RequestMapping(value = "/trainingCenter")
public class TrainingCenterController {
	
	@Autowired
	private SpecialityService specialityService;
	
	@Autowired
	private TrainingCenterService trainingCenterService;
	
	@Autowired
	private ExamCenterService examCenterService;
	
	@Autowired
	private DivisionService divisionService;
	
	@Autowired
	private AgreementService agreementService;
	
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private EligibleSpecialityService eligibleSpecialityService;
	
	@Autowired
	private TrainingCenterTrackingService trainingCenterTrackingService;
	
	@Autowired
	private AgreementTrackingService agreementTrackingService;
	
	@Autowired
	private PrinterService printerService;
	
	@Autowired
	private EligibleCenterService eligibleCenterService;
	
	
	@Autowired
	private EligibleCenterStatusService eligibleCenterStatusService;
	
	@Autowired
	private PersonService personService;
	
	
	@Autowired
	private StudentSessionService studentSessionService;
	

	@Autowired 
	private Logger_ logger_;

	@Secured({"ROLE_MANAGE_TRAINNING_CENTERS"})
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createGet(Model model) {
		model.addAttribute("specialities", specialityService.findAll());
		model.addAttribute("examCenters", examCenterService.findAll());
		model.addAttribute("divisions", divisionService.findAll());
		return "pv/trainingCenter_create";
	}
	
	@Secured({"ROLE_MANAGE_TRAINNING_CENTERS"})
	@RequestMapping(value = "/edit/{trainingCenterId}", method = RequestMethod.GET)
	public String editGet(Model model,@PathVariable int trainingCenterId) {
		TrainingCenter trainingCenter = trainingCenterService.findById(trainingCenterId);
		model.addAttribute("trainingCenter", trainingCenter);
		List<Speciality> allSpecialitiesLeft = new ArrayList<Speciality>();
		allSpecialitiesLeft = specialityService.findAll();
		for(EligibleSpeciality eligibleSpeciality : eligibleSpecialityService.findByTrainingCenter(trainingCenter) ){
			allSpecialitiesLeft.remove(eligibleSpeciality.getSpeciality());
		}
	
		model.addAttribute("specialitiesLeft", allSpecialitiesLeft);
		model.addAttribute("trainingCenterSpecialities", eligibleSpecialityService.findByTrainingCenter(trainingCenter));
		model.addAttribute("examCenters", examCenterService.findAll());
		model.addAttribute("divisions", divisionService.findAll());
		return "pv/trainingCenter_edit";
	}
	
	@Secured({"ROLE_VIEW_TRAINNING_CENTERS" , "ROLE_MANAGE_TRAINNING_CENTERS"})
	@RequestMapping(value = "/show/{trainingCenterId}", method = RequestMethod.GET)
	public String showGet(Model model,@PathVariable int  trainingCenterId) {
		TrainingCenter trainingCenter = trainingCenterService.findById(trainingCenterId);
		model.addAttribute("trainingCenter", trainingCenter);
		model.addAttribute("divisions", divisionService.findAll());
		return "pv/trainingCenter_show";
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@Transactional
	@Secured({"ROLE_MANAGE_TRAINNING_CENTERS"})
	public String createTrainingCenter(@ModelAttribute("trainingCenterform") @Validated TrainingCenterForm trainingCenterForm, BindingResult result, HttpServletRequest httpServletRequest) throws ParseException {
		TrainingCenter trainingCenter = null;
		if(result.hasErrors()){
			if(result.getFieldError("vide")!= null){
				logger_.log(Constants.NORMAL_LOG_DIR, "training center form empty "+result.getFieldError("vide").getCode(),httpServletRequest);
				return result.getFieldError("vide").getCode();
				
			}
		} 
		
		String[] listEligibleSpecialities=trainingCenterForm.getSpecialities().split(",");
        if(listEligibleSpecialities.length==0) return "NOSPECIALITIES";
        else{
			if(trainingCenterForm.getId() != 0 ){
				
				trainingCenter = trainingCenterService.persistTrainingCenterBean(trainingCenterForm, httpServletRequest);
				persistEligibleSpecialities(trainingCenter ,listEligibleSpecialities );
				
		    }else{
		    	trainingCenter = trainingCenterService.persistTrainingCenterBean(trainingCenterForm, httpServletRequest);
		    	List<Agreement> listOfAgreements = new ArrayList<Agreement>();
				listOfAgreements = agreementService.findByTrainingCenter(trainingCenter);
				persistEligibleSpecialities(trainingCenter , listEligibleSpecialities );
				return  "OKWITHAGREEMENT-"+String.valueOf(listOfAgreements.get(0).getId());
		    }
		
          
        }

	    return  "OKWITHAGREEMENT-"+String.valueOf(trainingCenter.getId());

	}

	/*
	 *@Author MPA
	 *this function helps to load l	
	@Autowired
	private Tracker tracker;
ist of Courses 
	 */
	@Secured({"ROLE_VIEW_TRAINNING_CENTERS","ROLE_MANAGE_TRAINNING_CENTERS"})
	@RequestMapping(value = {"/list/{speciality}/{divisionId}/{statusId}"})
	public String listTrainingCenter(@PathVariable int divisionId,@PathVariable String speciality,@PathVariable int statusId,Model model) {
		ArrayList<TrainingCenter> trainingCenters = null;
		if(speciality.equals("ALL")){
			if(statusId == -1){
				if(divisionId == 0 ){
					trainingCenters = (ArrayList<TrainingCenter>) trainingCenterService.findAll();
				}else{
					Division division = divisionService.findById(divisionId);
                    trainingCenters = (ArrayList<TrainingCenter>) trainingCenterService.findByDivision(division);
					
				}
		     }else{
		    	 if(divisionId == 0 ){
				
				    trainingCenters = (ArrayList<TrainingCenter>) trainingCenterService.findByStatus(statusId);
				}else{
						Division division = divisionService.findById(divisionId);
						trainingCenters = (ArrayList<TrainingCenter>) trainingCenterService.findByDivisionAndStatus(division,statusId);
						
				}
		    	 
		     }
		
		}else{
			Long specialityId = specialityService.findByAbbreviation(speciality).getId();
	        Speciality specialityRetrieved = specialityService.findById(specialityId);
	        if(statusId == -1){
				if(divisionId == 0 ){
					trainingCenters = (ArrayList<TrainingCenter>) trainingCenterService.findTrainingCenterByEligibleSpeciality(specialityRetrieved);
				}else{
					Division division = divisionService.findById(divisionId);
				    trainingCenters = (ArrayList<TrainingCenter>) trainingCenterService.findBySpecialityAndDivision(specialityRetrieved,division);
				}
	        }else{
	        	if(divisionId == 0 ){
					trainingCenters = (ArrayList<TrainingCenter>) trainingCenterService.findBySpecialityAndStatus(specialityRetrieved,statusId);
				}else{
					Division division = divisionService.findById(divisionId);
				    trainingCenters = (ArrayList<TrainingCenter>) trainingCenterService.findBySpecialityAndDivisionAndStatus(specialityRetrieved,division,statusId);
					
				}
	        	
	        }
			
		}

		ArrayList<Speciality> specialities = (ArrayList<Speciality>) specialityService.findAll();	
		ArrayList<Division> divisions = (ArrayList<Division>) divisionService.findAll();
		ArrayList<ExamCenter> examCenters = (ArrayList<ExamCenter>) examCenterService.findAll();
		model.addAttribute("trainingCenters", trainingCenters);
		model.addAttribute("divisions", divisions);
		model.addAttribute("specialities", specialities);
		model.addAttribute("examCenters", examCenters);		
		return "pv/trainingCenter_list";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/delete/{trainingCenterId}")
	@Secured({"ROLE_MANAGE_TRAINNING_CENTERS"})
	public String deleteTrainingCenter(@PathVariable int trainingCenterId, HttpServletRequest httpServletRequest) {
		TrainingCenter trainingCenter = trainingCenterService.findById(trainingCenterId);
		List<Student> listeOfStudentsRelatedToTrainingCenter = studentService.findByTrainingCenter(trainingCenter);
		
		if(listeOfStudentsRelatedToTrainingCenter.size() == 0){
			ArrayList<TrainingCenterTracking> listeOfTrackings= trainingCenterTrackingService.findByTrainingCenter( trainingCenter);
			listeOfTrackings.forEach((tracking)->{trainingCenterTrackingService.deleteTrainingCenterTracking(tracking);});
			List<EligibleSpeciality> listeOfEligibleSpecialities = new ArrayList<EligibleSpeciality>();
			listeOfEligibleSpecialities = eligibleSpecialityService.findByTrainingCenter(trainingCenter);
			listeOfEligibleSpecialities.forEach((eligibleSpeciality)->{eligibleSpecialityService.deleteEligibleSpeciality(eligibleSpeciality);});
			
			
			
			List<Agreement> listeOfAgreements = new ArrayList<Agreement>();
			listeOfAgreements = agreementService.findByTrainingCenter(trainingCenter);
			listeOfAgreements.forEach((agreement)->{
				List<AgreementTracking> listeOfAgreementTrackings = new ArrayList<AgreementTracking>();
				listeOfAgreementTrackings = agreementTrackingService.findByAgreement(agreement);
				listeOfAgreementTrackings.forEach((listeOfAgreementTracking)->{agreementTrackingService.deleteAgreementTracking(listeOfAgreementTracking);
				});
				agreementService.deleteAgreement(agreement);
				});
			trainingCenterService.deleteTrainingCenter(trainingCenter); 
	        logger_.log(Constants.NORMAL_LOG_DIR, "Delete the training center: " + trainingCenter.getName(), httpServletRequest);		
			return "OK";
		}
		return "KO";
	}

	private void persistEligibleSpecialities(TrainingCenter trainingCenter , String[] listEligibleSpecialities ){
     if(eligibleSpecialityService.findByTrainingCenter(trainingCenter).size() <= 0){
      	    for(int i=0;i<listEligibleSpecialities.length;i++) {
	        		Speciality speciality = specialityService.findById(Long.valueOf(listEligibleSpecialities[i]));
	        		EligibleSpeciality eligibleSpeciality = new EligibleSpeciality(trainingCenter ,  speciality);
	        		eligibleSpecialityService.createEligibleSpeciality(eligibleSpeciality);	
              
      		}
      	
      }else{
      	if(eligibleSpecialityService.findByTrainingCenter(trainingCenter).size() > 0){
	        	for(EligibleSpeciality eligibleSpeciality : trainingCenter.getEligibleSpeciality()){
	        		eligibleSpecialityService.deleteEligibleSpeciality(eligibleSpeciality);
	        	}
	        	for(int i=0;i<listEligibleSpecialities.length;i++) {
		        		Speciality speciality = specialityService.findById(Long.valueOf(listEligibleSpecialities[i]));
		        		EligibleSpeciality eligibleSpeciality = new EligibleSpeciality(trainingCenter ,  speciality);
		        		eligibleSpecialityService.createEligibleSpeciality(eligibleSpeciality);	
                
	             }
           }
      	
      }
	}
	
	@RequestMapping(value = "/printStudentsList", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> printPvProfessionalCardPdf(@RequestParam("trainingCenterId") int trainingCenterId, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, DocumentException{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		printerService.printStudentListForTrainingCenter(outStream, headers, trainingCenterId);

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(outStream.toByteArray());
	}
	
	
	
	/*
	 *@uthor MPA
	 *this controller load default list of students for a training center 
	 */ 

	@Secured({"ROLE_VIEW_TRAINNING_CENTERS"})
	@RequestMapping(value = "/list/{trainingCenterId}", method = RequestMethod.GET)
	public String candidatesList(@PathVariable int trainingCenterId,HttpServletRequest httpServletRequest, Model model) {

		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		TrainingCenter trainingCenter = trainingCenterService.findById(trainingCenterId);
		List<Student> listOfStudents = studentService.findByComputerizationDateBetweenAndTrainingCenter(cal.getTime(), today, trainingCenter);
		List<EligibleCenter>  listEligibleCentersOpened = eligibleCenterService.findByEligibleCenterStatus(eligibleCenterStatusService.findEligibleCenterStatusById(2));
		model.addAttribute("listType", "Student");
		model.addAttribute("listOpenEligibleCenters", listEligibleCentersOpened);
		model.addAttribute("listToDisplay", listOfStudents);
		model.addAttribute("trainingCenter", trainingCenter);
		return "pv/training_center_students_list";
	}
	
	
	@Secured({"ROLE_VIEW_TRAINNING_CENTERS"})
	@RequestMapping(value = "/listeSearchCandidate", method = RequestMethod.GET)
	public String listeSearchApplication(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
									 @RequestParam("status") int status,@RequestParam("eligibleCenterId") int eligibleCenterId, @RequestParam("licenseNumber") String licenseNumber,
									 @RequestParam("matricule") String matricule,  @RequestParam("trainingCenterId") int trainingCenterId, HttpServletRequest httpServletRequest, Model model) throws ParseException {
		List<Student> listOfStudents =new ArrayList<>();
		List<StudentSession> listOfStudentSessions =new ArrayList<>();
		java.sql.Date sqlStartDate = null;
		java.sql.Date sqlendDate = null;
		TrainingCenter trainingCenter = trainingCenterService.findById(trainingCenterId);
		List<EligibleSpeciality> trainingCenterSpecialities = eligibleSpecialityService.findByTrainingCenter(trainingCenter);
		if(!(licenseNumber.equals("undefined") && matricule.equals("undefined"))){
			if(!licenseNumber.equals("") || !matricule.equals("") ){
				if(!licenseNumber.equals("") && !licenseNumber.equals("undefined")){
					Person personsFound = personService.findPersonByLicenseNum(licenseNumber);
					if(personsFound != null){
						for(Student student : personsFound.getStudents()){
							if(student.getTrainingCenter().getId() == trainingCenter.getId())
								listOfStudents.add(student);
						}
						
					}
				}
				if(!matricule.equals("") && !matricule.equals("undefined")){
					Student student = studentService.findByReferenceNum(matricule);
					if(student != null && (student.getTrainingCenter().getId() == trainingCenter.getId()))
						listOfStudents.add(student);
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
				    listOfStudents = studentService.findByComputerizationDateBetweenAndTrainingCenter(startDateConverted, endDateConverted,trainingCenter);
				    model.addAttribute("listType", "Student");
					model.addAttribute("listToDisplay", listOfStudents);
				}
				/*else if(status == -1){
				    listOfStudents = studentService.findByComputerizationDateBetweenAndTrainingCenter(startDateConverted, endDateConverted, user.getTrainingCenter());
				    model.addAttribute("listType", "Student");
					model.addAttribute("listToDisplay", listOfStudents);
				}*/

			
		    }else{
				
					if(eligibleCenterId > 0){
						for(EligibleSpeciality elligilbeSpeciality : trainingCenterSpecialities){
							EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
							listOfStudentSessions.addAll(studentSessionService.findByEligibleCenterAndResultAndSpecialityAndTrainingCenter(eligibleCenter, status, elligilbeSpeciality.getSpeciality(),trainingCenter)) ;	
							}
					}else{
						for(EligibleSpeciality elligilbeSpeciality : trainingCenterSpecialities){
							listOfStudentSessions.addAll(studentSessionService.findByResultAndSpecialityAndTrainingCenter(status, elligilbeSpeciality.getSpeciality(), trainingCenter)) ;	
						}
					}
				
				model.addAttribute("listType", "StudentSession");
				model.addAttribute("listToDisplay", listOfStudentSessions);
			}
	
		}
		return "pv/training_center_students_list_search";
	}
	

}