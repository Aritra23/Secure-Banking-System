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

import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;

@SuppressWarnings("unused")
public class editProfileTransactionValidation implements Validator{
	
	public static final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*(@gmail.com|@asu.edu)$";
	
	@Override
	public boolean supports(Class<?> paramClass) {
		
		return SSProfileTransaction.class.equals(paramClass);
	}
	
	@Override
	public void validate(Object obj, Errors errors){
		SSProfileTransaction transaction = (SSProfileTransaction) obj;
		
		if(StringUtils.isBlank(transaction.getFirstname()) || StringUtils.isEmpty(transaction.getFirstname()) || (Pattern.matches("[a-zA-Z]+", transaction.getFirstname()) == false)) {
			errors.rejectValue("firstName", "invalidFirstName", new Object[]{"'firstName'"}, "Invalid First Name");
		}
		
		if(transaction.getFirstname().length() > 45) {
			errors.rejectValue("firstName", "invalidFirstName", new Object[]{"'firstName'"}, "First Name length is too high");
		}
		if(StringUtils.isBlank(transaction.getLastname()) || StringUtils.isEmpty(transaction.getLastname()) || (Pattern.matches("[a-zA-Z]+", transaction.getLastname()) == false)) {
			errors.rejectValue("lastName", "invalidLastName", new Object[]{"'lastName'"}, "Invalid Last Name");
		}
		
		if(transaction.getLastname().length() > 45) {
			errors.rejectValue("lastName", "invalidLastName", new Object[]{"'lastName'"}, "Last Name length is too high");
		}
		
		if(transaction.getPhoneNumber().length() != 10 || (Pattern.matches("[0-9]+", transaction.getPhoneNumber()) == false)) {
			errors.rejectValue("phoneNumber", "invalidPhone", new Object[]{"'phoneNumber'"}, "Invalid Phone Number");
		}
		if(StringUtils.isBlank(transaction.getAddress()) ||  StringUtils.isEmpty(transaction.getAddress())){
	    	errors.rejectValue("address", "invalidAddress", new Object[]{"'address'"},"Invalid Address");
	    }
		if(transaction.getAddress().length() > 45){
	    	errors.rejectValue("address", "invalidAddress", new Object[]{"'address'"}, "Address length is too high") ;
	    }
		
	    if(StringUtils.isBlank(transaction.getCity()) ||  StringUtils.isEmpty(transaction.getCity()) || (Pattern.matches("[a-zA-Z]+", transaction.getCity()) == false)){
	    	errors.rejectValue("city", "invalidCity", new Object[]{"'city'"},"Invalid City");
	    }
	    if(transaction.getCity().length() > 45){
	    	errors.rejectValue("city", "invalidCity", new Object[]{"'city'"}, "City length is too high") ;
	    }
	    
	    if(StringUtils.isBlank(transaction.getZipcode()) ||  StringUtils.isEmpty(transaction.getZipcode()) || (Pattern.matches("[0-9]+", transaction.getZipcode()) == false)){
	    	errors.rejectValue("zipcode", "invalidZipCode", new Object[]{"'zipcode'"},"Invalid Zipcode");
	    }
	    if(transaction.getZipcode().length() > 45){
	    	errors.rejectValue("zipcode", "invalidZipCode", new Object[]{"'zipcode'"}, "ZipCode length is too high") ;
	    }
	    
	    if(StringUtils.isBlank(transaction.getState()) ||  StringUtils.isEmpty(transaction.getState()) ||(Pattern.matches("[a-zA-Z]+", transaction.getState()) == false)){
	    	errors.rejectValue("state", "invalidState", new Object[]{"'state'"},"Invalid State");
	    }
	    if(transaction.getState().length() > 45){
	    	errors.rejectValue("state", "invalidState", new Object[]{"'State'"}, "State length is too high") ;
	    }
	    if(StringUtils.isBlank(transaction.getCountry()) ||  StringUtils.isEmpty(transaction.getCountry()) ||(Pattern.matches("[a-zA-Z]+", transaction.getCountry()) == false)){
	    	errors.rejectValue("country", "invalidCountry", new Object[]{"'country'"},"Invalid Country");
	    }
	    if(transaction.getCountry().length() > 45){
	    	errors.rejectValue("country", "invalidCountry", new Object[]{"'Country'"}, "Country length is too high") ;
	    }
	    
		
	}

}
