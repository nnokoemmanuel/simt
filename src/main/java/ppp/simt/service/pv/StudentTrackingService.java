package ppp.simt.service.pv;

import java.util.ArrayList;

import ppp.simt.entity.tracking.StudentTracking;

public interface StudentTrackingService {

	public void save(StudentTracking studentTracking);
	 public ArrayList<StudentTracking>  findByStudentId(int id);
}


