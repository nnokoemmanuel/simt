package ppp.simt.entity.core;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.pv.Student;

@Entity
@Table(name = "person")
public class Person {
	
	public Person(){

    }
	
	public Person( String surName, String givenName, String pob, String gender,java.sql.Date dob,String email,
			String phoneNumber,Country nationality, String licenseNum,String matricule,Date catBComputerizationDate) {
		super();
		this.surName = surName;
		this.givenName = givenName;
		this.pob = pob;
		this.gender = gender;
		this.dob = dob;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.nationality = nationality;
		this.licenseNum = licenseNum;
		this.matricule = matricule;
		this.catBComputerizationDate=catBComputerizationDate;
	}



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="surname")
	private String surName;
	
	@Column(name="given_name")
	private String givenName;
	
	@Column(name="pob")
	private String pob;
	
	@Column(name="gender")
	private String gender;
	
	@Column(name="nic")
	private String nic;
	
	@Column(name="dob")
	private java.sql.Date dob;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phoneNumber")
	private String phoneNumber;
	
	@Column(name="photo")
	private String photo;
	
	@Column(name="signature")
	private String signature;
	

	@Column(name="language")
	private String language;
	
	@OneToOne
    @JoinColumn(name ="nationality_id")
	private Country nationality;
	
	@Column(name="cat_b_computerization_date" , columnDefinition="datetime COMMENT 'computerization date of the student ofcandidate category B' ")
	@Temporal(TemporalType.DATE)
	private Date catBComputerizationDate;
	
	@Column(name="license_num", columnDefinition="varchar(15) COMMENT 'student license number '")
	private String licenseNum;
	
	@Column(name="matricule", columnDefinition="varchar(12) COMMENT 'student matricule number '")
	private String matricule;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
	private Set<Student> students;

	@OneToOne(mappedBy="person")
	private Archive archive;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
	private Set<Applicant> applicants;
	
	@Column(name="surname_cni")
	private String surnameCni;
	
	@Column(name="given_name_cni")
	private String givenNameCni;
	
	@Column(name="pob_cni")
	private String pobCni;
	
	@Column(name="dob_cni")
	private java.sql.Date  dobCni;
	
	@Column(name="gender_cni")
	private String genderCni;
	
	@Column(name="father_cni")
	private String fatherCni;
	
	@Column(name="mother_cni")
	private String motherCni;
	
	@Column(name="profession_cni")
	private String professionCni;
	
	@Column(name="address_cni")
	private String addressCni;
	
	@Column(name="height_cni")
	private String heightCni;
	
	@Column(name="issued_date_cni")
	private java.sql.Date  issuedDateCni;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getGivenName() {
		return givenName;
	}


	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}


	public String getSurName() {
		return surName;
	}


	public void setSurName(String surName) {
		this.surName = surName;
	}


	public java.sql.Date  getDob() {
		return dob;
	}


	public void setDob(java.sql.Date  dob) {
		this.dob = dob;
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

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}


	public String getEmail() {
		return email;
	}
	
	public String getNic() {
		return nic;
	}


	public void setNic(String nic) {
		this.nic = nic;
	}
   

	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public Country getNationality() {
		return nationality;
	}


	public void setNationality(Country nationality) {
		this.nationality = nationality;
	}
	
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	} 

	public String getLicenseNum() {
		return licenseNum;
	}

	public void setLicenseNum(String licenseNum) {
		this.licenseNum = licenseNum;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Date getCatBComputerizationDate() {
		return catBComputerizationDate;
	}

	public void setCatBComputerizationDate(Date catBComputerizationDate) {
		this.catBComputerizationDate = catBComputerizationDate;
	}

	@Override
	public String toString() {
		Format formatter = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dobUtil=convertSqlToUtil(dob);
		String dobString = formatter.format(dobUtil);

		return "Person [surName=" + surName + ", givenName=" + givenName + ", pob=" + pob+ ", gender=" + gender + 
			                 ", nic=" + nic+ ", dob=" + dobString+
			                 ", phoneNumber=" + phoneNumber + ", email=" + email+  "]";
	}
	
	private java.util.Date convertSqlToUtil(java.sql.Date sDate) {
        java.util.Date uDate = new java.util.Date(sDate.getTime());
        return uDate;
    }

	public Set<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(Set<Applicant> applicants) {
		this.applicants = applicants;
	}


	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}

	public String getSurnameCni() {
		return surnameCni;
	}

	public void setSurnameCni(String surnameCni) {
		this.surnameCni = surnameCni;
	}

	public String getGivenNameCni() {
		return givenNameCni;
	}

	public void setGivenNameCni(String givenNameCni) {
		this.givenNameCni = givenNameCni;
	}

	public String getPobCni() {
		return pobCni;
	}

	public void setPobCni(String pobCni) {
		this.pobCni = pobCni;
	}

	public java.sql.Date getDobCni() {
		return dobCni;
	}

	public void setDobCni(java.sql.Date dobCni) {
		this.dobCni = dobCni;
	}

	public String getGenderCni() {
		return genderCni;
	}

	public void setGenderCni(String genderCni) {
		this.genderCni = genderCni;
	}

	public String getFatherCni() {
		return fatherCni;
	}

	public void setFatherCni(String fatherCni) {
		this.fatherCni = fatherCni;
	}

	public String getMotherCni() {
		return motherCni;
	}

	public void setMotherCni(String motherCni) {
		this.motherCni = motherCni;
	}

	public String getProfessionCni() {
		return professionCni;
	}

	public void setProfessionCni(String professionCni) {
		this.professionCni = professionCni;
	}

	public String getAddressCni() {
		return addressCni;
	}

	public void setAddressCni(String addressCni) {
		this.addressCni = addressCni;
	}

	public String getHeightCni() {
		return heightCni;
	}

	public void setHeightCni(String heightCni) {
		this.heightCni = heightCni;
	}

	public java.sql.Date getIssuedDateCni() {
		return issuedDateCni;
	}

	public void setIssuedDateCni(java.sql.Date issuedDateCni) {
		this.issuedDateCni = issuedDateCni;
	}
	
	
}
