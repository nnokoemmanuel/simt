package ppp.simt.service.pv;

import java.util.ArrayList;

import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.tracking.AgreementTracking;


public interface AgreementTrackingService {
	
	public void save(AgreementTracking agreementTracking);
	public ArrayList<AgreementTracking> findByAgreement(Agreement agreement);
	public void deleteAgreementTracking(AgreementTracking agreementTracking);
	
}
