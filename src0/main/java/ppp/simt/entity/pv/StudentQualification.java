package ppp.simt.entity.pv;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name = "student_qualification")
public class StudentQualification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="entry_certificate_name")
	private String entryCertificateName;
	
	@Column(name="issued_date")
	private java.sql.Date  issuedDate;
	
	@Column(name="issued_place")
	private String issuedPlace;
	
	@Column(name="diplome_option")
	private String diplomeOption;
	
	@OneToOne(mappedBy="studentQualification")
	private StudentSession studentSession;
	
	
	public StudentQualification() {

	}

	
	public StudentQualification(String entryCertificateName, Date issuedDate, String issuedPlace,
			String diplomeOption) {
		super();
		this.entryCertificateName = entryCertificateName;
		this.issuedDate = issuedDate;
		this.issuedPlace = issuedPlace;
		this.diplomeOption = diplomeOption;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getEntryCertificateName() {
		return entryCertificateName;
	}


	public void setEntryCertificateName(String entryCertificateName) {
		this.entryCertificateName = entryCertificateName;
	}


	public java.sql.Date getIssuedDate() {
		return issuedDate;
	}


	public void setIssuedDate(java.sql.Date issuedDate) {
		this.issuedDate = issuedDate;
	}


	public String getIssuedPlace() {
		return issuedPlace;
	}


	public void setIssuedPlace(String issuedPlace) {
		this.issuedPlace = issuedPlace;
	}


	public String getDiplomeOption() {
		return diplomeOption;
	}


	public void setDiplomeOption(String diplomeOption) {
		this.diplomeOption = diplomeOption;
	}


	@Override
	public String toString() {
		return "StudentQualification [entryCertificateName=" + entryCertificateName + ", issuedDate=" + issuedDate
				+ ", issuedPlace=" + issuedPlace + ", diplomeOption=" + diplomeOption + "]";
	}

    
	

	
}
