package ppp.simt.form.validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ppp.simt.form.InSlipForm;

@Component
public class InSlipValidator implements Validator {



	public boolean supports(Class clazz) {
		return InSlipForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors e) {
		InSlipForm form = (InSlipForm) obj;
		String[] fileNameArray=form.getFilename().split("\\.");
		String fileExtension = fileNameArray[fileNameArray.length-1];
		if (fileExtension.equals(null) || fileExtension.length()<=2) {
				//e.rejectValue("exension", "bad.file.extension");
		}
      
	}
}