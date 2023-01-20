package ppp.simt.service.pv;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.EligibleCenterTracking;

import java.util.List;


public interface EligibleCenterTrackingService {

	public void save(EligibleCenterTracking eligibleCenterTracking);
    public ArrayList<EligibleCenterTracking>  findByEligibleCenterId(int id);

}
