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

import ppp.simt.entity.evaluation.Module; 

@Entity
@Table(name = "course")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
    
	@Column(name="complete_name", columnDefinition="varchar(255) not null COMMENT 'Course complete Name'")
	private String completeName;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="module_id")
    private  Module module;
	
	@Column(name="course_coeff", columnDefinition="integer(11) default 0 COMMENT 'coefficient of course '")
	private int courseCoefficient;
	

	@Column(name="course_max_note", columnDefinition="integer(11) COMMENT 'note maximale du cours  eg 20 over 20'")
	private int courseMaxNote;
	
	public int getCourseCoefficient() {
		return courseCoefficient;
	}
	
	public void setCourseCoefficient(int courseCoefficient) {
		this.courseCoefficient = courseCoefficient;
	}


	public Course(int id,String completeName, Module module, int courseCoefficient, int courseMaxNote) {
		super();
		this.id = id;
		this.completeName = completeName;
		this.module = module;
		this.courseCoefficient = courseCoefficient;
		this.courseMaxNote = courseMaxNote;
	}
    
	
	public Course(String completeName, Module module, int courseCoefficient, int courseMaxNote) {
		super();
		this.completeName = completeName;
		this.module = module;
		this.courseCoefficient = courseCoefficient;
		this.courseMaxNote = courseMaxNote;
	}


	


	public int getCourseMaxNote() {
		return courseMaxNote;
	}


	public void setCourseMaxNote(int courseMaxNote) {
		this.courseMaxNote = courseMaxNote;
	}


	public Course() {
		super();
	}


	public Module getModule() {
		return module;
	}



	public void setModule(Module module) {
		this.module = module;
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

	
	


}
