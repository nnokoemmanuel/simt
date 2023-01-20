package ppp.simt.service.pv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.pv.EligibleCenterStatus;
import ppp.simt.entity.pv.ExamCenter;
import ppp.simt.entity.pv.ExamSession;
import ppp.simt.entity.user.User;
import ppp.simt.repository.pv.EligibleCenterRepository;
import ppp.simt.repository.pv.EligibleCenterStatusRepository;


@Service
public class EligibleCenterStatusServiceImpl implements EligibleCenterStatusService {

	@Autowired
	private EligibleCenterStatusRepository eligibleCenterStatusRepository;
	@Autowired
	EntityManager em;

	

	
	@Autowired
	private ExamSessionService examSessionService;
	
	@Autowired
	private ExamCenterService examCenterService;

	
	@Override
	public EligibleCenterStatus findEligibleCenterStatusById(int eligibleCenterStatusId) {
		
		return eligibleCenterStatusRepository.findById(eligibleCenterStatusId);
	}

	
    @Override
    public EligibleCenterStatus findById(int eligibleCenterStatusId) {
        // TODO Auto-generated method stub
        return eligibleCenterStatusRepository.findById(eligibleCenterStatusId);
    }
}

