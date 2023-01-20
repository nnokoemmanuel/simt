package ppp.simt.repository.applicant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.tracking.EntranceEligibleCenterTracking;

public interface EntranceEligibleCenterTrackingRepository  extends JpaRepository<EntranceEligibleCenterTracking,Long> {

	public List<EntranceEligibleCenterTracking> findByEntranceEligibleCenterId(int id);
	
}