package ppp.simt.form;

import java.util.Date;

public class ArchiveForm  {

	private int id;
	//fields for person informations
	private String surName;
	private String givenName;
	private String pob;
	private String gender;
	private String nic;
	private String dob;
	private String phoneNumber;
	private String issuedDate;
	private String issuedPlace;
    private int issuedCountry; 
    private String status;
    private String examDate;
    private String examPlace;
    private int pvNumber;
    private String archiveNumber;
    private int nationality; 
    private Date registrationDate;
    private String fileName;
    private String service;
    private String authority;
    private String grade;
    private float finalAverage;
    private String licenseNum;
    private Date catBdate;
    private Long speciality;

	public ArchiveForm() {
		
	}
	
	public ArchiveForm(int id, String surName, String givenName, String pob, String gender,String nic,String dob, String phoneNumber,String issuedDate,String issuedPlace,int issuedCountry,
			String archiveNumber,String status,String examDate,String examPlace,int pvNumber,int nationality,
			Date registrationDate, String fileName, String service, String authority, String grade, float finalAverage,String licenseNum,Date catBdate,Long speciality) {
	
		
		super();
		this.id = id;
		this.surName=surName;
		this.givenName=givenName;
		this.pob=pob;
		this.gender=gender;
		this.nic=nic;
		this.dob=dob;
		this.phoneNumber=phoneNumber;
		this.issuedDate=issuedDate;
		this.issuedPlace=issuedPlace;
		this.issuedCountry=issuedCountry; 
		this.status=status;
		this.examDate=examDate;
		this.examPlace=examPlace;
		this.pvNumber=pvNumber;
		this.archiveNumber=archiveNumber;
		this.nationality=nationality;
		this.registrationDate = registrationDate;
		this.fileName=fileName;
		this.service=service;
		this.authority=authority;
		this.grade=grade;
		this.finalAverage=finalAverage;
		this.licenseNum = licenseNum;
		this.catBdate = catBdate;
		this.speciality = speciality;
	
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(String issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getIssuedPlace() {
		return issuedPlace;
	}

	public void setIssuedPlace(String issuedPlace) {
		this.issuedPlace = issuedPlace;
	}

	public int getIssuedCountry() {
		return issuedCountry;
	}

	public void setIssuedCountry(int issuedCountry) {
		this.issuedCountry = issuedCountry;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getExamPlace() {
		return examPlace;
	}

	public void setExamPlace(String examPlace) {
		this.examPlace = examPlace;
	}

	public int getPvNumber() {
		return pvNumber;
	}

	public void setPvNumber(int pvNumber) {
		this.pvNumber = pvNumber;
	}
	
	public String getArchiveNumber() {
		return archiveNumber;
	}

	public void setArchiveNumber(String archiveNumber) {
		this.archiveNumber = archiveNumber;
	}
	
	public void setNationality(int nationality) {
		this.nationality = nationality;
	}

	public int getNationality() {
		return nationality;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public float getFinalAverage() {
		return finalAverage;
	}

	public void setFinalAverage(float finalAverage) {
		this.finalAverage = finalAverage;
	}

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	public Date getCatBdate() {
		return catBdate;
	}

	public void setCatBdate(Date catBdate) {
		this.catBdate = catBdate;
	}

	public Long getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Long speciality) {
		this.speciality = speciality;
	}

	@Override
	public String toString() {
	
	
		return "ArchiveForm [surName=" + surName + ", givenName=" + givenName + ", pob=" + pob+ ", gender=" + gender + 
			                 ", nic=" + nic+ ", dob=" + dob+
			                 ", phoneNumber=" + phoneNumber + 
			                 ", issuedDate=" + issuedDate + ", issuedPlace=" + issuedPlace + ", issuedCountryId=" + issuedCountry + ", statusId=" + status + 
			                 ", examDate=" + examDate + ", examPlace=" + examPlace + ", pvNumber=" + pvNumber + ", examPlace=" + examPlace +
			                 ", nationalityId=" + nationality  + "]";
	}

	
}
