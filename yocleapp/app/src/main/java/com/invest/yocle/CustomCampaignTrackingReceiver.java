package com.invest.yocle;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


/**
 * This is a custom Google Analytics Tracking receiver for INSTALL_REFERRER
 * intents. The purpose of this custom receiver is:
 *
 *  - Catch the campaign data before it is delivered to GA Campaign
 *    Tracking receiver.
 *  - Forward the intent to Google Analytics Tracking receiver for
 *    normal behaviour.
 *
 *  The campaign URL can be made using the URL:
 *
 *      https://developers.google.com/analytics/devguides/collection/android/v4/campaigns?hl=es#google-play-url-builder
 *
 *      ...an example (the URL in the "referrer" url parameter is url encoded):
 *
 *      https://play.google.com/store/apps/details?id=es.javocsoft.basetest
 *                     &referrer=utm_source%3Dfacebook
 *                               %26utm_medium%3Dbanner
 *                               %26utm_content%3Dbanner1
 *                               %26utm_campaign%3DcampaignOne
 *
 *      The receiver can be test by using the ADN command:
 *
 *          adb shell am broadcast
 *                  -a com.android.vending.INSTALL_REFERRER
 *                  -n es.javocsoft.basetestapp/es.javocsoft.basetestapp.CustomCampaignTrackingReceiver
 *                  --es "referrer" "utm_source%3Dfacebook%26utm_medium%3Dbanner%26utm_content%3Dbanner1%26utm_campaign%3DcampaignOne"
 *
 *      ..if the test goes well an output like this should be seen:
 *
 *          Broadcasting: Intent { act=com.android.vending.INSTALL_REFERRER cmp=es.javocsoft.basetestapp/.CustomCampaignTrackingReceiver (has extras) }
 *          Broadcast completed: result=0
 *

 adb shell am broadcast -a com.android.vending.INSTALL_REFERRER -n com.invest.com.edu.yocle/com.invest.com.edu.yocle.CustomCampaignTrackingReceiver --es  "referrer" "utm_source%3Dfacebook%26utm_medium%3Dbanner%26utm_content%3D18%26utm_campaign%3DcampaignOne"


 */
public class CustomCampaignTrackingReceiver extends BroadcastReceiver {
    String TAG = "ADIAPONG";

    @Override
    public void onReceive(Context context, Intent intent) {

        String referrerString = intent.getStringExtra("referrer");
        referrerString = URLDecoder.decode(referrerString);
        //sending to mixpanel

        Log.i(TAG, "AAA on receive called");
        try {
            JSONObject props = new JSONObject();
            props.put("utm_source", splitQuery(referrerString)
                    .get("utm_source"));
            props.put("utm_medium", splitQuery(referrerString)
                    .get("utm_medium"));
            props.put("utm_content", splitQuery(referrerString)
                    .get("utm_content"));
            if (splitQuery(referrerString).get("utm_campaign") != null) {
                props.put("utm_campaign",
                        splitQuery(referrerString).get("utm_campaign"));
            }

            JsonHttpUtil json = new JsonHttpUtil(new FragmentJsonCallback() {
                @Override
                public void onTaskDone(String rv) {
                    int i=0;
                    i+=1;
                }
            }, 4);



            final TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);

            final String tmDevice, tmSerial, androidId;
            tmDevice = "" + tm.getDeviceId();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

            UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
            String deviceId = deviceUuid.toString();
            Log.i(TAG, "device id = "+deviceId);
        //    Log.i(TAG, "param="+"source="+props.getString("utm_source")+"&medium="+props.getString("utm_medium")+"&campgign="+props.getString("utm_campaign")+"&did="+props.getString("utm_content")+"&d=A");
            json.execute(Config.HTTP_SERVER_ROOT + "/invest/servlet/shareresponse", null, "source="+props.getString("utm_source")+"&medium="+props.getString("utm_medium")+"&campgign="+props.getString("utm_campaign")+"&content="+props.getString("utm_content")+"&d=A&did="+deviceId);



        }
        catch(Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public Map<String, String> splitQuery(String url)
            throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String[] pairs = url.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"),
                    URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }


}