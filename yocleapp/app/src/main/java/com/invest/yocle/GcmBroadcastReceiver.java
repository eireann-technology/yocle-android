package com.invest.yocle;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if(isWebViewActivityRunning(context) && same_msg_user_onshow(context, intent)) return;
			intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);		
			ComponentName comp = new ComponentName(context.getPackageName(),
					GCMNotificationIntentService.class.getName());
			startWakefulService(context, (intent.setComponent(comp)));
	        setResultCode(Activity.RESULT_OK);			        
	}

	public boolean same_msg_user_onshow(Context context, Intent intent) {
		SharedPreferences prefs = context.getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String uid = prefs.getString("msg_user_onshow", "");
		if(uid==null || uid.isEmpty()) uid = null;

		if(uid==null) return false;

		try {
			String msg = URLDecoder.decode(intent.getExtras().getString(Config.MESSAGE_KEY), "UTF-8");
            if(msg.indexOf(uid)>=0) {
				return true;
			}
			else return false;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isWebViewActivityRunning(Context context) {
		String[] activePackages;
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
			activePackages = getActivePackages(context);
		} else {
			activePackages = getActivePackagesCompat(context);
		}
		if (activePackages != null) {
			for (String activePackage : activePackages) {
				if (activePackage.equals("com.invest.yocle")) {
					return true;
				}
			}
		}
		return false;
	}



	String[] getActivePackagesCompat(Context context) {
		ActivityManager mActivityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		final List<ActivityManager.RunningTaskInfo> taskInfo = mActivityManager.getRunningTasks(1);
		final ComponentName componentName = taskInfo.get(0).topActivity;
		final String[] activePackages = new String[1];
		activePackages[0] = componentName.getPackageName();
		return activePackages;
	}

	String[] getActivePackages(Context context) {
		ActivityManager mActivityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		final Set<String> activePackages = new HashSet<String>();
		final List<ActivityManager.RunningAppProcessInfo> processInfos = mActivityManager.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
			if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				activePackages.addAll(Arrays.asList(processInfo.pkgList));
			}
		}
		return activePackages.toArray(new String[activePackages.size()]);
	}

}
