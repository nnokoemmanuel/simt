package ppp.simt.service.pv;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import ppp.simt.entity.core.Person;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.Student;
import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.form.StudentForm;

public interface StudentService {
	
	public List<Student> findAll();
	public Student persistStudent(StudentForm studentForm, HttpServletRequest httpServletRequest, Person person);
	public void createStudent(Student student);
	public String generateMatricule (int personId,String speciality);
	public String generateReferenceNum(int studentId,TrainingCenter trainingCenter);
	public void updateStudent(Student student);
    public List<Student> findByComputerizationDateBetweenAndTrainingCenter(java.util.Date startDate, java.util.Date enDate, TrainingCenter trainingCenter);
	public List<Student> findByPerson(Person person);
	public Student findByReferenceNum(String matricule);
	public Student findById(int student);
	public List<Student> findByTrainingCenter(TrainingCenter trainingCenter);
	public List<Student> findByTrainingCenterAndSpeciality(TrainingCenter trainingCenter,Speciality speciality);
    public List<Student> findByComputerizationDateBetween(java.util.Date startDate, java.util.Date enDate);
	public List<Student> findBySpeciality(long speciality);

}
