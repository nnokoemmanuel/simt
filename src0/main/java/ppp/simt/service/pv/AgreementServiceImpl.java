package ppp.simt.service.pv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.tracking.TrainingCenterTracking;
import ppp.simt.repository.pv.AgreementRepository;

@Service
public class AgreementServiceImpl implements AgreementService {

	@Autowired
	private AgreementRepository agreementRepository;
	
	@Override
	public List<Agreement> findAll() {
		// TODO Auto-generated method stub
		return agreementRepository.findAll();
	}

	@Override
	public Agreement findById(int id) {
		// TODO Auto-generated method stub
		return agreementRepository.findById(id);
	}
	
	@Override
	public List<Agreement> findByStatus(int status) {
		// TODO Auto-generated method stub
		return agreementRepository.findByStatus(status);
	}

	@Override
	public void createAgreement(Agreement agreement) {
		agreementRepository.save(agreement);
		
	}
	
	@Override
	public void updateAgreement(Agreement agreement) {
		agreementRepository.saveAndFlush(agreement);

	}
    
	
	@Override
	public List<Agreement> findByTrainingCenter(TrainingCenter trainingCenter) {
		// TODO Auto-generated method stub
		return agreementRepository.findByTrainingCenter(trainingCenter);
	}
	
	@Override
	public void deleteAgreement(Agreement  agreement) {
		agreementRepository.delete( agreement);
	}
}
