package ppp.simt.service.applicant;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.ApplicantTracking;
import ppp.simt.repository.applicant.ApplicantTrackingRepository;

@Service("ApplicantTrackingService")
public class ApplicantTrackingServiceImpl implements ApplicantTrackingService {
	
	 
		@Autowired
	    private ApplicantTrackingRepository applicantTrackingRepository;

		@Override
		public void save(ApplicantTracking applicant) {
			// TODO Auto-generated method stub
			applicantTrackingRepository.save(applicant);
			
		}

		@Override
		public ArrayList<ApplicantTracking> findByApplicantId(int id) {
			// TODO Auto-generated method stub
			return (ArrayList<ApplicantTracking>) applicantTrackingRepository.findByApplicantId(id);
		}

}