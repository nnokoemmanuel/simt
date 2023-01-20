package ppp.simt.entity.core;


import ppp.simt.entity.archive.ArchiveCategory;
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
@Table(name = "category")
public class Category {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="distanceFromCoast")
	private String distanceFromCost;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArchiveCategory> archiveCategory;

	public Category(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}

	public String getDistanceFromCost() {
		return distanceFromCost;
	}

	public void setDistanceFromCost(String distanceFromCost) {
		this.distanceFromCost = distanceFromCost;
	}

	public Set<ArchiveCategory> getArchiveCategory() {
		return archiveCategory;
	}

	public void setArchiveCategory(Set<ArchiveCategory> archiveCategory) {
		this.archiveCategory = archiveCategory;
	}
	

}
