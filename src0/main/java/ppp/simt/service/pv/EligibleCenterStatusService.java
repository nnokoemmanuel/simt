package ppp.simt.service.pv;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.EligibleCenterStatus;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.ExamSession;
import ppp.simt.entity.user.User;


public interface EligibleCenterStatusService {
	
	public EligibleCenterStatus findEligibleCenterStatusById(int eligibleCenterStatusId);	

    public EligibleCenterStatus findById(int eligibleCenterStatusId);

}
