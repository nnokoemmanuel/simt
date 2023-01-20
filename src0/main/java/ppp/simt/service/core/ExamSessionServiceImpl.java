package ppp.simt.service.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.pv.ExamSession;
import ppp.simt.repository.core.ExamSessionRepository;

@Service("ExamSessionService")
public class ExamSessionServiceImpl implements ExamSessionService {

   @Autowired 
   private ExamSessionRepository examSessionRepository;
	
   @Override
	public List<ExamSession> findAll() {
		// TODO Auto-generated method stub
		return examSessionRepository.findAll();
	}
   
	@Override
	public ExamSession findById(int id) {
		// TODO Auto-generated method stub
		return examSessionRepository.findById(id) ;
	}

	@Override
	public ExamSession findBySessionDate(String sessionDate) {
		// TODO Auto-generated method stub
		return examSessionRepository.findBySessionDate(sessionDate);
	}

	@Override
	public void createSession(ExamSession examsession) {
		// TODO Auto-generated method stub
		examSessionRepository.save(examsession); 
	}

	@Override
	public void updateSession(ExamSession examsession) {
		// TODO Auto-generated method stub
		examSessionRepository.save(examsession); 
	}

	@Override
	public ArrayList<ExamSession> findBySessionDateLike(String date) {
		// TODO Auto-generated method stub
		return examSessionRepository.findBySessionDateBetweenOrderBySessionDateAsc(java.sql.Date.valueOf(date+"-01-01"),java.sql.Date.valueOf(date+"-12-31"));

	}

	@Override
	public ArrayList<ExamSession> findNotYetDoneSession() {
		// TODO Auto-generated method stub
		java.util.Date now = new java.util.Date();	    
		return (ArrayList<ExamSession>) examSessionRepository.findBySessionDateGreaterThan(now);
	}

	@Override
	public ArrayList<ExamSession> findBySessionDateGreaterThan(java.sql.Date date){
		return (ArrayList<ExamSession>) examSessionRepository.findBySessionDateGreaterThan(date);
	}

}

