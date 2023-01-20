package ppp.simt.entity.archive;

import javax.persistence.*;


import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Authority;
import ppp.simt.entity.tracking.ArchiveFileTracking;

@Entity
public class ArchiveFile {
	private static final long serialVersionUID = 1L;
	
	public ArchiveFile(){

    }
	
	
  
    public ArchiveFile(String fileName, Archive archive, int deleted) {
		super();
		this.fileName = fileName;
		this.archive = archive;
		this.deleted = deleted;
	}



    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private int id;
    
    @Column(name="file_name")
    private String fileName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "archive_id")
	private Archive archive;
    

    @Column(name="deleted", columnDefinition="integer(11) COMMENT '0 si non et 1 si oui'")
 	private int deleted;
    
    @OneToMany(mappedBy = "archiveFile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ArchiveFileTracking> archiveFileTrackings = new HashSet<ArchiveFileTracking>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<ArchiveFileTracking> getArchiveFileTrackings() {
		return archiveFileTrackings;
	}

	public void setArchiveFileTrackings(Set<ArchiveFileTracking> archiveFileTrackings) {
		this.archiveFileTrackings = archiveFileTrackings;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	
	public String getFileName(){
		return fileName; 
	}
	
	public void setFileName(String fileName) {
		this.fileName= fileName;
	}

	public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}

}
