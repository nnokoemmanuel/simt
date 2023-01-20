package ppp.simt.service.pv;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ppp.simt.entity.core.Country;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionFile;
import ppp.simt.entity.pv.StudentSessionStatus;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentQualification;
import ppp.simt.entity.user.User;
import ppp.simt.form.CNIForm;
import ppp.simt.form.CandidateChekerForm;
import ppp.simt.form.StudentForm;
import ppp.simt.repository.pv.StudentSessionRepository;
import ppp.simt.service.core.CategoryService;
import ppp.simt.service.core.CoreService;
import ppp.simt.service.core.CountryService;
import ppp.simt.service.core.PersonService;
import ppp.simt.service.production.CapacityService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;

@Service("studentSessionService")
public class StudentSessionServiceImpl implements StudentSessionService{

	@Autowired
	private StudentSessionRepository studentSessionRepository;
	
	@Autowired
	private StudentSessionService studentSessionService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TrainingCenterService trainingCenterService;

	@Autowired
	EntityManager em;
	
	@Autowired
	private CoreService coreService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private EligibleCenterService eligibleCenterService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private StudentSessionFileService studentSessionFileService;
	
	@Autowired
	private CapacityService capacityService;
	
	@Autowired
	private StudentQualificationService studentQualificationService;
	
	@Value("${candidateSession.file.folder}")
	private String candidateSessionFileFolder;
	
	@Value("${candidateSession.photo.folder}")
	private String candidateSessionImageFolder;
	
	@Value("${candidateSession.signature.folder}")
	private String candidateSessionSignatureFolder;
	

	@Override
	public StudentSession findById(int studentSessionId) {
		return studentSessionRepository.findById(studentSessionId);
	}



//	@Override
//	public List<StudentSession> findByTrainingCenter(TrainingCenter trainingCenter) {
//		return studentSessionRepository.findByTrainingCenter(trainingCenter);
//	}

	@Override
	public List<StudentSession> findAll() {
		return studentSessionRepository.findAll();
	}
	
	
	@Override
	public StudentSession persistStudent(StudentForm studentForm, HttpServletRequest httpServletRequest, Person personne){
		User user = userService.getLogedUser(httpServletRequest);
		StudentSession studentSession = null;
		EligibleCenter eligibleCenter = null;
		String logMessage = "";

	            	Country personNationality = countryService.findCountryById(studentForm.getNationality());
	            	Person person;
	            	String matricule = null ;
	            	int countFile = 0;
	            	Student student = null ;
	            	if(personne == null) {
//	        		 person = new Person( studentForm.getSurName(), studentForm.getGivenName(), studentForm.getPob(), studentForm.getGender() , null,
//	        				coreService.convertUtilToSql(studentForm.getDob()), null,null,personNationality,studentForm.getLicenseNum(),matricule) ;
//	        		personService.createPerson(person);
	        		}else {
	        			 person = personne;
	        		}
	            	TrainingCenter trainingCenter= trainingCenterService.findById(studentForm.getTrainingCenter());
	        		//candidateSession = new CandidateSession(Theory, candidateForm.getPvNumber(), 0, 0,category,eligibleCenter,trainingSchool, person);
	        		//studentSession = new StudentSession(studentForm.getPvNumber(), 0, 1,eligibleCenter,countFile,student);
	        		
	        		studentSessionService.createStudentSession(studentSession); 
	        		MultipartFile image=studentForm.getImage();
	        		MultipartFile signature=studentForm.getSignature(); 
	 
	        		int lastIndex=image.getOriginalFilename().lastIndexOf(".");
			    	String extensionImage=image.getOriginalFilename().substring(lastIndex+1,image.getOriginalFilename().length());
			    	
			    	lastIndex=signature.getOriginalFilename().lastIndexOf(".");
			    	String extensionSignature=signature.getOriginalFilename().substring(lastIndex+1,signature.getOriginalFilename().length());
	 	  	        MultipartFile[] files=studentForm.getFile();
	 	  
	 	  			  try{  	
	 	  				
	 	  				int i=1;
	 	  				for(MultipartFile file:files) {
	 	  					
	 	 	  	        lastIndex=file.getOriginalFilename().lastIndexOf(".");
				    	String extension=file.getOriginalFilename().substring(lastIndex+1,file.getOriginalFilename().length());
	 	  				byte barr[]=file.getBytes();  
	 	  		        BufferedOutputStream bout=new BufferedOutputStream(  
	 	  		        		new FileOutputStream(candidateSessionFileFolder+studentSession.getId()+"_"+i+"."+extension));
	 	  		        bout.write(barr);  
	 	  		        bout.flush();  
	 	  		        bout.close();  
	 	  		        StudentSessionFile candidateFile= new StudentSessionFile();
	 	  		        candidateFile.setStudentSession(studentSession);
	 	  		        candidateFile.setDeleted(0);
	 	  		        candidateFile.setFileName(studentSession.getId()+"_"+i+"."+extension);
	 	  		        studentSessionFileService.save(candidateFile);
	 	  		        i++;
	 	  				}
	 	  				  
	 	  				byte barrImage[]=studentForm.getImage().getBytes();  
	 	  		        BufferedOutputStream boutImage=new BufferedOutputStream(  
	 	  		         new FileOutputStream(candidateSessionImageFolder+studentSession.getId()+"."+extensionImage));
	 	  		        boutImage.write(barrImage);  
	 	  		        boutImage.flush();  
	 	  		        boutImage.close();  
	 	  		      
	 	  		      byte barrSignature[]=studentForm.getSignature().getBytes();  
	  		           BufferedOutputStream boutSignature=new BufferedOutputStream(  
	  		        		new FileOutputStream(candidateSessionSignatureFolder+studentSession.getId()+"."+extensionSignature));
	  		            boutSignature.write(barrSignature);  
	  		            boutSignature.flush();  
	  		            boutSignature.close(); 
	  		            studentSession.setPhotoAndSignature(studentSession.getId()+"."+extensionImage);
	  		            studentSessionService.createStudentSession(studentSession);

	 	  		        }catch(Exception e){
	 	  		        	e.printStackTrace();
	 	  		        	
	 	  		        	studentSessionRepository.delete(studentSession);
	 	  		        	//return "KO";
	 	  		       }		 
	 	  		 
	        		//tracker.track(candidateSession, "CANDIDATE REGISTRATION BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
	        		tracker.track(studentSession, "REGISTERED THE STUDENT IN THE SESSION" , httpServletRequest);
	        		logger_.log(Constants.NORMAL_LOG_DIR, "Created the student Session: "+user); 

					
	        		logMessage = "REGISTERED THE STUDENT SESSION ";
	        		return studentSession;
	            	
	            	//return String.valueOf(candidateSession.getId());
	            }	            
	
	
	@Override
	public StudentSession persistDup(StudentForm studentForm, HttpServletRequest httpServletRequest,int eligibleCenterId){
		User user = userService.getLogedUser(httpServletRequest);
		StudentSession studentSession = null;
		
		String logMessage = "";
		String tracking = "";
		String matricule = null;
		int countFile = 0 ;
		Student student = null;
		
		Date dob = studentForm.getDob();
		java.sql.Date sDate = coreService.convertUtilToSql(dob);
		List<Person> personList = personService.findPersonByGivenNameAndSurnameAndPobAndDob(studentForm.getGivenName(), studentForm.getSurName(),studentForm.getPob(),sDate);
		
			Date today = new Date();
			LocalDate currentDate = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localDob = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			//Verification for age eligibility (age must be atleast 18)
			
			if ((localDob != null) && (currentDate != null)) {
	            int age = Period.between(localDob, currentDate).getYears();
	            //candidate's age greater or equal to 18
	            if(age >= 18){
	            	if(studentForm.getId() ==0  ){
	        			//case candidateSession does not exists yet
	        			//we create first Person instance
//	            	Person	person = new Person( studentForm.getSurName(), studentForm.getGivenName(), studentForm.getPob(), studentForm.getGender() , null,
//		        				coreService.convertUtilToSql(studentForm.getDob()),null,null,null,studentForm.getLicenseNum(),matricule) ;	
//	        		personService.createPerson(person);
	        		
	        		TrainingCenter trainingCenter= trainingCenterService.findById(studentForm.getTrainingCenter());
	        		EligibleCenter eligibleCenter = eligibleCenterService.findEligibleCenterById(eligibleCenterId);
       		
	        		//studentSession = new StudentSession(studentForm.getPvNumber(), 0, 1,eligibleCenter,countFile,student);
//	        		studentSession = new StudentSession("T", studentForm.getPvNumber(), 0, 1,eligibleCenter,trainingCenter, person,null,studentForm.getCapacities());
	        		
	        		
	        		studentSessionService.createStudentSession(studentSession);
	       
	
	    MultipartFile image=studentForm.getImage();
		MultipartFile signature=studentForm.getSignature(); 

		int lastIndex=image.getOriginalFilename().lastIndexOf(".");
 	    String extensionImage=image.getOriginalFilename().substring(lastIndex+1,image.getOriginalFilename().length());
 	
 	    lastIndex=signature.getOriginalFilename().lastIndexOf(".");
 	     String extensionSignature=signature.getOriginalFilename().substring(lastIndex+1,signature.getOriginalFilename().length());
        MultipartFile[] files=studentForm.getFile();

		  try{  	
			
			int i=1;
			for(MultipartFile file:files) {
				
 	        lastIndex=file.getOriginalFilename().lastIndexOf(".");
	    	String extension=file.getOriginalFilename().substring(lastIndex+1,file.getOriginalFilename().length());
			byte barr[]=file.getBytes();  
	        BufferedOutputStream bout=new BufferedOutputStream(  
	        		new FileOutputStream(candidateSessionFileFolder+studentSession.getId()+"_"+i+"."+extension));
	        bout.write(barr);  
	        bout.flush();  
	        bout.close();  
	        StudentSessionFile studentFile= new StudentSessionFile();
	        studentFile.setStudentSession(studentSession);
	        studentFile.setDeleted(0);
	        studentFile.setFileName(studentSession.getId()+"_"+i+"."+extension);
	        studentSessionFileService.save(studentFile);
	        i++;
			}
			  
			byte barrImage[]=studentForm.getImage().getBytes();  
	        BufferedOutputStream boutImage=new BufferedOutputStream(  
	         new FileOutputStream(candidateSessionImageFolder+studentSession.getId()+"."+extensionImage));
	        boutImage.write(barrImage);  
	        boutImage.flush();  
	        boutImage.close();  
	      
	      byte barrSignature[]=studentForm.getSignature().getBytes();  
          BufferedOutputStream boutSignature=new BufferedOutputStream(  
       		new FileOutputStream(candidateSessionSignatureFolder+studentSession.getId()+"."+extensionSignature));
           boutSignature.write(barrSignature);  
           boutSignature.flush();  
           boutSignature.close(); 
           studentSession.setPhotoAndSignature(studentSession.getId()+"."+extensionImage);
           studentSessionService.createStudentSession(studentSession);

	        }catch(Exception e){
	        	e.printStackTrace();
	        	
	        	studentSessionRepository.delete(studentSession);
	        	//return "KO";
	       }		 
         
	  		 
	        		
	        			        		
	        		//tracker.track(candidateSession, "CANDIDATE REGISTRATION BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
	        		tracker.track(studentSession, "REGISTERED THE CANDIDATE" , httpServletRequest);
	        		logger_.log(Constants.NORMAL_LOG_DIR, "Created the candidate Session: "+user); 

					
	        		logMessage = "REGISTERED THE CANDIDATE ";
	        		return studentSession;
	            	
	            	//return String.valueOf(candidateSession.getId());
	            }else {
	           
	            }
	            
	        } else {
	        	System.out.println("we have to return a message warning about the person's age being less than 18");
	            return null;
	        }
			
		}
			
		
		return studentSession;
		}
		
		
		

	@Override
	public void createStudentSession(StudentSession studentSession) {
		studentSessionRepository.save(studentSession);
		
	}
	
	
	@Override
	public void updateStudentSession(StudentSession studentSession) {
		studentSessionRepository.saveAndFlush(studentSession);

	} 
	

	@Override
	public List<StudentSession> findByEligibleCenterAndResult(EligibleCenter eligibleCenter, int result) {
		return studentSessionRepository.findByEligibleCenterAndResult(eligibleCenter,result, em);
	}

	@Override
	public List<StudentSession> findByPvResults(EligibleCenter eligibleCenter) {
		return studentSessionRepository.findByPvResults(eligibleCenter, em);
	}

	@Override
	public List<StudentSession> findByEligibleCenterAndResultOrderedByPvNumber(EligibleCenter eligibleCenter, int result) {
		return studentSessionRepository.findByEligibleCenterAndResultOrderedByPvNumber(eligibleCenter,result, em);
	}

	@Override
	public StudentSession save(StudentSession studentSession, HttpServletRequest httpServletRequest) {
		
		return studentSessionRepository.save(studentSession); 
		
	}
	
	
	@Override
	public CandidateChekerForm checkPrerequis(StudentForm studentForm){
		Date dob = studentForm.getDob();
		java.sql.Date sDate = coreService.convertUtilToSql(dob);
		List<Person> personList = personService.findPersonByGivenNameAndSurnameAndPobAndDob(studentForm.getGivenName(), studentForm.getSurName(),studentForm.getPob(),sDate);	
		  int sizePerson = personList.size() - 1;
		  	
		     List<Person> listEligiblePerson = new ArrayList<Person>(); 
		    if(personList.size()==0)
		    	return new CandidateChekerForm("new.candidate",null);
			for(int i = sizePerson ; i >= 0 ; i--) {
				int isEligible = 1;
				//List<StudentSession> canSesion = studentSessionService.findStudentSessionByPersonId(personList.get(i).getId());
				if(isEligible ==1 ) listEligiblePerson.add(personList.get(i)); 	
				}
							
				
			if(listEligiblePerson.size()==0) return new CandidateChekerForm("probability.duplicate",null);
			if(listEligiblePerson.size()==1)
			return new CandidateChekerForm("candidate.extension",listEligiblePerson.get(0));
			else return new CandidateChekerForm("many.candidate.found", null);
			
		
	

		
	}
	
	@Override
	public List<StudentSession> findByEligibleCenterAndResultAndSpeciality(EligibleCenter eligibleCenter, int result,Speciality speciality){

		return studentSessionRepository.findByEligibleCenterAndResultAndSpeciality( eligibleCenter,  result, speciality,em);
	}
	
	@Override
	public List<StudentSession> findByResultAndSpeciality(int result, Speciality speciality){
		
		return studentSessionRepository.findByResultAndSpeciality(result, speciality,em);
	}

	@Override
	public List<StudentSession> findByEligibleCenterAndResultOrderedByPerson(EligibleCenter eligibleCenter, int result) {
		return studentSessionRepository.findByEligibleCenterAndResultOrderedByPerson(eligibleCenter,result, em);
	}
	@Override
	public List<StudentSession> findByEligibleCenter(EligibleCenter eligibleCenter) {
		return studentSessionRepository.findByEligibleCenter(eligibleCenter);
	}

	@Override
	public Student findByReasonForReject(String reasonForReject) {
		// TODO Auto-generated method stub
		return studentSessionRepository.findByReasonForReject(reasonForReject);
	}



	@Override
	public StudentSession findById(String id) {
		// TODO Auto-generated method stub
		return studentSessionRepository.findById(id);
	}
	
	@Override
	public List<StudentSession> findByEligibleCenterAndResultAndSpecialityAndFinalResult(EligibleCenter eligibleCenter, int result,Speciality speciality){

		return studentSessionRepository.findByEligibleCenterAndResultAndSpecialityAndFinalResult( eligibleCenter,  result, speciality,em);
	}
	@Override
	public List<StudentSession> findByEligibleCenterAndResultAndSpecialityAndTrainingCenter(EligibleCenter eligibleCenter, int result,Speciality speciality, TrainingCenter trainingCenter){

		return studentSessionRepository.findByEligibleCenterAndResultAndSpecialityAndTrainingCenter( eligibleCenter,  result, speciality,trainingCenter,em);
	}
	
	@Override
	public List<StudentSession> findByResultAndSpecialityAndTrainingCenter(int result, Speciality speciality , TrainingCenter trainingCenter){
		return studentSessionRepository.findByResultAndSpecialityAndTrainingCenter(result, speciality , trainingCenter,em);
		
	}
	
	@Override
	public List<StudentSession> findByEligibleCenterAndResultAndSpecialityOrderBySurname(EligibleCenter eligibleCenter, int result,Speciality speciality){
		return studentSessionRepository.findByEligibleCenterAndResultAndSpecialityOrderBySurname( eligibleCenter,  result, speciality,  em);
	}


	@Override
	public List<StudentSession> findByStudent(Student student) {
		// TODO Auto-generated method stub
		return studentSessionRepository.findByStudent(student);
	}


	@Override
	public List<StudentSession> findByStudentOrderByEligibleCenterAsc(Student student) {
		// TODO Auto-generated method stub
		return studentSessionRepository.findByStudentOrderByEligibleCenterAsc(student);
	}

	@Override
	public StudentQualification addStudentHighestQualificationInformation(StudentSession studentSession,
			CNIForm cniForm, HttpServletRequest httpServletRequest) {
		System.out.println(cniForm);
		System.out.println(cniForm.getDiplomeIssuedDate());
		Date diplomeIssuedDate = null;
		try {
			diplomeIssuedDate = new SimpleDateFormat("dd/MM/yyyy").parse(cniForm.getDiplomeIssuedDate());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		StudentQualification studentQualification = new StudentQualification();
		studentQualification.setEntryCertificateName(cniForm.getDiplome());
		studentQualification.setDiplomeOption(cniForm.getDiplomeOption());
		studentQualification.setIssuedDate(coreService.convertUtilToSql(diplomeIssuedDate));
		studentQualification.setIssuedPlace(cniForm.getDiplomeIssuedPlace());
		studentQualificationService.save(studentQualification);
		return studentQualification;
	}

	@Override
	public List<StudentSession> findByEligibleCenterAndResultAndSpecialityAndTrainingCenterOrderBySurname(EligibleCenter eligibleCenter, int result,Speciality speciality, TrainingCenter trainingCenter){

		return studentSessionRepository.findByEligibleCenterAndResultAndSpecialityAndTrainingCenterOrderBySurname( eligibleCenter,  result, speciality,trainingCenter,em);
	}



	@Override
	public List<StudentSession> findByStudentSessionStatusAndEligibleCenter(StudentSessionStatus studentSessionStatus,
			EligibleCenter eligibleCenter) {
		// TODO Auto-generated method stub
		return studentSessionRepository.findByStudentSessionStatusAndEligibleCenter( studentSessionStatus,
				 eligibleCenter);
	}


}


