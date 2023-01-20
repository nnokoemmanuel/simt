package ppp.simt.service.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.core.Country;
import ppp.simt.entity.core.Person;
import ppp.simt.form.CNIForm;
import ppp.simt.repository.core.PersonRepository;


@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private CoreService coreService;
	
	@Autowired
	private EntityManager em;

	@Override
	public List<Person> findAll() {
		return personRepository.findAll();
	}

	@Override
	public void createPerson(Person person) {
		personRepository.save(person);
		
	}

	@Override
	public void updatePerson(Person person) {
		personRepository.saveAndFlush(person);
		
	}
	
	@Override
	public Person findPersonById(int personId) {
		
		return personRepository.findById(personId);
	}
	
	@Override	
	public void autoUpdatePersonFields(Person person ,String surName, String givenName, String pob, String gender,java.sql.Date dob,String email,
			String phoneNumber,Country nationality, String licenseNum,String matricule,Date catBComputerizationDate) {
		
		person.setSurName(surName);
		person.setGivenName(givenName);
		person.setPob(pob);
		person.setGender(gender);
		person.setDob(dob);
		person.setEmail(email);
		person.setPhoneNumber(phoneNumber);
		person.setNationality(nationality);
		person.setLicenseNum(licenseNum);
		person.setMatricule(matricule);
		person.setCatBComputerizationDate(catBComputerizationDate);
	
	}

	@Override
	public Person findPersonByGivenName(String givenName) {
		return personRepository.findByGivenName(givenName);
	}



	@Override
	public Person findPersonBySurName(String surName) {
		return personRepository.findBySurName(surName);
	}



	@Override
	public Person findPersonByPob(String pob) {
		return personRepository.findByPob(pob);
	}



	@Override
	public Person findPersonByDob(Date dob) {
		return personRepository.findByDob(dob);
	}
	
	@Override
	public List<Person> findPersonByGivenNameAndSurnameAndPobAndDob(String givenName, String surName,String pob, java.sql.Date dob) {
		return personRepository.findPersonByGivenNameAndSurnameAndPobAndDob(givenName, surName, pob, dob,em);
	}
	
	@Override
	public List<Person> findPersonByGivenNameAndSurnameAndPobAndDobAndGender(String givenName, String surName,String pob, java.sql.Date dob,String gender) {
		return personRepository.findPersonByGivenNameAndSurnameAndPobAndDobAndGender(givenName, surName, pob, dob,gender,em);
	}



	@Override
	public Person findPersonByLicenseNum(String licenseNum) {
		// TODO Auto-generated method stub
		return personRepository.findPersonByLicenseNum(licenseNum);
	}
	
	@Override
	public void deletePerson(Person person) {
		personRepository.delete(person);
	}

	@Override
	public Person addPersonIdInfoToPerson(Person person, CNIForm cniForm) {
		Date dob = null;
		try {
			dob = new SimpleDateFormat("dd/MM/yyyy").parse(cniForm.getDobCni());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date issuedDate = null;
		try {
			issuedDate = new SimpleDateFormat("dd/MM/yyyy").parse(cniForm.getIssuedDateCni());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		person.setNic(cniForm.getNic());
		person.setSurnameCni(cniForm.getSurnameCni());
		person.setGivenNameCni(cniForm.getGivenNameCni());
		person.setPobCni(cniForm.getPobCni());
		person.setGenderCni(cniForm.getGenderCni());
		person.setFatherCni(cniForm.getFatherCni());
		person.setMotherCni(cniForm.getMotherCni());
		person.setProfessionCni(cniForm.getProfessionCni());
		person.setAddressCni(cniForm.getAddressCni());
		person.setHeightCni(cniForm.getHeightCni());
		person.setLanguage(cniForm.getLanguage());
		
		person.setDobCni(coreService.convertUtilToSql(dob));
		person.setIssuedDateCni(coreService.convertUtilToSql(issuedDate));
		updatePerson(person);
		
		return person;
	}
	
	



}
