package ppp.simt.repository.applicant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.applicant.EntranceExamCenter;


@Repository("entranceExamCenterRepository")
public interface EntranceExamCenterRepository extends JpaRepository<EntranceExamCenter, Integer>{
	
	public EntranceExamCenter findById(int id);
	public List<EntranceExamCenter> findAll();
	public  EntranceExamCenter findByName(String name);

}
