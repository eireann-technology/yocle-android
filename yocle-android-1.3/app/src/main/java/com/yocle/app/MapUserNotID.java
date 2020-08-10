package com.yocle.app;

import android.content.Context;

public class MapUserNotID {

	public String shareRegIdWithAppServer(final Context context, final String qs) {
/*
		String result = "";
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("param", qs);
		try {
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
				httpCon = util.setUpHttpsConnection(context, Config.MAP_SERVER_URL);

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
*/
		return "";
	}
}
