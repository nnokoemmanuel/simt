package ppp.simt.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Office;



@Repository("officeRepository")
public interface OfficeRepository extends JpaRepository <Office, Integer>{
	
	Office findById(int office_id);

}