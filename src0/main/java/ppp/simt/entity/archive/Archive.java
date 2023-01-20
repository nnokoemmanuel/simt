package ppp.simt.entity.archive;

import javax.persistence.*;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ppp.simt.entity.archive.ArchiveFile;
import ppp.simt.entity.core.Authority;
import ppp.simt.entity.core.Country;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.production.ProfessionalCard;
import ppp.simt.entity.tracking.ArchiveTracking;

@Entity
@Table(name="archive")
public class Archive {
	private static final long serialVersionUID = 1L;
	
    public Archive(){

    }
    public Archive( Date registrationDate, Date issuedDate, String issuedPlace, Country issuedCountry,
			Person person, String status, Date examDate, String examPlace, int pvNumber,
			String archiveNumber, String service, String grade, float finalAverage,Speciality speciality) {

		super();
		this.registrationDate = registrationDate;
		this.issuedDate = issuedDate;
		this.issuedPlace = issuedPlace;
		this.issuedCountry = issuedCountry;
		this.person = person;
		this.status = status;
		this.examDate = examDate;
		this.examPlace = examPlace;
		this.pvNumber = pvNumber;
		//this.archiveCategory = archiveCategory;
		this.archiveNumber=archiveNumber;
		//this.fileName=fileName;
		this.service=service;
		this.grade=grade;
		this.finalAverage=finalAverage;
		this.speciality = speciality;
	}

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private int id;
    
    @Column(name="registration_date")
    private Date registrationDate;
    
    @Column(name="issued_date")
    private Date issuedDate;
    
    @Column(name="issued_place")
    private String issuedPlace;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="country_id")
    private Country issuedCountry;

	@OneToOne
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person person;
    
    @Column(name="status", columnDefinition="varchar(255) COMMENT 'REGISTERED,VALIDATED, INVALIDATED'")
    private String status;
    
    @Column(name="exam_date")
    private Date examDate;
    
    @Column(name="exam_place")
    private String examPlace;

    @Column(name="pv_number")
    private int pvNumber;

    @Column(name="archive_number")
    private String archiveNumber;

	@Column(name="service")
	private String service;
	
	@Column(name="photo_and_signature")
	private String photoAndSignature;

	//@OneToMany(fetch = FetchType.LAZY, mappedBy = "archive")
	//private Set<ArchiveFile> archiveFiles;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authority_id")
	private Authority authority;

	@Column(name="grade")
	private String grade;

	@Column(name="final_average")
	private float finalAverage;
	
	@OneToMany(mappedBy = "archive", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArchiveFile> archiveFiles = new HashSet<ArchiveFile>();

   // @OneToMany(mappedBy = "archive", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   // private Set<ArchiveCategory> archiveCategory;
    
    @OneToMany(mappedBy = "archive", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArchiveTracking> archiveTrackings = new HashSet<ArchiveTracking>();
    
//    @OneToMany(mappedBy = "archive", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private Set<Capacity> capacities;
    @Column(name="on_process", columnDefinition="integer COMMENT 'set with application id if archive is pending processs and 0 if not'")
	 private int onProcess;



	@OneToOne(mappedBy="archive")
	private ProfessionalCard professionalCard;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="speciality_id")
    private  Speciality speciality;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getOnProcess() {
		return onProcess;
	}
	public void setOnProcess(int onProcess) {
		this.onProcess = onProcess;
	}
	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}
   
	public Country getIssuedCountry() {
		return issuedCountry;
	}

	public void setIssuedCountry(Country issuedCountry) {
		this.issuedCountry = issuedCountry;
	}
	
	
	public String getStatus() {
		return status;
	}

	public String setStatus(String status) {
		return this.status = status;
	}
	
	public Set<ArchiveTracking> getArchiveTrackings() {
		return archiveTrackings;
	}

	public void setArchiveTrackings(Set<ArchiveTracking> archiveTrackings) {
		this.archiveTrackings = archiveTrackings;
	}

	public String getIssuedPlace() {
		return issuedPlace;
	}

	public void setIssuedPlace(String issuedPlace) {
		this.issuedPlace = issuedPlace;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*public Set<ArchiveCategory> getArchiveCategory() {
		return archiveCategory;
	}
	public void setArchiveCategory(Set<ArchiveCategory> archiveCategory) {
		this.archiveCategory = archiveCategory;
	}*/

	public int getPvNumber() {
		return pvNumber;
	}

	public void setPvNumber(int pvNumber) {
		this.pvNumber = pvNumber;
	}
	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}
	public String getExamPlace() {
		return examPlace;
	}

	public void setExamPlace(String examPlace) {
		this.examPlace = examPlace;
	}
	public String getArchiveNumber() {
		return archiveNumber;
	}
	public void setArchiveNumber(String archiveNumber) {
		this.archiveNumber = archiveNumber;
	}
//	public Set<Capacity> getCapacities() {
//		return capacities;
//	}
//	public void setCapacities(Set<Capacity> capacities) {
//		this.capacities = capacities;
//	}	


	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public float getFinalAverage() {
		return finalAverage;
	}

	public void setFinalAverage(float finalAverage) {
		this.finalAverage = finalAverage;
	}

	public ProfessionalCard getProfessionalCard() {
		return professionalCard;
	}

	public void setProfessionalCard(ProfessionalCard professionalCard) {
		this.professionalCard = professionalCard;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public Set<ArchiveFile> getArchiveFiles() {
		return archiveFiles;
	}

	public void setArchiveFiles(Set<ArchiveFile> archiveFiles) {
		this.archiveFiles = archiveFiles;
	}
	public Speciality getSpeciality() {
		return speciality;
	}
	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}
	public String getPhotoAndSignature() {
		return photoAndSignature;
	}
	public void setPhotoAndSignature(String photoAndSignature) {
		this.photoAndSignature = photoAndSignature;
	}
	
	

}

