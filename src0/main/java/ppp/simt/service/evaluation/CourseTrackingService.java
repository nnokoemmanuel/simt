package ppp.simt.service.evaluation;

import java.util.ArrayList;

import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.tracking.evaluation.CourseTracking;

public interface CourseTrackingService {
	
	public void save(CourseTracking courseTracking);
	public ArrayList<CourseTracking> findByCourse(Course course);
	public void deleteCourseTracking(CourseTracking courseTracking);
	
}
