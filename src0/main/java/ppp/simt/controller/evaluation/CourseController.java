/**
* @author MPA
* Cette classe permet d effectuer le CRUD pour les Cours
* 
*/
package ppp.simt.controller.evaluation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.tracking.evaluation.CourseTracking;
import ppp.simt.form.evaluation.CourseForm;
import ppp.simt.service.core.SpecialityService;
import ppp.simt.service.evaluation.CourseService;
import ppp.simt.service.evaluation.CourseTrackingService;
import ppp.simt.service.evaluation.ModuleService;
import ppp.simt.service.evaluation.TranscriptService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Controller
@RequestMapping(value = "/course")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private SpecialityService specialityService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private TranscriptService transcriptService;
	
	
	@Autowired
	private CourseTrackingService courseTrackingService;

	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;


	@Secured({"ROLE_MANAGE_COURSES_AND_MODULES"})
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createGet(Model model) {
		
		model.addAttribute("modules", moduleService.findAll());
		return "evaluation/course_create";
	}
	
	@Secured({"ROLE_MANAGE_COURSES_AND_MODULES"})
	@RequestMapping(value = "/edit/{courseId}", method = RequestMethod.GET)
	public String editGet(Model model,@PathVariable int courseId) {
		Course course = courseService.findById(courseId);
		model.addAttribute("course", course);
		model.addAttribute("modules", moduleService.findAll());
		return "evaluation/course_edit";
	}
	
	@Secured({"ROLE_MANAGE_COURSES_AND_MODULES"})
	@RequestMapping(value = "/show/{courseId}", method = RequestMethod.GET)
	public String showGet(Model model,@PathVariable int  courseId) {
		Course course = courseService.findById(courseId);
		model.addAttribute("course", course);
		model.addAttribute("module", course.getModule()); 
		return "evaluation/course_show";
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@Transactional
	@Secured({"ROLE_MANAGE_COURSES_AND_MODULES"})
	public String createCourse(@ModelAttribute("courseform") @Validated CourseForm courseForm, BindingResult result, HttpServletRequest httpServletRequest) throws ParseException {
		Course course = null;
		if(result.hasErrors()){
			if(result.getFieldError("vide")!= null){
				logger_.log(Constants.NORMAL_LOG_DIR, "course form empty "+result.getFieldError("vide").getCode(),httpServletRequest);
				return result.getFieldError("vide").getCode();
				
			}
		} 
		if(courseForm.getId() != -1 ){
			course = courseService.persistCourseBean(courseForm, httpServletRequest);
	    }else{
	    	course = courseService.persistCourseBean(courseForm, httpServletRequest);
	
	    }
	    return  "OK-"+String.valueOf(course.getId());

	}

	/*
	 *@Author MPA
	 *this function helps to load list of Courses 
	 */
	
	@RequestMapping(value = {"/list/{speciality}"})
	public String listCourse(@PathVariable String speciality,Model model) {
		ArrayList<Course> courses = null;
		if(!speciality.equals("ALL")){
			Long specialityId = specialityService.findByAbbreviation(speciality).getId();
	        Speciality specialityRetrieved = specialityService.findById(specialityId);
			courses = (ArrayList<Course>) courseService.findBySpeciality(specialityRetrieved);
		
		}else
			courses = (ArrayList<Course>) courseService.findAll();
		ArrayList<Speciality> specialities = (ArrayList<Speciality>) specialityService.findAll();
		model.addAttribute("specialities", specialities);	
		model.addAttribute("courses", courses);		
		return "evaluation/course_list";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/delete/{courseId}")
	@Secured({"ROLE_MANAGE_COURSES_AND_MODULES"})
	public String deleteCourse(@PathVariable int courseId, HttpServletRequest httpServletRequest) {
		Course course = courseService.findById(courseId);
		List<Transcript> listeOfTranscriptsRelatedToCourse = transcriptService.findByCourse(course);
		if(listeOfTranscriptsRelatedToCourse.size() == 0){
			ArrayList<CourseTracking> listeOfTrackings= courseTrackingService.findByCourse( course);
			listeOfTrackings.forEach((tracking)->{courseTrackingService.deleteCourseTracking(tracking);});
			courseService.deleteCourse(course); 
	        logger_.log(Constants.NORMAL_LOG_DIR, "Delete the course: " + course.getCompleteName(), httpServletRequest);		
			return "OK";
		}
		return "KO";
	}

	

}