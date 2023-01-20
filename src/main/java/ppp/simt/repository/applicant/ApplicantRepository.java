package ppp.simt.repository.applicant;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.applicant.Applicant;
import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;


@Repository("applicantRepository")
public interface ApplicantRepository extends JpaRepository <Applicant, Integer>{
	
	Applicant findById(int applicantId);

	List<Applicant> findAll(); 
		
	default List<Applicant> findByEntranceEligibleCenterAndResult(EntranceEligibleCenter eligibleCenter, String result, EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		query = "SELECT cs FROM Applicant cs WHERE cs.entranceEligibleCenter=:eligibleCenter AND cs.result=:examResult ORDER BY cs.note";
		q = em.createQuery(query);
		q.setParameter("eligibleCenter", eligibleCenter);
		q.setParameter("examResult", result);
		
		
		return q.getResultList();
	}
	
	default List<Applicant> findByEntranceEligibleCenterAndResultOrderedByPerson(EntranceEligibleCenter eligibleCenter, String result, EntityManager em) {
		String query = "";
		javax.persistence.Query q;
		System.out.println(result);
		query = "SELECT cs FROM StudentSession cs  WHERE cs.entranceEligibleCenter=:eligibleCenter AND cs.result=:examResult ORDER BY cs.student.person.surName, cs.student.person.givenName";
		q = em.createQuery(query);
		q.setParameter("eligibleCenter", eligibleCenter);
		q.setParameter("examResult", result);
		return q.getResultList();
	}

	default List<Applicant> findByEntranceEligibleCenterAndResultOrderedByPvNumber(EntranceEligibleCenter eligibleCenter, String result, EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		query = "SELECT cs FROM Applicant cs WHERE cs.eligibleCenter=:eligibleCenter AND cs.result=:examResult";
		q = em.createQuery(query);
		q.setParameter("eligibleCenter", eligibleCenter);
		q.setParameter("examResult", result);
		

		return q.getResultList();
	}
	

	public Applicant save(Applicant applicant);		

	public StudentSession save(StudentSession studentSession);
	public List<Applicant> findByEntranceEligibleCenter(EntranceEligibleCenter entranceEligibleCenter);
	public List<Applicant> findByEntranceEligibleCenterAndResult(EntranceEligibleCenter entranceEligibleCenter, String result);

	default List<Applicant> findByPvResults(EntranceEligibleCenter eligibleCenter, EntityManager em){
		String query = "";
		javax.persistence.Query q;
		query = "SELECT cs FROM Applicant cs WHERE cs.entranceEligibleCenter=:eligibleCenter AND cs.result in ('REGISTERED','PASSED THEORY')";
		q = em.createQuery(query);
		q.setParameter("eligibleCenter", eligibleCenter);
		return q.getResultList();  
	}

	//public List<Applicant> findByEntranceEligibleCenterAndResultAndSpeciality(EntranceEligibleCenter eligibleCenter, String result, Speciality speciality);
	default List<Applicant> findByEntranceEligibleCenterAndResultAndSpeciality(EntranceEligibleCenter eligibleCenter, String result, Speciality speciality, EntityManager em){
		String query = "";
		javax.persistence.Query q;
		query = "SELECT cs FROM Applicant cs WHERE cs.entranceEligibleCenter=:eligibleCenter AND cs.result=:result AND cs.speciality =:speciality ORDER BY cs.person.surName ASC";
		q = em.createQuery(query);
		q.setParameter("eligibleCenter", eligibleCenter);
		q.setParameter("result", result);
		q.setParameter("speciality", speciality);
		return q.getResultList();
	}



	default List<Applicant> findByEntranceEligibleCenterAndResultAndSpecialityAndStudent(EntranceEligibleCenter eligibleCenter, Speciality speciality, EntityManager em){
		String query = "";
		javax.persistence.Query q;
		query = "SELECT cs FROM Applicant cs WHERE cs.entranceEligibleCenter=:eligibleCenter AND cs.result='PASSED PRACTICALS' AND cs.speciality =:speciality AND cs.student IS NOT NULL ORDER BY cs.person.surName ASC";
		q = em.createQuery(query);
		q.setParameter("eligibleCenter", eligibleCenter);
		q.setParameter("speciality", speciality);
		return q.getResultList();
	}

	default List<Applicant> findByEntranceEligibleCenterAndResultAndStudentExists(EntranceEligibleCenter entranceEligibleCenter, String result, EntityManager em){
		String query = "";
		javax.persistence.Query q;
		query = "SELECT app FROM Applicant app WHERE app.entranceEligibleCenter=:entranceEligibleCenter AND app.result=:result AND app.student IS NOT NULL ";
		q = em.createQuery(query);
		q.setParameter("entranceEligibleCenter", entranceEligibleCenter);
		q.setParameter("result", result);
		return q.getResultList();
	}
	
	default List<Applicant> findByEntranceEligibleCenterAndSpeciality(EntranceEligibleCenter entranceEligibleCenter,Speciality speciality, EntityManager em){
		String query = "";
		javax.persistence.Query q;
		query = "SELECT cs FROM Applicant cs WHERE cs.entranceEligibleCenter=:entranceEligibleCenter AND cs.speciality =:speciality";
		q = em.createQuery(query);
		q.setParameter("entranceEligibleCenter", entranceEligibleCenter);
		q.setParameter("speciality", speciality);
		return q.getResultList();
		
	}
	
	default List<Applicant> findByEntranceEligibleCenterAndResultOrderedByNote(EntranceEligibleCenter eligibleCenter, String result, EntityManager em) {
		String query = "";
		javax.persistence.Query q;
		System.out.println(result);
		query = "SELECT cs FROM Applicant cs  WHERE cs.entranceEligibleCenter=:eligibleCenter AND cs.result=:examResult ORDER BY cs.note DESC";
		q = em.createQuery(query);
		q.setParameter("eligibleCenter", eligibleCenter);
		q.setParameter("examResult", result);
		return q.getResultList();
	}

}
