package ppp.simt.repository.core;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.tracking.InSlipTracking;

public interface InSlipTrackingRepository  extends JpaRepository<InSlipTracking,Long>{

	public ArrayList<InSlipTracking>  findByInSlipId(int id);
}
