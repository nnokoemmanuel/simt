package ppp.simt.service.core;

import java.util.Date;
import java.util.List;

import ppp.simt.entity.core.Country;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.user.User;
import ppp.simt.form.CNIForm;


public interface PersonService {
	public List<Person> findAll();
	public void createPerson(Person person);
	public void updatePerson(Person person);
	public Person findPersonById(int personId);
	public void autoUpdatePersonFields( Person person ,String surName, String givenName, String pob, String gender,java.sql.Date dob, String email,
			String phoneNumber,Country nationality,String licenseNum, String matricule,Date catBComputerizationDate);
	public Person findPersonByGivenName(String givenName);
	public Person findPersonBySurName(String surName);
	public Person findPersonByPob(String pob);
	public Person findPersonByDob(Date dob);
	public List<Person> findPersonByGivenNameAndSurnameAndPobAndDob(String givenName, String surName,String pob, java.sql.Date dob);
	public Person findPersonByLicenseNum(String licenseNum);
	public List<Person> findPersonByGivenNameAndSurnameAndPobAndDobAndGender(String givenName, String surName,String pob, java.sql.Date dob,String gender);
	public void deletePerson(Person person) ;
	public Person addPersonIdInfoToPerson(Person person, CNIForm cniForm) ;
	

}
