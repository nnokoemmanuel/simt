package ppp.simt.service.core;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.InSlipTracking;
import ppp.simt.repository.core.InSlipTrackingRepository;

@Service("InSlipTrackingService")
public class InSlipTrackingServiceImpl implements InSlipTrackingService {

	@Autowired
    private InSlipTrackingRepository inSlipTrackingRepository;

	@Override
	public void save(InSlipTracking inSlip) {
		// TODO Auto-generated method stub
		inSlipTrackingRepository.save(inSlip);
	}

	@Override
	public ArrayList<InSlipTracking> findByInSlipId(int id) {
		// TODO Auto-generated method stub
		return (ArrayList<InSlipTracking>) inSlipTrackingRepository.findByInSlipId(id) ;
	}

	
}
