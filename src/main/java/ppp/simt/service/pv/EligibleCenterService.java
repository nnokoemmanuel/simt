package ppp.simt.service.pv;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.EligibleCenterStatus;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.ExamSession;
import ppp.simt.entity.user.User;


public interface EligibleCenterService {
	
	public List<EligibleCenter> findAll();
	public void createEligibleCenter(EligibleCenter eligibleCenter);
	public void updateEligibleCenter(EligibleCenter eligibleCenter);
	
	public EligibleCenter findEligibleCenterById(int eligibleCenterId);	
	public void managePvReset(EligibleCenter eligibleCenter,User user);
	public void managePvClose(EligibleCenter eligibleCenter,User user);
	public void managePvValidate(EligibleCenter eligibleCenter,User user);
	public List<EligibleCenter> findByRegionAndSessionDate(int regionId,int sessionId);
	public List<EligibleCenter> findByExamSessionAndExamCenter(ExamSession session, ExamCenter center);
    public void save(EligibleCenter center);
    //public String saveAllCenter(String centers, String session) throws ParseException;
	public EligibleCenter findById(int eligibleCenterId);
	public void managePvOpen(EligibleCenter eligibleCenter,User user);
	public void generatePV(EligibleCenter eligibleCenter, User user);
	public void resetGeneratedPV(EligibleCenter eligibleCenter, User user);
	public List<EligibleCenter> findByEligibleCenterStatus(EligibleCenterStatus eligibleCenterStatus);
	
	public String saveAllCenter(String centers, String session, HttpServletRequest request) throws ParseException;
	public List<EligibleCenter> findByExamSession(ExamSession examSession);
}
