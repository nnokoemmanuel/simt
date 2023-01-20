package ppp.simt.service.applicant;

import java.util.ArrayList;

import ppp.simt.entity.tracking.EntranceEligibleCenterTracking;

public interface EntranceEligibleCenterTrackingService {
	
	public void save(EntranceEligibleCenterTracking entranceEligibleCenterTracking);
	public ArrayList<EntranceEligibleCenterTracking> findByEntranceEligibleCenterId(int id);
}