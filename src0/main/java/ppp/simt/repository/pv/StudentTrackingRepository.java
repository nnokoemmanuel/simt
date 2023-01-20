package ppp.simt.repository.pv;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.tracking.StudentTracking;

public interface StudentTrackingRepository extends JpaRepository<StudentTracking,Long>{

	 public ArrayList<StudentTracking> findByStudentId(int id);
	 
}
