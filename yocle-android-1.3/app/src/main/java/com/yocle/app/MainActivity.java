package com.yocle.app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
//import android.support.v7.widget.PopupMenu;
//import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;

import android.os.AsyncTask;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.util.UUID;

import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//import com.yalantis.contextmenu.R;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

//import com.google.android.gms.gcm.GoogleCloudMessaging;

//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.common.ConnectionResult;

// http://sampleprogramz.com/android/serviceactivity.php

// https://github.com/firebase/quickstart-android/blob/5d87d878ea54daa2a3987d00724af28d605eab1d/messaging/app/src/main/java/com/google/firebase/quickstart/fcm/java/MainActivity.java#L101-L118
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.quickstart.fcm.R;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.quickstart.fcm.R;


public class MainActivity extends AppCompatActivity implements
        OnMenuItemClickListener, OnMenuItemLongClickListener
{
    IntentFilter intentFilter;
    public boolean m_loggedin = false;

    static String titles[] = null;
    static String urls[] = {"index.php?platform=android"};
	AssetManager assetmanager = null;
	MainActivity mainactivity = null;
	String TAG = "ADIAPONG";

    String[] menutitles = {"123","123","123","123","123","123","123"};
    TypedArray menuIcons;
    String[] pageUrl = {"anchortest.php", "dev/login.php", "dev/login.php", "dev/login.php", "dev/login.php", "dev/login.php", "dev/login.php"};
    String[] anchors = {"","","","","","","","","","","","","","","","","",""};

     // nav drawer title
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private List<RowItem> rowItems;
    private CustomAdapter adapter;
    
    private LinearLayout area1 = null;
    private boolean bShowLoginMenu = true;
    private boolean bShowRegisterMenu = true;
    private boolean bShowLogoutMenu = false;
    private boolean bShowChangePwdMenu = false;
    private Bundle g_savedInstanceState = null;
    private Bundle g_savedInstanceState1 = null;
    HashMap<String, WebViewFragment> fragments = new HashMap<String, WebViewFragment>();
    public ViewPager viewPager = null;
	SampleFragmentPagerAdapter _adapter = null;

    ShareExternalServer appUtil;
    MapUserNotID mapUser;
    AsyncTask<Void, Void, String> mapUserTask;
    Context context;
    private Handler mUiHandler1 = new Handler();
    boolean bNetwork = true;
    String jsUid = "";
    ConnectivityManager connectivityManager = null;
    int lastVisitedTabItem = 0;

    public static final String REG_ID = "AdiaInvestmentGCMID";
    public static final String USER_ID = "AdiaInvestmentUID";
    public static final String USER_NAME = "AdiaInvestmentName";
    public static final String USER_COOKIE = "AdiaInvestmentCookie";
    public static final String URL_ID = "AdiaInvestmentURL";
    private static final String APP_VERSION = "appVersion";
    public static final String USER_LIKE = "AdiaInvestmentLike";

    public static final String SOCIAL_NETWORK_TAG = "SocialIntegrationMain.SOCIAL_NETWORK_TAG";
    private static ProgressDialog pd = null;
    static Context singleton;


    private MyWebReceiver receiver;
    private int versionCode = 0;
    String appURI = "";

    private DownloadManager downloadManager;
    private long downloadReference;

    private SwipeRefreshLayout swipe;
    private SwipeRefreshLayout swipe1;
    boolean bSwiped;

    private FragmentManager fragmentManager = null;
    private ContextMenuDialogFragment mMenuDialogFragment = null;
    public boolean bShowCMenu = false;
    public String jsonStr = "{\"menuitems\":[]}";
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    final int MY_PERMISSIONS_REQUEST_STORE = 1;


    protected static void showProgress(String message) {
        pd = new ProgressDialog(singleton);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(Html.fromHtml("<font color='#FFFFFF'>" + message + "</font>"));
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    protected static void hideProgress() {
        if (pd!=null)
            pd.dismiss();
    }

    public void createCMenuFromJS(String jsonStr) {
        this.jsonStr = jsonStr;
        invalidateOptionsMenu();
    }

    public void initMenuFragment(String jsonStr) {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects(jsonStr));
        menuParams.setClosableOutside(false);
        menuParams.setAnimationDuration(30);
        if(mMenuDialogFragment!=null) {
            mMenuDialogFragment = null;
        }
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects(String jsonStr) {
        List<MenuObject> menuObjects = new ArrayList<>();
        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);
        menuObjects.add(close);

        try {

            JSONObject jsonObj = new JSONObject(jsonStr);
            JSONArray menuitems = jsonObj.getJSONArray("menuitems");

            String a = "";
            String t = "";
            JSONObject c = null;
            for (int i = 0; i < menuitems.length(); i++) {
                c = menuitems.getJSONObject(i);
                a = c.getString("anchor");
                anchors[i+1] = a;
                t = c.getString("title");
                MenuObject item = new MenuObject(t);
                item.setResource(R.drawable.ic_star);
                menuObjects.add(item);
            }

            if(menuitems.length()<=0) bShowCMenu = false;
            else bShowCMenu = true;
        } catch(Exception e) {

        }

        return menuObjects;
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
    //  Toast.makeText(this, "Clicked on position: " + position + " anchor=" + anchors[position], Toast.LENGTH_SHORT).show();
        if(position>0) {
            fragments.get("0").goToAnchor(anchors[position]);
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
    //    Toast.makeText(this, "Long clicked on position: " + position + " anchor="+anchors[position], Toast.LENGTH_SHORT).show();
        if(position>0) {
            fragments.get("0").goToAnchor(anchors[position]);
        }
    }


    //check for internet connection
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    Log.v(TAG,String.valueOf(i));
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.v(TAG, "connected!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


 //   @Override
    public void onRefresh() {
        if(swipe.isRefreshing())
        swipe.setRefreshing(false);
    }

    public SwipeRefreshLayout returnSwipe() {
        return null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String url = intent.getStringExtra("message"); // show notification message
        if (url != null) {
            String title = intent.getStringExtra("title");
            intent.removeExtra("message");
            intent.removeExtra("title");

            Intent i = new Intent(mainactivity, WebViewActivity.class);
            i.putExtra("message", url);
            i.putExtra("title", title);
            mainactivity.startActivityForResult(i, Config.COMMAND_BACKWIN);
        }
    }

    public boolean checkIfNotificationMessage() {
        String url = getIntent().getStringExtra("message"); // show notification message
        if (url != null) {
            String title = getIntent().getStringExtra("title");
            getIntent().removeExtra("message");
            getIntent().removeExtra("title");

            Intent intent = new Intent(mainactivity, WebViewActivity.class);
            intent.putExtra("message", url);
            intent.putExtra("title", title);
            mainactivity.startActivityForResult(intent, Config.COMMAND_BACKWIN);
            return true;

        }

        return false;
    }


    private void getOverflowMenu() {

        try {
           ViewConfiguration config = ViewConfiguration.get(this);
           Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
           if(menuKeyField != null) {
               menuKeyField.setAccessible(true);
               menuKeyField.setBoolean(config, false);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
     }
    
    protected String getPageTitle(String url) {
    	int i = 0;
    	for(i=0; i<pageUrl.length; i++) {
    		if(pageUrl[i].equals(url)) break;    		
    	}
    	
    	if(i==pageUrl.length) return "";
    	
    	return " - "+menutitles[i];    	
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
            	if(area1.getVisibility()== View.VISIBLE) {
                    //mDrawerLayout.closeDrawers();
                    //return false;
                    onBackPressed();
                }
                //return false;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public class SampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
        final int PAGE_COUNT = titles.length;

        public SampleFragmentPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
        	if(position==20) {
                return ListViewFragment.create(position);        		
        	}
        	else {
        		WebViewFragment fragment = new WebViewFragment();
        		fragments.put(String.valueOf(position), fragment);
        		Bundle b = new Bundle();  
        		b.putInt("page", position);
                b.putString("url", urls[position]);
        		Log.i("CPP", "MainActivity getitem position=" + position);
                if (g_savedInstanceState != null) {
                	Bundle wv = g_savedInstanceState.getBundle(String.valueOf(position)+"wv");
                	if(wv!=null)
                		Log.i("CPP", "MainActivity getitem position="+position +" set webview bundle");
                		b.putBundle("wv", wv);                	
                }                        
                fragment.setArguments(b);

        		return fragment;
        	}
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    class SlideitemListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
//            Log.i(TAG, "Menu onclick item# "+position);
            if(position==0) { // home
            	area1.setVisibility(View.VISIBLE);
                setTitle(getResources().getString(R.string.home_title));
                fragments.get("0").webView.loadUrl("javascript:external_call(\"home\")");
            }
            else if(position>=1 && position <= pageUrl.length-1) {
                adapter.notifyDataSetChanged();
                fragments.get("0").webView.loadUrl("javascript:external_call(\""+menutitles[position].toLowerCase().replaceAll(" ", "_")+"\")");
            }
            else if(position==pageUrl.length) { // logout
                fragments.get("0").webView.loadUrl("javascript:external_call(\"logout\")");
             //   ToLogout();
            }
            mDrawerLayout.closeDrawers();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        mToolBarTextView.setText(mTitle);

    }

    public void setTitle(int position) {
        setTitle(getResources().getString(R.string.home_title) + " - " + titles[position]);
    }

    @Override
   public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        initMenuFragment(jsonStr);

        MenuItem item = menu.findItem(R.id.context_menu);
        item.setVisible(bShowCMenu);

        return true;

    }

    public void updateLoginprofile(String name) {
    	bShowLoginMenu = false;
        bShowRegisterMenu = false;
        bShowChangePwdMenu = bShowLogoutMenu = true;
        if(fragments.get("2")!=null) fragments.get("2").loadWebPage();
        changeProfile(2, name);
        addLogoutItem();
        invalidateOptionsMenu();
    }

    public void logoutOK() {
    	bShowLoginMenu = true;
	   	bShowRegisterMenu = true;
        bShowChangePwdMenu = bShowLogoutMenu = false;
        changeProfile(1, "");
	    invalidateOptionsMenu();
    }


    public void changeProfile(int i, String uri, String name) {

        if (i==1) { // show nologonprofile

            m_loggedin = false;
            ToLogout();

        } else { // show logonedprofile

            //if (regId != null && regId.length() > 0) {
             //   fragments.get("0").notifyToken(regId); // notify notification token
            //}

            RelativeLayout rl = (RelativeLayout)findViewById(R.id.nologonprofile);
            rl.setVisibility(View.GONE);
            rl = (RelativeLayout)findViewById(R.id.logonedprofile);
            rl.setVisibility(View.VISIBLE);
            TextView tv = (TextView)rl.findViewById(R.id.userName);
            tv.setText(name);
            final RoundedImageView img = (RoundedImageView)rl.findViewById(R.id.avatar);
            FetchImage fetch = new FetchImage(getApplicationContext(), new BitmapCallback() {
                @Override
                public void onTaskDone(Bitmap rv) {
                    img.setImageBitmap(rv);
                }
            });
            fetch.execute(Config.HTTPS_SERVER_ROOT + "/"+uri);
            addLogoutItem();
            mDrawerList.setVisibility(View.VISIBLE);
            m_loggedin = true;

        }

    }



    public void changeProfile(int i, String name) {
        if(i==1) { // show nologonprofile
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.nologonprofile);
            rl.setVisibility(View.VISIBLE);
            rl = (RelativeLayout)findViewById(R.id.logonedprofile);
            rl.setVisibility(View.GONE);
        }
        else { // show logonedprofile
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.nologonprofile);
            rl.setVisibility(View.GONE);
            rl = (RelativeLayout)findViewById(R.id.logonedprofile);
            rl.setVisibility(View.VISIBLE);
            TextView tv = (TextView)rl.findViewById(R.id.userName);
            tv.setText(name);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(androidx.core.view.GravityCompat.START);
                return true;
          default:
             return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    /***

     * Called when invalidateOptionsMenu() is triggered

     */

    public void ToLogout() {
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "MainActivity onSaveInstanceState 1");
        super.onSaveInstanceState(outState);
        Log.i(TAG, "MainActivity onSaveInstanceState 2");

        outState.putInt("area1onshow", area1.getVisibility());
        Log.i(TAG, "MainActivity onSaveInstanceState 3");

        outState.putInt("area1activeitem", viewPager.getCurrentItem());
        Log.i(TAG, "MainActivity onSaveInstanceState 4");

        Log.i(TAG, "MainActivity onSaveInstanceState 5");


    //    outState.putBundle("888wv", fragments.get("888").bundle);
        Log.i(TAG, "MainActivity onSaveInstanceState 6");

        WebViewFragment fragment = null;
        for(int i=0; i<titles.length; i++) {
        	fragment = fragments.get(String.valueOf(i));
        	if(fragment!=null)
        	{
                outState.putBundle(String.valueOf(i) + "wv", fragments.get(String.valueOf(i)).bundle);

                Log.i(TAG, "MainActivity onSaveInstanceState 7--"+i);
        	}
        }
        Log.i(TAG, "MainActivity onSaveInstanceState 8");

        g_savedInstanceState1 = outState;

        Log.i(TAG, "MainActivity onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
    	super.onRestoreInstanceState(state); 
    	Log.i(TAG, "MainActivity onRestoreInstanceState");
		Log.i("CPP", "MainActivity onRestoreInstanceState");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (g_savedInstanceState1 != null) {
            if(g_savedInstanceState1.getInt("area1onshow")==View.GONE) {
                area1.setVisibility(View.GONE);
             //   area2.setVisibility(View.VISIBLE);
            }
            else {
             //   area2.setVisibility(View.GONE);
                int area1activeitem = g_savedInstanceState1.getInt("area1activeitem");
                viewPager.setCurrentItem(area1activeitem);
            }

            g_savedInstanceState1 = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
 //       swipe.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);

        super.onStop();
	    ACaching.saveMemoryCacheToCacheFolder("8888");
	    ACaching.getSaveCookie("8888", 1);    
	    Log.i(TAG, "MainActivity OnStop -- savememorycachetocachefolder"); 
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void showHomePageAfterLogin(String name) {
     //   area2.setVisibility(View.GONE);
        area1.setVisibility(View.VISIBLE);
        setTitle(lastVisitedTabItem);
        viewPager.setCurrentItem(lastVisitedTabItem);
        lastVisitedTabItem = 0;
        updateLoginprofile(name);
    }


    public void storeLike() {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_LIKE, "1");
        editor.commit();
    }

    public boolean checkLikeBefore() {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String like = prefs.getString(USER_LIKE, "");
        if(like==null || like.isEmpty()) return false;
        else return true;
    }

    public void storeSocialNetworkAction(Context context, int action) {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("SN_ACTION", action);
        editor.commit();
    }
    public int getSocialNetworkAction(Context context) {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        return prefs.getInt("SN_ACTION", 0);
    }
    public void removeSocialNetworkAction() {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        prefs.edit().remove("SN_ACTION").commit();
    }

    public void storeUserInfo(Context context, String uid, String name) {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        jsUid = uid;
        Log.i(TAG, "Saving uid on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_ID, uid);
        editor.putString(USER_NAME, name);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
    }

    public boolean isLogoned() {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String userId = prefs.getString(USER_ID, "");
        if (userId.isEmpty()) {
            //	Log.i(TAG, "User id not found.");
            return false;
        }
        else return true;
    }

    String m_cookie = "";
    public void storeCookie(Context context, String cookie) {
//        m_cookie = cookie+"; login=1";
        m_cookie = cookie;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_STORE);

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_STORE);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                final SharedPreferences prefs = getSharedPreferences(
                        MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
                //    int appVersion = getAppVersion(context);
                //   Log.i(TAG, "Saving regId on app version " + appVersion);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(USER_COOKIE, cookie);
                editor.commit();
            }
        }
        else {
            final SharedPreferences prefs = getSharedPreferences(
                    MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
            //    int appVersion = getAppVersion(context);
            //   Log.i(TAG, "Saving regId on app version " + appVersion);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(USER_COOKIE, cookie);
            editor.commit();
        }
    }

    public boolean restoreLogonSession(CookieManager cookieManager) {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);

        String cookie = prefs.getString(USER_COOKIE, "");
        cookie = cookie.replaceAll("; ", ";");
        String[] p = cookie.split(";");
        for(int i=0; i<p.length; i++)
            if(p[i] != "") cookieManager.setCookie(Config.HTTPS_SERVER_ROOT, p[i]);

        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_STORE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    final SharedPreferences prefs = getSharedPreferences(
                            MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
                    //    int appVersion = getAppVersion(context);
                    //   Log.i(TAG, "Saving regId on app version " + appVersion);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString(USER_COOKIE, m_cookie);
                    editor.commit();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public boolean restoreUserInfo() {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String userId = prefs.getString(USER_ID, "");
        if (userId.isEmpty()) {
            //	Log.i(TAG, "User id not found.");
            return false;
        }

        String userName = prefs.getString(USER_NAME, "");
        updateLoginprofile(userName);

        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            //	Log.i(TAG, "App version changed.");
            return false;
        }

        addLogoutItem();

        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = prefs.getString(USER_COOKIE, "");
        cookieManager.setCookie(Config.HTTPS_SERVER_ROOT + "/", cookie);

        return true;
    }

    public void addLogoutItem() {
        if(rowItems.size()>menutitles.length) return;
        int i=7;
        RowItem items = new RowItem(getResources().getString(R.string.logout), menuIcons.getResourceId(i, -1), "logout", "0");

//                changeProfile(2, "");
        rowItems.add(items);
        adapter.notifyDataSetChanged();
    }
    public void removeUserInfo() {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
//        prefs.edit().remove(USER_ID).commit();
//        prefs.edit().remove(USER_NAME).commit();
//        prefs.edit().remove(USER_COOKIE).commit();

        SharedPreferences.Editor editor = prefs.edit();

        String userId = prefs.getString(USER_ID, "");
        String userName = prefs.getString(USER_NAME, "");
        String userCookie = prefs.getString(USER_COOKIE, "");

        int c = 0;
        if(!userId.isEmpty()) {
            editor.remove(USER_ID);
            Log.i(TAG, "removeuserinfo 1");
            ++c;
        }
        if(!userName.isEmpty()) {
            editor.remove(USER_NAME);
            Log.i(TAG, "removeuserinfo 2");
            ++c;
        }
        if(!userCookie.isEmpty()) {
            editor.remove(USER_COOKIE);
            Log.i(TAG, "removeuserinfo 3");
            ++c;
        }

        if(c>=1) editor.apply();
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            Log.e("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, 9000).show();
            } else {
                //Toast.makeText(this, "Google play SDK is not supported", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "This device is not supported.");
                //finish();
            }
            return false;
        }
        return true;
    }

    public boolean isNetworkStatusAvialable (Context context) {
        if(connectivityManager==null) connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected() && bNetwork)
                    return true;
                else return false;
        }
        return false;
    }

    public void mapUserInBackground(final String qs) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {

                    Log.i(TAG, "MAP user qs = "+qs);

                    final String param = util.encrypt(qs);

                    mapUser = new MapUserNotID();
                    String result = mapUser.shareRegIdWithAppServer(context, param);
                    return result;
                } catch (Exception ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.d("MapUserrActivity", "Error: " + msg);
                }
                Log.d("MapUserActivity", "AsyncTask completed: " + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                //Toast.makeText(getApplicationContext(),"Map User with Server." + msg, Toast.LENGTH_LONG).show();
            }
        }.execute(null, null, null);
    }

    private void logout(final String cookie) {
/*
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {

//                    URL obj = new URL(Config.LOGOUT_URL);
//                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    HttpsURLConnection con = util.setUpHttpsConnection(context, Config.LOGOUT_URL);
                    if(cookie!=null) con.setRequestProperty("Cookie", cookie);
                    // optional default is GET
                    con.setRequestMethod("GET");

                    //add request header
                    //   con.setRequestProperty("User-Agent", USER_AGENT);

                    int responseCode = con.getResponseCode();

                    Log.i(TAG, "MainActivity logout server response code: " + responseCode);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                }
                catch(Exception e) {

                }

                return "";
            }
        }.execute(null, null, null);
*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Config.COMMAND_BACKWIN) {

        }
        else {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public String getDeviceID() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String deviceId = deviceUuid.toString();

        return deviceId;
    }

    public void showPaymentPage(String uri) {
        Intent intent = new Intent(mainactivity, WebViewActivity.class);
        intent.putExtra("message", Config.HTTPS_SERVER_ROOT+uri);
        mainactivity.startActivity(intent);
    }

    public void showPage(String uri) {
        lastVisitedTabItem = viewPager.getCurrentItem();
        area1.setVisibility(View.GONE);
        setTitle(getResources().getString(R.string.home_title));
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void startShareEditActivity(Context context, String share_message, String socialnetwork, String imguri) {
        Intent intent = new Intent(context, EditTextActivity.class);
        intent.putExtra("defaulttext", share_message);
        intent.putExtra("socialnetwork", String.valueOf(socialnetwork));
        intent.putExtra("imguri", imguri);
        startActivityForResult(intent, WebViewFragment.REQ_SOCIAL_NETWORK_SHARE);


    }

    public void viewFreeclassform(String url) {
        Intent intent = new Intent(mainactivity, WebViewActivity.class);
        intent.putExtra("message", url);
        startActivity(intent);
    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.invest.adiainvestment",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                hash += "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    //broadcast receiver to get notification when the web request finishes
    public class MyWebReceiver extends BroadcastReceiver {

        public static final String PROCESS_RESPONSE = "com.as400samplecode.intent.action.PROCESS_RESPONSE";

        @Override
        public void onReceive(Context context, Intent intent) {

            String reponseMessage = intent.getStringExtra(GetAppVersionService.RESPONSE_MESSAGE);
            Log.v(TAG, reponseMessage);

            intent.removeExtra(GetAppVersionService.RESPONSE_MESSAGE);

            //parse the JSON response
            JSONObject responseObj;
            try {
                responseObj = new JSONObject(reponseMessage);
                boolean success = responseObj.getBoolean("success");
                //if the reponse was successful check further
                if(success){
                    //get the latest version from the JSON string
                    String v = responseObj.getString("latestVersion");
                    String v0 = v;
                    v = v.replaceAll("\\.", "");
                    int latestVersion = Integer.valueOf(v).intValue();
                    //get the lastest application URI from the JSON string
                 //   appURI = responseObj.getString("appURI");

                    //check if we need to upgrade?
                    if(latestVersion > versionCode){
                        //oh yeah we do need an upgrade, let the user know send an alert message
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage(getResources().getString(R.string.newversionmsg1)+" "+v0+", "+getResources().getString(R.string.newversionmsg2))
                                .setPositiveButton(getResources().getString(R.string.install), new DialogInterface.OnClickListener() {
                                    //if the user agrees to upgrade
                                    public void onClick(DialogInterface dialog, int id) {

                                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                        try {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                        } catch (android.content.ActivityNotFoundException anfe) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                        }

                                    }
                                })
                                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                    }
                                });
                        //show the alert message
                        builder.create().show();
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    //broadcast receiver to get notification about ongoing downloads
    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(downloadReference == referenceId){
            try {
                Log.v(TAG, "Downloading of the new app version complete");
                //start the installation of the latest version
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
            //    Uri u = Uri.parse(Environment.DIRECTORY_DOWNLOADS + "/adiai.apk");
                installIntent.setDataAndType(downloadManager.getUriForDownloadedFile(downloadReference),
                        "application/vnd.android.package-archive");
                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(installIntent);
            }
            catch(Exception e) {
                e.printStackTrace();

            }

        }
        }
    };

    public void storeNewVersionPromptCounter(Context context, int counter) {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("INSTALLCOUNT", counter);
        editor.commit();
    }
    public int getNewVersionPromptCounter(Context context) {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        return prefs.getInt("INSTALLCOUNT", 0);
    }

    public void hideActionBar() {
        getSupportActionBar().hide();
    }

    public void showActionBar() {
        getSupportActionBar().show();
    }

    public void store_msg_user_onshow(String uid) {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("msg_user_onshow", uid);
        editor.commit();
    }

    public String check_msg_user_onshow() {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String uid = prefs.getString("msg_user_onshow", "");
        if(uid==null || uid.isEmpty()) return null;
        else return uid;
    }

    public void toRegister(final WebView wview) {
        wview.post(new Runnable() {
            @Override
            public void run() {
                wview.loadUrl("javascript:continueregister()");
            }
        });

    }


    public void alert2(String msg, String title){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNegativeButton("No", null).show();
    }

    public void alert2(String msg){
        alert2(msg, "");
    }

    @Override
    public void onBackPressed(){
        // alert2("test", "testing");
        fragments.get("0").webView.loadUrl("javascript:external_call(\"onBackPressed\")");
        //GCMNotificationIntentService
        //sendNotification("Notification Test");
    }
///*
    @Override
    @android.annotation.TargetApi(19)
    protected void onStart(){
        Log.i(TAG, "onStart");

        super.onStart();

        //alert2("onStart1");

        // added by alantypoon 20190208
        hideActionBar();

        // enable debugging on console
        if (Build.VERSION.SDK_INT >= 19){
            if (0 != (getApplicationInfo().flags & android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE)) {
                //alert2("onStart2");
                WebView.setWebContentsDebuggingEnabled(true);
                //alert2("onStart3");
            }
        }
    }
//*/

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "onCreate");
        try {
            File httpCacheDir = new File(getCacheDir(), "http");
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            Log.i(TAG, "HTTP response cache installation failed:" + e);
        }
        MainActivity.titles = new String[1];
        MainActivity.titles[0] = getResources().getString(R.string.m_home);
        menutitles[0] = getResources().getString(R.string.m_home);
        menutitles[1] = getResources().getString(R.string.m_profile);
        menutitles[2] = getResources().getString(R.string.m_activity);
        menutitles[3] = getResources().getString(R.string.m_schedule);
        menutitles[4] = getResources().getString(R.string.m_messenger);
        menutitles[5] = getResources().getString(R.string.m_whatisup);
        menutitles[6] = getResources().getString(R.string.m_peers);

        Log.i(TAG, "MainActivity onCreate()");

        PackageInfo pInfo = null;
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        //get the app version Name for display
        String version = pInfo.versionName;
        //get the app version Code for checking
        versionCode = pInfo.versionCode;

        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(intent.getAction())) {
                Log.w(TAG, "Main Activity is not the root.  Finishing Main Activity instead of launching.");
                finish();
                return;
            }
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if(savedInstanceState==null) {
            Log.i(TAG, "MainActivity onCreate NULL savedInstancestate");
        } else {
            Log.i(TAG, "MainActivity onCreate with savedInstancestate");
        }
        singleton = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        menuIcons = getResources().obtainTypedArray(R.array.icons);

        mDrawerList = (ListView) findViewById(R.id.slider_list);
        mDrawerList.setVisibility(View.INVISIBLE);
        rowItems = new ArrayList<RowItem>();

        for (int i = 0; i < menutitles.length; i++) {
            RowItem items = new RowItem(menutitles[i], menuIcons.getResourceId(
                    i, -1), pageUrl[i], "0");
            rowItems.add(items);
        }

        menuIcons.recycle();

        changeProfile(1, "");

        adapter = new CustomAdapter(getApplicationContext(), rowItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new SlideitemListener());

        fragmentManager = getSupportFragmentManager();

        setTitle(getResources().getString(R.string.home_title));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.app_name,
                R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);
        if (Build.VERSION.SDK_INT >= 21) {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }
            nvDrawer.setPadding(0, result, 0, 0);
        }

        g_savedInstanceState = savedInstanceState;
        _adapter = new SampleFragmentPagerAdapter();

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(_adapter);
        viewPager.setOffscreenPageLimit(2);

        assetmanager = getAssets();
        ACaching.setContext(getApplicationContext());
        ACaching.setCacheFolder(getCacheDir());
        ACaching.setAssetManager(assetmanager);

        ACaching.readAssetRootCache();
        mainactivity = this;

        // init drawer menu
        mTitle = mDrawerTitle = getTitle();
        menuIcons = getResources().obtainTypedArray(R.array.icons);

        area1 = (LinearLayout) findViewById(R.id.area1);

        if (savedInstanceState != null) {
            if(savedInstanceState.getInt("area1onshow")==View.GONE) {
                area1.setVisibility(View.GONE);
                //        area2.setVisibility(View.VISIBLE);
            }
            else {
                //        area2.setVisibility(View.GONE);
                int area1activeitem = savedInstanceState.getInt("area1activeitem");
                viewPager.setCurrentItem(area1activeitem);
            }
        }

        context = getApplicationContext();

        //if(isNetworkStatusAvialable(context) && checkPlayServices()) {
            //Toast.makeText(this, "Google play SDK is supported in this device", Toast.LENGTH_SHORT).show();
            //registerGCM();
        //}

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.nologonprofile);
        Button bR = (Button)rl.findViewById(R.id.join);
        bR.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
            area1.setVisibility(View.GONE);
            mDrawerLayout.closeDrawers();
            }
        });
        bR.setVisibility(View.INVISIBLE);
        //Button b1 = null;
    }

    ////////////////////////////////////////////////////////////////////////
    // GCM
    ////////////////////////////////////////////////////////////////////////
/*
    GoogleCloudMessaging gcm;
    boolean bGCMRegisterSuccess = false;
    String regId;
    AsyncTask<Void, Void, String> shareRegidTask;

    public String registerGCM() {
        regId = "";
        if(isNetworkStatusAvialable(context) && checkPlayServices()) {
            //Toast.makeText(this, "Google play SDK is supported in this device", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "registerGCM");

            //boolean bNewVersion = false;
            regId = getRegistrationId(context);
            if (regId.indexOf("::<<>>::") == 0) { // different version of app
                regId = regId.replace("::<<>>::", "");
            }
            registerInBackground(regId);

        }
        return regId;
    }

    ///////////////////////////////////////////////////////////

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            //Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            //Log.i(TAG, "App version changed.");
            return "::<<>>::"+registrationId;
        }
        return registrationId;
    }

    ///////////////////////////////////////////////////////////

    @SuppressLint("StaticFieldLeak")
    private void registerInBackground(final String old_regid) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    //Thread.sleep(10000); // delay the registration

                    Log.i(TAG, "Creating GoogleCloudMessaging...");
                    gcm = GoogleCloudMessaging.getInstance(context);
                    Log.i(TAG, "Created GoogleCloudMessaging...");

                    regId = gcm.register(Config.GOOGLE_PROJECT_ID);
                    msg = "registered=" + regId;
                    Log.i(TAG, msg);

                    if(regId.equals(old_regid)) {
                        Log.i(TAG, "Equal regid:"+regId);
                    }
                    ;
                    if(!TextUtils.isEmpty(old_regid)) { // try to get uid from javascript to see whether the user has logoned
                        mUiHandler1.post(new Runnable() {
                            @Override
                            public void run() {
                                //    webView.loadUrl("javascript:getUid()");
                            }
                        });
                    }

                    // register with servlet
                    final String nid = util.encrypt(regId) + (((!TextUtils.isEmpty(old_regid))?"zzz"+ util.encrypt(old_regid)+"zzz"+util.encrypt(jsUid):""));

                    appUtil = new ShareExternalServer();

                    shareRegidTask = new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... params) {
                            String result = "";
                            if (result.equals("SUCCESS")){
                                bGCMRegisterSuccess = true;
                            }
                            return result;
                        }

                        @Override
                        protected void onPostExecute(String result) {
                            shareRegidTask = null;
                        }


                    };

                    if (shareRegidTask != null){
                        shareRegidTask.execute(null, null, null);
                    }


                } catch (Exception ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.e(TAG, "Error: " + msg);
                }
                Log.i(TAG, "AsyncTask completed: " + msg);
                return msg;
            }

            ////////////////////////////////////////////////////////

            @Override
            protected void onPostExecute(String msg) {
                //Toast.makeText(getApplicationContext(),"Registered with GCM Server." + msg, Toast.LENGTH_LONG).show();
            }
        }.execute(null, null, null);
    }

    ///////////////////////////////////////////////////////////

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs =
                getSharedPreferences(
                        MainActivity.class.getSimpleName(),
                        Context.MODE_PRIVATE
                );
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
    }

    // onrecvnotification
    // followed by GCMNotificationIntentService
*/
    ////////////////////////////////////////////////////////////////////////
    // FCM
    ////////////////////////////////////////////////////////////////////////

    //FirebaseInstanceId.getInstance().getInstanceId();

    /*
   .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>(){
        @Override
        public void onComplete(@NonNull Task<InstanceIdResult> task) {
            if (!task.isSuccessful()) {
                Log.w(TAG, "getInstanceId failed", task.getException());
                return;
            }

            // Get new Instance ID token
            String token = task.getResult().getToken();

            // Log and toast
            String msg = getString(R.string.msg_token_fmt, token);
            Log.d(TAG, msg);
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    });
    */

    // FirebaseMessaging.getInstance().setAutoInitEnabled(true);
/*

    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, new OnSuccessListener<InstanceIdResult>() {
        @Override
        public void onSuccess(InstanceIdResult instanceIdResult) {
            String token = instanceIdResult.getToken();
            Log.i("FCM Token", token);
            saveToken(token);
        }
    });
*/
/*
    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( MyActivity.this,  new OnSuccessListener<InstanceIdResult>() {
        @Override
        public void onSuccess(InstanceIdResult instanceIdResult) {
            String newToken = instanceIdResult.getToken();
            Log.e("newToken",newToken);

        }
    });
*/

    // Get token
    // [START retrieve_current_token]
    FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

    @Override
    public void onComplete(@NonNull Task<InstanceIdResult> task) {
        if (!task.isSuccessful()) {
            Log.w(TAG, "getInstanceId failed", task.getException());
            return;
        }

        // Get new Instance ID token
        String token = task.getResult().getToken();

        // Log and toast
        //String msg = getString(R.string.msg_token_fmt, token);
        Log.d(TAG, token);
        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
});
    // [END retrieve_current_token]
}
