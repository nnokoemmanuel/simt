package ppp.simt.service.pv;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.pv.ExamSession;
import ppp.simt.repository.pv.ExamSessionRepository;

@Service
public class ExamSessionServiceImpl implements ExamSessionService{
	
	@Autowired
	public ExamSessionRepository examSessionRepository;
	
	public ExamSession findById(int id) {
		
		return examSessionRepository.findById(id);
	}
	
	public List<ExamSession> findAll() {
		
		return examSessionRepository.findAll();
	}
	
	public List<ExamSession> findByYear(int year) {
		return examSessionRepository.findByYear(year);
	}

	@Override
	public ExamSession findOneBySessionDate(Date session) {
		// TODO Auto-generated method stub
		return examSessionRepository.findOneBySessionDate(session);
	}

	@Override
	public void save(ExamSession session) {
		// TODO Auto-generated method stub
		examSessionRepository.save(session);
	}

	@Override
	public List<ExamSession> findByStatusOrderByIdDesc(String status) {
		// TODO Auto-generated method stub
		return examSessionRepository.findByStatusOrderByIdDesc(status);
	}

}
