package ppp.simt.entity.applicant;



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
import javax.persistence.CascadeType;

import ppp.simt.entity.pv.TrainingCenter;


@Entity
@Table(name = "choice_training_center")
public class TrainingCenterChoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="training_center_id")
    private TrainingCenter trainingCenter;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="applicant_id")
    private  Applicant applicant;

	@Column(name="order_choice", columnDefinition="varchar(255) COMMENT 'les choix de applicant'")
	private String order;

	public TrainingCenterChoice(Long id, String order) {
		this.id = id;
		this.order = order;
	}

	public TrainingCenterChoice() {

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

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}


	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
