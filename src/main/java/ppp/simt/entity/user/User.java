package ppp.simt.entity.user;

import javax.persistence.*;

import ppp.simt.entity.core.Office;
import ppp.simt.entity.pv.TrainingCenter;

import java.util.Date;
import java.util.Set;


@Entity
public class User {
  
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
    private int id;
 
    private String firstName;
    private String lastName;
    private String pob;
    private Date dob;
    private String gender;
    private String phoneNumber;
    private String email;
    private String image;
    private Date registerOn = new Date();
    private String password;
    private int enabled;
    private int tokenExpired;
    private String username;
    private int active;
    private Date lastLogin;
    private Date passwordRequestedAt;
    private String token;
    
    @ManyToMany
    @JoinTable( 
        name = "user_role", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Set<Role> roles;
    
    @OneToOne
	@JoinColumn(name="office_id")
	private Office office;
    
    @OneToOne
   	@JoinColumn(name="group_id")
   	private Group group;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="training_center_id")
    private TrainingCenter trainingCenter;

    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int isEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int isTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(int tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
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

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setPasswordRequestedAt(Date passwordRequestedAt) {
		this.passwordRequestedAt = passwordRequestedAt;
	}

	public Date getPasswordRequestedAt() {
		return passwordRequestedAt;
	}

	public int getEnabled() {
		return enabled;
	}

	public int getTokenExpired() {
		return tokenExpired;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group groupId) {
		this.group = groupId;
	}

	public TrainingCenter getTrainingCenter() {
		return trainingCenter;
	}

	public void setTrainingCenter(TrainingCenter trainingCenter) {
		this.trainingCenter = trainingCenter;
	}

	
	
	
    
    
}