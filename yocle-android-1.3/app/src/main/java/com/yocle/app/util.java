package com.yocle.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.security.cert.Certificate;
import java.security.*;
//import java.security.Certificate;
//import java.security.Key;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.io.*;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
//import javax.xml.bind.DatatypeConverter;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class util {
  static final byte[] keyValue = new byte[] { 'A', 'm', '1', '0', 'K', 's', 'P', 'w', 'm', 'E', '8', '8', 'n', 'Y', 'H', 'S' };
//	static SecretKey secretKey;		
	
//	static {
//		secretKey = (SecretKey)generateKey(keyValue);		
//	}
	
	
	public static String encrypt(String plainText) {
	 try {
		    if(plainText.equals("")) return "";
		 
			Cipher cipher = Cipher.getInstance("AES");
			
			SecretKey secretKey = (SecretKey)generateKey(cipher, keyValue);
			
			
		 	byte[] plainTextByte = plainText.getBytes();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] encryptedByte = cipher.doFinal(plainTextByte);
//			String encryptedText = DatatypeConverter.printHexBinary(encryptedByte);
			String encryptedText = toHexString(encryptedByte);			
			return encryptedText;
	 }
	 catch(Exception e) { return null; }
	}
	
	public static Key generateKey(Cipher cipher, byte[] keyValue) {
	  try {
        Key key = new SecretKeySpec(keyValue, "AES");
        return key;
		}
		catch(Exception e) { return null; }
  }
	
	
	public static String toHexString( byte[] bytes )
	  {
	      StringBuffer sb = new StringBuffer( bytes.length*2 );
	      for( int i = 0; i < bytes.length; i++ )
	      {
	          sb.append( toHex(bytes[i] >> 4) );
	          sb.append( toHex(bytes[i]) );
	      }

	      return sb.toString();
	  }
	  private static char toHex(int nibble)
	  {
	      final char[] hexDigit =
	      {
	          '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
	      };
	      return hexDigit[nibble & 0xF];
	  }

	  public static String MD5(String str) {
		byte[] defaultBytes = str.getBytes();
		try{
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(defaultBytes);
			byte messageDigest[] = algorithm.digest();

			return toHexString(messageDigest);
		}
		catch(NoSuchAlgorithmException nsae){ return null; }
	}

    public static String urlHash(String url) {
		String u = MD5(url);
		String prefix = "0";
		if(url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT + "/member/")>0) {
			prefix = "m";
		}
		else if(url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT + "/notification.html") ==0) {
			prefix = "n";
		}
		else if(url.toLowerCase().equals(Config.HTTPS_SERVER_ROOT+"/") || url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/index.html")==0) {
			prefix = "h";
		}
		else if(url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/weeklyreport.html")==0) {
			prefix = "w";
		}
		else if(url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/a_reader.html")==0 || url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/b_adiaiwords.html")==0 ||
				url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/c_newuser.html")==0 || url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/d_famouspeople.html")==0 ||
				url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/e_smallstory.html")==0 || url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/f_hint.html")==0 ||
				url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/growthconfirm.html")==0 || url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/growthobserve.html")==0 ||
				url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/interestconfirm.html")==0 || url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/interestobserve.html")==0 ||
				url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/research.html")==0 || url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/weeklymessage.html")==0 ||
				url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/property.html")==0 || url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/portfolio.html")==0 ||
				url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/classprediction.html")==0 || url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/facebook_prediction.html")==0)
		{
			prefix = "r";
		}
		else if(url.toLowerCase().indexOf(Config.HTTPS_SERVER_ROOT+"/portfolio.html")==0) {
			prefix = "p";
		}

		return prefix+u;
	}

	@SuppressLint("SdCardPath")
	public static HttpsURLConnection setUpHttpsConnection(Context mainactivitycontext, String urlString) {
		try {
		/*
			CertificateFactory cf = CertificateFactory.getInstance("X.509");

			InputStream caInput = new BufferedInputStream(mainactivitycontext.getAssets().open("adiai.crt"));
			Certificate ca = cf.generateCertificate(caInput);
			System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());

			// Create a KeyStore containing our trusted CAs
			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", ca);

			// Create a TrustManager that trusts the CAs in our KeyStore
			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
			tmf.init(keyStore);

			// Create an SSLContext that uses our TrustManager
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, tmf.getTrustManagers(), null);
*/
			// Tell the URLConnection to use a SocketFactory from our SSLContext
			URL url = new URL(urlString);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
/*
			HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			urlConnection.setHostnameVerifier(hostnameVerifier );

			if (urlConnection != null) {
				SSLSocketFactory factory = context.getSocketFactory();
				urlConnection.setSSLSocketFactory(context.getSocketFactory());
			}
*/
			return urlConnection;
		}
/*
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}
*/
		catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			String err = ex.toString();
			err = err + "1";
			Log.e("ADIAPONG", "Failed to establish SSL connection to server: " + ex.toString());
		}
		return null;
	}


	@SuppressLint("SdCardPath")
	public static HttpsURLConnection setUpHttpsConnection1(Context mainactivitycontext, String urlString) {
		try {

			CertificateFactory cf = CertificateFactory.getInstance("X.509");

			InputStream caInput = new BufferedInputStream(mainactivitycontext.getAssets().open("adiai.crt"));
			Certificate ca = cf.generateCertificate(caInput);
			System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());

			// Create a KeyStore containing our trusted CAs
			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			keyStore.setCertificateEntry("ca", ca);

			// Create a TrustManager that trusts the CAs in our KeyStore
			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
			tmf.init(keyStore);

			// Create an SSLContext that uses our TrustManager
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, tmf.getTrustManagers(), null);

			// Tell the URLConnection to use a SocketFactory from our SSLContext
			URL url = new URL(urlString);
			HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

			HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
			urlConnection.setHostnameVerifier(hostnameVerifier );

			if (urlConnection != null) {
				SSLSocketFactory factory = context.getSocketFactory();
				urlConnection.setSSLSocketFactory(context.getSocketFactory());
			}

			return urlConnection;
		}

		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			String err = ex.toString();
			err = err + "1";
			Log.e("ADIAPONG", "Failed to establish SSL connection to server: " + ex.toString());
		}
		return null;
	}


}
