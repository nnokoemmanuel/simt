package ppp.simt.entity.pv;

import java.util.Date;
import java.util.Set;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;


@Entity
@Table(name = "student")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="computerization_date" , columnDefinition="datetime COMMENT 'computerization date of the student' ")
	@Temporal(TemporalType.DATE)
	private Date computerizationDate;
	
	@Column(name="reference_num", columnDefinition="varchar(255) COMMENT 'student reference number '")
	private String referenceNum;
	
	@Column(name="diplome", columnDefinition="varchar(100) COMMENT 'BACC/GCE-O-LEVEL, CAPEC, BEPC/CAP/GCE-A-LEVEL '")
	private String diplome;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="training_center_id")
    private  TrainingCenter trainingCenter;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="speciality_id")
    private  Speciality speciality;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="person_id")
    private  Person person;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
	private Set<StudentSession> studentSessions;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "student")
	private Set<Applicant> applicants;
	
	public Student() {

	}

	public Student(Date computerizationDate, Speciality speciality, String referenceNum, TrainingCenter trainingCenter,Person person) {
		super();
		this.computerizationDate = computerizationDate;
		this.speciality = speciality;
		this.referenceNum = referenceNum;
		this.trainingCenter = trainingCenter;
		this.person = person;
	}
	
	public Student(Date computerizationDate, Speciality speciality, String referenceNum, TrainingCenter trainingCenter,Person person, String diplome) {
		super();
		this.computerizationDate = computerizationDate;
		this.speciality = speciality;
		this.referenceNum = referenceNum;
		this.trainingCenter = trainingCenter;
		this.person = person;
		this.diplome = diplome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getComputerizationDate() {
		return computerizationDate;
	}

	public void setComputerizationDate(Date computerizationDate) {
		this.computerizationDate = computerizationDate;
	}

	public TrainingCenter getTrainingCenter() {
		return trainingCenter;
	}

	public void setTrainingCenter(TrainingCenter trainingCenter) {
		this.trainingCenter = trainingCenter;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public String getReferenceNum() {
		return referenceNum;
	}

	public void setReferenceNum(String referenceNum) {
		this.referenceNum = referenceNum;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Set<StudentSession> getStudentSessions() {
		return studentSessions;
	}

	public void setStudentSessions(Set<StudentSession> studentSessions) {
		this.studentSessions = studentSessions;
	}

	public Set<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(Set<Applicant> applicants) {
		this.applicants = applicants;
	}

	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	
	
	
	
   
	
	

}
