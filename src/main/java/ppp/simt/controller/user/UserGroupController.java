package ppp.simt.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.GroupRole;
import ppp.simt.entity.user.Role;
import ppp.simt.entity.user.User;
import ppp.simt.service.user.GroupRoleService;
import ppp.simt.service.user.GroupService;
import ppp.simt.service.user.RoleService;
import ppp.simt.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class UserGroupController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private GroupRoleService groupRoleService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/marine/user/group/list", method=RequestMethod.GET)
	public String listUserGroup(HttpServletRequest httpServletRequest, Model model){
		
		List<Group> allGroups = groupService.findAll();
		model.addAttribute("groups", allGroups);

		return "user/list_group";
	}

	@RequestMapping(value="/marine/user/form", method=RequestMethod.GET)
	public String newRole(Model model, HttpServletRequest httpServletRequest){
		List<Role> roles = new ArrayList<Role>();
		List<Role> roless = roleService.findAll();
		for(int i=0;i<roless.size();i++){
			Role role = roless.get(i);
			roles.add(role);
		}

		model.addAttribute("roles",roles);
		return "user/create_group";
	}

	@RequestMapping(value="/marine/group/createPost", method=RequestMethod.GET)
	public String saveRole(@RequestParam("groupName") String groupName, @RequestParam("role") String role, HttpServletRequest httpServletRequest, Model model){

		Group group=new Group();
		group.setName(groupName);
		groupService.save(group);
		Group groups = groupService.findById(group.getId());

		String[] s= role.split("/");
		for (int i=0; i<s.length;i++) {
			Role roles = roleService.findById(Integer.parseInt(s[i]));
			GroupRole groupRole=new GroupRole(groups,roles);
			groupRoleService.save(groupRole);
			//System.out.println("moise"+roles);
		}

		List<Group> allGroups = groupService.findAll();
		model.addAttribute("groups", allGroups);
		return "user/list_group";
	}


	@RequestMapping(value="/marine/user/group/delete", method=RequestMethod.GET)
	public String deleteGroup(@RequestParam("id") long id,Model model, HttpServletRequest httpServletRequest){
		//TODO: DELETE GROUP
		//System.out.println(id);

		Group groups = groupService.findById(id);
		User user= userService.findByGroup(groups);
		System.out.println(user);
		user.setGroup(null);
		groupRoleService.delete(groups);
		groupService.delete(groups);


		List<Group> allGroups = groupService.findAll();
		model.addAttribute("groups", allGroups);
		return "user/list_group";
	}
	
	@RequestMapping(value="/marine/user/group/edit/load", method=RequestMethod.GET)
	public String editGroup(@RequestParam("id") long id, Model model, HttpServletRequest httpServletRequest){
		Group group = groupService.findGroupById(id);
		Set<GroupRole> groupRoles = group.getGroupRoles();
		List<Role> roles = new ArrayList<Role>();
		List<Role> roless = roleService.findAll();
		roles.addAll(roless);
		List<Long> groupRolesIds = new ArrayList<Long>(); 
		 for (GroupRole role : groupRoles) {
		        groupRolesIds.add(role.getRoleId().getId());
		     }
		     
		model.addAttribute("group", group);
		model.addAttribute("groupRoleIds", groupRolesIds);
		model.addAttribute("roles", roles);
		return "user/edit_group";
	}
	
	@RequestMapping(value="/marine/user/group/edit/save", method=RequestMethod.GET)
	public String updateGroup(@RequestParam("id") long id,@RequestParam("role") String role, Model model, HttpServletRequest httpServletRequest) {
		
	
		Group group = groupService.findById(id);
		groupRoleService.delete(group);
		String[] s= role.split("/");
		for (int i=0; i<s.length;i++) {
			Role currentRole = roleService.findById(Integer.parseInt(s[i]));
			GroupRole groupRole=new GroupRole(group,currentRole);
	
			groupRoleService.save(groupRole);
			
		}		
		List<Group> allGroups = groupService.findAll();
		model.addAttribute("groups", allGroups);
		return "user/list_group";
	}
	
	@RequestMapping(value="/marine/user/group/view", method=RequestMethod.GET)
	public String viewGroup(@RequestParam("id") long id, Model model, HttpServletRequest httpServletRequest){
		Group group = groupService.findGroupById(id);
		Set<GroupRole> groupRoles = group.getGroupRoles();
		
		List<Role> roles = new ArrayList<Role>();
		List<Role> roless = roleService.findAll();
		roles.addAll(roless);		     
		model.addAttribute("group", group);
		model.addAttribute("groupRoles", groupRoles);
		return "user/view_group";
	}

}
