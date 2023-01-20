package ppp.simt.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.user.Group;
import ppp.simt.repository.user.GroupRepository;

import java.util.List;



@Service
public class GroupServiceImpl implements GroupService {
	
	@Autowired
	private GroupRepository groupRepository;


	@Override
	public List<Group> findAll() {
		return groupRepository.findAll();
	}

	@Override
	public Group findGroupById(Long id) {
		return groupRepository.findById(id);
	}


	@Override
	public Group findById(long id) {
		return groupRepository.findById(id);
	}

	@Override
	public void save(Group group) {
		groupRepository.save(group);

	}

	@Override
	public void delete(Group group) {
		groupRepository.delete(group);

	}

	@Override
	public Group findByName(String name) {
		// TODO Auto-generated method stub
		return groupRepository.findByName(name);
	}

}