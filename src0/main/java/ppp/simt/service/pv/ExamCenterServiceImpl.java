package ppp.simt.service.pv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.repository.pv.ExamCenterRepository;

@Service
public class ExamCenterServiceImpl implements ExamCenterService {

	@Autowired
	private ExamCenterRepository examCenterRepository;
	
	@Override
	public List<ExamCenter> findAll() {
		// TODO Auto-generated method stub
		return examCenterRepository.findAll();
	}

	@Override
	public ExamCenter findById(int id) {
		// TODO Auto-generated method stub
		return examCenterRepository.findById(id);
	}
	
	@Override
	public ExamCenter findByName(String name) {
		// TODO Auto-generated method stub
		return examCenterRepository.findByName(name);
	}

	@Override
	public void save(ExamCenter center) {
		 examCenterRepository.save(center);
		
	}

}
