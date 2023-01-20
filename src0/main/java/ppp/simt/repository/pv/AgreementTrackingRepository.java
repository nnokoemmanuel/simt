package ppp.simt.repository.pv;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.tracking.AgreementTracking;
import ppp.simt.entity.user.User;

public interface AgreementTrackingRepository  extends JpaRepository<AgreementTracking,Integer> {

	public ArrayList<AgreementTracking>  findByAgreement(Agreement agreement);
	public ArrayList<AgreementTracking> findByUser(User user);
	
}
