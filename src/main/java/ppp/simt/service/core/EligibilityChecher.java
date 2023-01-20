package ppp.simt.service.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.env.Environment;
import ppp.simt.form.SimtLicenseChecker;

@Service
public class EligibilityChecher {
	@Autowired
	private Environment env;

	public SimtLicenseChecker check(String license, String speciality,String diplome)
	{
		System.out.println(env.getProperty("api.base.url")+"?license="+license+"&speciality="+speciality+"&diplome="+diplome);
		 RestTemplate restTemplate = new RestTemplate();
		 SimtLicenseChecker result = restTemplate.getForObject(env.getProperty("api.base.url")+"?license="+license+"&speciality="+speciality+"&diplome="+diplome, SimtLicenseChecker.class);
		 return result;
			
	}
}
