package ppp.simt.service.production;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.production.ApplicationStatus;
import ppp.simt.repository.production.ApplicationStatusRepository;

import java.util.List;


@Service
public class ApplicationStatusServiceImpl implements ApplicationStatusService {
	
	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;


	@Override
	public List<ApplicationStatus> findAll() {
		return applicationStatusRepository.findAll();
	}


	@Override
	public ApplicationStatus findApplicationStatusById(int applicationStatusId) {

		return applicationStatusRepository.findById(applicationStatusId);
	}


}

