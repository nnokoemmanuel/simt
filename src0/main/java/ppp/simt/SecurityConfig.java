package ppp.simt;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ppp.simt.service.user.UserDetail;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserDetail userDetails;
	
	
	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth
	         .userDetailsService(userDetails)
	         .passwordEncoder(bCryptPasswordEncoder);
	  }
	 

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
		authorizeRequests()
			.antMatchers("/sw.js").permitAll()
			.antMatchers("/account/reset").permitAll()
			.antMatchers("/install-sw.js").permitAll()
			.antMatchers("/password/forgotten/reset/**").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/password/forgotten/**").permitAll()
			.anyRequest()
			.authenticated().and().csrf().disable().formLogin()
			.loginPage("/login").failureUrl("/login?error=true")
			.defaultSuccessUrl("/home?login=true")
			.usernameParameter("username")
			.passwordParameter("password")
			.and().logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login?logout=true").and().exceptionHandling()
			.accessDeniedPage("/access-denied");
	}
	 
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**","/assets/**", "/capacityImages/**");
}

}
