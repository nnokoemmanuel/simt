package ppp.simt.form;


import java.util.Date;
import ppp.simt.entity.pv.TrainingCenter;




public class TrainingCenterForm {


	private int id;
	private int divisionId;
	private String name;
	private String creationDate;
	private String founder;
	private String owner;
	private String ownerContact;
	private String location;
	private int maxStudent;
	private String agreementAuthorisationNumber;
	private String agreementIssuedDate;
	private String specialities;


	


	public TrainingCenterForm() {
		super();
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
    
	public String getOwnerContact() {
		return ownerContact;
	}


	public void setOwnerContact(String ownerContact) {
		this.ownerContact = ownerContact;
	}


	public String getSpecialities() {
		return specialities;
	}


	public void setSpecialities(String specialities) {
		this.specialities = specialities;
	}


	public int getDivisionId() {
		return divisionId;
	}


	public void setDivisionId(int divisionId) {
		this.divisionId = divisionId;
	}




	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}


	public String getFounder() {
		return founder;
	}


	public void setFounder(String founder) {
		this.founder = founder;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public int getMaxStudent() {
		return maxStudent;
	}


	public void setMaxStudent(int maxStudent) {
		this.maxStudent = maxStudent;
	}


	public String getAgreementAuthorisationNumber() {
		return agreementAuthorisationNumber;
	}


	public void setAgreementAuthorisationNumber(String agreementAuthorisationNumber) {
		this.agreementAuthorisationNumber = agreementAuthorisationNumber;
	}


	public String getAgreementIssuedDate() {
		return agreementIssuedDate;
	}


	public void setAgreementIssuedDate(String agreementIssuedDate) {
		this.agreementIssuedDate = agreementIssuedDate;
	}




	public TrainingCenter convertToTrainingCenter(){
		TrainingCenter trainingCenter = new TrainingCenter();
		trainingCenter.setAbbreviation(null);
		trainingCenter.setFounder(founder);
		trainingCenter.setLocation(location);
		trainingCenter.setMaxStudent(maxStudent);
		trainingCenter.setName(name);
		trainingCenter.setOwner(owner);

		return trainingCenter;
		
	}
	
	public static TrainingCenterForm fromTrainingCenter(TrainingCenter trainingCenter){
		TrainingCenterForm trainingCenterForm = new TrainingCenterForm();
		trainingCenterForm.setDivisionId(trainingCenter.getDivision().getId());

		trainingCenterForm.setFounder(trainingCenter.getFounder());
		trainingCenterForm.setLocation(trainingCenter.getLocation());
		trainingCenterForm.setMaxStudent(trainingCenter.getMaxStudent());
		trainingCenterForm.setOwner(trainingCenter.getOwner());
		trainingCenterForm.setName(trainingCenter.getName());
		return trainingCenterForm;
		
	}
	
	
	
}
