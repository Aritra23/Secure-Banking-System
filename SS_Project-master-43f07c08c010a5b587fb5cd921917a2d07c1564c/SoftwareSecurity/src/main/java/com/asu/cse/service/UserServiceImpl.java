package com.asu.cse.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import java.lang.System;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.dao.SSProfileTransactionDao;
import com.asu.cse.dao.UserDao;
import com.asu.cse.model.SSFundTransaction;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;
import com.asu.cse.dao.UserRoleDao;
import com.asu.cse.dao.SSUserManagementDao;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.lingala.zip4j.core.*;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.*;
import net.lingala.zip4j.util.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private SSFundTransactionService ssFundTransactionService;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private SSUserManagementDao	ssUserManagementDao;

	public SSFundTransactionService getSsFundTransactionService() {
		return ssFundTransactionService;
	}

	public void setSsFundTransactionService(SSFundTransactionService ssFundTransactionService) {
		this.ssFundTransactionService = ssFundTransactionService;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	@Autowired
	private SendEmailService sendEmail;

	public SendEmailService getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(SendEmailService sendEmail) {
		this.sendEmail = sendEmail;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public void addUser(SSUser user) {
		// TODO Auto-generated method stub
		userDao.addUser(user);
	}

	@Override
	@Transactional
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		try {
		userRoleDao.deleteUserRole(userId);
		} catch (Exception e) {
			java.lang.System.out.println("Service layer exception, no user role exists for" + userId);
		}
		try {
		ssUserManagementDao.deleteUserManagement(userId);
		} catch (Exception e) {
			java.lang.System.out.println("Service layer exception, no user management entry exists for" + userId);
		}
		try {
		userDao.deleteUser(userId);
		} catch (Exception e) {
			java.lang.System.out.println("Exception in service layer, no user exists for" + userId);
		}
	}

	@Override
	@Transactional
	public SSUser getUser(Integer userId) {
		// TODO Auto-generated method stub
		return userDao.getUser(userId);
	}

	@Override
	public SSUser addUserAfterSignUp(SSProfileTransaction ssProfileTransaction) {
		SSUser user = new SSUser();
		user.setFirstName(ssProfileTransaction.getFirstname());
		user.setLastName(ssProfileTransaction.getLastname());
		user.setUserName(ssProfileTransaction.getUserName());
		user.setPassword(ssProfileTransaction.getPassword());
		user.setAddress(ssProfileTransaction.getAddress());
		user.setCity(ssProfileTransaction.getCity());
		user.setState(ssProfileTransaction.getState());
		user.setZipcode(ssProfileTransaction.getZipcode().toString());
		user.setCountry("US");
		user.setEnabled(1);
		user.setEmail(ssProfileTransaction.getEmail());
		user.setPhoneNumber(ssProfileTransaction.getPhoneNumber().toString());
		user.setSocial(ssProfileTransaction.getSocial());
		user.setForgotPasswordEnabled((byte) 0);
		return userDao.addUser(user);

	}

	@Override
	public List<SSUser> getAllUsersByEditProfileRequests(List<SSProfileTransaction> ProfileTransaction) {
		List<SSUser> UserList = new ArrayList<SSUser>();
		SSUser temp;
		if (ProfileTransaction.isEmpty() || ProfileTransaction == null) {
			return null;
		}
		for (SSProfileTransaction Transaction : ProfileTransaction) {
			temp = userDao.getUser(Transaction.getUserId());
			if (temp != null) {
				UserList.add(temp);
			}
		}
		return UserList;
	}

	@Override
	public Integer editUserAfterApproval(Integer userId, SSProfileTransaction Transaction) {
		SSUser user = new SSUser();
		user.setFirstName(Transaction.getFirstname());
		user.setLastName(Transaction.getLastname());
		user.setUserName(Transaction.getUserName());
		user.setUserid(userId);
		user.setPassword(Transaction.getPassword());
		user.setAddress(Transaction.getAddress());
		user.setCity(Transaction.getCity());
		user.setState(Transaction.getState());
		user.setZipcode(Transaction.getZipcode().toString());
		user.setCountry(Transaction.getCountry());
		user.setEnabled(1);
		user.setEmail(Transaction.getEmail());
		user.setPhoneNumber(Transaction.getPhoneNumber().toString());
		user.setSocial(Transaction.getSocial());
		user.setForgotPasswordEnabled((byte) 0);
		SSUser response = (SSUser) userDao.updateUser(user);
		if (response == null)
			return -1;
		return 0;
	}

	@Override
	public List<SSUser> getUsersByRole(String role) {
		return userDao.getUsersByRole(role);
	}

	@Override
	public void forgotPassword(String email) {
		String temporaryPassword = generateRandomString();
		SSUser user = userDao.getUserByEmail(email);
		user.setForgotPasswordEnabled((byte) 1);
		user.setPassword(temporaryPassword);
		SSUser updatedUser = userDao.updateUser(user);
		String text = "Here is your temporary password:" + temporaryPassword;
		String subject = "[Pitchfork State Bank] Temporary password";
		sendEmail.sendEmailBasic(email, subject, text);

	}

	// REFERENCE:
	// http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string

	private String generateRandomString() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(60, random).toString(32);
	}

	@Override
	public boolean updatePassword(SSUser user, String newPassword) {
		user.setPassword(newPassword);
		// Disabing Forgot password enabled
		user.setForgotPasswordEnabled((byte) 0);
		SSUser updatedUser = userDao.updateUser(user);
		String text = "Your password has been updated.";
		String subject = "[Pitchfork State Bank] Password Update";
		sendEmail.sendEmailBasic(user.getEmail(), subject, text);
		return true;
	}

	// REFERENCE :
	// http://howtodoinjava.com/2014/07/29/create-pdf-files-in-java-itext-tutorial/
	// http://www.mysamplecode.com/2012/10/create-table-pdf-java-and-itext.html

	@Override
	public void emailStatement(Integer userId, String monthYear) {

		List<SSFundTransaction> fundTransactionsByMonth = ssFundTransactionService
				.getUserFundTransactionsByMonth(userId, monthYear);
		SSUser user = userDao.getUser(userId);
		Document doc = new Document();
		PdfWriter docWriter = null;

		try {
			Font font = new Font(FontFamily.TIMES_ROMAN, 12);
			Font headerFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
			try {
				try {
					docWriter = PdfWriter.getInstance(doc,
							new FileOutputStream("Bank_Statement_" + monthYear + ".pdf"));
					docWriter.setEncryption(user.getSocial().getBytes(), user.getSocial().getBytes(),
							PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
				} catch (DocumentException e) {
					
				}
			} catch (FileNotFoundException e) {
				
			}

			doc.addAuthor("PitchFork Bank");
			doc.addCreationDate();
			doc.addProducer();
			doc.addCreator("PitchFork Bank");
			doc.addTitle("PitchFork Bank Statement : " + monthYear);
			doc.setPageSize(PageSize.LETTER);
			doc.open();
			Paragraph paragraph = new Paragraph("");

			if (fundTransactionsByMonth.isEmpty() || fundTransactionsByMonth == null) {
				paragraph.add("No transactions available in the selected month.");
				try {
					doc.add(paragraph);
				} catch (DocumentException e) {
					
				}
				doc.close();
				docWriter.close();

			}

			else {

				float[] columnWidths = { .5f, 2f, 2f, 2f, 2f, 2f, 2f };
				PdfPTable table = new PdfPTable(columnWidths);
				table.setWidthPercentage(90f);

				insertCell(table, "Sl No", Element.ALIGN_RIGHT, 1, headerFont);
				insertCell(table, "Transaction Type", Element.ALIGN_LEFT, 1, headerFont);
				insertCell(table, "Source Account", Element.ALIGN_LEFT, 1, headerFont);
				insertCell(table, "Destination Account", Element.ALIGN_RIGHT, 1, headerFont);
				insertCell(table, "Amount", Element.ALIGN_RIGHT, 1, headerFont);
				insertCell(table, "Status", Element.ALIGN_RIGHT, 1, headerFont);
				insertCell(table, "Date", Element.ALIGN_RIGHT, 1, headerFont);
				table.setHeaderRows(1);

				for (int i = 0; i < fundTransactionsByMonth.size(); i++) {

					SSFundTransaction ssFundTransaction = fundTransactionsByMonth.get(i);
					String transactionType = ssFundTransaction.getTransactionType().split("_")[1];
					String sourceAccount = "";
					String destinationAccount = "";
					if (transactionType.equals("CREDIT")) {
						destinationAccount = String.valueOf(ssFundTransaction.getDestinationAccount());
					} else if (transactionType.equals("DEBIT")) {
						sourceAccount = String.valueOf(ssFundTransaction.getSourceAccount());
					} else {
						sourceAccount = String.valueOf(ssFundTransaction.getSourceAccount());
						destinationAccount = String.valueOf(ssFundTransaction.getDestinationAccount());
					}
					String amount = String.valueOf(ssFundTransaction.getAmount());
					String status = ssFundTransaction.getStatus();
					Timestamp timestamp = ssFundTransaction.getTimeStamp();
					insertCell(table, String.valueOf(i + 1), Element.ALIGN_RIGHT, 1, font);
					insertCell(table, transactionType, Element.ALIGN_RIGHT, 1, font);
					insertCell(table, sourceAccount, Element.ALIGN_RIGHT, 1, font);
					insertCell(table, destinationAccount, Element.ALIGN_RIGHT, 1, font);
					insertCell(table, amount, Element.ALIGN_RIGHT, 1, font);
					insertCell(table, status, Element.ALIGN_RIGHT, 1, font);
					insertCell(table,
							monthYear.split("-")[0] + " " + timestamp.getDay() + " " + monthYear.split("-")[1],
							Element.ALIGN_RIGHT, 1, font);

				}

				paragraph.add(table);
				try {
					doc.add(paragraph);
				} catch (DocumentException e) {
					
				}
				doc.close();
				docWriter.close();
			}

		} finally {
			if (doc != null) {
				doc.close();
			}
			if (docWriter != null) {
				docWriter.close();
			}
		}

		String to = user.getEmail();
		String subject = "[PitchFork State Bank] Statement :" + monthYear;
		String text = "Please find attached your bank statement. Please use your SSN to unlock the document.";
		sendEmail.sendEmailWithAttachment(to, subject, text, "Bank_Statement_" + monthYear + ".pdf");
		File file = new File("Bank_Statement_" + monthYear + ".pdf");
		if (file.exists()) {
			file.delete();
		}
	}

	private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

		PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
		cell.setHorizontalAlignment(align);
		cell.setColspan(colspan);
		if (text.trim().equalsIgnoreCase("")) {
			cell.setMinimumHeight(10f);
		}
		table.addCell(cell);

	}

	@Override
	public void downloadLogs(SSUser user) {
		String inputPath = System.getProperty("catalina.home") + "/logs";
		String ssn= user.getSocial();
		zipDirectory(inputPath,ssn.substring(ssn.length()-4)+user.getPassword());
		String subject = "[PitchFork State Bank] System Log files";
		String text = "Please find attached the system log files.";
		sendEmail.sendEmailWithAttachment(user.getEmail(), subject, text, System.getProperty("catalina.home")+"/logs.zip");
		File file = new File(System.getProperty("catalina.home")+"/logs.zip");
		if (file.exists()) {
			file.delete();
		}
	}
	// REFERENCE: https://windchill101.wordpress.com/2012/05/02/creating-a-zip-file-in-java-using-the-zip4j-library/ 
	private void zipDirectory(String directory, String password)
	{
		try {
			ZipFile folderToZip = new ZipFile(directory + ".zip");
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			parameters.setEncryptFiles(true);
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
			parameters.setPassword(password);
			folderToZip.addFolder(directory, parameters);
		} catch (ZipException e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	public List<SSUser> getAllUsersByUserName(String userNameSearch){
		 List<SSUser> UserList = userDao.getUsersByUserNameSearch(userNameSearch);
		
		return UserList;
	}

	@Override
	public void setEnabledBitAfterDeleteProfileSubmission(Integer userId, Integer enabled) {
		 userDao.setEnabledBitAfterDeleteProfileSubmission(userId, enabled);
	}
	
}
