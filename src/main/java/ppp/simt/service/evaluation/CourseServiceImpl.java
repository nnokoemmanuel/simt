package ppp.simt.service.evaluation;


import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppp.simt.repository.evaluation.CourseRepository;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Module;
import ppp.simt.entity.user.User;
import ppp.simt.form.evaluation.CourseForm;


@Service("courseService")
public class CourseServiceImpl implements CourseService{

	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	EntityManager em;
	
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;



	@Override
	public List<Course> findAll() {
		return courseRepository.findAll();
	}
	
	@Override
	public void createCourse(Course course) {
		courseRepository.save(course);
		
	}
	
	@Override
	public void updateCourse(Course course) {
		courseRepository.saveAndFlush(course);

	}


	@Override
	public Course findById(int courseId) {
		return courseRepository.findById(courseId);
	}

	@Override
	public List<Course> findBySpeciality(Speciality speciality) {
		return courseRepository.findBySpeciality(speciality, em);
	}
	
	/**
	 * Convertie un formulaire en bean
	 * @return
	 */
	@Override
	public Course persistCourseBean(CourseForm courseForm,HttpServletRequest httpServletRequest) throws ParseException{
		
		User user = userService.getLogedUser(httpServletRequest);
		Course course = null;
		Module module = null;
		if(courseForm.getModuleId() != -1)
		   module = moduleService.findModuleById(courseForm.getModuleId());	
		
		if(courseForm.getId() ==0  ){
		    //case module does not exists yet
			//we create first Person instance
			if(module != null){
				course = new Course(courseForm.getCompleteName(),  module, courseForm.getCourseCoeff(), courseForm.getCourseMaxNote());
				courseService.createCourse(course);
				tracker.track(course, "REGISTERED THE COURSE" , httpServletRequest);
				logger_.log(Constants.NORMAL_LOG_DIR, "Initiates the course Registration: "+user);	
			}
			
			return course;
			
		}else{
			Course courseToEdit = courseService.findById(courseForm.getId());
			courseToEdit.setCompleteName(courseForm.getCompleteName());
			courseToEdit.setCourseCoefficient(courseForm.getCourseCoeff());
			courseToEdit.setCourseMaxNote(courseForm.getCourseMaxNote());
		    courseService.updateCourse(courseToEdit);
			tracker.track(courseToEdit, "EDITED THE COURSE " , httpServletRequest);
    		logger_.log(Constants.NORMAL_LOG_DIR, "Initiates the course Edition: "+user);
			return courseToEdit;
		}
	}
	
	
	@Override
	public void deleteCourse(Course course) {
		courseRepository.delete(course);
	}
	
	@Override
	public List<Course> findByModule(Module module) {
		return courseRepository.findByModule(module);
	}
	
	@Override
	public List<Course> findBySpecialityAndOrderByCompleteName(Speciality speciality){
		return courseRepository.findBySpecialityAndOrderByCompleteName( speciality,  em);
	}
	
	@Override
	public List<Course>  findByModuleOrderByCompleteName(Module module){
		return courseRepository.findByModuleOrderByCompleteName(module);
	}
	
	@Override
	public List<Course> findBySpecialityWithoutTranscriptsLink(Speciality speciality){
		return courseRepository.findBySpecialityWithoutTranscriptsLink(speciality, em);
		
	}

	@Override
	public List<Course> findByCompleteName(String courseName) {
		// TODO Auto-generated method stub
		return courseRepository.findByCompleteName(courseName);
	}

	@Override
	public Course findByCompleteNameAndModule(String courseName, Module module) {
		// TODO Auto-generated method stub
		return courseRepository.findByCompleteNameAndModule( courseName,  module);
	}




}



