package ppp.simt.repository.pv;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.tracking.StudentSessionTracking;

public interface StudentSessionTrackingRepository extends JpaRepository<StudentSessionTracking,Long>{

	 public ArrayList<StudentSessionTracking> findByStudentSessionId(int id);
	 
}
