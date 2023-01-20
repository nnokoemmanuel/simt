package ppp.simt.entity.pv;

import java.util.Date;
import java.util.Set;

import ppp.simt.entity.core.Region;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.pv.ExamCenter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "agreement")
public class Agreement {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="authorisation_number")
	private String authorisationNumber;
	
	@ManyToOne
    @JoinColumn(name ="training_center_id")
    private  TrainingCenter trainingCenter;
	
	@Column(name="issued_date", columnDefinition="datetime not null COMMENT 'agreement issued date'")
	private java.util.Date issuedDate;
	
	@Column(name="suspension_date", columnDefinition="datetime null COMMENT 'agreement suspension date'")
	private java.util.Date suspensionDate;
	
	@Column(name="status" , columnDefinition="integer(11) not null COMMENT 'agreement state 0 if suspended 1 if active'")
	private int status;

	
	public Agreement() {
		super();
	}
    
	
    public Agreement(int id, String authorisationNumber, TrainingCenter trainingCenter, Date issuedDate,
			Date suspensionDate, int status) {
		super();
		this.id = id;
		this.authorisationNumber = authorisationNumber;
		this.trainingCenter = trainingCenter;
		this.issuedDate = issuedDate;
		this.suspensionDate = suspensionDate;
		this.status = status;
	}
    public Agreement( String authorisationNumber, TrainingCenter trainingCenter, Date issuedDate,
			Date suspensionDate, int status) {
		
		this.authorisationNumber = authorisationNumber;
		this.trainingCenter = trainingCenter;
		this.issuedDate = issuedDate;
		this.suspensionDate = suspensionDate;
		this.status = status;
	}

	public String getAuthorisationNumber() {
		return authorisationNumber;
	}

	public void setAuthorisationNumber(String authorisationNumber) {
		this.authorisationNumber = authorisationNumber;
	}

	public TrainingCenter getTrainingCenter() {
		return trainingCenter;
	}

	public void setTrainingCenter(TrainingCenter trainingCenter) {
		this.trainingCenter = trainingCenter;
	}

	public java.util.Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(java.util.Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public java.util.Date getSuspensionDate() {
		return suspensionDate;
	}

	public void setSuspensionDate(java.util.Date suspensionDate) {
		this.suspensionDate = suspensionDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return "Division [id=" + id + ", authorisationNumber=" + authorisationNumber + ", status=" + status + "]";
	}

}
