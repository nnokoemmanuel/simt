package ppp.simt.service.evaluation;

import java.util.List;

import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.pv.StudentSession;



public interface TranscriptService {
	public Transcript findById(int studentSessionId);
	public List<Transcript> findAll();
	public List<Transcript> findByStudentSession(StudentSession studentSession);
	public List<Transcript> findByCourse(Course course);
	public void createTranscript(Transcript transcript);
	public void updateTranscript(Transcript transcript);
	public Transcript findByStudentSessionAndCourse(StudentSession studentSession,Course course);

}
