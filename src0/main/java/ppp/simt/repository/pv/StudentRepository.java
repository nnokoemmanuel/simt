package ppp.simt.repository.pv;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.Student;


@Repository("studentRepository")
public interface StudentRepository extends JpaRepository <Student, Integer>{
	public Student findById(int id);
	public StudentSession save(StudentSession studentSession);
	List<Student> findByComputerizationDateBetweenAndTrainingCenter(java.util.Date startDate, java.util.Date enDate, TrainingCenter trainingCenter);
	public List<Student> findByPerson(Person person);
	public Student findByReferenceNum(String matricule);
	public List<Student> findByTrainingCenter(TrainingCenter trainingCenter);
    default List<Student> findByTrainingCenterAndSpeciality(TrainingCenter trainingCenter,Speciality speciality,EntityManager em){
		String query = "";
		javax.persistence.Query q;
		query = "SELECT s FROM Student s WHERE s.trainingCenter=:trainingCenter AND s.speciality=:speciality";
		q = em.createQuery(query);
		q.setParameter("trainingCenter", trainingCenter);
		q.setParameter("speciality", speciality);
		
		
		return q.getResultList();
	}
    public List<Student> findByComputerizationDateBetween(Date startDate, Date enDate);
	public List<Student> findBySpeciality(long speciality);
}