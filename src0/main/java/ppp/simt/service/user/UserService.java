package ppp.simt.service.user;

import javax.servlet.http.HttpServletRequest;

import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.Role;
import ppp.simt.entity.user.User;

import java.util.ArrayList;
import java.util.List;


public interface UserService {
	
    public User getLogedUser(HttpServletRequest httpServletRequest);
	public User findUserByEmail(String email);
	public User findUserByUsername(String userName);
	public void saveUser(User user);
	public User findUserById(int id);
	//public void update(User user);
	public ArrayList <User> findUserByActive(int id);
    public  String encryptPassword(String password);
    public  boolean matche(String password,String encrypted);
	public List<User> findAll();
	public void update(User user);
	public User findByGroup(Group group);
	public void sentEmail(String subject, String message, String email, String serverName, String serverPassword);
    public void delete(Long roleId,int userId);
	public List<Long> findUserRolesByUserId(int userId);
	public User findByToken(String token);	
}