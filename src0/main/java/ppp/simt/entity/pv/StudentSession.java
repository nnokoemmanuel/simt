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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.ApplicationTranscript;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.production.ProfessionalCard;
import ppp.simt.entity.tracking.StudentSessionTracking; 

 
@Entity
@Table(name = "student_session")
public class StudentSession { 
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="pvNumber", columnDefinition="integer(11) default -1 COMMENT 'pv number of the candidate session '")
	private int pvNumber;
	
	@Column(name="processed", columnDefinition="integer(11) default 0 COMMENT 'tells if the candidate session is already processed 1 if yes and 0 if not '")
	private int processed;
	
	@ManyToOne
    @JoinColumn(name ="eligible_center_id")
    private  EligibleCenter eligibleCenter; 

	@Column(name="photo_and_signature")
	private String photoAndSignature;

//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "studentSession")
//	private Set<Certificate> certificate;
	
	@OneToOne(mappedBy="studentSession")
	private Certificate certificate;
	
	@OneToOne(mappedBy="studentSession") 
	private ApplicationTranscript applicationTranscript;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "studentSession")
	private Set<StudentSessionFile> studentSessionFiles;
	 
	@OneToMany(mappedBy = "studentSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<StudentSessionTracking> studentSessionTrackings;
	
	@ManyToOne
    @JoinColumn(name ="student_id")
    private  Student student;
	
	@ManyToOne
    @JoinColumn(name ="student_session_status_id")
    private  StudentSessionStatus studentSessionStatus;
	
	@Column(name="count_file" , columnDefinition="integer default 0 COMMENT 'number of files uploaded it is used for display purpose '")
	private int countFile;

	@Column(name="reason_for_reject") 
	private String reasonForReject;
	
	@Column(name="final_result", columnDefinition="float COMMENT 'la moyenne finale obtenue par le candidat apres deliberation ' default 0")
	private double note;
	
	
	
	@OneToOne
	@JoinColumn(name = "studentQualification_id", referencedColumnName = "id")
	private StudentQualification studentQualification;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="speciality_id")
	private Speciality speciality;

	public StudentSession(int pvNumber, int processed, StudentSessionStatus studentSessionStatus,EligibleCenter eligibleCenter,int countFile, Student student,String reasonForReject) {
		super();
	
		this.pvNumber = pvNumber;
		this.processed = processed;
		this.eligibleCenter = eligibleCenter;
		this.countFile = countFile;
		this.student = student;
		this.studentSessionStatus = studentSessionStatus;
		this.reasonForReject= reasonForReject;
	}

	public StudentSession(){
		 
	}

	public StudentSession(int id, int pvNumber, int processed, StudentSessionStatus studentSessionStatus ,EligibleCenter eligibleCenter,int countFile, Student student) {
		this.id = id;
		this.pvNumber = pvNumber;
		this.processed = processed;
		this.eligibleCenter = eligibleCenter;
		this.student = student;
		this.studentSessionStatus = studentSessionStatus;
		this.countFile = countFile;
			
	}
	
	public StudentSession(int id, String photoAndSignature, int pvNumber, int processed,  StudentSessionStatus studentSessionStatus ,EligibleCenter eligibleCenter,int countFile, Student student ) {
		this.id = id;
		this.pvNumber = pvNumber;
		this.processed = processed;
		this.eligibleCenter = eligibleCenter;
		this.student = student;
		this.studentSessionStatus = studentSessionStatus;
		this.countFile = countFile;
		this.photoAndSignature = photoAndSignature;
	}
	public StudentSession(int id, String photoAndSignature, int pvNumber, int processed,  StudentSessionStatus studentSessionStatus ,EligibleCenter eligibleCenter,int countFile, Student student ,  String studentSurname) {
		this.id = id;
		this.pvNumber = pvNumber;
		this.processed = processed;
		this.eligibleCenter = eligibleCenter;
		this.student = student;
		this.studentSessionStatus = studentSessionStatus;
		this.countFile = countFile;
		this.photoAndSignature = photoAndSignature;
		this.student.getPerson().setSurName(studentSurname);
	} 
	public StudentSession(int id, String photoAndSignature, int pvNumber, int processed,  StudentSessionStatus studentSessionStatus ,EligibleCenter eligibleCenter,int countFile, Student student,float note  ) {
		this.id = id;
		this.pvNumber = pvNumber;
		this.processed = processed;
		this.eligibleCenter = eligibleCenter;
		this.student = student;
		this.studentSessionStatus = studentSessionStatus;
		this.countFile = countFile;
		this.photoAndSignature = photoAndSignature;
		this.note = note;
	}

	public StudentSession(int id, String photoAndSignature, int pvNumber, int processed,  StudentSessionStatus studentSessionStatus ,EligibleCenter eligibleCenter,int countFile, Student student ,String trainingCentername,String studentSurname, String studentGivenName) {
		this.id = id;
		this.pvNumber = pvNumber;
		this.processed = processed;
		this.eligibleCenter = eligibleCenter;
		this.student = student;
		this.studentSessionStatus = studentSessionStatus;
		this.countFile = countFile;
		this.photoAndSignature = photoAndSignature;
		this.student.getTrainingCenter().setName(trainingCentername);
		this.student.getPerson().setSurName(studentSurname);
		this.student.getPerson().setGivenName(studentGivenName);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id; 
	}	
	
	public double getNote() {
		return note;
	}



	public void setNote(double note) {
		this.note = note;
	}

	public int getPvNumber() {
		return pvNumber;
	}

	public void setPvNumber(int pvNumber) {
		this.pvNumber = pvNumber;
	}

	public int getProcessed() {
		return processed;
	}

	public void setProcessed(int processed) {
		this.processed = processed;
	}
	
	public EligibleCenter getEligibleCenter() {
		return eligibleCenter;
	}
	
	public void setEligibleCenter(EligibleCenter eligibleCenter) {
		this.eligibleCenter =eligibleCenter;
	}

	public String getPhotoAndSignature() {
		return photoAndSignature;
	}

	public void setPhotoAndSignature(String photoAndSignature) {
		this.photoAndSignature = photoAndSignature;
	}
	public Set<StudentSessionFile> getStudentSessionFiles() {
		return studentSessionFiles;
	}
	public Set<StudentSessionTracking> getStudentSessionTrackings() {
		return studentSessionTrackings;
	}

	public void setStudentSessionTrackings(Set<StudentSessionTracking> studentSessionTrackings) {
		this.studentSessionTrackings = studentSessionTrackings;
	}

	public int getCountFile() {
		return countFile;
	}

	public void setCountFile(int countFile) {
		this.countFile = countFile;
	}

	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}

	public void setStudentSessionFiles(Set<StudentSessionFile> studentSessionFiles) {
		this.studentSessionFiles = studentSessionFiles;
	}

	public StudentSessionStatus getStudentSessionStatus() {
		return studentSessionStatus;
	}

	public void setStudentSessionStatus(StudentSessionStatus studentSessionStatus) {
		this.studentSessionStatus = studentSessionStatus;
	}
	
	

	public String getReasonForReject() {
		return reasonForReject;
	}

	public void setReasonForReject(String reasonForReject) {
		this.reasonForReject = reasonForReject;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public ApplicationTranscript getApplicationTranscript() {
		return applicationTranscript;
	}

	public void setApplicationTranscript(ApplicationTranscript applicationTranscript) {
		this.applicationTranscript = applicationTranscript;
	}

	public StudentQualification getStudentQualification() {
		return studentQualification;
	}

	public void setStudentQualification(StudentQualification studentQualification) {
		this.studentQualification = studentQualification;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}
}
