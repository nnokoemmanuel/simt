package ppp.simt.service.core;

import java.util.ArrayList;

import ppp.simt.entity.tracking.OutSlipTracking;

public interface OutSlipTrackingService {
	
	public void save(OutSlipTracking outSlip);
	public ArrayList<OutSlipTracking>  findByOutSlipId(int id);


}
