package ppp.simt.entity.core;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.configuration.SpecialityPrerequisite;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.pv.EligibleSpeciality;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;

@Entity
@Table(name = "speciality")
public class Speciality {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="name_in_english")
	private String nameEnglish;
	
	@Column(name="abbreviation")
	private String abbreviation;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality")
	private Set<Student> students;
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality")
	private Set<EligibleSpeciality> eligibleSpeciality;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality")
	private Set<Applicant> applicants;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality")
	private Set<Application> application;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality")
	private Set<Archive> archives;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality")
	private Set<StudentSession> studentSessions;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality")
	private Set<SpecialityPrerequisite> specialityPrerequisites;
	
    public Speciality(){
		
	}

	public Speciality(Long id, String name, String abbreviation) {
		super();
		this.id = id;
		this.name = name;
		this.abbreviation = abbreviation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
	
	public String getNameEnglish() {
		return nameEnglish;
	}

	public void setNameEnglish(String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Set<EligibleSpeciality> getEligibleSpeciality() {
		return eligibleSpeciality;
	}

	public void setEligibleSpeciality(Set<EligibleSpeciality> eligibleSpeciality) {
		this.eligibleSpeciality = eligibleSpeciality;
	}

	public Set<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(Set<Applicant> applicants) {
		this.applicants = applicants;
	}

	public Set<Application> getApplication() {
		return application;
	}

	public void setApplication(Set<Application> application) {
		this.application = application;
	}

	public Set<Archive> getArchives() {
		return archives;
	}

	public void setArchives(Set<Archive> archives) {
		this.archives = archives;
	}

	public Set<StudentSession> getStudentSessions() {
		return studentSessions;
	}

	public void setStudentSessions(Set<StudentSession> studentSessions) {
		this.studentSessions = studentSessions;
	}
}
