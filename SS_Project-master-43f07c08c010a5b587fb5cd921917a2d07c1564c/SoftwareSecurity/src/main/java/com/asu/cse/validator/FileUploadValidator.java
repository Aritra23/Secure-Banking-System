package com.asu.cse.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.asu.cse.model.FileUpload;

public class FileUploadValidator implements Validator{

	@Override
	public boolean supports(Class clazz) {
		//just validate the FileUpload instances
		return FileUpload.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		FileUpload file = (FileUpload)target;
		if(file == null){
			errors.rejectValue("file", "required.fileUpload");
		}
		if(file.getFile().getSize()==0){
			errors.rejectValue("file", "required.fileUpload");
		}
		if(file.getFile().getOriginalFilename()==""){
			errors.rejectValue("file", "required.fileUpload");
		}
		
		if(file.getFile().getSize()>10000){
			errors.rejectValue("file", "required.Smallersize");
		}
		if(!file.getFile().getOriginalFilename().endsWith(".txt"));{
			errors.rejectValue("file", "Invalid File Extension");
		}
		
	}
}

