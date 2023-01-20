package ppp.simt.service.user;

import java.util.List;

import ppp.simt.entity.tracking.UserTracking;


public interface UserTrackingService {

	public void save(UserTracking user);
    public List<UserTracking> findByUserId(int id);
}
