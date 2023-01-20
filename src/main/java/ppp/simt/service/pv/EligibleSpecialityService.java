package ppp.simt.service.pv;

import java.util.List;

import ppp.simt.entity.pv.EligibleSpeciality;

import ppp.simt.entity.pv.TrainingCenter;



public interface EligibleSpecialityService {
	
	public EligibleSpeciality findById(int eligibleSpecialityId);	
	public List<EligibleSpeciality> findByTrainingCenter(TrainingCenter trainingCenter);
	public void createEligibleSpeciality(EligibleSpeciality eligibleSpeciality);
	public void updateEligibleSpeciality(EligibleSpeciality eligibleSpeciality);
	public void deleteEligibleSpeciality(EligibleSpeciality eligibleSpeciality);
	public List<EligibleSpeciality> findAll();

}
