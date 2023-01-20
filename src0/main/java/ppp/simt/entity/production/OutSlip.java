package ppp.simt.entity.production;

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

import ppp.simt.entity.core.Office;
import ppp.simt.entity.tracking.OutSlipTracking;

@Entity
@Table(name="out_slip")
public class OutSlip {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	
	@Column(name="creation_date", columnDefinition="datetime COMMENT 'out slip creation date '")
	private java.util.Date creationDate;
	
	@Column(name="start_date", columnDefinition="datetime COMMENT 'in slip creation date '")
	private java.util.Date startDate;
	
	@Column(name="end_date")
	private java.util.Date endDate;
	
	@Column(name="reference_number")
	private String referenceNumber;
	
	@Column
	private int status;
	
	@Column(name="delivery_date")
	private java.util.Date deliveryDate;
	
	 @Column(name="total")
     private int total;

	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "office_id")
	 private Office office;
	
	 @OneToMany(fetch = FetchType.LAZY, mappedBy = "outSlip")
	 private Set<Application> applications;
	 
	 @OneToMany(fetch = FetchType.LAZY, mappedBy = "outSlip")
	 private Set<OutSlipTracking> outSlipTrackings;
	 
	
	 
	public OutSlip() {
		super();
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public java.util.Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}
    
	
	public java.util.Date getStartDate() {
		return startDate;
	}


	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}

	public java.util.Date getEndDate() {
		return endDate;
	}


	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}


	public String getReferenceNumber() {
		return referenceNumber;
	}


	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public java.util.Date getDeliveryDate() {
		return deliveryDate;
	}


	public void setDeliveryDate(java.util.Date deliveryDate) {
		this.deliveryDate = deliveryDate;
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


	public Set<OutSlipTracking> getOutSlipTrackings() {
		return outSlipTrackings;
	}


	public void setOutSlipTrackings(Set<OutSlipTracking> outSlipTrackings) {
		this.outSlipTrackings = outSlipTrackings;
	}
    
	public int getTotal() {
		return total;
	}


	public void setTotal(int total) {
		this.total = total;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
