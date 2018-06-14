package com.invest.yocle;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;

public class ShareExternalServer {

	public String shareRegIdWithAppServer(final Context context,
			final String regId) {

		String result = "";
		boolean bSuccess = false;
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("nid", regId);
		paramsMap.put("device", "android");
		try {
		/*
			URL serverUrl = null;
			try {
				serverUrl = new URL(Config.APP_SERVER_URL);
			} catch (MalformedURLException e) {
				Log.e("AppUtil", "URL Connection Error: "
						+ Config.APP_SERVER_URL, e);
				result = "Invalid URL: " + Config.APP_SERVER_URL;
			}
		*/
			StringBuilder postBody = new StringBuilder();
			Iterator<Entry<String, String>> iterator = paramsMap.entrySet()
					.iterator();

			while (iterator.hasNext()) {
				Entry<String, String> param = iterator.next();
				postBody.append(param.getKey()).append('=')
						.append(param.getValue());
				if (iterator.hasNext()) {
					postBody.append('&');
				}
			}
			String body = postBody.toString();
			Log.i("ADIAPONG", "PONGPONG notregister qs="+body);
			byte[] bytes = body.getBytes();
	//		HttpURLConnection httpCon = null;
			HttpsURLConnection httpCon = null;
			try {
//				httpCon = (HttpURLConnection) serverUrl.openConnection();
				httpCon = util.setUpHttpsConnection(context, Config.APP_SERVER_URL);
				httpCon.setRequestMethod("POST");
			//	httpCon.setRequestProperty("User-Agent", USER_AGENT);
				httpCon.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		 
				// Send post request
				httpCon.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());
				wr.writeBytes(body);
				wr.flush();
				wr.close();				
				
/*
				httpCon.setDoOutput(true);
				httpCon.setUseCaches(false);
				httpCon.setFixedLengthStreamingMode(bytes.length);
				httpCon.setRequestMethod("POST");
				httpCon.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=UTF-8");
				OutputStream out = httpCon.getOutputStream();
				out.write(bytes);
				out.close();
*/
				int status = httpCon.getResponseCode();
				if (status == 200) {
					// open the stream and put it into BufferedReader
					BufferedReader br = new BufferedReader(
		                               new InputStreamReader(httpCon.getInputStream()));
				    String line = "";
				    String html = "";
				    do {
				      line = br.readLine();
				      if(line!=null) html += line;
				    } while (line != null);					
									
					Log.i("ADIAPONG", "Register GCM ID result="+html);
					
					if(html.indexOf("Registration successfully done")>=0) bSuccess = true;
					
					result = "RegId shared with Application Server. RegId: "
							+ regId;
				} else {
					result = "Post Failure." + " Status: " + status;
				}				
				
			} finally {
				if (httpCon != null) {
					httpCon.disconnect();
				}
			}

		} catch (IOException e) {
			result = "Post Failure. Error in sharing with App Server.";
			Log.e("AppUtil", "Error in sharing with App Server: " + e);
		}
		
		if(bSuccess) return "SUCCESS";
		else return "ERROR";
		
//		return result;
	}
}
