package ppp.simt.repository.core;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.core.Person;



@Repository("personRepository")
public interface PersonRepository extends JpaRepository <Person, Integer>{
	
	Person findById(int personId);
	Person findByGivenName(String GivenName);
	Person findBySurName(String surName);
	Person findByPob(String Pob);
	Person findByDob(Date Dob);
	default List<Person> findPersonByGivenNameAndSurnameAndPobAndDob(String givenName, String surName, String pob, java.sql.Date dob,EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		String personQuery;
		
		query = "SELECT p FROM Person p WHERE p.surName=:surName AND p.givenName=:givenName AND p.dob LIKE CONCAT(:dob,'%') AND p.pob=:pob" ;
		q = em.createQuery(query);
		q.setParameter("surName", surName);
		q.setParameter("givenName", givenName);
		q.setParameter("dob", dob);
		q.setParameter("pob", pob);		
		
		return q.getResultList();
	}
	
	default List<Person> findPersonByGivenNameAndSurnameAndPobAndDobAndGender(String givenName, String surName, String pob, java.sql.Date dob,String gender,EntityManager em) {
		
		String query = "";
		javax.persistence.Query q;
		String personQuery;
		
		query = "SELECT p FROM Person p WHERE p.surName=:surName AND p.givenName=:givenName AND p.gender=:gender AND p.dob LIKE CONCAT(:dob,'%') AND p.pob=:pob" ;
		q = em.createQuery(query);
		q.setParameter("surName", surName);
		q.setParameter("givenName", givenName);
		q.setParameter("dob", dob);
		q.setParameter("pob", pob);		
		q.setParameter("gender", gender);	
		
		return q.getResultList();
	}
	
	
	@Query(value = "SELECT * FROM person " +  
            " WHERE MATCH (given_name, surname, pob) AGAINST (:searchTerm) > 0 and dob LIKE :dob limit 25", 
            nativeQuery = true)
	List<Person>  findCandidatesBornAt(@Param("searchTerm") String searchTerm, @Param("dob") String dob);

	@Query(value = "SELECT * FROM person " +  
            " WHERE MATCH (given_name, surname, pob) AGAINST (:searchTerm) > 0  limit 50", 
            nativeQuery = true)
	List<Person>  findCandidates(@Param("searchTerm") String searchTerm);
	Person findPersonByLicenseNum(String licenseNum);

}