package ppp.simt.entity.tracking;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.user.User;

@Entity
@Table(name="entrance_eligible_center_tracking")
public class EntranceEligibleCenterTracking {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@ManyToOne
    @JoinColumn(name ="user_id")
	private User user;
	
	
	@Column(name="operation", columnDefinition="TEXT")
	private String operation;
	
	@Column(name="operation_date")
	private Date operationDate;
	
	@ManyToOne
	@JoinColumn(name="entrance_eligible_center_id")
	private EntranceEligibleCenter entranceEligibleCenter;
	
	public EntranceEligibleCenterTracking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public EntranceEligibleCenter getEntranceEligibleCenter() {
		return entranceEligibleCenter;
	}

	public void setEntranceEligibleCenter(EntranceEligibleCenter entranceEligibleCenter) {
		this.entranceEligibleCenter = entranceEligibleCenter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
