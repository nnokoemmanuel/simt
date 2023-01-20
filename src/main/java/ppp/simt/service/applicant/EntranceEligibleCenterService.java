package ppp.simt.service.applicant;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ppp.simt.entity.applicant.EntranceEligibleCenter;
import ppp.simt.entity.applicant.EntranceEligibleCenterStatus;
import ppp.simt.entity.applicant.EntranceExamCenter;
import ppp.simt.entity.applicant.EntranceExamSession;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.EligibleCenterStatus;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.ExamSession;
import ppp.simt.entity.user.User;


public interface EntranceEligibleCenterService {
	
	public List<EntranceEligibleCenter> findAll();
	public void createEntranceEligibleCenter(EntranceEligibleCenter entranceEligibleCenter);
	public void updateEntranceEligibleCenter(EntranceEligibleCenter entranceEligibleCenter);
	
	public EntranceEligibleCenter findEntranceEligibleCenterById(int entranceEligibleCenterId);	
	public void managePvReset(EntranceEligibleCenter entranceEligibleCenter,User user);
	public void managePvClose(EntranceEligibleCenter entranceEligibleCenter,User user);
	public void managePvValidate(EntranceEligibleCenter entranceEligibleCenter,User user);
	public List<EntranceEligibleCenter> findByRegionAndSessionDate(int regionId,int sessionId);
	public List<EntranceEligibleCenter> findByEntanceExamSessionAndEntranceExamCenter(EntranceExamSession session, EntranceExamCenter center);
    public void save(EntranceEligibleCenter center);

	public EntranceEligibleCenter findById(int entranceEligibleCenter);
	public void managePvOpen(EntranceEligibleCenter entranceEligibleCenter,User user);
	public void generatePV(EntranceEligibleCenter entranceEligibleCenter, User user);
	public void resetGeneratedPV(EntranceEligibleCenter entranceEligibleCenter, User user);
	public List<EntranceEligibleCenter> findByEntranceEligibleCenterStatus(EntranceEligibleCenterStatus entranceEligibleCenterStatus);
	
	public String saveAllCenter(String centers, String session, HttpServletRequest request) throws ParseException;
	public List<EntranceEligibleCenter> findByEntranceExamSession(EntranceExamSession examSession);
}
