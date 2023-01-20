package ppp.simt.repository.core;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.tracking.OutSlipTracking;

public interface OutSlipTrackingRepository extends JpaRepository<OutSlipTracking,Long>{
	
	public ArrayList<OutSlipTracking>  findByOutSlipId(int id);

}
