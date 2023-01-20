package ppp.simt.service.user;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import ppp.simt.entity.user.User;

@Service
public class UserCredentialManager {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public String sentResetingMessage(HttpServletRequest request, String email) {
		User user=userService.findUserByEmail(email);
		String response ="resetting.mail.sent";
		if(user==null) response="user.not.found";
		else {
			 Date lastRequest=user.getPasswordRequestedAt();
			 if(lastRequest!=null)
			 {
             Calendar cal=Calendar.getInstance();
             cal.setTime(lastRequest);
             cal.add(Calendar.DATE,1);
             
             Calendar nowCal =Calendar.getInstance();
             nowCal.setTime(new Date());
            if(cal.compareTo(nowCal)>=0) {
            	response="can.not.reset.twice";
            }else {
            	
            	
            	}
            }
            
		  }
		
		return null;
	
	}

	public  void reset(HttpServletRequest request) {
		
	}
	
	public void sentMail() {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo("radius.bikadai@gmail.com"); 
        message.setSubject("TEST RESETING"); 
        message.setText("HELLO WORLD! VISIT htts://www.ssdt.cm");
        javaMailSender.send(message);
	}
}
