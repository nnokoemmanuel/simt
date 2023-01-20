/**
 * Validator of UserForm
 */
package ppp.simt.form.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ppp.simt.form.TrainingCenterForm;


@Component
public class TrainingCenterFormValidator implements Validator {



	public boolean supports(Class clazz) {
		return TrainingCenterForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors e) {
		
		TrainingCenterForm trainingCenterForm = (TrainingCenterForm) obj;

		if (trainingCenterForm.equals(null)) {
				e.rejectValue("vide", "form.empty.exception");
		}
      

	}
}