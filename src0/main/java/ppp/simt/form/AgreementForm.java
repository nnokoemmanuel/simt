package ppp.simt.form;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.pv.TrainingCenter;




public class AgreementForm {


	private int id;
	private int TrainingCenterId;
	private String authorisationNumber;
	private String issuedDate;
	private String suspensionDate;
	private int status;


	
	public AgreementForm() {
		super();
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
	


	public int getTrainingCenterId() {
		return TrainingCenterId;
	}


	public void setTrainingCenterId(int trainingCenterId) {
		TrainingCenterId = trainingCenterId;
	}


	public String getAuthorisationNumber() {
		return authorisationNumber;
	}


	public void setAuthorisationNumber(String authorisationNumber) {
		this.authorisationNumber = authorisationNumber;
	}


	public String getIssuedDate() {
		return issuedDate;
	}


	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}


	public String getSuspensionDate() {
		return suspensionDate;
	}


	public void setSuspensionDate(String suspensionDate) {
		this.suspensionDate = suspensionDate;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public Agreement convertToAgreement(){
		Agreement agreement = new Agreement();
		agreement.setAuthorisationNumber(authorisationNumber);
		agreement.setStatus(status);
		return agreement;
		
	}
	
	public static AgreementForm fromAgreement(Agreement agreement){
		AgreementForm agreementForm = new AgreementForm();
		agreementForm.setAuthorisationNumber(agreement.getAuthorisationNumber());
		agreementForm.setStatus(agreement.getStatus());
		agreementForm.setTrainingCenterId(agreement.getTrainingCenter().getId());
		return agreementForm;
		
	}
	
	
	
}
