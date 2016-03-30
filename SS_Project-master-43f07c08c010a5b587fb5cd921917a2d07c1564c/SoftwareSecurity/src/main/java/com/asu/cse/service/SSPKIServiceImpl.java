package com.asu.cse.service;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;

import javax.crypto.Cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.asu.cse.controller.UserController;
import com.asu.cse.model.SSUser;
import com.sun.mail.handlers.text_html;

import sun.security.x509.*;
import org.apache.commons.codec.binary.Base64;

@Service
public class SSPKIServiceImpl implements SSPKIService {
	@Autowired
	protected SendEmailService sendEmail;
	public static final String SERVERKEYDIRNAME = "";
	public static final String CLIENTKEYDIRNAME = "";
	
	// Reference: http://snipplr.com/view/18368/
	public void SaveKeyPair(String filename, KeyPair keyPair) throws IOException {
		PublicKey publicKey = keyPair.getPublic();

		// Store Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
		File f = new File(filename);
		FileOutputStream fos = new FileOutputStream(f);
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();

	}

	public KeyPair LoadKeyPair(String filename, String algorithm)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		// Read Public Key.
		File filePublicKey = new File(filename);
		FileInputStream fis = new FileInputStream(filename);
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();

		// Read Private Key.
		File filePrivateKey = new File(filename + ".pvt");
		fis = new FileInputStream(filename + ".pvt");
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();

		// Generate KeyPair.
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		return new KeyPair(publicKey, privateKey);
	}

	public PublicKey loadPubicKeyPair(String filename, String algorithm)
			throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		// Read Public Key.
		File filePublicKey = new File(filename);
		FileInputStream fis = new FileInputStream(filename);
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();

		// Generate KeyPair.
		KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		
		return publicKey;
	}
	
	public String encrypt(byte[] textBytes, int len, KeyPair pair) throws Exception {
		Cipher rsaCipher = Cipher.getInstance("RSA");

		// get the public key
		PublicKey pk = pair.getPublic();
		// Initialize the cipher for encryption. Use the public key.
		rsaCipher.init(Cipher.ENCRYPT_MODE, pk);
		// Perform the encryption using doFinal
		byte[] encByte = rsaCipher.doFinal(textBytes, 0, len);
		// converts to base64 for easier display.
		byte[] base64Cipher = Base64.encodeBase64(encByte);
		return new String(base64Cipher);
	}

	public String decrypt(byte[] cipherBytes, int len, KeyPair pair) throws Exception {
		Cipher rsaCipher = Cipher.getInstance("RSA");
		// get the public key
		PrivateKey pvk = pair.getPrivate();
		// Initialize the cipher for encryption. Use the public key.
		rsaCipher.init(Cipher.DECRYPT_MODE, pvk);
		// Perform the encryption using doFinal
		byte[] decByte = rsaCipher.doFinal(cipherBytes);
		return new String(decByte);
	}

	public Date addDays(Date d, int days) {
		d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
		return d;
	}

	/**
	 * Create a self-signed X.509 Certificate
	 * 
	 * @param dn
	 *            the X.509 Distinguished Name, eg "CN=Test, L=London, C=GB"
	 * @param pair
	 *            the KeyPair
	 * @param days
	 *            how many days from now the Certificate is valid for
	 * @param algorithm
	 *            the signing algorithm, eg "SHA1withRSA" Reference:
	 *            http://bfo.com/blog/2011/03/08/
	 *            odds_and_ends_creating_a_new_x_509_certificate.html
	 */
	@SuppressWarnings("restriction")
	X509Certificate generateCertificate(String dn, KeyPair pair, int days, String algorithm)
			throws GeneralSecurityException, IOException {
		PrivateKey privkey = pair.getPrivate();
		X509CertInfo info = new X509CertInfo();
		Date from = new Date();
		Date from1 = new Date();
		Date to = addDays(from1, 10000);
		CertificateValidity interval = new CertificateValidity(from, to);
		BigInteger sn = new BigInteger(64, new SecureRandom());
		X500Name owner = new X500Name(dn);
		info.set(X509CertInfo.VALIDITY, interval);
		info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(sn));
		info.set(X509CertInfo.SUBJECT, new CertificateSubjectName(owner));
		info.set(X509CertInfo.ISSUER, new CertificateIssuerName(owner));
		info.set(X509CertInfo.KEY, new CertificateX509Key(pair.getPublic()));
		info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
		AlgorithmId algo = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
		info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algo));
		X509CertImpl cert = new X509CertImpl(info);
		cert.sign(privkey, algorithm);
		algo = (AlgorithmId) cert.get(X509CertImpl.SIG_ALG);
		info.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algo);
		cert = new X509CertImpl(info);
		cert.sign(privkey, algorithm);
		return cert;
	}

	private void writeToFile(String fPath, String data) {

		File f = new File(fPath);
		//f.getParentFile().mkdirs();
		try {
			FileOutputStream fos1 = new FileOutputStream(f);
			fos1.write(data.getBytes());
			fos1.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}

	X509Certificate generatePKI(SSUser user) throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(1024);
		KeyPair keyPair = keyPairGenerator.genKeyPair();

		SaveKeyPair(getClientPublicKeyPath(user), keyPair);

		X509Certificate cert = generateCertificate("CN=Bank, L=AX, C=GB", keyPair, 0, "SHA1withRSA");

		try {
			FileOutputStream fos1 = new FileOutputStream(user.getSocial() + ".cert");
			String op = "-----BEGIN CERTIFICATE-----\n";
			fos1.write(op.getBytes());
			op = DatatypeConverter.printBase64Binary(cert.getEncoded());
			fos1.write(op.getBytes());
			op = "\n-----END CERTIFICATE-----";
			fos1.write(op.getBytes());
			fos1.close();

			/*
			 * String certEmail = "-----BEGIN CERTIFICATE-----\n"; certEmail +=
			 * DatatypeConverter.printBase64Binary(cert.getEncoded()); certEmail
			 * += "\n-----END CERTIFICATE-----"; System.out.println(
			 * "Sending Certificate");
			 */
			sendEmail.sendEmailClientCertificate(user, "");
		} catch (CertificateEncodingException e) {
			return null;
		}
		return cert;
	}

	@Override
	public String generateClientCertificate(SSUser user) {
		try {
			X509Certificate cert = generatePKI(user);
			return cert.toString();
		} catch (Exception e) {
			return null;
		}
	}

	private String getClientPublicKeyPath(SSUser user) {
		return user.getSocial() + ".pub";
	}

	@Override
	public boolean verifyClientCertificate(SSUser user, String certificate) {
		try {
			CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
			InputStream in = new ByteArrayInputStream(certificate.getBytes());
			X509Certificate cert = (X509Certificate) certFactory.generateCertificate(in);
			PublicKey pubkey = loadPubicKeyPair(getClientPublicKeyPath(user), "RSA");
			cert.verify(pubkey);
			return true;
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		} catch (SignatureException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		}
		return false;
	}

	public static void main(String ag[]) {
		SSPKIServiceImpl ser = new SSPKIServiceImpl();
		SSUser user = new SSUser();
		user.setEmail("vasani.ashwin@gmail.com");
		user.setSocial("1234567812");
		/*
		 * String test = "Ashwin"; try { KeyPair keypair =
		 * ser.LoadKeyPair(user.getSocial(), "RSA"); String cipher =
		 * ser.encrypt(test.getBytes(), test.length(), keypair);
		 * System.out.println(cipher); String plain =
		 * ser.decrypt(Base64.decodeBase64(cipher), cipher.length(), keypair);
		 * System.out.println(plain); } catch (NoSuchAlgorithmException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (InvalidKeySpecException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

	}
}
