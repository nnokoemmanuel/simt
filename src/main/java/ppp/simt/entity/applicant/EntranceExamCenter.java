package ppp.simt.entity.applicant;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

import ppp.simt.entity.core.Division;
import ppp.simt.entity.pv.District;
import ppp.simt.entity.pv.SubDistrict;

@Entity
@Table(name = "entrance_exam_center")
public class EntranceExamCenter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@OneToMany(mappedBy = "entranceExamCenter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EntranceEligibleCenter> entranceEligibleCenters = new HashSet<EntranceEligibleCenter>();
	
	@ManyToOne
    @JoinColumn(name ="division_id")
    private  Division division;

	public EntranceExamCenter(String name, Set<EntranceEligibleCenter> entranceEligibleCenters, Division division) {
		super();
		this.name = name;
		this.entranceEligibleCenters = entranceEligibleCenters;
		this.division = division;
	}
	
	public EntranceExamCenter() {
	
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

	public Set<EntranceEligibleCenter> getEntranceEligibleCenters() {
		return entranceEligibleCenters;
	}

	public void setEntranceEligibleCenters(Set<EntranceEligibleCenter> entranceEligibleCenters) {
		this.entranceEligibleCenters = entranceEligibleCenters;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}


}
