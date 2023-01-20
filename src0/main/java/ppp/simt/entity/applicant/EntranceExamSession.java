package ppp.simt.entity.applicant;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "entrance_exam_session")
public class EntranceExamSession {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="session_date", columnDefinition="DATE  COMMENT 'session date'")
	@Temporal(TemporalType.DATE)
	private Date sessionDate;
	
	
	@Column(name="create_at", columnDefinition="datetime  COMMENT 'session creation date'")
	private java.util.Date createdOn;
	
	@Column(name="status")
	private String status;
	
	@OneToMany(mappedBy = "entranceExamSession", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<EntranceEligibleCenter> entranceEligibleCenters = new HashSet<EntranceEligibleCenter>();

	public EntranceExamSession(Date sessionDate, Date createdOn, String status,
			Set<EntranceEligibleCenter> entranceEligibleCenters) {
		super();
		this.sessionDate = sessionDate;
		this.createdOn = createdOn;
		this.status = status;
		this.entranceEligibleCenters = entranceEligibleCenters;
	}
	
	public EntranceExamSession() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getSessionDate() {
		return sessionDate;
	}

	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}

	public java.util.Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(java.util.Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<EntranceEligibleCenter> getEntranceEligibleCenters() {
		return entranceEligibleCenters;
	}

	public void setEntranceEligibleCenters(Set<EntranceEligibleCenter> entranceEligibleCenters) {
		this.entranceEligibleCenters = entranceEligibleCenters;
	}
	
	


}
