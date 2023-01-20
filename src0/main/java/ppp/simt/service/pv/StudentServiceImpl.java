package ppp.simt.service.pv;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import ppp.simt.entity.core.Country;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.user.User;
import ppp.simt.form.StudentForm;
import ppp.simt.repository.pv.StudentRepository;
import ppp.simt.service.core.CoreService;
import ppp.simt.service.core.CountryService;
import ppp.simt.service.core.PersonService;
import ppp.simt.service.core.SpecialityService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Service("studentService")
public class StudentServiceImpl implements StudentService {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private CoreService coreService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SpecialityService specialityService;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	EntityManager em; 
	
	@Value("${student.diplome.folder}")
	private String diplomeFolder;	
	
	@Override
	public Student persistStudent(StudentForm studentForm, HttpServletRequest httpServletRequest, Person personne){
		User user = userService.getLogedUser(httpServletRequest);
		String logMessage = "";

	            	Country personNationality = countryService.findCountryById(studentForm.getNationality());
	            	Speciality speciality = specialityService.findById(studentForm.getSpeciality());
	            	Person person;
	            	Student student = null ;
	            	if(personne == null) {
	        		 person = new Person( studentForm.getSurName(), studentForm.getGivenName(), studentForm.getPob(), studentForm.getGender(),coreService.convertUtilToSql(studentForm.getDob()),
	        				 			  studentForm.getEmail(),studentForm.getPhoneNumber(),personNationality,studentForm.getLicenseNum(),null,studentForm.getCatBdate()) ;
	        		personService.createPerson(person);

	    
	        		String matricule = studentService.generateMatricule(person.getId(),speciality.getAbbreviation());
	        		person.setMatricule(matricule);
	        		}else {
	        			 person = personne;
	        		}

	            	TrainingCenter trainingCenter= user.getTrainingCenter();

	            	java.util.Date now = new java.util.Date();

	            	//Date registration = formatter.format(studentComputerization.getTime());
	            	String filename = studentForm.getDiplome();
	  	          String[] fileNameArray = filename.split("\\.");
	  	          String extension =  fileNameArray[fileNameArray.length-1];
	  	          
	        		student = new Student(now,speciality,null,trainingCenter,person, null);
	        		studentService.createStudent(student);	
	        		String referenceNum = studentService.generateReferenceNum(student.getId(), trainingCenter) ;
	        		student.setReferenceNum(referenceNum);
	        		student.setDiplome(student.getId()+"."+extension);
	        		
	        		
	  			  try{  
	  			  byte barr[]=studentForm.getDiplome().getBytes();  
	  		        BufferedOutputStream bout=new BufferedOutputStream(  
	  		        new FileOutputStream(diplomeFolder+student.getId()+"."+extension));
	  		        bout.write(barr);  
	  		        bout.flush();  
	  		        bout.close();  
	  		        }catch(Exception e){System.out.println(e);}
	        		
	        		
	        		
	        		studentService.updateStudent(student);
	 	  		 
	        		tracker.track(student, "REGISTERED THE STUDENT" , httpServletRequest);
	        		logger_.log(Constants.NORMAL_LOG_DIR, "Created the student: "+user); 

					
	        		logMessage = "REGISTERED THE STUDENT ";
	        		return student;
	            	
	            	//return String.valueOf(candidateSession.getId());
	            }	            

	@Override
	public void createStudent(Student student) {
		studentRepository.save(student);
		
	}

	@Override
	public String generateMatricule(int personId,String speciality) {
		String str = String.format("%05d", personId);
		String matricule = null;
		if (speciality.equals("MAE")) {
			matricule = "MT"+str+"MA";
		} else if (speciality.equals("DPCSR")) {
			matricule = "MT"+str+"DP";
		} else {
			matricule = "MT"+str+"IP";
		}
		
		
		return matricule;
	}

	@Override
	public String generateReferenceNum(int studentId, TrainingCenter trainingCenter) {
		String abbreviation = trainingCenter.getAbbreviation();
		String str = String.format("%05d", studentId);
		String referenceNum = abbreviation+str;
		
		return referenceNum;
	}

	@Override
	public List<Student> findAll() {
		return studentRepository.findAll();
	}
	
	
	@Override
	public void updateStudent(Student student) {
		studentRepository.saveAndFlush(student);

	}
	
	@Override
	public List<Student> findByComputerizationDateBetweenAndTrainingCenter(Date startDate, Date enDate, TrainingCenter trainingCenter) {
		// TODO Auto-generated method stub
		return studentRepository.findByComputerizationDateBetweenAndTrainingCenter(startDate, enDate, trainingCenter);
	}

	@Override
	public List<Student> findByPerson(Person person){
		return studentRepository.findByPerson(person);
	}
	
	@Override
	public Student findByReferenceNum(String matricule){
		return studentRepository.findByReferenceNum(matricule);
	}

	@Override
	public Student findById(int student) {
		// TODO Auto-generated method stub
		return studentRepository.findById(student);
	}

	@Override
	public List<Student> findByTrainingCenter(TrainingCenter trainingCenter) {
		return studentRepository.findByTrainingCenter(trainingCenter);
	}

	@Override
	public List<Student> findByTrainingCenterAndSpeciality(TrainingCenter trainingCenter, Speciality speciality) {
		return studentRepository.findByTrainingCenterAndSpeciality(trainingCenter, speciality,em);
	}
	
	@Override
	public List<Student> findByComputerizationDateBetween(Date startDate, Date enDate) {
		// TODO Auto-generated method stub
		return studentRepository.findByComputerizationDateBetween(startDate, enDate);
	}

	@Override
	public List<Student> findBySpeciality(long speciality) {
		// TODO Auto-generated method stub
		return studentRepository.findBySpeciality(speciality);
	}
}
