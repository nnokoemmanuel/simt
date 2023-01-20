package ppp.simt.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.user.Role;
import ppp.simt.repository.user.RoleRepository;



@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}
	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}
	@Override
	public Role findById(long id) {
		// TODO Auto-generated method stub
		return roleRepository.findById(id);
	}
	/*@Override
	public void delete(Role roles) {
		roleRepository.deleteRole(roles);
		
	}*/

}