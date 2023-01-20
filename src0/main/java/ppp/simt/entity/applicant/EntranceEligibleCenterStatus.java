package ppp.simt.entity.applicant;

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
@Table(name = "entrance_eligible_center_status")
public class EntranceEligibleCenterStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="description", columnDefinition="varchar(255) COMMENT 'entrance eligible center status'")
	private String description;
	
	
	@OneToMany(mappedBy = "entranceEligibleCenterStatus", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<EntranceEligibleCenter> entranceEligibleCenters;


	public EntranceEligibleCenterStatus(String description, Set<EntranceEligibleCenter> entranceEligibleCenters) {
		super();
		this.description = description;
		this.entranceEligibleCenters = entranceEligibleCenters;
	}
    
	public EntranceEligibleCenterStatus() {
	
	}

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


	public Set<EntranceEligibleCenter> getEntranceEligibleCenters() {
		return entranceEligibleCenters;
	}


	public void setEntranceEligibleCenters(Set<EntranceEligibleCenter> entranceEligibleCenters) {
		this.entranceEligibleCenters = entranceEligibleCenters;
	}
	
	

}
