package ppp.simt.service.evaluation;

import java.util.ArrayList;

import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.tracking.AgreementTracking;
import ppp.simt.entity.tracking.evaluation.TranscriptTracking;

public interface TranscriptTrackingService {

	
	public void save(TranscriptTracking transcriptTracking);
	public ArrayList<TranscriptTracking> findByTranscript(Transcript transcript);
	public ArrayList<TranscriptTracking> findByTranscriptId(int id);

}