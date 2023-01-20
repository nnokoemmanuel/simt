package ppp.simt.repository.pv;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Division;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.TrainingCenter;

@Repository("trainingCenterRepository")
public interface TrainingCenterRepository extends JpaRepository<TrainingCenter, Integer>{
	TrainingCenter findById(int id);
	 List<TrainingCenter> findAll();
	 
	default List<TrainingCenter> findTrainingCenterByEligibleSpeciality(Speciality speciality, EntityManager em){
		String query = "";
		javax.persistence.Query q;
		query = "SELECT tc FROM TrainingCenter tc JOIN tc.eligibleSpeciality es WHERE es.speciality=:speciality";
		
		q = em.createQuery(query);
		q.setParameter("speciality", speciality);
		
		return q.getResultList();
		
	}
	

	 
	 
	 default List<TrainingCenter> findBySpecialityAndDivision(Speciality speciality,Division division, EntityManager em) {
			
			String query = "SELECT tc FROM TrainingCenter tc JOIN tc.eligibleSpeciality es JOIN tc.division div  WHERE es.speciality=:speciality AND div=:division";

			javax.persistence.Query q;
			q = em.createQuery(query);
			q.setParameter("speciality", speciality);
			q.setParameter("division", division);

			
			return q.getResultList();
     } 
	 
	 

	
	 
	 
	 default List<TrainingCenter> findBySpecialityAndStatus(Speciality speciality,int status, EntityManager em) {
			
			String query = "SELECT tc FROM TrainingCenter tc JOIN tc.eligibleSpeciality es JOIN tc.agreement ag  WHERE tc.status=:status ";

			javax.persistence.Query q;
			q = em.createQuery(query);
			q.setParameter("speciality", speciality);
			q.setParameter("status", status);

			
			return q.getResultList();
      } 
	 
	 default List<TrainingCenter> findBySpecialityAndDivisionAndStatus(Speciality speciality,Division division,int status, EntityManager em) {
		 
		 String query = "SELECT tc FROM TrainingCenter tc JOIN tc.eligibleSpeciality es JOIN tc.division div  WHERE es.speciality=:speciality AND div=:division AND tc.status=:status";

			javax.persistence.Query q;
			q = em.createQuery(query);
			q.setParameter("speciality", speciality);
			q.setParameter("division", division);
			q.setParameter("status", status);

			
			return q.getResultList();
		 
	 }
	 
     
     
    
     
     default List<TrainingCenter> findByDivision(Division division, EntityManager em) {
			
    	 String query = "SELECT tc FROM TrainingCenter tc  JOIN tc.division div   WHERE div=:division  ";

			javax.persistence.Query q;
			q = em.createQuery(query);
			q.setParameter("division", division);
			return q.getResultList();
     }
     
     public List<TrainingCenter> findByStatus(int status);
 	 public List<TrainingCenter>  findByDivisionAndStatus(Division division , int status);
 	 
 	
 	


 	
	 
	
}
