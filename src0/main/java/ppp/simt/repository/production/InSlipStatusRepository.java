package ppp.simt.repository.production;

import org.springframework.stereotype.Repository;
import ppp.simt.entity.production.InSlipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository("inSlipStatusRepository")
public interface InSlipStatusRepository extends JpaRepository<InSlipStatus, Integer>{
	
	InSlipStatus findById(int id);
}
