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
import ppp.simt.entity.evaluation.Module;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.tracking.evaluation.CourseTracking;
import ppp.simt.entity.tracking.evaluation.ModuleTracking;
import ppp.simt.form.evaluation.CourseForm;
import ppp.simt.form.evaluation.ModuleForm;
import ppp.simt.service.core.SpecialityService;
import ppp.simt.service.evaluation.CourseService;
import ppp.simt.service.evaluation.CourseTrackingService;
import ppp.simt.service.evaluation.ModuleService;
import ppp.simt.service.evaluation.ModuleTrackingService;
import ppp.simt.service.evaluation.TranscriptService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Controller
@RequestMapping(value = "/module")
public class ModuleController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private SpecialityService specialityService;
	
	@Autowired
	private ModuleService moduleService;
	
	@Autowired
	private TranscriptService transcriptService;
	
	
	@Autowired
	private ModuleTrackingService moduleTrackingService;

	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;


	@Secured({"ROLE_MANAGE_COURSES_AND_MODULES"})
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createGet(Model model) {
		
		model.addAttribute("specialities", specialityService.findAll());
		return "evaluation/module_create";
	}
	
	@Secured({"ROLE_MANAGE_COURSES_AND_MODULES"})
	@RequestMapping(value = "/edit/{moduleId}", method = RequestMethod.GET)
	public String editGet(Model model,@PathVariable int moduleId) {
		Module module = moduleService.findModuleById(moduleId);
		model.addAttribute("module", module);
		model.addAttribute("specialities", specialityService.findAll());
		return "evaluation/module_edit";
	}
	
	@Secured({"ROLE_MANAGE_COURSES_AND_MODULES"})
	@RequestMapping(value = "/show/{moduleId}", method = RequestMethod.GET)
	public String showGet(Model model,@PathVariable int  moduleId) {
		Module module = moduleService.findModuleById(moduleId);
		model.addAttribute("module", module);
		model.addAttribute("speciality", module.getSpeciality()); 
		return "evaluation/module_show";
	}

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@Transactional
	@Secured({"ROLE_MANAGE_COURSES_AND_MODULES"})
	public String createCourse(@ModelAttribute("moduleform") @Validated ModuleForm moduleForm, BindingResult result, HttpServletRequest httpServletRequest) throws ParseException {
		Module module = null;
		if(result.hasErrors()){
			if(result.getFieldError("vide")!= null){
				logger_.log(Constants.NORMAL_LOG_DIR, "module form empty "+result.getFieldError("vide").getCode(),httpServletRequest);
				return result.getFieldError("vide").getCode();
				
			}
		} 
		if(moduleForm.getId() != -1 ){
			module = moduleService.persistModuleBean(moduleForm, httpServletRequest);
	    }else{
	    	Speciality speciality = specialityService.findByAbbreviation(moduleForm.getSpecialityAbbreviation());
	    	if(moduleService.findByModuleClassificationAndSpeciality(moduleForm.getModuleClassification(), speciality).size() > 0)
	    		return "CLASSIFICATION-EXISTS";
	    	module = moduleService.persistModuleBean(moduleForm, httpServletRequest);
	
	    }
	    return  "OK-"+String.valueOf(module.getId());

	}

	/*
	 *@Author MPA
	 *this function helps to load list of Modules 
	 */
	
	@RequestMapping(value = {"/list/{speciality}"})
	public String listCourse(@PathVariable String speciality,Model model) {
		ArrayList<Module> modules = null;
		if(!speciality.equals("ALL")){
			Long specialityId = specialityService.findByAbbreviation(speciality).getId();
	        Speciality specialityRetrieved = specialityService.findById(specialityId);
			modules = (ArrayList<Module>) moduleService.findBySpeciality(specialityRetrieved);
		
		}else
			modules = (ArrayList<Module>) moduleService.findAll();
		ArrayList<Speciality> specialities = (ArrayList<Speciality>) specialityService.findAll();
		model.addAttribute("specialities", specialities);	
		model.addAttribute("modules", modules);		
		return "evaluation/module_list";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/delete/{moduleId}")
	@Secured({"ROLE_MANAGE_COURSES_AND_MODULES"})
	public String deleteCourse(@PathVariable int moduleId, HttpServletRequest httpServletRequest) {
		Module module = moduleService.findModuleById(moduleId);
		List<Course> courses = new ArrayList<Course>();
		courses = courseService.findByModule(module);
		List<Transcript> listeOfTranscriptsRelatedToCourse = new ArrayList<Transcript>();
		for(Course course : courses){
		 listeOfTranscriptsRelatedToCourse.addAll(transcriptService.findByCourse(course));
		}
		if(listeOfTranscriptsRelatedToCourse.size() == 0){
			ArrayList<ModuleTracking> listeOfTrackings= moduleTrackingService.findByModule(module);
			listeOfTrackings.forEach((tracking)->{moduleTrackingService.deleteModuleTracking(tracking);});
			moduleService.deleteModule(module); 
	        logger_.log(Constants.NORMAL_LOG_DIR, "Delete the module: " + module.getCompleteName(), httpServletRequest);		
			return "OK";
		}
		return "KO";
	}

	

}