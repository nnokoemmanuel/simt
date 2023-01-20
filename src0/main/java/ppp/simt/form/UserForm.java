package ppp.simt.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.Role;
import ppp.simt.entity.user.User;
import ppp.simt.service.core.OfficeService;
import ppp.simt.service.user.GroupService;
import ppp.simt.service.user.RoleService;


public class UserForm  {

	private int id;
	private String username;
	private Date dob;
	private String pob;
	private String firstName;
	private String lastName;
	private String email;
	private int active;
	private Date lastLogin;
	private String gender;
	private String password;
	private String image;
	private Date registerOn;
	private String phoneNumber;
	private int office;
	private int trainingCenter;
	private long group;
	private Long [] roles;
	private List<Role> rolesAsObject;

	
	public UserForm() {
	}
	
	public UserForm(int id, String username, Date dob, String pob, String firstName, String lastName,
			String email, int active, Date lastLogin, String gender, String password, String image, String phoneNumber,int office, int trainingCenter,long group,Long[] roles) {
		super();
		this.id = id;
		this.username = username;
		this.dob = dob;
		this.pob = pob;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.active = active;
		this.lastLogin = lastLogin;
		this.gender = gender;
		this.password=password;
		this.image=image;
		this.phoneNumber = phoneNumber;
		this.office = office;
		this.trainingCenter=trainingCenter;
		this.roles=roles;
		this.group=group;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getPob() {
		return pob;
	}
	public void setPob(String pob) {
		this.pob = pob;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getRegisterOn() {
		return registerOn;
	}

	public void setRegisterOn(Date registerOn) {
		this.registerOn = registerOn;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getOffice() {
		return office;
	}

	public void setOffice(int office) {
		this.office = office;
	}

	public long getGroup() {
		return group;
	}

	public void setGroup(long group) {
		this.group = group;
	}
	
	public Long[] getRoles() {
		return roles;
	}

	public void setRoles(Long[] roles) {
		this.roles = roles;
	}


	public List<Role> getRolesAsObject() {
		return rolesAsObject;
	}

	public void setRolesAsObject(List<Role> rolesAsObject) {
		this.rolesAsObject = rolesAsObject;
	}

	public int getTrainingCenter() {
		return trainingCenter;
	}

	public void setTrainingCenter(int trainingCenter) {
		this.trainingCenter = trainingCenter;
	}

	/**
	 * Convert Form to User entity
	 * @return
	 */
	public User convertToUser(){
		User user = new User();
		
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setDob(dob);
		user.setPob(pob); 
		user.setGender(gender);
		user.setActive(active);
		user.setImage(image);
		user.setId(id);
		user.setPhoneNumber(phoneNumber);
		
	   
	    
		return user;
	}

	/*
	 * Populate userform from user entity
	 */
	public static UserForm fromUser(User user) {
		UserForm userForm = new UserForm();
		userForm.setActive(user.getActive());
		userForm.setId(user.getId());
		userForm.setEmail(user.getEmail());
		userForm.setLastName(user.getLastName());
		userForm.setUsername(user.getUsername());
		userForm.setDob(user.getDob());
		userForm.setFirstName(user.getFirstName());
		userForm.setPob(user.getPob());
		userForm.setGender(user.getGender());
		userForm.setImage(user.getImage());
		userForm.setRegisterOn(user.getRegisterOn());
		userForm.setLastLogin(user.getLastLogin());
		userForm.setPhoneNumber(user.getPhoneNumber());
		userForm.setOffice(user.getOffice().getId());
		userForm.setGroup(user.getGroup().getId());
		userForm.setPassword(user.getPassword());
		ArrayList<Role> roleObject = new ArrayList();
		roleObject.addAll(user.getRoles());
		userForm.setRolesAsObject(roleObject);
		
		
		
		
		return userForm;
	}

	@Override
	public String toString() {
		return "UserForm [id=" + id + ", username=" + username + ", role=" + roles + ", dob=" + dob + ", pob=" + pob
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", active=" + active
				+ ", lastLogin=" + lastLogin + ", gender=" + gender + ", password=" + password + ", image=" + image
				+ ", registerOn=" + registerOn + ", phoneNumber=" + phoneNumber + ",rolesAsObject=" + rolesAsObject + ", group=" + group + ", office=" + office + "]";
	}

  
	
}
