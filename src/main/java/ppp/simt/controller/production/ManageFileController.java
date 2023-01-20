package ppp.simt.controller.production;


import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.itextpdf.text.DocumentException;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Authority;
import ppp.simt.entity.core.Office;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.production.*;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionFile;
import ppp.simt.entity.user.User;
import ppp.simt.form.ApplicationForm;
import ppp.simt.repository.pv.StudentSessionRepository;
import ppp.simt.service.archive.ArchiveService;
import ppp.simt.service.core.AuthorityService;
import ppp.simt.service.core.CountryService;
import ppp.simt.service.core.OfficeService;
import ppp.simt.service.core.PersonService;
import ppp.simt.service.engines.PrinterService;
import ppp.simt.service.engines.StateCheckerService;
import ppp.simt.service.evaluation.TranscriptService;
import ppp.simt.service.production.*;
import ppp.simt.service.pv.StudentSessionFileService;
import ppp.simt.service.pv.StudentSessionService;
import ppp.simt.service.pv.EligibleCenterService;
import ppp.simt.service.pv.ExamCenterService;
import ppp.simt.service.pv.ExamSessionService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/manageFile")
public class ManageFileController {

	@Autowired
	private InSlipService inSlipService;
	
	@Autowired
	private InSlipStatusService inSlipStatusService;

	@Autowired
	private OfficeService officeService;
	
	
	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private ProcessTypeService processTypeService;

	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private StudentSessionService studentSessionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired
	private StateCheckerService stateCheckerService;
	
	@Autowired
	private PrinterService printerService;
	
	@Autowired

	private PersonService personService;
	
	@Autowired
	private CapacityService capacityService;
	
	@Autowired
	private PrintReportService printReportService;
	
	@Autowired
	private ArchiveService archiveService;
    
	@Autowired
	private ExamSessionService examSessionService;
	
	@Autowired
	private EligibleCenterService eligibleCenterService;
	
	@Autowired
	private ExamCenterService examCenterService;
	
	@Autowired
	private CertificateService certificateService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private StudentSessionFileService studentSessionFileService;
	
	@Autowired
	private TranscriptService transcriptService;
	
	@Autowired
	private ProfessionalCardService professionalCardService;
	

	
	@Autowired
    ServletContext context;
	
	@Autowired
	private StudentSessionRepository studentSessionRepository;

	@Autowired
	private ApplicationTranscriptService applicationTranscriptService;

	
	@Value("${capacity.preview.folder}")
	private String capacityImageFolder;
	
    @Value("${professional.card.preview.folder}")
   	private String professionalCardPreviewFolder;
    
    @Value("${professional.card.folder}")
   	private String professionalCardFolder;


	@Secured({"ROLE_PROCESS_FILE","ROLE_VIEW_FILE"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String inSlipList(HttpServletRequest httpServletRequest, Model model) {
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		User user = userService.getLogedUser(httpServletRequest);
		List<Application> listApplication = applicationService.findByComputerizationDateBetweenAndOffice(cal.getTime(), today, user.getOffice());


		Office myOffice = officeService.findOfficeById(user.getOffice().getId());
		List<Office> listOffices = officeService.findAll();

		List<ApplicationStatus> listApplacationStatus = applicationStatusService.findAll();
		List<ProcessType> listProcessType = processTypeService.findAll();
		model.addAttribute("listApplication", listApplication);
		model.addAttribute("listProcessType", listProcessType);
		model.addAttribute("listApplacationStatus", listApplacationStatus);
		model.addAttribute("listOffices", listOffices);
		model.addAttribute("myOffice", myOffice);
		return "production/manage_files";
	}
	
	@Secured({"ROLE_PROCESS_FILE"})
	@RequestMapping(value = "/process/record/Getform/{personId}/{processTypeId}/{sourceId}/{sourceEntity}", method = RequestMethod.GET)
	public String processRecordGetForm(HttpServletRequest httpServletRequest, Model model , @PathVariable("personId") int personId , @PathVariable("processTypeId") int processTypeId , @PathVariable("sourceId") int sourceId , @PathVariable("sourceEntity") String sourceEntity ) {
		Person applicantPerson = personService.findPersonById(personId);
		User user = userService.getLogedUser(httpServletRequest);
		 if(applicantPerson != null)
			 model.addAttribute("person", applicantPerson);
		
		if(sourceEntity.equals("StudentSession")){	
			StudentSession applicantStudentSession	= studentSessionService.findById(sourceId);

			 
			 if(applicantStudentSession != null){
				 model.addAttribute("studentSession", applicantStudentSession);
				 Set<StudentSessionFile> applicantStudentSessionFiles = studentSessionFileService.findByStudentSessionAndDeleted(applicantStudentSession, 0);
				 model.addAttribute("studentSessionFiles", applicantStudentSessionFiles);
			 }
		 
		}else if(sourceEntity.equals("Archive")){
			Archive applicantArchive	= archiveService.findArchiveById(sourceId);
			model.addAttribute("archive", applicantArchive);
			//Capacity applicantCapacity = capacityService.findByPerson(applicantArchive.getPerson());
	        //model.addAttribute("capacity", applicantCapacity);

	   }else if(sourceEntity.equals("Certificate")){
		    Certificate applicantCertificate	= certificateService.findById(sourceId);
	        model.addAttribute("certificate", applicantCertificate);
	        model.addAttribute("studentSessionFiles", applicantCertificate.getStudentSession().getStudentSessionFiles());

	   }else if(sourceEntity.equals("Transcript")){
		   
		    ApplicationTranscript applicantTranscript	= applicationTranscriptService.findById(sourceId);
	        model.addAttribute("transcript", applicantTranscript);
	        model.addAttribute("studentSessionFiles", applicantTranscript.getStudentSession().getStudentSessionFiles());

	   }else if(sourceEntity.equals("ProfessionalCard")){
		    ProfessionalCard professionalCard	= professionalCardService.findById(sourceId);
	        model.addAttribute("professionalCard", professionalCard);
	        if(professionalCard.getCertificate() != null)
	        	model.addAttribute("studentSessionFiles", professionalCard.getCertificate().getStudentSession().getStudentSessionFiles());

	   }else if(sourceEntity.equals("Archive")){
		    Archive archive	= archiveService.findArchiveById(sourceId);
	        model.addAttribute("archive", archive);

	   }   
		
		//send register application button 's datas
		if(processTypeId != -1)
			model.addAttribute("processTypeId",processTypeId);
		if(sourceId != -1)
			model.addAttribute("sourceId",sourceId);
		if(sourceEntity != null)
			model.addAttribute("sourceEntity",sourceEntity);
		
		inSlipService.manageOfficeInSlipClosing(user.getOffice());//service to check if an inslip should be closed
		InSlipStatus inslipStatus = inSlipStatusService.findById(2);
		
		List<InSlip> inSlipsOpened = inSlipService.findByStatusAndOffice(inslipStatus, user.getOffice());
		ProcessType processType = processTypeService.findProcessTypeById(processTypeId);
		if(processType != null)
			model.addAttribute("processType",processType);
		model.addAttribute("inSlips", inSlipsOpened);
		model.addAttribute("countries", countryService.findAll());
		model.addAttribute("authorities", authorityService.findAll());
	
		return "production/processingForm";
	}

	@Secured({"ROLE_PROCESS_FILE"})
	@RequestMapping(value = "/listeSearchApplication", method = RequestMethod.GET)
	public String listeSearchApplication(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
									 @RequestParam("status") int status,@RequestParam("processType") int processType, @RequestParam("office") int office,
									 @RequestParam("inSlipReference") String inSlipReference,@RequestParam("fileNumber") String fileNumber, HttpServletRequest httpServletRequest, Model model) throws ParseException {
		List<Application> listApplication =new ArrayList<>();
		java.sql.Date sqlStartDate = null;
		java.sql.Date sqlendDate = null;

		if(!fileNumber.equals("")){
			listApplication = applicationService.findByInSlipAndDateAndStatusAndOfficeAndProcessTypeAndFileNumber(sqlStartDate, sqlendDate, status, office,inSlipReference, processType, fileNumber);
		}else if(!startDate.isEmpty()){
			java.util.Date startDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
			java.util.Date endDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
			sqlStartDate = new java.sql.Date(startDateConverted.getTime());
			sqlendDate = new java.sql.Date(endDateConverted.getTime());
		}
		listApplication = applicationService.findByInSlipAndDateAndStatusAndOfficeAndProcessTypeAndFileNumber(sqlStartDate, sqlendDate, status, office,inSlipReference, processType, fileNumber);


		model.addAttribute("listApplication", listApplication);
		return "production/application_list_search";
	}
	

	@RequestMapping(value="/manage/{action}/{id}", method = RequestMethod.GET) 
	public String manageApplication(@PathVariable(value="action") String action ,@PathVariable(value="id") int id , HttpServletRequest httpServletRequest, Model model) {
		User user = userService.getLogedUser(httpServletRequest);
		Application application = applicationService.findAppById(id);
		String state = stateCheckerService.stateEmbedded(application, user, action);
		String message ="";
		if(state == "ok"){
			int newStatus = getStatus(action);

			switch(action){
				case "reject":
					//setNewStatus(application, newStatus);
					//message = "REJECTED THE APPLICATION";
					model.addAttribute("action", action);
					model.addAttribute("application", application);
					return "production/add_comment";
					//return view to receive message for reject
				case "confirm_reject":
					setNewStatus(application, newStatus);
					message = "CONFIRMED THE APPLICATION IS A REJECT";
					tracker.track(application, message, httpServletRequest);
					break;
				case "cancel":
					setNewStatus(application, newStatus);
					message = "CANCELLED PROCESSING ON THE APPLICATION";
					tracker.track(application, message, httpServletRequest);
					break;
				case "confirm":
					setNewStatus(application, newStatus);
					message = "CONFIRMED THE APPLICATION";
					tracker.track(application, message, httpServletRequest);
					break;
				case "reset":
					setNewStatus(application, newStatus);
					message = "RESETTED THE APPLICATION";
					tracker.track(application, message, httpServletRequest);
					break;
				case "print_successful":
					setNewStatus(application, newStatus);
					//application.setSuccessfulyRejectedDate(new java.util.Date());
					applicationService.save(application);
					message = "MARKED THE APPLICATION AS SUCESSFULLY PRINTED";
					tracker.track(application, message, httpServletRequest);
					break;
				case "print_unsuccessful":
					setNewStatus(application, newStatus);
					message = "MARKED THE APPLICATION AS UNSUCCESSFULLY PRINTED";
					tracker.track(application, message, httpServletRequest);
					break;
				case "deliver_with_reject":
					setNewStatus(application, newStatus);
					
//					Capacity capacity = application.getCapacity();
//					capacity.setOnProcess(0);
					setNewStatus(application, newStatus);
					application.setSuccessfulyRejectedDate(new java.util.Date());
					applicationService.save(application);
					message = "MARKED AS DELIVER WITH REJECT";
					tracker.track(application, message, httpServletRequest);
					break;
				case "request_reprint":
					model.addAttribute("action", action);
					model.addAttribute("application", application);
					return "production/add_comment";
				case "edit":
					Person person = null;
					if (application.getArchive()!=null) {
						 person = application.getArchive().getPerson();
						
					}
					if (application.getCertificate()!=null) {
						 person = application.getCertificate().getStudentSession().getStudent().getPerson();
						
					}
					if (application.getTranscript()!=null) {
						 person = application.getTranscript().getStudentSession().getStudent().getPerson();
						
					}
					model.addAttribute("action", action);
					model.addAttribute("application", application);
					model.addAttribute("countries", countryService.findAll());
					model.addAttribute("authorities", authorityService.findAll());
					model.addAttribute("person", person);
					return "production/editProcessingForm";	
				case "grant_reprint":
					setNewStatus(application, newStatus);
					message = "GRANTED REPRINT REQUEST";
					tracker.track(application, message, httpServletRequest);
					break;
				case "print_report":
					model.addAttribute("action", action);
					model.addAttribute("application", application);
					return "production/print_report";
					
					
				default:
					return "invalid action";
			}
			
			model.addAttribute("proceed",  "ok");	
		}else if(state=="koBadRole"){
			model.addAttribute("proceed",  "koBadRole");
			//return "koBadRole";
			
		}else if(state =="koBadState"){
			model.addAttribute("proceed",  "koBadState");
			//return "koBadState";
		}
		return "production/change";
	}
	
	public int getStatus(String action) {
		int newStatus;
		switch(action){
		case "reject":
			newStatus=2;
			break;
		case "confirm_reject":
			newStatus=7;
			break;
		case "cancel":
			newStatus=3;
			break;
		case "confirm":
			newStatus=4;
			break;
		case "reset":
			newStatus=1;
			break;
		case "deliver_with_reject":
			newStatus=10;
			break;
		case "request_reprint":
			newStatus=11;
			break;
		case "grant_reprint":
			newStatus=4;
			break;
		case "print_successful":
			newStatus=6;
			break;
		case "print_unsuccessful":
			newStatus=8;
			break;
		default:
			newStatus=0;
			break;
		}
		return newStatus;
	}
	
	public void setNewStatus(Application application, int newStatus) {
		ApplicationStatus applicationStatus = applicationStatusService.findApplicationStatusById(newStatus);
		application.setApplicationStatus(applicationStatus);
		applicationService.save(application);
	}
	
	@Secured({"ROLE_PRINT_APPLICATION"})
	@RequestMapping(value = "/capacity/preview", method = RequestMethod.GET)
	
	public String capacityPreview(@RequestParam("id") int id,Model model,HttpServletRequest httpServletRequest, HttpServletResponse response) 
			throws Exception{
		Application application = applicationService.findAppById(id);
		int applicationStatus = application.getApplicationStatus().getId();
		//String filename = capacityImageFolder+"marine_capacity.pdf";
		String extension = "jpg";
		
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		printerService.capacityPreview(outStream, headers, id, applicationStatus, httpServletRequest,"preview");
		String filename=capacityImageFolder+"marine_capacity.pdf";
        FileOutputStream output = new FileOutputStream(filename);
        output.write(outStream.toByteArray());
		printerService.generateImageFromPDF(filename, extension,id);
		    
			String fileName1 = capacityImageFolder+"capacity_marine_"+id+"-"+1+"."+extension;
			String fileName2 = capacityImageFolder+"capacity_marine_"+id+"-"+2+"."+extension;
			BufferedImage img1 = ImageIO.read(new File(fileName1));
			BufferedImage img2 = ImageIO.read(new File(fileName2));
	    	int offset = 2;
            int width = img1.getWidth() + img2.getWidth() + offset;
            int height = Math.max(img1.getHeight(), img2.getHeight()) + offset;
            BufferedImage newImage = new BufferedImage(2024, 638,BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = newImage.createGraphics();
            Color oldColor = g2.getColor();
            g2.setPaint(Color.BLACK);
            g2.fillRect(0, 0, width, height);
            g2.setColor(oldColor);
            g2.drawImage(img1, null, 0, 0);
            g2.drawImage(img2, null, img1.getWidth() + offset, 0);
            g2.dispose();
            String formatName = String.format(capacityImageFolder+"merge_capacity_"+id+"."+extension);
            ImageIOUtil.writeImage(newImage, String.format(capacityImageFolder+"merge_capacity_"+id+"."+extension), 300);
	        //ImageIOUtil.writeImage(newImage, formatName, 300);
	        
	        model.addAttribute("id", id);
			return "production/capacity_preview";
		
	}
	
	
	@Secured({"ROLE_PRINT_APPLICATION"})
	@RequestMapping(value = "/capacity/print", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> capacityPrint(@RequestParam("id") int id,Model model,HttpServletRequest httpServletRequest, HttpServletResponse response) 
			throws IOException, DocumentException{
		Application application = applicationService.findAppById(id);
		int applicationStatus = application.getApplicationStatus().getId();
		String capacityNumber = capacityService.generateCapacityNum(id);

		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		printerService.capacityPreview(outStream, headers, id, applicationStatus, httpServletRequest,"print");
		setNewStatus(application, 5);
		File directory = new File(capacityImageFolder);
		//FileUtils.cleanDirectory(directory);

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(outStream.toByteArray());
	}
	
	
	/*
	 *@uthor MPA
	 *this controller helps to check if the in slip ref entered by the user exists , if the application number is unused and if the form serial number entered is not already used
	 */
	
	
	@ResponseBody
	@Secured({"ROLE_PROCESS_FILE"})
	@RequestMapping(value = "/processForm/checkApplication", method = RequestMethod.POST)
	public String processCheckApplicationForm(HttpServletRequest httpServletRequest, Model model,@RequestParam("inslipRefEntered") String inslipRefEntered,@RequestParam("appNumEntered") int appNumEntered,@RequestParam("formSerialNumberEntered") String formSerialNumberEntered,@RequestParam("appId") int appId) {
		InSlip inSlipFound = null;
		inSlipFound = inSlipService.findByReferenceNumber(inslipRefEntered);
		if(inSlipFound == null )
			return "inslip.not.found";
		else{
			 User user = userService.getLogedUser(httpServletRequest);
			if(inSlipFound.getOffice().getId() != user.getOffice().getId())
				return "you.can.not.process.in.slips.of.office@"+inSlipFound.getOffice().getName();
			List<Integer> usedAppNums = new ArrayList<Integer>();
			List<Integer> unUsedAppNums = new ArrayList<Integer>();
			InSlip inSlip = inSlipService.findInSlipById(inSlipFound.getId());
			int inSlipSize  = inSlip.getTotal() ;
		    List<Application> apps = applicationService.findAppByInSlipId(inSlipFound.getId());
		    for(int i=0; i<apps.size(); i++) 
		    { 
		        usedAppNums.add(apps.get(i).getNumber());
		    }
		    for(int i=1; i<=inSlipSize; i++) 
		    { 
		        if(usedAppNums.contains(i)==false) {
	
		        	unUsedAppNums.add(i);
		        }
		    }
		    
		    if( usedAppNums.contains(appNumEntered) == true )
		    	return "inslip.application.number.already.used";
		    else{
		    	if(unUsedAppNums.contains(appNumEntered) == false )
		    		return "application.number.not.include.for.this.in.slip";

		    }
			//we verify the form serial number appId
			/*if( appId != 0 ){
				//get the application and verify if it is at the status of rejected
				Application application = applicationService.findAppById(appId);
				if(application.getApplicationStatus().getDescription().equals("DELIVERED WITH REJECT")){
					//we verify if the application retuns is the same
					//if(application.getSerialNumber().equals(formSerialNumberEntered))
					//	return "ok";
					//else
					//	return "please.reentered.old.serial.number";
				}
			}else{
				List<Application> appsFound = applicationService.findByFormSerialNumber(formSerialNumberEntered);
				if(appsFound.size() > 0 )
					return "form.serial.number.already.used";
			}
*/
		}
		return "ok";
	}

    @RequestMapping(value="/save", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    @Secured({"ROLE_PROCESS_FILE"})
    public String saveApplication(@ModelAttribute("applicationForm") @Validated ApplicationForm applicationForm, BindingResult result , HttpServletRequest httpServletRequest, Model model) throws ParseException{
        User user = userService.getLogedUser(httpServletRequest);
       
        //Application application = applicationService.persistApplicationBean(applicationForm, user);
        Application application = applicationService.persistApplicationBean(applicationForm, user,httpServletRequest);
        InSlip inSlipEntered = inSlipService.findByReferenceNumber(applicationForm.getInslipReference());
		if(inSlipEntered.getApplications().size() == inSlipEntered.getTotal())
			inSlipEntered.setInSlipStatus(inSlipStatusService.findById(3));
        //call tracking and log services
        if(application.getId()==0){
            //case archive creation
            logger_.log(Constants.NORMAL_LOG_DIR, "APPLICATION CREATED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"APPLICATION ID IS "+application.getId(),httpServletRequest);
        }else{
            //case archive edition
            logger_.log(Constants.NORMAL_LOG_DIR, "APPLICATION EDITED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"APPLICATION ID IS "+application.getId(),httpServletRequest);
        }
        return String.valueOf(application.getId());
    }
    
    
    @RequestMapping(value="/manage/save/{action}/{id}/{message}", method = RequestMethod.GET) 
    public String updateStatusWithComments(@PathVariable(value="action") String action ,@PathVariable(value="id") int id,@PathVariable(value="message") String message, HttpServletRequest httpServletRequest, Model model) {
    	User user = userService.getLogedUser(httpServletRequest);
    	Application application = applicationService.findAppById(id);
    	String state = stateCheckerService.stateEmbedded(application, user, action);
    	if(state== "ok") {
    		int newStatus = getStatus(action);
        	setNewStatus(application, newStatus);
        	applicationService.save(application);
        	tracker.track(application, action.toUpperCase() + ":" + message  , httpServletRequest);
        	model.addAttribute("proceed",  "ok");
        	
    	}else if(state=="koBadRole"){			
			model.addAttribute("proceed",  "koBadRole");			
		}else if(state =="koBadState"){
			model.addAttribute("proceed",  "koBadState");
		}	
    	return "production/change";
    }

    @RequestMapping(value="/manage/print/report/{action}/{id}", method = RequestMethod.GET) 
    public String printReport(@PathVariable(value="action") String action ,@PathVariable(value="id") int id,@RequestParam(value="cardNumber") String cardNumber, HttpServletRequest httpServletRequest, Model model) {
    	User user = userService.getLogedUser(httpServletRequest);
    	Application application = applicationService.findAppById(id);
    	PrintReport printReport = new PrintReport();
    	String message = "";
    	int newStatus = getStatus(action);
    	setNewStatus(application, newStatus);

    	java.util.Date printDate = new java.util.Date();
    	if(action.equals("print_successful")) {

			if(application.getProcessType().getAbbreviation().equals("NT") || application.getProcessType().getAbbreviation().equals("DT")){
				ApplicationTranscript applicationTranscript = applicationTranscriptService.findById(application.getTranscript().getId());
				applicationTranscript.setOnProcess(0);
				applicationTranscript.setIsPrinted(1);
				applicationTranscript.setMatricule(printerService.generateTranscriptNumber(application.getSpeciality().getAbbreviation(), applicationTranscript.getId()));
				applicationTranscriptService.saveTranscript(applicationTranscript);

			}
			if(application.getProcessType().getAbbreviation().equals("NC") || application.getProcessType().getAbbreviation().equals("DC") ){
				Certificate	certificate = certificateService.findById(application.getCertificate().getId());
				if(certificate.getStudentSession().getEligibleCenter().getJuryNumber() != null)
					certificate.setJuryNumber(certificate.getStudentSession().getEligibleCenter().getJuryNumber());
				certificate.setOnProcess(0);
				certificate.setIsPrinted(1);
				certificateService.save(certificate);

			}
			if(application.getProcessType().getAbbreviation().equals("DP") || application.getProcessType().getAbbreviation().equals("NP")){
				ProfessionalCard professionalCard = professionalCardService.findById(application.getCertificate().getProfessionalCard().getId());
				professionalCard.setOnProcess(0);
				professionalCard.setIsPrinted(1);
				professionalCardService.save(professionalCard);
			}
    		printReport.setApplication(application);
    		printReport.setPrintDate(printDate);
    		printReport.setCardNumber(cardNumber);
    		printReport.setStatus("successful");
			application.setSuccessfulyRejectedDate(new java.util.Date());
    		printReportService.save(printReport);
        	message = "APPLICATION SUCCESSFULLY PRINTED";
    		model.addAttribute("proceed",  "ok"); 	
    	}else{
    		printReport.setApplication(application);
    		printReport.setPrintDate(printDate);
    		printReport.setCardNumber(cardNumber);
    		printReport.setStatus("unsuccessful");
    		printReportService.save(printReport);
    		message = "APPLICATION UNSUCCESSFULLY PRINTED";
    		model.addAttribute("proceed",  "ok");
    	}
		applicationService.save(application);
    	tracker.track(application, message  , httpServletRequest);
    	return "production/change";
    }
    
    @RequestMapping(value="/detail/{id}", method = RequestMethod.GET)
    public String showDetails(@PathVariable(value="id") int id, HttpServletRequest httpServletRequest, Model model){
    	Application application = applicationService.findAppById(id);
    	Person person = null;
    	int source = 0;
		if(application.getCertificate() != null) {
			if (application.getCertificate().getStudentSession() != null) {
				person = application.getCertificate().getStudentSession().getStudent().getPerson();
			}
		}
		else if (application.getTranscript()!=null) {
			person = application.getTranscript().getStudentSession().getStudent().getPerson();
			
		}
		else{
    		person = application.getArchive().getPerson();
    		source = 1;
    	}
    	model.addAttribute("application", application);
    	model.addAttribute("person", person);
    	model.addAttribute("source", source);
    	return "production/application_detail";
    }
    
    @RequestMapping(value="/detail/confirm/{id}", method = RequestMethod.GET)
    public String showConfirmDetails(@PathVariable(value="id") int id, HttpServletRequest httpServletRequest, Model model)
    {
    	Application application = applicationService.findAppById(id);
    	List<Authority> authorities = authorityService.findAll();
		Person person = null;
    	int source = 0;
		if(application.getCertificate() != null) {
			if (application.getCertificate().getStudentSession() != null) {
				person = application.getCertificate().getStudentSession().getStudent().getPerson();
			}
		}
		else if (application.getTranscript()!=null) {
			person = application.getTranscript().getStudentSession().getStudent().getPerson();

		}
		else{
			person = application.getArchive().getPerson();
			source = 1;
		}
		model.addAttribute("application", application);
		model.addAttribute("person", person);
		model.addAttribute("source", source);
		model.addAttribute("authorities", authorities);
    	return "production/confirm_application";
    }
    
    
    @RequestMapping(value="/editApplication/{applicationId}", method=RequestMethod.POST)
    @ResponseBody
    @Transactional
    @Secured({"ROLE_PROCESS_FILE"})
    public String editApplication(@ModelAttribute("applicationForm") @Validated ApplicationForm applicationForm, BindingResult result,@PathVariable(value="applicationId") int id, Model model, HttpServletRequest httpServletRequest) throws ParseException{
        User user = userService.getLogedUser(httpServletRequest);
        Application application = applicationService.findAppById(id);
        Authority authority = authorityService.findAuthorityById(applicationForm.getAuthority());
        Person person;
        
    	if (application.getTranscript()!=null){
			person = application.getTranscript().getStudentSession().getStudent().getPerson();
		}else if(application.getArchive()!=null) {
			person=application.getArchive().getPerson();
		}else {
			person = application.getCertificate().getStudentSession().getStudent().getPerson();
		}
     
    	person.setPhoneNumber(applicationForm.getApplicantPhoneNumber());
    	personService.updatePerson(person);

        application.setAuthority(authority);
        application.setFormSerialNumber(applicationForm.getFormSerialNumber());
        application.setNumber(applicationForm.getApplicationNumber());
       // application.setInSlip(inSlip);
        applicationService.updateApplication(application);
        
        //call tracking and log services
       
            logger_.log(Constants.NORMAL_LOG_DIR, "APPLICATION EDITED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"APPLICATION ID IS "+application.getId(),httpServletRequest);
        
        return "done";
    }
    
	@RequestMapping(value = "/productionPrintPreviewProfessionalCard", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public String productionPrintPreviewProfessionalCardPdf(@RequestParam("applicationId") int applicationId, @RequestParam("token") String token, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws Exception{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		Application application = applicationService.findAppById(applicationId);
		int studentSessionId = application.getCertificate().getStudentSession().getId();
//		application.setApplicationStatus(applicationStatusService.findApplicationStatusById(5));
//		applicationService.save(application);
		String extension = "jpg";
		printerService.printProfessionalCard(outStream, headers, application, token);
		String filename=professionalCardFolder+"professional_card_"+studentSessionId+".pdf";
		FileOutputStream output = new FileOutputStream(filename);
		output.write(outStream.toByteArray());
		printerService.generateImageFromProfessionalCardPDF(filename, extension,studentSessionId);

		String fileName1 = professionalCardPreviewFolder+"professional_card_"+studentSessionId+"-"+1+"."+extension;
		String fileName2 = professionalCardPreviewFolder+"professional_card_"+studentSessionId+"-"+2+"."+extension;
		BufferedImage img1 = ImageIO.read(new File(fileName1));
		BufferedImage img2 = ImageIO.read(new File(fileName2));
		int offset = 2;
		int width = img1.getWidth() + img2.getWidth() + offset;
		int height = Math.max(img1.getHeight(), img2.getHeight()) + offset;
		BufferedImage newImage = new BufferedImage(2024, 638,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();
		g2.setPaint(Color.BLACK);
		g2.fillRect(0, 0, width, height);
		g2.setColor(oldColor);
		g2.drawImage(img1, null, 0, 0);
		g2.drawImage(img2, null, img1.getWidth() + offset, 0);
		g2.dispose();
		String formatName = String.format(professionalCardPreviewFolder+"merge_professional_card_"+studentSessionId+"."+extension);
		ImageIOUtil.writeImage(newImage, String.format(professionalCardPreviewFolder+"merge_professional_card_"+studentSessionId+"."+extension), 300);
		//ImageIOUtil.writeImage(newImage, formatName, 300);

		model.addAttribute("studentSessionId", studentSessionId);
		model.addAttribute("application", application);

		return "production/production_professional_card_preview";
	}
    
	@RequestMapping(value = "/printPvProfessionalCard", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> printPvProfessionalCardPdf(@RequestParam("applicationId") int applicationId,@RequestParam("token") String token, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, DocumentException, ParseException{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		Application application = applicationService.findAppById(applicationId);
		StudentSession studentSession = application.getCertificate().getStudentSession();
		Date date = new Date();
		//ApplicationTranscript applicationTranscript = applicationTranscriptService.findById(studentSession.getApplicationTranscript().getId());
		ProfessionalCard professionalCard = studentSession.getCertificate().getProfessionalCard();
		professionalCard.setMatricule(printerService.generateProfessionalCardNumber(studentSession.getStudent().getSpeciality().getAbbreviation(), studentSession.getCertificate().getId()));
		professionalCard.setIssueDate(date);
		professionalCardService.save(professionalCard);

		printerService.printProfessionalCard(outStream, headers, application, token);
		Speciality speciality = studentSession.getSpeciality(); 
		if(speciality == null)
			speciality = studentSession.getStudent().getSpeciality();
		application.setSpeciality(speciality);
		application.setApplicationStatus(applicationStatusService.findApplicationStatusById(5));
		applicationService.save(application);
		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(outStream.toByteArray());
	}

	
	@Secured({"ROLE_PROCESS_FILE","ROLE_VIEW_FILE"})
	@RequestMapping(value = "/listOneApplication", method = RequestMethod.GET)
	public String listOneApplication(@RequestParam("applicationId") int appId,HttpServletRequest httpServletRequest, Model model) {
		
		Application application = applicationService.findAppById(appId);
		model.addAttribute("application", application);
		return "production/list_one_application";
	}
    
}

