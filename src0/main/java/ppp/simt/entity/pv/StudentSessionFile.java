package ppp.simt.entity.pv;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.tracking.StudentSessionTracking;

@Entity
@Table(name="student_session_file")
public class StudentSessionFile {
private static final long serialVersionUID = 1L;
	
    public StudentSessionFile(){
    	super(); 
    }
    
   //public studentSessionFile(int id, String file, int filename, int deleted){
   public StudentSessionFile(int id, MultipartFile file, String filename, int deleted){
    	

    //public studentSessionFile(String filename, int deleted){
    	super();
		this.deleted = deleted;
		this.fileName = filename;

    }
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private int id;
    
	@ManyToOne
    @JoinColumn(name ="student_session_id")
    private  StudentSession studentSession;
	
    @Column(name="file_name")
    private String fileName; 
   
    @Column(name="deleted")
 	private int deleted;



	public int getId() { 
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public int getDeleted() {
		return deleted;
	}



	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public StudentSession getStudentSession() {
		return studentSession;
	}

	public void setStudentSession(StudentSession studentSession) {
		this.studentSession = studentSession;
	}




}
