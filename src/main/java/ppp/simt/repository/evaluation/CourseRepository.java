package ppp.simt.repository.evaluation;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Module;




@Repository("courseRepository")
public interface CourseRepository extends JpaRepository <Course, Integer>{
	
	Course findById(int courseId);

	public List<Course> findAll(); 
	
	public List<Course> findByModule(Module module); 

	public Course save(Course course);
	
	
	public List<Course> findByModuleOrderByCompleteName(Module module);

    default List<Course> findBySpeciality(Speciality speciality, EntityManager em) {
   
		
		
		String query = "SELECT DISTINCT new  ppp.simt.entity.evaluation.Course(co.id,co.completeName, co.module, co.courseCoefficient, co.courseMaxNote ) FROM Course co, Module mo , Speciality sp ";
		String whereClause = "WHERE co.module = mo.id AND  mo.speciality =: speciality  ";
		query += whereClause;
		javax.persistence.Query q;
		q = em.createQuery(query);
		q.setParameter("speciality", speciality);

		
		return q.getResultList();
	} 
    
	
    default List<Course> findBySpecialityAndOrderByCompleteName(Speciality speciality, EntityManager em) {
   
		
		
		String query = "SELECT DISTINCT new  ppp.simt.entity.evaluation.Course(co.id,co.completeName, co.module, co.courseCoefficient, co.courseMaxNote ) FROM Course co, Module mo , Speciality sp ";
		String whereClause = "WHERE co.module = mo.id AND  mo.speciality =: speciality order by co.completeName ";
		query += whereClause;
		javax.persistence.Query q;
		q = em.createQuery(query);
		q.setParameter("speciality", speciality);

		
		return q.getResultList();
	} 
    
    
       default List<Course> findBySpecialityWithoutTranscriptsLink(Speciality speciality, EntityManager em) {
   
		
		
		String query = "SELECT DISTINCT new  ppp.simt.entity.evaluation.Course(co.id,co.completeName, co.module, co.courseCoefficient, co.courseMaxNote ) FROM Course co, Module mo , Speciality sp  ";
		String whereClause = "WHERE co.module = mo.id AND  mo.speciality =: speciality AND co.id not in (SELECT DISTINCT co.id FROM Course co, Transcript tr where tr.course=co.id ) ";
		query += whereClause;
		javax.persistence.Query q;
		q = em.createQuery(query);
		q.setParameter("speciality", speciality);

		
		return q.getResultList();
	} 
    
    
    List<Course> findByCompleteName(String courseName);
    
    Course findByCompleteNameAndModule(String courseName,Module module);
	

}
