package ppp.simt.controller.user;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ppp.simt.entity.user.GroupRole;
import ppp.simt.entity.user.Role;
import ppp.simt.entity.user.User;
import ppp.simt.service.user.UserService;

@Controller
public class UserProfileController {
	
	@Autowired
	private UserService userService;	
	
	@RequestMapping(value="/marine/user/profile", method=RequestMethod.GET)
	public String showProfilePage(HttpServletRequest httpServletRequest, Model model){
		User connectedUser = userService.getLogedUser(httpServletRequest);
		
		Set <GroupRole> assignedGroupRoles = connectedUser.getGroup().getGroupRoles();
		Set<Role> assignedUserRoles = connectedUser.getRoles();
		Set<Role> actualRolesInGroup = new HashSet<Role>();
		
		for (GroupRole role: assignedGroupRoles) {
			actualRolesInGroup.add(role.getRoleId());
		}

		actualRolesInGroup.removeAll(assignedUserRoles);
		model.addAttribute("connectedUser", connectedUser);
		model.addAttribute("groupRoles", actualRolesInGroup); 
			
		return "user/user_profile";
	}
}
