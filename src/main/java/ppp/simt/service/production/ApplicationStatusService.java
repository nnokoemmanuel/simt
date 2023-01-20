package ppp.simt.service.production;

import java.util.List;

import ppp.simt.entity.production.ApplicationStatus;


public interface ApplicationStatusService {

	public List<ApplicationStatus> findAll();
	public ApplicationStatus findApplicationStatusById(int applicationStatusId);
}

