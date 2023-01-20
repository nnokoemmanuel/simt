package ppp.simt.service.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ppp.simt.entity.user.Group;
import ppp.simt.entity.user.User;
import ppp.simt.repository.user.RoleRepository;
import ppp.simt.repository.user.UserRepository;

//import com.ppp.util.Constants;


@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	EntityManager em;
   

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	public User getLogedUser(HttpServletRequest httpServletRequest){
		
		HttpSession httpSession = httpServletRequest.getSession();
		SecurityContext securityContext = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
		
		if(securityContext == null)
			return null;
		
		String userName = securityContext.getAuthentication().getName();
		List<String> roles = new ArrayList<>();
		
		for(GrantedAuthority ga:securityContext.getAuthentication().getAuthorities()){
			roles.add(ga.getAuthority());
		}
		
		User user = userRepository.findByUsername(userName);
		
		return user;
}
	

	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}


	@Override
	public User findUserById(int id) {
		User user = userRepository.findById(id);
		return user;
	}


	@Override
	public User findByGroup(Group group) {
		User user = userRepository.findByGroup(group);
		return user;
	}


	@Override
	public void update(User user) {
		User oldUser = findUserById(user.getId());
		
		/*if(httpServletRequest.isUserInRole(Constants.ROLE_ADMIN)){
			user.setPassword(oldUser.getPassword());
			user.setUsername(oldUser.getUsername());
			user.setEmail(oldUser.getEmail());
	        //Role userRole = roleRepository.findByName(role);
	        //user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
			userRepository.save(user);*/
		//}else{
			oldUser.setGender(user.getGender());
			oldUser.setFirstName(user.getFirstName());
			oldUser.setPhoneNumber(user.getPhoneNumber());
			oldUser.setDob(user.getDob());
			oldUser.setPob(user.getPob());
			oldUser.setLastName(user.getLastName());
			oldUser.setEmail(user.getEmail());
			oldUser.setActive(user.getActive());
			oldUser.setImage(user.getImage());
			//oldUser.setRegisterOn(user.getLastLogin());
			oldUser.setRegisterOn(user.getRegisterOn());
			oldUser.setOffice(user.getOffice());
			oldUser.setGroup(user.getGroup());
			oldUser.setLastLogin(user.getLastLogin());
			oldUser.setRoles(user.getRoles());
			userRepository.save(oldUser);
			
			
		}
	//}
	
	

	@Override
	public User findUserByUsername(String userName) {
		return userRepository.findByUsername(userName);
	}
	@Override
	public ArrayList <User> findUserByActive(int status) {
		return userRepository.findByActive(status);
	}
	public  String encryptPassword(String password){
		
		return bCryptPasswordEncoder.encode(password);
	}
	
    public  boolean matche(String password, String encrypted){
		return bCryptPasswordEncoder.matches(password,encrypted);
	}

    @Override
    public List<User> findAll() {
    	return userRepository.findAll();
	     
    }

	@Override
	public void sentEmail(String subject,String message, String email, String serverName, String serverPassword) {
	
		    Properties props = new Properties();
		    props.put("mail.transport.protocol", "smtp");
		    props.put("mail.smtp.auth", "true");
		    props.setProperty("mail.smtp.**ssl.enable", "true");
		    props.setProperty("mail.smtp.**ssl.required", "true");
		    props.put("mail.debug", "true");
		    props.put("mail.smtp.ssl.trust", "*");
		    props.put("mail.smtp.starttls.enable","true");

		    props.put("mail.25", 587);
		    props.put("mail.smtp.host", "smtp.gmail.com");
		    props.put("mail.smtp.port", 587);
		    
		    Authenticator auth = new Authenticator() {
		        public PasswordAuthentication getPasswordAuthentication() {
		            return new PasswordAuthentication(serverName, serverPassword);
		        }
		    };
		   try {
		    Session session = Session.getInstance(props, auth);
		    Message msg = new MimeMessage(session);

		    msg.setFrom(new InternetAddress(serverName));
		    InternetAddress[] toAddresses = { new InternetAddress(email) };
		    msg.setRecipients(Message.RecipientType.TO, toAddresses);
		    msg.setSubject(subject);
		    msg.setSentDate(new Date());
		    msg.setHeader("Content-Type", "text/html; charset=UTF-8");
		    msg.setContent(message,"text/html; charset=utf-8" );
		    Transport.send(msg);
		    }catch(MessagingException e) {
		     e.printStackTrace();
		    }
		
		   
		   
		   
	}

	@Override
	public void delete(Long roleId,int userId) {
		userRepository.delete(roleId, userId);
		
	}
    
	@Override
	public List<Long> findUserRolesByUserId(int userId){
		return userRepository.findUserRolesByUserId(userId);
		
	}



	@Override
	public User findByToken(String token) {
		// TODO Auto-generated method stub
		return userRepository.findByToken(token);
	}
	
}