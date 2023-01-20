package ppp.simt.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ppp.simt.entity.pv.StudentSession;
import ppp.simt.entity.pv.EligibleCenter;
import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.User;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;


@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	 User findByEmail(String email);
	 
	 ArrayList<User> findAll();
	 User findByUsername(String userName);
	 User findById(int id);
	 ArrayList <User> findByActive(int active);
	 User findByGroup(Group group);
	 
	 @Transactional
	 @Modifying
	 @Query(value = "delete from  user_role  where user_role.user_id = :userId and user_role.role_id  = :roleId",
	    		nativeQuery = true)
	 void delete(@Param("roleId") Long roleId,@Param("userId") int userId );

	 
	 @Query(value = "SELECT user_role.role_id FROM user_role  where user_role.user_id = :userId ",
	    		nativeQuery = true) 
      List<Long> findUserRolesByUserId(@Param("userId") int userId);
	 
	 User findByToken(String token);
	
}