package ppp.simt.service.applicant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.applicant.EntranceEligibleCenterStatus;
import ppp.simt.entity.applicant.EntranceExamCenter;
import ppp.simt.entity.applicant.EntranceExamSession;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.EligibleCenterStatus;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.ExamSession;
import ppp.simt.entity.user.User;
import ppp.simt.repository.applicant.EntranceEligibleCenterRepository;
import ppp.simt.repository.pv.EligibleCenterRepository;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Service
public class  EntranceEligibleCenterServiceImpl implements EntranceEligibleCenterService {

	@Autowired
	private EntranceEligibleCenterRepository entranceEligibleCenterRepository;
	@Autowired
	EntityManager em;

	@Autowired
	private EntranceExamSessionService examSessionService;
	
	@Autowired
	private EntranceEligibleCenterStatusService entranceEligibleCenterStatusService;
	
	@Autowired
	private EntranceExamCenterService examCenterService;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public List<EntranceEligibleCenter> findAll() {
		return entranceEligibleCenterRepository.findAll();
	}

	
	
	@Override
	public void createEntranceEligibleCenter(EntranceEligibleCenter entranceEligibleCenter) {
		entranceEligibleCenterRepository.save(entranceEligibleCenter);
		
	}

	@Override
	public void updateEntranceEligibleCenter(EntranceEligibleCenter entranceEligibleCenter) {
		entranceEligibleCenterRepository.saveAndFlush(entranceEligibleCenter);
		
	}
	
	@Override
	public EntranceEligibleCenter findEntranceEligibleCenterById(int entranceEligibleCenterId) {
		
		return entranceEligibleCenterRepository.findById(entranceEligibleCenterId);
	}
	
	@Override
	public void managePvReset(EntranceEligibleCenter entranceEligibleCenter,User user){
		
			if( entranceEligibleCenter!= null ){
				entranceEligibleCenter.setEntranceEligibleCenterStatus(entranceEligibleCenterStatusService.findById(2));
				updateEntranceEligibleCenter(entranceEligibleCenter);
			}
	}
	
	@Override
	public void managePvClose(EntranceEligibleCenter entranceEligibleCenter,User user){
		
			if( entranceEligibleCenter!= null ){
				entranceEligibleCenter.setEntranceEligibleCenterStatus(entranceEligibleCenterStatusService.findById(3));
				updateEntranceEligibleCenter(entranceEligibleCenter);
			}
	}
	
	@Override
	public void managePvValidate(EntranceEligibleCenter entranceEligibleCenter,User user){
		
			if( entranceEligibleCenter!= null ){
				entranceEligibleCenter.setEntranceEligibleCenterStatus(entranceEligibleCenterStatusService.findById(4));
				updateEntranceEligibleCenter(entranceEligibleCenter);
			}
	}
	
	@Override
	public List<EntranceEligibleCenter> findByRegionAndSessionDate(int regionId, int sessionId) {
		
		return entranceEligibleCenterRepository.findByRegionAndSessionDate(regionId, sessionId,em);
	}



	@Override
	public List<EntranceEligibleCenter> findByEntanceExamSessionAndEntranceExamCenter(EntranceExamSession session, EntranceExamCenter center) {
		// TODO Auto-generated method stub
		return entranceEligibleCenterRepository.findByEntranceExamSessionAndEntranceExamCenter(session, center);
	}



	@Override
	public void save(EntranceEligibleCenter center) {
		entranceEligibleCenterRepository.save(center);
		
	}



	@Override
	@Transactional
	//public String saveAllCenter(String centers, String session) throws ParseException {
	public String saveAllCenter(String centers, String session,HttpServletRequest request) throws ParseException {
		User user = userService.getLogedUser(request);
		String response  ="ok";
		java.util.Date sessionDate=new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(session);
		java.sql.Date sqlSessionDate = new java.sql.Date(sessionDate.getTime());
        String[] listCenters=centers.split(",");
        if(listCenters.length==0) response="ko";
        else {
        	EntranceExamSession existingSession=examSessionService.findOneBySessionDate(sessionDate);
        		
        	if(existingSession==null)
        	{	
        		existingSession=new EntranceExamSession();
        		existingSession.setSessionDate(sessionDate);
        		existingSession.setCreatedOn(new java.util.Date());
        		examSessionService.save(existingSession);

        	}
        	for(int i=0;i<listCenters.length;i++) {
        		EntranceExamCenter center=examCenterService.findById(Integer.valueOf(listCenters[i]));
        		List<EntranceEligibleCenter> eligibecenter=entranceEligibleCenterRepository.findByEntranceExamSessionAndEntranceExamCenter(existingSession, center);
        		if(eligibecenter==null||eligibecenter.size()==0) {
        			EntranceEligibleCenter el= new EntranceEligibleCenter();
        			el.setEntranceExamCenter(center);
        			el.setEntranceExamSession(existingSession);
        			el.setEntranceEligibleCenterStatus(entranceEligibleCenterStatusService.findById(1));
        			entranceEligibleCenterRepository.save(el);
        			
        			tracker.track(el, "PV WAS CREATED BY USER: " +  user.getFirstName()+" "+user.getLastName(), request);
        			logger_.log(Constants.NORMAL_LOG_DIR, "Resetted the pv: "+user);
        		}
        	
        	}
        }
		
		return response;
	}

	public EntranceEligibleCenter findById(int entranceEligibleCenterId) {

		return entranceEligibleCenterRepository.findById(entranceEligibleCenterId);
	}
	
	@Override
	public void managePvOpen(EntranceEligibleCenter entranceEligibleCenter,User user){
		
			if( entranceEligibleCenter!= null ){
				entranceEligibleCenter.setEntranceEligibleCenterStatus(entranceEligibleCenterStatusService.findById(2));
				System.out.println(entranceEligibleCenterStatusService.findById(2)+"MMMMMMMMMMMMMMMMMMMMMMM");
				updateEntranceEligibleCenter(entranceEligibleCenter);
			}
	}



	@Override
	public void generatePV(EntranceEligibleCenter entranceEligibleCenter, User user) {
		if( entranceEligibleCenter!= null ){
			entranceEligibleCenter.setEntranceEligibleCenterStatus(entranceEligibleCenterStatusService.findById(5));
			updateEntranceEligibleCenter(entranceEligibleCenter);
		}
		
	}



	@Override
	public void resetGeneratedPV(EntranceEligibleCenter entranceEligibleCenter, User user) {
		// TODO Auto-generated method stub
		if( entranceEligibleCenter!= null ){
			entranceEligibleCenter.setEntranceEligibleCenterStatus(entranceEligibleCenterStatusService.findById(3));
			updateEntranceEligibleCenter(entranceEligibleCenter);
		}
	}
	
	@Override
	public List<EntranceEligibleCenter> findByEntranceEligibleCenterStatus(EntranceEligibleCenterStatus entranceEligibleCenterStatus) {
		return entranceEligibleCenterRepository.findByEntranceEligibleCenterStatus( entranceEligibleCenterStatus);
	}



	@Override
	public List<EntranceEligibleCenter> findByEntranceExamSession(EntranceExamSession examSession) {
		// TODO Auto-generated method stub
		return entranceEligibleCenterRepository.findByEntranceExamSession(examSession);
	}


	
	
}
