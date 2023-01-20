package ppp.simt.repository.applicant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.tracking.ApplicantTracking;

public interface ApplicantTrackingRepository  extends JpaRepository<ApplicantTracking,Long> {

	public List<ApplicantTracking> findByApplicantId(int id);
	
}