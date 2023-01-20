package ppp.simt.form;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class ApplicantForm {
	
	private int id;
	private String surName;
	private String givenName;
	private String pob;
	private String gender;
	private String entry;
	private Date dob;
	private int pvNumber ;
	private int processed;
    private int entranceEligibleCenter; 
    private int trainingCenter;
    private int person;
    private int nationality;
    private Date catBdate;
    private String email;
    private String phoneNumber;
    private String licenseNum;
    private Long speciality;
	private int trainingCenterChoice1;
	private int trainingCenterChoice2;
	private int trainingCenterChoice3;
	private String diplome;
    
public ApplicantForm() {
		
	}
	
	public ApplicantForm(int id, String surName, String givenName, String pob, String gender,String entry,
						Date dob, int processed,int examResult ,int entranceEligibleCenter,int trainingCenter,int person,int status,
						Date examDate,String examPlace,int pvNumber,int nationality,String licenseNum,Date catBdate,String email,String phoneNumber,Long speciality,String diplome) {
	
		super();
		this.id = id;
		this.surName=surName;
		this.givenName=givenName;
		this.pob=pob;
		this.gender=gender;
		this.entry=entry;
		this.dob=dob;
		this.processed=processed;
		//this.examResult=examResult;
		this.entranceEligibleCenter=entranceEligibleCenter;
		this.trainingCenter=trainingCenter;
		this.person=person; 
		this.pvNumber=pvNumber;
		this.nationality=nationality;
		
		this.licenseNum = licenseNum;
		this.catBdate = catBdate;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.speciality = speciality;
		this.diplome = diplome;
	
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

	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getPvNumber() {
		return pvNumber;
	}

	public void setPvNumber(int pvNumber) {
		this.pvNumber = pvNumber;
	}

	public int getProcessed() {
		return processed;
	}

	public void setProcessed(int processed) {
		this.processed = processed;
	}

	public int getEntranceEligibleCenter() {
		return entranceEligibleCenter;
	}

	public void setEntranceEligibleCenter(int entranceEligibleCenter) {
		this.entranceEligibleCenter = entranceEligibleCenter;
	}

	public int getTrainingCenter() {
		return trainingCenter;
	}

	public void setTrainingCenter(int trainingCenter) {
		this.trainingCenter = trainingCenter;
	}

	public int getPerson() {
		return person;
	}

	public void setPerson(int person) {
		this.person = person;
	}

	public int getNationality() {
		return nationality;
	}

	public void setNationality(int nationality) {
		this.nationality = nationality;
	}

	public Date getCatBdate() {
		return catBdate;
	}

	public void setCatBdate(Date catBdate) {
		this.catBdate = catBdate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	public Long getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Long speciality) {
		this.speciality = speciality;
	}

	public int getTrainingCenterChoice1() {
		return trainingCenterChoice1;
	}

	public void setTrainingCenterChoice1(int trainingCenterChoice1) {
		this.trainingCenterChoice1 = trainingCenterChoice1;
	}

	public int getTrainingCenterChoice2() {
		return trainingCenterChoice2;
	}

	public void setTrainingCenterChoice2(int trainingCenterChoice2) {
		this.trainingCenterChoice2 = trainingCenterChoice2;
	}

	public int getTrainingCenterChoice3() {
		return trainingCenterChoice3;
	}

	public void setTrainingCenterChoice3(int trainingCenterChoice3) {
		this.trainingCenterChoice3 = trainingCenterChoice3;
	}
}
