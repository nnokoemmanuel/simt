package ppp.simt.service.user;

import java.util.List;

import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.GroupRole;
import ppp.simt.entity.user.Role;


public interface GroupRoleService {


	public void save(GroupRole groupRole);
	public List<GroupRole> findGroupRoleByGroup(long id);
	public List<GroupRole> findAll();
	public void delete(Group groupId);
	public List<GroupRole> findRolesByGroup(Group group);
	
}
