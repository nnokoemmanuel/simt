package ppp.simt.service.pv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.pv.StudentQualification;
import ppp.simt.repository.pv.StudentQualificationRepository;

@Service
public class StudentQualificationServiceImpl implements StudentQualificationService{
	
	@Autowired
	private StudentQualificationRepository studentQualificationRepository;

	@Override
	public void save(StudentQualification studentQualification) {
		studentQualificationRepository.save(studentQualification);
		
	}

}
