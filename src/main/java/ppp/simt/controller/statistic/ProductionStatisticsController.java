package ppp.simt.controller.statistic;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ppp.simt.entity.core.Category;
import ppp.simt.entity.core.Office;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.ApplicationStatus;
import ppp.simt.entity.production.ProcessType;
import ppp.simt.service.core.CategoryService;
import ppp.simt.service.core.OfficeService;
import ppp.simt.service.production.ApplicationService;
import ppp.simt.service.production.ApplicationStatusService;
import ppp.simt.service.production.ProcessTypeService;
import ppp.simt.statistics.ProductionResult;
import ppp.simt.statistics.Result;



@Controller
@RequestMapping("/statistics")
public class ProductionStatisticsController {
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private ProcessTypeService processTypeService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ApplicationStatusService applicationStatusService;
	
	 @RequestMapping(value="/list", method=RequestMethod.GET)
	 public String loadStatisticsView(HttpServletRequest httpServletRequest, Model model){
		 List<Office> offices = officeService.findAll();
		 List<ProcessType> processTypes = processTypeService.findAll() ;
		 List<ApplicationStatus> appStatus = applicationStatusService.findAll();
				 
		 
		 model.addAttribute("offices", offices);
		 model.addAttribute("processTypes", processTypes);
		 model.addAttribute("appStatus", appStatus);
		 
		 return "statistics/production_list";
	 }
	 
	 @RequestMapping(value="/search", method=RequestMethod.GET)
	 public String searchStatistics(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("appStatus") int appStatus, HttpServletRequest httpServletRequest, Model model) throws ParseException{
		 java.sql.Date sqlStartDate = null;
		 java.sql.Date sqlendDate = null;
		 List<ProcessType> processTypes = processTypeService.findAll() ;
		 
		 if(!startDate.isEmpty()){
			 java.util.Date startDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
			 java.util.Date endDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
			 sqlStartDate = new java.sql.Date(startDateConverted.getTime());
			 sqlendDate = new java.sql.Date(endDateConverted.getTime());
		}
		 List<Office> offices = officeService.findAll();
		 List<Category> categories = categoryService.findAll();
		 
		 ArrayList<ProductionResult> results=new ArrayList<ProductionResult>();
		 ArrayList<Long> totalPerOffice=new ArrayList<Long>();

		 for(int i=0; i<processTypes.size(); i++) {
			 
			 ProductionResult productionResult = new ProductionResult();
			 productionResult.setProcessType(processTypes.get(i));
			 ArrayList<Result> officeResults=new ArrayList<Result>();
			 
			 for(int j=0; j<offices.size(); j++) {
				 int noValue=0;
				 Result result =new Result();
				 result.setOffice(offices.get(j));
				 
				 long applicationTotalByProcessType = (long)(applicationService.getDistinctTotalByOfficePerStudentSession( sqlStartDate , sqlendDate, offices.get(j).getId(), appStatus, processTypes.get(i).getId(), em)).size();

				 
				 result.setTotal(applicationTotalByProcessType);
				 officeResults.add(result);
				 
			 }			
			 
			 productionResult.setResult(officeResults);
			 results.add(productionResult);

		 }
		for(int i=0; i<offices.size();i++) {
			int noValue=0;
			
			Long productionTotal = (long)(applicationService.getDistinctTotalByOfficePerStudentSession(sqlStartDate, sqlendDate, offices.get(i).getId(), appStatus, 0, em)).size();
			totalPerOffice.add(productionTotal);
			
		}
		
		
		 model.addAttribute("offices", offices);
		 model.addAttribute("results", results);
		 model.addAttribute("officeTotals", totalPerOffice);
		 model.addAttribute("processTypes", processTypes);

		 return "statistics/production_search_results";
	 }

}
