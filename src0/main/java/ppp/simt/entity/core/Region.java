package ppp.simt.entity.core;

import org.springframework.beans.factory.annotation.Autowired;

import ppp.simt.entity.core.Division;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name = "region")
public class Region {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="abreviation")
	private String abreviation;
	
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Division> divisions = new HashSet<Division>();
    
	public Region() {
		super();
	}
	
	public Region( String name, String abreviation, Set<Division> divisions) {
		super();
		this.name = name;
		this.abreviation = abreviation;
		this.divisions = divisions;
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

	public String getAbreviation() {
		return abreviation;
	}

	public void setAbreviation(String abreviation) {
		this.abreviation = abreviation;
	}

	public Set<Division> getDivisions() {
		return divisions;
	}

	public void setDivisions(Set<Division> divisions) {
		this.divisions = divisions;
	}
	
	@Override
	public String toString() {
		return "Region [id=" + id + ", name=" + name + ", abreviation=" + abreviation 
				+ "]";
	}

}
