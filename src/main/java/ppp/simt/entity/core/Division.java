package ppp.simt.entity.core;

import java.util.Set;

import ppp.simt.entity.core.Region;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.pv.ExamCenter;

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
@Table(name = "division")
public class Division {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne
    @JoinColumn(name ="region_id")
    private  Region region;
	
	@OneToMany(mappedBy = "division", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<TrainingCenter> trainingCenter;
	
	@OneToMany(mappedBy = "division", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<ExamCenter> examCenters;
	
	public Division() {
		super();
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
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	
	public Set<TrainingCenter> getTrainingCenter() {
		return trainingCenter;
	}

	public void setTrainingCenter(Set<TrainingCenter> trainingCenter) {
		this.trainingCenter = trainingCenter;
	}

	public Set<ExamCenter> getExamCenters() {
		return examCenters;
	}

	public void setExamCenters(Set<ExamCenter> examCenters) {
		this.examCenters = examCenters;
	}

	@Override
	public String toString() {
		return "Division [id=" + id + ", name=" + name + ", region=" + region + "]";
	}

}
