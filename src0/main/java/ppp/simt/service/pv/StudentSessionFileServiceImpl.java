package ppp.simt.service.pv;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ppp.simt.entity.core.Category;
import ppp.simt.entity.core.Country;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionFile;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.user.User;
import ppp.simt.form.StudentForm;
import ppp.simt.repository.pv.StudentSessionFileRepository;
import ppp.simt.service.core.CategoryService;
import ppp.simt.service.core.CoreService;
import ppp.simt.service.core.CountryService;
import ppp.simt.service.core.PersonService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;

@Service("studentSessionFileService")
public class StudentSessionFileServiceImpl implements StudentSessionFileService{

	@Autowired
	private StudentSessionFileRepository studentSessionFileRepository;
	
	@Autowired
	private StudentSessionService studentSessionService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TrainingCenterService trainingCenterService;

	@Autowired
	EntityManager em;
	
	@Autowired
	private CoreService coreService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private EligibleCenterService eligibleCenterService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired
	private CountryService countryService;
	
	@Value("${candidateSession.file.folder}")
	private String candidateSessionFolder;
	

	@Override
	public StudentSessionFile findById(int studentSessionFileId) {
		return studentSessionFileRepository.findById(studentSessionFileId);
	}


	@Override
	public List<StudentSessionFile> findAll() {
		return studentSessionFileRepository.findAll();
	}
	
	@Override
	public Set<StudentSessionFile> findByStudentSessionAndDeleted(StudentSession studentSession, int deleted){
		return studentSessionFileRepository.findByStudentSessionAndDeleted(studentSession,  deleted);
	}


	@Override
	public void save(StudentSessionFile studentFile) {
		studentSessionFileRepository.save(studentFile);
		
	}
	





}
