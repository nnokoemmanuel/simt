package ppp.simt.repository.production;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.production.ApplicationFile;



@Repository("applicationFileRepository")
public interface ApplicationFileRepository extends JpaRepository <ApplicationFile, Integer>{
	
	ApplicationFile findById(int applicationFileId);

}