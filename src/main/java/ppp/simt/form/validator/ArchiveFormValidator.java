/**
 * Validator of UserForm
 */
package ppp.simt.form.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ppp.simt.form.ArchiveForm;

@Component
public class ArchiveFormValidator implements Validator {



	public boolean supports(Class clazz) {
		return ArchiveForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors e) {
		
		ArchiveForm archiveForm = (ArchiveForm) obj;

		if (archiveForm.equals(null)) {
				e.rejectValue("vide", "form.empty.exception");
		}
      

	}
}