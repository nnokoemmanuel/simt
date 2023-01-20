package ppp.simt.entity.production;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

import ppp.simt.entity.core.Authority;
import ppp.simt.entity.pv.StudentSession;


@Entity
@Table(name="certificate")
public class Certificate {
	
	private static final long serialVersionUID = 1L;

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id", updatable = false, nullable = false)
	 private int id;

	 @Column(name="on_process", columnDefinition="integer COMMENT 'set with student session id if attestation is pending processs and 0 if not'")
	 private int onProcess;
	 
	 @Column(name="number")
	 private String number;
	 
	 @Column(name="type")
     private String type;
	 
	 @Column(name="printed_date")
	 private java.util.Date printedDate;
	 
	 @Column(name="jury_number")
	 private String juryNumber;
	 
	 @OneToOne
	 @JoinColumn(name = "student_session_id", referencedColumnName = "id")
	 private StudentSession studentSession;

//	@ManyToOne
//	@JoinColumn(name="student_session_id")
//	private StudentSession studentSession;

	 @ManyToOne
	 private Authority authority;

	@OneToOne(mappedBy="certificate")
	private ProfessionalCard professionalCard;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "certificate")
	private Set<Application> applications;

	@Column(name="is_printed", columnDefinition="integer COMMENT '1 si imprimer et 0 si non'")
	private int isPrinted;

	 public StudentSession getStudentSession() {
		return studentSession;
	}

	public void setStudentSession(StudentSession studentSession) {
		this.studentSession = studentSession;
	}

	public Certificate() {
		 super();
		 
	 }
   
	
	
	
	public Certificate(int onProcess, String number, String type, Date printedDate, String juryNumber,
			StudentSession studentSession, Authority authority, ProfessionalCard professionalCard, int isPrinted) {
		super();
		this.onProcess = onProcess;
		this.number = number;
		this.type = type;
		this.printedDate = printedDate;
		this.juryNumber = juryNumber;
		this.studentSession = studentSession;
		this.authority = authority;
		this.professionalCard = professionalCard;
		this.isPrinted = isPrinted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public java.util.Date getPrintedDate() {
		return printedDate;
	}

	public void setPrintedDate(java.util.Date printedDate) {
		this.printedDate = printedDate;
	}

	public String getJuryNumber() {
		return juryNumber;
	}

	public void setJuryNumber(String juryNumber) {
		this.juryNumber = juryNumber;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public ProfessionalCard getProfessionalCard() {
		return professionalCard;
	}


	public void setProfessionalCard(ProfessionalCard professionalCard) {
		this.professionalCard = professionalCard;
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}

	public int getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(int isPrinted) {
		this.isPrinted = isPrinted;
	}

	public int getOnProcess() {
		return onProcess;
	}

	public void setOnProcess(int onProcess) {
		this.onProcess = onProcess;
	}
	
}
