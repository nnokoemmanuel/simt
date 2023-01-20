package ppp.simt.form;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionFile;

//public class CandidateForm extends  CandidateSessionFile{
public class StudentForm{
	private int id;
	private String surName;
	private String givenName;
	private String pob;
	private String gender;
	private String entry;
	private Date dob;
	private int pvNumber ;
	private int processed;
	//private int examResult;
    private int eligibleCenter; 
    private int trainingCenter;
    private int person;
    private int nationality;
    private Date catBdate;
    private String email;
    private String phoneNumber;
    private String licenseNum;
    private Long speciality;
    private String diplome;
    
	private MultipartFile[] file;
    private MultipartFile image;
    private MultipartFile signature; 

	public StudentForm() {
		
	}
	
	public StudentForm(MultipartFile[] file,MultipartFile image,MultipartFile signature,int id, String surName, String givenName, String pob, String gender,String entry,
						Date dob, int processed,int examResult ,int eligibleCenter,int trainingCenter,int person,int status,
						Date examDate,String examPlace,int pvNumber,int nationality,String licenseNum,Date catBdate,String email,String phoneNumber,Long speciality) {
	
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
		this.eligibleCenter=eligibleCenter;
		this.trainingCenter=trainingCenter;
		this.person=person; 
		this.pvNumber=pvNumber;
		this.nationality=nationality;
		
		this.file=file;
		this.image=image;
		this.signature=signature;
		this.licenseNum = licenseNum;
		this.catBdate = catBdate;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.speciality = speciality;
	
	}
	
	public StudentForm(MultipartFile[] file,MultipartFile image,MultipartFile signature,int id, String surName, String givenName, String pob, String gender,String entry,
			Date dob, int processed,int examResult ,int eligibleCenter,int trainingCenter,int person,int status,
			Date examDate,String examPlace,int pvNumber,int nationality,String licenseNum,Date catBdate,String email,String phoneNumber,Long speciality, String diplome) {

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
		this.eligibleCenter=eligibleCenter;
		this.trainingCenter=trainingCenter;
		this.person=person; 
		this.pvNumber=pvNumber;
		this.nationality=nationality;
		this.file=file;
		this.image=image;
		this.signature=signature;
		this.licenseNum = licenseNum;
		this.catBdate = catBdate;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.speciality = speciality;
		this.diplome= diplome;
		
	}

	
	 public MultipartFile[] getFile() {
		return file;
	}

	public void setFile(MultipartFile[] file) {
		this.file = file;
	}  
	
	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public MultipartFile getSignature() {
		return signature;
	}
	
	public void setSignature(MultipartFile signature) {
		this.signature = signature;
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

//	public int getExamResult() {
//		return examResult;
//	}
//
//	public void setExamResult(int examResult) {
//		this.examResult = examResult;
//	}

	public int getEligibleCenter() {
		return eligibleCenter;
	}

	public void setEligibleCenter(int eligibleCenter) {
		this.eligibleCenter = eligibleCenter;
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

	public StudentSessionFile toStudentSessionFile() {
		StudentSessionFile studentSessionFile=new StudentSessionFile();
		
		return studentSessionFile;
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
	
	

	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	/**
	 * Convertie un formulaire en bean
	 * @return
	 */
	public StudentSession parsetToStudentSessionBean(){
		StudentSession student = new StudentSession();
		student.setId(getId());
		student.setPvNumber(getPvNumber());
		student.setProcessed(getProcessed());
		//student.setExamResult(getExamResult());
		//candidate.setCapacities(getCapacities());
        
		//==
		//candidate.setFile(getFile());
		
		return student;
	}
	
	
	/**
	 * Convertie un entity candidate en DTO
	 * @param candidate
	 * @return
	 */
	public static StudentForm fromCandidateSessionBean(StudentSession studentSession){
		StudentForm studentForm = new StudentForm();
		studentForm.setId(studentSession.getId());
		studentForm.setPvNumber(studentSession.getPvNumber());
		studentForm.setProcessed(studentSession.getProcessed());
		//studentForm.setExamResult(studentSession.getExamResult());
		studentForm.setEligibleCenter(studentSession.getEligibleCenter().getId());
		//studentForm.setTrainingCenter(studentSession.getTrainingCenter().getId());
	
		return studentForm;
	}
	
	
	

	

	@Override
	public String toString() {
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
	
		return "CandidateForm [surName=" + surName + ", givenName=" + givenName + ", pob=" + pob+ ", gender=" + gender + 
			                 ", entry=" + entry+ ", processed=" + processed +  ", eligibleCenter=" + eligibleCenter + ", trainingCenter=" + trainingCenter + ", person=" + person + ", pvNumber=" + pvNumber + ", nationalityId=" + nationality  + "]";
	}

	
}
