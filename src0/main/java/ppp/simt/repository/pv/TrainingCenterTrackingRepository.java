package ppp.simt.repository.pv;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.tracking.TrainingCenterTracking;
import ppp.simt.entity.user.User;

public interface TrainingCenterTrackingRepository  extends JpaRepository<TrainingCenterTracking,Integer> {

	public ArrayList<TrainingCenterTracking>  findByTrainingCenter(TrainingCenter trainingCenter);
	public ArrayList<TrainingCenterTracking> findByUser(User user);
	
}
