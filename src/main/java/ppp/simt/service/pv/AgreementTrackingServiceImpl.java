package ppp.simt.service.pv;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.tracking.AgreementTracking;
import ppp.simt.repository.pv.AgreementTrackingRepository;

@Service("agreementTrackingService")
public class AgreementTrackingServiceImpl implements AgreementTrackingService {
	
	 
		@Autowired
	    private AgreementTrackingRepository  agreementTrackingRepository;

		@Override
		public void save(AgreementTracking  agreementTracking) {
			// TODO Auto-generated method stub
			 agreementTrackingRepository.save( agreementTracking);
			
		}

		@Override
		public ArrayList<AgreementTracking> findByAgreement(Agreement agreement) {
			// TODO Auto-generated method stub
			return (ArrayList<AgreementTracking>)  agreementTrackingRepository.findByAgreement(agreement);
		}
		
		
		@Override
		public void deleteAgreementTracking(AgreementTracking  agreementTracking) {
			 agreementTrackingRepository.delete( agreementTracking);
		}


		

		

}
