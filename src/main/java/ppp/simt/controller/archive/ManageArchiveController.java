
/**
* Cette classe permet de manipuler les Archives CAPEC
 */

package ppp.simt.controller.archive;


import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import ppp.simt.entity.tracking.ArchiveTracking;
import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.production.ProfessionalCard;
import ppp.simt.entity.user.User;
import ppp.simt.form.ArchiveForm;
import ppp.simt.form.SimtLicenseChecker;
import ppp.simt.service.archive.ArchiveFileService;
import ppp.simt.service.archive.ArchiveService;
import ppp.simt.service.archive.ArchiveTrackingService;
import ppp.simt.service.core.AuthorityService;
import ppp.simt.service.core.CategoryService;
import ppp.simt.service.core.CountryService;
import ppp.simt.service.core.EligibilityChecher;
import ppp.simt.service.core.PersonService;
import ppp.simt.service.engines.StateCheckerService;
import ppp.simt.service.production.ProfessionalCardService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Controller
@RequestMapping("/archive")
public class ManageArchiveController {
	@Autowired
    ServletContext context;
	
	@Autowired
	private Environment env;
	
	@Autowired 
	private Logger_ logger_;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private ProfessionalCardService professionalCardService;
	
	@Autowired
	private StateCheckerService stateCheckerService;
	
	@Autowired
	private ArchiveTrackingService archiveTrackingService;
	
	@Autowired 
	private Tracker tracker;
	
	@Autowired
	protected EligibilityChecher eligibilityChecher;
	
	@Autowired
	private AuthorityService authorityService ;

	@Autowired
	private ArchiveFileService archiveFileService;
	
	@Secured({"ROLE_MANAGE_ARCHIVE"})
	@RequestMapping(value="/create/{action}/{archiveId}", method=RequestMethod.GET)
	public String createArchive(Model model ,@PathVariable(value="action") String action , @PathVariable(value="archiveId") int archiveId){
		
		//we add attributes common to both views
		model.addAttribute("countries", countryService.findAll());
		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("authorities", authorityService.findAll());
		if(action.equals("actionCreate") && archiveId == 0 ){
			//here we render create form 
    		
		return "archive/create";
		}
		//here we render edition form
		Archive archive = archiveService.findArchiveById(archiveId);
		model.addAttribute("archive", archive);
		return "archive/edit";
	}
	
	@RequestMapping(value="/verify/applicant/licenseInfos", method=RequestMethod.GET)
	@ResponseBody
	public  String searchStudentLicense(@ModelAttribute("archiveForm") @Validated  ArchiveForm archiveForm,@RequestParam("license") String license,@RequestParam("speciality") int speciality,@RequestParam("surName") String surName,@RequestParam("givenName") String givenName,@RequestParam("pob") String pob,@RequestParam("dob") Date dob, HttpServletRequest http, HttpServletResponse response ) throws ParseException {
			HashMap<String, String> map = new HashMap<>();
			String specialty;
			if(speciality==1);
			specialty = "moniteur";
			Person person = personService.findPersonByLicenseNum(license);
			if (person!=null) {
				return "dupilcation.of.person";
			}
			SimtLicenseChecker resultCheck = eligibilityChecher.check(license, specialty, "BAC");
			
			SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
		    String formDob = newFormat.format(dob);
		    String apiDob = newFormat.format(resultCheck.getPerson().getDob());
            
		    String formInfos = givenName.toUpperCase()+" "+surName.toUpperCase()+" "+pob.toUpperCase()+" "+formDob ;
			String apiInfos = resultCheck.getPerson().getGn().toUpperCase()+" "+resultCheck.getPerson().getSn().toUpperCase()+" "+resultCheck.getPerson().getPob().toUpperCase()+" "+apiDob;
			
		    int levensteinDistance = getLevenshteinDistance(apiInfos, formInfos);
		    int maxValeu = Math.max(apiInfos.length(), formInfos.length());
			int percentageMatching = (levensteinDistance*100/maxValeu);
			if (resultCheck!=null) {
				if (percentageMatching<20) {
					Format formatter = new SimpleDateFormat("dd/MM/yyyy");
					String dobString = formatter.format(resultCheck.getPerson().getDob());
				    try{
					String catBDate = formatter.format(resultCheck.getCatB_Date());
				
				    }catch(Exception e){
				    	return "ko.catb.not.found";
				    	
				    }
					return "ok";
				}else {
					return "ko";
				}
				
				
			} else {
				return "ko";
			}
		
	}
	
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	@ResponseBody
	@Transactional
    @Secured({"ROLE_MANAGE_ARCHIVE"})
	public String saveArchive(@ModelAttribute("archiveForm") @Validated  ArchiveForm archiveForm,  BindingResult result ,HttpServletRequest httpServletRequest, Model model) 
			throws ParseException{
	    //DEBUGGING PURPOSE System.out.println("archive form contain : "+archiveForm.toString());
	    User user = userService.getLogedUser(httpServletRequest);
		if(result.hasErrors()){
			if(result.getFieldError("vide")!= null){
				logger_.log(Constants.NORMAL_LOG_DIR, "archive empty "+result.getFieldError("vide").getCode(),httpServletRequest);
				return result.getFieldError("vide").getCode();
				
			}
		} 
		 
		Archive archive = archiveService.persistArchiveBean(archiveForm, httpServletRequest);
	 
		//call tracking and log services
		if(archiveForm.getId()==0){
			//case archive creation
			tracker.track(archive, "ARCHIVE CREATED BY USER", httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "ARCHIVE CREATED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ARCHIVE ID IS "+archive.getId(),httpServletRequest);
		}else{
		   //case archive edition
			tracker.track(archive, "ARCHIVE EDITED BY USER", httpServletRequest);
			logger_.log(Constants.NORMAL_LOG_DIR, "ARCHIVE EDITED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ARCHIVE ID IS "+archive.getId(),httpServletRequest);
		}
		
		return String.valueOf(archive.getId());
	}

	/*
	 *@uthor NNoko
	 *this function helps to manage all Archives Statuses
	 */

	@ResponseBody
	@RequestMapping(value="/update/{action}/{id}", method=RequestMethod.GET)
	public String manageArchiveStates(@PathVariable(value="action") String action ,
			                          @PathVariable(value="id") int id ,
			                          HttpServletRequest httpServletRequest, 
			                          Model model){
		User user = userService.getLogedUser(httpServletRequest);
		Archive  archive = archiveService.findArchiveById(id);
	   
		
		
		if(archive.getStatus().equals("REGISTERED") && (action.equals("validate")) ) {
				archive.getStatus();
				//System.out.println("validate action :: GET STATUS == "+archive.getStatus());
				archive.setStatus("VALIDATED");
				//System.out.println("validate action :: SET STATUS == "+archive.getStatus());
				archiveService.updateArchive(archive);
				valideArchiveTrackAndLog( archive , user,httpServletRequest );
				
				stateCheckerService.stateEmbedded((Archive)archive, user,"validate").equals("ok");
				
		        	
	        		System.out.println("UPDATES :: ACTION validate -> STATUS (from REGISTERED to VALIDATED) ");
	        		
	           return "ok";
	 				
		}
		
		
		
		// <Invalidate Status | Reset Action>
		if(archive.getStatus().equals("VALIDATED") && (action.equals("invalidate")) ) {
				archive.getStatus();
				//System.out.println("invalidate OR reset action :: GET STATUS == "+archive.getStatus());
				archive.setStatus("REGISTERED");
				//System.out.println("invalidate OR reset action :: SET STATUS == "+archive.getStatus());
				archiveService.updateArchive(archive);
				resetArchiveTrackAndLog( archive , user,httpServletRequest );
				
				
				stateCheckerService.stateEmbedded((Archive)archive, user,"invalidate").equals("ok");
					
					System.out.println("UPDATES :: ACTION invalidate -> STATUS (from VALIDATED to INVALIDATED) ");
					
				return "ok";
		}
		
		 
        
			
        else if(archive.getStatus().equals("REGISTERED") && action.equals("delete")){ 
             System.out.println("we delete");
        	if(stateCheckerService.stateEmbedded((Archive)archive, user,"delete").equals("ok")){
        		ProfessionalCard profCardRelatedToArchive= professionalCardService.findByArchive(archive);
        		if(profCardRelatedToArchive != null){
        			return "koProfCardsLinked";
        		}else{
        			Person personFound = archive.getPerson();
        			ArrayList<ArchiveTracking> listeOfTrackings= archiveTrackingService.findByArchiveId( archive.getId());
        			if(listeOfTrackings.size() > 0)
        				listeOfTrackings.forEach((tracking)->{archiveTrackingService.deleteArchiveTracking(tracking);});
        			archiveService.deleteArchive(archive); 
        			
        			personFound.setNationality(null);
        			personService.updatePerson(personFound);
        			if(personFound != null){
        				personService.deletePerson(personFound);
        			}
        	        logger_.log(Constants.NORMAL_LOG_DIR, "Delete the archive: " + archive.getArchiveNumber(), httpServletRequest);		
        			
        	        return "ok";
        	        //return "ok";
        			
        		}
			}	
        }
    	
        
        
        return "ok";
	
	}

	
	
	/*
	 *this function helps to Valide + track + Log Actions an Archive
	 */

	private void valideArchiveTrackAndLog(Archive archive, User user, HttpServletRequest httpServletRequest ){
		archiveService.manageArchiveValide( archive,user);
		tracker.track(archive, "ARCHIVE VALIDATED BY USER", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "ARCHIVE VALIDATED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ARCHIVE ID IS "+archive.getId(),httpServletRequest);

	}
	
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	@Secured({"ROLE_VIEW_ARCHIVE"})
	public String viewArchiveDetail(@RequestParam("id") int id,HttpServletRequest httpServletRequest, Model model){
		
		Archive archive = archiveService.findArchiveById(id);
		model.addAttribute("archive",archive);
		model.addAttribute("archiveFiles", archive.getArchiveFiles());
		return "archive/archive_detail";
	}

	
	@RequestMapping(value="/validate/detail", method=RequestMethod.GET)
	@Secured({"ROLE_VIEW_ARCHIVE"})
	public String cloadValidateDetail(@RequestParam("id") int id,HttpServletRequest httpServletRequest, Model model){
		
		Archive archive = archiveService.findArchiveById(id);
		model.addAttribute("archive",archive);
		model.addAttribute("archiveFiles", archive.getArchiveFiles());
		return "archive/validate_archive";
	}
	
	
	/*
	 *this function helps to reset an Archive + track + Log Actions an Archive
	 */

	private void resetArchiveTrackAndLog(Archive archive, User user, HttpServletRequest httpServletRequest ){
		archiveService.manageArchiveReset( archive,user);
		tracker.track(archive, "ARCHIVE WAS INVALIDATED BY USER", httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "ARCHIVE WAS INVALIDATED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ARCHIVE ID IS "+archive.getId(),httpServletRequest);

	}
	
	
	/*
	 *@Author MPA
	 *this function helps to load list of Archive at the state registered not old than 1 month by default
	 */
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model){
		  
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		
	    model.addAttribute("archives", archiveService.findByStatusAndRegistrationDateBetween("REGISTERED",cal.getTime(), today));
		return "archive/archive_list";
	}
	

	

	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String findArchives(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			                  @RequestParam("status") String status,@RequestParam("archiveNumber") String archiveNumber, Model model, HttpServletRequest httpServletRequest) throws ParseException {
		
		
		List<Archive> listOfArchive =new ArrayList<>();
		java.sql.Date sqlStartDate = null;
		java.sql.Date sqlendDate = null;
		if(!archiveNumber.equals("NULL")){
			List<Archive> listOfSearch =new ArrayList<>();
			listOfSearch=archiveService.findByArchiveNumber(archiveNumber);
			if(listOfSearch.size()>0){
				listOfArchive.addAll(listOfSearch); 
			}else{
				Person person = personService.findPersonByLicenseNum(archiveNumber);
				if(person != null)
					listOfSearch.add(archiveService.findByPerson(person));
			}
		}else{
			if(!startDate.isEmpty()){
				java.util.Date startDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
				java.util.Date endDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
				sqlStartDate = new java.sql.Date(startDateConverted.getTime());
				sqlendDate = new java.sql.Date(endDateConverted.getTime());
			}
			if(!status.equals("ALL"))
				listOfArchive.addAll(archiveService.findByStatusAndRegistrationDateBetween(status,sqlStartDate, sqlendDate));
			else
				listOfArchive.addAll(archiveService.findByRegistrationDateBetween(sqlStartDate, sqlendDate));

		}
		
		model.addAttribute("archives", listOfArchive);
		return "archive/table";
		
	}

	@Secured({"ROLE_MANAGE_ARCHIVE"})
	@RequestMapping(value="/actionEdit/{archiveId}", method=RequestMethod.GET)
	public String editArchive(Model model , @PathVariable(value="archiveId") int archiveId){

		//we add attributes common to both views
		model.addAttribute("countries", countryService.findAll());
		model.addAttribute("authorities", authorityService.findAll());

		Archive archive = archiveService.findArchiveById(archiveId);
		model.addAttribute("archive", archive);
		return "archive/edit";
	}


    @RequestMapping(value="/saveEditArchive", method=RequestMethod.GET)
    @ResponseBody
	@Transactional
    @Secured({"ROLE_MANAGE_ARCHIVE"})
    public String saveEditArchive(@RequestParam("archiveId") int archiveId,@RequestParam("issuedDate") String issuedDate,@RequestParam("issuedPlace") String issuedPlace,@RequestParam("issuedCountry") int issuedCountry,
								  @RequestParam("examDate") String examDate,@RequestParam("examPlace") String examPlace,@RequestParam("pvNumber") int pvNumber,@RequestParam("archiveBureau") String archiveBureau,
								  @RequestParam("issuedAuthority") int issuedAuthority,@RequestParam("grade") String grade,@RequestParam("archiveNumber") String archiveNumber,@RequestParam("finalAverage") Float finalAverage,
                                   HttpServletRequest httpServletRequest, Model model) throws ParseException {

        User user = userService.getLogedUser(httpServletRequest);
		Archive archive = archiveService.findArchiveById(archiveId);
		java.util.Date examDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(examDate);
		java.sql.Date sqlExamDate = new java.sql.Date(examDateConverted.getTime());
		java.util.Date issuedDateConverted = new SimpleDateFormat("dd/MM/yyyy").parse(issuedDate);
		java.sql.Date sqlIssuedDate = new java.sql.Date(examDateConverted.getTime());

		archive.setExamDate(sqlExamDate);
		archive.setAuthority(authorityService.findAuthorityById(issuedAuthority));
		archive.setFinalAverage(finalAverage);
		archive.setGrade(grade);
		archive.setArchiveNumber(archiveNumber);
		archive.setExamPlace(examPlace);
		archive.setIssuedCountry(countryService.findCountryById(issuedCountry));
		archive.setIssuedDate(sqlIssuedDate);
		archive.setIssuedPlace(issuedPlace);
		archive.setPvNumber(pvNumber);
		archive.setService(archiveBureau);
		archiveService.save(archive);

        tracker.track(archive, "ARCHIVE EDITED BY USER", httpServletRequest);
        logger_.log(Constants.NORMAL_LOG_DIR, "ARCHIVE EDITED BY USER " +user.getId()+" AND NAME " + user.getFirstName()+" "+user.getLastName()+"ARCHIVE ID IS "+archive.getId(),httpServletRequest);
            return "archive/archive_list";
    }
    
    
    /**
     * LevenshteinDistance
     * 
     */
        public static int getLevenshteinDistance(String s, String t) {
            if (s == null || t == null) {
                throw new IllegalArgumentException("Strings must not be null");
            }
     
            int n = s.length(); // length of s
            int m = t.length(); // length of t
     
            if (n == 0) {
                return m;
            } else if (m == 0) {
                return n;
            }
     
            if (n > m) {
                // swap the input strings to consume less memory
                String tmp = s;
                s = t;
                t = tmp;
                n = m;
                m = t.length();
            }
     
            int p[] = new int[n + 1]; //'previous' cost array, horizontally
            int d[] = new int[n + 1]; // cost array, horizontally
            int _d[]; //placeholder to assist in swapping p and d
     
            // indexes into strings s and t
            int i; // iterates through s
            int j; // iterates through t
     
            char t_j; // jth character of t
     
            int cost; // cost
     
            for (i = 0; i <= n; i++) {
                p[i] = i;
            }
     
            for (j = 1; j <= m; j++) {
                t_j = t.charAt(j - 1);
                d[0] = j;
     
                for (i = 1; i <= n; i++) {
                    cost = s.charAt(i - 1) == t_j ? 0 : 1;
                    // minimum of cell to the left+1, to the top+1, diagonally left and up +cost
                    d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
                }
     
                // copy current distance counts to 'previous row' distance counts
                _d = p;
                p = d;
                d = _d;
            }
     
            return p[n];
        }

}

