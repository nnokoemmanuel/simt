package ppp.simt.service.pv;


import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ppp.simt.repository.evaluation.CourseRepository;
import ppp.simt.repository.pv.TrainingCenterRepository;
import ppp.simt.service.core.CoreService;
import ppp.simt.service.core.DivisionService;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;
import ppp.simt.entity.core.Division;
import ppp.simt.entity.core.Speciality;
import ppp.simt.entity.evaluation.Course;
import ppp.simt.entity.evaluation.Module;
import ppp.simt.entity.pv.Agreement;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.user.User;
import ppp.simt.form.TrainingCenterForm;
import ppp.simt.form.evaluation.CourseForm;


@Service("trainingCenterService")
public class TrainingCenterServiceImpl implements TrainingCenterService{

	@Autowired
	private TrainingCenterRepository trainingCenterRepository;
	
	@Autowired
	private DivisionService divisionService;
	
	@Autowired
	private ExamCenterService examCenterService;
	
	@Autowired
	private CoreService coreService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AgreementService agreementService;
	
	@Autowired
	private TrainingCenterService trainingCenterService;
	
	@Autowired
	EntityManager em;
	
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private Tracker tracker;



	@Override
	public List<TrainingCenter> findAll() {
		return trainingCenterRepository.findAll();
	}
	
	@Override
	public void createTrainingCenter(TrainingCenter trainingCenter) {
		trainingCenterRepository.save(trainingCenter);
		
	}
	
	@Override
	public void updateTrainingCenter(TrainingCenter trainingCenter) {
		trainingCenterRepository.saveAndFlush(trainingCenter);

	}


	@Override
	public TrainingCenter findById(int trainingCenterId) {
		return trainingCenterRepository.findById(trainingCenterId);
	}


	/**
	 * Convertie un formulaire en bean
	 * @return
	 */
	@Override
	public TrainingCenter persistTrainingCenterBean(TrainingCenterForm trainingCenterForm,HttpServletRequest httpServletRequest) throws ParseException{
		
		User user = userService.getLogedUser(httpServletRequest);
		TrainingCenter trainingCenter = null;
		Division division =null;

		
		if(trainingCenterForm.getDivisionId() != -1)
		   division = divisionService.findById(trainingCenterForm.getDivisionId());	
		
		if(trainingCenterForm.getId() ==0  ){

			if(division != null){
			
				Date now = new Date();
				trainingCenter = new TrainingCenter( trainingCenterForm.getName(),  now, trainingCenterForm.getFounder(), trainingCenterForm.getOwner(),
						trainingCenterForm.getLocation(), division,null , 1,trainingCenterForm.getOwnerContact());
				trainingCenterService.createTrainingCenter(trainingCenter);
				tracker.track(trainingCenter, "REGISTERED THE TRAINING CENTER" , httpServletRequest);
				logger_.log(Constants.NORMAL_LOG_DIR, "Initiates the trainingcenter Registration: "+user);
				//register training center elligible specialities
				
				//create training center agreement
				Agreement agreement = new Agreement( trainingCenterForm.getAgreementAuthorisationNumber(),  trainingCenter, coreService.convertSqlToUtil(coreService.stringToDate(trainingCenterForm.getAgreementIssuedDate())),null, 1);
				agreementService.createAgreement(agreement);
				tracker.track(agreement, "REGISTERED THE AGREEMENT" , httpServletRequest);
				logger_.log(Constants.NORMAL_LOG_DIR, "Initiates the agreement Registration: "+user);	
			}
			
			return trainingCenter;
			
		}else{
			TrainingCenter trainingCenterToEdit = trainingCenterService.findById(trainingCenterForm.getId());
			
			trainingCenterToEdit.setDivision(division);
			trainingCenterToEdit.setFounder(trainingCenterForm.getFounder());
			trainingCenterToEdit.setLocation(trainingCenterForm.getLocation());
			if(trainingCenterToEdit.getMaxStudent() != trainingCenterForm.getMaxStudent()){
				//track the edition of trainning center max students by the user
				tracker.track(trainingCenterToEdit, "CHANGED THE TRAINING CENTER MAXIMUM CAPACITY VALUE FROM "+trainingCenterToEdit.getMaxStudent()+" STUDENTS TO "+trainingCenterForm.getMaxStudent()+" STUDENTS" , httpServletRequest);
	    		logger_.log(Constants.NORMAL_LOG_DIR, "Initiates the training center max students value Edition: "+user);
			}
			trainingCenterToEdit.setMaxStudent(trainingCenterForm.getMaxStudent());
			trainingCenterToEdit.setName(trainingCenterForm.getName());
			trainingCenterToEdit.setOwner(trainingCenterForm.getOwner());
			trainingCenterToEdit.setOwnerContact(trainingCenterForm.getOwnerContact());
			trainingCenterService.updateTrainingCenter(trainingCenterToEdit);
			
			//This part updates only the first agreement at index 0
			Agreement agreement = trainingCenterToEdit.getAgreements().get(0);
			agreement.setAuthorisationNumber(trainingCenterForm.getAgreementAuthorisationNumber());
			agreement.setIssuedDate(coreService.convertSqlToUtil(coreService.stringToDate(trainingCenterForm.getAgreementIssuedDate())));
			agreementService.updateAgreement(agreement);
			tracker.track(trainingCenterToEdit, "EDITED THE TRAINING CENTER " , httpServletRequest);
    		logger_.log(Constants.NORMAL_LOG_DIR, "Initiates the training center Edition: "+user);
			return trainingCenterToEdit;
		}
	}
	
	
	@Override
	public void deleteTrainingCenter(TrainingCenter trainingCenter) {
		trainingCenterRepository.delete(trainingCenter);
	}
	


	@Override
	public List<TrainingCenter>  findBySpecialityAndDivisionAndStatus(Speciality speciality,Division division,int status) {
		return trainingCenterRepository.findBySpecialityAndDivisionAndStatus( speciality, division, status,  em) ;
	}
	
	@Override
	public List<TrainingCenter> findBySpecialityAndStatus(Speciality speciality,int status){
		return trainingCenterRepository.findBySpecialityAndStatus( speciality, status, em);
		
	}
	
	
	

	@Override
	public List<TrainingCenter> findTrainingCenterByEligibleSpeciality(Speciality speciality) {
		return trainingCenterRepository.findTrainingCenterByEligibleSpeciality(speciality, em);
	}

	@Override
	public void save(TrainingCenter center) {
		 trainingCenterRepository.save(center);
		
	}
	
	@Override
	public List<TrainingCenter> findBySpecialityAndDivision(Speciality speciality,Division division) {
		return trainingCenterRepository.findBySpecialityAndDivision( speciality, division,  em) ;
	}
	

	

	@Override
	public List<TrainingCenter> findByDivision(Division division) {
		// TODO Auto-generated method stub
		return trainingCenterRepository.findByDivision( division,em);
	}
	
	
	
	
	
	@Override
	public List<TrainingCenter> findByStatus(int status){
		return trainingCenterRepository.findByStatus(status);
	}
		
	
	@Override
	public List<TrainingCenter>  findByDivisionAndStatus(Division division , int status){
		return trainingCenterRepository.findByDivisionAndStatus( division ,  status);
	}
	


}



