package ppp.simt.repository.production;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.production.ApplicationTranscript;
import ppp.simt.entity.pv.StudentSession;

@Repository("applicationTranscriptRepository")
public interface ApplicationTranscriptRepository extends JpaRepository <ApplicationTranscript, Integer>{
	
	public List<ApplicationTranscript> findAll();
	public ApplicationTranscript findById(int applicationId);
	public ApplicationTranscript save(ApplicationTranscript applicationTranscript);
	public ApplicationTranscript findByStudentSession(StudentSession studentSession);
	public ApplicationTranscript findByMatricule(String matricule) ;

}
