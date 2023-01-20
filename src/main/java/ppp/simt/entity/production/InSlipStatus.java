package ppp.simt.entity.production;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="in_slip_status")
public class InSlipStatus {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id", updatable = false, nullable = false)
	 private int id;
	 
	 @Column(name="name", columnDefinition="varchar(255) COMMENT 'Caption of State/status'")
	 private String name;
	 
	 @Column(name="description", columnDefinition="varchar(255) COMMENT 'brief description of status'")
	 private String description;
	 
	 @OneToMany(fetch = FetchType.LAZY, mappedBy = "inSlipStatus")
	 private Set<InSlip> inslip;
	 
	 public InSlipStatus(){
		 
	 }

	 public int getId() {
		return id;
	 }

	 public void setId(int id) {
		this.id = id;
	 }

	 public String getName() {
		return name;
	 }

	 public void setName(String name) {
		this.name = name;
	 }

	 public String getDescription() {
		return description;
	 }

	 public void setDescription(String description) {
		this.description = description;
	 }

	public Set<InSlip> getInslip() {
		return inslip;
	}

	public void setInslip(Set<InSlip> inslip) {
		this.inslip = inslip;
	}
	 
	 

}
