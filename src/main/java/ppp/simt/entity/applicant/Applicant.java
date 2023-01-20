package ppp.simt.entity.applicant;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.Student;

@Entity
@Table(name = "applicant")
public class Applicant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="computerization_date" , columnDefinition="datetime COMMENT 'computerization date of the applicant' ")
	@Temporal(TemporalType.DATE)
	private Date computerizationDate;
	

	@Column(name="exam_result", columnDefinition="varchar(100) COMMENT 'applicant exam result, REGISTERED, PASSED THEORY, FAILED THEORY, FAILED PRACTICALS, PASSED PRACTICALS '")

	private String result;
	
	@Column(name="diplome", columnDefinition="varchar(100) COMMENT 'BACC/GCE OR CAPEC '")
	private String diplome;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="speciality_id")
    private  Speciality speciality;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="person_id")
    private  Person person;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="student_id")
    private  Student student;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="entrance_eligible_center_id")
    private  EntranceEligibleCenter entranceEligibleCenter;
    
	@Column(name="pvNumber", columnDefinition="integer(11) COMMENT 'pv number of the candidate session '")
	private int pvNumber;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "applicant")
	private List<TrainingCenterChoice> trainingCenterChoice;

	@Column(name="final_result", columnDefinition="float COMMENT 'la moyenne obtenu par le candidat apres deliberation ' default 0")
	private float note;

	public Applicant() {
		super();
	}

	public Applicant(Date computerizationDate, String result, Speciality speciality, Person person, 
			EntranceEligibleCenter entranceEligibleCenter,int note) {
		super();
		this.computerizationDate = computerizationDate;
		this.result = result;
		this.speciality = speciality;
		this.person = person;
		this.entranceEligibleCenter = entranceEligibleCenter;
	}
	
	public Applicant(Date computerizationDate, String result, Speciality speciality, Person person, 
			EntranceEligibleCenter entranceEligibleCenter,int note, String diplome) {
		super();
		this.computerizationDate = computerizationDate;
		this.result = result;
		this.speciality = speciality;
		this.person = person;
		this.entranceEligibleCenter = entranceEligibleCenter;
		this.diplome = diplome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getPvNumber() {
		return pvNumber;
	}

	public void setPvNumber(int pvNumber) {
		this.pvNumber = pvNumber;
	}


	public Date getComputerizationDate() {
		return computerizationDate;
	}

	public void setComputerizationDate(Date computerizationDate) {
		this.computerizationDate = computerizationDate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public EntranceEligibleCenter getEntranceEligibleCenter() {
		return entranceEligibleCenter;
	}

	public void setEntranceEligibleCenter(EntranceEligibleCenter entranceEligibleCenter) {
		this.entranceEligibleCenter = entranceEligibleCenter;
	}

	public List<TrainingCenterChoice> getTrainingCenterChoice() {
		return trainingCenterChoice;
	}

	public void setTrainingCenterChoice(List<TrainingCenterChoice> trainingCenterChoice) {
		this.trainingCenterChoice = trainingCenterChoice;
	}

	public float getNote() {
		return note;
	}

	public void setNote(float note) {
		this.note = note;
	}

	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}
	
	
}
