package ppp.simt.controller.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ppp.simt.entity.core.Office;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.InSlip;
import ppp.simt.entity.production.InSlipStatus;
import ppp.simt.entity.user.User;
import ppp.simt.form.InSlipForm;
import ppp.simt.service.core.OfficeService;
import ppp.simt.service.engines.StateCheckerService;
import ppp.simt.service.production.ApplicationService;
import ppp.simt.service.production.InSlipService;
import ppp.simt.service.production.InSlipStatusService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;



@Controller
@RequestMapping("/inSlip")
public class InSlipController {

	@Autowired
	private InSlipService inSlipService;
	
	@Autowired
	private InSlipStatusService inSlipStatusService;

	@Autowired
	private OfficeService officeService;
	
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private StateCheckerService stateCheckerService;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;
	

	@Secured({"ROLE_VIEW_INSLIP"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String inSlipList(HttpServletRequest httpServletRequest, Model model) {

		List<InSlip> inSlips = inSlipService.findAll();
		User user = userService.getLogedUser(httpServletRequest);
		Office userOffice = user.getOffice();
		List<InSlip> inslipsByOffice = inSlipService.findByOffice(userOffice);
		model.addAttribute("inSlips", inSlips);
		model.addAttribute("inslipsByOffice", inslipsByOffice);
		List<Office> listOffices = officeService.findAll();
		model.addAttribute("listOffices", listOffices);
		model.addAttribute("userOffice", userOffice);
		model.addAttribute("user", user);
		return "inslip/inslip_list";
	}
	
	@Secured({"ROLE_VIEW_INSLIP"})
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String inSlipDetails(@RequestParam("id") int id,HttpServletRequest httpServletRequest, Model model) {
		List<Integer> usedAppNums = new ArrayList<Integer>();
		List<Integer> unUsedAppNums = new ArrayList<Integer>();

		InSlip inSlip = inSlipService.findInSlipById(id);
		int inSlipSize  = inSlip.getTotal() ;
	    List<Application> apps = applicationService.findAppByInSlipId(id);
	    for(int i=0; i<apps.size(); i++) 
	    { 
	        usedAppNums.add(apps.get(i).getNumber());
	    }
	    for(int i=1; i<=inSlipSize; i++) 
	    { 
	        if(usedAppNums.contains(i)==false) {

	        	unUsedAppNums.add(i);
	        }
	    }
	    
		model.addAttribute("inSlip", inSlip);
		model.addAttribute("unUsedAppNums", unUsedAppNums);
		model.addAttribute("apps", apps);
		return "inslip/inslip_detail";
	}

	@Secured({"ROLE_VIEW_INSLIP"})
	@RequestMapping(value = "/listeSearchInSlip", method = RequestMethod.GET)
	public String listeSearchInSlips( @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("status") int status, @RequestParam("office") int office,
			@RequestParam("referenceNum") String referenceNum, HttpServletRequest httpServletRequest, Model model) throws ParseException {
		List<InSlip> inSlips =new ArrayList<>();
		User user = userService.getLogedUser(httpServletRequest);
		Office userOffice = user.getOffice();
		List<InSlip> inslipsByOffice = inSlipService.findByOffice(userOffice);
		java.sql.Date sqlStartDate = null;
		java.sql.Date sqlendDate = null;
		InSlipStatus inSlipStatus = inSlipStatusService.findById(status);

		if(!referenceNum.equals("")){
			inSlips = inSlipService.findByInSlipAndDateAndStatusAndOffice(sqlStartDate, sqlendDate, inSlipStatus, office, referenceNum);
		}else if(!startDate.isEmpty()){
			java.util.Date startDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
			java.util.Date endDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
			sqlStartDate = new java.sql.Date(startDateConverted.getTime());
			sqlendDate = new java.sql.Date(endDateConverted.getTime());
		}
			inSlips = inSlipService.findByInSlipAndDateAndStatusAndOffice(sqlStartDate, sqlendDate, inSlipStatus, office, referenceNum);

		
		model.addAttribute("userOffice", userOffice);
		model.addAttribute("user", user);	
		model.addAttribute("inslipsByOffice", inslipsByOffice);	
		model.addAttribute("inSlips", inSlips);
		return "inslip/inslip_list_search";
	}
	

	@Secured({"ROLE_MANAGE_INSLIP"})
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(HttpServletRequest httpServletRequest, Model model) {
		return "inslip/create";
	}
	


	@Secured({"ROLE_MANAGE_INSLIP"})
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id") int id , HttpServletRequest httpServletRequest, Model model) {
		InSlip slip=inSlipService.findInSlipById(id);
		
		System.out.println("slip"+slip);
		model.addAttribute("slip", slip);
		return "inslip/edit";
	}
	
	/*
	 *@uthor MPA
	 *this function helps to manage all Inslip Statuses
	 */
	
	
	
	@RequestMapping(value="/update/{action}/{id}", method=RequestMethod.GET)
	public String manageInSlipStates(@PathVariable(value="action") String action ,@PathVariable(value="id") int id ,HttpServletRequest httpServletRequest, Model model){
		User user = userService.getLogedUser(httpServletRequest);
		InSlip inslip = inSlipService.findInSlipById(id);
		if(action.equals("close")){
	        //instructions to register in slip		
			if(stateCheckerService.stateEmbedded((InSlip)inslip, user,"close").equals("ok")){
				closeInslipTrackAndLog( inslip , user,httpServletRequest );
				model.addAttribute("proceed",  "ok");
				
				tracker.track(inslip, "IN SLIP WAS CLOSED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
				logger_.log(Constants.NORMAL_LOG_DIR, "Created the inslip: "+user);
				
				return "inslip/change";
	        }else if(stateCheckerService.stateEmbedded((InSlip)inslip, user,"close").equals("koBadRole")){
	        	model.addAttribute("proceed",  "koBadRole");
	        	return "inslip/change";	
	        }else{
		        model.addAttribute("proceed",  "koBadState");
			    return "inslip/change";
		    }
		}else if(action.equals("reset")){			
			if(stateCheckerService.stateEmbedded((InSlip)inslip, user,"reset").equals("ok")){
			resetInslipTrackAndLog( inslip , user,httpServletRequest );
			model.addAttribute("proceed",  "ok");
			
			tracker.track(inslip, "IN SLIP IS RESETTED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Created the inslip: "+user);
			
	        return "inslip/change";
	        }else if(stateCheckerService.stateEmbedded((InSlip)inslip, user,"reset").equals("koBadRole")){
	        	model.addAttribute("proceed",  "koBadRole");
	        	return "inslip/change";	
	        }else{
		        model.addAttribute("proceed",  "koBadState");
			    return "inslip/change";
		    }
			
		}else if(action.equals("open")){
			if(stateCheckerService.stateEmbedded((InSlip)inslip, user,"open").equals("ok")){
			openInSlipTrackAndLog( inslip , user,httpServletRequest );
			model.addAttribute("proceed",  "ok");
			
			tracker.track(inslip, "IN SLIP IS OPENED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Created the inslip: "+user);
			
	        return "inslip/change";
	        
	        }else if(stateCheckerService.stateEmbedded((InSlip)inslip, user,"open").equals("koBadRole")){
	        	model.addAttribute("proceed",  "koBadRole");
	        	return "inslip/change";	
	        }else{
		        model.addAttribute("proceed",  "koBadState");
			    return "inslip/change";
		    }
			
		}

		return "ok";
	}
	
	/*
	 *@uthor MPA
	 *this function helps to reset an InSLIP 
	 */
	
	private void resetInslipTrackAndLog(InSlip inslip, User user, HttpServletRequest httpServletRequest ){
		inSlipService.manageInSlipReset( inslip,user);
		logger_.log(Constants.NORMAL_LOG_DIR, "IN SLIP RESETTED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"IN SLIP ID IS "+inslip.getId(),httpServletRequest);
				
	}
	
	/*
	 *@uthor MPA
	 *this function helps to reset an InSLIP 
	 */
	
	private void openInSlipTrackAndLog(InSlip inslip, User user, HttpServletRequest httpServletRequest ){
		inSlipService.manageInSlipOpen( inslip,user);
		logger_.log(Constants.NORMAL_LOG_DIR, "IN SLIP OPENED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"IN SLIP ID IS "+inslip.getId(),httpServletRequest);
				
	}
	
	/*
	 *@uthor MPA
	 *this function helps to close an InSLIP 
	 */
	
	private void closeInslipTrackAndLog(InSlip inslip, User user, HttpServletRequest httpServletRequest ){
		inSlipService.manageInSlipClose( inslip,user);
		logger_.log(Constants.NORMAL_LOG_DIR, "IN SLIP CLOSED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"IN SLIP ID IS "+inslip.getId(),httpServletRequest);
				
	}
	
	

	@Secured({"ROLE_MANAGE_INSLIP"})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public String createPost(@ModelAttribute("inSlipForm") @Validated InSlipForm inSlipForm,  BindingResult result,HttpServletRequest httpServletRequest, Model model) {
		
		if(result.hasErrors()){
			return "failedId";
		}
		else
		{
			
		  User user=userService.getLogedUser(httpServletRequest);
		  if(user.getOffice()==null){
			  return "failedId";
		  }
	
			  String response="OK";
			  response =inSlipService.saveInSlip(inSlipForm, user.getOffice());
			  if(response=="OK"){
			      return "successId";
			  }else{
				  if(response=="KOEXIST") return "failedSlipExist";
				  return "failedId";
			  }

		}
	}
	
	@Secured({"ROLE_INSLIP_CONTROLLER"})
	@RequestMapping(value = "/makeReport", method = RequestMethod.GET)
	public String makeInSlipReport(@RequestParam("id") int id,HttpServletRequest httpServletRequest, Model model) {
		User user=userService.getLogedUser(httpServletRequest);
		InSlip inSlip = inSlipService.findInSlipById(id);
		model.addAttribute("inSlip", inSlip);
		model.addAttribute("user", user);
		return "inslip/report";
	}
	
	@Secured({"ROLE_INSLIP_CONTROLLER"})
	@RequestMapping(value = "/RegisterMakeReport", method = RequestMethod.GET)
	public String registerMakeInSlipReport(@RequestParam("id") int id,@RequestParam("message") String message,HttpServletRequest httpServletRequest, Model model) {
		
		InSlip inSlip = inSlipService.findInSlipById(id);
		InSlipStatus status = inSlipStatusService.findById(4);
		String newMessage ="The In-slip was successfully controlled.";
		if(message=="normal") {
			inSlip.setStatus(status);
		}else{
			inSlip.setStatus(status);
			newMessage += "COMMENT: " + message;
		}
		inSlipService.save(inSlip);
		
		
		tracker.track(inSlip,  newMessage, httpServletRequest);
		
		List<InSlip> inSlips = inSlipService.findAll();
		model.addAttribute("inSlips", inSlips);
		List<Office> listOffices = officeService.findAll();
		model.addAttribute("listOffices", listOffices);
		
		return "inslip/inslip_list";
	}
	
}

