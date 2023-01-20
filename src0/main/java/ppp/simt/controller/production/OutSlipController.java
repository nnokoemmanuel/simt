package ppp.simt.controller.production;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ppp.simt.entity.core.Office;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.InSlip;
import ppp.simt.entity.production.OutSlip;
import ppp.simt.entity.user.User;
import ppp.simt.form.OutSlipForm;
import ppp.simt.repository.production.OutSlipRepository;
import ppp.simt.service.core.OfficeService;
import ppp.simt.service.engines.PrinterService;
import ppp.simt.service.engines.SmsHandler;
import ppp.simt.service.engines.StateCheckerService;
import ppp.simt.service.production.ApplicationService;
import ppp.simt.service.production.OutSlipService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/outSlip")
public class OutSlipController {

	@Autowired
	private OutSlipService outSlipService;

	@Autowired
	private OutSlipRepository outSlipRepository;


	@Autowired
	private OfficeService officeService;

	@Autowired
	private PrinterService printerService;

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

	@Autowired
	private SmsHandler smsHandler;
	

	@Secured({"ROLE_VIEW_OUTSLIP"})
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String outSlipList(HttpServletRequest httpServletRequest, Model model) {

		List<OutSlip> outSlips = outSlipService.findAll();
		model.addAttribute("outSlips", outSlips);
		List<Office> listOffices = officeService.findAll();
		model.addAttribute("listOffices", listOffices);
		User user = userService.getLogedUser(httpServletRequest);
		System.out.println(user.getGroup().getId());
		Office userOffice = user.getOffice();
		System.out.println(userOffice.getName());
		List<OutSlip> outSlipsByOffice = outSlipService.findByOffice(userOffice);
		model.addAttribute("outSlipsByOffice", outSlipsByOffice);

		model.addAttribute("userOffice", userOffice);
		model.addAttribute("user", user);
		return "outslip/outslip_list";
	}

	@Secured({"ROLE_VIEW_OUTSLIP"})
	@RequestMapping(value = "/listeSearchOutSlip", method = RequestMethod.GET)

	public String listeSearchOutSlips(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
									 @RequestParam("status") int status, @RequestParam("office") int office,
									 @RequestParam("referenceNum") String referenceNum, HttpServletRequest httpServletRequest, Model model) throws ParseException {
		java.sql.Date sqlStartDate = null;
		java.sql.Date sqlendDate = null;
		User user = userService.getLogedUser(httpServletRequest);
		Office userOffice = user.getOffice();
		List<OutSlip> outSlipsByOffice = outSlipService.findByOffice(userOffice);
		List<OutSlip> outSlips =new ArrayList<>();

		if(!referenceNum.equals("")){
			outSlips = outSlipService.findByOutSlipAndDateAndStatusAndOffice(sqlStartDate,sqlendDate,status,office,referenceNum);
		}else if(!startDate.isEmpty()){
			java.util.Date startDateConverted=new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
			java.util.Date  endDateConverted=new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
			sqlStartDate = new java.sql.Date(startDateConverted.getTime());
			sqlendDate = new java.sql.Date(endDateConverted.getTime());
		}

		outSlips = outSlipService.findByOutSlipAndDateAndStatusAndOffice(sqlStartDate,sqlendDate,status,office,referenceNum);

		//System.out.println(outSlips);
		//System.out.println(sqlStartDate+""+sqlendDate+""+status+""+office+""+referenceNum);

		model.addAttribute("userOffice", userOffice);
		model.addAttribute("user", user);	
		model.addAttribute("outSlipsByOffice", outSlipsByOffice);	
		model.addAttribute("outSlips", outSlips);
		return "outslip/outslip_list_search";
	}

	@Secured({"ROLE_VIEW_OUTSLIP"})
	@RequestMapping(value = "/printSuccessfully", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> outSlipApplicationPrintedPdf(@RequestParam("id") int id, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, DocumentException{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		System.out.println(id);
		//applicationStatusPrinted = 6
		printerService.ouSlipPdf(outStream,headers,id , Constants.applicationStatusPrinted);

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(outStream.toByteArray());
	}
	@Secured({"ROLE_VIEW_OUTSLIP"})
	@RequestMapping(value = "/printRejected", method = RequestMethod.GET,  produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> outSlipApplicationRejectedPdf(@RequestParam("id") int id, Model model, HttpServletRequest httpServletRequest, HttpServletResponse response)
			throws IOException, DocumentException{
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		System.out.println(id);
		//applicationStatusRejected = 10
		printerService.ouSlipPdf(outStream,headers,id , Constants.applicationStatusRejected);

		return ResponseEntity
				.ok()
				.headers(headers)
				.contentType(MediaType.APPLICATION_PDF)
				.body(outStream.toByteArray());
	}
	
	/*
	 *@uthor MPA
	 *this function helps to manage all Inslip Statuses
	 */
	
	
	@ResponseBody
	@RequestMapping(value="/update/{action}/{id}", method=RequestMethod.GET)
	public String manageOutSlipStates(@ModelAttribute("outSlipForm") @Validated OutSlipForm outSlipForm, @PathVariable(value="action") String action ,@PathVariable(value="id") int id ,HttpServletRequest httpServletRequest, BindingResult result){
		User user = userService.getLogedUser(httpServletRequest);
		OutSlip outslip = outSlipService.findOutSlipById(id);
		if(action.equals("validate")){
	        //instructions to register in slip
			
			if(stateCheckerService.stateEmbedded((OutSlip)outslip, user,"validate").equals("ok")){
				validateOutslipTrackAndLog( outslip , user,httpServletRequest );

				tracker.track(outslip, "OUT SLIP WAS VALIDATED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
				logger_.log(Constants.NORMAL_LOG_DIR, "Created the outslip: "+user);
				
				return "ok";
	        }else if(stateCheckerService.stateEmbedded((OutSlip)outslip, user,"validate").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
		}else if(action.equals("reset")){
			
			if(stateCheckerService.stateEmbedded((OutSlip)outslip, user,"reset").equals("ok")){
			resetOutslipTrackAndLog( outslip , user,httpServletRequest );
			
			tracker.track(outslip, "OUT SLIP IS RESETTED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Created the outslip: "+user);
			
	        return "ok";
	        }else if(stateCheckerService.stateEmbedded((OutSlip)outslip, user,"reset").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }
			
		}else if(action.equals("deliver")){
			
			if(stateCheckerService.stateEmbedded((OutSlip)outslip, user,"deliver").equals("ok")){
			deliverOutSlipTrackAndLog( outslip , user,httpServletRequest );
			
			tracker.track(outslip, "OUT SLIP IS DELIVERED BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "Created the outslip: "+user);
			
	        return "ok";
	        }else if(stateCheckerService.stateEmbedded((OutSlip)outslip, user,"deliver").equals("koBadRole")){
	        	return "koBadRole";	
	        }else{
			    return "koBadState";
		    }

		}

		return "ok";
	}

	/*
	 *@uthor MPA
	 *this function helps to reset an OutSlip 
	 */
	
	private void resetOutslipTrackAndLog(OutSlip outSlip, User user, HttpServletRequest httpServletRequest ){
		outSlipService.manageOutSlipReset( outSlip,user);
		logger_.log(Constants.NORMAL_LOG_DIR, "OUT SLIP RESETTED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"OUT SLIP ID IS "+outSlip.getId(),httpServletRequest);
				
	}

	/*
	 *@uthor MPA
	 *this function helps to validate an OutSlip 
	 */
	
	private void validateOutslipTrackAndLog(OutSlip outSlip, User user, HttpServletRequest httpServletRequest ){
		outSlipService.manageOutSlipValidate( outSlip,user);
		logger_.log(Constants.NORMAL_LOG_DIR, "OUT SLIP VALIDATED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"OUT SLIP ID IS "+outSlip.getId(),httpServletRequest);
				
	}
	
	/*
	 *@uthor MPA
	 *this function helps to deliver an OutSlip 
	 */
	
	private void deliverOutSlipTrackAndLog(OutSlip outSlip, User user, HttpServletRequest httpServletRequest ){
		outSlipService.manageOutSlipDeliver( outSlip,user);
		logger_.log(Constants.NORMAL_LOG_DIR, "OUT SLIP DELIVERED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"OUT SLIP ID IS "+outSlip.getId(),httpServletRequest);
				
	}

	private void confirmOutSlipTrackAndLog(OutSlip outSlip, User user, HttpServletRequest httpServletRequest ){
		outSlipService.manageOutSlipConfirm( outSlip,user);
		logger_.log(Constants.NORMAL_LOG_DIR, "OUT SLIP CONFIRMED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"OUT SLIP ID IS "+outSlip.getId(),httpServletRequest);

	}

	
	@Secured({"ROLE_MANAGE_OUTSLIP"})
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public String create(@RequestParam("id") int id, @RequestParam("startDate") String startDateAsString,@RequestParam("endDate") String endDateAsString, HttpServletRequest httpServletRequest, Model model) throws ParseException {
		java.util.Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(startDateAsString);
		java.util.Date  endDate=new SimpleDateFormat("dd/MM/yyyy").parse(endDateAsString);
		List<Application> apps=applicationService.findBySuccessfulyRejectedDateBetweenAndOutSlipIsNull(startDate, endDate);
		Set<Application> setApps=new HashSet<>();
		setApps.addAll(apps);
		
		if(apps.size()!=0)
		{
		   OutSlip slip=new OutSlip();
		   slip.setApplications(setApps);
		   slip.setStartDate(startDate);
		   slip.setEndDate(endDate);
		   slip.setCreationDate(new java.util.Date());
		   Date date = new Date();
		   LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		   int year  = localDate.getYear();
		   int month = localDate.getMonthValue();
		   String monthAstring =""+month;
		   if(month<10) monthAstring="0"+month;
		    java.util.Date firstDayOfMont =new SimpleDateFormat("dd/MM/yyyy").parse("01/"+monthAstring+"/"+year);
			List<OutSlip> slips=outSlipService.findByOfficeAndCreationDateBetween(userService.getLogedUser(httpServletRequest).getOffice(), firstDayOfMont, new java.util.Date());
			
		   slip.setOffice(userService.getLogedUser(httpServletRequest).getOffice());
		   String reference =userService.getLogedUser(httpServletRequest).getOffice().getAbreviation();
		   reference=reference+"-"+(slips.size()+1)+"-"+monthAstring+""+(year-2000);
		   slip.setReferenceNumber(reference);
		   slip.setStatus(1);
		   slip.setTotal(apps.size());
		   outSlipService.save(slip);
		   for(int i=0;i<apps.size();i++) {
			   Application app=apps.get(i);
			   app.setOutSlip(slip);
			   applicationService.save(app);
		   }
		   return "ok";
		   
		}else return "ko";
	}	
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String create(HttpServletRequest request, Model model) {
		return "outslip/create";
	}

	@Secured({"ROLE_MANAGE_OUTSLIP"})
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	@ResponseBody
	public String createPost(@ModelAttribute("outSlipForm") @Validated OutSlipForm outSlipForm, @RequestParam("id") int id, BindingResult result, HttpServletRequest httpServletRequest, Model model) {

		User user = userService.getLogedUser(httpServletRequest);
		OutSlip outslip = outSlipService.findOutSlipById(id);
			if(stateCheckerService.stateEmbedded((OutSlip)outslip, user,"confirm").equals("ok")){
				if(result.hasErrors()) System.out.println("moise2----");
				else
				{
					String newMessage ="The Out-slip was successfully confirmed.";
					String response="OK";
					response =outSlipService.saveOutSlip(outSlipForm, id);
					Set<Application> allApplicationInOutSlip = outslip.getApplications();
					for (Application application : allApplicationInOutSlip) {
						Person person = new Person();
//						if(application.getCapacity().getPerson().getStudentSessions() != null)
//							person = application.getCapacity().getPerson();
//						else
//							person = application.getCapacity().getPerson();

						String telephone = person.getPhoneNumber();
						String message = getMessage(application);
						smsHandler.sendSms("237"+telephone , message);
					}

				}
				confirmOutSlipTrackAndLog( outslip , user,httpServletRequest );

				tracker.track(outslip, "The Out-slip was successfully confirmed BY USER: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
				logger_.log(Constants.NORMAL_LOG_DIR, "confirmed the outslip: "+user);

				return "ok";
			}else if(stateCheckerService.stateEmbedded((OutSlip)outslip, user,"deliver").equals("koBadRole")){
				return "koBadRole";
			}else{
				return "koBadState";
			}
	}

	@Secured({"ROLE_MANAGE_OUTSLIP"})
	@RequestMapping(value = "/confirmView", method = RequestMethod.GET)
	public String confirmView(@RequestParam("id") int id, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
							  @RequestParam("status") int status, @RequestParam("office") int office,
							  @RequestParam("referenceNum") String referenceNum, HttpServletRequest httpServletRequest, Model model) {
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("status", status);
		model.addAttribute("office", office);
		model.addAttribute("referenceNum", referenceNum);
		model.addAttribute("id", id);
		return "outslip/confirm";
	}

	private String getMessage(Application application) {
		int region = application.getOffice().getId();
		String message="";
		if((region == 7)||(region == 10))
		{
			message ="Your Maritime Driving License has been printed, get to your regional  delegation of transports and collect it ";
		}
		else
		{
			message ="Veuillez vous Presenter  à la Direction des affaires Maritime de votre Lieu de depot pour le retrait de votre Capacité.";
		}
		return message;
	}
}