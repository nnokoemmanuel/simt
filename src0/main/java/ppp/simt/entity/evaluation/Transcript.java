package ppp.simt.entity.evaluation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ppp.simt.entity.pv.StudentSession;


@Entity
@Table(name = "transcript")
public class Transcript {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="student_session_id")
    private  StudentSession studentSession;
    
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="course_id")  
    private  Course course;
	
	@Column(name="course_note", columnDefinition="FLOAT COMMENT 'mark of the student for a corresponding course '")
	private float courseNote;
	
	public Transcript() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public StudentSession getStudentSession() {
		return studentSession;
	}

	public void setStudentSession(StudentSession studentSession) {
		this.studentSession = studentSession;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public float getCourseNote() {
		return courseNote;
	}  

	public void setCourseNote(float courseNote) {
		this.courseNote = courseNote;
	}


}
