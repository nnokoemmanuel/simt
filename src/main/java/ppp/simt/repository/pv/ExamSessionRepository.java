package ppp.simt.repository.pv;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ppp.simt.entity.pv.ExamSession;

public interface ExamSessionRepository extends JpaRepository<ExamSession, Integer>{
	
	public ExamSession findById(int id);
	public List<ExamSession> findAll();
	
	@Query("SELECT es from ExamSession es where es.sessionDate LIKE CONCAT(:year,'%') Order By es.id ASC")
	public List<ExamSession> findByYear(@Param("year") int  year);
    
	public ExamSession findOneBySessionDate(java.util.Date session);
    
	public List<ExamSession> findByStatusOrderByIdDesc(String status);

}
