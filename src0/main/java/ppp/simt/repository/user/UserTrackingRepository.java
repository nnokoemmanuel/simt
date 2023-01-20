
package ppp.simt.repository.user;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ppp.simt.entity.tracking.UserTracking;



public interface UserTrackingRepository extends JpaRepository<UserTracking,Integer> {
    public List<UserTracking> findByUserId(int id);
    public List<UserTracking> findAll();
   
} 