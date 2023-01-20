package ppp.simt.entity.production;

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

import ppp.simt.entity.pv.StudentSession;

@Entity
@Table(name = "application_transcript")
public class ApplicationTranscript {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;

	@Column(name="issue_date")
	private Date issueDate;

	@Column(name="matricule", columnDefinition="varchar(255) not null COMMENT 'matricule'")
	private String matricule;

	@Column(name="on_process", columnDefinition="integer COMMENT 'à valeur > 0 signifie la presence de un processus qui est appliaction id sur la carte professionnel et à 0 si non '")
	private int onProcess;

	@OneToOne
	@JoinColumn(name = "student_session_id", referencedColumnName = "id")
	private StudentSession studentSession;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "transcript")
	private Set<Application> applications;
	
	@Column(name="is_printed", columnDefinition="integer COMMENT '1 si imprimer et 0 si non'")
	private int isPrinted;
	
	public ApplicationTranscript(){
		super();
	}
	
	

	public ApplicationTranscript(Date issueDate, String matricule, int onProcess, StudentSession studentSession,
			int isPrinted) {
		super();
		this.issueDate = issueDate;
		this.matricule = matricule;
		this.onProcess = onProcess;
		this.studentSession = studentSession;
		this.isPrinted = isPrinted;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
	
	public int getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(int isPrinted) {
		this.isPrinted = isPrinted;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public int getOnProcess() {
		return onProcess;
	}

	public void setOnProcess(int onProcess) {
		this.onProcess = onProcess;
	}

	public StudentSession getStudentSession() {
		return studentSession;
	}

	public void setStudentSession(StudentSession studentSession) {
		this.studentSession = studentSession;
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}
	
	

}
