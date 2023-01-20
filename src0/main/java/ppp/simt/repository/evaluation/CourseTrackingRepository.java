package ppp.simt.repository.evaluation;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.tracking.evaluation.CourseTracking;
import ppp.simt.entity.user.User;

public interface CourseTrackingRepository  extends JpaRepository<CourseTracking,Integer> {

	public ArrayList<CourseTracking>  findByCourse(Course course);
	public ArrayList<CourseTracking> findByUser(User user);
	
}
