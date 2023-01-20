package ppp.simt.service.archive;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Authority;
import ppp.simt.entity.core.Country;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.user.User;
import ppp.simt.form.ArchiveForm;
import ppp.simt.repository.archive.ArchiveRepository;
import ppp.simt.service.core.AuthorityService;
import ppp.simt.service.core.CategoryService;
import ppp.simt.service.core.CoreService;
import ppp.simt.service.core.CountryService;
import ppp.simt.service.core.PersonService;
import ppp.simt.service.core.SpecialityService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Service("ArchiveService")
public class ArchiveServiceImpl implements ArchiveService {

	@Autowired
	private ArchiveRepository archiveRepository;
	
	@Autowired
	private CoreService coreService;
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private CountryService countryService;
			
	@Autowired
	private PersonService personService;
			
	@Autowired
	private CategoryService categoryService;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ArchiveTrackingService archiveTrackingService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SpecialityService  specialityService;
	
	
	@Autowired
	private AuthorityService  authorityService;
	
	
	
	@Override
	public List<Archive> findAll() {
		return archiveRepository.findAll();
	}

	
	
	@Override
	public void createArchive(Archive archive) {
		archiveRepository.save(archive);
		
	}
	
	@Override
	public void updateArchive(Archive archive) { 
		archiveRepository.saveAndFlush(archive);
		
	}

	
	@Override
	public Archive findArchiveById(int archiveId) {
		
		return archiveRepository.findById(archiveId);
	}
	
	/**
	 * Convertie un formulaire en bean
	 * @return
	 */
	@Override
	public Archive persistArchiveBean(ArchiveForm archiveForm,HttpServletRequest httpServletRequest) throws ParseException{
		 User user = userService.getLogedUser(httpServletRequest);
		Archive archive = null;
		if(archiveForm.getId() ==0  ){
			//case archive does not exists yet
			//we create first Person instance
		Speciality speciality = specialityService.findById(archiveForm.getSpeciality());
		Person person = personService.findPersonByLicenseNum(archiveForm.getLicenseNum());
		Date formDate=new SimpleDateFormat("dd/MM/yyyy").parse(archiveForm.getDob());
    	if(person == null) {
    	 Country personCountry= countryService.findCountryById(31);
		 person = new Person( archiveForm.getSurName(), archiveForm.getGivenName(), archiveForm.getPob(), archiveForm.getGender(),coreService.convertUtilToSql(coreService.stringToDate(archiveForm.getDob())),
				 null,archiveForm.getPhoneNumber(),personCountry,archiveForm.getLicenseNum(),null,archiveForm.getCatBdate()) ;
		personService.createPerson(person);
		String matricule = studentService.generateMatricule(person.getId(),speciality.getAbbreviation());
		person.setMatricule(matricule);
		}
		
		//personService.createPerson(person);
		Country issuedCountry= countryService.findCountryById(archiveForm.getIssuedCountry());
		archive = new Archive(new Date(),coreService.convertSqlToUtil(coreService.stringToDate(archiveForm.getIssuedDate())) , archiveForm.getIssuedPlace(), issuedCountry, person, "REGISTERED" , coreService.convertSqlToUtil(coreService.stringToDate(archiveForm.getExamDate())) ,archiveForm.getExamPlace(), archiveForm.getPvNumber() ,archiveForm.getArchiveNumber(), archiveForm.getService(),archiveForm.getGrade(),archiveForm.getFinalAverage(),speciality);
		Authority authority = authorityService.findAuthorityById(Integer.valueOf(archiveForm.getAuthority()));
		archive.setAuthority(authority);
		archiveService.createArchive(archive);
		
		tracker.track(archive, "REGISTERED THE ARCHIVE" , httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "Initiates the Archive Registration: "+user);
		
		
		return archive;
		}else{
			System.out.println("we edit here ----yes");
			Archive archiveRetrieved = archiveService.findArchiveById(archiveForm.getId());
			Person  personAttachedToRetrievedArchive = archiveRetrieved.getPerson();
			Country personNationality = countryService.findCountryById(archiveForm.getNationality());
            //personService.autoUpdatePersonFields( personAttachedToRetrievedArchive ,archiveForm.getSurName(), archiveForm.getGivenName(), archiveForm.getPob(), archiveForm.getGender(), null ,null, archiveForm.getNic(), coreService.convertUtilToSql(coreService.stringToDate(archiveForm.getDob())),null, archiveForm.getOccupation(), null ,archiveForm.getPhoneNumber(),personNationality);
			personService.updatePerson(personAttachedToRetrievedArchive);
			Country issuedCountry= countryService.findCountryById(archiveForm.getIssuedCountry());
			//archiveService.autoUpdateArchiveFields(archiveRetrieved, coreService.convertSqlToUtil(coreService.stringToDate(archiveForm.getIssuedDate())) ,archiveForm.getIssuedPlace() , issuedCountry , personAttachedToRetrievedArchive ,coreService.convertSqlToUtil(coreService.stringToDate(archiveForm.getExamDate())), archiveForm.getExamPlace() , archiveForm.getPvNumber() , null,archiveForm.getArchiveNumber() );
			archiveService.updateArchive(archiveRetrieved);
			
			
			tracker.track(archiveRetrieved, "EDITED THE ARCHIVE " , httpServletRequest);
    		logger_.log(Constants.NORMAL_LOG_DIR, "Initiates the Archive Edition: "+user);
		
			
			return archiveRetrieved;
		}
	}
	/*@Override
	public List<Archive> findArchiveByPeriods(java.util.Date startDate, java.util.Date endDate) {
		// TODO Auto-generated method stub
		return archiveRepository.findByRegistrationDateBetween(startDate,endDate);
		
	}



	@Override
	public void autoUpdateArchiveFields(Archive archive, Date issuedDate, String issuedPlace, Country issuedCountry,
			Person person, Date examDate, String examPlace, int pvNumber, Date expiryDate) {
		// TODO Auto-generated method stub

	}


	@Override
	public List<Archive> findArchiveByPeriodsAndStatus(Date startDate, Date endDate, int status) {
		// TODO Auto-generated method stub
		return archiveRepository.findByRegistrationDateBetweenAndStatus(startDate,endDate, status);

	}*/
	/*@Override
	public List<Archive> findByStatus(int status) {
		// TODO Auto-generated method stub
		return archiveRepository.findByStatus(status);
	}*/
	
	@Override
	public List<Archive> findByStatus(String status) {
		// TODO Auto-generated method stub
		return archiveRepository.findByStatus(status);
	}

	@Override
	public void manageArchiveReset(Archive archive, User user){

		if( archive!= null ){
			archive.setStatus("REGISTERED");
			updateArchive(archive);
		}
	}

	@Override
	public void manageArchiveValide(Archive archive,User user){

		if( archive!= null ){
			archive.setStatus("VALIDATED");
			updateArchive(archive);
		}
	}
	@Override
	public void manageArchiveSuspend(Archive archive,User user){

		if( archive!= null ){
			archive.setStatus("SUSPENDED");
			updateArchive(archive);
		}
	}



	@Override
	public Archive save(Archive archive) {
		// TODO Auto-generated method stub
		return archiveRepository.save(archive);
	}
	
	@Override
	public List<Archive> findByStatusAndRegistrationDateBetween(String archiveStatus,java.util.Date startDate, java.util.Date enDate) {
		// TODO Auto-generated method stub
		return archiveRepository.findByStatusAndRegistrationDateBetween( archiveStatus, startDate,  enDate);
	}
	
	@Override
	public List<Archive> findByArchiveNumber(String archiveNumber){
		// TODO Auto-generated method stub
		return archiveRepository.findByArchiveNumber( archiveNumber);
	}
	
	@Override
	public List<Archive> findByRegistrationDateBetween(java.util.Date startDate, java.util.Date enDate){
		return archiveRepository.findByRegistrationDateBetween( startDate,enDate);
	}
	
	@Override
	public Archive findByPerson(Person person){
		return archiveRepository.findByPerson(person);
	}
	
	@Override
	public void deleteArchive(Archive archive) {
		archiveRepository.delete(archive);
	}



	


}
