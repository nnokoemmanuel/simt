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

import ppp.simt.entity.production.OutSlip;
import ppp.simt.entity.user.User;

@Entity
@Table(name="out_slip_tracking")
public class OutSlipTracking {
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
	@JoinColumn(name="out_slip_id")
	private OutSlip outSlip;
	
	public OutSlipTracking() {
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

	public OutSlip getOutSlip() {
		return outSlip;
	}

	public void setOutSlip(OutSlip outSlip) {
		this.outSlip = outSlip;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
   

}
