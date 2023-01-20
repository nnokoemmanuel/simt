package ppp.simt.service.core;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ppp.simt.entity.pv.ExamSession;

public interface ExamSessionService {

	List<ExamSession> findAll();
	
	ExamSession findById(int id);
	
	ExamSession findBySessionDate(String sessionDate);

	public void createSession(ExamSession examsession);
	
	public void updateSession(ExamSession examsession);
	

	ArrayList<ExamSession> findBySessionDateLike(String requestedCalendar);

	List<ExamSession> findNotYetDoneSession();
	
    public ArrayList<ExamSession> findBySessionDateGreaterThan(java.sql.Date date);


}