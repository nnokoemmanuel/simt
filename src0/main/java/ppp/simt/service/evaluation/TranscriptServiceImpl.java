package ppp.simt.service.evaluation;


import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.repository.evaluation.TranscriptRepository;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.pv.StudentSession;


@Service("transcriptService")
public class TranscriptServiceImpl implements TranscriptService{

	@Autowired
	private TranscriptRepository transcriptRepository;
	
	@Autowired
	EntityManager em;



	@Override
	public List<Transcript> findAll() {
		return transcriptRepository.findAll();
	}
	
	@Override
	public void createTranscript(Transcript transcript) {
		transcriptRepository.save(transcript);
		
	}
	
	@Override
	public void updateTranscript(Transcript transcript) {
		transcriptRepository.saveAndFlush(transcript);

	}
	
	@Override
	public Transcript findByStudentSessionAndCourse(StudentSession studentSession,Course course){
		return transcriptRepository.findByStudentSessionAndCourse(studentSession,course);
	}


	@Override
	public Transcript findById(int studentSessionNoteId){
		return transcriptRepository.findById(studentSessionNoteId);
	}

	@Override
	public List<Transcript> findByStudentSession(StudentSession studentSession) {
		return transcriptRepository.findByStudentSession(studentSession);
	}
    
	
	@Override
	public List<Transcript> findByCourse(Course course) {
		return transcriptRepository.findByCourse(course);
	}

}



