package ppp.simt.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ppp.simt.entity.user.Group;



@Repository("groupRepository")
public interface GroupRepository extends JpaRepository <Group, Integer>{
	
	Group findById(long id);
	void delete(Group group);
	public Group findByName(String name) ;

}