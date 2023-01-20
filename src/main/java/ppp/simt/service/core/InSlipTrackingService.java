package ppp.simt.service.core;

import java.util.ArrayList;

import ppp.simt.entity.tracking.InSlipTracking;

public interface InSlipTrackingService {
	
	public void save(InSlipTracking inSlip);
	public ArrayList<InSlipTracking>  findByInSlipId(int id);

}
