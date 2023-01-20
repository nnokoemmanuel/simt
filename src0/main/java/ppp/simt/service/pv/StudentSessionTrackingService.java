package ppp.simt.service.pv;

import java.util.ArrayList;

import ppp.simt.entity.tracking.StudentSessionTracking;

public interface StudentSessionTrackingService {

	public void save(StudentSessionTracking StudentSessionTracking);
	 public ArrayList<StudentSessionTracking>  findByStudentSessionId(int id);
}
