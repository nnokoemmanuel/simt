package ppp.simt.service.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.production.InSlipStatus;
import ppp.simt.repository.production.InSlipStatusRepository;

@Service
public class InSlipStatusServiceImpl implements InSlipStatusService{
	
	@Autowired
	private InSlipStatusRepository inSlipStatusRepository;

	@Override
	public InSlipStatus findById(int id) {
		
		return inSlipStatusRepository.findById(id);
	}

}
