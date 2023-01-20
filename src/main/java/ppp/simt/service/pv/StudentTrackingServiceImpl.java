package ppp.simt.service.pv;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.StudentTracking;
import ppp.simt.repository.pv.StudentTrackingRepository;

@Service("studentTrackingService")
public class StudentTrackingServiceImpl  implements StudentTrackingService{

	@Autowired
    private StudentTrackingRepository studentTrackingRepository;
    
	@Override
	public void save(StudentTracking StudentTracking) {
		// TODO Auto-generated method stub
		studentTrackingRepository.save(StudentTracking);
		
	}

	@Override
	public ArrayList<StudentTracking> findByStudentId(int id) {
		// TODO Auto-generated method stub
		return (ArrayList<StudentTracking>)studentTrackingRepository.findByStudentId(id) ;
	}

}
