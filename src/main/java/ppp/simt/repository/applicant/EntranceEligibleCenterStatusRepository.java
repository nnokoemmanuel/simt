package ppp.simt.repository.applicant;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.applicant.EntranceEligibleCenterStatus;





@Repository("entranceEligibleCenterStatusRepository")
public interface EntranceEligibleCenterStatusRepository extends JpaRepository <EntranceEligibleCenterStatus, Integer>{
	
	EntranceEligibleCenterStatus findById(int entranceEligibleCenterStarusId);


}

