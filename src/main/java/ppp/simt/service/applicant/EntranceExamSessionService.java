package ppp.simt.service.applicant;

import java.util.List;

import ppp.simt.entity.applicant.EntranceExamSession;


public interface EntranceExamSessionService {
	
	public List<EntranceExamSession> findAll();
	public EntranceExamSession findById(int sessionId);
	public List<EntranceExamSession> findByYear(int year);
	public EntranceExamSession findOneBySessionDate(java.util.Date session);
    public void save(EntranceExamSession session);
    public List<EntranceExamSession> findByStatusOrderByIdDesc(String status);
}
