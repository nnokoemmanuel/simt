package ppp.simt.service.pv;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.EligibleCenterTracking;
import ppp.simt.repository.pv.EligibleCenterTrackingRepository;

@Service("eligibleCenterTrackingService")
public  class EligibleCenterTrackingServiceImpl  implements  EligibleCenterTrackingService{

	@Autowired
    private EligibleCenterTrackingRepository  eligibleCenterTrackingRepository;
	
	@Override
	public void save(EligibleCenterTracking eligibleCenter) {
		// TODO Auto-generated method stub
		eligibleCenterTrackingRepository.save(eligibleCenter);
		
	}

	@Override
	public ArrayList<EligibleCenterTracking> findByEligibleCenterId(int id) {
		// TODO Auto-generated method stub
		return (ArrayList<EligibleCenterTracking>)eligibleCenterTrackingRepository.findByEligibleCenterId(id);
	}

	

}