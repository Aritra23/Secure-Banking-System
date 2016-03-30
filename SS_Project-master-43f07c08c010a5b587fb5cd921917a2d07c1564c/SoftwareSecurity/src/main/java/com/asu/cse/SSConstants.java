package com.asu.cse;

public class SSConstants {

	/* Transaction constants */
	public static final String CUSTOMER_DEBIT_TRANSACTION = "CUST_DEBIT";
	public static final String CUSTOMER_CREDIT_TRANSACTION = "CUST_CREDIT";
	public static final String CUSTOMER_TRANSFER_TRANSACTION = "CUST_TRANSFER";
	public static final String CUSTOMER_SIGNUP_TRANSACTION = "CUST_Signup";
	public static final String CUSTOMER_UPDATE_TRANSACTION = "CUST_Update";
	public static final String INTERNAL_EMPLOYEE_SIGNUP = "Internal_Signup";
	public static final String INTERNAL_EMPLOYEE_UPDATE = "INT_Update";
	public static final String MERCHANT_SIGNUP = "MER_Signup";
	public static final String MERCHANT_UPDATE = "MER_Update";
	public static final String CUSTOMER_PROFILE_DELETE_TRN = "CUST_PROFILE_DELETE";
	public static final String MERCHANT_PROFLE_DELETE_TRN = "MER_PROFILE_DELETE";
	public static final String MERCHANT_REQUEST_MONEY = "MER_REQUEST_FUNDS";
	
	/*  Critical Transactions constants */
	public static final float CRITICAL_TRANSACTION_LIMIT = 1000;
	
	/* User Role constants */
	public static final String EXTERNAL_CUSTOMER_ROLE = "EXT_CUSTOMER";
	public static final String EXTERNAL_MERCHANT_ROLE = "EXT_MERCHANT";
	public static final String INTERNAL_REGULAR_EMPLOYEE_ROLE = "INT_REMP";
	public static final String INTERNAL_MANAGER_ROLE = "INT_MEMP";
	public static final String INTERNAL_ADMIN_ROLE = "INT_AEMP";
	
	/* Status constants */
	public static final String PENDING = "Pending";
	public static final String APPROVED = "Approved";
	public static final String REJECTED = "Rejected";
	
	/* Account Type */
	public static final String SAVING = "SAVING";
	public static final String CHECKING = "CHECKING";
	
	/* Captcha */
	public static final String CAPTCHAURL = "https://www.google.com/recaptcha/api/siteverify";
	public static final String CAPTCHASECRET = "6Ldllw8TAAAAAMgbAn81pmB3EjIJ1_ujBTy9fnfF";
	public static final Integer MAX_ATTEMPTS = 3;
	
}
