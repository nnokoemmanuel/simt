package ppp.simt.repository.pv;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.tracking.EligibleCenterTracking;



public interface EligibleCenterTrackingRepository extends JpaRepository<EligibleCenterTracking,Long>{
	
	public ArrayList<EligibleCenterTracking>  findByEligibleCenterId(int id);

}