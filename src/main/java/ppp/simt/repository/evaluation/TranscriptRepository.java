package ppp.simt.repository.evaluation;

import java.util.List;


import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.pv.StudentSession;



@Repository("transcriptRepository")
public interface TranscriptRepository extends JpaRepository <Transcript, Integer>{
	
	Transcript findById(int  transcriptId);

	List<Transcript> findAll(); 

	public  Transcript save( Transcript  transcript);		

	public List<Transcript> findByStudentSession(StudentSession studentSession);
	
	public Transcript findByStudentSessionAndCourse(StudentSession studentSession,Course course);
	
	public List<Transcript> findByCourse(Course course);

}
