/**
 * Validator of UserForm
 */
package ppp.simt.form.validator;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ppp.simt.form.FileUploadForm;

@Component
public class FileUploadFormValidator implements Validator {



	public boolean supports(Class clazz) {
		return FileUploadForm.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors e) {
		FileUploadForm fileUploadForm = (FileUploadForm) obj;
		String[] fileNameArray=fileUploadForm.getFileName().split("\\.");
		String fileExtension = fileNameArray[fileNameArray.length-1];
		if (fileExtension.equals(null) || fileExtension.length()<=2) {
				e.rejectValue("exension", "bad.file.extension");
		}


	}
}