package ppp.simt.entity.pv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ppp.simt.entity.core.Speciality;


@Entity
@Table(name = "eligible_speciality")
public class EligibleSpeciality {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name ="training_center_id")
    private  TrainingCenter trainingCenter;
	
	@ManyToOne
    @JoinColumn(name ="speciality_id")
    private  Speciality speciality;
    
	
    
	public EligibleSpeciality() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TrainingCenter getTrainingCenter() {
		return trainingCenter;
	}

	public void setTrainingCenter(TrainingCenter trainingCenter) {
		this.trainingCenter = trainingCenter;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public EligibleSpeciality(TrainingCenter trainingCenter, Speciality speciality) {
		super();
		this.trainingCenter = trainingCenter;
		this.speciality = speciality;
	}
	
	
	

}
