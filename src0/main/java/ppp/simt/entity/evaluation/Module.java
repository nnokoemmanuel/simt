package ppp.simt.entity.evaluation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ppp.simt.entity.core.Speciality;


@Entity
@Table(name = "module")
public class Module {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id; 
	
	@Column(name="complete_name", columnDefinition="varchar(255) not null COMMENT 'Course category complete Name eg Connaissances et aptitude pedagogiques ou Connaissances et aptitudes pratiques professionnelles'")
	private String completeName;
	
	@Column(name="classification")
	private int moduleClassification;
	
	@Column(name="module_min_note", columnDefinition="integer(11) COMMENT 'note eliminatoire du module eg 1 over 20 '")
	private int moduleMinNote;
	
	@Column(name="module_percentage", columnDefinition="integer default 0 COMMENT 'percentage of module '")
	private int modulePercentage;
	
	@Column(name="status", columnDefinition="integer(11) COMMENT 'status of the module 1 if actif and 0 if suspended that means it is not taken in consideration' DEFAULT 1")
	private int status;
	
	public int getModuleMinNote() {
		return moduleMinNote;
	}


	public void setModuleMinNote(int moduleMinNote) {
		this.moduleMinNote = moduleMinNote;
	}


	public int getModuleClassification() {
		return moduleClassification;
	}


	public void setModuleClassification(int moduleClassification) {
		this.moduleClassification = moduleClassification;
	}
	

	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}





	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="speciality_id")
    private  Speciality speciality;

	public Module() {
		super();
	}
	

	public Module(String completeName, Speciality speciality , int moduleClassification , int moduleMinNote,int modulePercentage) {
		super();
		this.completeName = completeName;
		this.speciality = speciality;
		this.moduleClassification= moduleClassification;
		this.moduleMinNote=moduleMinNote;
		this.modulePercentage=modulePercentage;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getCompleteName() {
		return completeName;
	}



	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}
	
	public Speciality getSpeciality() {
		return speciality;
	}



	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}


	public int getModulePercentage() {
		return modulePercentage;
	}


	public void setModulePercentage(int modulePercentage) {
		this.modulePercentage = modulePercentage;
	}
   
	
	
	


}
