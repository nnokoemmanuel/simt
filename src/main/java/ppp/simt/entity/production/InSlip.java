package ppp.simt.entity.production;

import ppp.simt.entity.core.Office;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.tracking.InSlipTracking;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="in_slip")
public class InSlip {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id", updatable = false, nullable = false)
	 private int id;
	
	@Column(name="total", columnDefinition="integer COMMENT 'total number of applications in a in slip'")
	private int total;

	@Column(name="not_found", columnDefinition="integer COMMENT 'total number of applications not found in a in slip'")
	private  int notFound;
	
//	@Column(name="status", columnDefinition="integer(11) COMMENT 'in slip status 1 - registered , 2 - opened , 3 - closed , 4 - controlled (in slip is ok) , 5 - controlled (in slip is bad) '")
//	private int status;
	
	@Column(name="creation_date", columnDefinition="datetime COMMENT 'in slip creation date '")
	private java.util.Date creationDate;
	
	@Column(name="reference_number", columnDefinition="varchar(255) COMMENT 'in slip reference number '")
	private String referenceNumber;
	
	@Column(name="inslip_type", columnDefinition="varchar(255) COMMENT 'determines the source of the inslip -- Transcript= T, Certificate = C, Professional Card = P  '")
	private String inSlipType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private Office office;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inSlip")
    private Set<Application> applications;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inSlip")
	private Set<InSlipTracking> inSlipTrackings;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "in_slip_status_id")
    private InSlipStatus inSlipStatus;

	@ManyToOne
	@JoinColumn(name="eligible_center_id")
	private EligibleCenter eligibleCenter;
    
	public InSlip(){
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public int getNotFound() {
		return notFound;
	}


	public void setNotFound(int notFound) {
		this.notFound = notFound;
	}

	

	public InSlipStatus getStatus() {
		return inSlipStatus;
	}


	public void setStatus(InSlipStatus inSlipStatus) {
		this.inSlipStatus = inSlipStatus;
	}


	public java.util.Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}


	public String getReferenceNumber() {
		return referenceNumber;
	}


	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}


	public Office getOffice() {
		return office;
	}


	public void setOffice(Office office) {
		this.office = office;
	}


	public Set<Application> getApplications() {
		return applications;
	}


	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}


	public Set<InSlipTracking> getInSlipTrackings() {
		return inSlipTrackings;
	}


	public void setInSlipTrackings(Set<InSlipTracking> inSlipTrackings) {
		this.inSlipTrackings = inSlipTrackings;
	}


	public InSlipStatus getInSlipStatus() {
		return inSlipStatus;
	}

	public void setInSlipStatus(InSlipStatus inSlipStatus) {
		this.inSlipStatus = inSlipStatus;
	}

	public EligibleCenter getEligibleCenter() {
		return eligibleCenter;
	}

	public void setEligibleCenter(EligibleCenter eligibleCenter) {
		this.eligibleCenter = eligibleCenter;
	}


	public String getInSlipType() {
		return inSlipType;
	}


	public void setInSlipType(String inSlipType) {
		this.inSlipType = inSlipType;
	}


	
	
	
}
