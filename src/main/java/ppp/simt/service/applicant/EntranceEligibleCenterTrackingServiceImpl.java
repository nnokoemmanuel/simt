package ppp.simt.service.applicant;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.EntranceEligibleCenterTracking;
import ppp.simt.repository.applicant.EntranceEligibleCenterTrackingRepository;

@Service("EntranceEligibleCenterTrackingService")
public class EntranceEligibleCenterTrackingServiceImpl implements EntranceEligibleCenterTrackingService {
	
	 
		@Autowired
	    private EntranceEligibleCenterTrackingRepository entranceEligibleCenterTrackingRepository;

		@Override
		public void save(EntranceEligibleCenterTracking entranceEligibleCenter) {
			// TODO Auto-generated method stub
			entranceEligibleCenterTrackingRepository.save(entranceEligibleCenter);
			
		}

		@Override
		public ArrayList<EntranceEligibleCenterTracking> findByEntranceEligibleCenterId(int id) {
			// TODO Auto-generated method stub
			return (ArrayList<EntranceEligibleCenterTracking>) entranceEligibleCenterTrackingRepository.findByEntranceEligibleCenterId(id);
		}

}