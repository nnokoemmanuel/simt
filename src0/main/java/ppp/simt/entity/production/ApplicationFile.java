package ppp.simt.entity.production;

import javax.persistence.Column;
import javax.persistence.*;


@Entity
@Table(name="application_file")
public class ApplicationFile {

	private static final long serialVersionUID = 1L;

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id", updatable = false, nullable = false)
	 private int id;
	 
	 @Column(name="name", columnDefinition="varchar(255) COMMENT 'file name '")
	 private String name ;
	 

	 @Column(name="is_deleted", columnDefinition="integer(11) COMMENT '1 if the file is deletable and 0 if not'")
	 private int isDeleted;

	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "application_id")
	private Application application;
	 
	 public ApplicationFile() {
		 super();
	 }
     
	 
	 
	public ApplicationFile(String name,  Application application , int isDeleted) {
		super();
		this.name = name;
		this.isDeleted = isDeleted;
		this.application = application;
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

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	 
}
