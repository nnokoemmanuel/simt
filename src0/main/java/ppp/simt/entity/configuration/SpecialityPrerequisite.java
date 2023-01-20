package ppp.simt.entity.configuration;

import javax.persistence.*;

import ppp.simt.entity.core.Speciality;
@Entity
@Table(name = "speciality_prerequisite")
public class SpecialityPrerequisite {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="entry_diploma", columnDefinition="varchar(255) not null COMMENT 'Entry Diploma'")
	private String entryDiploma;
	
	@Column(name="duration_in_months", columnDefinition="integer(11) not null COMMENT ' Number of months required for training at specified speciality'")
	private int duration;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="speciality_id")
	private Speciality speciality;
	
	
	public SpecialityPrerequisite(){
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getEntryDiploma() {
		return entryDiploma;
	}


	public void setEntryDiploma(String entryDiploma) {
		this.entryDiploma = entryDiploma;
	}


	public int getDuration() {
		return duration;
	}


	public void setDuration(int duration) {
		this.duration = duration;
	}


	public Speciality getSpeciality() {
		return speciality;
	}


	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}
	
	

}
