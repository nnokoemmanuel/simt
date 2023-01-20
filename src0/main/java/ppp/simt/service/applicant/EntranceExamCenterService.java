package ppp.simt.service.applicant;

import java.util.List;

import ppp.simt.entity.applicant.EntranceExamCenter;


public interface EntranceExamCenterService {
	
	public List<EntranceExamCenter> findAll();
	public EntranceExamCenter findById(int id);
	public EntranceExamCenter findByName(String name);
	

}
