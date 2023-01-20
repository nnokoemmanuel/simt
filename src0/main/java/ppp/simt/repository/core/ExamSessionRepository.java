package ppp.simt.repository.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.pv.ExamSession;


@Repository("ExamSessionRepository")
public interface ExamSessionRepository  extends JpaRepository<ExamSession,Integer> {
	
	List<ExamSession> findAll();
	
	ExamSession findById(int id);

	ExamSession findBySessionDate(String  sessionDate);

	//public void createSession(ExamSession examsession);
	
	
	@Modifying
    @Query(value = "insert into ExamSession (id,sessionDate,createdOn) select :id,:sessionDate,:createdOn from marinedb", nativeQuery = true)
	public void createSession(@Param("id")int id, @Param("sessionDate")String sessionDate, @Param("createdOn")String createdOn);
	
	@Modifying
    @Query(value = "update ExamSession (id,sessionDate1,createdOn) select :id,:sessionDate1,:createdOn from marinedb", nativeQuery = true)
	public void updateSession(@Param("id")int id, @Param("sessionDate1")String sessionDate1, @Param("createdOn")String createdOn);

	List<ExamSession> findBySessionDateGreaterThan(Date now);

	ArrayList<ExamSession> findBySessionDateBetweenOrderBySessionDateAsc(Date today,Date today_);


   
	

}