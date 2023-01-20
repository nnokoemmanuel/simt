package ppp.simt.entity.production;


import java.util.Set;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Authority;
import ppp.simt.entity.core.Office;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.tracking.ApplicationTracking;
import ppp.simt.entity.user.User;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name="application")
public class Application {
	private static final long serialVersionUID = 1L;


	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id", updatable = false, nullable = false)
	 private int id;
	 
	
	 @Column(name="serial_number", columnDefinition="varchar(255) not null COMMENT 'application serial number'")
	 private String serialNumber;
	 
	 @Column(name="form_serial_number", columnDefinition="varchar(255) not null COMMENT 'form serial number'")
	 private String formSerialNumber;
	 
	 @Column(name="computerizationDate", columnDefinition="datetime not null COMMENT 'application computerization date'")
	 private java.util.Date computerizationDate;
	 
	 @Column(name="successfuly_rejected_date", columnDefinition="datetime COMMENT 'succesfully printed or rejected date '")
	 private java.util.Date successfulyRejectedDate;
	 
	 @Column(name="issue_date", columnDefinition="datetime COMMENT 'application issued date '")
	 private java.util.Date issueDate;
	 
	 
	 @Column(name="photo", columnDefinition="varchar(255) COMMENT 'application photo '")
	 private String photo;
	 
	 @Column(name="signature", columnDefinition="varchar(255) COMMENT 'application signature '")
	 private String signature;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "in_slip_id")
	 private InSlip inSlip;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "out_slip_id")
	 private OutSlip outSlip;
	

	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "process_type_id")
	 private ProcessType processType;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "application_status_id")
	 private ApplicationStatus applicationStatus;
	 
	 @Column(name="number")
	 private int number;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "office_id")
	 private Office office;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "authority_id")
	 private Authority authority;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "user_id")
	 private User user;
	 
	 
	 @OneToMany(fetch = FetchType.LAZY, mappedBy = "application")
	 private Set<ApplicationTracking> applicationTrackings;
	 
	 @OneToMany(fetch = FetchType.LAZY, mappedBy = "application")
	 private Set<ApplicationFile> applicationFiles;

	@ManyToOne
	@JoinColumn(name="certificate_id")
	private Certificate certificate;

	@ManyToOne
	@JoinColumn(name="archive_id")
	private Archive archive;

	@ManyToOne
	@JoinColumn(name="speciality_id")
	private Speciality speciality;
	
	@ManyToOne
	@JoinColumn(name="transcript_id")
	private ApplicationTranscript transcript;

	public Application() {
		 
	 }
     

	public Application(String serialNumber, Date computerizationDate, Date successfulyRejectedDate, Date issueDate,
			String photo, String signature, InSlip inSlip, OutSlip outSlip, ProcessType processType,
			ApplicationStatus applicationStatus, int number, Office office, Authority authority,
			User user, String formSerialNumber ) {
		super();
		this.serialNumber = serialNumber;
		this.computerizationDate = computerizationDate;
		this.successfulyRejectedDate = successfulyRejectedDate;
		this.issueDate = issueDate;
		this.photo = photo;
		this.signature = signature;
		this.inSlip = inSlip;
		this.outSlip = outSlip;
		this.processType = processType;
		this.applicationStatus = applicationStatus;
		this.number = number;
		this.office = office;
		this.authority = authority;
		this.user = user;
		this.formSerialNumber = formSerialNumber;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getSerialNumber() {
		return serialNumber;
	}


	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}


	public java.util.Date getComputerizationDate() {
		return computerizationDate;
	}


	public void setComputerizationDate(java.util.Date computerizationDate) {
		this.computerizationDate = computerizationDate;
	}


	public java.util.Date getIssueDate() {
		return issueDate;
	}


	public void setIssueDate(java.util.Date issueDate) {
		this.issueDate = issueDate;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public InSlip getInSlip() {
		return inSlip;
	}


	public void setInSlip(InSlip inSlip) {
		this.inSlip = inSlip;
	}


	public OutSlip getOutSlip() {
		return outSlip;
	}


	public void setOutSlip(OutSlip outSlip) {
		this.outSlip = outSlip;
	}


	public ProcessType getProcessType() {
		return processType;
	}


	public void setProcessType(ProcessType processType) {
		this.processType = processType;
	}


	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}


	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}


	public Set<ApplicationTracking> getApplicationTrackings() {
		return applicationTrackings;
	}


	public void setApplicationTrackings(Set<ApplicationTracking> applicationTrackings) {
		this.applicationTrackings = applicationTrackings;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public java.util.Date getSuccessfulyRejectedDate() {
		return successfulyRejectedDate;
	}


	public void setSuccessfulyRejectedDate(java.util.Date successfulyRejectedDate) {
		this.successfulyRejectedDate = successfulyRejectedDate;
	}
	
	public Office getOffice() {
		return office;
	}


	public void setOffice(Office office) {
		this.office = office;
	}
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	public Authority getAuthority() {
		return authority;
	}


	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	  
	
	 public Set<ApplicationFile> getApplicationFiles() {
			return applicationFiles;
		}

	public void setApplicationFiles(Set<ApplicationFile> applicationFiles) {
			this.applicationFiles = applicationFiles;
		}


	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}


	public Speciality getSpeciality() {
		return speciality;
	}


	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}


	public ApplicationTranscript getTranscript() {
		return transcript;
	}


	public void setTranscript(ApplicationTranscript transcript) {
		this.transcript = transcript;
	}


	public String getFormSerialNumber() {
		return formSerialNumber;
	}


	public void setFormSerialNumber(String formSerialNumber) {
		this.formSerialNumber = formSerialNumber;
	}
	
	
	
	
}
