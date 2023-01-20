package ppp.simt.service.production;

import java.util.List;

import ppp.simt.entity.production.ApplicationTranscript;
import ppp.simt.entity.pv.StudentSession;

public interface ApplicationTranscriptService {
	
	
	public List<ApplicationTranscript> findAll();
	public ApplicationTranscript findById(int id);
	public ApplicationTranscript findByStudentSession(StudentSession studentSession);
//	public void save(ApplicationTranscript applicationTranscript);
	public void saveTranscript(ApplicationTranscript applicationTranscript);
	public ApplicationTranscript findByMatricule(String matricule) ;
	public void createApplicationTranscript(ApplicationTranscript applicationTranscript);
	public void updateApplicationTranscript(ApplicationTranscript applicationTranscript);
	
	

}
