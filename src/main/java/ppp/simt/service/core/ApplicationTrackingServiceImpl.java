package ppp.simt.service.core;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.ApplicationTracking;
import ppp.simt.repository.core.ApplicationTrackingRepository;

@Service("ApplicationTrackingService")
public class ApplicationTrackingServiceImpl implements ApplicationTrackingService{

	@Autowired
    private ApplicationTrackingRepository applicationTrackingRepository;
	
	@Override
	public void save(ApplicationTracking application) {
		// TODO Auto-generated method stub
		applicationTrackingRepository.save(application); 
	}

	@Override
	public ArrayList<ApplicationTracking> findByApplicationId(int id) {
		// TODO Auto-generated method stub
		return (ArrayList<ApplicationTracking>) applicationTrackingRepository.findByApplicationId(id);
	}

}
