package ppp.simt.entity.pv;
import ppp.simt.entity.core.Division;
import ppp.simt.entity.pv.EligibleCenter;

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


@Entity
@Table(name = "exam_center")
public class ExamCenter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="name",nullable=false)
	private String name;
	
	@OneToMany(mappedBy = "examCenter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EligibleCenter> eligibleCenters = new HashSet<EligibleCenter>();
	
	@ManyToOne
    @JoinColumn(name ="district_id")
    private  District district;
	
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	@ManyToOne
    @JoinColumn(name ="sub_district_id")
    private  SubDistrict subDistrict;
	
	@ManyToOne
    @JoinColumn(name ="division_id")
    private  Division division;
	
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
	
	public Set<EligibleCenter> getEligibleCenters() {
		return eligibleCenters;
	}

	public void setEligibleCenters(Set<EligibleCenter> eligibleCenters) {
		this.eligibleCenters = eligibleCenters;
	}
	
	public District getDistrict() {
		return district;
	}
	
	public void setDistrict(District district) {
		this.district =district;
	}
	
	public SubDistrict getSubDistrict(){
		return subDistrict;
	}
	
	public void setSubDistrict(SubDistrict subDistrict) {
		this.subDistrict = subDistrict;
	}
}
