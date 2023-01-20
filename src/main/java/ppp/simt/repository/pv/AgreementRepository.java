package ppp.simt.repository.pv;


import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.pv.TrainingCenter;




@Repository("agreementRepository")
public interface AgreementRepository extends JpaRepository <Agreement, Integer>{
	
	Agreement findById(int agreementId);
	public List<Agreement> findAll(); 
	public List<Agreement> findByStatus(int status); 
	public List<Agreement> findByTrainingCenter(TrainingCenter trainingCenter); 
	public Agreement save(Agreement agreement);
	
	

	
 
    
    

	

}
