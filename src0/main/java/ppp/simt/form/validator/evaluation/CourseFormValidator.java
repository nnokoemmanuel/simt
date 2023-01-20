/**
 * Validator of UserForm
 */
package ppp.simt.form.validator.evaluation;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ppp.simt.form.evaluation.CourseForm;


@Component
public class CourseFormValidator implements Validator {



	public boolean supports(Class clazz) {
		return CourseForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors e) {
		
		CourseForm courseForm = (CourseForm) obj;

		if (courseForm.equals(null)) {
				e.rejectValue("vide", "form.empty.exception");
		}
      

	}
}