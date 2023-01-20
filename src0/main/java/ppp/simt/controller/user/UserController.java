package ppp.simt.controller.user;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ppp.simt.entity.core.Office;
import ppp.simt.entity.pv.TrainingCenter;
import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.GroupRole;
import ppp.simt.entity.user.Role;
import ppp.simt.entity.user.User;
import ppp.simt.form.UserForm;
import ppp.simt.form.validator.UserFormValidator;
import ppp.simt.service.core.OfficeService;
import ppp.simt.service.pv.TrainingCenterService;
import ppp.simt.service.user.GroupRoleService;
import ppp.simt.service.user.GroupService;
import ppp.simt.service.user.RoleService;
import ppp.simt.service.user.UserService;
import ppp.simt.service.user.UserTrackingService;
import ppp.simt.util.Constants;
import ppp.simt.util.Logger_;
import ppp.simt.util.Tracker;


@Controller
public class UserController {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OfficeService officeService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupRoleService groupRoleService;
	
	@Autowired

	private Tracker tracker;
	
	@Autowired
	private Logger_ logger_;
	
	@Autowired
	private UserTrackingService userTrackingService;

	@Autowired
	protected UserFormValidator userValidator;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String serverName;
			
	@Value("${spring.mail.password}")
	private String serverPassword;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private TrainingCenterService trainingCenterService;


	
	@InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
	    final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    binder.setValidator(userValidator);
	}
	

	
	@RequestMapping(value="/marine/user/create", method=RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public String createUser(Model model){
		
        List<Role> roless = roleService.findAll();
		List<Office> officess = officeService.findAll();
		List<Group> groupss = groupService.findAll();
		List<TrainingCenter> trainingCenters = trainingCenterService.findAll();

		List<Office> offices = new ArrayList<Office>();
		List<Role> roles = new ArrayList<Role>();
		List<Group> groups = new ArrayList<Group>();

		roles.addAll(roless);
		groups.addAll(groupss);
		offices.addAll(officess);
        
				
		//model.addAttribute("roles",roles);
		model.addAttribute("offices", offices);
		model.addAttribute("groups", groups);
		model.addAttribute("trainingCenters", trainingCenters);

		return "user/create";
	}
	
	
	@RequestMapping(value="/marine/user/listGroupRole", method=RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public String getGroupRole(@RequestParam("id") long id,Model model, HttpServletRequest httpServletRequest){
		List<Role> roless = roleService.findAll();
		List<Role> roles = new ArrayList<Role>();
		roles.addAll(roless);
		Group group = groupService.findById(id);
		List<GroupRole> groupRoleList =  groupRoleService.findRolesByGroup(group);
		List<Long> groupRolesIds = new ArrayList<Long>();
		for (GroupRole role : groupRoleList) {
	        groupRolesIds.add(role.getRoleId().getId());
	     }
		model.addAttribute("roless",roles);
		model.addAttribute("groupRoles", groupRolesIds);
		return "user/role_view";
		
	}
	
	/*
	 * Enregistrement d'un utilisateur
	 */
	@ResponseBody
	@RequestMapping(value="/marine/user/createPost", method=RequestMethod.POST)
	@Secured({"ROLE_ADMIN"})
	public String createPost(@ModelAttribute("userform") @Validated UserForm userform, BindingResult result,HttpServletRequest httpServletRequest){
		
		//System.out.println("===================>CREATE");
				if(result.hasErrors()){

					if(result.getFieldError("email")!=null){
						return result.getFieldError("email").getCode();
						
					}

					if(result.getFieldError("username")!=null)
						return result.getFieldError("username").getCode();


					if(result.getFieldError("error")!=null){
						return result.getFieldError("error").getCode();
					}
						

				}
		
		
		User user = userform.convertToUser();
		 Set<Role> userRole=new HashSet<>();
		    Group group =groupService.findGroupById((long)userform.getGroup()) ;
		    user.setGroup(group);
		        
		    if(userform.getRoles()!=null)
		    {
			for(int i=0 ;i<userform.getRoles().length;i++ ){
				Role role=roleService.findById(userform.getRoles()[i]);
				if(role!=null)
				userRole.add(role);
			}}
		   
		    user.setRoles(userRole);

		    user.setOffice(officeService.findOfficeById(userform.getOffice()));
		    if(userform.getTrainingCenter() == 0){
				user.setTrainingCenter(null);
			}else {
				user.setTrainingCenter(trainingCenterService.findById(userform.getTrainingCenter()));
			}
		    user.setPassword(userform.getPassword());
            user.setEmail(userform.getEmail());
            user.setUsername(userform.getUsername());
            
		      userService.saveUser(user);	
		
		tracker.track(user, "CREATE | REGISTER A USER WITH THE NAME: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "Created the user: "+user);	

		return String.valueOf(user.getId());
		
		
	}
	
	@RequestMapping(value="/marine/user/list", method=RequestMethod.GET)
	public String list(Model model){
        List<User> users = userService.findAll();
		model.addAttribute("users", users);
			
		return "user/list";
	}
	

	//@RequestMapping(value="/marine/user/detail", method=RequestMethod.GET)
	@RequestMapping(value="/marine/user/detail", method=RequestMethod.GET)
	public String detail(@RequestParam("id") int id,HttpServletRequest httpServletRequest, Model model){
		User connectedUser = userService.findUserById(id);
		Set <GroupRole> assignedGroupRoles = connectedUser.getGroup().getGroupRoles();
		Set<Role> assignedUserRoles = connectedUser.getRoles();
		Set<Role> actualRolesInGroup = new HashSet<Role>();
		
		for (GroupRole role: assignedGroupRoles) {
			actualRolesInGroup.add(role.getRoleId());
		}
		actualRolesInGroup.removeAll(assignedUserRoles);
		model.addAttribute("groupRoles", actualRolesInGroup);
		model.addAttribute("connectedUser", connectedUser);
		return "user/user_profile";
	}
	
	
	
	/**
	 * Affiche le formulaire de modification d'un utilisateur
	 * @return
	 */
	@RequestMapping(value="/marine/user/update/{userId}", method=RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public String updateGet(@PathVariable(value="userId") int userId ,Model model){
		
		User user = userService.findUserById(userId);
		UserForm userForm = UserForm.fromUser(user);
		List<Role> roleObject = userForm.getRolesAsObject();
		
		List<Role> roless = roleService.findAll();
		List<Office> officess = officeService.findAll();
		List<Group> groupss = groupService.findAll();
		List<TrainingCenter> trainingCenters = trainingCenterService.findAll();
		List<Office> offices = new ArrayList<Office>();
		List<Role> roles = new ArrayList<Role>();
		List<Long> roleObjects = new ArrayList<Long>();
		List<Group> groups = new ArrayList<Group>();
		
		
		for (Role roleOb : roleObject) {
			roleObjects.add(roleOb.getId());
	     }
		
		roles.addAll(roless);
		groups.addAll(groupss);
		offices.addAll(officess);
		//model.addAttribute("roleObjects", roleObjects);		
		model.addAttribute("userForm",userForm);
 		model.addAttribute("offices", offices);
		//model.addAttribute("roles",roles);
		model.addAttribute("groups",groups);
		model.addAttribute("trainingCenters",trainingCenters);
		return "user/update";
	}
	
	
	@RequestMapping(value="/marine/user/listGroupRoleUpdate", method=RequestMethod.GET)
	@Secured({"ROLE_ADMIN"})
	public String getGroupRoleUpdate(@RequestParam("id") long id,@RequestParam("userId") int userId,@RequestParam("oldGroupId") long oldGroupId,Model model, HttpServletRequest httpServletRequest){		
		
		List<Role> roless = roleService.findAll();
		List<Role> roles = new ArrayList<Role>();
		roles.addAll(roless);
		Group group = groupService.findById(id);
		Group oldGroup = groupService.findById(oldGroupId);
		List<GroupRole> groupRoleList =  groupRoleService.findRolesByGroup(group);
		List<GroupRole> oldGroupRoleList =  groupRoleService.findRolesByGroup(oldGroup);
		
		List<Long> groupRolesIds = new ArrayList<Long>();
		List<Long> oldGroupRolesIds = new ArrayList<Long>();
		
		User user = userService.findUserById(userId);
		UserForm userForm = UserForm.fromUser(user);
		List<Long> roleObjects = new ArrayList<Long>();
		
		List<Role> roleObject = userForm.getRolesAsObject();
		for (Role roleOb : roleObject) {
			roleObjects.add(roleOb.getId());
	     }
				
		for (GroupRole role : oldGroupRoleList) {
			oldGroupRolesIds.add(role.getRoleId().getId());
	     }
		
		for (GroupRole role : groupRoleList) {
	        groupRolesIds.add(role.getRoleId().getId());
	     }
		
		
		List<Long> merged = new ArrayList(roleObjects);
        merged.addAll(groupRolesIds);
        
         merged.removeAll(oldGroupRolesIds);
        
        List<Long> finalRoles = new ArrayList(merged);
        finalRoles.addAll(groupRolesIds);
		
		
		model.addAttribute("roleObjects", roleObjects);
		model.addAttribute("roless",roles);
		model.addAttribute("finalRoles", finalRoles);
		return "user/update_role_view";
		
	}
	

	/*
	 * Enregistrement d'un utilisateur
	 */
	@ResponseBody
	@RequestMapping(value="/marine/user/update", method=RequestMethod.POST)
	@Secured({"ROLE_ADMIN"})
	public String updatePost(@ModelAttribute("userform") @Validated UserForm userform, Model model, BindingResult result,HttpServletRequest httpServletRequest){
		User user = userform.convertToUser();
		int userId = user.getId();
		Set<Role> userRole = new HashSet<>();
		Group group =groupService.findGroupById((long)userform.getGroup()) ;
		Office office= officeService.findOfficeById(userform.getOffice());
		user.setGroup(group);
		user.setOffice(office);
		List<Long> dbRoles = userService.findUserRolesByUserId(userId);
				
		if(userform.getRoles()!=null)
	    {
		for(int i=0 ;i<userform.getRoles().length;i++ ){
			Role role=roleService.findById(userform.getRoles()[i]);
			Long roleId = role.getId();
			userService.delete(roleId, userId);
		}
		}
		 if(userform.getRoles()!=null)
		    {
			for(int i=0 ;i<userform.getRoles().length;i++ ){				
				Role role=roleService.findById(userform.getRoles()[i]);
				if(role!=null)
				userRole.add(role);
			}
			}
		user.setRoles(userRole);
		user.setOffice(officeService.findOfficeById(userform.getOffice()));
		if(userform.getTrainingCenter() == 0){
			user.setTrainingCenter(null);
		}else {
			user.setTrainingCenter(trainingCenterService.findById(userform.getTrainingCenter()));
		}
		user.setEmail(userform.getEmail());
		userService.update(user);
		
		tracker.track(user, "EDIT | UPDATE A USER WITH THE NAME: " +  user.getFirstName()+" "+user.getLastName(), httpServletRequest);
		logger_.log(Constants.NORMAL_LOG_DIR, "Created the user: "+user);
				
		return String.valueOf(user.getId());
	}



@RequestMapping(value="account/reset")
@ResponseBody
public String resetPassword(@RequestParam("email") String email, HttpServletRequest request) throws UnknownHostException {
    User user =userService.findUserByEmail(email); 
	String baseUrl = String.format("%s://%s:%d",request.getScheme(),  request.getServerName(), request.getServerPort());

    String subject="password recovery";
    if(user==null) return "ko";
    else {
   	   String token = UUID.randomUUID().toString();

    	String contextPath = request.getContextPath();
		baseUrl.replace("https", "http");
		baseUrl.replace("http", "https");
		baseUrl +=	contextPath;

		String message = "Hello! "+user.getUsername()+"\n To reset your password - please visit "+baseUrl+"/password/forgotten/reset/"+token+" \n Regards, the Team.";


    	 userService.sentEmail(subject,message, email, serverName, serverPassword);
 		 token = token.replace('-', 'p');
 		 token += user.getId();
 		 user.setToken(token);
 		 Date now = new Date();
 		 user.setPasswordRequestedAt(now);
 		 userService.update(user);
	return "ok";
    }
}

@RequestMapping(value="/password/forgotten/reset/{token}")
public String resetForm(@PathVariable String token, Model model) {
	User user =userService.findByToken(token);
	if(user==null) model.addAttribute("tokenCheck", "invalid");
	else model.addAttribute("tokenCheck", "valid");
	model.addAttribute("token", token);
	return "reset";
}


@RequestMapping(value="/password/forgotten/reset/step/{token}/{password}")
@ResponseBody
public String reset(@PathVariable String token, @PathVariable String password) {
	User user =userService.findByToken(token);
	if(user==null) return "notFound";
	user.setToken(null);
	String encodedPassword =bCryptPasswordEncoder.encode(password);
	user.setPassword(encodedPassword);
	userService.saveUser(user);
	return "done";
	
}

}