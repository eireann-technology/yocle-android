package com.invest.yocle;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by pong2 on 8/4/2015.
 */
public class JsonHttpUtil extends AsyncTask<String, Integer, Integer> {

    private FragmentJsonCallback caller;
    private String result;
    private int action;
    private Context context;

    public JsonHttpUtil(FragmentJsonCallback caller, int action){
        this.caller=caller;
        this.action = action;
    }
    public JsonHttpUtil(Context context, FragmentJsonCallback caller, int action){
        this.context = context;
        this.caller=caller;
        this.action = action;
    }
    protected Integer doInBackground(String... inputs) {
        String url = inputs[0];
        String cookie = inputs[1];
        String qs = inputs[2];
                try {
                    URL u;
/*
                    if(!qs.isEmpty()) u = new URL(url+"?"+qs);
                    else u = new URL(url);

                    URLConnection uc = u.openConnection();
*/

                    HttpsURLConnection uc = util.setUpHttpsConnection(context, ((!qs.isEmpty())?url+"?"+qs:url));
                    if(cookie!=null) uc.setRequestProperty("Cookie", cookie);
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
                        // if cancel() is called, leave the loop early
                    if (isCancelled()) {
                    }

                    result = resultBuilder.toString();
                    totalSize = resultBuilder.length();
                    return totalSize;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                }
                return 0;
            }

            protected void onPostExecute(Integer rv) {
                if(rv>0) {
                    if(action==1 || action==2 || action==3 || action==4) caller.onTaskDone(result);
                }

            }
}

