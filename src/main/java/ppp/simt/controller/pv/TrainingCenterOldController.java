package ppp.simt.controller.pv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.service.pv.TrainingCenterService;

@Controller
@RequestMapping("/center")
public class TrainingCenterOldController {
	@Autowired
	private TrainingCenterService trainingCenterService;
	
	@RequestMapping("/list")
	public String loadCenters(Model model) {
		model.addAttribute("centers", trainingCenterService.findAll());
		return "examCenters/list";
	}
	
	@RequestMapping("/maxCapacity")
	@ResponseBody
	public String modifyMaxCapacity(@RequestParam("id") int id, @RequestParam("") int maxStudent) {
		TrainingCenter  center =trainingCenterService.findById(id);
		 center.setMaxStudent(maxStudent);
		trainingCenterService.save(center);
		return "accepted";
	}
}
