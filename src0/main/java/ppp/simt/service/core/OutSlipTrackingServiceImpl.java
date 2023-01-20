package ppp.simt.service.core;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.OutSlipTracking;
import ppp.simt.repository.core.OutSlipTrackingRepository;

@Service("OutSlipTrackingService")
public class OutSlipTrackingServiceImpl implements OutSlipTrackingService  {


	@Autowired
    private OutSlipTrackingRepository outSlipTrackingRepository;

	
	@Override
	public void save(OutSlipTracking outSlip) {
		// TODO Auto-generated method stub
		outSlipTrackingRepository.save(outSlip);
	}

	@Override
	public ArrayList<OutSlipTracking> findByOutSlipId(int id) {
		// TODO Auto-generated method stub
		return  (ArrayList<OutSlipTracking>) outSlipTrackingRepository.findByOutSlipId(id);
	}

}
