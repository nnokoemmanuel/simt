package ppp.simt.repository.pv;


import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import ppp.simt.entity.pv.EligibleSpeciality;

import ppp.simt.entity.pv.TrainingCenter;





@Repository("eligibleSpecialityRepository")
public interface EligibleSpecialityRepository extends JpaRepository <EligibleSpeciality, Integer>{
	
	EligibleSpeciality findById(int eligibleSpecialityId);

	List<EligibleSpeciality> findByTrainingCenter(TrainingCenter trainingCenter);

	 
}

