package ppp.simt.entity.applicant;

import java.util.Set;

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
@Table(name = "entrance_eligible_center")
public class EntranceEligibleCenter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@OneToMany(mappedBy = "entranceEligibleCenter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Applicant> applicants;
	
	@ManyToOne
    @JoinColumn(name ="entrance_exam_center_id")
    private  EntranceExamCenter entranceExamCenter;
	
	@ManyToOne
    @JoinColumn(name ="entrance_exam_session_id")
    private  EntranceExamSession entranceExamSession;
	
	@ManyToOne
    @JoinColumn(name ="entrance_eligible_center_status_id")
    private  EntranceEligibleCenterStatus entranceEligibleCenterStatus;

	public EntranceEligibleCenter(Set<Applicant> applicants, EntranceExamCenter entranceExamCenter,
			EntranceExamSession entranceExamSession, EntranceEligibleCenterStatus entranceEligibleCenterStatus) {
		super();
		this.applicants = applicants;
		this.entranceExamCenter = entranceExamCenter;
		this.entranceExamSession = entranceExamSession;
		this.entranceEligibleCenterStatus = entranceEligibleCenterStatus;
	}
    
	public EntranceEligibleCenter() {
	}  
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(Set<Applicant> applicants) {
		this.applicants = applicants;
	}

	public EntranceExamCenter getEntranceExamCenter() {
		return entranceExamCenter;
	}

	public void setEntranceExamCenter(EntranceExamCenter entranceExamCenter) {
		this.entranceExamCenter = entranceExamCenter;
	}

	public EntranceExamSession getEntranceExamSession() {
		return entranceExamSession;
	}

	public void setEntranceExamSession(EntranceExamSession entranceExamSession) {
		this.entranceExamSession = entranceExamSession;
	}

	public EntranceEligibleCenterStatus getEntranceEligibleCenterStatus() {
		return entranceEligibleCenterStatus;
	}

	public void setEntranceEligibleCenterStatus(EntranceEligibleCenterStatus entranceEligibleCenterStatus) {
		this.entranceEligibleCenterStatus = entranceEligibleCenterStatus;
	}

	
	

}
