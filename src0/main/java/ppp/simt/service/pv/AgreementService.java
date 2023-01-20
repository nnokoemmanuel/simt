package ppp.simt.service.pv;

import java.util.List;
import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.pv.TrainingCenter;


public interface AgreementService {
	
	public List<Agreement> findAll();
	public Agreement findById(int id);
	public List<Agreement> findByStatus(int status);
	public void createAgreement(Agreement agreement);
	public void updateAgreement(Agreement agreement);
	List<Agreement> findByTrainingCenter(TrainingCenter trainingCenter);
	public void deleteAgreement(Agreement  agreement) ;

	

}
