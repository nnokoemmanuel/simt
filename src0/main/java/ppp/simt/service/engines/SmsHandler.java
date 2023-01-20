package ppp.simt.service.engines;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

@Component
public class SmsHandler {
	@Autowired
	private Environment env;

	public void sendSms(String destinationnumber, String message) {

		Date today = new Date();
		Time time = new Time(today.getTime());
		LocalTime localTime = time.toLocalTime();
		JdbcTemplate secondaryJdbcTemplate= new JdbcTemplate(remoteDataSource());
		//SendSMS sms = new SendSMS();
		String sql = "insert into outbox ( UpdatedInDB, InsertIntoDB, " +
				"SendingDateTime, SendBefore, SendAfter, Text, DestinationNumber, ID," +
				" RelativeValidity, SenderID, CreatorID, Retries, Priority, coding," +
				" auth, class, TextDecoded, multipart, SendingTimeOut, UDH, " +
				" DeliveryReport) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		secondaryJdbcTemplate.update(sql, today, today, today,localTime,localTime, "marine", destinationnumber,null,255
				, "MINT", "PPP", 0,0,"Default_No_Compression",null,-1, message, "false", today , null, "default" );
	}

	public DataSource remoteDataSource(){
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(env.getProperty("smsd.spring.datasource.driver-class-name"));
	    dataSource.setUrl(env.getProperty("smsd.spring.datasource.url"));
	    dataSource.setUsername(env.getProperty("smsd.spring.datasource.username"));
	    dataSource.setPassword(env.getProperty("smsd.spring.datasource.password"));
	    return dataSource;
	   }
	
	public int encryptPolynomial(int value){
		return value;//*88+value*47+36;
	}
}
