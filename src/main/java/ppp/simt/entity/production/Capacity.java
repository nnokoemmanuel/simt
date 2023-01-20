package ppp.simt.entity.production;

import javax.persistence.*;

import ppp.simt.entity.core.Person;

import java.util.Set;

@Entity
@Table(name="capacity")
public class Capacity {
	
	private static final long serialVersionUID = 1L;

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id", updatable = false, nullable = false)
	 private int id;
	 
	 @Column(name="capacity_number", columnDefinition="varchar(255) COMMENT 'capacity number'")
	 private String capacityNumber;

	 @Column(name="status", columnDefinition="varchar(255) COMMENT 'capacity status to tells if capacity is suspended or not 1 if suspended and 0 if not '")
	 private String status;
	 
	 @Column(name="on_process", columnDefinition="integer COMMENT 'set with application id if capacity is pending processs and 0 if not'")
	 private int onProcess;

		
	 public Capacity() {
		 super();
		 
	 }

	public Capacity(String capacityNumber, String status, int onProcess ) {
		this.capacityNumber = capacityNumber;
		this.status = status;
		this.onProcess = onProcess;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCapacityNumber() {
		return capacityNumber;
	}

	public void setCapacityNumber(String capacityNumber) {
		this.capacityNumber = capacityNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getOnProcess() {
		return onProcess;
	}

	public void setOnProcess(int onProcess) {
		this.onProcess = onProcess;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
