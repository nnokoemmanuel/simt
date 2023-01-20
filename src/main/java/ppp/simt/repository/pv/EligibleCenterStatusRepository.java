package ppp.simt.repository.pv;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import ppp.simt.entity.pv.EligibleCenterStatus;


@Repository("eligibleCenterStatusRepository")
public interface EligibleCenterStatusRepository extends JpaRepository <EligibleCenterStatus, Integer>{
	
	EligibleCenterStatus findById(int eligibleCenterId);


}

