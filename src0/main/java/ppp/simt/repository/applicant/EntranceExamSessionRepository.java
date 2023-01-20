package ppp.simt.repository.applicant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.applicant.EntranceExamSession;

@Repository("entranceExamSessionRepository")
public interface EntranceExamSessionRepository extends JpaRepository<EntranceExamSession, Integer>{
	
	public EntranceExamSession findById(int id);
	public List<EntranceExamSession> findAll();
	
	@Query("SELECT es from EntranceExamSession es where es.sessionDate LIKE CONCAT(:year,'%') Order By es.id ASC")
	public List<EntranceExamSession> findByYear(@Param("year") int  year);
    
	public EntranceExamSession findOneBySessionDate(java.util.Date session);
    
	public List<EntranceExamSession> findByStatusOrderByIdDesc(String status);

}
