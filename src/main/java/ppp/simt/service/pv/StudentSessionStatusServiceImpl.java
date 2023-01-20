package ppp.simt.service.pv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.pv.StudentSessionStatus;
import ppp.simt.repository.pv.StudentSessionStatusRepository;

@Service("studentSessionStatusService")
public class StudentSessionStatusServiceImpl implements StudentSessionStatusService{

	@Autowired
	private StudentSessionStatusRepository studentSessionStatusRepository;
	
	
	@Override
	public StudentSessionStatus findById(int studentSessionStatusId) {
		// TODO Auto-generated method stub
		return studentSessionStatusRepository.findById(studentSessionStatusId);
	}

}
