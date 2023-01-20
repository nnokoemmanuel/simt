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

import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.user.User;

@Entity
@Table(name="student_session_tracking")
public class StudentSessionTracking {
	
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
	@JoinColumn(name="student_session_id")
	private StudentSession studentSession;

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

	public StudentSession getStudentSession() {
		return studentSession;
	}

	public void setStudentSession(StudentSession studentSession) {
		this.studentSession = studentSession;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
