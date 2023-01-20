package ppp.simt.form;

import java.util.List;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.production.ApplicationTranscript;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.production.ProcessType;
import ppp.simt.entity.production.ProfessionalCard;

public class ConsultRecordForm {
	private List<StudentSession> studentSessions;
	private List<Student> student;
	private Person person;
	private String photo;
	private String lastCandidatePhoto;
	private String lastCandidateSignature;
	private String photoSource;
	private String lastCertificate;
	private Certificate Certificate;
	private Archive archive;
	private ApplicationTranscript applicationTranscript;
	private ProfessionalCard professionalCard;
	private String recordOrigin; //carry the entity from which a record is made
	private String latestSpeciality;
	private List<ProcessType> possibleProcessType;
	
public ConsultRecordForm() {
	 super();
}
public List<StudentSession> getStudentSessions() {
	return studentSessions;
}

public void setStudentSessions(List<StudentSession> studentSessions) {
	this.studentSessions = studentSessions;
}

public List<ProcessType> getPossibleProcessType() {
	return possibleProcessType;
}
public void setPossibleProcessType(List<ProcessType> possibleProcessType) {
	this.possibleProcessType = possibleProcessType;
}

public Certificate getCertificate() {
	return Certificate;
}
public void setCertificate(Certificate certificate) {
	Certificate = certificate;
}

public String getLatestSpeciality() {
	return latestSpeciality;
}
public void setLatestSpeciality(String latestSpeciality) {
	this.latestSpeciality = latestSpeciality;
}
public Archive getArchive() {
	return archive;
}
public void setArchive(Archive archive) {
	this.archive = archive;
}

public ApplicationTranscript getApplicationTranscript() {
	return applicationTranscript;
}
public void setApplicationTranscript(ApplicationTranscript applicationTranscript) {
	this.applicationTranscript = applicationTranscript;
}
public ProfessionalCard getProfessionalCard() {
	return professionalCard;
}
public void setProfessionalCard(ProfessionalCard professionalCard) {
	this.professionalCard = professionalCard;
}
public String getRecordOrigin() {
	return recordOrigin;
}
public void setRecordOrigin(String recordOrigin) {
	this.recordOrigin = recordOrigin;
}
public List<Student> getStudent() {
	return student;
}
public void setStudent(List<Student> student) {
	this.student = student;
}
public Person getPerson() {
	return person;
}
public void setPerson(Person person) {
	this.person = person;
}
public String getPhoto() {
	return photo;
}
public void setPhoto(String photo) {
	this.photo = photo;
}
public String getLastCandidatePhoto() {
	return lastCandidatePhoto;
}
public void setLastCandidatePhoto(String lastCandidatePhoto) {
	this.lastCandidatePhoto = lastCandidatePhoto;
}
public String getLastCandidateSignature() {
	return lastCandidateSignature;
}
public void setLastCandidateSignature(String lastCandidateSignature) {
	this.lastCandidateSignature = lastCandidateSignature;
}
public String getPhotoSource() {
	return photoSource;
}
public void setPhotoSource(String photoSource) {
	this.photoSource = photoSource;
}
public String getLastCertificate() {
	return lastCertificate;
}
public void setLastCertificate(String lastCertificate) {
	this.lastCertificate = lastCertificate;
}

}

