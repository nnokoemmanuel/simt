package ppp.simt.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.archive.ArchiveFile;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Module;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.InSlip;
import ppp.simt.entity.production.OutSlip;
import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.tracking.AgreementTracking;
import ppp.simt.entity.tracking.ApplicantTracking;
import ppp.simt.entity.tracking.ApplicationTracking;
import ppp.simt.entity.tracking.ArchiveFileTracking;
import ppp.simt.entity.tracking.ArchiveTracking;
import ppp.simt.entity.tracking.EligibleCenterTracking;
import ppp.simt.entity.tracking.EntranceEligibleCenterTracking;
import ppp.simt.entity.tracking.InSlipTracking;
import ppp.simt.entity.tracking.OutSlipTracking;
import ppp.simt.entity.tracking.StudentSessionTracking;
import ppp.simt.entity.tracking.StudentTracking;
import ppp.simt.entity.tracking.TrainingCenterTracking;
import ppp.simt.entity.tracking.UserTracking;
import ppp.simt.entity.tracking.evaluation.CourseTracking;
import ppp.simt.entity.tracking.evaluation.ModuleTracking;
import ppp.simt.entity.tracking.evaluation.TranscriptTracking;
import ppp.simt.entity.user.User;
import ppp.simt.service.applicant.ApplicantTrackingService;
import ppp.simt.service.applicant.EntranceEligibleCenterTrackingService;
import ppp.simt.service.archive.ArchiveFileTrackingService;
import ppp.simt.service.archive.ArchiveTrackingService;
import ppp.simt.service.core.ApplicationTrackingService;
import ppp.simt.service.core.InSlipTrackingService;
import ppp.simt.service.core.OutSlipTrackingService;
import ppp.simt.service.evaluation.CourseTrackingService;
import ppp.simt.service.evaluation.ModuleTrackingService;
import ppp.simt.service.evaluation.TranscriptTrackingService;
import ppp.simt.service.pv.AgreementTrackingService;
import ppp.simt.service.pv.EligibleCenterTrackingService;
import ppp.simt.service.pv.StudentSessionTrackingService;
import ppp.simt.service.pv.StudentTrackingService;
import ppp.simt.service.pv.TrainingCenterTrackingService;
import ppp.simt.service.user.UserService;
import ppp.simt.service.user.UserTrackingService;

@Service("tracker")
public class Tracker {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserTrackingService userTrackingService;
	
	@Autowired  
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
	private StudentSessionTrackingService  studentSessionTrackingService;

	@Autowired  
	private StudentTrackingService  studentTrackingService;
	
	@Autowired
	private EntranceEligibleCenterTrackingService entranceEligibleCenterTrackingService;

	@Autowired  
	private ApplicantTrackingService applicantTrackingService;
	
	@Autowired  
	private CourseTrackingService courseTrackingService;
	
	@Autowired  
	private ModuleTrackingService moduleTrackingService;
	
	
	@Autowired  
	private AgreementTrackingService agreementTrackingService;
	
	@Autowired  
	private TrainingCenterTrackingService trainingCenterTrackingService;
	
	@Autowired  
	private TranscriptTrackingService transcriptTrackingService;
	
	
	public void track(Object source, String operation, HttpServletRequest httpServletRequest){
		
	      if(source instanceof User){
	    
	    	  trackUser( source ,  operation,  httpServletRequest );
	        
	      }else if(source instanceof Application){
	  	      	    
	    	  trackApplication( source ,  operation, httpServletRequest );	 
	  		        
	  	  }else if(source instanceof Archive){
		  		  	      	    
	  		  trackArchive( source ,  httpServletRequest ,  operation );            
		  	  		        
		  }else  if(source instanceof ArchiveFile){
							  	      	    
			  trackArchiveFile( source , httpServletRequest ,  operation  );		  	          
						  		        
		  }else if(source instanceof InSlip){
										  	      	    
			  trackInSlip( source , httpServletRequest ,  operation  );				  	            	  
									  		        
		  }else if(source instanceof OutSlip){
													  	      	    
			  trackOutSlip( source ,  httpServletRequest , operation  );						  	            	  
		  }else if(source instanceof EligibleCenter){
	      	    
			  trackEligibleCenter( source ,  httpServletRequest , operation  );						  	            	  
		  }else if(source instanceof StudentSession){
	      	    
			  trackCandidateSession( source ,  httpServletRequest , operation  );						  	            	  
		  }else if(source instanceof Student){
	      	    
			  trackStudent( source ,  httpServletRequest , operation  );						  	            	  
		  }else if(source instanceof EntranceEligibleCenter){
	      	    
			  trackEntranceEligibleCenter( source ,  httpServletRequest , operation  );						  	            	  
		  }else if(source instanceof Applicant){
	      	    
  	          trackApplicant( source ,  operation, httpServletRequest );	 
		        
	      }
		  else if(source instanceof Course){
	      	    
  	          trackCourse( source ,   httpServletRequest , operation);	 
		        
	      }
		  else if(source instanceof Module){
  	          trackModule( source ,   httpServletRequest , operation);

		        
	      }
		  else if(source instanceof Agreement){
  	          trackAgreement( source ,   httpServletRequest , operation);

		        
	      }
		  else if(source instanceof TrainingCenter){
  	          trackTrainingCenter( source ,   httpServletRequest , operation);

		        
	      }
		  else if(source instanceof Transcript){
			  trackTranscript( source ,   httpServletRequest , operation);

		        
	      }
	      
	     
	}
	
	
	private void trackUser(Object source , String operation,  HttpServletRequest httpServletRequest ){
		
	  UserTracking tracking=new UserTracking();
  	 // System.out.println("emms-UserTracking");
  	  User name=(User)source;
  	  tracking.setOperation(operation);	    	  
  	  tracking.setUser(name);
  	  tracking.setName(userService.getLogedUser(httpServletRequest).getUsername());
  	  tracking.setOperationDate(new Date());
  	  userTrackingService.save(tracking);
		
	}
	
	private void trackApplication(Object source , String operation,HttpServletRequest httpServletRequest ){
	  ApplicationTracking tracking=new ApplicationTracking();
      Application application=(Application)source;
	 // System.out.println("emms-ApplicationTracking");
	    	  
	  tracking.setUser(userService.getLogedUser(httpServletRequest));
	  tracking.setApplication(application);
	  tracking.setOperation(operation);	
	  tracking.setOperationDate(new Date());
	    	  
	  applicationTrackingService.save(tracking);
	}
	
	
	private void trackArchive(Object source , HttpServletRequest httpServletRequest , String operation ) {
	  ArchiveTracking tracking=new ArchiveTracking();
	  Archive archive=(Archive)source;
	  //System.out.println("emms-ArchiveTracking");
	    	  
	  tracking.setUser(userService.getLogedUser(httpServletRequest));
	  tracking.setArchive(archive);
	  tracking.setOperation(operation);	
	  tracking.setOperationDate(new Date());
	    	  
	    	  archiveTrackingService.save(tracking);
	}
	
	private void trackArchiveFile(Object source , HttpServletRequest httpServletRequest , String operation  ){
	  ArchiveFileTracking tracking=new ArchiveFileTracking();
      ArchiveFile archiveFile=(ArchiveFile)source;
      //System.out.println("emms-ArchiveFileTracking");
    	  
      tracking.setUser(userService.getLogedUser(httpServletRequest));
      tracking.setArchiveFile(archiveFile);
      tracking.setOperation(operation);	
      tracking.setOperationDate(new Date());
    	  
      archiveFileTrackingService.save(tracking);
	}
	
	private void trackInSlip(Object source , HttpServletRequest httpServletRequest , String operation  ){
	   InSlipTracking tracking=new InSlipTracking();
       InSlip inSlip=(InSlip)source;
       //System.out.println("emms-InSlipTracking");
    	  
       tracking.setUser(userService.getLogedUser(httpServletRequest));
       tracking.setInSlip(inSlip);
       tracking.setOperation(operation);	
       tracking.setOperationDate(new Date());
    	  
       inSlipTrackingService.save(tracking);
	}
	
	private void trackOutSlip(Object source , HttpServletRequest httpServletRequest , String operation  ){
		OutSlipTracking tracking=new OutSlipTracking();
    	OutSlip outSlip=(OutSlip)source;
    	//System.out.println("emms-OutSlipTracking");
    	  
    	tracking.setUser(userService.getLogedUser(httpServletRequest));
    	tracking.setOutSlip(outSlip);
    	tracking.setOperation(operation);	
    	tracking.setOperationDate(new Date());
    	  
    	outSlipTrackingService.save(tracking);
        
	}
	
	private void trackEligibleCenter(Object source , HttpServletRequest httpServletRequest , String operation  ){
		EligibleCenterTracking tracking=new EligibleCenterTracking();
		EligibleCenter eligibleCenter=(EligibleCenter)source;
    	//System.out.println("emms-EligibleCenterTracking");
    	  
    	tracking.setUser(userService.getLogedUser(httpServletRequest));
    	tracking.setEligibleCenter(eligibleCenter);
    	tracking.setOperation(operation);	
    	tracking.setOperationDate(new Date());
    	  
    	eligibleCenterTrackingService.save(tracking);
        
	}
	
	private void trackEntranceEligibleCenter(Object source , HttpServletRequest httpServletRequest , String operation  ){
		EntranceEligibleCenterTracking tracking=new EntranceEligibleCenterTracking();
		EntranceEligibleCenter entranceEligibleCenter=(EntranceEligibleCenter)source;
    	//System.out.println("emms-EntranceEligibleCenterTracking");
    	  
    	tracking.setUser(userService.getLogedUser(httpServletRequest));
    	tracking.setEntranceEligibleCenter(entranceEligibleCenter);
    	tracking.setOperation(operation);	
    	tracking.setOperationDate(new Date());
    	  
    	entranceEligibleCenterTrackingService.save(tracking);
        
	}
	
	private void trackCandidateSession(Object source , HttpServletRequest httpServletRequest , String operation  ){
		StudentSessionTracking tracking=new StudentSessionTracking();
		StudentSession studentSession=(StudentSession)source;
    	//System.out.println("emms-CandidateSessionTracking");
    	  
    	tracking.setUser(userService.getLogedUser(httpServletRequest));
    	tracking.setStudentSession(studentSession);
    	tracking.setOperation(operation);	
    	tracking.setOperationDate(new Date());
    	  
    	studentSessionTrackingService.save(tracking);
        
	}
	
	
	private void trackStudent(Object source , HttpServletRequest httpServletRequest , String operation  ){
		StudentTracking tracking=new StudentTracking();
		Student student=(Student)source;
    	System.out.println("emms-studentTracking");
    	  
    	tracking.setUser(userService.getLogedUser(httpServletRequest));
    	tracking.setStudent(student);
    	tracking.setOperation(operation);	
    	tracking.setOperationDate(new Date());
    	  
    	studentTrackingService.save(tracking);
        
	}
	

	private void trackApplicant(Object source , String operation,HttpServletRequest httpServletRequest ){
		  ApplicantTracking tracking=new ApplicantTracking();
	      Applicant applicant=(Applicant)source;
		 // System.out.println("emms-ApplicationTracking");
		    	  
		  tracking.setUser(userService.getLogedUser(httpServletRequest));
		  tracking.setApplicant(applicant);
		  tracking.setOperation(operation);	
		  tracking.setOperationDate(new Date());
		    	  
		  applicantTrackingService.save(tracking);
		}
	
	
	private void trackCourse(Object source , HttpServletRequest httpServletRequest , String operation ) {
		  CourseTracking tracking=new CourseTracking();
		  Course course=(Course)source; 	  
		  tracking.setUser(userService.getLogedUser(httpServletRequest));
		  tracking.setCourse(course);
		  tracking.setOperation(operation);	
		  tracking.setOperationDate(new Date());
          courseTrackingService.save(tracking);
		}
	
	private void trackModule(Object source , HttpServletRequest httpServletRequest , String operation ) {
		  ModuleTracking tracking=new ModuleTracking();
		  Module module=(Module)source;
		  tracking.setUser(userService.getLogedUser(httpServletRequest));
		  tracking.setModule(module);
		  tracking.setOperation(operation);	
		  tracking.setOperationDate(new Date());
          moduleTrackingService.save(tracking);
		}
	
	private void trackTrainingCenter(Object source , HttpServletRequest httpServletRequest , String operation ) {
		  TrainingCenterTracking tracking=new TrainingCenterTracking();
		  TrainingCenter trainingCenter=(TrainingCenter)source;
		  tracking.setUser(userService.getLogedUser(httpServletRequest));
		  tracking.setTrainingCenter(trainingCenter);
		  tracking.setOperation(operation);	
		  tracking.setOperationDate(new Date());
		  trainingCenterTrackingService.save(tracking);
		}
	
	private void trackAgreement(Object source , HttpServletRequest httpServletRequest , String operation ) {
		  AgreementTracking tracking=new AgreementTracking();
		  Agreement agreement=(Agreement)source;
		  tracking.setUser(userService.getLogedUser(httpServletRequest));
		  tracking.setAgreement(agreement);
		  tracking.setOperation(operation);	
		  tracking.setOperationDate(new Date());
          agreementTrackingService.save(tracking);
		}
	
	private void trackTranscript(Object source , HttpServletRequest httpServletRequest , String operation ) {
		  TranscriptTracking tracking=new TranscriptTracking();
		  Transcript transcript=(Transcript)source;
		  tracking.setUser(userService.getLogedUser(httpServletRequest));
		  tracking.setTranscript(transcript);
		  tracking.setOperation(operation);	
		  tracking.setOperationDate(new Date());
		  transcriptTrackingService.save(tracking);
		}
	
}







	
	





