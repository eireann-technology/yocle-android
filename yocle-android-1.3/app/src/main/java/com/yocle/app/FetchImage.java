package com.yocle.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.webkit.CookieManager;

import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by pong2 on 8/4/2015.
 */
public class FetchImage extends AsyncTask<String, Integer, Integer> {

    private BitmapCallback caller;
    private Bitmap bitmap;
    private Context context;

    public FetchImage(Context context, BitmapCallback caller){
        this.caller=caller;
        this.context = context;
    }
    protected Integer doInBackground(String... inputs) {
        String uri = inputs[0];
       // uri = "http://dev.adiai.com:8442/people/m10.jpg";
        try {
            CookieManager cookieManager = CookieManager.getInstance();
            String cookie = cookieManager.getCookie(new URL(uri).getHost());

// Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(
                                java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
            };

// Install the all-trusting trust manager
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
            }





//            HttpsURLConnection conn = util.setUpHttpsConnection(context, uri);
            URL url = new URL(uri);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            if(cookie!=null) conn.setRequestProperty("Cookie",cookie);
//            bitmap = BitmapFactory.decodeStream(new URL(uri).openConnection().getInputStream());
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            return 1;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected void onPostExecute(Integer rv) {
        if(rv>0) {
            caller.onTaskDone(bitmap);
        }

    }
}

