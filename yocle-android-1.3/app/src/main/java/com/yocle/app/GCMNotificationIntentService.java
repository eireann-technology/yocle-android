package com.yocle.app;

import java.net.URLDecoder;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import android.util.Log;

// import com.google.android.gms.gcm.GoogleCloudMessaging;


public class GCMNotificationIntentService extends IntentService {

/*
    public void sendBroadcastNotification(Bundle extras) {

        Intent intentBroadcast = new Intent(BROADCAST_MESSAGE_NAME);
        intentBroadcast.putExtra(CoreConstants.EXTRA_INTENT_MSG_ID,
                mIntentMsgId);

        sendBroadcast(intentBroadcast);
    }
*/
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	NotificationCompat.Builder builder;

	public GCMNotificationIntentService() {
		super("GcmIntentService");
	}

	public static final String TAG = "yocle";

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle extras = intent.getExtras();
		if (extras.isEmpty()) {
			return;
		}
/*
		try {
			GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
			String messageType = gcm.getMessageType(intent);

			Log.i("yoclepong", "YYY");

			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {

				sendNotification("Send error: " + extras.toString());

			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {

				sendNotification("Deleted messages on server: " + extras.toString());

			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

				//Log.i("yoclepong", "zzz");
				sendNotification(URLDecoder.decode(extras.getString(Config.MESSAGE_KEY), "UTF-8"));
				//Log.i(TAG, "Received: " + extras.toString());
			}

			GcmBroadcastReceiver.completeWakefulIntent(intent);

		} catch(Exception e) {
			e.printStackTrace(System.out);
			Log.e("PongPong", "Error in receiving message", e);
		}
*/
	}

	///////////////////////////////////////////////////////////
	
	private void sendNotification(String msg) {

		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		Log.i("yoclepong", "XXX>"+msg);
		String[] m = msg.split(":::");
		
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra("message", m[2]);
		i.putExtra("title", m[0]);
//		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				i, PendingIntent.FLAG_UPDATE_CURRENT);

		Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle(m[1])
				.setAutoCancel(true)
				.setContentIntent(contentIntent)
				.setStyle(new NotificationCompat.BigTextStyle().bigText(m[0]))
				.setContentText(m[1])
				.setSound(soundUri);

		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}
}
