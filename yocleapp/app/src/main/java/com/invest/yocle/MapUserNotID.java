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

public class MapUserNotID {

	public String shareRegIdWithAppServer(final Context context,
			final String qs) {

		String result = "";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("param", qs);
		try {
		/*
			URL serverUrl = null;
			try {
				serverUrl = new URL(Config.MAP_SERVER_URL);
			} catch (MalformedURLException e) {
				Log.e("AppUtil", "URL Connection Error: "
						+ Config.MAP_SERVER_URL, e);
				result = "Invalid URL: " + Config.MAP_SERVER_URL;
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
			byte[] bytes = body.getBytes();
//			HttpURLConnection httpCon = null;
			HttpsURLConnection httpCon = null;
			try {
//				httpCon = (HttpURLConnection) serverUrl.openConnection();
				httpCon = util.setUpHttpsConnection(context, Config.MAP_SERVER_URL);


	//			URL serverUrl = new URL("https://www.godaddy.com");
	//			httpCon = (HttpsURLConnection) serverUrl.openConnection();
				// Send post request

				httpCon.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());
				wr.writeBytes(body);
				wr.flush();
				wr.close();				

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
				
					Log.i("ADIAPONG", "Map user id result ="+html);
					
					result = "MapUser completed";
				} else {
					result = "Post Failure." + " Status: " + status;
				}				
				
			} finally {
				if (httpCon != null) {
					httpCon.disconnect();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
			result = "Post Failure. Error in mapping user with App Server.";
			Log.e("ADIAPONG", "Error in mapping user with App Server: " + e);
		}
		return result;
	}
}
