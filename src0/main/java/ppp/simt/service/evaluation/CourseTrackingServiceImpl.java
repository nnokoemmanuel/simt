package ppp.simt.service.evaluation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.tracking.evaluation.CourseTracking;
import ppp.simt.repository.evaluation.CourseTrackingRepository;



@Service("courseTrackingService")
public class CourseTrackingServiceImpl implements CourseTrackingService {
	
	 
		@Autowired
	    private CourseTrackingRepository courseTrackingRepository;

		@Override
		public void save(CourseTracking courseTracking) {
			// TODO Auto-generated method stub
			courseTrackingRepository.save(courseTracking);
			
		}

		@Override
		public ArrayList<CourseTracking> findByCourse(Course course) {
			// TODO Auto-generated method stub
			return (ArrayList<CourseTracking>) courseTrackingRepository.findByCourse(course);
		}
		
		
		@Override
		public void deleteCourseTracking(CourseTracking courseTracking) {
			courseTrackingRepository.delete(courseTracking);
		}

}
