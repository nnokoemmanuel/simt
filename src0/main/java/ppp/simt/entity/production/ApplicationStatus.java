package ppp.simt.entity.production;

import javax.persistence.*;

@Entity
@Table(name="application_status")
public class ApplicationStatus {
	    private static final long serialVersionUID = 1L;

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "id", updatable = false, nullable = false)
		private int id;
	    
	    @Column(name="description", columnDefinition="varchar(255) COMMENT 'application status description'")
	    private String description;
	    
	    public ApplicationStatus() {
	    	super();
	    }

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
		
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		
	    
	    
	 
}
