package ppp.simt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.ServletContext;
import org.springframework.core.io.InputStreamResource;


import com.itextpdf.text.DocumentException;

import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.ApplicationTranscript;
import ppp.simt.entity.production.Certificate;
import ppp.simt.entity.production.ProfessionalCard;
import ppp.simt.entity.pv.ExamSession;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.form.SimtLicenseChecker;
import ppp.simt.repository.core.PersonRepository;
import ppp.simt.service.archive.ArchiveService;
import ppp.simt.service.core.ConsultRecordService;
import ppp.simt.service.core.EligibilityChecher;
import ppp.simt.service.production.ApplicationService;
import ppp.simt.service.production.ApplicationTranscriptService;
import ppp.simt.service.production.CertificateService;
import ppp.simt.service.production.ProfessionalCardService;
import ppp.simt.service.pv.EligibleCenterService;
import ppp.simt.service.pv.ExamCenterService;
import ppp.simt.service.pv.ExamSessionService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.service.pv.TrainingCenterService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ConsultRecordController {
	
	@Autowired
	private ConsultRecordService consultRecordService;

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	protected StudentService studentService;
	
	@Autowired
	protected EligibilityChecher eligibilityChecher;
	
	@Autowired
	private EligibleCenterService eligibleCenterService;
	
	@Autowired
	private ExamSessionService examSessionService;
	
	@Autowired
	private ExamCenterService examCenterService;
	
	@Value("${candidateSession.file.folder}")
   	private String studentSessionFiles;
	
	@Autowired
    private ServletContext servletContext;
	
	@Autowired
	protected CertificateService certificateService;
	
	@Autowired
	protected ArchiveService archiveService  ;
	
	@Autowired
	protected ProfessionalCardService professionalCardService ;
	
	@Autowired
	protected  ApplicationTranscriptService  applicationTranscriptService ;
	
	@Autowired
	protected  ApplicationService applicationService   ;
	
	

	
	@RequestMapping("/read")
	public String findRecord(@RequestParam("searchTerm") String searchTerm, Model model) {
		System.out.println("helllllllllllllllloooooooooooo " + searchTerm);
		try {
			model.addAttribute("records", consultRecordService.findRecord(searchTerm));			
		} catch (Exception e) {
			model.addAttribute("error", 1);
			model.addAttribute("records", null);			
		}
		return  "search/consult";
	}

	@RequestMapping(value="/more/{personId}", method = RequestMethod.GET)
	public String showMore(@PathVariable(value="personId") int personId, HttpServletRequest httpServletRequest, Model model){
		int maxApplication=0 ;
		int tmpId=0 ;
		String photo="";
		String candidatePhoto ="";
		String status ="";
		int countFile =0 ;
		Person person =personRepository.findById(personId);
		

    

		List<Student> studentOccurance = studentService.findByPerson(person);
		List<StudentSession> finalStudentSessions = new ArrayList<StudentSession>();
		List<Application> finalApplications = new ArrayList<Application>();
		for(Student student : studentOccurance){
			for(StudentSession studentSession: student.getStudentSessions()){
				finalStudentSessions.add(studentSession);
				if(studentSession.getCertificate() != null){
					List<Application> addedApps = applicationService.findApplicationByCertificate(studentSession.getCertificate());
					for(Application addedApp : addedApps){
						if(!finalApplications.contains(addedApp))
							finalApplications.add(addedApp);
					}
				}
				if(studentSession.getApplicationTranscript() != null){
					List<Application> addedApps = applicationService.findApplicationByTranscript(studentSession.getApplicationTranscript());
					for(Application addedApp : addedApps){
						if(!finalApplications.contains(addedApp))
							finalApplications.add(addedApp);
					}
				}
			}
			
		}
		
		Archive archive = archiveService.findByPerson(person);
		List<Certificate> certificates = new ArrayList<Certificate>()  ;
		List<Application> applications = new ArrayList<Application>()  ;
		
		
		if(studentOccurance.size()!=0) {
			for (int i = 0; i < studentOccurance.size(); i++) {
				Set<StudentSession>  studentSessions = studentOccurance.get(i).getStudentSessions();
				
				if(studentSessions.size()>0) {
					for(StudentSession studentSession : studentSessions){
						if(!(studentSession.getStudentSessionStatus().getId() == 1 || studentSession.getStudentSessionStatus().getId() == 3 || studentSession.getStudentSessionStatus().getId() == 6)) {
							status = "OK";
							photo = studentSession.getPhotoAndSignature();
							countFile = studentSession.getCountFile();
							if(studentSession.getStudentSessionStatus().getId() == 4) {
							Certificate certificate = certificateService.findCertificateByStudentSession(studentSession) ;
							certificates.add(certificate);
							if(certificate!=null) {
							List<Application> certificateApps = applicationService.findApplicationByCertificate(certificate);
							for (int x = 0; x < certificateApps.size(); x++) {
								applications.add(certificateApps.get(x));
							}
								}
									}
							ApplicationTranscript appTranscript = applicationTranscriptService.findByStudentSession(studentSession);
							List<Application> transcriptApps = applicationService.findApplicationByTranscript(appTranscript);
							for (int n = 0; n < transcriptApps.size(); n++) {
								applications.add(transcriptApps.get(n));
							}
							model.addAttribute("countFile", countFile);
						}
						
					}
					
					model.addAttribute("applications", finalApplications);
					model.addAttribute("photo", photo);
					model.addAttribute("certificates", certificates);
					model.addAttribute("studentSessions", finalStudentSessions);
					model.addAttribute("students", studentOccurance);
					
				}

				
			}
			
		}

		model.addAttribute("person", person);
		model.addAttribute("archive",archive);
		return "consult/more_page";
	}
	
	@RequestMapping("/view")
	public String view(@RequestParam("id") int id,Model model) {
		
		Student student= studentService.findById(id);
		model.addAttribute("student", student);
		SimtLicenseChecker result= eligibilityChecher.check(student.getPerson().getLicenseNum(), "moniteur","BEPEC");
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String catBDate = formatter.format(result.getCatB_Date());
		Set<StudentSession> studentSessions = student.getStudentSessions();

		
		String status ="";
		String photo = "";
		int countFile  ;
		if(student.getStudentSessions().size()>0) {
			for(StudentSession studentSession : studentSessions){
				if(!(studentSession.getStudentSessionStatus().getId() == 1 || studentSession.getStudentSessionStatus().getId() == 3)) {
					status = "OK";
					photo = studentSession.getPhotoAndSignature();
					countFile = studentSession.getCountFile();
					model.addAttribute("countFile", countFile);
					
					break;
				}
				
			}
			model.addAttribute("photo", photo);
			
		}
		
		model.addAttribute("studentSession_Status", status);
		
		String actifSession ="yes";
		List<ExamSession> session = examSessionService.findByStatusOrderByIdDesc("OPEN");
		if(session.size()==0 || student.getStudentSessions().size()!=0 || result.getCan_be_presented().equals("no")) {
			
			actifSession ="no";
		}else {
			
			model.addAttribute("session", session.get(0));
			model.addAttribute("eligibleCenters", eligibleCenterService.findByExamSession(session.get(0)));
		}
		 
		//Test for cat G :: MAE or Experts[IPCSR, DPCSR]
		String all_categories = result.getCategories();
		 if(!all_categories.contains("G")) {
				
				model.addAttribute("all_categories",all_categories);
				
			} 
		 
		 
		model.addAttribute("isActifSession","no");
		model.addAttribute("categories", result.getCategories());
		model.addAttribute("examCenters", examCenterService.findAll());
		model.addAttribute("CatB_Date",catBDate);
		return "pv/consult_details"; 
	}
	
	@RequestMapping(value="/downloadFile/{studentSessionId}/{count}", method=RequestMethod.GET)
	public  void downloadFile( @PathVariable(value="studentSessionId") int studentSessionId ,@PathVariable(value="count") int count ,Model model,HttpServletRequest httpServletRequest,HttpServletResponse response) 
			throws IOException, DocumentException{
		Student student= studentService.findById(studentSessionId);
		Set<StudentSession> studentSessions = student.getStudentSessions();
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		HttpHeaders headers = new HttpHeaders();
		
		String fileName = studentSessionId+"_"+count+".pdf";		
		String file = studentSessionFiles + "/" + studentSessionId+"_"+count+".pdf";
		
		 response.setContentType("application/pdf");
	        //response.setHeader("Content-Disposition", "attachment; filename=\"demo.pdf\"");
	        InputStream inputStream = new FileInputStream(new File(studentSessionFiles + fileName));
	        int nRead;
	        while ((nRead = inputStream.read()) != -1) {
	            response.getWriter().write(nRead);
	        }
		
	}
	
	@RequestMapping(value="/consult/load", method=RequestMethod.GET)
	public String showConsultView(HttpServletRequest httpServletRequest, Model model) {

		return  "search/main_view";
	}
	
}
