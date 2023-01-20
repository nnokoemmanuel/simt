package ppp.simt.repository.evaluation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.tracking.evaluation.TranscriptTracking;
import ppp.simt.entity.user.User;

public interface TranscriptTrackingRepository extends JpaRepository<TranscriptTracking,Long> {

	public List<TranscriptTracking> findByTranscriptId(int id);
	
	public ArrayList<TranscriptTracking>  findByTranscript(Transcript transcript);
	public ArrayList<TranscriptTracking> findByUser(User user);
}
