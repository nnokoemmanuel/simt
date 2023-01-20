package ppp.simt.entity.pv;


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

/*
 *Circonscription maritime (en francais) 
 */

@Entity
@Table(name = "district")
public class District {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="name", columnDefinition="varchar(255) not null COMMENT 'district name'")
	private String name;
	
	@OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ExamCenter> examCenters;
	
	@OneToMany(mappedBy = "district", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SubDistrict> subDistricts;
	
	@ManyToOne
	@JoinColumn(name ="division_id")
	private Division division; 
	
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
	
	public Set<ExamCenter> getExamCenters() {
		return examCenters;
	}
	
	public void setExamCenters(Set<ExamCenter> examCenter) {
		this.examCenters = examCenter;
	}
	
	public Set<SubDistrict> getSubDistrict() {
		return subDistricts;
	}
	
	public void setSubDistrict(Set<SubDistrict> subDistricts) {
		this.subDistricts = subDistricts;
	}
	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

}
