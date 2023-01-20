package ppp.simt.service.applicant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.applicant.EntranceExamCenter;
import ppp.simt.repository.applicant.EntranceExamCenterRepository;

@Service
public class EntranceExamCenterServiceImpl implements EntranceExamCenterService {

	@Autowired
	private EntranceExamCenterRepository entranceExamCenterRepository;
	
	@Override
	public List<EntranceExamCenter> findAll() {
		// TODO Auto-generated method stub
		return entranceExamCenterRepository.findAll();
	}

	@Override
	public EntranceExamCenter findById(int id) {
		// TODO Auto-generated method stub
		return entranceExamCenterRepository.findById(id);
	}
	
	@Override
	public EntranceExamCenter findByName(String name) {
		// TODO Auto-generated method stub
		return entranceExamCenterRepository.findByName(name);
	}

}
