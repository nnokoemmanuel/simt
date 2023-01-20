package ppp.simt.service.user;

import java.util.List;

import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.GroupRole;


public interface GroupService {
	
	public List<Group> findAll();
	public Group findById(long id);
	public Group findGroupById(Long id);

	public void save(Group group);
	void delete(Group group);
	public Group findByName(String name);
}