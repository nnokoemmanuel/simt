package ppp.simt.service.pv;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionStatus;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentQualification;
import ppp.simt.form.CNIForm;
import ppp.simt.form.CandidateChekerForm;
import ppp.simt.form.StudentForm;

public interface StudentSessionService {
	
	public StudentSession findById(int studentSessionId);
	public List<StudentSession> findByEligibleCenter(EligibleCenter eligibleCenter);
	//public List<StudentSession> findByTrainingCenter(TrainingCenter trainingCenter);
	public List<StudentSession> findAll();
	public StudentSession persistStudent(StudentForm studentForm, HttpServletRequest httpServletRequest, Person person);
	public void createStudentSession(StudentSession studentSession);
	public StudentSession persistDup(StudentForm studentForm, HttpServletRequest httpServletRequest,int eligibleCenterId);
	public void updateStudentSession(StudentSession studentSession);
	public List<StudentSession> findByEligibleCenterAndResult(EligibleCenter eligibleCenter, int result);
	public List<StudentSession> findByPvResults(EligibleCenter eligibleCenter);
	public List<StudentSession> findByEligibleCenterAndResultOrderedByPvNumber(EligibleCenter eligibleCenter, int result);
	//public StudentSession save(StudentSession studentSession);
	public StudentSession save(StudentSession studentSession, HttpServletRequest httpServletRequest);
	
	public CandidateChekerForm checkPrerequis(StudentForm studentForm);
	public List<StudentSession> findByEligibleCenterAndResultAndSpeciality(EligibleCenter eligibleCenter, int result,Speciality speciality);
	public List<StudentSession> findByEligibleCenterAndResultOrderedByPerson(EligibleCenter eligibleCenter, int result);
	public List<StudentSession> findByResultAndSpeciality(int result, Speciality speciality);
	

	Student findByReasonForReject(String reasonForReject);
	public StudentSession findById(String id);
	public List<StudentSession> findByEligibleCenterAndResultAndSpecialityAndFinalResult(EligibleCenter eligibleCenter, int result,Speciality speciality);
	
	public List<StudentSession> findByEligibleCenterAndResultAndSpecialityAndTrainingCenter(EligibleCenter eligibleCenter, int result,Speciality speciality, TrainingCenter trainingCenter);
	public List<StudentSession> findByResultAndSpecialityAndTrainingCenter(int result, Speciality speciality , TrainingCenter trainingCenter);
	public List<StudentSession> findByEligibleCenterAndResultAndSpecialityOrderBySurname(EligibleCenter eligibleCenter, int result,Speciality speciality);
	public List<StudentSession> findByStudent(Student student);
	public StudentQualification addStudentHighestQualificationInformation(StudentSession studentSession,
			CNIForm cniForm, HttpServletRequest httpServletRequest);
	public List<StudentSession> findByEligibleCenterAndResultAndSpecialityAndTrainingCenterOrderBySurname(EligibleCenter eligibleCenter, int result,Speciality speciality, TrainingCenter trainingCenter);
	public List<StudentSession> findByStudentOrderByEligibleCenterAsc(Student student);
	public List<StudentSession> findByStudentSessionStatusAndEligibleCenter(StudentSessionStatus studentSessionStatus,EligibleCenter eligibleCenter);

}
