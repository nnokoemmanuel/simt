package ppp.simt.repository.core;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.tracking.ApplicationTracking;

public interface ApplicationTrackingRepository  extends JpaRepository<ApplicationTracking,Long>{

	
	public ArrayList<ApplicationTracking> findByApplicationId(int id);
}
