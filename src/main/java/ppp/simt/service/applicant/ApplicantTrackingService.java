package ppp.simt.service.applicant;

import java.util.ArrayList;

import ppp.simt.entity.tracking.ApplicantTracking;

public interface ApplicantTrackingService {
	
	public void save(ApplicantTracking applicantTracking);
	public ArrayList<ApplicantTracking> findByApplicantId(int id);
}