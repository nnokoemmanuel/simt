/**
 * Validator of UserForm
 */
package ppp.simt.form.validator.evaluation;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ppp.simt.form.evaluation.ModuleForm;


@Component
public class ModuleFormValidator implements Validator {



	public boolean supports(Class clazz) {
		return ModuleForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors e) {
		
		ModuleForm moduleForm = (ModuleForm) obj;

		if (moduleForm.equals(null)) {
				e.rejectValue("vide", "form.empty.exception");
		}
      

	}
}