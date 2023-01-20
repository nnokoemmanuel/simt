package ppp.simt.service.pv;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.StudentSessionTracking;
import ppp.simt.repository.pv.StudentSessionTrackingRepository;

@Service("candidateSessionTrackingService")
public class StudentSessionTrackingServiceImpl  implements StudentSessionTrackingService{

	@Autowired
    private StudentSessionTrackingRepository studentSessionTrackingRepository;
    
	@Override
	public void save(StudentSessionTracking StudentSessionTracking) {
		// TODO Auto-generated method stub
		studentSessionTrackingRepository.save(StudentSessionTracking);
		
	}

	@Override
	public ArrayList<StudentSessionTracking> findByStudentSessionId(int id) {
		// TODO Auto-generated method stub
		return (ArrayList<StudentSessionTracking>)studentSessionTrackingRepository.findByStudentSessionId(id) ;
	}

}
