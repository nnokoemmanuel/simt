package ppp.simt.service.evaluation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.tracking.AgreementTracking;
import ppp.simt.entity.tracking.evaluation.TranscriptTracking;
import ppp.simt.repository.evaluation.TranscriptTrackingRepository;

@Service("TranscriptTrackingService")
public class TranscriptTrackingServiceImpl implements TranscriptTrackingService {

	@Autowired
    private TranscriptTrackingRepository transcriptTrackingRepository;

	@Override
	public void save(TranscriptTracking transcript) {
		// TODO Auto-generated method stub
		transcriptTrackingRepository.save(transcript);
		
	}

	@Override
	public ArrayList<TranscriptTracking> findByTranscriptId(int id) {
		// TODO Auto-generated method stub
		return (ArrayList<TranscriptTracking>) transcriptTrackingRepository.findByTranscriptId(id);
	}

	@Override
	public ArrayList<TranscriptTracking> findByTranscript(Transcript transcript) {
		// TODO Auto-generated method stub
		
		return (ArrayList<TranscriptTracking>)  transcriptTrackingRepository.findByTranscript(transcript);
	}

}
