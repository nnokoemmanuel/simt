package ppp.simt.service.evaluation;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Module;
import ppp.simt.form.evaluation.CourseForm;




public interface CourseService {
	public Course findById(int courseId);
	public List<Course> findAll();
	public List<Course> findBySpeciality(Speciality speciality);
	public List<Course> findByModule(Module module);
	public void createCourse(Course course);
	public void updateCourse(Course course);
	Course persistCourseBean(CourseForm courseForm, HttpServletRequest httpServletRequest) throws ParseException;
	public void deleteCourse(Course course);
	public List<Course>  findBySpecialityAndOrderByCompleteName(Speciality speciality);
	public List<Course>  findByModuleOrderByCompleteName(Module module);
	public List<Course> findBySpecialityWithoutTranscriptsLink(Speciality speciality);
	public List<Course> findByCompleteName(String courseName);
	public Course findByCompleteNameAndModule(String courseName,Module module);

}
