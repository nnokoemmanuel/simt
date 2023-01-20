/**
 * Validator of UserForm
 */
package ppp.simt.form.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ppp.simt.form.AgreementForm;


@Component
public class AgreementFormValidator implements Validator {



	public boolean supports(Class clazz) {
		return AgreementForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors e) {
		
		AgreementForm agreementForm = (AgreementForm) obj;

		if (agreementForm.equals(null)) {
				e.rejectValue("vide", "form.empty.exception");
		}
      

	}
}