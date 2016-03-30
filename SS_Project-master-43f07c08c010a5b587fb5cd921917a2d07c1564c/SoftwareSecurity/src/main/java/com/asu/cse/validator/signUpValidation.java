package com.asu.cse.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.*; 
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.asu.cse.model.SSUser;

@SuppressWarnings("unused")
public class signUpValidation implements Validator {

	//public static final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*(@gmail.com|@asu.edu)$";
	
	@Override
	public boolean supports(Class<?> paramClass) {
		
		return SSUser.class.equals(paramClass);
	
	}

	@Override
	public void validate(Object obj, Errors errors) {
		SSUser user = (SSUser) obj;
		if(user.getPhoneNumber().length() != 10 || (Pattern.matches("[0-9]+", user.getPhoneNumber()) == false)) {
		errors.rejectValue("phoneNumber", "invalidPhone", new Object[]{"'phoneNumber'"}, "Invalid Phone Number");
		}
		
		if(StringUtils.isBlank(user.getFirstName()) || StringUtils.isEmpty(user.getFirstName()) || (Pattern.matches("[a-zA-Z]+", user.getFirstName()) == false)) {
			errors.rejectValue("firstName", "invalidFirstName", new Object[]{"'firstName'"}, "Invalid First Name");
		}
		
		if(user.getFirstName().length() > 45) {
			errors.rejectValue("firstName", "invalidFirstName", new Object[]{"'firstName'"}, "First Name length is too high");
		}
		
		if(StringUtils.isBlank(user.getLastName()) || StringUtils.isEmpty(user.getLastName()) || (Pattern.matches("[a-zA-Z]+", user.getLastName()) == false)) {
			errors.rejectValue("lastName", "invalidLastName", new Object[]{"'lastName'"}, "Invalid Last Name");
		}
		
		if(user.getLastName().length() > 45) {
			errors.rejectValue("lastName", "invalidLastName", new Object[]{"'lastName'"}, "Last Name length is too high");
		}
		
		if(user.getEmail().length() > 45) {
			errors.rejectValue("email", "invalidEmail", new Object[]{"'email'"}, "Email length is too high");
		}
	    if (StringUtils.isBlank(user.getEmail()) || !StringUtils.isEmpty(user.getEmail())) {
	       Pattern p = Pattern.compile(emailPattern);
		   Matcher m = p.matcher(user.getEmail());
		   boolean validEmail = m.matches();
		   
		   if(!validEmail) {
			   errors.rejectValue("email", "invalidEmail", new Object[]{"'email'"}, "Invalid Email");
		   }
		   
	     } else {
	    	 errors.rejectValue("email", "invalidEmail", new Object[]{"'email'"}, "Email is required");
	     }
	    
	    if(StringUtils.isNotBlank(user.getPassword()) && StringUtils.isNotEmpty(user.getPassword()) && 
	    	  StringUtils.isNotBlank(user.getConfirmPassword()) && StringUtils.isNotEmpty(user.getConfirmPassword()))
	    {
    		if(!user.getPassword().equals(user.getPassword())) 
    		{
    			errors.rejectValue("password", "passwordNotMatched", new Object[]{"'password'"}, "Passwords don't match");
    		} 
	    } else 
	    {
    		errors.rejectValue("password", "passwordNotSet", new Object[]{"'password'"}, "1 or more passwords are missing");
    	}
	    if(user.getSocial().length() != 9 || (Pattern.matches("[0-9]+", user.getSocial()) == false)){
	    	errors.rejectValue("ssn", "invalidssn", new Object[]{"'ssn'"}, "SSN length is incorrect") ;
	    }
	    if(StringUtils.isBlank(user.getSocial()) ||  StringUtils.isEmpty(user.getSocial())){
	    	errors.rejectValue("ssn", "invalidssn", new Object[]{"'ssn'"},"Invalid SSN");
	    }
	    if(user.getAddress().length() > 45){
	    	errors.rejectValue("address", "invalidAddress", new Object[]{"'address'"}, "Address length is too high") ;
	    }
	    if(StringUtils.isBlank(user.getAddress()) ||  StringUtils.isEmpty(user.getAddress())){
	    	errors.rejectValue("address", "invalidAddress", new Object[]{"'address'"},"Invalid Address");
	    }
	    if(user.getCity().length() > 45){
	    	errors.rejectValue("city", "invalidCity", new Object[]{"'city'"}, "City length is too high") ;
	    }
	    if(StringUtils.isBlank(user.getCity()) ||  StringUtils.isEmpty(user.getCity()) || (Pattern.matches("[a-zA-Z]+", user.getCity()) == false)){
	    	errors.rejectValue("city", "invalidCity", new Object[]{"'city'"},"Invalid City");
	    }
	    if(user.getState().length() > 45){
	    	errors.rejectValue("state", "invalidState", new Object[]{"'State'"}, "State length is too high") ;
	    }
	    if(StringUtils.isBlank(user.getState()) ||  StringUtils.isEmpty(user.getState()) ||(Pattern.matches("[a-zA-Z]+", user.getState()) == false)){
	    	errors.rejectValue("state", "invalidState", new Object[]{"'state'"},"Invalid State");
	    }
	    if(user.getZipcode().length() > 45){
	    	errors.rejectValue("zipcode", "invalidZipCode", new Object[]{"'zipcode'"}, "ZipCode length is too high") ;
	    }
	    if(StringUtils.isBlank(user.getZipcode()) ||  StringUtils.isEmpty(user.getZipcode()) || (Pattern.matches("[0-9]+", user.getZipcode()) == false)){
	    	errors.rejectValue("zipcode", "invalidZipCode", new Object[]{"'zipcode'"},"Invalid Zipcode");
	    }
	    if(user.getUserName().length() > 45){
	    	errors.rejectValue("userName", "invaliduserName", new Object[]{"'userName'"}, "User Name length is too high") ;
	    }
	    if(StringUtils.isBlank(user.getUserName()) ||  StringUtils.isEmpty(user.getUserName()) ||(Pattern.matches("[a-zA-Z0-9_]+", user.getUserName()) == false)){
	    	errors.rejectValue("userName", "invaliduserName", new Object[]{"'userName'"},"Invalid User Name");
	    }
	    
	  
	}

}
