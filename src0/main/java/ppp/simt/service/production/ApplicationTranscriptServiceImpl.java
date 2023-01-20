package ppp.simt.service.production;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.production.ApplicationTranscript;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.repository.production.ApplicationTranscriptRepository;

@Service("applicationTranscriptService")
public class ApplicationTranscriptServiceImpl implements ApplicationTranscriptService{
	
	@Autowired
	EntityManager em;
	
	@Autowired
	public ApplicationTranscriptRepository applicationTranscriptRepository;

	@Override
	public List<ApplicationTranscript> findAll() {
		return applicationTranscriptRepository.findAll();
	}

	@Override
	public ApplicationTranscript findById(int applicationId) {
		return applicationTranscriptRepository.findById(applicationId);
	}

	@Override
	public void saveTranscript(ApplicationTranscript applicationTranscript) {
		 applicationTranscriptRepository.save(applicationTranscript);
		
	}

	@Override
	public ApplicationTranscript findByStudentSession(StudentSession studentSession) {
		return applicationTranscriptRepository.findByStudentSession(studentSession);
	}
	
	@Override
	public ApplicationTranscript findByMatricule(String matricule) {
		return applicationTranscriptRepository.findByMatricule( matricule);
	}
	
	@Override
	public void createApplicationTranscript(ApplicationTranscript applicationTranscript) {
		applicationTranscriptRepository.save(applicationTranscript);

	}

	@Override
	public void updateApplicationTranscript(ApplicationTranscript applicationTranscript) {
		applicationTranscriptRepository.saveAndFlush(applicationTranscript);

	}

}
