package ppp.simt.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ppp.simt.form.ApplicantForm;



@Component
public class ApplicantFormValidator implements Validator  {
	
	
	public boolean supports(Class clazz) {
		return true;
	}

	@Override
	public void validate(Object obj, Errors e) {

		ApplicantForm applicantForm = (ApplicantForm) obj;	
		
		
		if (applicantForm.equals(null)) {
			e.rejectValue("vide", "form.empty.exception");
	}
	}


}
