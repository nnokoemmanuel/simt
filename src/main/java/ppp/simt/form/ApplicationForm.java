package ppp.simt.form;

import javax.persistence.Column;

public class ApplicationForm  {

	private int id;
	//fields for Application informations
	private int authority;
	private int processtype;
	private String inslipReference;
	private int  applicationNumber;
    private int sourceId;
    private int applicationStatus;
	private String applicantPhoneNumber;
	private String sourceEntity;
	private String formSerialNumber;
	private String language;
	

	public ApplicationForm() {
		
	}
	
	


	public ApplicationForm(int authority, int processtype, String inslipReference, int applicationNumber,
			String formSerialNumber, int sourceId, int applicationStatus , String applicantPhoneNumber, String sourceEntity,String language) {

		super();
		this.authority = authority;
		this.processtype = processtype;
		this.inslipReference = inslipReference;
		this.applicationNumber = applicationNumber;

		this.formSerialNumber = formSerialNumber;
		this.sourceId = sourceId;
		this.applicationStatus = applicationStatus;
		this.applicantPhoneNumber = applicantPhoneNumber;
		this.sourceEntity = sourceEntity;
		this.language = language;

	}





	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}


   

	public String getLanguage() {
		return language;
	}




	public void setLanguage(String language) {
		this.language = language;
	}




	public int getAuthority() {
		return authority;
	}




	public void setAuthority(int authority) {
		this.authority = authority;
	}




	public int getProcesstype() {
		return processtype;
	}




	public void setProcesstype(int processtype) {
		this.processtype = processtype;
	}




	public String getInslipReference() {
		return inslipReference;
	}




	public void setInslipReference(String inslipReference) {
		this.inslipReference = inslipReference;
	}




	public int getApplicationNumber() {
		return applicationNumber;
	}




	public void setApplicationNumber(int applicationNumber) {
		this.applicationNumber = applicationNumber;
	}




	public int getSourceId() {
		return sourceId;
	}



	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getApplicationStatus() {
		return applicationStatus;
	}




	public void setApplicationStatus(int applicationStatus) {
		this.applicationStatus = applicationStatus;
	}


	public String getApplicantPhoneNumber() {
		return applicantPhoneNumber;
	}




	public void setApplicantPhoneNumber(String applicantPhoneNumber) {
		this.applicantPhoneNumber = applicantPhoneNumber;

	}

	public String getSourceEntity() {
		return sourceEntity;
	}




	public void setSourceEntity(String sourceEntity) {
		this.sourceEntity = sourceEntity;
	}
    
	
	public String getFormSerialNumber() {
		return formSerialNumber;
	}




	public void setFormSerialNumber(String formSerialNumber) {
		this.formSerialNumber = formSerialNumber;
	}


  


	@Override
	public String toString() {
	
	
		return "ApplicationForm [authority=" + authority + ", processtype=" + processtype + ", inslipReference=" + inslipReference+ ", applicationNumber=" + applicationNumber + 
				             ", sourceEntity=" + sourceEntity+
			                 ", sourceId=" + sourceId + ", applicationStatus=" + applicationStatus +"formSerialNumber="+ formSerialNumber + "]";
	}

	
}
