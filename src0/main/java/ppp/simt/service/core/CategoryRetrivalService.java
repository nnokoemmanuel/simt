package ppp.simt.service.core;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ppp.simt.form.CategoryForm;
import ppp.simt.form.SimtLicenseChecker;
@Service
public class CategoryRetrivalService {
	@Autowired
	private Environment env;

	public List<CategoryForm> findAll(String license)
	{
		 RestTemplate restTemplate = new RestTemplate();
		 List<CategoryForm> result = restTemplate.getForObject(env.getProperty("api.base.url.category")+"?license="+license, List.class);
		 return result;
			
	}
}
