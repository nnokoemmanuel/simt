package ppp.simt.entity.pv;

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

import ppp.simt.entity.core.Authority;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.InSlip;


@Entity
@Table(name = "eligible_center")
public class EligibleCenter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@ManyToOne
    @JoinColumn(name ="exam_session_id")
    private  ExamSession examSession;
	
	@ManyToOne
    @JoinColumn(name ="exam_center_id")
    private  ExamCenter examCenter;
	
	@OneToMany(mappedBy = "eligibleCenter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<StudentSession> studentSessions;
	
	@ManyToOne
    @JoinColumn(name ="eligible_center_status_id")
    private  EligibleCenterStatus eligibleCenterStatus;
	
	
    @Column(name="jury_number")
    private String juryNumber;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "eligibleCenter")
	private Set<InSlip> inSlip;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public ExamSession getExamSession() {
		return examSession;
	}

	public void setExamSession(ExamSession examSession) {
		this.examSession = examSession;
	}

	public ExamCenter getExamCenter() {
		return examCenter;
	}

	public void setExamCenter(ExamCenter examCenter) {
		this.examCenter = examCenter;
	}
	
	public Set<StudentSession> getStudentSessions(){
		return studentSessions;
	}
	
	public void setStudentSessions(Set<StudentSession> studentSessions) {
		this.studentSessions = studentSessions;
	}
	
	
	public String getJuryNumber() {
		return juryNumber;
	}

	public void setJuryNumber(String juryNumber) {
		this.juryNumber = juryNumber;
	}

	public EligibleCenterStatus getEligibleCenterStatus() {
		return eligibleCenterStatus;
	}

	public void setEligibleCenterStatus(EligibleCenterStatus eligibleCenterStatus) {
		this.eligibleCenterStatus = eligibleCenterStatus;
	}

	public Set<InSlip> getInSlip() {
		return inSlip;
	}

	public void setInSlip(Set<InSlip> inSlip) {
		this.inSlip = inSlip;
	}
}
