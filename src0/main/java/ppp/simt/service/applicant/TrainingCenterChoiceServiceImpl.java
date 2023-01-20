package ppp.simt.service.applicant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.applicant.TrainingCenterChoice;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.repository.applicant.TrainingCenterChoiceRepository;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("trainingCenterChoiceService")
public class TrainingCenterChoiceServiceImpl implements TrainingCenterChoiceService{
	
	@Autowired
	EntityManager em;

	@Autowired
	private TrainingCenterChoiceRepository trainingCenterChoiceRepository;


	@Override
	public TrainingCenterChoice findById(int trainingCenterChoice) {
		return trainingCenterChoiceRepository.findById(trainingCenterChoice);
	}

	@Override
	public void save(TrainingCenterChoice trainingCenterChoice) {
		trainingCenterChoiceRepository.save(trainingCenterChoice);
		
	}

	@Override
	public TrainingCenterChoice findTrainingCenterIdByApplicantIdAndFirstChoice(Applicant applicant, String order) {
		return trainingCenterChoiceRepository.findTrainingCenterIdByApplicantIdAndFirstChoice(applicant, order);
	}
}



