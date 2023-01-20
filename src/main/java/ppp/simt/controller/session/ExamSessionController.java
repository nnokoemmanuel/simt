package ppp.simt.controller.session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ppp.simt.entity.pv.ExamSession;
import ppp.simt.service.core.ExamSessionService;
import ppp.simt.util.Constants;



@Controller
public class ExamSessionController {
	
	@Autowired
	ExamSessionService examSessionService;
	
	
	
	
	 @RequestMapping(value="/marine/session/list", method=RequestMethod.GET)
		
	 public String ExamSessionModule(HttpServletRequest httpServletRequest, Model model){	
		
		 
		 List<ExamSession> session = examSessionService.findAll();
		 model.addAttribute("session", session);
		 
		   return "session/list"; 
		}

	

	 
	 @RequestMapping(value="/marine/session/update", method=RequestMethod.GET)
	 @Secured({"ROLE_MANAGE_ELIGIBLE_CENTER"})
	 public String updateExamSessionGET(HttpServletRequest httpServletRequest,
				                       @RequestParam("id") int id, 
				                       Model model){	
		 
		 ExamSession session1 = examSessionService.findById(id);
		
         model.addAttribute("session1", session1);
         model.addAttribute("id", id);
		  
		 return "session/update"; 
		}
	 
		 
	
	@RequestMapping(value = "/marine/session/update/post/{id}/{sessionDate1}", method=RequestMethod.GET)
	//@Secured({"ROLE_MANAGE_ELIGIBLE_CENTER","ROLE_CLOSE_ELIGIBLE_CENTER"})
	public String updateExamSessionPOST(
				                       @PathVariable("id") int id, 
				                       @PathVariable("sessionDate1") String sessionDate1,
				                       HttpServletRequest httpServletRequest,
				                       Model model) {
	 
		ExamSession examsession = examSessionService.findById(id);

		examsession.setSessionDate(java.sql.Date.valueOf(sessionDate1));

		examSessionService.updateSession(examsession);
		 	
		List<ExamSession> session = examSessionService.findAll();
		model.addAttribute("session", session);
	  
		return "session/list"; 
		
		   
	 }
	
	
	 @RequestMapping(value="/marine/session/list/{date}", method=RequestMethod.GET)
		
		public ModelAndView searchSessionGET(@PathVariable Optional<String> date){	 
	
		int maxYear = Calendar.getInstance().get(Calendar.YEAR) + 1;

		String requestedCalendar = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));

		
		//test if the date passed between the min year and the max year
		if (date.isPresent() && (Integer.parseInt(date.get()) >= Constants.FIRST_YEAR_DEPLOY && Integer.parseInt(date.get()) <= maxYear))
			requestedCalendar = date.get();
		
		ArrayList<ExamSession> sessions = (ArrayList<ExamSession>) examSessionService.findBySessionDateLike(requestedCalendar);

		ModelAndView model = new ModelAndView("session/list", "sessionSearch", sessions);

		model.addObject("Calendar", requestedCalendar);
		model.addObject("maxYear", maxYear);
		model.addObject("minYear", Constants.FIRST_YEAR_DEPLOY);
		return model;
	 }

	
	
}


