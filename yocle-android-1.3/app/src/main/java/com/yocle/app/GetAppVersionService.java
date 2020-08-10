package com.yocle.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.yocle.app.MainActivity.MyWebReceiver;


import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class GetAppVersionService  extends IntentService{

    private static final String LOG_TAG = "MyWebService";
    public static final String REQUEST_STRING = "myRequest";
    public static final String RESPONSE_STRING = "myResponse";
    public static final String RESPONSE_MESSAGE = "myResponseMessage";

    private String URL = null;
    private static final int REGISTRATION_TIMEOUT = 3 * 1000;
    private static final int WAIT_TIMEOUT = 30 * 1000;

    public GetAppVersionService() {
        super("GetAppVersionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        String requestString = intent.getStringExtra(REQUEST_STRING);
        Log.v(LOG_TAG, requestString);
        String responseMessage = "";

        try {
            java.net.URL url = new URL(requestString);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setConnectTimeout(30000);
            uc.setReadTimeout(30000);

            int totalSize = 0;
            StringBuilder resultBuilder = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(uc.getInputStream());BufferedReader in = new BufferedReader(reader);

            String resultPiece;
            while ((resultPiece = in.readLine()) != null) {
                resultBuilder.append(resultPiece);
            }
            in.close();

            responseMessage = resultBuilder.toString();

        }catch (Exception e) {
            Log.w("HTTP4:",e );
            responseMessage = e.getMessage();
        }


        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(MyWebReceiver.PROCESS_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(RESPONSE_MESSAGE, responseMessage);
        sendBroadcast(broadcastIntent);
    }
}
