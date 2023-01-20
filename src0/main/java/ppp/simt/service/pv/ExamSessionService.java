package ppp.simt.service.pv;

import java.util.List;

import ppp.simt.entity.pv.ExamSession;

public interface ExamSessionService {
	
	public List<ExamSession> findAll();
	public ExamSession findById(int sessionId);
	public List<ExamSession> findByYear(int year);
	public ExamSession findOneBySessionDate(java.util.Date session);
    public void save(ExamSession session);
    public List<ExamSession> findByStatusOrderByIdDesc(String status);
}
