
/**
* @author MPA
* Cette classe permet de manipuler les actions liees aux transcripts
* 
*/

package ppp.simt.controller.evaluation;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Transcript;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.service.core.CoreService;
import ppp.simt.service.engines.PrinterService;
import ppp.simt.service.evaluation.CourseService;
import ppp.simt.service.evaluation.TranscriptService;
import ppp.simt.service.pv.EligibleCenterService;
import ppp.simt.service.pv.StudentSessionService;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Controller
@RequestMapping("/evaluation")
public class ManageEvaluationController {
	@Autowired
    ServletContext context;
	
	@Autowired
	private Environment env;
	
	@Autowired 
	private Logger_ logger_;
		
	@Autowired
	private StudentSessionService studentSessionService;
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private TranscriptService transcriptService;
	
	@Autowired
	private EligibleCenterService eligibleCenterService;
	
	@Autowired
	private CoreService coreService;
	
	@Autowired
	private PrinterService printerService;
	
	@Autowired 
	private Tracker tracker;
	
	
	
	/*
	 *@Author MPA
	 *this function helps to load view to manage transcripts
	 */
	@RequestMapping(value="/manage_pv_transcript", method=RequestMethod.GET)
	@Secured({"ROLE_MANAGE_CANDIDATES_TRANSCRIPT"})
	public String manage_pv_list(Model model, @RequestParam("eligibleCenterId") int eligibleCenterId){
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		List<StudentSession> successfulStudentsinCenter = studentSessionService.findByEligibleCenterAndResult(eligibleCenter, 4);
		
		model.addAttribute("students", successfulStudentsinCenter);
		model.addAttribute("eligibleCenter", eligibleCenter);		
		return "evaluation/manage_candidate_transcript";
	}
	

	/*
	 *@Author MPA
	 *this function helps to save transcripts
	 */

	@RequestMapping(value="/saveNotes", method=RequestMethod.POST)
	@ResponseBody
	public String saveNotes(Model model, @RequestParam("studentSessionId") int studentSessionId,  @RequestParam("studentSessionNotes") String studentSessionNotes,HttpServletRequest httpServletRequest){
		StudentSession studentSession = studentSessionService.findById(studentSessionId);
		Map<Integer, Float> studentSessionNotesMap = coreService.constructMapFromStringJSON(studentSessionNotes);
		for(Entry<Integer,Float> studessionNote : studentSessionNotesMap.entrySet()){
			Course course = courseService.findById(studessionNote.getKey());
			Transcript transcriptToPersist= transcriptService.findByStudentSessionAndCourse(studentSession,course);
			if(transcriptToPersist == null){
				transcriptToPersist =  new Transcript();
				transcriptToPersist.setStudentSession(studentSession);
				transcriptToPersist.setCourse(course);
				transcriptToPersist.setCourseNote(studessionNote.getValue());
				transcriptService.createTranscript(transcriptToPersist);
			}else{
				transcriptToPersist.setCourseNote(studessionNote.getValue());
				transcriptService.updateTranscript(transcriptToPersist);
			}
		}
		return "ok";
	}
	
	
	@RequestMapping(value="/load", method=RequestMethod.GET)
	@Secured({"ROLE_MANAGE_CANDIDATES_TRANSCRIPT"})
	public String loadCandidates(@RequestParam("eligibleCenter") int eligibleCenterId,@RequestParam("result") String result, Model model, HttpServletRequest httpServletRequest){
		int examResult = Integer.parseInt(result);
		EligibleCenter eligibleCenter = eligibleCenterService.findById(eligibleCenterId);
		List<StudentSession> studentSessions = studentSessionService.findByEligibleCenterAndResult(eligibleCenter, examResult);
		model.addAttribute("students",studentSessions);
		model.addAttribute("indicator", result);
		model.addAttribute("eligibleCenterId", eligibleCenterId);
		return "evaluation/list_student_result";
	}
	

		
}
	


