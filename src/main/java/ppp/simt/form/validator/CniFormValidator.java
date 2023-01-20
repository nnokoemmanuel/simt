package ppp.simt.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ppp.simt.form.CNIForm;



@Component
public class CniFormValidator implements Validator{

	public boolean supports(Class clazz) {
		return true;
	}

	public void validate(Object obj, Errors e) {
		
		CNIForm cniForm = (CNIForm) obj;
			

		if (cniForm.equals(null)) {
				e.rejectValue("vide", "form.empty.exception");
		}
	}
}
