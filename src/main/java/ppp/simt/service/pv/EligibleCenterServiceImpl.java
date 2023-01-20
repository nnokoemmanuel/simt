package ppp.simt.service.pv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.EligibleCenterStatus;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.ExamSession;
import ppp.simt.entity.user.User;
import ppp.simt.repository.pv.EligibleCenterRepository;
import ppp.simt.service.user.UserService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Service
public class  EligibleCenterServiceImpl implements EligibleCenterService {

	@Autowired
	private EligibleCenterRepository eligibleCenterRepository;
	@Autowired
	EntityManager em;

	@Autowired
	private ExamSessionService examSessionService;
	
	@Autowired
	private EligibleCenterStatusService eligibleCenterStatusService;
	
	@Autowired
	private ExamCenterService examCenterService;
	
	@Autowired
	private Tracker tracker;
	
	@Autowired 
	private Logger_ logger_;
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public List<EligibleCenter> findAll() {
		return eligibleCenterRepository.findAll();
	}

	
	
	@Override
	public void createEligibleCenter(EligibleCenter eligibleCenter) {
		eligibleCenterRepository.save(eligibleCenter);
		
	}

	@Override
	public void updateEligibleCenter(EligibleCenter eligibleCenter) {
		eligibleCenterRepository.saveAndFlush(eligibleCenter);
		
	}
	
	@Override
	public EligibleCenter findEligibleCenterById(int eligibleCenterId) {
		
		return eligibleCenterRepository.findById(eligibleCenterId);
	}
	
	@Override
	public void managePvReset(EligibleCenter eligibleCenter,User user){
		
			if( eligibleCenter!= null ){
				eligibleCenter.setEligibleCenterStatus(eligibleCenterStatusService.findById(2));
				updateEligibleCenter(eligibleCenter);
			}
	}
	
	@Override
	public void managePvClose(EligibleCenter eligibleCenter,User user){
		
			if( eligibleCenter!= null ){
				eligibleCenter.setEligibleCenterStatus(eligibleCenterStatusService.findById(3));
				updateEligibleCenter(eligibleCenter);
			}
	}
	
	@Override
	public void managePvValidate(EligibleCenter eligibleCenter,User user){
		
			if( eligibleCenter!= null ){
				eligibleCenter.setEligibleCenterStatus(eligibleCenterStatusService.findById(4));

				updateEligibleCenter(eligibleCenter);
			}
	}
	
	@Override
	public List<EligibleCenter> findByRegionAndSessionDate(int regionId, int sessionId) {
		
		return eligibleCenterRepository.findByRegionAndSessionDate(regionId, sessionId,em);
	}



	@Override
	public List<EligibleCenter> findByExamSessionAndExamCenter(ExamSession session, ExamCenter center) {
		// TODO Auto-generated method stub
		return eligibleCenterRepository.findByExamSessionAndExamCenter(session, center);
	}



	@Override
	public void save(EligibleCenter center) {
		eligibleCenterRepository.save(center);
		
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
        	ExamSession existingSession=examSessionService.findOneBySessionDate(sessionDate);
        	if(existingSession==null)
        	{	
        		existingSession=new ExamSession();
        		existingSession.setStatus("REGISTER");
        		existingSession.setSessionDate(sessionDate);
        		existingSession.setCreatedOn(new java.util.Date());
        		examSessionService.save(existingSession);

        	}
        	for(int i=0;i<listCenters.length;i++) {
        		ExamCenter center=examCenterService.findById(Integer.valueOf(listCenters[i]));
        		List<EligibleCenter> eligibecenter=eligibleCenterRepository.findByExamSessionAndExamCenter(existingSession, center);
        		if(eligibecenter==null||eligibecenter.size()==0) {
        			EligibleCenter el= new EligibleCenter();
        			el.setExamCenter(center);
        			el.setExamSession(existingSession);
        			el.setEligibleCenterStatus(eligibleCenterStatusService.findById(1));
        			eligibleCenterRepository.save(el);
        			
        			tracker.track(el, "PV WAS CREATED BY USER: " +  user.getFirstName()+" "+user.getLastName(), request);
        			logger_.log(Constants.NORMAL_LOG_DIR, "Resetted the pv: "+user);
        		}
        	
        	}
        }
		
		return response;
	}

	public EligibleCenter findById(int eligibleCenterId) {

		return eligibleCenterRepository.findById(eligibleCenterId);
	}
	
	@Override
	public void managePvOpen(EligibleCenter eligibleCenter,User user){
		
			if( eligibleCenter!= null ){
				eligibleCenter.setEligibleCenterStatus(eligibleCenterStatusService.findById(2));
				System.out.println(eligibleCenterStatusService.findById(2)+"MMMMMMMMMMMMMMMMMMMMMMM");
				updateEligibleCenter(eligibleCenter);
			}
	}



	@Override
	public void generatePV(EligibleCenter eligibleCenter, User user) {
		if( eligibleCenter!= null ){
			eligibleCenter.setEligibleCenterStatus(eligibleCenterStatusService.findById(5));
			updateEligibleCenter(eligibleCenter);
		}
		
	}



	@Override
	public void resetGeneratedPV(EligibleCenter eligibleCenter, User user) {
		// TODO Auto-generated method stub
		if( eligibleCenter!= null ){
			eligibleCenter.setEligibleCenterStatus(eligibleCenterStatusService.findById(3));
			updateEligibleCenter(eligibleCenter);
		}
	}
	
	@Override
	public List<EligibleCenter> findByEligibleCenterStatus(EligibleCenterStatus eligibleCenterStatus) {
		return eligibleCenterRepository.findByEligibleCenterStatus( eligibleCenterStatus);
	}



	@Override
	public List<EligibleCenter> findByExamSession(ExamSession examSession) {
		// TODO Auto-generated method stub
		return eligibleCenterRepository.findByExamSession(examSession);
	}


	
	
}
