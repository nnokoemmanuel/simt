package ppp.simt.repository.production;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.production.ProcessType;


@Repository("processTypeRepository")
public interface ProcessTypeRepository extends JpaRepository <ProcessType, Integer>{

    ProcessType findById(int processTypeId);
    public ProcessType findByAbbreviation(String abbreviation);
	
}
