package ppp.simt.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.GroupRole;
import ppp.simt.entity.user.Role;
import ppp.simt.repository.user.GroupRoleRepository;

import java.util.List;


@Service
public class GroupRoleServiceImpl implements GroupRoleService {


	@Autowired
	private GroupRoleRepository groupRoleRepository;

	@Override
	public void save(GroupRole groupRole) {
		 groupRoleRepository.save(groupRole);
	}

	public List<GroupRole> findGroupRoleByGroup(long id){

		return groupRoleRepository.findByGroupId(id);

	}

	@Override
	public void delete(Group groupId) {
		groupRoleRepository.deleteGroupRole(groupId);

	}

	@Override
	public List<GroupRole> findAll() {
		return groupRoleRepository.findAll();
	}

	@Override
	public List<GroupRole> findRolesByGroup(Group group) {
		
		return groupRoleRepository.findRolesByGroupId(group);
	}

}

