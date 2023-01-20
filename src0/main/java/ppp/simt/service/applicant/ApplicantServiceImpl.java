package ppp.simt.service.applicant;


import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.applicant.TrainingCenterChoice;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.form.ApplicantForm;
import ppp.simt.repository.applicant.ApplicantRepository;

import javax.servlet.http.HttpServletRequest;
import ppp.simt.entity.core.Country;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.user.User;

import ppp.simt.service.core.CoreService;
import ppp.simt.service.core.CountryService;
import ppp.simt.service.core.PersonService;
import ppp.simt.service.core.SpecialityService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.service.pv.TrainingCenterService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;

@Service("applicantService")
public class ApplicantServiceImpl implements ApplicantService{

	@Autowired
	private ApplicantRepository applicantRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private CoreService coreService;
	
	@Autowired
	private ApplicantService applicantService;
	
	@Autowired
	private SpecialityService specialityService;
	

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired 
	private Logger_ logger_;

	@Autowired
	EntityManager em;

	@Autowired
	private TrainingCenterService trainingCenterService;

	@Autowired
	private TrainingCenterChoiceService trainingCenterChoiceService;


	@Override
	public List<Applicant> findAll() {
		return applicantRepository.findAll();
	}
	
	@Override
	public void createApplicant(Applicant applicant) {
		applicantRepository.save(applicant);
		
	}
	
	
	@Override
	public void updateApplicant(Applicant applicant) {
		applicantRepository.saveAndFlush(applicant);

	}
	
	@Override
	public List<Applicant> findByEntranceEligibleCenterAndResultOrderedByPerson(EntranceEligibleCenter eligibleCenter, String result) {
		return applicantRepository.findByEntranceEligibleCenterAndResultOrderedByPerson(eligibleCenter,result, em);
	}
	
	@Override
	public List<Applicant> findByPvResults(EntranceEligibleCenter eligibleCenter) {
		return applicantRepository.findByPvResults(eligibleCenter, em);
	}

	public List<Applicant> findByEntranceEligibleCenterAndResultAndSpecialityAndStudent(EntranceEligibleCenter eligibleCenter, Speciality speciality){
		return applicantRepository.findByEntranceEligibleCenterAndResultAndSpecialityAndStudent(eligibleCenter, speciality, em);
	}

	@Override
	public Applicant persistApplicant(ApplicantForm applicantForm, HttpServletRequest httpServletRequest, Person personne,EntranceEligibleCenter entranceEligibleCenter){
		User user = userService.getLogedUser(httpServletRequest);
		String logMessage = "";

	            	Country personNationality = countryService.findCountryById(applicantForm.getNationality());
	            	Speciality speciality = specialityService.findById(applicantForm.getSpeciality());
	            	Person person;
	            	Applicant applicant = null ;
	            	if(personne == null) {
	        		 person = new Person( applicantForm.getSurName(), applicantForm.getGivenName(), applicantForm.getPob(), applicantForm.getGender(),coreService.convertUtilToSql(applicantForm.getDob()),
	        				 applicantForm.getEmail(),applicantForm.getPhoneNumber(),personNationality,applicantForm.getLicenseNum().trim(),null,applicantForm.getCatBdate()) ;
	        		personService.createPerson(person);
	        		String matricule = studentService.generateMatricule(person.getId(),speciality.getAbbreviation());
	        		person.setMatricule(matricule);
	        		}else {
	        			 person = personne;
	        		}
	            	
	            	java.util.Date now = new java.util.Date();
	        		applicant = new Applicant(now,"REGISTERED",speciality,person,entranceEligibleCenter,0, applicantForm.getDiplome() );
	        		applicantService.createApplicant(applicant);

	        		TrainingCenterChoice trainingCenterChoice1= new TrainingCenterChoice();
					TrainingCenter trainingCenter = trainingCenterService.findById(applicantForm.getTrainingCenterChoice1());
					trainingCenterChoice1.setTrainingCenter(trainingCenter);
					trainingCenterChoice1.setApplicant(applicant);
					trainingCenterChoice1.setOrder("First");
					trainingCenterChoiceService.save(trainingCenterChoice1);
					System.out.println("ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"+applicantForm.getTrainingCenterChoice2());
					//if (applicantForm.getTrainingCenterChoice2() != null) {
						TrainingCenterChoice trainingCenterChoice2 = new TrainingCenterChoice();
						trainingCenter = trainingCenterService.findById(applicantForm.getTrainingCenterChoice2());
						trainingCenterChoice2.setTrainingCenter(trainingCenter);
						trainingCenterChoice2.setApplicant(applicant);
						trainingCenterChoice2.setOrder("Second");
						trainingCenterChoiceService.save(trainingCenterChoice2);
					//}
					//if (applicantForm.getTrainingCenterChoice3() != null) {
						TrainingCenterChoice trainingCenterChoice3 = new TrainingCenterChoice();
						trainingCenter = trainingCenterService.findById(applicantForm.getTrainingCenterChoice3());
						trainingCenterChoice3.setTrainingCenter(trainingCenter);
						trainingCenterChoice3.setApplicant(applicant);
						trainingCenterChoice3.setOrder("Third");
						trainingCenterChoiceService.save(trainingCenterChoice3);
				//	}
	        		tracker.track(applicant, "REGISTERED THE APPLICANT" , httpServletRequest);
	        		logger_.log(Constants.NORMAL_LOG_DIR, "Created the applicant: "+user); 
					
	        		logMessage = "REGISTERED THE APPLICANT ";
	        		return applicant;
	            	
	            }

	@Override
	public List<Applicant> findByEntranceEligibleCenterAndResultAndSpeciality(EntranceEligibleCenter eligibleCenter, String result, Speciality speciality) {
		return applicantRepository.findByEntranceEligibleCenterAndResultAndSpeciality ( eligibleCenter,  result, speciality, em);
	}

	@Override
	public Applicant findById(int applicant) {
		return applicantRepository.findById(applicant);
	}

	@Override
	public List<Applicant> findByEntranceEligibleCenter(EntranceEligibleCenter entranceEligibleCenter) {
		return applicantRepository.findByEntranceEligibleCenter(entranceEligibleCenter);
	}
	@Override
	public List<Applicant> findByEntranceEligibleCenterAndResult(EntranceEligibleCenter entranceEligibleCenter, String result) {
		return applicantRepository.findByEntranceEligibleCenterAndResult(entranceEligibleCenter,result);
	}

	@Override
	public List<Applicant> findByEntranceEligibleCenterAndResultAndStudentExists(
			EntranceEligibleCenter entranceEligibleCenter, String result) {

		return applicantRepository.findByEntranceEligibleCenterAndResultAndStudentExists(entranceEligibleCenter, result, em);
	}

	@Override
	public List<Applicant> findByEntranceEligibleCenterAndSpeciality(EntranceEligibleCenter entranceEligibleCenter,
			Speciality speciality) {
		return applicantRepository.findByEntranceEligibleCenterAndSpeciality(entranceEligibleCenter, speciality, em);
	}

	@Override
	public List<Applicant> findByEntranceEligibleCenterAndResultOrderedByNote(EntranceEligibleCenter eligibleCenter,
			String result) {
		// TODO Auto-generated method stub
		return applicantRepository.findByEntranceEligibleCenterAndResultOrderedByNote(eligibleCenter, result, em);
	}

}



