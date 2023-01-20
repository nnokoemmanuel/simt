package ppp.simt.service.applicant;


import javax.persistence.EntityManager;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.applicant.EntranceEligibleCenterStatus;

import ppp.simt.repository.applicant.EntranceEligibleCenterStatusRepository;



@Service
public class EntranceEligibleCenterStatusServiceImpl implements EntranceEligibleCenterStatusService {

	@Autowired
	private EntranceEligibleCenterStatusRepository entranceEligibleCenterStatusRepository;
	@Autowired
	EntityManager em;

	

	
	@Autowired
	private EntranceExamSessionService entranceExamSessionService;
	
	@Autowired
	private EntranceExamCenterService entranceExamCenterService;

	
	@Override
	public EntranceEligibleCenterStatus findEntranceEligibleCenterStatusById(int entranceEligibleCenterStatusId) {
		
		return entranceEligibleCenterStatusRepository.findById(entranceEligibleCenterStatusId);
	}

	
    @Override
    public EntranceEligibleCenterStatus findById(int entranceEligibleCenterStatusId) {
        // TODO Auto-generated method stub
        return entranceEligibleCenterStatusRepository.findById(entranceEligibleCenterStatusId);
    }
}

