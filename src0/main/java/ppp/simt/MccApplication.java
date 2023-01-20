package ppp.simt;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;


import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

import ppp.simt.util.Constants;


@SpringBootApplication
public class MccApplication extends SpringBootServletInitializer  implements WebMvcConfigurer  {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(MccApplication.class);
	}
 
	public static void main(String[] args) {
		SpringApplication.run(MccApplication.class, args);
	}
	 @Bean
	 public CookieLocaleResolver localeResolver() {
	    CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
	    cookieLocaleResolver.setDefaultLocale(new Locale("fr"));
	    cookieLocaleResolver.setCookieName(Constants.COOKIE_LANGUAGE_NAME);
	    return cookieLocaleResolver;
	}

	    @Bean 
	    public LocaleChangeInterceptor localeChangeInterceptor() {
	        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	        lci.setParamName("lang");
	        return lci;
	    }

		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			return bCryptPasswordEncoder;
		}
		
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
		
	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {

	        registry.addResourceHandler("/images/**").addResourceLocations("/static/images/")
	                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	        registry.addResourceHandler("/css/**").addResourceLocations("/static/css/")
	                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	        registry.addResourceHandler("/js/**").addResourceLocations("/static/js/")
	                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	        registry.addResourceHandler("/assets/**").addResourceLocations("/static/assets/")
            .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	        registry.addResourceHandler("/install-sw.js").addResourceLocations("/static/")
            .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	        registry.addResourceHandler("/capacityImages/**").addResourceLocations("/static/capacityImages/")
            .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	    }
	    
	    @Bean
	    public JavaMailSender getJavaMailSender() {
	    	  JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		        mailSender.setHost("smtp.gmail.com");
		        mailSender.setPort(587);
		        mailSender.setUsername("mail.ssdt@gmail.com");
		        mailSender.setPassword("Pres123print");
		        Properties props = mailSender.getJavaMailProperties();
		        props.put("mail.transport.protocol", "smtp");
		        props.put("mail.smtp.auth", "true");
		        props.put("mail.smtp.auth", "true");
		        props.setProperty("mail.smtp.**ssl.enable", "true");
		        props.setProperty("mail.smtp.**ssl.required", "true");
		        props.put("mail.debug", "true");
	            props.put("mail.smtp.ssl.trust", "*");
		        return mailSender;
	    }
	    
	    
	    
	    @Bean
	    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
	        StrictHttpFirewall firewall = new StrictHttpFirewall();
	        firewall.setAllowUrlEncodedSlash(true);    
	        return firewall;
	    }
}