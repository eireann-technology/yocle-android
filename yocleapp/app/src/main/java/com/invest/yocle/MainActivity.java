package com.invest.yocle;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.FragmentTransaction;
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
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
//import android.support.v7.widget.PopupMenu;
//import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;

import android.os.AsyncTask;
import android.content.SharedPreferences;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import android.os.Handler;
import android.text.TextUtils;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.ConnectionResult;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.UUID;

import android.support.v4.graphics.drawable.DrawableCompat;
import 	android.widget.Button;

import javax.net.ssl.HttpsURLConnection;
import com.google.gson.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.os.Environment;

import android.support.v4.widget.SwipeRefreshLayout;

//import com.yalantis.contextmenu.R;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

public class MainActivity extends ActionBarActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

//    static String titles[] = {Resources.getSystem().getString(R.string.m_home)};
//    static String titles[] = {"11"};
    static String titles[] = null;
    static String urls[] = {"index.php?platform=android"};
//    static String urls[] = {"actionbar.html"};
	AssetManager assetmanager = null;
	MainActivity mainactivity = null;
	String TAG = "ADIAPONG";

    String[] menutitles = {"123","123","123","123","123","123","123"};
//    String[] menutitles = {getResources().getString(R.string.m_home), getResources().getString(R.string.m_editprofile), getResources().getString(R.string.m_previewprofile), getResources().getString(R.string.m_printprofile),
        //getResources().getString(R.string.m_network), getResources().getString(R.string.m_createactivity), getResources().getString(R.string.m_searchactivity), getResources().getString(R.string.m_schedule), getResources().getString(R.string.m_postproject), getResources().getString(R.string.m_gsleaguetable)};
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
//    private FrameLayout area2 = null;
 //   private RelativeLayout mDrawerPane = null;
    
    private boolean bShowLoginMenu = true;
    private boolean bShowRegisterMenu = true;
    private boolean bShowLogoutMenu = false;
    private boolean bShowChangePwdMenu = false;
    private Bundle g_savedInstanceState = null;
    private Bundle g_savedInstanceState1 = null;
    HashMap<String, WebViewFragment> fragments = new HashMap<String, WebViewFragment>();
    public ViewPager viewPager = null;
    
    
	SampleFragmentPagerAdapter _adapter = null;

    GoogleCloudMessaging gcm;
    ShareExternalServer appUtil;
    MapUserNotID mapUser;
    AsyncTask<Void, Void, String> shareRegidTask;
    AsyncTask<Void, Void, String> mapUserTask;
    Context context;
    String regId;
    private Handler mUiHandler1 = new Handler();
    boolean bNetwork = true;
    boolean bGCMRegisterSuccess = false;
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
        if(pd!=null)
            pd.dismiss();
    }

    /*
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	//    retainedPages = _adapter.exportList();
	//    outState.putSerializable("retainedPages", (Serializable) retainedPages);
	    super.onSaveInstanceState(outState);
	}
    */


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
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

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
        }
        catch(Exception e) {}

        return menuObjects;
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {
    //    Toast.makeText(this, "Clicked on position: " + position + " anchor=" + anchors[position], Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCreate(Bundle savedInstanceState) {

  //      String c = "email=alantypoon@gmail.com; pwd=1234; reset_pwd=; remember=0; login=1;";
  //      storeCookie(this, c);

        try {

  //          String ctext = AesEncryptionUtil.encrypt("123 abc 123", "1234567890abcdef");
  //          String ptext = AesEncryptionUtil.decrypt("tLdSIcEaTgknjahgFiv1WPQXr7Km/LJi0ECIPa/8EMw=", "1234567890abcdef");

            File httpCacheDir = new File(getCacheDir(), "http");
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            Log.i(TAG, "HTTP response cache installation failed:" + e);
        }

/*
        try {
            File httpCacheDir = new File(context.getCacheDir(), "https");
            long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
            Class.forName("android.net.http.HttpResponseCache")
                    .getMethod("install", File.class, long.class)
                    .invoke(null, httpCacheDir, httpCacheSize);
        } catch (Exception httpResponseCacheNotAvailable) {
        }
*/
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
        }
        catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        //get the app version Name for display
        String version = pInfo.versionName;
        //get the app version Code for checking
        versionCode = pInfo.versionCode;

      //  requestWindowFeature(Window.FEATURE_ACTION_BAR);
/*
        String msg = getIntent().getStringExtra("message"); // show notification message
        if (!isTaskRoot() && msg == null) {
            finish();
            return;
        }
*/

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
    //    printHashKey();

        if(savedInstanceState==null) {
            Log.i(TAG, "MainActivity onCreate NULL savedInstancestate");
        }
        else {
            Log.i(TAG, "MainActivity onCreate with savedInstancestate");
        }

   //     new SimpleEula(this).show();

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
 //       initMenuFragment();


        // Set the menu icon instead of the launcher icon.
//        final ActionBar ab = getSupportActionBar();
//
//        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
//        ab.setDisplayHomeAsUpEnabled(true);

//        ab.setHomeButtonEnabled(false); // disable the button
//        ab.setDisplayHomeAsUpEnabled(false); // remove the left caret
//        ab.setDisplayShowHomeEnabled(false); // remove the icon

        setTitle(getResources().getString(R.string.home_title));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.app_name,
                R.string.app_name);

//        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        NavigationView nvDrawer = (NavigationView) findViewById(R.id.nvView);

//        setupDrawerContent(nvDrawer);


        if (Build.VERSION.SDK_INT >= 21) {
            int result = 0;
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = getResources().getDimensionPixelSize(resourceId);
            }

            nvDrawer.setPadding(0, result, 0, 0);
        }

        g_savedInstanceState = savedInstanceState;
        
        if(savedInstanceState!=null) {
/*
        	for(int i=0; i<urls.length; i++) {
        		WebViewFragment f = (WebViewFragment)getSupportFragmentManager().getFragment(savedInstanceState, String.valueOf(i+"f"));
        	    if(f!=null) {
        	    	Log.i("CPP", "MainActivity restore bundle position="+i);
        	    	fragments.put(String.valueOf(i),  f);
        	    }
        	}
*/

        }
        
                
        _adapter = new SampleFragmentPagerAdapter();
        
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(_adapter);
        viewPager.setOffscreenPageLimit(2);
//        viewPager.setOffscreenPageLimit(0);


/*
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(viewPager);

        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.fff);    //define any color in xml resources and set it here, I have used white
            }

            @Override
            public int getDividerColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

*/
    //    final ImageButton ib = (ImageButton) findViewById(R.id.menubutton);
        
                
        assetmanager = getAssets();
        ACaching.setContext(getApplicationContext());
        ACaching.setCacheFolder(getCacheDir());
        ACaching.setAssetManager(assetmanager);

        ACaching.readAssetRootCache();
/*
        ACaching.readAssetCache("scripts");
        ACaching.readAssetCache("scripts/bxslider");
        ACaching.readAssetCache("scripts/fancybox");
        ACaching.readAssetCache("images");
        ACaching.readAssetCache("chui");
        ACaching.loadMemoryCacheFromCacheFolder("8888");
        ACaching.getSaveCookie("8888", 2);
*/
        mainactivity = this;

        // init drawer menu
        mTitle = mDrawerTitle = getTitle();
        menuIcons = getResources().obtainTypedArray(R.array.icons);

        area1 = (LinearLayout) findViewById(R.id.area1);

/*
        area2 = (FrameLayout) findViewById(R.id.area2);

        // Add image selector fragment to the activity's container layout
        WebViewFragment	fragment = new WebViewFragment();
        fragments.put("888", fragment);
    	Bundle b = new Bundle();
        b.putInt("page", 888);
        if (savedInstanceState != null) {
        	Bundle wv = savedInstanceState.getBundle("888wv");
        	if(wv!=null) {
        		Log.i("CPP", "MainActivity getitem position=888 set webview bundle");
        		b.putBundle("wv", wv);
        	}        	
           	String area2url = savedInstanceState.getString("area2url");
        	String title = savedInstanceState.getString("title");        
        	if(area2url!=null) b.putString("url", area2url.replace(Config.HTTPS_SERVER_ROOT+"/", ""));
        	else b.putString("url", "about:blank");
        	if(title!=null) setTitle(title);
        }        
        else b.putString("url", "about:blank");
        
        fragment.setArguments(b);
        
        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(area2.getId(), fragment);

        // Commit the transaction
        t.commit();
*/

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
     //   else area2.setVisibility(View.GONE);


        context = getApplicationContext();

        if(isNetworkStatusAvialable(context) && checkPlayServices()) {
            //Toast.makeText(this, "Google play SDK is supported in this device", Toast.LENGTH_SHORT).show();
            registerGCM();
        }

        RelativeLayout rl = (RelativeLayout)findViewById(R.id.nologonprofile);
        Button bR = (Button)rl.findViewById(R.id.join);
        bR.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                area1.setVisibility(View.GONE);
         //       area2.setVisibility(View.VISIBLE);
//            	setTitle("顥森投資 - 關於我們");
        //        ((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).loadURL(Config.HTTPS_SERVER_ROOT + "/registerform.html");
                mDrawerLayout.closeDrawers();
            }
        });
        bR.setVisibility(View.INVISIBLE);

        Button b1 = null;
/*
        Button b1 = (Button)findViewById(R.id.bweeklyreport);
        b1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                area2.setVisibility(View.GONE);
                area1.setVisibility(View.VISIBLE);
                setTitle(1);
                viewPager.setCurrentItem(1);
                mDrawerLayout.closeDrawers();            }
        });

        b1 = (Button)findViewById(R.id.bportfolio);
        b1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                area1.setVisibility(View.GONE);
                area2.setVisibility(View.VISIBLE);
                setTitle("顥森天下 - " + getResources().getString(R.string.portfolio));
                //	((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).webView.loadUrl("about:blank");
                ((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).loadURL(Config.HTTPS_SERVER_ROOT+"/portfolio.html");
                mDrawerLayout.closeDrawers();            }
        });

        b1 = (Button)findViewById(R.id.bnotification);
        b1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                area2.setVisibility(View.GONE);
                area1.setVisibility(View.VISIBLE);
                setTitle(2);
                viewPager.setCurrentItem(2);
                mDrawerLayout.closeDrawers();
            }
        });

        b1 = (Button)findViewById(R.id.memberstatus);
        b1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainactivity, WebViewActivity.class);
                intent.putExtra("message", Config.HTTPS_SERVER_ROOT + "/memberstatus.html");
                intent.putExtra("title", getResources().getString(R.string.memberstatus));
                mainactivity.startActivity(intent);
            }
        });
*/


/*
        b1 = (Button) findViewById(R.id.points);
        b1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //               Intent intent = new Intent(mainactivity, sn_MainActivity.class);
                //               mainactivity.startActivity(intent);

                showArea2("顥森投資 - 收費模式", Config.HTTPS_SERVER_ROOT+"/paid/scheme.html");
//                showArea2("顥森投資 - 績分計劃", Config.HTTPS_SERVER_ROOT+"/vodandroid.html");
            }
        });

*/
        //restoreUserInfo();

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

   //     if(swipe1.isRefreshing())
   //         swipe1.setRefreshing(false);
     /*
        if(area1.getVisibility()==View.VISIBLE) {
            if(fragments.get("0").webView.getScrollY()==0) {
                swipe.setRefreshing(true);
                swipe.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragments.get("0").webView.reload();
                        swipe.setRefreshing(false);
                    }
                }, 1000);
            }
            else {
                swipe.setRefreshing(false);
            }
        }
        else {
            if(fragments.get("888").webView.getScrollY()==0) {
                swipe1.setRefreshing(true);
                swipe1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragments.get("888").webView.reload();
                        swipe1.setRefreshing(false);
                    }
                }, 1000);
            }
            else {
                swipe1.setRefreshing(false);
            }
        }

*/
    }

    public SwipeRefreshLayout returnSwipe() {
        /*
        if(area1.getVisibility()==View.VISIBLE) {
            return swipe;
        }
        else return swipe1;
        */
        return null;
    }
/*
    public void showArea2(String title, String url) {
        area1.setVisibility(View.GONE);
        area2.setVisibility(View.VISIBLE);
        setTitle(title);
        // 	((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).webView.loadUrl("about:blank");
        ((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).loadURL(url);
        mDrawerLayout.closeDrawers();
    }
*/

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
/*
            if(type=='f') url = msg;

            if(type=='n') {
                area1.setVisibility(View.GONE);
                area2.setVisibility(View.VISIBLE);
//            setTitle(3);
                fragments.get("888").loadURL(url);
            }
            else if(type=='w') {
                area2.setVisibility(View.GONE);
                area1.setVisibility(View.VISIBLE);
                setTitle(1);
                viewPager.setCurrentItem(1);
            }
            else if(type=='f') {
                area2.setVisibility(View.GONE);
                area1.setVisibility(View.VISIBLE);
                setTitle(0);
                viewPager.setCurrentItem(0);
                viewFreeclassform(url);
            }
            else if(type=='a') {
                area1.setVisibility(View.GONE);
                area2.setVisibility(View.VISIBLE);
                setTitle(getResources().getString(R.string.home_title)+" - " + getResources().getString(R.string.b_adiaiwordshtml));
                //	((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).webView.loadUrl("about:blank");
                ((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).loadURL(Config.HTTPS_SERVER_ROOT + "/adiaiposts.html");

                Intent intent = new Intent(mainactivity, WebViewActivity.class);
                intent.putExtra("message", url);

                String title = "";
                char c = msg.charAt(8);
                if(c=='a') title = getResources().getString(R.string.a_readerhtml);
                if(c=='b') title = getResources().getString(R.string.b_adiaiwordshtml);
                if(c=='c') title = getResources().getString(R.string.c_newuserhtml);
                if(c=='d') title = getResources().getString(R.string.d_famouspeoplehtml);
                if(c=='e') title = getResources().getString(R.string.e_smallstoryhtml);
                if(c=='f') title = getResources().getString(R.string.f_hinthtml);
                intent.putExtra("title", title);
                mainactivity.startActivity(intent);
            }
            else if(type=='e') {
                area2.setVisibility(View.GONE);
                area1.setVisibility(View.VISIBLE);
                setTitle(0);
                viewPager.setCurrentItem(0);
                Intent intent = new Intent(mainactivity, WebViewActivity.class);
                intent.putExtra("message", Config.HTTPS_SERVER_ROOT + "/memberstatus.html");
                intent.putExtra("title", getResources().getString(R.string.memberstatus));
                mainactivity.startActivity(intent);
            }
*/
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
                    mDrawerLayout.closeDrawers();
                    return false;
                }
             /*
            	if(area2.getVisibility()==View.VISIBLE) {
                    String uu = fragments.get("888").webView.getUrl();
                    if(uu.indexOf("/a_reader.html")>0 || uu.indexOf("/c_newuser.html")>0 || uu.indexOf("/d_famouspeople.html")>0 || uu.indexOf("/f_hint.html")>0 || uu.indexOf("/e_smallstory.html")>0 || uu.indexOf("/b_adiaiwords.html")>0)  {
                        fragments.get("888").webView.loadUrl("javascript:(function() { window.location=\"/adiaiposts.html\";" +
                                "})()");

                        //           fragments.get("888").webView.goBack();
                        return true;
                    }

                    if(uu.indexOf("/scheme/redeem.html")>0)  {
                        fragments.get("888").webView.loadUrl("javascript:(function() { window.location=\"/scheme/scheme.html\";" +
                                "})()");

             //           fragments.get("888").webView.goBack();
                        return true;
                    }
            		if(fragments.get("888").webView.canGoBack()) {
            			String historyUrl = "";
            			WebBackForwardList mWebBackForwardList = fragments.get("888").webView.copyBackForwardList();
            			if (mWebBackForwardList.getCurrentIndex() > 0) 
            				historyUrl = mWebBackForwardList.getItemAtIndex(mWebBackForwardList.getCurrentIndex()-1).getUrl();
            			if(historyUrl.equals("about:blank")) {
            				Log.i(TAG, "onKeyDown aboutblank back url="+historyUrl);
            				setTitle(viewPager.getCurrentItem());
                			area2.setVisibility(View.GONE);
                			area1.setVisibility(View.VISIBLE);
                			return true;
            			}
            			String filename = historyUrl.substring(historyUrl.lastIndexOf("/")+1);
            			if(filename.indexOf("#")>=0) {
            				filename = filename.substring(0, filename.indexOf("#"));
            			}
            			Log.i(TAG, "onKeyDown filename=" + filename + " back url=" + historyUrl);
            			setTitle(getResources().getString(R.string.home_title) + " - "+ getPageTitle(filename));
            			fragments.get("888").webView.goBack();
            			return true;
            		}
            		else {
            			setTitle(viewPager.getCurrentItem());
            			area2.setVisibility(View.GONE);
            			area1.setVisibility(View.VISIBLE);
            			return true;
            		}
            	}
            */
            	
                return false;
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
            //	area2.setVisibility(View.GONE);
            	area1.setVisibility(View.VISIBLE);
//            	setTitle(0);
                setTitle(getResources().getString(R.string.home_title));
 //               viewPager.setCurrentItem(0);
                fragments.get("0").webView.loadUrl("javascript:external_call(\"home\")");
            }
            else if(position>=1 && position <= pageUrl.length-1) {
                adapter.notifyDataSetChanged();
                fragments.get("0").webView.loadUrl("javascript:external_call(\""+menutitles[position].toLowerCase().replaceAll(" ", "_")+"\")");
/*
                area1.setVisibility(View.GONE);
                area2.setVisibility(View.VISIBLE);
                setTitle(menutitles[position]);
                ((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).loadURL(Config.HTTPS_SERVER_ROOT + "/" + pageUrl[position]);
*/
            }
            else if(position==pageUrl.length) { // logout
                fragments.get("0").webView.loadUrl("javascript:external_call(\"logout\")");
             //   ToLogout();
            }

            mDrawerLayout.closeDrawers();
//            mDrawerLayout.closeDrawer(mDrawerPane);
            
            
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
//        getSupportActionBar().setTitle(title);


        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        mToolBarTextView.setText(mTitle);

    }

    public void setTitle(int position) {
        setTitle(getResources().getString(R.string.home_title) + " - " + titles[position]);
    }

    @Override
   public boolean onCreateOptionsMenu(Menu menu) {
        /*
        getMenuInflater().inflate(R.menu.main, menu);
      //  return true;euser
        return super.onCreateOptionsMenu(menu);
*/

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
        //updateProfile(name, "已登入");
        if(fragments.get("2")!=null) fragments.get("2").loadWebPage();
     //   if(fragments.get("3")!=null) fragments.get("3").loadWebPage();
        changeProfile(2, name);
        addLogoutItem();
        invalidateOptionsMenu();
    }

    public void logoutOK() {
    	bShowLoginMenu = true;
	   	bShowRegisterMenu = true;
        bShowChangePwdMenu = bShowLogoutMenu = false;
//	   	updateProfile("未登入", "");
        changeProfile(1, "");
	    invalidateOptionsMenu();
/*
   //     mUiHandler1.post(new Runnable() {
        //    @Override
         //   public void run() {
                final AlertDialog alert = new AlertDialog.Builder(this).create();
        //        alert.setTitle("登出成功");
                alert.setTitle(Html.fromHtml("<font color='#FFFFFF'>"+getResources().getString(R.string.logoutsuccess)+"</font>"));
                alert.setMessage(getResources().getString(R.string.logoutsuccessmsg));
                alert.setButton(getResources().getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
                                     @Override
                                     public void onShow(DialogInterface arg0) {
                                         alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GREEN);
                                     }
                                 });

                alert.show();
         //   }
   //     });
 */
    }


    public void changeProfile(int i, String uri, String name) {

        if(i==1) { // show nologonprofile
            /*
            RelativeLayout rl = (RelativeLayout)findViewById(R.id.nologonprofile);
            rl.setVisibility(View.VISIBLE);
            rl = (RelativeLayout)findViewById(R.id.logonedprofile);
            rl.setVisibility(View.GONE);
            */
    //        fragments.get("0").webView.loadUrl("javascript:external_call(\"logout\")");
            ToLogout();
        }
        else { // show logonedprofile
            if(regId!=null && regId.length()>0) {
/*
                mUiHandler1.post(new Runnable() {
                    @Override
                    public void run() {
                        fragments.get("0").notifyToken(regId); // notify token
                    }
                });
*/
                fragments.get("0").notifyToken(regId); // notify token
            }

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
                mDrawerLayout.openDrawer(android.support.v4.view.GravityCompat.START);
                return true;

/*
            case R.id.login:
              	area1.setVisibility(View.GONE);
            	area2.setVisibility(View.VISIBLE);            	
            	setTitle(getResources().getString(R.string.home_title));
        	   ((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).loadURL(Config.HTTPS_SERVER_ROOT + "/loginform.html");
            //    updateLoginprofile("ABC");
                return true;

           case R.id.register:
               area1.setVisibility(View.GONE);
               area2.setVisibility(View.VISIBLE);
            	setTitle(getResources().getString(R.string.home_title));
               ((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).loadURL(Config.HTTPS_SERVER_ROOT + "/registerform.html");
               return true;
            case R.id.changepwd:
                Intent intent = new Intent(mainactivity, WebViewActivity.class);
                intent.putExtra("message", Config.HTTPS_SERVER_ROOT + "/changepwd.html");
                mainactivity.startActivity(intent);
                return true;
            case R.id.logout:
                ToLogout();
               return true;
 */
          default:
             return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    /***

     * Called when invalidateOptionsMenu() is triggered

     */

    public void ToLogout() {
        try {
            CookieManager cookieManager = CookieManager.getInstance();
            String cookie = cookieManager.getCookie(new URL(Config.LOGOUT_URL).getHost());
         //   logout(cookie);
            Log.i(TAG, "To logout 1");
 //           cookieManager.removeAllCookie();
            ACaching.removeSpecialCaches("999");
            Log.i(TAG, "To logout 2");
            removeUserInfo();
            Log.i(TAG, "To logout 3");

            if(fragments!=null) {
                WebViewFragment wvf = fragments.get("2");
                if(wvf != null) wvf.loadWebPage();
                else Log.i(TAG, "Logout null fragments.get(2)");
            }
            else {
                Log.i(TAG, "Logout Null Fragments object");
            }
            Log.i(TAG, "To logout 4");

 //           fragments.get("3").loadWebPage();
//            fragments.get("888").logoutSocialNetwork();

            if(fragments!=null) {
                WebViewFragment wvf = fragments.get("888");
            //    if(wvf != null) wvf.logoutSocialNetwork();
            //    else Log.i(TAG, "Logout null fragments.get(888)");
            }
            else {
                Log.i(TAG, "Logout Null Fragments object");
            }

            Log.i(TAG, "To logout 5");
            logoutOK();
            Log.i(TAG, "To logout 6");

            invalidateOptionsMenu();
            Log.i(TAG, "To logout 7");

            rowItems.remove(menutitles.length);

            adapter.notifyDataSetChanged();
            Log.i(TAG, "To logout 8");

            mDrawerList.setVisibility(View.INVISIBLE);
            changeProfile(1, "");
            Log.i(TAG, "To logout 9");

        }
        catch (Exception e) {
            Log.i(TAG, e.toString());
            Log.e(TAG, "Exception", e);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
/*
        menu.findItem(R.id.login).setVisible(bShowLoginMenu);
        menu.findItem(R.id.register).setVisible(bShowRegisterMenu);
        menu.findItem(R.id.changepwd).setVisible(bShowChangePwdMenu);
        menu.findItem(R.id.logout).setVisible(bShowLogoutMenu);
*/
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

        //     ((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).webView.saveState(outState);

        Log.i(TAG, "MainActivity onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
    	super.onRestoreInstanceState(state); 
  //      ((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).webView.saveState(state);        
    	Log.i(TAG, "MainActivity onRestoreInstanceState");
		Log.i("CPP", "MainActivity onRestoreInstanceState");

    }


    @Override
    protected void onStart() {
        super.onStart();

/*
        //check of internet is available before making a web service request
        int newversionpromptcount = getNewVersionPromptCounter(context);
        if(++newversionpromptcount>5) newversionpromptcount = 0;
        storeNewVersionPromptCounter(context, newversionpromptcount);
        if(newversionpromptcount==1 && isNetworkAvailable(this)){
            //Broadcast receiver for our Web Request

            if(receiver==null) {
                IntentFilter filter = new IntentFilter(MyWebReceiver.PROCESS_RESPONSE);
                filter.addCategory(Intent.CATEGORY_DEFAULT);
                receiver = new MyWebReceiver();
                registerReceiver(receiver, filter);

                //Broadcast receiver for the download manager
                filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
                registerReceiver(downloadReceiver, filter);
            }
            Intent msgIntent = new Intent(this, GetAppVersionService.class);
            msgIntent.putExtra(GetAppVersionService.REQUEST_STRING, Config.HTTP_SERVER_ROOT+"/invest/servlet/getversion");
            startService(msgIntent);
        }
*/
/*
        swipe.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
                new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (fragments.get("0").webView.getScrollY() == 0)
                            swipe.setEnabled(true);
                        else
                            swipe.setEnabled(false);

                    }
                });
*/
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

    public String registerGCM() {

        boolean bNewVersion = false;
        //gcm = GoogleCloudMessaging.getInstance(this);
        regId = getRegistrationId(context);

        if(regId.indexOf("::<<>>::")==0) { // different version of app
            regId = regId.replace("::<<>>::", "");
            bNewVersion = true;
        }

//		if (TextUtils.isEmpty(regId) || bNewVersion)
        if(true)
        {
            registerInBackground(regId);
        } else {
            Log.i(TAG, "RegID "+regId+" already registered");
        }
        return regId;
    }

    public void showHomePageAfterLogin(String name) {
     //   area2.setVisibility(View.GONE);
        area1.setVisibility(View.VISIBLE);
        setTitle(lastVisitedTabItem);
        viewPager.setCurrentItem(lastVisitedTabItem);
        lastVisitedTabItem = 0;
        updateLoginprofile(name);
    }

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
    ;
    private void registerInBackground(final String old_regid) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    //if (gcm == null) {

                    Thread.sleep(10000); // delay the registration

                    Log.i("yoclepong", "111");
                    gcm = GoogleCloudMessaging.getInstance(context);
                    Log.i("yoclepong", "222");
                    //}
                    regId = gcm.register(Config.GOOGLE_PROJECT_ID);
                    if(regId!=null && regId.length()>0) {
                        mUiHandler1.post(new Runnable() {
                            @Override
                            public void run() {
                                fragments.get("0").notifyToken(regId); // notify token
                                }
                        });
                    }
//					regId = "12345678";
                    msg = "registered=" + regId;
                    Log.i("yoclepong", msg);

                    if(regId.equals(old_regid)) {
                        Log.i(TAG, "Equal regid:"+regId);
//						return "";
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

            //        storeRegistrationId(context, regId);

                    // register with servlet
                    final String nid = util.encrypt(regId) + (((!TextUtils.isEmpty(old_regid))?"zzz"+ util.encrypt(old_regid)+"zzz"+util.encrypt(jsUid):""));

                    appUtil = new ShareExternalServer();
                    shareRegidTask = new AsyncTask<Void, Void, String>() {
                        @Override
                        protected String doInBackground(Void... params) {
                         //   String result = appUtil.shareRegIdWithAppServer(context, nid);
                            String result = "";
                            if(result.equals("SUCCESS")) bGCMRegisterSuccess = true;
                            return result;
                        }

                        @Override
                        protected void onPostExecute(String result) {
                            shareRegidTask = null;
                            //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                        }


                    };
                    shareRegidTask.execute(null, null, null);


//				} catch (IOException ex) {
                } catch (Exception ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.e(TAG, "Error: " + msg);
                }
                Log.i(TAG, "AsyncTask completed: " + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                //Toast.makeText(getApplicationContext(),"Registered with GCM Server." + msg, Toast.LENGTH_LONG).show();
            }
        }.execute(null, null, null);
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
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

/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeAllCookies(new ValueCallback<Boolean>() {
                // a callback which is executed when the cookies have been removed
                @Override
                public void onReceiveValue(Boolean aBoolean) {
                    Log.d(TAG, "Cookie removed: " + aBoolean);
                }
            });
        }
        else cookieManager.removeAllCookie();
*/
//        cookie = "email=alantypoon@gmail.com; pwd=1234; reset_pwd=; remember=0; login=1;";
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
                finish();
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
/*
        lastVisitedTabItem = viewPager.getCurrentItem();
        area1.setVisibility(View.GONE);
        area2.setVisibility(View.VISIBLE);
        setTitle("顥森天下");
        ((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).webView.loadUrl(Config.HTTPS_SERVER_ROOT+uri);
*/

        Intent intent = new Intent(mainactivity, WebViewActivity.class);
        intent.putExtra("message", Config.HTTPS_SERVER_ROOT+uri);
        mainactivity.startActivity(intent);
    }

    public void showPage(String uri) {
        lastVisitedTabItem = viewPager.getCurrentItem();
        area1.setVisibility(View.GONE);
     //   area2.setVisibility(View.VISIBLE);
        setTitle(getResources().getString(R.string.home_title));
     //   ((WebViewFragment) getSupportFragmentManager().findFragmentById(R.id.area2)).loadURL(Config.HTTPS_SERVER_ROOT+uri);
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void startShareEditActivity(Context context, String share_message, String socialnetwork, String imguri) {
        Intent intent = new Intent(context, EditTextActivity.class);
//					intent.putExtra("defaulttext", getResources().getString(R.string.defaulttext));
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
            PackageInfo info = getPackageManager().getPackageInfo("com.invest.adiainvestment",
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

    public void toRegister(final WebView wview) {
        wview.post(new Runnable() {
            @Override
            public void run() {
                wview.loadUrl("javascript:continueregister()");
            }
        });

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
/*
                                        //start downloading the file using the download manager
                                        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                                        Uri Download_Uri = Uri.parse(appURI);
                                        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                                        request.setAllowedOverRoaming(false);
                                        request.setTitle("顥森天下下載中");
                                        request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS, "adiai.apk");
                                        downloadReference = downloadManager.enqueue(request);
*/

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

}
