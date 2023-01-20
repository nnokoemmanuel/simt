package ppp.simt.service.core;

import java.util.ArrayList;

import ppp.simt.entity.tracking.ApplicationTracking;

public interface ApplicationTrackingService {
	
	public void save(ApplicationTracking application);
	public ArrayList<ApplicationTracking> findByApplicationId(int id);

}
