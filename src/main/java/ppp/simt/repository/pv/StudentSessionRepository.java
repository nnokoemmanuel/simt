package ppp.simt.repository.pv;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

//import com.ppp.entity.Candidate;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.StudentSessionStatus;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.Student;



@Repository("studentSessionRepository")
public interface StudentSessionRepository extends JpaRepository <StudentSession, Integer>{
	
	StudentSession findById(int candidateSession);
	List<StudentSession> findByEligibleCenter(EligibleCenter eligibleCenter);
	
	//List<StudentSession> findByTrainingCenter(TrainingCenter trainingCenter);
	List<StudentSession> findAll();
		
	default List<StudentSession> findByEligibleCenterAndResult(EligibleCenter eligibleCenter, int result, EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		query = "SELECT cs FROM StudentSession cs WHERE cs.eligibleCenter=:eligibleCenter AND cs.studentSessionStatus.id=:examResult";
		q = em.createQuery(query);
		q.setParameter("eligibleCenter", eligibleCenter);
		q.setParameter("examResult", result);
		
		
		return q.getResultList();
	}

	
	default List<StudentSession> findByPvResults(EligibleCenter eligibleCenter, EntityManager em){
		String query = "";
		javax.persistence.Query q;
		query = "SELECT cs FROM StudentSession cs WHERE cs.eligibleCenter=:eligibleCenter AND cs.studentSessionStatus.id IN (1,2) ";
		q = em.createQuery(query);
		q.setParameter("eligibleCenter", eligibleCenter);
		return q.getResultList();
	}
	
	default List<StudentSession> findByEligibleCenterAndResultOrderedByPerson(EligibleCenter eligibleCenter, int result, EntityManager em) {
		String query = "";
		javax.persistence.Query q;
		System.out.println(result);
		query = "SELECT cs FROM StudentSession cs  WHERE cs.eligibleCenter=:eligibleCenter AND cs.studentSessionStatus.id=:examResult ORDER BY cs.student.person.surName, cs.student.person.givenName";
		q = em.createQuery(query);
		q.setParameter("eligibleCenter", eligibleCenter);
		q.setParameter("examResult", result);
		return q.getResultList();
	}

	default List<StudentSession> findByEligibleCenterAndResultOrderedByPvNumber(EligibleCenter eligibleCenter, int result, EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		query = "SELECT cs FROM StudentSession cs WHERE cs.eligibleCenter=:eligibleCenter AND cs.studentSessionStatus.id=:examResult";
		q = em.createQuery(query);
		q.setParameter("eligibleCenter", eligibleCenter);
		q.setParameter("examResult", result);
		

		return q.getResultList();
	}
	

	public StudentSession save(StudentSession studentSession);
	
	
	Student findByReasonForReject(String reasonForReject);
	public StudentSession findById(String id);
	
	

	default List<StudentSession> findByEligibleCenterAndResultAndSpeciality(EligibleCenter eligibleCenter, int result,Speciality speciality, EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		
		query = "SELECT DISTINCT new  ppp.simt.entity.pv.StudentSession( cs.id, cs.photoAndSignature, cs.pvNumber, cs.processed, cs.studentSessionStatus, cs.eligibleCenter, cs.countFile, cs.student, cs.student.trainingCenter.name, cs.student.person.surName, cs.student.person.givenName) FROM StudentSession cs, StudentSessionStatus ss , Student st , Speciality sp ";
		String whereClause = "WHERE cs.student = st.id AND cs.speciality =:speciality AND  cs.eligibleCenter =: eligibleCenter AND cs.studentSessionStatus=ss.id AND ss.id=:examResult ORDER BY cs.student.trainingCenter.name , cs.student.person.surName,  cs.student.person.givenName ASC";

		query += whereClause;
		q = em.createQuery(query);
		System.out.println(q);

		q.setParameter("speciality", speciality);
		q.setParameter("examResult", result);
		q.setParameter("eligibleCenter", eligibleCenter);
		
		return q.getResultList();
	}
	

	default List<StudentSession> findByEligibleCenterAndResultAndSpecialityOrderBySurname(EligibleCenter eligibleCenter, int result,Speciality speciality, EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		
		query = "SELECT DISTINCT new  ppp.simt.entity.pv.StudentSession( cs.id, cs.photoAndSignature, cs.pvNumber, cs.processed, cs.studentSessionStatus, cs.eligibleCenter, cs.countFile ,cs.student, cs.student.person.surName ) FROM StudentSession cs, StudentSessionStatus ss , Student st , Speciality sp ";
		String whereClause = "WHERE cs.student = st.id AND cs.speciality =:speciality AND  cs.eligibleCenter =: eligibleCenter AND cs.studentSessionStatus=ss.id AND ss.id=:examResult ORDER BY cs.student.person.surName ASC";

		query += whereClause;
		q = em.createQuery(query);
		System.out.println(q);

		q.setParameter("speciality", speciality);
		q.setParameter("examResult", result);
		q.setParameter("eligibleCenter", eligibleCenter);
		
		return q.getResultList();
	}


	default List<StudentSession> findByEligibleCenterAndResultAndSpecialityAndTrainingCenterOrderBySurname(EligibleCenter eligibleCenter, int result,Speciality speciality,TrainingCenter trainingCenter, EntityManager em) {

		String query = "";
		javax.persistence.Query q;

		query = "SELECT DISTINCT new  ppp.simt.entity.pv.StudentSession( cs.id, cs.photoAndSignature, cs.pvNumber, cs.processed, cs.studentSessionStatus, cs.eligibleCenter, cs.countFile ,cs.student, cs.student.person.surName) FROM StudentSession cs, StudentSessionStatus ss , Student st , Speciality sp , TrainingCenter tr ";
		String whereClause = "WHERE cs.student = st.id AND cs.speciality =:speciality AND  cs.eligibleCenter =: eligibleCenter AND cs.studentSessionStatus=ss.id AND ss.id=:examResult AND  cs.student.trainingCenter =:trainingCenter ORDER BY cs.student.person.surName ASC";

		query += whereClause;
		q = em.createQuery(query);
		System.out.println(q);

		q.setParameter("speciality", speciality);
		q.setParameter("examResult", result);
		q.setParameter("eligibleCenter", eligibleCenter);
		q.setParameter("trainingCenter", trainingCenter);
		return q.getResultList();
	}
	
	default List<StudentSession> findByResultAndSpeciality(int result, Speciality speciality,EntityManager em){
		
		String query = "";
		javax.persistence.Query q;
		query = "SELECT DISTINCT new  ppp.simt.entity.pv.StudentSession(cs.id, cs.photoAndSignature, cs.pvNumber, cs.processed, cs.studentSessionStatus, cs.eligibleCenter, cs.countFile ,cs.student) FROM StudentSession cs, StudentSessionStatus ss , Student st , Speciality sp ";
		String whereClause = "WHERE cs.student = st.id  AND cs.studentSessionStatus=ss.id AND ss.id=:examResult ORDER BY cs.pvNumber";
		if(result != 4){
			whereClause = "WHERE cs.student = st.id AND cs.speciality =:speciality  AND cs.studentSessionStatus=ss.id AND ss.id=:examResult";
		}else{
			query = "SELECT DISTINCT new  ppp.simt.entity.pv.StudentSession(cs.id, cs.photoAndSignature, cs.pvNumber, cs.processed, cs.studentSessionStatus, cs.eligibleCenter, cs.countFile ,cs.student) FROM StudentSession cs , Student st , StudentSessionStatus ss";
		}
		query += whereClause;
		q = em.createQuery(query);
		if(result != 4)
			q.setParameter("speciality", speciality);
		q.setParameter("examResult", result);
		
     	return q.getResultList();
		
	}
	
	
	default List<StudentSession> findByEligibleCenterAndResultAndSpecialityAndComputerizationDateBetween(java.sql.Date startDate,java.sql.Date endDate ,EligibleCenter eligibleCenter, int result,Speciality speciality , EntityManager em){
		
		String query = "";
		javax.persistence.Query q;
		
		query = "SELECT DISTINCT new  ppp.simt.entity.pv.StudentSession(cs.id, cs.photoAndSignature, cs.pvNumber, cs.processed, cs.studentSessionStatus, cs.eligibleCenter, cs.countFile ,cs.student) FROM StudentSession cs, StudentSessionStatus ss , Student st , Speciality sp ";
		String whereClause = "WHERE cs.student = st.id AND  cs.eligibleCenter =: eligibleCenter  AND cs.studentSessionStatus=ss.id AND ss.id=:examResult ORDER BY cs.pvNumber";
		if(result != 4){
			whereClause = "WHERE cs.student = st.id AND cs.speciality =:speciality AND  cs.eligibleCenter =: eligibleCenter AND cs.studentSessionStatus=ss.id AND ss.id=:examResult";
		}else{
			query = "SELECT DISTINCT  new  ppp.simt.entity.pv.StudentSession(cs.id, cs.photoAndSignature, cs.pvNumber, cs.processed, cs.studentSessionStatus, cs.eligibleCenter, cs.countFile ,cs.student) FROM StudentSession cs , Student st , StudentSessionStatus ss";
		}
		query += whereClause;
		q = em.createQuery(query);
		if(result != 4)
			q.setParameter("speciality", speciality);
		q.setParameter("examResult", result);
		q.setParameter("eligibleCenter", eligibleCenter);
		
		return q.getResultList();
	}
	
	default List<StudentSession> findByEligibleCenterAndResultAndSpecialityAndTrainingCenter(EligibleCenter eligibleCenter, int result,Speciality speciality,TrainingCenter trainingCenter, EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		
		query = "SELECT DISTINCT new  ppp.simt.entity.pv.StudentSession( cs.id, cs.photoAndSignature, cs.pvNumber, cs.processed, cs.studentSessionStatus, cs.eligibleCenter, cs.countFile ,cs.student) FROM StudentSession cs, StudentSessionStatus ss , Student st , Speciality sp , TrainingCenter tr ";
		String whereClause = "WHERE cs.student = st.id AND cs.speciality =:speciality AND  cs.eligibleCenter =: eligibleCenter AND cs.studentSessionStatus=ss.id AND ss.id=:examResult AND  cs.student.trainingCenter =:trainingCenter ";

		query += whereClause;
		q = em.createQuery(query);
		System.out.println(q);

		q.setParameter("speciality", speciality);
		q.setParameter("examResult", result);
		q.setParameter("eligibleCenter", eligibleCenter);
		q.setParameter("trainingCenter", trainingCenter);
		return q.getResultList();
	}
	
	
  default List<StudentSession> findByResultAndSpecialityAndTrainingCenter(int result, Speciality speciality, TrainingCenter trainingCenter,EntityManager em){
		
		String query = "";
		javax.persistence.Query q;
		query = "SELECT DISTINCT new  ppp.simt.entity.pv.StudentSession(cs.id, cs.photoAndSignature, cs.pvNumber, cs.processed, cs.studentSessionStatus, cs.eligibleCenter, cs.countFile ,cs.student) FROM StudentSession cs, StudentSessionStatus ss , Student st , Speciality sp ";
		String  whereClause = "WHERE cs.student = st.id AND cs.speciality =:speciality  AND cs.studentSessionStatus=ss.id AND ss.id=:examResult AND cs.student.trainingCenter=: trainingCenter";
	
		query += whereClause;
		q = em.createQuery(query);
		q.setParameter("speciality", speciality);
		q.setParameter("examResult", result);
		q.setParameter("trainingCenter", trainingCenter);
		
     	return q.getResultList();
		
	}
  
  default List<StudentSession> findByEligibleCenterAndResultAndSpecialityAndFinalResult(EligibleCenter eligibleCenter, int result,Speciality speciality, EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		
		query = "SELECT DISTINCT new  ppp.simt.entity.pv.StudentSession( cs.id, cs.photoAndSignature, cs.pvNumber, cs.processed, cs.studentSessionStatus, cs.eligibleCenter, cs.countFile ,cs.student,cs.note) FROM StudentSession cs, StudentSessionStatus ss , Student st , Speciality sp ";
		String whereClause = "WHERE cs.student = st.id AND cs.speciality =:speciality AND  cs.eligibleCenter =: eligibleCenter AND cs.studentSessionStatus=ss.id AND ss.id=:examResult ORDER BY cs.note DESC";

		query += whereClause;
		q = em.createQuery(query);
		System.out.println(q);

		q.setParameter("speciality", speciality);
		q.setParameter("examResult", result);
		q.setParameter("eligibleCenter", eligibleCenter);
		return q.getResultList();
	}
  
  	List<StudentSession> findByStudent(Student student);
	List<StudentSession> findByStudentOrderByEligibleCenterAsc(Student student);
	public List<StudentSession> findByStudentSessionStatusAndEligibleCenter(StudentSessionStatus studentSessionStatus,
			EligibleCenter eligibleCenter) ;

	


}
