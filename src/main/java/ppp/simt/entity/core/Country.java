package ppp.simt.entity.core;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import ppp.simt.entity.archive.*;

@Entity
@Table(name = "country")
public class Country {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="country_name")
    private String countryName;

	@Column(name="abrevation")
    private String countryAbrevation;
	
	@Column(name="country_name_french")
    private String countryNameFrench;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "issuedCountry")
    private Set<Archive> archives;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
 
	public String getCountryNameFrench() {
		return countryNameFrench;
	}

	public void setCountryNameFrench(String countryNameFrench) {
		this.countryNameFrench = countryNameFrench;
	}

	public Set<Archive> getArchives() {
		return archives;
	}

	public void setArchives(Set<Archive> archives) {
		this.archives = archives;
	}
    
	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountryAbrevation() {
		return countryAbrevation;
	}

	public void setCountryAbrevation(String countryAbrevation) {
		this.countryAbrevation = countryAbrevation;
	}

}
