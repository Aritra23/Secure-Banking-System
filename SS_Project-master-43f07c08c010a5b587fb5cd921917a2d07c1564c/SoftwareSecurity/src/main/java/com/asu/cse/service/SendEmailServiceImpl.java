package com.asu.cse.service;

import java.util.Date;
//https://github.com/upcrob/spring-security-otp/blob/master/src/main/java/com/upcrob/springsecurity/otp/EmailSendStrategy.java
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.asu.cse.dao.SSOTPDao;
import com.asu.cse.dao.UserDao;
import com.asu.cse.model.Account;
import com.asu.cse.model.SSFundTransaction;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;

@Service
@Configuration
@EnableAsync
public class SendEmailServiceImpl implements SendEmailService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private SSOTPDao ssotp;
	final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	@Async
	private void sendEmail(String to, int pin) {
		if (to == null)
			return;
		Session session;
		Properties props;
		final String username = "pitchforkstatebank@gmail.com";//
		final String password = "AsAmNsKkKgroup5";

		props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");

		session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(username));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
			msg.setSubject("[Pitchfork State Bank] OTP");
			msg.setText("Here is your OTP " + pin);
			msg.setSentDate(new Date());
			Transport.send(msg);
		} catch (MessagingException e) {
			return;
		}
		return;
	}

	@Override
	public boolean sendEmailUsingUserName(String userName) {
		SSUser user = userDao.getUserByUserName(userName);
		sendEmail(user.getEmail(), ssotp.getPin(user.getEmail()));
		return true;
	}

	@Override
	public boolean sendEmailUsingUserEmail(String email) {
		sendEmail(email, ssotp.getPin(email));
		return true;
	}

	@Override
	public boolean sendEmailUsingUserName(SSUser user) {
		sendEmail(user.getEmail(), ssotp.getPin(user.getEmail()));
		return true;
	}

	@Override
	public boolean validatePin(String email, int pin) {
		return ssotp.isValidPin(email, pin);
	}

	@Override
	public boolean validatePin(SSUser user, int pin) {
		return ssotp.isValidPin(user.getEmail(), pin);
	}

	@Override
	@Async
	public void sendEmailBasic(String to, String subject, String text) {
		if (to == null || text == null || subject == null)
			return;
		Session session;
		Properties props;
		final String from = "pitchforkstatebank@gmail.com";
		final String password = "AsAmNsKkKgroup5";

		props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");

		session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
			msg.setSubject(subject);
			msg.setText(text);
			msg.setSentDate(new Date());
			Transport.send(msg);
		} catch (MessagingException e) {
			return;
		}
		return;
	}

	@Override
	@Async
	public void sendEmailAccountCreation(SSProfileTransaction user, Account ss) {
		String subject, text;
		
		subject = "Account Created!!";
		text = "Welcome to Pitchfork State Bank. \n";
		text += "\nYour account creation request is approved by us";
		text += "\nYour Account Number is : " + ss.getAccountid();
		text += "\nAccount username is : " + user.getUserName();
		text += "\nAccount Type : " + ss.getAccounttype();
		text += "\nWe have also deposited 1000$ in your account";
		text += "\nThanks You!!";
		sendEmailBasic(user.getEmail(), subject, text);
	}

	@Override
	@Async
	public void sendAccountTransferUpdate(SSFundTransaction transaction, boolean isapproved, SSUser user) {
		String subject, text;
		
		if (isapproved)
			subject = "Transaction "+ transaction.getFundTransactionId() +" is Approved";
		else
			subject = "Transaction "+ transaction.getFundTransactionId() +" is Dispproved";
		text = "Hi "+ user.getUserName()+"\n\n";
		text += "You fund transaction "+ transaction.getFundTransactionId()+" has been approved by the bank \n";
		text += "Please login to your bank to check updated balance \n";
		text += "\nThanks You!!";
		sendEmailBasic(user.getEmail(), subject, text);		
	}

	@Override
	@Async
	public void sendFundApprovalRequest(SSFundTransaction transaction, SSUser user) {
		String subject, text;

		subject = "You have received a Transaction approval request";
		text = "Hi "+ user.getUserName()+"\n\n";
		text += "Please login to bank account to approve transaction#"+transaction.getFundTransactionId()+" request\n";
		text += "\nThanks You!!";
		sendEmailBasic(user.getEmail(), subject, text);
	}

	@Override
	@Async
	public void sendAccountProfileUpdate(SSProfileTransaction transaction, boolean isapproved, SSUser user) {
		String subject, text;
		
		if (isapproved)
			subject = "Transaction "+ transaction.getProfileTransactionId() +" is Approved";
		else
			subject = "Transaction "+ transaction.getProfileTransactionId() +" is Dispproved";
		text = "Hi "+ user.getUserName()+"\n\n";
		text += "You update profile transaction "+ transaction.getProfileTransactionId()+" has been approved by the bank \n";
		text += "Please login to your bank to check updated balance \n";
		text += "\nThanks You!!";
		sendEmailBasic(user.getEmail(), subject, text);
	}

	@Override
	@Async
	public void sendProfileApprovalRequest(SSProfileTransaction transaction, SSUser user) {
		String subject, text;
		
		subject = "Your Profile is Updated";
		text = "Hi "+ user.getUserName()+"\n\n";
		text += "Transaction#"+transaction.getProfileTransactionId()+" is created for change profile request\n";
		text += "\nThanks You!!";
		sendEmailBasic(user.getEmail(), subject, text);
	}

	@Override
	@Async
	public void sendInsufficientBalance(SSFundTransaction transaction, SSUser user) {
		String subject, text;
		
		subject = "Transaction Rejected Due To Insufficient Funds";
		text = "Hi "+ user.getUserName()+"\n\n";
		text += "Transaction#"+transaction.getFundTransactionId()+" is rejected due to insufficient funds\n";
		text += "\nThanks You!!";
		sendEmailBasic(user.getEmail(), subject, text);
	}

	@Override
	@Async
	public void sendFundTranferRequest(SSFundTransaction transaction, SSUser user) {
		String subject, text;
		
		subject = "New Transaction Created";
		text = "Hi "+ user.getUserName()+"\n\n";
		text += "Transaction#"+transaction.getFundTransactionId()+" is created by you\n";
		text += "\nThanks You!!";
		sendEmailBasic(user.getEmail(), subject, text);
	}

	@Override
	@Async
	public void sendEmailClientCertificate(SSUser user, String certificate) {
		String subject, text;

		subject = "Your Public Certificate\n";
		text = "Hi " + user.getUserName();
		sendEmailWithAttachment(user.getEmail(), subject, text, user.getSocial() + ".cert");		
	}
	
	@Override
	@Async
	public void sendEmailWithAttachment(String to, String subject, String text, String attachmentPath) {
		if (to == null || text == null || subject == null)
			return;
		Session session;
		Properties props;
		final String from = "pitchforkstatebank@gmail.com";
		final String password = "AsAmNsKkKgroup5";

		props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.transport.protocol", "smtp");

		session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});

		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
			msg.setSubject(subject);
			msg.setText("Please find the attachment.");
			msg.setSentDate(new Date());
			DataSource dataSource = new FileDataSource(attachmentPath);
			msg.setDataHandler(new DataHandler(dataSource));
			String[] parts = attachmentPath.split("/");
			String fileName = parts[parts.length-1];
			msg.setFileName(fileName);
			Transport.send(msg);
		} catch (MessagingException e) {
			return;
		}
		return;
		
	}
}
