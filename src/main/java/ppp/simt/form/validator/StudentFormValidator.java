/**
 * Validator of CandidateForm
 */
package ppp.simt.form.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ppp.simt.form.CandidateChekerForm;
import ppp.simt.form.StudentForm;
import ppp.simt.service.pv.StudentSessionService;

@Component
public class StudentFormValidator implements Validator {
	
	@Autowired
	private StudentSessionService studentSessionService;


	public boolean supports(Class clazz) {
		return true;
	}

	public void validate(Object obj, Errors e) {
		
		StudentForm studentForm = (StudentForm) obj;
		String licenseNum = studentForm.getLicenseNum();
			

		if (studentForm.equals(null)) {
				e.rejectValue("vide", "form.empty.exception");
		}
		
	CandidateChekerForm candidateState = studentSessionService.checkPrerequis(studentForm);
	
	if (candidateState.getMessage() == "probability.duplicate" ) {
		e.rejectValue("licenseNum", "dupilcation.of.person");
	}
	}
	

}



