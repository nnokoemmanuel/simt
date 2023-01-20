package ppp.simt.repository.pv;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.pv.ExamCenter;

public interface ExamCenterRepository extends JpaRepository<ExamCenter, Integer>{
	
	public ExamCenter findById(int id);
	public List<ExamCenter> findAll();
	public  ExamCenter findByName(String name);

}
