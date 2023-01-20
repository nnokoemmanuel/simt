package ppp.simt.service.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ppp.simt.entity.user.GroupRole;
import ppp.simt.entity.user.Role;
import ppp.simt.entity.user.User;
import ppp.simt.repository.user.UserRepository;

@Service
@Transactional
public class UserDetail implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(userName);
        if(user==null) return null;
        UserBuilder builder = null;
      
          builder = org.springframework.security.core.userdetails.User.withUsername(userName);
          builder.password(user.getPassword());
          builder.authorities(getAuthorities(user));
          
          return builder.build();      
    }
    

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        String [] finalRole;
        if(user.getGroup()!=null)
        {
          Set<GroupRole> groupsRole =user.getGroup().getGroupRoles();
          List<Role> roleGroup=new ArrayList<>();
          for(GroupRole grole: groupsRole) {
        	  roleGroup.add(grole.getRoleId());
        	  
          }
          String[] authoritiesFromGroup =roleGroup.stream().map((role) -> role.getName()).toArray(String[]::new);
          finalRole = Stream.concat(Arrays.stream(userRoles), Arrays.stream(authoritiesFromGroup))
                  .toArray(String[]::new);
        }else {
        	finalRole=userRoles;
        }
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(finalRole);
        return authorities;
    }

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

}