package ppp.simt.entity.pv;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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



import ppp.simt.entity.applicant.TrainingCenterChoice;
import ppp.simt.entity.core.Division;
import ppp.simt.entity.user.User;


@Entity
@Table(name = "training_center")
public class TrainingCenter {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	

	@Column(name="name", columnDefinition="varchar(255) COMMENT 'boad driving school name'")
	private String name;

	@Column(name="creation_date", columnDefinition="datetime  COMMENT 'training school creation date'")
	private Date creationDate;
	

	@Column(name="founder", columnDefinition="varchar(255) COMMENT 'training school founder name'")
	private String founder;
	
	@Column(name="owner", columnDefinition="varchar(255) COMMENT 'training school owner name'")
	private String owner;
	
	@Column(name="owner_phone_number", columnDefinition="varchar(255) COMMENT 'training school owner phone number'")
	private String ownerContact;
	
	
	@Column(name="location", columnDefinition="varchar(255) COMMENT 'training school location '")
	private String location;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trainingCenter")
	private List<Agreement> agreements; 
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trainingCenter")
	private Set<Student> students;
	
	@ManyToOne
    @JoinColumn(name ="division_id")
    private  Division division;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trainingCenter")
	private List<EligibleSpeciality> eligibleSpeciality;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trainingCenter")
	private List<User> users;
	
	
	
	@Column(name="abbreviation", columnDefinition="varchar(3) COMMENT 'training school abbreviation '")
	private String abbreviation;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "trainingCenter")
	private List<TrainingCenterChoice> trainingCenterChoice;
	
	@Column(name="max_student", columnDefinition="integer COMMENT 'maximum number of students in a training school' default 0")
	private int maxStudent;
	
	@Column(name="status" , columnDefinition="integer(11) not null COMMENT 'agreement state 0 if suspended 1 if active'")
	private int status;

	

	public TrainingCenter(int id, String name,  Date creationDate, String founder, String owner,
			String location, Division division,String abbreviation) {
		super();
		this.id = id;
		this.name = name;
		this.creationDate = creationDate;
		this.founder = founder;
		this.owner = owner;
		this.location = location;
		this.division = division;
		this.abbreviation = abbreviation;
	}
	
	public TrainingCenter( String name,  Date creationDate, String founder, String owner,
			String location, Division division,String abbreviation , int status , String ownerContact) {
		super();
		this.name = name;
		this.creationDate = creationDate;
		this.founder = founder;
		this.owner = owner;
		this.location = location;
		this.division = division;
		this.abbreviation = abbreviation;
		this.status = status;
		this.ownerContact= ownerContact;
	}


	public TrainingCenter(){
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
 


	public String getOwnerContact() {
		return ownerContact;
	}

	public void setOwnerContact(String ownerContact) {
		this.ownerContact = ownerContact;
	}

	public List<Agreement> getAgreements() {
		return agreements;
	}

	public void setAgreements(List<Agreement> agreements) {
		this.agreements = agreements;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getFounder() {
		return founder;
	}

	public void setFounder(String founder) {
		this.founder = founder;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}


	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<EligibleSpeciality> getEligibleSpeciality() {
		return eligibleSpeciality;
	}

	public void ListEligibleSpeciality(List<EligibleSpeciality> eligibleSpeciality) {
		this.eligibleSpeciality = eligibleSpeciality;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public List<TrainingCenterChoice> getTrainingCenterChoice() {
		return trainingCenterChoice;
	}

	public void setTrainingCenterChoice(List<TrainingCenterChoice> trainingCenterChoice) {
		this.trainingCenterChoice = trainingCenterChoice;
	}

	public int getMaxStudent() {
		return maxStudent;
	}

	public void setMaxStudent(int maxStudent) {
		this.maxStudent = maxStudent;
	}

	public void setEligibleSpeciality(List<EligibleSpeciality> eligibleSpeciality) {
		this.eligibleSpeciality = eligibleSpeciality;
	}
	
}
