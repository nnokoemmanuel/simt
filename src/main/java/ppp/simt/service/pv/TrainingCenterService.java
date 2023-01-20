package ppp.simt.service.pv;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import ppp.simt.entity.core.Division;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.form.TrainingCenterForm;





public interface TrainingCenterService {
	public TrainingCenter findById(int trainingCenterId);
	public List<TrainingCenter> findAll();
	public void createTrainingCenter(TrainingCenter trainingCenter);
	public void updateTrainingCenter(TrainingCenter trainingCenter);
	TrainingCenter persistTrainingCenterBean(TrainingCenterForm trainingCenterForm, HttpServletRequest httpServletRequest) throws ParseException;
	public void deleteTrainingCenter(TrainingCenter trainingCenter);
	public List<TrainingCenter>  findByDivision(Division division);
	public List<TrainingCenter> findTrainingCenterByEligibleSpeciality(Speciality speciality); 
	public List<TrainingCenter>  findBySpecialityAndDivisionAndStatus(Speciality speciality,Division division,int status);
	public List<TrainingCenter> findBySpecialityAndStatus(Speciality speciality,int status);
	public List<TrainingCenter> findBySpecialityAndDivision(Speciality speciality,Division division);
	public List<TrainingCenter> findByStatus(int status);
	public List<TrainingCenter>  findByDivisionAndStatus(Division division , int status);
	public void save(TrainingCenter center);


}
