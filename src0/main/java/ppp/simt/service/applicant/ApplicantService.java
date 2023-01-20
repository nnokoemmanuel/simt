package ppp.simt.service.applicant;

import java.util.List;


import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.form.ApplicantForm;
import ppp.simt.entity.core.Person;


public interface ApplicantService {
	public Applicant findById(int applicant);
	public List<Applicant> findByEntranceEligibleCenter(EntranceEligibleCenter entranceEligibleCenter);
	public List<Applicant> findAll();
	public void createApplicant(Applicant applicant);
	public void updateApplicant(Applicant applicant);
	public List<Applicant> findByEntranceEligibleCenterAndResult(EntranceEligibleCenter entranceEligibleCenter, String result);
	public List<Applicant> findByEntranceEligibleCenterAndResultOrderedByPerson(EntranceEligibleCenter eligibleCenter,String result);
	public List<Applicant> findByPvResults(EntranceEligibleCenter eligibleCenter);
	public Applicant persistApplicant(ApplicantForm applicantForm, HttpServletRequest httpServletRequest, Person person,EntranceEligibleCenter entranceEligibleCenter);
	public List<Applicant> findByEntranceEligibleCenterAndResultAndSpeciality(EntranceEligibleCenter eligibleCenter, String result, Speciality speciality);
	public List<Applicant> findByEntranceEligibleCenterAndResultAndSpecialityAndStudent(EntranceEligibleCenter eligibleCenter, Speciality speciality);
	public List<Applicant> findByEntranceEligibleCenterAndResultAndStudentExists(
			EntranceEligibleCenter entranceEligibleCenter, String result);
	public List<Applicant> findByEntranceEligibleCenterAndSpeciality(EntranceEligibleCenter entranceEligibleCenter,Speciality speciality);
	public List<Applicant> findByEntranceEligibleCenterAndResultOrderedByNote(EntranceEligibleCenter eligibleCenter,String result);

}
