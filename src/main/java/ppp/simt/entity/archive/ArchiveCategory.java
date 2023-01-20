package ppp.simt.entity.archive;

import javax.persistence.*;

import ppp.simt.entity.core.Category;

@Entity
@Table(name="archive_category")
public class ArchiveCategory {
	private static final long serialVersionUID = 1L;
	
    public ArchiveCategory(){

    }
    
    public ArchiveCategory( Category category) {
		super();
		//this.archive = archive;
		this.category = category;
	}


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private int id;
    
	//@ManyToOne
   // @JoinColumn(name ="archive_id")
   // private Archive archive;
    
    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;

	/*public Archive getArchive() {
		return archive;
	}

	public void setArchive(Archive archive) {
		this.archive = archive;
	}*/

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	} 
    
    
    
}