
package ppp.simt.service.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.tracking.UserTracking;
import ppp.simt.repository.user.UserTrackingRepository;



@Service("userTrackingService")
public  class UserTrackingServiceImpl implements UserTrackingService  {
    
	@Autowired
    private UserTrackingRepository userTrackingRepository;
	
    @Override
	public void save(UserTracking user) {
		userTrackingRepository.save(user);	
	}

	@Override
	public  ArrayList<UserTracking> findByUserId(int id) {
		// TODO Auto-generated method stub
		return (ArrayList<UserTracking>) userTrackingRepository.findByUserId(id);
	}
    
 
}
