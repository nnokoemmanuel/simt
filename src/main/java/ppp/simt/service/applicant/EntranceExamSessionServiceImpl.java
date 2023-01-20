package ppp.simt.service.applicant;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.applicant.EntranceExamSession;
import ppp.simt.repository.applicant.EntranceExamSessionRepository;


@Service
public class EntranceExamSessionServiceImpl implements EntranceExamSessionService{
	
	@Autowired
	public EntranceExamSessionRepository entranceExamSessionRepository;
	
	public EntranceExamSession findById(int id) {
		
		return entranceExamSessionRepository.findById(id);
	}
	
	public List<EntranceExamSession> findAll() {
		
		return entranceExamSessionRepository.findAll();
	}
	
	public List<EntranceExamSession> findByYear(int year) {
		return entranceExamSessionRepository.findByYear(year);
	}

	@Override
	public EntranceExamSession findOneBySessionDate(Date session) {
		// TODO Auto-generated method stub
		return entranceExamSessionRepository.findOneBySessionDate(session);
	}

	@Override
	public void save(EntranceExamSession session) {
		// TODO Auto-generated method stub
		entranceExamSessionRepository.save(session);
	}

	@Override
	public List<EntranceExamSession> findByStatusOrderByIdDesc(String status) {
		// TODO Auto-generated method stub
		return entranceExamSessionRepository.findByStatusOrderByIdDesc(status);
	}

}
