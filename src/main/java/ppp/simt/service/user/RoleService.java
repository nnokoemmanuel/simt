package ppp.simt.service.user;

import java.util.List;

import ppp.simt.entity.user.Role;

public interface RoleService {
    public List<Role> findAll();
    public Role findByName(String name);
    public Role findById(long id);
    //public void delete(Role roles);
}