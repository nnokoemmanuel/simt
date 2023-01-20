package ppp.simt.service.pv;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.pv.EligibleSpeciality;

import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.repository.pv.EligibleSpecialityRepository;


@Service
public class EligibleSpecialityServiceImpl implements EligibleSpecialityService {

	@Autowired
	private EligibleSpecialityRepository eligibleSpecialityRepository;
	@Autowired
	EntityManager em;
	

	@Override
	public EligibleSpeciality findById(int eligibleSpecialityId) {
		
		return eligibleSpecialityRepository.findById(eligibleSpecialityId);
	}
	
	@Override
	public List<EligibleSpeciality> findByTrainingCenter(TrainingCenter trainingCenter){
		return eligibleSpecialityRepository.findByTrainingCenter(trainingCenter);
	}
	
	
	@Override
	public void createEligibleSpeciality(EligibleSpeciality eligibleSpeciality) {
		eligibleSpecialityRepository.save(eligibleSpeciality);
		
	}
	
	@Override
	public void updateEligibleSpeciality(EligibleSpeciality eligibleSpeciality) {
		eligibleSpecialityRepository.saveAndFlush(eligibleSpeciality);

	}
	
	@Override
	public void deleteEligibleSpeciality(EligibleSpeciality eligibleSpeciality) {
		eligibleSpecialityRepository.delete(eligibleSpeciality);
	}

	@Override
	public List<EligibleSpeciality> findAll() {
		return eligibleSpecialityRepository.findAll();
	}
	
	

}
