/**
 * Validator of UserForm
 */
package ppp.simt.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ppp.simt.entity.user.Role;
import ppp.simt.entity.user.User;
import ppp.simt.form.UserForm;
import ppp.simt.service.user.RoleService;
import ppp.simt.service.user.UserService;

@Component
public class UserFormValidator implements Validator {

	@Autowired
	private UserService userService;
 
	@Autowired
	private RoleService roleService;

	public boolean supports(Class clazz) {
		//return UserForm.class.isAssignableFrom(clazz);
		return true;
	}

	public void validate(Object obj, Errors e) {
		UserForm userForm = (UserForm) obj;
		User user = userService.findUserByEmail(userForm.getEmail());
		if (user != null) {
			if (userForm.getId() == 0)
				e.rejectValue("email", "email.used");
			else {
				user = userService.findUserById(userForm.getId());
				if (userForm.getId() != user.getId())
					e.rejectValue("email", "email.used");
			}
		}

		user = userService.findUserByUsername(userForm.getUsername());
		if (user != null) {
			if (userForm.getId() == 0)
				e.rejectValue("username", "username.used");
			else {
				user = userService.findUserById(userForm.getId());
				if (userForm.getId() != user.getId()) {
					e.rejectValue("username", "username.used");
				}
			}
		}


	}
}