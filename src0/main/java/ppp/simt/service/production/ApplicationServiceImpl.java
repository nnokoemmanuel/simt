package ppp.simt.service.production;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.archive.ArchiveFile;
import ppp.simt.entity.core.Authority;
import ppp.simt.entity.core.Office;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.ApplicationFile;
import ppp.simt.entity.production.ApplicationStatus;
import ppp.simt.entity.production.ApplicationTranscript;
import ppp.simt.entity.production.Capacity;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.production.InSlip;
import ppp.simt.entity.production.OutSlip;
import ppp.simt.entity.production.ProcessType;
import ppp.simt.entity.production.ProfessionalCard;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionFile;
import ppp.simt.entity.user.User;
import ppp.simt.form.ApplicationForm;
import ppp.simt.repository.production.ApplicationRepository;
import ppp.simt.service.archive.ArchiveFileService;
import ppp.simt.service.archive.ArchiveService;
import ppp.simt.service.core.AuthorityService;
import ppp.simt.service.core.PersonService;
import ppp.simt.service.core.SpecialityService;
import ppp.simt.service.pv.StudentSessionFileService;
import ppp.simt.service.pv.StudentSessionService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private ApplicationStatusService applicationStatusService;

	@Autowired
	private ProcessTypeService processTypeService;

	@Autowired
	private StudentSessionService studentSessionService;
	@Autowired
	private StudentSessionFileService studentSessionFileService;

	@Autowired
	private InSlipService inSlipService;

	@Autowired
	private AuthorityService authorityService;

	@Autowired
	private CapacityService capacityService;

	@Autowired
	private ArchiveService archiveService;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private CertificateService certificateService;
	
	@Autowired
	private ApplicationFileService applicationFileService;
	

	@Autowired
	private ArchiveFileService archiveFileService; 
	
	
	@Autowired
	private ProfessionalCardService professionalCardService;
	
	@Autowired
	private SpecialityService specialityService;
	
	@Autowired
	private ApplicationTranscriptService applicationTranscriptService;
	
	@Value("${studentSession.file.folder}")
	private String studentSessionFileFolder;
	
	@Value("${application.file.folder}")
	private String applicationFileFolder;
	
	@Value("${studentSession.photo.folder}")
	private String studentSessionPhotoFolder;
	
	@Value("${studentSession.signature.folder}")
	private String studentSessionSignatureFolder;
	
	
	@Value("${archive.photo.folder}")
	private String archivePhotoFolder;
	
	@Value("${archive.signature.folder}")
	private String archiveSignatureFolder;
	
	@Value("${application.photo.folder}")
	private String applicationPhotoFolder;
	
	@Value("${application.signature.folder}")
	private String applicationSignatureFolder;
	
	
	@Value("${archive.file.folder}")
	private String archiveFileFolder;
	
	

	@Autowired
	EntityManager em;

	@Override
	public List<Application> findAll() {
		return applicationRepository.findAll();
	}


	@Override
	public Application findAppById(int appId) {
		return applicationRepository.findById(appId);
	}


	@Override
	public List<Application> findAppByInSlipId(int inSlipId) {

		return applicationRepository.findAppByInSlipId(inSlipId);
	}

	@Override
	public List<Application> findApplicationByOutSlipAndApplicationStatus(int outSlipId, int status){

		return applicationRepository.findApplicationByOutSlipAndApplicationStatus(outSlipId,status);

	}


	@Override
	public List<Application> findBySuccessfulyRejectedDateBetweenAndOutSlipIsNull(Date startDate, Date enDate) {
		// TODO Auto-generated method stub
		return applicationRepository.findBySuccessfulyRejectedDateBetweenAndOutSlipIsNull(startDate, enDate);
	}


	@Override
	public void save(Application application) {
		applicationRepository.save(application);
		
	}

	@Override
	public List<Application> findByComputerizationDateBetweenAndOffice(Date startDate, Date enDate, Office office) {
		// TODO Auto-generated method stub
		return applicationRepository.findByComputerizationDateBetweenAndOffice(startDate, enDate, office);
	}

	@Override
	public List<Application> findByInSlipAndDateAndStatusAndOfficeAndProcessTypeAndFileNumber(java.sql.Date start, java.sql.Date end, int status, int office, String inSlipReference, int processType, String fileNumber) {

		return applicationRepository.findByInSlipAndDateAndStatusAndOfficeAndProcessTypeAndFileNumber(start, end, status, office, inSlipReference, processType, fileNumber,em);
	}


	@Override
	public void updateApplication(Application application) {
		applicationRepository.saveAndFlush(application);

	}

	/**
	 * Convertie un formulaire en bean
	 * @return
	 */
	@Override
	public Application persistApplicationBean(ApplicationForm applicationForm,User user,HttpServletRequest httpServletRequest){
		 User currentUser = userService.getLogedUser(httpServletRequest);
		 
		Application application = null;
		if(applicationForm.getId() ==0  ){
			
			//case application does not exists yet
			ApplicationStatus  applicationStatus = applicationStatusService.findApplicationStatusById(applicationForm.getApplicationStatus());
			ProcessType applicationProcessType  = processTypeService.findProcessTypeById(applicationForm.getProcesstype());
			Authority authority = authorityService.findAuthorityById(applicationForm.getAuthority());
			String applicationSerialNumber = applicationForm.getInslipReference()+'-'+applicationForm.getApplicationNumber();
			
			if(applicationProcessType.getDescription().equals("NEW CERTIFICATE")||applicationProcessType.getDescription().equals("DUPLICATE OF CERTIFICATE")||applicationProcessType.getDescription().equals("NEW PROFESSIONAL_CARD")  || applicationProcessType.getDescription().equals("DUPLICATE OF PROFESSIONAL_CARD") ){
				if((applicationForm.getSourceEntity().equals("StudentSession")|| ((applicationForm.getSourceEntity().equals("archive")||applicationForm.getSourceEntity().equals("professionalCard")) && applicationProcessType.getDescription().contains("NEW PROFESSIONAL_CARD") )) && (!applicationProcessType.getDescription().contains("DUPLICATE"))){
					StudentSession applicantStudentSession = null;
					if(applicationForm.getSourceEntity().equals("StudentSession"))
				    	 applicantStudentSession = studentSessionService.findById(applicationForm.getSourceId());
				
					applicantStudentSession.setProcessed(0);
					studentSessionService.updateStudentSession(applicantStudentSession);			
					InSlip inSlipEntered = inSlipService.findByReferenceNumber(applicationForm.getInslipReference());
					//call service to generate application number and persist it to the application
					
				
					application = new Application(applicationSerialNumber, new Date(), null, null,
							null, null, inSlipEntered,  null , applicationProcessType,
							applicationStatus,applicationForm.getApplicationNumber(),user.getOffice(), authority,
							user ,applicationForm.getFormSerialNumber());
					
					save(application);
					ProfessionalCard professionalCardNew = null;
					Certificate certificateNew;
					if(applicantStudentSession.getCertificate()==null)
					{   
						certificateNew  = new  Certificate(application.getId(),null, applicantStudentSession.getStudent().getSpeciality().getAbbreviation(), null, applicantStudentSession.getEligibleCenter().getJuryNumber(),
								applicantStudentSession, authority, null, 0) ;
						certificateNew.setStudentSession(applicantStudentSession);
                    	certificateService.createCertificate(certificateNew);
                    }else {
                    	certificateNew = applicantStudentSession.getCertificate();
							
					}
				    if(applicationProcessType.getDescription().contains("CERTIFICATE")){
					    certificateNew.setOnProcess(application.getId());
					    certificateService.updateCertificate(certificateNew);
				    }
				    if(applicationProcessType.getDescription().equals("NEW PROFESSIONAL_CARD")){
				    	if(applicantStudentSession.getCertificate().getProfessionalCard()==null){
				    
				    		if(applicationForm.getSourceEntity().equals("Archive")) {
				    			Archive archive = archiveService.findArchiveById(applicationForm.getSourceId());
				    			professionalCardNew = new ProfessionalCard(null, null,application.getId(), null, archive,
					    				0) ;
				    			professionalCardNew.setCertificate(certificateNew);
				    			professionalCardNew.setMatricule("");
				    			professionalCardService.createProfessionalCard(professionalCardNew);
				    			archive.setOnProcess(application.getId());
				    			archiveService.updateArchive(archive);
				    			moveArchiveFile( archive, application);
				    			
				    		}else{
				    			if(applicationForm.getSourceEntity().equals("ProfessionalCard"))
				    				professionalCardNew = professionalCardService.findById(applicationForm.getSourceId());
				    			if(applicationForm.getSourceEntity().equals("StudentSession")){
				    				
				    				professionalCardNew = new ProfessionalCard(null, null,application.getId(),applicantStudentSession.getCertificate() , null,
						    				0) ;
				    				professionalCardNew.setMatricule("");
				    				professionalCardNew.setCertificate(applicantStudentSession.getCertificate());
				    				professionalCardService.createProfessionalCard(professionalCardNew);
				    				moveStudentSessionFile( applicantStudentSession, application);
				    				
				    			}
				    		}
				    		
				    	}else{
				    		professionalCardNew = applicantStudentSession.getCertificate().getProfessionalCard();
				    		moveStudentSessionFile( applicantStudentSession, application);
				    	}
			
				    	professionalCardNew.setOnProcess(application.getId());
				    	professionalCardService.updateProfessionalCard(professionalCardNew);
				    }  
				    
                    application.setCertificate(certificateNew);
					updateApplication(application);
					if(applicationProcessType.getDescription().equals("NEW CERTIFICATE")){
						moveStudentSessionFile( applicantStudentSession, application);
					}
					Person applicantPerson = applicantStudentSession.getStudent().getPerson();
					if(applicationForm.getLanguage() != null)
						applicantPerson.setLanguage(applicationForm.getLanguage());
					if(applicationForm.getApplicantPhoneNumber() != null)
						applicantPerson.setPhoneNumber(applicationForm.getApplicantPhoneNumber());	
					personService.updatePerson(applicantPerson);
                    Speciality speciality= applicantStudentSession.getSpeciality();
                    if(speciality == null)
                    	speciality=applicantStudentSession.getSpeciality();
					application.setSpeciality(speciality);
					updateApplication(application);
					tracker.track(application, "REGISTERED THE APPLICATION" , httpServletRequest);
					logger_.log(Constants.NORMAL_LOG_DIR, "Register the application : "+currentUser);				
					return  application;
					
				}
				
				if(applicationForm.getSourceEntity().equals("Certificate") && ( applicationProcessType.getDescription().equals("NEW CERTIFICATE") || applicationProcessType.getDescription().equals("DUPLICATE OF CERTIFICATE"))){
					Certificate certificateNew = certificateService.findById(applicationForm.getSourceId());
					StudentSession applicantStudentSession = certificateNew.getStudentSession();
					applicantStudentSession.setProcessed(0);
					studentSessionService.updateStudentSession(applicantStudentSession);			
					InSlip inSlipEntered = inSlipService.findByReferenceNumber(applicationForm.getInslipReference());
					//call service to generate application number and persist it to the application
					application = new Application(applicationSerialNumber, new Date(), null, null,
							null, null, inSlipEntered,  null , applicationProcessType,
							applicationStatus,applicationForm.getApplicationNumber(),user.getOffice(), authority,
							user ,applicationForm.getFormSerialNumber());
					
					application.setSpeciality(applicantStudentSession.getSpeciality());
					save(application);
				    certificateNew.setOnProcess(application.getId());
				    certificateService.updateCertificate(certificateNew);
                    application.setCertificate(certificateNew);
                    updateApplication(application);
					moveStudentSessionFile( applicantStudentSession, application);
					Person applicantPerson = applicantStudentSession.getStudent().getPerson();
					if(applicationForm.getLanguage() != null)
						applicantPerson.setLanguage(applicationForm.getLanguage());
					if(applicationForm.getApplicantPhoneNumber() != null)
						applicantPerson.setPhoneNumber(applicationForm.getApplicantPhoneNumber());	
					personService.updatePerson(applicantPerson);				
					tracker.track(application, "REGISTERED THE APPLICATION" , httpServletRequest);
					logger_.log(Constants.NORMAL_LOG_DIR, "Register the application : "+currentUser);				
					return  application;
					
				}
				
				if(applicationForm.getSourceEntity().equals("ProfessionalCard") && (applicationProcessType.getDescription().equals("NEW PROFESSIONAL_CARD") || applicationProcessType.getDescription().equals("DUPLICATE OF PROFESSIONAL_CARD"))){
					ProfessionalCard professionalCardNew = professionalCardService.findById(applicationForm.getSourceId());
					InSlip inSlipEntered = inSlipService.findByReferenceNumber(applicationForm.getInslipReference());
					//call service to generate application number and persist it to the application
					application = new Application(applicationSerialNumber, new Date(), null, null,
							null, null, inSlipEntered,  null , applicationProcessType,
							applicationStatus,applicationForm.getApplicationNumber(),user.getOffice(), authority,
							user ,applicationForm.getFormSerialNumber());
					save(application);
					Person applicantPerson;
					if(professionalCardNew.getCertificate()!=null){
						StudentSession applicantStudentSession = professionalCardNew.getCertificate().getStudentSession();
						applicantStudentSession.setProcessed(0);
						studentSessionService.updateStudentSession(applicantStudentSession);
						 application.setCertificate(professionalCardNew.getCertificate());
						 application.setArchive(null);
						 moveStudentSessionFile( applicantStudentSession, application);
						 applicantPerson = applicantStudentSession.getStudent().getPerson();
						 application.setSpeciality(applicantStudentSession.getSpeciality());
						 updateApplication(application);
					}else{
						Archive archive = professionalCardNew.getArchive();
						archive.setOnProcess(application.getId());
		    			archiveService.updateArchive(archive); 
		    			application.setCertificate(null);
		    			application.setArchive(archive);
		    			Speciality speciality = specialityService.findByAbbreviation("MAE");
		    			application.setSpeciality(speciality);
		    			updateApplication(application);
		    			applicantPerson = archive.getPerson();
		    			moveArchiveFile( archive, application);
					}
		
					professionalCardNew.setOnProcess(application.getId());
					professionalCardService.updateProfessionalCard(professionalCardNew);
					updateApplication(application);
					
					if(applicationForm.getLanguage() != null)
						applicantPerson.setLanguage(applicationForm.getLanguage());
					if(applicationForm.getApplicantPhoneNumber() != null)
						applicantPerson.setPhoneNumber(applicationForm.getApplicantPhoneNumber());	
					personService.updatePerson(applicantPerson);				
					tracker.track(application, "REGISTERED THE APPLICATION" , httpServletRequest);
					logger_.log(Constants.NORMAL_LOG_DIR, "Register the application : "+currentUser);				
					return  application;
					
				}
				
				
		} else if(applicationProcessType.getDescription().equals("NEW TRANSCRIPT") || applicationProcessType.getDescription().equals("DUPLICATE OF TRANSCRIPT")){
			if(applicationForm.getSourceEntity().equals("StudentSession") && (!applicationProcessType.getDescription().contains("DUPLICATE"))){
				StudentSession applicantStudentSession = studentSessionService.findById(applicationForm.getSourceId());
				applicantStudentSession.setProcessed(0);
				studentSessionService.updateStudentSession(applicantStudentSession);			
				InSlip inSlipEntered = inSlipService.findByReferenceNumber(applicationForm.getInslipReference());
				//call service to generate application number and persist it to the application
				
			
				application = new Application(applicationSerialNumber, new Date(), null, null,
						null, null, inSlipEntered,  null , applicationProcessType,
						applicationStatus,applicationForm.getApplicationNumber(),user.getOffice(), authority,
						user ,applicationForm.getFormSerialNumber());
				application.setSpeciality(applicantStudentSession.getSpeciality());
				save(application);
				ApplicationTranscript applicationTranscriptNew;
				if(applicantStudentSession.getApplicationTranscript()==null)
				{   
					applicationTranscriptNew  = new  ApplicationTranscript(null, null, application.getId(), applicantStudentSession , 0);
					applicationTranscriptNew.setMatricule("");
					applicationTranscriptNew.setStudentSession(applicantStudentSession);
                	applicationTranscriptService.createApplicationTranscript(applicationTranscriptNew);
                }else {
                	applicationTranscriptNew = applicantStudentSession.getApplicationTranscript();
						
				}
				
				applicationTranscriptNew.setOnProcess(application.getId());
				applicationTranscriptService.updateApplicationTranscript(applicationTranscriptNew);
			    application.setTranscript(applicationTranscriptNew);	    
				updateApplication(application);
			    moveStudentSessionFile( applicantStudentSession, application);

				Person applicantPerson = applicantStudentSession.getStudent().getPerson();
				if(applicationForm.getLanguage() != null)
					applicantPerson.setLanguage(applicationForm.getLanguage());
				if(applicationForm.getApplicantPhoneNumber() != null)
					applicantPerson.setPhoneNumber(applicationForm.getApplicantPhoneNumber());	
				personService.updatePerson(applicantPerson);				
				tracker.track(application, "REGISTERED THE APPLICATION" , httpServletRequest);
				logger_.log(Constants.NORMAL_LOG_DIR, "Register the application : "+currentUser);				
				return  application;
				
			}	
			
			if(applicationForm.getSourceEntity().equals("Transcript") ){
				ApplicationTranscript applicationTranscriptNew = applicationTranscriptService.findById(applicationForm.getSourceId());
				StudentSession applicantStudentSession = applicationTranscriptNew.getStudentSession();
				applicantStudentSession.setProcessed(0);
				studentSessionService.updateStudentSession(applicantStudentSession);			
				InSlip inSlipEntered = inSlipService.findByReferenceNumber(applicationForm.getInslipReference());
				//call service to generate application number and persist it to the application
				
			
				application = new Application(applicationSerialNumber, new Date(), null, null,
						null, null, inSlipEntered,  null , applicationProcessType,
						applicationStatus,applicationForm.getApplicationNumber(),user.getOffice(), authority,
						user ,applicationForm.getFormSerialNumber());
				application.setSpeciality(applicantStudentSession.getSpeciality());
				save(application);
				applicationTranscriptNew.setOnProcess(application.getId());
				applicationTranscriptService.updateApplicationTranscript(applicationTranscriptNew);
			    application.setTranscript(applicationTranscriptNew);	    
				updateApplication(application);
			    moveStudentSessionFile( applicantStudentSession, application);
				Person applicantPerson = applicantStudentSession.getStudent().getPerson();
				if(applicationForm.getLanguage() != null)
					applicantPerson.setLanguage(applicationForm.getLanguage());
				if(applicationForm.getApplicantPhoneNumber() != null)
					applicantPerson.setPhoneNumber(applicationForm.getApplicantPhoneNumber());	
				personService.updatePerson(applicantPerson);				
				tracker.track(application, "REGISTERED THE APPLICATION" , httpServletRequest);
				logger_.log(Constants.NORMAL_LOG_DIR, "Register the application : "+currentUser);				
				
				return  application;
				
			}	
			
		    	
		}

		}else{
             //cas edition application ou rejet d 'une application
			return null;
		}
		return null;
	}


	@Override
	public List<Application> getDistinctTotalByOfficePerStudentSession(java.sql.Date sqlStartDate , java.sql.Date sqlendDate, int officeId, int appStatutId, int processType, EntityManager em) {
		// TODO Auto-generated method stub
		return applicationRepository.getDistinctTotalByOfficePerStudentSession(sqlStartDate , sqlendDate, officeId, appStatutId, processType, em);
	}


	@Override
	public List<Application> findApplicationByCertificate(Certificate certificate) {
		return applicationRepository.findApplicationByCertificate(certificate);
	}


	@Override
	public List<Application> findApplicationByArchive(Archive archive) {
		return applicationRepository.findApplicationByArchive(archive);
	}


	@Override
	public List<Application> findApplicationByTranscript(ApplicationTranscript applicationTranscript) {
		return applicationRepository.findApplicationByTranscript(applicationTranscript);
	}



   private void moveStudentSessionFile(StudentSession applicantStudentSession,Application application){
	    ApplicationFile applicantFile = null;
		//photo and signature persistence
		if(applicantStudentSession.getPhotoAndSignature() != null){
			application.setPhoto(Integer.toString(application.getId())+".jpg");
			application.setSignature(Integer.toString(application.getId())+".jpg");
			applicationFileService.copyFilesFromOneFolderToAnother(studentSessionPhotoFolder,applicationPhotoFolder , applicantStudentSession.getPhotoAndSignature() , Integer.toString(application.getId())+".jpg");
			applicationFileService.copyFilesFromOneFolderToAnother(studentSessionSignatureFolder,applicationSignatureFolder , applicantStudentSession.getPhotoAndSignature() , Integer.toString(application.getId())+".jpg");

		}
		
		if(studentSessionFileService.findByStudentSessionAndDeleted(applicantStudentSession, 0).size() > 0){
			//candidateSession files persistance
			Set<StudentSessionFile> listOfCandidateSessionFiles = studentSessionFileService.findByStudentSessionAndDeleted(applicantStudentSession, 0);                                                       
			int count = 0;
			for(StudentSessionFile file: listOfCandidateSessionFiles ){
				if(listOfCandidateSessionFiles.size() > 0){
					if(listOfCandidateSessionFiles.size()==1){
						String fileName = Integer.toString(application.getId())+".pdf";
						applicantFile = new ApplicationFile(fileName, application , 0);
						applicationFileService.createApplicationFile(applicantFile);
						applicationFileService.copyFilesFromOneFolderToAnother(studentSessionFileFolder,applicationFileFolder , file.getFileName() , fileName);
					}else{
						String fileName = Integer.toString(application.getId())+"_"+count+".pdf";
						applicantFile = new ApplicationFile(fileName, application , 0);
						applicationFileService.createApplicationFile(applicantFile);
						applicationFileService.copyFilesFromOneFolderToAnother(studentSessionFileFolder,applicationFileFolder , file.getFileName() , fileName);
					}
			    }
				count+=1;
			
			}
		}
		updateApplication(application);
		
   }
   
   private void moveArchiveFile(Archive archive,Application application){
	    ApplicationFile applicantFile = null;
		//photo and signature persistence
		if(archive.getPhotoAndSignature() != null){
			application.setPhoto(Integer.toString(application.getId())+".jpg");
			application.setSignature(Integer.toString(application.getId())+".jpg");
			applicationFileService.copyFilesFromOneFolderToAnother(archivePhotoFolder,applicationPhotoFolder , archive.getPhotoAndSignature() , Integer.toString(application.getId())+".jpg");
			applicationFileService.copyFilesFromOneFolderToAnother(archiveSignatureFolder,applicationSignatureFolder , archive.getPhotoAndSignature() , Integer.toString(application.getId())+".jpg");

		}
		
		if(archiveFileService.findByArchiveAndDeleted(archive, 0).size() > 0){
			//candidateSession files persistance
			Set<ArchiveFile> listOfArchiveFiles = archiveFileService.findByArchiveAndDeleted(archive, 0);                                                       
			int count = 0;
			for(ArchiveFile file: listOfArchiveFiles ){
				if(listOfArchiveFiles.size() > 0){
					if(listOfArchiveFiles.size()==1){
						String fileName = Integer.toString(application.getId())+".pdf";
						applicantFile = new ApplicationFile(fileName, application , 0);
						applicationFileService.createApplicationFile(applicantFile);
						applicationFileService.copyFilesFromOneFolderToAnother(archiveFileFolder,applicationFileFolder , file.getFileName() , fileName);
					}else{
						String fileName = Integer.toString(application.getId())+"_"+count+".pdf";
						applicantFile = new ApplicationFile(fileName, application , 0);
						applicationFileService.createApplicationFile(applicantFile);
						applicationFileService.copyFilesFromOneFolderToAnother(archiveFileFolder,applicationFileFolder , file.getFileName() , fileName);
					}
			    }
				count+=1;
			
			}
		}
		updateApplication(application);
		
  }

}