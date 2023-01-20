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

/*
 *Sous-Circonscription maritime (en francais) 
 */

@Entity
@Table(name = "sub_district")
public class SubDistrict {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	

	@Column(name="name", columnDefinition="varchar(255)  not null COMMENT 'name of the subdistrict '")
	private String name;
	
	@OneToMany(mappedBy = "subDistrict", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ExamCenter> examCenters;
	
	@ManyToOne
    @JoinColumn(name ="district_id")
    private  District district;
	
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
	
	public District getDistrict() {
		return district;
	}
	
	public void setDistrict(District district) {
		this.district =district;
	}

}
