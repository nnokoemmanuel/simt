package ppp.simt.service.engines;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.archive.Archive;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.production.Application;
import ppp.simt.entity.production.InSlip;
import ppp.simt.entity.production.OutSlip;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.user.User;
import ppp.simt.service.archive.ArchiveService;
import ppp.simt.service.core.CoreService;
import ppp.simt.service.pv.StudentService;
import ppp.simt.service.pv.StudentSessionService;


@Service
public class ApplicantCheckerServiceImpl implements ApplicantCheckerService {

	@Autowired
	private CoreService coreService;
	
	@Autowired
	private ArchiveService archiveService;
	
	@Autowired
	private StudentSessionService studentSessionService;
	
	@Autowired
	private StudentService studentService;

	@Override
	public boolean capecExistance(Person applicantPerson , String userSpeciality) {
		// TODO Auto-generated method stub
		Archive personArchive = archiveService.findByPerson(applicantPerson);
		

		userSpeciality="MAE";
		
		if(personArchive != null){
			return true;
		}else{
			List<Student> students = studentService.findByPerson(applicantPerson);
			for(Student student : students){
					List<StudentSession> studentSessions = studentSessionService.findByStudent(student);
					for(StudentSession studentSession : studentSessions){
						Speciality studentSessionSpeciality = studentSession.getSpeciality();
						
						if(studentSessionSpeciality == null)
							studentSessionSpeciality = studentSession.getStudent().getSpeciality();
						
						if(studentSessionSpeciality.getAbbreviation().equals(userSpeciality)){
							if(studentSession.getStudentSessionStatus().getId() == 4 && studentSession.getEligibleCenter().getEligibleCenterStatus().getId()==4)
								return true;
					     }
				     }
			}
		}
		return false;
	}
	
	
}
