package ppp.simt.entity.pv;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "eligible_center_status")
public class EligibleCenterStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="description", columnDefinition="varchar(255) COMMENT 'eligible center status'")
	private String description;
	
	
	@OneToMany(mappedBy = "eligibleCenterStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<EligibleCenter> eligibleCenters;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Set<EligibleCenter> getEligibleCenters() {
		return eligibleCenters;
	}


	public void setEligibleCenters(Set<EligibleCenter> eligibleCenters) {
		this.eligibleCenters = eligibleCenters;
	}
	
	
	
	
	
	

}
