
/**
* Cette classe permet de manipuler les pv (eligibles centers)
 */

package ppp.simt.controller.pv;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.DocumentException;

import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionFile;
import ppp.simt.entity.core.Authority;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.ApplicationFile;
import ppp.simt.entity.production.ApplicationStatus;
import ppp.simt.entity.production.ApplicationTranscript;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.production.InSlip;
import ppp.simt.entity.production.InSlipStatus;
import ppp.simt.entity.production.ProcessType;
import ppp.simt.entity.production.ProfessionalCard;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.User;
import ppp.simt.service.core.AuthorityService;
import ppp.simt.service.core.CategoryService;
import ppp.simt.service.core.RegionService;
import ppp.simt.service.engines.PrinterService;
import ppp.simt.service.engines.StateCheckerService;
import ppp.simt.service.production.ApplicationFileService;
import ppp.simt.service.production.ApplicationService;
import ppp.simt.service.production.ApplicationStatusService;
import ppp.simt.service.production.ApplicationTranscriptService;
import ppp.simt.service.production.CertificateService;
import ppp.simt.service.production.InSlipService;
import ppp.simt.service.production.InSlipStatusService;
import ppp.simt.service.production.ProcessTypeService;
import ppp.simt.service.production.ProfessionalCardService;
import ppp.simt.service.pv.StudentSessionService;
import ppp.simt.service.pv.EligibleCenterService;
import ppp.simt.service.pv.ExamCenterService;
import ppp.simt.service.pv.ExamSessionService;
import ppp.simt.service.user.GroupService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Controller
@RequestMapping("/pv")
public class ManagePvController {
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
	private EligibleCenterService eligibleCenterService;
	
	@Autowired
	private RegionService regionService;
	
	@Autowired
	private ExamSessionService examSessionService;
	
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private PrinterService printerService;
	

	@Autowired
	private ExamCenterService examCenterService;
	
	@Autowired
	private StudentSessionService studentSessionService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private InSlipService inSlipService;
	
	@Autowired
	private InSlipStatusService inSlipStatusService;
	
	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private ProcessTypeService processTypeService;
	
	@Autowired 
	private Tracker tracker;
	
	@Autowired 
	private CertificateService certificateService;
	
	@Autowired 
	private ProfessionalCardService professionalCardService;
	
	@Autowired
	private ApplicationTranscriptService applicationTranscriptService;
	
	
	@Autowired
	private ApplicationFileService applicationFileService;

	@Autowired
	private GroupService groupService;


	@Value("${certificate.background.folder}")
   	private String certificateBackGroundFolder;
    
    @Value("${certificate.preview.folder}")
    private String certificateFilesFolder;
	
	@Value("${transcript.preview.folder}")
	private String transcriptPreviewFolder;
	
    @Value("${professional.card.preview.folder}")
   	private String professionalCardPreviewFolder;
	
    @Value("${professional.card.folder}")
   	private String professionalCardFolder;
    
    @Value("${studentSession.file.folder}")
   	private String studentSessionFileFolder;
    
    @Value("${studentSession.photo.folder}")
   	private String studentSessionPhotoFolder;
    
    @Value("${studentSession.signature.folder}")
   	private String studentSessionSignatureFolder;
    
    @Value("${application.file.folder}")
   	private String applicationFileFolder;
    
    @Value("${application.photo.folder}")
   	private String applicationPhotoFolder;
    
    @Value("${application.signature.folder}")
   	private String applicationSignatureFolder;
	
	/*
	 *@uthor MPA
	 *this function helps to manage all pv Statuses
	 */
	
	 
	@ResponseBody
	@RequestMapping(value="/update/{action}/{id}", method=RequestMethod.GET)
	public String managePvStates(@PathVariable(value="action") String action ,@PathVariable(value="id") String id ,HttpServletRequest httpServletRequest, Model model){
		User user = userService.getLogedUser(httpServletRequest);
		EligibleCenter eligibleCenter = null;

		if(action.equals("reset")){
	        //instructions to reset Pv
			eligibleCenter = eligibleCenterService.findEligibleCenterById(Integer.parseInt(id));
			if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"reset").equals("ok")){
				resetpvTrackAndLog( eligibleCenter , user,httpServletRequest );
				
				tracker.track(eligibleCenter, "PV WAS RESETTED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
				logger_.log(Constants.NORMAL_LOG_DIR, "Created the pv: "+user);
				
				return "ok";
	        }else if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"reset").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
		}else if(action.equals("close")){
			eligibleCenter = eligibleCenterService.findEligibleCenterById(Integer.parseInt(id));
			if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"close").equals("ok")){
			closepvTrackAndLog( eligibleCenter , user,httpServletRequest );
			
			tracker.track(eligibleCenter, "PV WAS CLOSED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Created the pv: "+user);
			
	        return "ok";
	        }else if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"close").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
			
		}else if(action.equals("validate")){
			String mots[] = id.split("@");

			eligibleCenter = eligibleCenterService.findEligibleCenterById(Integer.parseInt(mots[0]));
			if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"validate").equals("ok")){
			vaidatepvTrackAndLog( eligibleCenter , user,httpServletRequest );
			eligibleCenter.setJuryNumber(mots[1]);
			eligibleCenterService.updateEligibleCenter(eligibleCenter);

			tracker.track(eligibleCenter, "PV WAS VALIDATED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Created the pv: "+user);
			
	        return "ok";
	        }else if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"validate").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
			
		}else if(action.equals("open")){
			eligibleCenter = eligibleCenterService.findEligibleCenterById(Integer.parseInt(id));
			if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"open").equals("ok")){
			openpvTrackAndLog( eligibleCenter , user,httpServletRequest );
			
			tracker.track(eligibleCenter, "PV WAS OPEN BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Created the pv: "+user);
			
	        return "ok";
	        }else if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"open").equals("koBadRole")){
				return "koBadRole";
	        }else{
			    return "koBadState";
		    }
			
		}else if(action.equals("generate")){
			eligibleCenter = eligibleCenterService.findEligibleCenterById(Integer.parseInt(id));
			System.out.println(eligibleCenter.getExamCenter());
			if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"generate").equals("ok")){
			 boolean isPvGenerable = checkPVGenerable(eligibleCenter);
			 if (isPvGenerable == true) {
				 System.out.println("howeee");
				 List<StudentSession> students = studentSessionService.findByEligibleCenterAndResultOrderedByPerson(eligibleCenter, 4);
				 for (int i=0; i<students.size(); i++){
					 StudentSession studentSession = students.get(i);
					 studentSession.setPvNumber(i + 1);
					 studentSessionService.updateStudentSession(studentSession);
					 
				 }
				 generatePvTrackAndLog( eligibleCenter , user,httpServletRequest );
					
					tracker.track(eligibleCenter, "PV GENERATED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
					logger_.log(Constants.NORMAL_LOG_DIR, "Generated the PV: "+user);
					
			        return "ok"; 
			 }else{
				 return "koNotReady";
			 }
			
	        }else if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"generate").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
			
		}else if(action.equals("reset_pv")){
			eligibleCenter = eligibleCenterService.findEligibleCenterById(Integer.parseInt(id));
			if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"reset_pv").equals("ok")){
			resetPvTrackAndLog( eligibleCenter , user,httpServletRequest );
			List<StudentSession> students = studentSessionService.findByEligibleCenterAndResultOrderedByPerson(eligibleCenter, 4);
			 for (int i=0; i<students.size(); i++){

				 StudentSession studentSession = students.get(i);
				 studentSession.setPvNumber(0);
				 studentSessionService.updateStudentSession(studentSession);
				
			 }
			tracker.track(eligibleCenter, "PV WAS RESETTED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Resetted the pv: "+user);
			
	        return "ok";
	        }else if(stateCheckerService.stateEmbedded((EligibleCenter)eligibleCenter, user,"reset_pv").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
		
			
		}
		/*emms
		else if (action.equals("created")){ 
			tracker.track(eligibleCenter, "PV WAS CREATED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Resetted the pv: "+user);
			
		}*/

		return "ok";
	}
	
	private void resetPvTrackAndLog(EligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		eligibleCenterService.resetGeneratedPV(eligibleCenter, user);
		tracker.track(eligibleCenter, "GENERATED PV", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "PV RESETTED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ELIGIBLE CENTER ID IS "+eligibleCenter.getId(),httpServletRequest);
		
	}

	/*
	 *@Author TABI
	 *this function determines the indicator if all candidates meet requirements for the PV of that center to be generated 
	 */
	private boolean checkPVGenerable(EligibleCenter eligibleCenter) {
		List<StudentSession> candidates = studentSessionService.findByPvResults(eligibleCenter);
		List<StudentSession> allCandidates = studentSessionService.findByEligibleCenter(eligibleCenter);
		if (candidates.size()>0 || allCandidates.size()<=0){
			return false;
		}else{  
			return true;
		}
		
	}

	/*
	 *@Author TABI
	 *this function helps to generate PV
	 */
	private void generatePvTrackAndLog(EligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stubpv_list
		eligibleCenterService.generatePV(eligibleCenter, user);
		tracker.track(eligibleCenter, "GENERATED PV", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "PV RESETTED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ELIGIBLE CENTER ID IS "+eligibleCenter.getId(),httpServletRequest);
		
	}

	/*
	 *@Author TABI
	 *this function helps to load list of EligibleCenters 
	 */
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model){
		int year = Calendar.getInstance().get(Calendar.YEAR);
		model.addAttribute("currentYear", year);
		model.addAttribute("sessions", examSessionService.findByYear(year));
	    model.addAttribute("eligibleCenters", eligibleCenterService.findAll());
	    model.addAttribute("regions", regionService.findAll());
		return "pv/pv_list";
	}
	

	
	/*
	 *@Author nnoko
	 *this function helps to load view to manage-pv 
	 */
	@RequestMapping(value="/manage_pv_list", method=RequestMethod.GET)
	public String manage_pv_list(Model model, @RequestParam("eligibleCenterId") int eligibleCenterId){
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		List<StudentSession> presentedStudentsinCenter = studentSessionService.findByEligibleCenterAndResult(eligibleCenter, 1);

		model.addAttribute("students", presentedStudentsinCenter);
		model.addAttribute("eligibleCenter", eligibleCenter);
	
		
		return "pv/manage_candidate_result";
	}
	
	
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String findPV(@RequestParam("region") int region,@RequestParam("session") int session, Model model, HttpServletRequest httpServletRequest){
		
		List<EligibleCenter> eligibleCenters = eligibleCenterService.findByRegionAndSessionDate(region, session);
		model.addAttribute("eligibleCenters", eligibleCenters);
		return "pv/search-results";
		
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
	
	
	private void resetpvTrackAndLog(EligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest ){
		eligibleCenterService.managePvReset( eligibleCenter,user);
		tracker.track(eligibleCenter, "RESET ELIGIBLE CENTER", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "PV RESETTED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ELIGIBLE CENTER ID IS "+eligibleCenter.getId(),httpServletRequest);
				
	}
	
	/*
	 *@uthor MPA
	 *this function helps to close a pv 
	 *
	 */
	
	private void closepvTrackAndLog(EligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest ){
		eligibleCenterService.managePvClose( eligibleCenter,user);
		tracker.track(eligibleCenter, "CLOSE ELIGIBLE CENTER", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "PV CLOSED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ELIGIBLE CENTER ID IS "+eligibleCenter.getId(),httpServletRequest);
				
	}
	
	/*
	 *@uthor MPA
	 *this function helps to validate a pv 
	 */
	private void vaidatepvTrackAndLog(EligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest ){
		eligibleCenterService.managePvValidate( eligibleCenter,user);
		tracker.track(eligibleCenter, "VALIDATE ELIGIBLE CENTER", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "PV VALIDATED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ELIGIBLE CENTER ID IS "+eligibleCenter.getId(),httpServletRequest);
				
	}
	
	/*
	 *@uthor MPA
	 *this function helps to open a pv 
	 */
	private void openpvTrackAndLog(EligibleCenter eligibleCenter, User user, HttpServletRequest httpServletRequest ){
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
	public ResponseEntity<byte[]> outSlipApplicationPrintedPdf(@RequestParam("id") int id, @RequestParam("typeList") String typeList, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, DocumentException{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		//applicationStatusPrinted =7 6
		Group groupTrainingCenter = groupService.findByName("GROUP TRAINNING CENTER MANAGER");


		if(typeList.contains("candidates_list_appouved")){
			printerService.printEligibleList(outStream, headers, id, typeList);
			//printerService.ouSlipPdf(outStream,headers,id , Constants.applicationStatusPrinted);
		}else{
			User user = userService.getLogedUser(httpServletRequest);


			if (typeList.contains("candidates_list_presented_print")){

				if((user.getTrainingCenter() != null && user.getGroup().equals(groupTrainingCenter))){

					printerService.printCandidatsList(outStream, headers, id, typeList, user.getTrainingCenter().getId());
				}else {
					printerService.printCandidatsList(outStream, headers, id, typeList);
				}

			}else {
				printerService.printList(outStream, headers, id, typeList);
			}



			//printerService.ouSlipPdf(outStream,headers,id , Constants.applicationStatusPrinted);
		}


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

	
	@RequestMapping("/maxCapacity")
	@ResponseBody
	public String modifyMaxCapacity(@RequestParam("id") int id, @RequestParam("") int maxStudent) {
		EligibleCenter  center =eligibleCenterService.findById(id);
		if(center.getEligibleCenterStatus().getId()!=2)  return "forbidden";
		//else center.setMaxStudent(maxStudent);
		eligibleCenterService.save(center);
		return "accepted";
	}
	
	@Secured({"ROLE_PRINT_TRANSCRIPT"})
	@RequestMapping(value = "/printTranscript", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> transcriptPrintedPdf(@RequestParam("studentSessionId") int studentSessionId,@RequestParam("applicationId") int applicationId,@RequestParam("eligiblecenterId") int eligiblecenterId, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, DocumentException{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
	
		StudentSession studentSession = studentSessionService.findById(studentSessionId);
		ApplicationTranscript applicationTranscript = applicationTranscriptService.findById(studentSession.getApplicationTranscript().getId());
		applicationTranscript.setMatricule(printerService.generateTranscriptNumber(studentSession.getStudent().getSpeciality().getAbbreviation(), applicationTranscript.getId()));
		applicationTranscriptService.saveTranscript(applicationTranscript);
		//printerService.printTranscript(outStream, headers, studentSessionId,eligiblecenterId);
		printerService.printTranscript(outStream, headers, studentSessionId,eligiblecenterId,httpServletRequest);
		
		Application application = applicationService.findAppById(applicationId);
		application.setApplicationStatus(applicationStatusService.findApplicationStatusById(5));
		applicationService.save(application);
		
		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(outStream.toByteArray());
	}
	
	
	 /**
	 *@throws Exception 
	 * @Author nnoko
	 *this function helps to Print Student Certificate
	 */
	@RequestMapping(value="/printPvCertificatePreview", method=RequestMethod.GET)
	public String managePvCertificatePreview(@RequestParam("studentSessionId") int studentSessionId, @RequestParam("eligibleCenterId") int eligibleCenterId,Model model,HttpServletRequest httpServletRequest,HttpServletResponse response)
			throws Exception{
		
		StudentSession studentSession = studentSessionService.findById(studentSessionId);		
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);		
        List<StudentSession> successfulStudentsinCenter = studentSessionService.findByEligibleCenterAndResult(eligibleCenter, 4);
        String extension = "jpg";
		
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		File directory = new File(certificateFilesFolder);
		

			
		printerService.printCertificate(outStream, headers, studentSessionId, eligibleCenterId,"preview");
		String filename=certificateFilesFolder + "certificate.pdf";
		FileOutputStream output = new FileOutputStream(filename);
        output.write(outStream.toByteArray());
        printerService.generateImageFromPDF(filename, extension,studentSessionId);
        String fileName1 = certificateFilesFolder+"certificate_"+studentSessionId+"-"+1+"."+extension;
        BufferedImage img1 = ImageIO.read(new File(fileName1));
        ImageIOUtil.writeImage(img1, String.format(certificateFilesFolder+"certificate"+studentSessionId+"."+extension), 300);
        model.addAttribute("studentSessionId", studentSessionId);
       
        return "pv/certificate_preview";
		}
	
	
	
	
	 /**
		 *@Author nnoko
		 *this function helps to Print Student Certificate
		 */
		@RequestMapping(value="/printPvCertificate", method=RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
		public ResponseEntity<byte[]> managePvCertificate(@RequestParam("studentSessionId") int studentSessionId,@RequestParam("applicationId") int applicationId, @RequestParam("eligibleCenterId") int eligibleCenterId,Model model,HttpServletRequest httpServletRequest,HttpServletResponse response)
				throws IOException, DocumentException{
			StudentSession studentSession = studentSessionService.findById(studentSessionId);		
			EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);		
	        List<StudentSession> successfulStudentsinCenter = studentSessionService.findByEligibleCenterAndResult(eligibleCenter, 4);
	        Date date = new Date(); 
	        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	        String printedDate = formatter.format(date);
	        
	        Authority authority = authorityService.findAuthorityById(1);
			Certificate certificate = new Certificate() ; 
			ByteArrayOutputStream outStream=new ByteArrayOutputStream();
			HttpHeaders headers = new HttpHeaders();
			File directory = new File(certificateFilesFolder);
			
			Speciality studentSessionSpeciality = studentSession.getStudent().getSpeciality();
			String speciality = studentSessionSpeciality.getAbbreviation();
			List<Certificate> certificatesPerType = certificateService.findCertificatesByType(speciality);
			int certificateNum = certificatesPerType.size()+1 ;
			String certificateValue = printerService.generateCertificateNumber(speciality, certificateNum);
				
			printerService.printCertificate(outStream, headers, studentSessionId, eligibleCenterId,"print");
			if(studentSession.getCertificate()==null) {
			certificate.setAuthority(authority);
			certificate.setNumber(certificateValue);
			certificate.setStudentSession(studentSession);
			certificate.setType(speciality);
			certificate.setJuryNumber(studentSession.getEligibleCenter().getJuryNumber());
			certificate.setPrintedDate(date);
			certificateService.save(certificate);
			}else {
				certificate = certificateService.findCertificateByStudentSession(studentSession);
				certificate.setJuryNumber(studentSession.getEligibleCenter().getJuryNumber());
				certificate.setPrintedDate(date);
				certificateService.save(certificate);
			}
			Application application= applicationService.findAppById(applicationId);
			Speciality specialityToPersist = studentSession.getSpeciality();
			if(specialityToPersist == null)
				specialityToPersist= studentSession.getStudent().getSpeciality();
			application.setSpeciality(specialityToPersist);
			application.setApplicationStatus(applicationStatusService.findApplicationStatusById(5));
			applicationService.save(application);

				return ResponseEntity
						.ok()
						.headers(headers)
						.contentType(MediaType.APPLICATION_PDF)
						.body(outStream.toByteArray());
			}
		
		
	
	@RequestMapping(value = "/printPvProfessionalCard", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> printPvProfessionalCardPdf(@RequestParam("studentSessionId") int studentSessionId,@RequestParam("eligibleCenterId") int eligibleCenterId,@RequestParam("token") String token, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, DocumentException, ParseException{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		//System.out.println("I ammmm printinnnnng");
		StudentSession studentSession = studentSessionService.findById(studentSessionId);
		Date date = new Date();
		ApplicationTranscript applicationTranscript = applicationTranscriptService.findById(studentSession.getApplicationTranscript().getId());
		ProfessionalCard professionalCard = studentSession.getCertificate().getProfessionalCard();
		professionalCard.setMatricule(printerService.generateProfessionalCardNumber(studentSession.getStudent().getSpeciality().getAbbreviation(), applicationTranscript.getId()));
		professionalCard.setIssueDate(date);
		professionalCardService.save(professionalCard);

	//	printerService.printProfessionalCard(outStream, headers, studentSessionId,eligibleCenterId, token);

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(outStream.toByteArray());
	}
	
	@RequestMapping(value = "/printByNote", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> outSlipApplicationPrintedPdfByNote(@RequestParam("id") int id, @RequestParam("typeList") String typeList, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, DocumentException{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		if(typeList.contains("candidates_list_appouved")){
			printerService.printEligibleListByNote(outStream, headers, id, typeList);
		}else{
			printerService.printListByNote(outStream, headers, id, typeList);
		}


		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(outStream.toByteArray());
	}
	
	
	@Secured({"ROLE_PRINT_TRANSCRIPT"})
	@RequestMapping(value = "/printTranscriptPreview", method = RequestMethod.GET)
	
	public String transcriptPreview(@RequestParam("studentSessionId") int studentSessionId,@RequestParam("eligibleCenterId") int eligibleCenterId,Model model,HttpServletRequest httpServletRequest, HttpServletResponse response) 
			throws Exception{
		String extension = "jpg";
		int totalStudents = 1;
		List<StudentSession> allCenterStudents = null;
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		printerService.printTrainscriptPreview(outStream, headers, studentSessionId,eligibleCenterId, httpServletRequest);
		String filename=transcriptPreviewFolder+"transcript.pdf";
        FileOutputStream output = new FileOutputStream(filename);
        output.write(outStream.toByteArray());
        if(studentSessionId != 0){
        	printerService.generateImageFromtranscriptPDF(filename, extension,studentSessionId);
        	String fileName1 = transcriptPreviewFolder+"transcript_"+studentSessionId+"-"+1+"."+extension;
    		
    		//String fileName1 = transcriptPreviewFolder+"transcript_"+studentSessionId+"."+extension;
    		BufferedImage img1 = ImageIO.read(new File(fileName1));
        }
        if(eligibleCenterId != 0)
        	printerService.generateImageFromtranscriptPDF(filename, extension,eligibleCenterId);


		EligibleCenter eligibleCenter=null;
		if(eligibleCenterId != 0){
			 eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
        	if(eligibleCenter != null && eligibleCenter instanceof EligibleCenter ){
        		studentSessionId = eligibleCenterId;
				allCenterStudents = studentSessionService.findByEligibleCenter(eligibleCenter);
				totalStudents = allCenterStudents.size();
        	}
		}
		model.addAttribute("allCenterStudents", allCenterStudents);
		model.addAttribute("totalStudents", totalStudents);
	    model.addAttribute("studentSessionId", studentSessionId);
		model.addAttribute("eligibleCenter", eligibleCenter);
		return "pv/transcript_preview";
		
	}
	
	@RequestMapping(value = "/printPreviewProfessionalCard", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public String printPreviewProfessionalCardPdf(@RequestParam("studentSessionId") int studentSessionId,@RequestParam("eligibleCenterId") int eligibleCenterId,@RequestParam("token") String token, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws Exception{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		
		String extension = "jpg";
//		printerService.printProfessionalCard(outStream, headers, studentSessionId,eligibleCenterId, token);
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
		
		return "pv/professional_card_preview";
	}
	


	private InSlip createInSlipFromPV(EligibleCenter eligibleCenter, List<StudentSession> allStudentSessions, String type, User connectedUser, HttpServletRequest httpServletRequest) {
		//TODO
		//port creating inslip into a reusable chunk
		InSlipStatus inSlipStatus = inSlipStatusService.findById(1);
		//generate InSlips for transcript and its applications
		InSlip inslip = new InSlip();
		inslip.setEligibleCenter(eligibleCenter);
		inslip.setCreationDate(new Date());
		inslip.setOffice(connectedUser.getOffice());
		inslip.setStatus(inSlipStatus);
		inslip.setTotal(allStudentSessions.size());
		inslip.setInSlipType(type);
		
		String referenceNumber = inSlipService.generateReferenceNumber(inslip, type);
		inslip.setReferenceNumber(referenceNumber);
		inSlipService.save(inslip);
		
		tracker.track(inslip, "IN SLIP CREATED FROM PV BY USER: " +  connectedUser.getFirstName()+" "+connectedUser.getLastName(), httpServletRequest);
		
		return inslip;
	}

	/*
	 * THIS FUNCTION IS COMMENTED TO PREVENT USERS FROM ACCESSING THE VIEW TO GENERATE INSLIPS FROM pv
	 * This could be uncommented only when an appropriate flow is adopted before it is uncommented from the frontend and here in the backend
	@Secured({"ROLE_GENERATE_PV_INSLIP"})
	@RequestMapping(value="/get/generate/inslip", method=RequestMethod.GET)
	public String generateInslipLoad(@RequestParam("eligibleCenterId") int eligibleCenterId, Model model, HttpServletRequest httpServletRequest){
		
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		User connectedUser = userService.getLogedUser(httpServletRequest);
		
		model.addAttribute("eligibleCenter", eligibleCenter);
		model.addAttribute("connectedUser", connectedUser);
		return "pv/inslip/load_generator";
		
	}
	*/
	
	
	@Secured({"ROLE_GENERATE_PV_INSLIP"})
	@RequestMapping(value="/generate_inslip/{eligibleCenterId}", method=RequestMethod.GET)
	public String generateInSlipFromPV(@PathVariable(value="eligibleCenterId") int eligibleCenterId , @RequestParam("inSlipType") String inSlipType, HttpServletRequest httpServletRequest, Model model){
		
		User connectedUser = userService.getLogedUser(httpServletRequest);
		int rejectedResultValue = 3;
		int successfulResultValue = 4;
		List<StudentSession> allStudentSessions;
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		List<StudentSession> allStudentSession = studentSessionService.findByEligibleCenterAndResult(eligibleCenter, rejectedResultValue);
		for (int j = 0; j < allStudentSession.size(); j++) {
			Certificate certificate = certificateService.findCertificateByStudentSession(allStudentSession.get(j));
			if (certificate.getIsPrinted()!=0) {
				return "certificate.not.printed";
			
		}
			}
		
		boolean isGenerableInSlip = checkIfUserCanGenerateInSlipFromPV(eligibleCenter, inSlipType);
		if (isGenerableInSlip==false){
			
			return "inslip.exists";
		}
		
		if (inSlipType.equals("T")){
			allStudentSessions = studentSessionService.findByEligibleCenter(eligibleCenter);
			List<StudentSession> rejectedstudentSessions = studentSessionService.findByEligibleCenterAndResult(eligibleCenter, rejectedResultValue);
			
			allStudentSessions.removeAll(rejectedstudentSessions);
		}else{
			allStudentSessions = studentSessionService.findByEligibleCenterAndResult(eligibleCenter, successfulResultValue);
		}
		
		InSlip inSlip = createInSlipFromPV(eligibleCenter, allStudentSessions, inSlipType, connectedUser, httpServletRequest);
		int processTypeId=0;
		if(inSlipType.equals("T")){
			processTypeId=1;
		}else if(inSlipType.equals("C")){
			processTypeId=2;
		}else {
			processTypeId =3;
		}
		ProcessType processType = processTypeService.findProcessTypeById(processTypeId);
		ApplicationStatus applicationStatus = applicationStatusService.findApplicationStatusById(1);
		
		
		if(inSlipType.equals("T")){
			createApplicationsForTranscript(allStudentSessions,inSlip,connectedUser,processType,applicationStatus, httpServletRequest);
		}else if(inSlipType.equals("C")){
			createApplicationsForCertificate(allStudentSessions,inSlip,connectedUser,processType,applicationStatus, httpServletRequest);
		}else {
			createApplicationsForProfessionalCard(allStudentSessions,inSlip,connectedUser,processType,applicationStatus, httpServletRequest);
		}
				
		return "ok";
	}

	private void createApplicationsForProfessionalCard(List<StudentSession> allStudentSessions, InSlip inSlip,
			User connectedUser, ProcessType processType, ApplicationStatus applicationStatus, HttpServletRequest httpServletRequest) {
		
		for (int i=0; i<allStudentSessions.size();i++){

			Certificate certificate;
			ProfessionalCard professionalCard;
			if(allStudentSessions.get(i).getCertificate()==null){
				certificate= new Certificate();
				certificate.setStudentSession(allStudentSessions.get(i));
				certificate.setAuthority(null);
				certificate.setIsPrinted(0);
				certificate.setNumber(null);
				certificate.setPrintedDate(null);
				certificate.setType(null);
				certificateService.save(certificate);
				
				professionalCard = new ProfessionalCard();
				professionalCard.setCertificate(certificate);
				professionalCard.setIssueDate(null);
				professionalCard.setMatricule("");
				professionalCard.setOnProcess(0);
				professionalCard.setIssueDate(null);
				professionalCardService.save(professionalCard);
			}else{
				certificate = allStudentSessions.get(i).getCertificate();
				if (certificate.getProfessionalCard()==null){
					professionalCard = new ProfessionalCard();
					professionalCard.setCertificate(certificate);
					professionalCard.setIssueDate(null);
					professionalCard.setMatricule("");
					professionalCard.setOnProcess(0);

					professionalCard.setIssueDate(null);
					professionalCardService.save(professionalCard);
				}else{
					professionalCard = certificate.getProfessionalCard();
				}
			}
			Application application =new Application();
			application.setInSlip(inSlip);
			application.setProcessType(processType);
			application.setOffice(connectedUser.getOffice());
			application.setApplicationStatus(applicationStatus);
			application.setAuthority(null);
			application.setCertificate(certificate);
			application.setComputerizationDate(new Date());
			application.setPhoto(null);
			application.setSignature(null);
			application.setNumber(i+1);
			application.setPhoto(allStudentSessions.get(i).getPhotoAndSignature());
			application.setSignature(allStudentSessions.get(i).getPhotoAndSignature());
			//generate reference number
			application.setSerialNumber(inSlip.getReferenceNumber() + "-"+(i+1));
			application.setSpeciality(allStudentSessions.get(i).getStudent().getSpeciality());
			application.setFormSerialNumber("0");
	
			applicationService.save(application);	
			professionalCard.setOnProcess(application.getId());
			professionalCardService.updateProfessionalCard(professionalCard);
			tracker.track(application, "APPLICATION CREATED FROM SYSTEM BY USER: " +  connectedUser.getFirstName()+" "+connectedUser.getLastName(), httpServletRequest);
			//copy files from student session to application
			application = copyFilesFromStudentSessionToApplication(allStudentSessions.get(i),application, allStudentSessions.get(i).getStudentSessionFiles());
			
			
		}

		
	}

	private void createApplicationsForCertificate(List<StudentSession> allStudentSessions, InSlip inSlip,
			User connectedUser, ProcessType processType, ApplicationStatus applicationStatus, HttpServletRequest httpServletRequest) {
		
		for (int i=0; i<allStudentSessions.size();i++){
		//	ProcessType processType = processTypeService.findProcessTypeById(2);
		//	ApplicationStatus applicationStatus = applicationStatusService.findApplicationStatusById(1);
			//create transcript entity to persist values. Link it to applications and persist values.			
			Certificate certificate;
			if(allStudentSessions.get(i).getCertificate()==null){
				certificate= new Certificate();
				certificate.setStudentSession(allStudentSessions.get(i));
				certificate.setAuthority(null);
				certificate.setIsPrinted(0);
				certificate.setNumber(null);
				certificate.setOnProcess(0);
				certificate.setPrintedDate(null);
				certificate.setType(null);
				certificateService.save(certificate);

			}else{
				certificate = allStudentSessions.get(i).getCertificate();
			}
			Application application =new Application();
			application.setInSlip(inSlip);
			application.setProcessType(processType);
			application.setOffice(connectedUser.getOffice());
			application.setApplicationStatus(applicationStatus);
			application.setAuthority(null);
			application.setCertificate(certificate);
			application.setComputerizationDate(new Date());
			application.setPhoto(null);
			application.setSignature(null);
			application.setNumber(i+1);
			application.setPhoto(allStudentSessions.get(i).getPhotoAndSignature());
			application.setSignature(allStudentSessions.get(i).getPhotoAndSignature());
			application.setFormSerialNumber("0");
			//generate reference number
			application.setSerialNumber(inSlip.getReferenceNumber() + "-"+(i+1));
			application.setSpeciality(allStudentSessions.get(i).getStudent().getSpeciality());
			

			applicationService.save(application);	
			certificate.setOnProcess(application.getId());
			certificateService.updateCertificate(certificate);
			tracker.track(application, "APPLICATION CREATED FROM SYSTEM BY USER: " +  connectedUser.getFirstName()+" "+connectedUser.getLastName(), httpServletRequest);
			application = copyFilesFromStudentSessionToApplication(allStudentSessions.get(i),application, allStudentSessions.get(i).getStudentSessionFiles());
			
		}

		
	}

	private void createApplicationsForTranscript(List<StudentSession> allStudentSessions, InSlip inSlip,
			User connectedUser, ProcessType processType, ApplicationStatus applicationStatus, HttpServletRequest httpServletRequest) {
		for (int i=0; i<allStudentSessions.size();i++){
			
			ApplicationTranscript applicationTranscript;
			if(allStudentSessions.get(i).getApplicationTranscript()==null){
				applicationTranscript = new ApplicationTranscript();
				applicationTranscript.setIssueDate(new Date());
				applicationTranscript.setStudentSession(allStudentSessions.get(i));
				applicationTranscript.setOnProcess(0);
				applicationTranscript.setMatricule("");

//				System.out.println(applicationTranscript.toString());
//				System.out.println("I AM HERE AGAIN");
				applicationTranscriptService.saveTranscript(applicationTranscript);
				
			}else{
				applicationTranscript = allStudentSessions.get(i).getApplicationTranscript();
//				System.out.println("I AM HERE WEAJJ");
			}
			
			//create transcript entity to persist values. Link it to applications and persist values.			
			
			Application application =new Application();
			application.setInSlip(inSlip);
			application.setProcessType(processType);
			application.setOffice(connectedUser.getOffice());
			application.setApplicationStatus(applicationStatus);
			application.setAuthority(null);
			application.setCertificate(null);
			application.setTranscript(applicationTranscript);
			application.setComputerizationDate(new Date());
			application.setPhoto(null);
			application.setSignature(null);
			application.setNumber(i+1);
			application.setFormSerialNumber("0");
			application.setPhoto(allStudentSessions.get(i).getPhotoAndSignature());
			application.setSignature(allStudentSessions.get(i).getPhotoAndSignature());
			//generate reference number
			application.setSerialNumber(inSlip.getReferenceNumber() + "-"+(i+1));
			application.setSpeciality(allStudentSessions.get(i).getStudent().getSpeciality());
			applicationService.save(application);		
			tracker.track(application, "APPLICATION CREATED FROM SYSTEM BY USER: " +  connectedUser.getFirstName()+" "+connectedUser.getLastName(), httpServletRequest);
			application = copyFilesFromStudentSessionToApplication(allStudentSessions.get(i),application, allStudentSessions.get(i).getStudentSessionFiles());
			
		}

		
	}

	private boolean checkIfUserCanGenerateInSlipFromPV(EligibleCenter eligibleCenter, String inSlipType) {
		// TODO Auto-generated method stub
		boolean isGenerable = true;
		List<InSlip> inslips = inSlipService.findByEligibleCenter(eligibleCenter);
		System.out.println(inslips);
		for(int i=0; i<inslips.size();i++){
			if (inslips.get(i).getInSlipType().equals(inSlipType)){
				isGenerable=false;
			}
		}
		return isGenerable;
	}
	
	 private static void copy(File src, File dest) throws IOException {
	        InputStream is = null;
	        OutputStream os = null;
	        try {
	            is = new FileInputStream(src);
	            os = new FileOutputStream(dest);

	            // buffer size 1K
	            byte[] buf = new byte[1024];

	            int bytesRead;
	            while ((bytesRead = is.read(buf)) > 0) {
	                os.write(buf, 0, bytesRead);
	            }
	        } finally {
	            is.close();
	            os.close();
	        }
	    }
	 
	 private Application copyFilesFromStudentSessionToApplication(StudentSession studentSession,Application application, Set<StudentSessionFile> studentSessionFiles) {
		File studentSessionPhoto = new File(studentSessionPhotoFolder+studentSession.getPhotoAndSignature());
		File studentSessionSignature = new File(studentSessionPhotoFolder+studentSession.getPhotoAndSignature());
		File applicationPhoto = new File(applicationPhotoFolder+application.getId()+".jpg");
		File applicationSignature = new File(studentSessionPhotoFolder+application.getId()+".jpg");
		
		try {
			//System.out.println(studentSessionPhoto);
			copy(studentSessionPhoto, applicationPhoto);
			copy(studentSessionSignature, applicationSignature);
			
			application.setPhoto(application.getId()+".jpg");
			application.setSignature(application.getId()+".jpg");
			applicationService.updateApplication(application);
			//System.out.println(applicationPhoto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int index=0;
		for (StudentSessionFile studentSessionFile : studentSessionFiles) {
			File studentSessionFilePath = new File(studentSessionFileFolder+studentSessionFile.getFileName());
			File applicationFilePath = new File(applicationFileFolder+application.getId()+"_"+ index+".pdf");
			try {
				//System.out.println(studentSessionPhoto);
				copy(studentSessionFilePath, applicationFilePath);
				
				
				ApplicationFile applicationFile = new ApplicationFile();
				applicationFile.setApplication(application);
				applicationFile.setName(application.getId()+"_"+ index+".pdf");
				applicationFile.setIsDeleted(0);
				applicationFileService.createApplicationFile(applicationFile);
				//applicationService.updateApplication(application);
				//System.out.println(applicationPhoto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			index++;
		}
		return application;
	}

	@Secured({"ROLE_PRINT_TRANSCRIPT"})
	@RequestMapping(value = "/printTranscriptPreviews", method = RequestMethod.GET)

	public String transcriptPreviews(@RequestParam("studentSessionId") int studentSessionId,@RequestParam("eligibleCenterId") int eligibleCenterId,@RequestParam("id") int id,Model model,HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws Exception{
		String extension = "jpg";
		int totalStudents = 1;
		Application application = applicationService.findAppById(id);
		List<StudentSession> allCenterStudents = null;
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		printerService.printTrainscriptPreview(outStream, headers, application.getTranscript().getStudentSession().getId(),eligibleCenterId, httpServletRequest);
		String filename=transcriptPreviewFolder+"transcript.pdf";
		FileOutputStream output = new FileOutputStream(filename);
		output.write(outStream.toByteArray());
		StudentSession studentSession =null;
		if(studentSessionId != 0){
			 studentSession = studentSessionService.findById(studentSessionId);
			printerService.generateImageFromtranscriptPDF(filename, extension,studentSessionId);
			String fileName1 = transcriptPreviewFolder+"transcript_"+studentSessionId+"-"+1+"."+extension;

			//String fileName1 = transcriptPreviewFolder+"transcript_"+studentSessionId+"."+extension;
			BufferedImage img1 = ImageIO.read(new File(fileName1));
		}
		//if(eligibleCenterId != 0)
		//	printerService.generateImageFromtranscriptPDF(filename, extension,eligibleCenterId);


		/*EligibleCenter eligibleCenter=null;
		if(eligibleCenterId != 0){
			eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
			if(eligibleCenter != null && eligibleCenter instanceof EligibleCenter ){
				studentSessionId = eligibleCenterId;
				allCenterStudents = studentSessionService.findByEligibleCenter(eligibleCenter);
				totalStudents = allCenterStudents.size();
			}
		}*/
		model.addAttribute("allCenterStudents", allCenterStudents);
		model.addAttribute("totalStudents", totalStudents);
		model.addAttribute("studentSessionId", studentSessionId);
		model.addAttribute("applicationId", application.getId());
		model.addAttribute("studentSession", studentSession);
		return "production/production_transcript_preview";

	}



}

