package ppp.simt.service.pv;

import java.util.List;

import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.ExamSession;

public interface ExamCenterService {
	
	public List<ExamCenter> findAll();
	public ExamCenter findById(int id);
	public ExamCenter findByName(String name);
	public void save(ExamCenter center);
	

}
