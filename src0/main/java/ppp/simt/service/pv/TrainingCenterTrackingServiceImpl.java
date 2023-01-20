package ppp.simt.service.pv;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.tracking.TrainingCenterTracking;
import ppp.simt.repository.pv.TrainingCenterTrackingRepository;



@Service("trainingCenterTrackingService")
public class TrainingCenterTrackingServiceImpl implements TrainingCenterTrackingService {
	
	 
		@Autowired
	    private TrainingCenterTrackingRepository  trainingCenterTrackingRepository;

		@Override
		public void save( TrainingCenterTracking  trainingCenterTracking) {
			// TODO Auto-generated method stub
			 trainingCenterTrackingRepository.save( trainingCenterTracking);
			
		}

		@Override
		public ArrayList<TrainingCenterTracking> findByTrainingCenter( TrainingCenter  trainingCenter) {
			// TODO Auto-generated method stub
			return (ArrayList<TrainingCenterTracking>)  trainingCenterTrackingRepository.findByTrainingCenter( trainingCenter);
		}
		
		
		@Override
		public void deleteTrainingCenterTracking(TrainingCenterTracking  trainingCenterTracking) {
			 trainingCenterTrackingRepository.delete( trainingCenterTracking);
		}

}
