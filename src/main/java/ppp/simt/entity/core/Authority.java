package ppp.simt.entity.core;


import ppp.simt.entity.archive.Archive;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "authority")
public class Authority {
	
	public Authority(){
		super();
    }
	public Authority(String completeName, String position, String signature, int status,
			Region region) {
		super();
		this.completeName = completeName;
		this.position = position;
		this.signature = signature;
		this.status = status;
		this.region = region;
	}

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@Column(name="complete_name", columnDefinition="varchar(255) not null COMMENT 'Authority complete Name'")
	private String completeName;

	@Column(name="position", columnDefinition="varchar(255) not null COMMENT 'Authority position'")
	private String position;

	@Column(name="signature", columnDefinition="varchar(255) not null COMMENT 'Authority signature '")
	private String signature;
	
	@Column(name="status", columnDefinition="integer(11) not null COMMENT ' Status du delegué, 0 si le delegué est inactif et 1 sinon'")
	private int status;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	private Region region;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "authority")
	private Set<Archive> archive;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public String getCompleteName() {
		return completeName;
	}


	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}
	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	
	public Region getRegion() {
		return region;
	}


	
	public void setRegion(Region region) {
		this.region = region;
	}

	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public Set<Archive> getArchive() {
		return archive;
	}

	public void setArchive(Set<Archive> archive) {
		this.archive = archive;
	}

	@Override
	public String toString() {

		return "Authority [completeName=" + completeName + "]";
	}
	

}
