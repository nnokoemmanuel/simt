package ppp.simt.entity.pv;
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
@Table(name = "exam_session")
public class ExamSession {
	
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
	
	@OneToMany(mappedBy = "examSession", cascade = CascadeType.ALL, orphanRemoval=true)
    private Set<EligibleCenter> eligibleCenters = new HashSet<EligibleCenter>();

	
	public ExamSession() {
		super();
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


	public Set<EligibleCenter> getEligibleCenters() {
		return eligibleCenters;
	}
	
	public void setEligibleCenters(Set<EligibleCenter> eligibleCenters) {
		this.eligibleCenters = eligibleCenters;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}
	


}
