package ppp.simt.form;

import ppp.simt.entity.core.Person;

public class CandidateChekerForm {

	String message;
	Person person;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public CandidateChekerForm(String message, Person person) {
		super();
		this.message = message;
		this.person = person;
	}
	
	
}
