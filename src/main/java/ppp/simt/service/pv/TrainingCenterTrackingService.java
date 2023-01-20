package ppp.simt.service.pv;

import java.util.ArrayList;

import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.tracking.TrainingCenterTracking;

public interface TrainingCenterTrackingService {
	
	public void save(TrainingCenterTracking trainingCenterTracking);
	public ArrayList<TrainingCenterTracking> findByTrainingCenter(TrainingCenter trainingCenter);
	public void deleteTrainingCenterTracking(TrainingCenterTracking trainingCenterTracking);
	
}
