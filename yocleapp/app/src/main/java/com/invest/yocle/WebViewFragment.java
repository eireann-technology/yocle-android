package com.invest.yocle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ResponseCache;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Session.StatusCallback;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.widget.LikeView;
*/
//import com.github.gorbin.asne.core.SocialNetwork;
//import com.github.gorbin.asne.core.SocialNetworkManager;
//import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
//import com.github.gorbin.asne.core.listener.OnPostingCompleteListener;
//import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.ActivityManager.RecentTaskInfo;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.*;
import com.invest.json.GenToken;
import com.invest.json.LikeAction;
import com.invest.json.ViewItemError;

import com.facebook.FacebookSdk;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//public class WebViewFragment extends Fragment implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {
	public class WebViewFragment extends Fragment implements SwipeWebViewInterface, SwipeRefreshLayout.OnRefreshListener {
//	public class WebViewFragment extends Fragment {
//implements Serializable {
//	public static SocialNetworkManager mSocialNetworkManager;
//	UiLifecycleHelper uiHelper;
	int SN_LOGIN = 1;
	int SN_LIKE = 2;
	int SN_SHARE = 3;
	String g_shareimguri = "";
	int sn_action = 0;

	public static final String ARG_PAGE = "ARG_PAGE";
	Context context;
	String regId;

	private ProgressDialog pDialog = null;
	public final int progress_bar_type = 0;

	public static final String REG_ID = "AdiaInvestmentGCMID";
	public static final String USER_ID = "AdiaInvestmentUID";
	public static final String URL_ID = "AdiaInvestmentURL";
	private static final String APP_VERSION = "appVersion";

	private static final int REQ_START_STANDALONE_PLAYER = 1;
	private static final int REQ_RESOLVE_SERVICE_MISSING = 2;
	private static final int BUFFER_SIZE = 4096;
	public static final int REQ_SOCIAL_NETWORK_SHARE = 888;

	private GenToken sharetoken = null;

	static final String TAG = "ADIAPONG";

	//	AsyncTask<Void, Void, String> shareRegidTask;
	AsyncTask<Void, Void, String> mapUserTask;

	//	protected FrameLayout webViewPlaceholder;
	public WebView webView = null;
	private boolean bCheckActiveInternetConnection = true;
	FragmentActivity mainactivity = null;

	protected boolean oncreatecalled = false;

	private Handler mUiHandler = new Handler();
	private Handler mUiHandler1 = new Handler();
	ConnectivityManager connectivityManager = null;
	AlertDialog alertDialog = null;
	Date dtime = null;
	int nTime = 0;
	Thread pingThread = null;
	String jsUid = "";
	Bundle g_savedInstanceState = null;
	Bundle bundle = new Bundle();
	String g_url = null;

	// 3 levels cache system
	String html_source = "";
	Integer lock = new Integer(1);
	AssetManager assetManager = null;
	String g_cookie = "";
	String doc_store_folder = "";
	boolean bNetwork = true;
	boolean bGCMRegisterSuccess = false;
	String baseURL = "";
	String position = null;
	ACaching cache = null;
	//	boolean bCallFromResume = false;
//	boolean bReloadOnPageFinish = false;
	boolean bOnPauseCalled = false;


	Map<String, String> mimes = null;
	private int mPage;

	CallbackManager callbackManager;
	ShareDialog shareDialog;

	SwipeRefreshLayout swipe;
	private static final int INPUT_FILE_REQUEST_CODE = 1;
	private static final int FILECHOOSER_RESULTCODE = 1;
	private ValueCallback<Uri> mUploadMessage;
	private Uri mCapturedImageURI = null;
	private ValueCallback<Uri[]> mFilePathCallback;
	private String mCameraPhotoPath;
	private String mCameraVideoPath;
	File videoFile;
	File photoFile;

	private boolean bPageFinished = false;

	private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;

	/*
	static TrustManager[] xtmArray = new MytmArray[] { new MytmArray() };
	private static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		// Android 采用X509的证书信息机制
		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, xtmArray, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
			// HttpsURLConnection.setDefaultHostnameVerifier(DO_NOT_VERIFY);//
			// 不进行主机名确认
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
	static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			// TODO Auto-generated method stub
			// System.out.println("Warning: URL Host: " + hostname + " vs. "
			// + session.getPeerHost());
			return true;
		}
	};

	@Override
	public SwipeRefreshLayout returnSwipeObject() {
		return ((MainActivity)getActivity()).returnSwipe();
	}
	@Override
	public void onRefresh() {
		Log.d("swipepong", "onRefresh() called");

		if (swipe.isRefreshing()) {
			try {
				Thread.sleep(100);
			}
			catch(Exception e) {}
			swipe.setRefreshing(false);
		}
		webView.reload();
	}

	private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
		@Override
		public void onCancel() {
			Log.d("HelloFacebook", "Canceled");
		}

		@Override
		public void onError(FacebookException error) {
			Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
			String title = getString(R.string.facebookerror);
			String alertMessage = error.getMessage();
			showResult(title, alertMessage);
		}

		@Override
		public void onSuccess(Sharer.Result result) {
			Log.d("HelloFacebook", "Success!");
			if (result.getPostId() != null) {
				if(sharetoken!=null) {
					getCookie();

					JsonHttpUtil json = new JsonHttpUtil(context, new FragmentJsonCallback() {
						@Override
						public void onTaskDone(String rv) {
							sharetoken = Share_Callback(rv);
							sharetoken = null;
						}
					}, 2);

					json.execute(Config.HTTPS_SERVER_ROOT + "/invest/servlet/shareshare", g_cookie, "id=" + sharetoken.id + "&source=" + sharetoken.utm_source + "&medium=" + sharetoken.utm_medium + "&campaign=" + sharetoken.utm_campaign);
				}

				String title = getString(R.string.facebooksuccess);
				String id = result.getPostId();
				String alertMessage = getString(R.string.facebooksuccess, id);
				showResult(title, alertMessage);
			}
		}

		private void showResult(String title, String alertMessage) {
			Toast.makeText(getActivity(), alertMessage, Toast.LENGTH_LONG).show();
		}
	};


	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}


	public WebViewFragment() {

	}

	/*
        public void create(int page, String url) {
            Bundle args = new Bundle();
            args.putInt("page", page);
            args.putString("url", url);
            this.setArguments(args);
        }
    */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "Page " + position + " oncreate");
		super.onCreate(savedInstanceState);
		g_savedInstanceState = savedInstanceState;
		mPage = getArguments().getInt("page");
		position = String.valueOf(mPage);
		if (getArguments().getString("url").equals("about:blank")) g_url = "about:blank";
//		else g_url = Config.HTTPS_SERVER_ROOT + "/" + getArguments().getString("url");
		else g_url = Config.HTTPS_SERVER_ROOT + "/" + getArguments().getString("url");

		if(getArguments().getString("url").indexOf("https://videoboard.hk/")>=0) g_url = getArguments().getString("url");

		bundle.clear();
		Bundle b = getArguments().getBundle("wv");
		if (b != null) {
			Log.i("CPP", "WebViewFragment getitem position=" + position + " set webview bundle");
			bundle = b;
		}
		assetManager = getActivity().getAssets();
		cache = new ACaching(this);

		FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		shareDialog = new ShareDialog(this);

		// this part is optional
		shareDialog.registerCallback(callbackManager, shareCallback);
		//   setRetainInstance(true);
	//	Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//	g_savedInstanceState = this.getArguments();
		Log.i(TAG, "Page " + position + " oncreateview");


		ArrayList<String> fbScope = new ArrayList<String>();
		fbScope.addAll(Arrays.asList("public_profile"));
		String linkedInScope = "r_basicprofile+r_fullprofile+rw_nus+r_network+w_messages+r_emailaddress+r_contactinfo";
/*
		//Use manager to manage SocialNetworks
		mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(MainActivity.SOCIAL_NETWORK_TAG);

		//Check if manager exist
		if (mSocialNetworkManager == null) {
			mSocialNetworkManager = new SocialNetworkManager();

			//Init and add to manager FacebookSocialNetwork
//			FacebookSocialNetwork fbNetwork = new FacebookSocialNetwork(this, fbScope);
			FacebookSocialNetwork fbNetwork = new FacebookSocialNetwork(this, null);
			mSocialNetworkManager.addSocialNetwork(fbNetwork);

			//Initiate every network from mSocialNetworkManager
			getFragmentManager().beginTransaction().add(mSocialNetworkManager, MainActivity.SOCIAL_NETWORK_TAG).commit();
			mSocialNetworkManager.setOnInitializationCompleteListener(this);
		} else {
			//if manager exist - get and setup login only for initialized SocialNetworks
			if (!mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
				List<SocialNetwork> socialNetworks = mSocialNetworkManager.getInitializedSocialNetworks();
				for (SocialNetwork socialNetwork : socialNetworks) {
					socialNetwork.setOnLoginCompleteListener(this);
					//initSocialNetwork(socialNetwork);
				}
			}
		}
*/

		return inflater.inflate(R.layout.webviewfragment, container, false);
//        return inflater.inflate(R.layout.webviewfragment, null, false);
	}

/*
	@Override
	public void onSocialNetworkManagerInitialized() {
		//when init SocialNetworks - get and setup login only for initialized SocialNetworks
		for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
			socialNetwork.setOnLoginCompleteListener(this);
			//initSocialNetwork(socialNetwork);
		}
	}
*/
/*
	@Override
	public void onLoginSuccess(int networkId) {
	//	MainActivity.hideProgress();
		sn_action = ((MainActivity) getActivity()).getSocialNetworkAction(((MainActivity) getActivity()).context);
		if (sn_action == SN_LOGIN) {
			if (isSocialNetworkConnected(FacebookSocialNetwork.ID)) {
				uiHelper = new UiLifecycleHelper(this.getActivity(), mStatusCallback);
				sn_action = 0;
				((MainActivity) getActivity()).removeSocialNetworkAction();
			}
		} else if (sn_action == SN_SHARE) {
			sn_action = 0;
			((MainActivity) getActivity()).removeSocialNetworkAction();
//			Toast.makeText(getActivity(), getResources().getString(R.string.fbsdklogoned), Toast.LENGTH_LONG).show();
			showDialog1("Facebook 登入成功", getResources().getString(R.string.fbsdklogoned));
		}

		//startProfile(networkId);
	}

	@Override
	public void onError(int networkId, String requestID, String errorMessage, Object data) {
		MainActivity.hideProgress();
//		Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();
		showDialog1("Facebook 分亨錯誤", errorMessage);
	}
*/
	public void share2Facebook(String msg) {

		PackageManager pm = getActivity().getPackageManager();
		try {

			if (appInstalledOrNot("com.facebook.katana")) {
				Intent svc = new Intent(getActivity(), OverlayButtonService.class);
				getActivity().startService(svc);

				String uri = "facebook://facebook.com/wall";
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			} else {
				Toast.makeText(getActivity(), "Facebook not Installed",
						Toast.LENGTH_SHORT).show();
				Uri uri = Uri.parse("market://details?id=com.facebook.katana");
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(goToMarket);
			}
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Facebook not Installed", Toast.LENGTH_SHORT)
					.show();
		}

	}


	public void openPDFViewer(String file) {

		PackageManager pm = getActivity().getPackageManager();
		try {

			if (appInstalledOrNot("com.adobe.reader")) {
				Intent intent;
				intent = new Intent(Intent.ACTION_VIEW);

				File f = new File(file);
				f.setReadable( true, false );
				Uri location = Uri.fromFile(f);
			//	Intent intent = new Intent(Intent.ACTION_VIEW);
			//	intent.setDataAndType(location, mimes.get(ext));

				intent.setDataAndType(location, "application/pdf");
				try {
					startActivity(intent);
				} catch (ActivityNotFoundException e) {
				}

			} else {
				/*
				Toast.makeText(getActivity(), "Acrobat PDF reader not Installed",
						Toast.LENGTH_SHORT).show();
				Uri uri = Uri.parse("market://details?id=com.adobe.reader");
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(goToMarket);
				*/

				// No application to view, ask to download one

				mUiHandler1.post(new Runnable() {
					@Override
					public void run() {
						AlertDialog.Builder builder = new AlertDialog.Builder(mainactivity);
						builder.setTitle("No Acrobat PDF Reader Application Found");
						builder.setMessage("Download one from Android Market?");
						builder.setPositiveButton("Yes, Please",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Intent marketIntent = new Intent(Intent.ACTION_VIEW);
										marketIntent
												.setData(Uri
														.parse("market://details?id=com.adobe.reader"));
										startActivity(marketIntent);
									}
								});
						builder.setNegativeButton("No, Thanks", null);
						builder.create().show();
					}
				});
			}
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Acrobat reader not Installed", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void share2WhatsApp(String msg) {

		PackageManager pm = getActivity().getPackageManager();
		try {

			if (appInstalledOrNot("com.whatsapp")) {
				Intent waIntent = new Intent(Intent.ACTION_SEND);
				waIntent.setType("text/plain");
				String text = msg;

				PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
				//Check if package exists or not. If not then code
				//in catch block will be called
				waIntent.setPackage("com.whatsapp");

				waIntent.putExtra(Intent.EXTRA_TEXT, text);
				startActivity(Intent.createChooser(waIntent, "Share with"));
			} else {
				Toast.makeText(getActivity(), "WhatsApp not Installed",
						Toast.LENGTH_SHORT).show();
				Uri uri = Uri.parse("market://details?id=com.whatsapp");
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(goToMarket);

			}
		} catch (NameNotFoundException e) {
			Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void share2Wechat(String msg) {

		PackageManager pm = getActivity().getPackageManager();
		try {

			if (appInstalledOrNot("com.tencent.mm")) {
				Intent intent = new Intent();
				ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
				intent.setComponent(comp);
				intent.setAction("android.intent.action.SEND");
				intent.setType("plain/text");
				intent.putExtra(Intent.EXTRA_TEXT, msg);
				startActivity(intent);
			} else {
				Toast.makeText(getActivity(), "Wechat not Installed",
						Toast.LENGTH_SHORT).show();
				Uri uri = Uri.parse("market://details?id=com.tencent.mm");
				Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
				startActivity(goToMarket);

			}
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Wechat not Installed", Toast.LENGTH_SHORT)
					.show();
		}

	}


	private boolean appInstalledOrNot(String uri) {
		PackageManager pm = getActivity().getPackageManager();
		boolean app_installed = false;
		try {
			pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (PackageManager.NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
	}


	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		Log.i(TAG, "Page " + position + " onactivitiycreated");
		super.onActivityCreated(savedInstanceState);

		mainactivity = getActivity();
		context = getActivity().getApplication();

		if (webView == null) {
			Log.i(TAG, "Page " + position + " calling initUI");
			initUI(savedInstanceState);
		}
	}


	@Override
	public void onStart() {
		super.onStart();

		bCheckActiveInternetConnection = true;
		context = getActivity().getApplicationContext();

		//       startPingThread();
/*
		swipe.getViewTreeObserver().addOnScrollChangedListener(mOnScrollChangedListener =
				new ViewTreeObserver.OnScrollChangedListener() {
					@Override
					public void onScrollChanged() {
						if (webView.getScrollY() == 0)
							swipe.setEnabled(true);
						else
							swipe.setEnabled(false);

					}
				});
*/


		Log.i(TAG, "Page " + position + " OnStart");
	}

	@Override
	public void onResume() {
		super.onResume();

		String url = getURL2Load();
		webView.onResume();

		if (webView != null) getCookie();
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

	private void storeUrl(Context context, String url) {
/*		
		final SharedPreferences prefs = getActivity().getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		int appVersion =  getAppVersion(context);
		Log.i(TAG, "Page "+position+" Saving url on app version " + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(URL_ID, url);
		editor.putInt(APP_VERSION, appVersion);
		editor.commit();
*/
	}

	/*
	@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //相当于Fragment的onResume
        	if(webView!=null) {
        		boolean bhasnetwork = true;
    	    	if(bhasnetwork) {
//    	    		webView.onResume();
    	//    		loadOrRefetchURL(g_url);
    				//webView.onResume();
    	    		
    	    		String url = getURL2Load();
    	    		
    	    		if(!ACaching.isCacheValid(url)) {
    	    			Log.i(TAG, "Page "+position+" onResume call  loadwebpage="+url);
    	    			//loadWebPage(g_savedInstanceState, url);	 
    	    			loadOrRefetchURL(url);
    	    		}
    	    		else if(ACaching.isCacheValid(url)) {
    	    			Log.i(TAG, "Page "+position+" onResume call webview resume="+url);
    	    			webView.onResume();
    	    		}
    	    		else {
    	    			Log.i(TAG, "Page "+position+" onResume eee="+url);
    	    		}
    	    		
    	    		return;
    	    	}    	    
    	    }
        } else {
            //相当于Fragment的onPause
        	if (webView != null) {
//    	    	g_url = webView.getUrl();
 //   	    	webView.onPause();
    	    	Log.i(TAG, "Page "+position+" OnPause"); 
    	    }
        }
    }
	*/

	@Override
	public void onStop() {
//		swipe.getViewTreeObserver().removeOnScrollChangedListener(mOnScrollChangedListener);
		super.onStop();
		if (webView != null) {
			//g_url = webView.getUrl();
			storeUrl(context, g_url);
		}
//	    ACaching.saveMemoryCacheToCacheFolder(position);
//	    ACaching.getSaveCookie(position, 1);    
		Log.i(TAG, "Page " + position + " OnStop");
	}


	@Override
	public void onPause() {
		super.onPause();
		g_savedInstanceState = null;
		if (webView != null) {
//	    	g_url = webView.getUrl();
			webView.onPause();
			bOnPauseCalled = true;
			Log.i(TAG, "Page " + position + " OnPause");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Page " + position + " OnDestroy");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		Log.i("CPP", "Page " + position + " onConfigurationChanged");
        
  /*  
      if (webView != null)
      {
        // Remove the WebView from the old placeholder
        webViewPlaceholder.removeView(webView);
      }
   
       
      // Load the layout resource for the new configuration
      setContentView(R.layout.activity_main);
   
      // Reinitialize the UI
//      initUI();
 * 
 */
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		bundle.clear();
		if (webView.saveState(bundle) == null) {
			Log.i("CPP", "ERROR Page " + position + " fail to savestate in onSaveInstanceState");

		} else Log.i("CPP", "Page " + position + " onSaveInstanceState");
	}
    
/*    
    // should be done in MainActivity
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
                if(webView.canGoBack()){
                	webView.goBack();
                	//webView.reload();
                }else{
                    finish();
                }
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
*/

	public void getCookie() {
		try {
			CookieManager cookieManager = CookieManager.getInstance();
			g_cookie = cookieManager.getCookie(new URL(Config.HTTPS_SERVER_ROOT + "/").getHost());
			Log.i(TAG, "Page " + position + " Cookie=" + g_cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static boolean isAppOnForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getPackageName();
		List<RecentTaskInfo> appTask = activityManager.getRecentTasks(Integer.MAX_VALUE, 1);

		if (appTask == null) {
			return false;
		}

		if (appTask.get(0).baseIntent.toString().contains(packageName)) {
			return true;
		}
		return false;
	}

	public void startPingThread() {
		if (pingThread != null) return;

		Log.i(TAG, "Page " + position + " pingThread is creating");
		pingThread = new Thread(new Runnable() {

			public void run() {
				int code = 0;
				while (bCheckActiveInternetConnection) {
					try {
						Thread.sleep(1000 * 10);
					} catch (Exception e) {
						e.printStackTrace();
						//Log.i(TAG, "Page "+position+" Run Thread.sleep error");
					}

					try {
						URL u = new URL("http://www.google.com");
						HttpURLConnection huc = (HttpURLConnection) u.openConnection();
						huc.setConnectTimeout(10000);
						huc.setReadTimeout(10000);
						huc.setRequestMethod("GET");
						huc.connect();
						code = huc.getResponseCode();
		            	  
		           /* 	  	
		            	  if(isOnline()) code = 200;
		            	  else code = 404;
		           */
					} catch (Exception e) {
						code = 404;
						e.printStackTrace();
					}

//		              if(!bAlertDialogShown) code = 404;
//		              else code = 200;

//		              long diffInMillies = new Date().getTime() - dtime.getTime();
//		              if(diffInMillies/1000 >= 8) 
					{

						if (code == 200) {
							// hide alert dialog if it is being shown
							//if(alertDialog.isShowing())
							{
								bNetwork = true;
			            		 /*
			            		    dtime = new Date();
					    			mUiHandler1.post(new Runnable() {
					                    @Override
					                    public void run() {
					                    	nTime = 0;
					                       // if(alertDialog!=null) alertDialog.hide();
					                    }
					                });
								*/
							}
						} else {
							bNetwork = false;
			            	 /* 
			            	  if(alertDialog!=null && !alertDialog.isShowing()) 
			            	  //if(++nTime>=2)
			            	  {
					    			mUiHandler1.post(new Runnable() {
					                    @Override
					                    public void run() {
					                    	if(alertDialog != null) alertDialog.show();
					                        //Log.i(TAG, "Page "+position+" AlertDialog is shown");
					                    }
					                });
			            	  }
			            	  */
							if (isAppOnForeground(context)) {
								mUiHandler1.post(new Runnable() {
									@Override
									public void run() {
										Toast.makeText(mainactivity, "沒有互聯網連接, 請檢查您的互聯網連接", Toast.LENGTH_SHORT).show();
									}
								});
							}
						}
					}
				}

				pingThread = null;
			}


		});

		pingThread.start();


	}

	protected void initUI(Bundle savedInstanceState) {

		mimes = new HashMap<String, String>();
		mimes.put("html", "text/html");
		mimes.put("htm", "text/html");
		mimes.put("mp4", "video/mp4");
		mimes.put("ppt", "application/vnd.ms-powerpoint");
		mimes.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
		mimes.put("doc", "application/msword");
		mimes.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		mimes.put("xls", "application/vnd.ms-excel");
		mimes.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		mimes.put("gif", "image/gif");
		mimes.put("jpg", "image/jpeg");
		mimes.put("png", "image/png");
		mimes.put("txt", "text/plain");
		mimes.put("txtx", "text/plain");
		mimes.put("css", "text/css");
		mimes.put("js", "text/javascript");

		swipe = (SwipeRefreshLayout) getView().findViewById(R.id.swipe);
		swipe.setOnRefreshListener(this);
		swipe.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
		swipe.setDistanceToTriggerSync(150);
		swipe.setNestedScrollingEnabled(true);
//		swipe.setEnabled(false);

		((MainActivity) getActivity()).store_msg_user_onshow("");


		// Retrieve UI elements
		webView = ((WebView) getView().findViewById(R.id.webview));
//		webView = new SwipeWebView(context, null);
//		webView.setId(R.id.webview);

		webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
		webView.getSettings().setPluginState(android.webkit.WebSettings.PluginState.ON_DEMAND);


/*
		webView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
			@Override
			public void onScrollChanged() {
				if (webView.getScrollY() == 0) {
					swipe.setEnabled(true);

//					if (!swipe.isRefreshing()) {
//						swipe.setEnabled(true);
//						mUiHandler.postDelayed(new Runnable() {
//							@Override
//							public void run() {
//								if (swipe.isRefreshing()) {
//									swipe.setRefreshing(false);
//								}
//							}

//						}, 3000);
					}
//					webView.reload();



//						((MainActivity) getActivity()).runOnUiThread(new Runnable() {
//							@Override
//							public void run() {
//								if(!swipe.isRefreshing()) {
//									swipe.setEnabled(true);

//									try {
//										Thread.sleep(1000);
//									} catch (InterruptedException e) {
//									}


//									swipe.setRefreshing(false);
//									webView.reload();
//								}
//							}
//						});

				} else {
					swipe.setEnabled(false);
				//	SwipeRefreshLayout swipe = ((MainActivity)getActivity()).returnSwipe();
				//	if(swipe.isRefreshing()) swipe.setEnabled(false);
				//	if(swipe.isRefreshing()) swipe.setEnabled(false);
				}
			}
		});
*/





		// Initialize the WebView if necessary
//	    if (webView == null)
		boolean bBundle = false;
		{

			ProgressBar spinner = (ProgressBar) getView().findViewById(R.id.progress1);
			spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#80DAEB"), android.graphics.PorterDuff.Mode.MULTIPLY);


			//    if(webView.restoreState(bundle)!=null) bBundle = true;


			// Create the webview
			//    webView = new WebView(this);
			//      webView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			webView.getSettings().setSupportZoom(true);
			//      webView.getSettings().setBuiltInZoomControls(true);
			webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
			webView.setScrollbarFadingEnabled(true);
			webView.getSettings().setLoadsImagesAutomatically(true);

			// Load the URLs inside the WebView, not in the external web browser
			//    webView.setWebViewClient(new WebViewClient());
			WebSettings webSettings = webView.getSettings();
			if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
				//	      webSettings.setAllowFileAccessFromFileURLs(true);
				//	      webSettings.setAllowUniversalAccessFromFileURLs(true);
			}

			webSettings.setJavaScriptEnabled(true);

			webView.setWebChromeClient(new WebChromeClient() {
				/*
                                public boolean onConsoleMessage(ConsoleMessage cm) {

                                    return true;
                                }
                */
				@Override
				public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {

					DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch (which) {
								case DialogInterface.BUTTON_POSITIVE:
									//Yes button clicked
									result.confirm();
									break;

								case DialogInterface.BUTTON_NEGATIVE:
									//No button clicked
									result.cancel();
									break;
							}
						}
					};


					new AlertDialog.Builder(getActivity())
							.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + getResources().getText(R.string.app_name) + "</font>"))
							.setMessage(message)
							.setNegativeButton(android.R.string.cancel, dialogClickListener)
							.setPositiveButton(android.R.string.ok, dialogClickListener)
							.setCancelable(false)
							.create()
							.show();
					return true;
				}

				// For Android 5.0
				public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
					// Double check that we don't have any existing callbacks
					if (mFilePathCallback != null) {
						mFilePathCallback.onReceiveValue(null);
					}
					mFilePathCallback = filePath;
					Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
					if (takeVideoIntent.resolveActivity(((MainActivity) getActivity()).getPackageManager()) != null) {
						// Create the File where the photo should go
						videoFile = null;
						try {
							videoFile = createVideoFile();
							//	takeVideoIntent.putExtra("PhotoPath", mCameraPhotoPath);
						} catch (IOException ex) {
							// Error occurred while creating the File
							Log.e(TAG, "Unable to create Image File", ex);
						}
						// Continue only if the File was successfully created
						if (videoFile != null) {
							mCameraVideoPath = "file:" + videoFile.getAbsolutePath();
							takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(videoFile));
						} else {
							takeVideoIntent = null;
						}
					}

					Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					if (takePictureIntent.resolveActivity(((MainActivity) getActivity()).getPackageManager()) != null) {
						// Create the File where the photo should go
						photoFile = null;
						try {
							photoFile = createImageFile();
							//	takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
						} catch (IOException ex) {
							// Error occurred while creating the File
							Log.e(TAG, "Unable to create Image File", ex);
						}
						// Continue only if the File was successfully created
						if (photoFile != null) {
							mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
							takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(photoFile));
						} else {
							takePictureIntent = null;
						}
					}
					Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
					contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
					contentSelectionIntent.setType("*/*");
					Intent[] intentArray;
					if (takePictureIntent != null) {
						intentArray = new Intent[]{takePictureIntent, takeVideoIntent};
					} else {
						intentArray = new Intent[0];
					}
					Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
					chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
					chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
					chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
					startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);
					return true;
				}

				// openFileChooser for Android 3.0+
				public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
					mUploadMessage = uploadMsg;
					// Create AndroidExampleFolder at sdcard
					// Create AndroidExampleFolder at sdcard
					File imageStorageDir = new File(
							Environment.getExternalStoragePublicDirectory(
									Environment.DIRECTORY_PICTURES)
							, "AndroidExampleFolder");
					if (!imageStorageDir.exists()) {
						// Create AndroidExampleFolder at sdcard
						imageStorageDir.mkdirs();
					}


/*
					File file1 = new File(
							imageStorageDir + File.separator + "MP4_"
									+ String.valueOf(System.currentTimeMillis())
									+ ".mp4");
					mCapturedImageURI = Uri.fromFile(file1);
					// Camera capture image intent

					final Intent captureVideoIntent = new Intent(
							android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
					captureVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
*/

					// Create camera captured image file path and name
					File file = new File(
							imageStorageDir + File.separator + "IMG_"
									+ String.valueOf(System.currentTimeMillis())
									+ ".jpg");
					mCapturedImageURI = Uri.fromFile(file);
					// Camera capture image intent

					final Intent captureIntent = new Intent(
							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
					captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

					Intent i = new Intent(Intent.ACTION_GET_CONTENT);
					i.addCategory(Intent.CATEGORY_OPENABLE);
					i.setType("image/*");
					// Create file chooser intent
					Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
					// Set camera intent to file chooser
					chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
							, new Parcelable[]{captureIntent});
					// On select image call onActivityResult method of activity
					startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
				}

				// openFileChooser for Android < 3.0
				public void openFileChooser(ValueCallback<Uri> uploadMsg) {
					openFileChooser(uploadMsg, "");
				}

				//openFileChooser for other Android versions
				public void openFileChooser(ValueCallback<Uri> uploadMsg,
											String acceptType,
											String capture) {
					openFileChooser(uploadMsg, acceptType);
				}

				public boolean onConsoleMessage(ConsoleMessage cm) {

					onConsoleMessage(cm.message(), cm.lineNumber(), cm.sourceId());
					return true;
				}

				public void onConsoleMessage(String message, int lineNumber, String sourceID) {
					//Log.d("androidruntime", "Show console messages, Used for debugging: " + message);

				}
			});


			webView.setWebViewClient(new WebViewClient() {
				@Override
				public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
					WebResourceResponse asset = checkAsset(url);
					if(asset==null) {


					}

					return asset;
				}

				@SuppressLint("NewApi")
				@Override
				public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
					if(true) return null;

					if (request == null || request.getUrl() == null) return null;
					String url = request.getUrl().toString();
					if(url.indexOf(Config.HTTPS_SERVER_ROOT)>=0) {
						int pos = url.indexOf("?");
						if(pos>0) url = url.substring(0, pos);
					}


					WebResourceResponse asset = checkAsset(url);
					if(asset==null) {
//						if (! (url.startsWith("http://") || url.startsWith("https://")) || ResponseCache.getDefault() == null) return null;
						if (! (url.startsWith("http://") || url.startsWith("https://"))) {
							return null;
						}



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

						if(!ConnectionDetector.isConnectingToInternet(context)) {
							try {
								final HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
								connection.setHostnameVerifier(DO_NOT_VERIFY);

							//	connection.addRequestProperty("Cache-Control", "only-if-cached");
								int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
								connection.addRequestProperty("Cache-Control", "max-stale=" + maxStale);

								connection.connect();

								String content_type = connection.getContentType();
								if(content_type==null) {
									content_type = "font/woff2";
								}
								final String separator = "; charset=";
								final int pos = content_type.indexOf(separator);    // TODO: Better protocol compatibility
								final String mime_type = pos >= 0 ? content_type.substring(0, pos) : content_type;
								final String encoding = pos >= 0 ? content_type.substring(pos + separator.length()) : "UTF-8";

								InputStream is = connection.getInputStream();

								return new WebResourceResponse(mime_type, encoding, is);
								// the resource was cached! show it
							} catch (FileNotFoundException e) {
								e.printStackTrace(); return null;
								// the resource was not cached
							} catch (Exception me) {
								me.printStackTrace(); return null;
							}
						}
						else {

							try {
								final HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
								connection.addRequestProperty("Cache-Control", "max-age=3600");
								connection.setHostnameVerifier(DO_NOT_VERIFY);

								connection.connect();
								String content_type = connection.getContentType();
								if(content_type==null) {
									content_type = "font/woff2";
								}

								final String separator = "; charset=";
								final int pos = content_type.indexOf(separator);    // TODO: Better protocol compatibility
								final String mime_type = pos >= 0 ? content_type.substring(0, pos) : content_type;
								final String encoding = pos >= 0 ? content_type.substring(pos + separator.length()) : "UTF-8";
								return new WebResourceResponse(mime_type, encoding, connection.getInputStream());
							} catch (final Exception e) {
								e.printStackTrace();
								return null;
							}

						//	return null;
						}
					}
					else return asset;
				}

				private WebResourceResponse checkAsset(String url) {
					String u = url;
					u = u.replace(Config.HTTPS_SERVER_ROOT + "/", "");
					if (u.equals("")) return null;

					int pos = u.indexOf("?");
					if(pos>0)
						u = u.substring(0, pos);

					String ext = u.substring(u.lastIndexOf(".") + 1);
					ext = mimes.get(ext.toLowerCase());
					if (!ACaching.hasAssetCache(u)) {
						return null;
					}
					WebResourceResponse r = loadFromAssets(u, ext, "UTF8");
					if(r==null) {
						return null;
					}
					else {
						Log.i("yocleppp", "Asset found "+u);
						return r;
					}
				}

				private WebResourceResponse loadFromAssets(String asset_path, String mimeType, String encoding) {
					InputStream input = null;
					try {
						input = assetManager.open(asset_path);
						WebResourceResponse response =
								new WebResourceResponse(mimeType, encoding, input);

						return response;
					} catch (IOException e) {
					}
					return null;
				}

				@Override
				public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
					//	super.onReceivedSslError(view, handler, error);
					// this will ignore the Ssl error and will go forward to your site
					handler.proceed();
				}

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					Log.i(TAG, "Page " + position + " shouldOverrideUrlLoading url=" + url);
/*
					if (url.indexOf(Config.HTTPS_SERVER_ROOT) == -1) {
						Intent intent = new Intent(mainactivity, WebViewActivity.class);
						intent.putExtra("message", url);
						mainactivity.startActivity(intent);
						return true;
					}
*/
					return false;
				}

				@Override
				public void onReceivedError(WebView view, int errorCode,
											String description, String failingUrl) {
					if (errorCode == ERROR_TIMEOUT) {
						view.stopLoading();  // may not be needed
						String html = getReloadURL(failingUrl);
						view.loadData(html, "text/html; charset=utf-8", "utf-8");
					}
				}

				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon) {
					Log.i(TAG, "Page " + position + " onPageStarted url=" + url);
					super.onPageStarted(view, url, favicon);

					View v = getView();
					if (v == null) {
						Log.i(TAG, "ERROR Page " + position + " onPageStarted empty view");
						return;

					}
					v.findViewById(R.id.progress1).setVisibility(View.VISIBLE);
				}

				@Override
				public void onPageFinished(WebView view, String url) {
					super.onPageFinished(view, url);
					bPageFinished = true;
					View v = getView();
					if (v == null) return;

					if(swipe.isRefreshing()) swipe.setRefreshing(false);

					if (url.indexOf(Config.HTTPS_SERVER_ROOT) >= 0)
						webView.clearHistory();

					v.findViewById(R.id.progress1).setVisibility(View.GONE);
				}
			});

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//	webView.setWebContentsDebuggingEnabled(true);
			}

			webView.addJavascriptInterface(new WebViewJavaScriptInterface(getActivity()), "app");

			{
				dtime = new Date();
				webView.getSettings().setDomStorageEnabled(true);

				// Set cache size to 8 mb by default. should be more than enough
				webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);

				// This next one is crazy. It's the DEFAULT location for your app's cache
				// But it didn't work for me without this line
				File dir = getActivity().getCacheDir();
				if (!dir.exists()) {
					dir.mkdir();
				}
				Log.i(TAG, "Page " + position + " Cache dir = " + dir.getPath());
				webView.getSettings().setAppCachePath(dir.getPath());
				webView.getSettings().setAllowFileAccess(true);
				webView.getSettings().setAppCacheEnabled(true);
				//      webView.getSettings().setRenderPriority(RenderPriority.HIGH);

				webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

/*
				if (Build.VERSION.SDK_INT >= 21) {
//					webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
					webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
				} else if (Build.VERSION.SDK_INT >= 11) {
					webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
				}
*/

				if (Build.VERSION.SDK_INT >= 19) {
					webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
				}
				else if(Build.VERSION.SDK_INT >=11 && Build.VERSION.SDK_INT < 19) {
					webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
				}


				alertDialog = new AlertDialog.Builder(getActivity()).create();
				alertDialog.setTitle(Html.fromHtml("<font color='#FFFFFF'>錯誤</font>"));
				//	alertDialog.setTitle("錯誤 ");
				alertDialog.setMessage("沒有互聯網連接, 請檢查您的互聯網連接");
				alertDialog.setButton("確定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dtime = new Date();
						nTime = 0;
					}
				});


			}

		}

		if (webView.restoreState(bundle) != null) {
			Log.i("CPP", "InitUI webview.restorestate position=" + position);
		} else
		{
			if (!((MainActivity) getActivity()).checkIfNotificationMessage()) {
				String url = getURL2Load();
				Log.i(TAG, "Page " + position + " InitUI to to load url by calling loadwebpage=" + url);
				loadWebPage(savedInstanceState, url);
			}
			else {
				String url = getURL2Load();
				loadWebPage(savedInstanceState, url);
			}
		}
	}

	public String getURL2Load() {

		String msg = getActivity().getIntent().getStringExtra("message");
		String url = Config.HTTPS_SERVER_ROOT;

		if (msg != null) {
			getActivity().getIntent().removeExtra("message");
			url = Config.HTTPS_SERVER_ROOT + msg;
			Log.i(TAG, "Page " + position + " OnStart msg URL = " + url);
		} else if (g_url != null) url = g_url;
		else {
			String lasturl = getLastUrl(context);
			if (lasturl != null) url = lasturl;
		}

//		if(url.equals("data:text/html,chromewebdata")) url = Config.HTTPS_SERVER_ROOT;
		if (url.startsWith("data:text/html")) {
			Log.i(TAG, "EEE TEXT");
			url = Config.HTTPS_SERVER_ROOT;
		}

		if (url.equals(Config.HTTPS_SERVER_ROOT)) {
			Log.i(TAG, "Page " + position + "ERROR geturl2load return server root");
		}
		return url;

	}

	private String getLastUrl(Context context) {
		if (true) return g_url;

		final SharedPreferences prefs = getActivity().getSharedPreferences(
				MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
		String url = prefs.getString(URL_ID, "");
		if (url.isEmpty()) {
			//	Log.i(TAG, "Page "+position+" Url not found.");
			return null;
		}
		return url;
	}

	/*
        private void fetchRemoteURl(final String url) {
		
            new AsyncTask<Void, Void, String>() {
                String html;
                @Override
                protected String doInBackground(Void... params) {
                    try {
                        html = getRemoteContent(url);
					
                        int i = html.length();
									
                //		Log.i(TAG, "Page "+position+" QQQ "+url+"\n"+html.substring(i-500, i));
                        synchronized(lock) {
                            html_source = html;
                            lock.notify();
                        }
                    } catch (Exception ex) {
                    }
                    return "1";
                }

                @Override
                protected void onPostExecute(String msg) {
				
                }
            }.execute(null, null, null);
		
        }

        public String getRemoteContent(String url) throws IOException {
            CookieManager cookieManager = CookieManager.getInstance();
		
            String cookie = null;
         //   if(url.indexOf("adiai.js")>=0) cookie = g_cookie;
         //   else

            cookie = cookieManager.getCookie(new URL(url).getHost());
            if(cookie!=null && cookie.equals("")) cookie = null;
        
            String ts = String.valueOf(new Date().getTime());
            URL u = new URL(url);
      //      URL u = new URL(url+"?r="+ts);
           Log.i(TAG, "Page "+position+" Refetching url "+url+" with cookie="+cookie);
            URLConnection uc = u.openConnection();
            if(cookie!=null) uc.setRequestProperty("Cookie", cookie);
            uc.setConnectTimeout(30000);
            uc.setReadTimeout(30000);

        

            InputStream is = uc.getInputStream();
    //        Map<String, List<String>> responseheaders = uc.getHeaderFields();
        
        
            String r_cookie = uc.getHeaderField("Set-Cookie");
        
            if(r_cookie!=null && !r_cookie.equals("")) {
                g_cookie = r_cookie;
                int i = 0;
                i = g_cookie.indexOf(";");
                if(i>0) g_cookie = g_cookie.substring(0, i+1);
        	
                cookieManager.setCookie(url, g_cookie);
            }
        
            Log.i(TAG, "Page "+position+" Return cookie 2="+g_cookie);
        
        		
            ByteArrayOutputStream outputDoc = new ByteArrayOutputStream();
            byte buf[]=new byte[1024];
            int len;
            while((len=is.read(buf))>0)
            {
             outputDoc.write(buf,0, len);
            }
        
            byte[] content = outputDoc.toByteArray();
            outputDoc.close();
        
            return new String(content, "UTF-8");
        }
    */
	protected void loadOrRefetchURL(String url) {
			((MainActivity) getActivity()).storeCookie(((MainActivity) getActivity()).context, ACaching.g_cookie);
			webView.loadUrl(url);
	}

	protected void loadURL(String url) {
		g_url = url;
		webView.loadUrl(url);
	}

/*
	public void showFBLoginButton(final boolean isLogoned) {
		LikeView likeView = (LikeView) getView().findViewById(R.id.like_view);
		likeView.setVisibility(View.GONE);
		ImageButton fblogin = (ImageButton) getView().findViewById(R.id.fblogin);
		fblogin.setVisibility(View.VISIBLE);
		fblogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!isLogoned) {
					showAlertDialog("<font color='#FFFFFF'>請先登錄</font>", "Before you can share to social network platform, please login the app first. If you don't have account, please create an account first");
					return;
				}
				int networkId = FacebookSocialNetwork.ID;
				SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
				if (!socialNetwork.isConnected()) {
					if (networkId != 0) {
						sn_action = SN_LOGIN;
						((MainActivity)getActivity()).storeSocialNetworkAction(((MainActivity) getActivity()).context, SN_LOGIN);
						socialNetwork.requestLogin();
						MainActivity.showProgress("請稍候");
					}
				}
			}
		});


	}
*/


/*
	public boolean isSocialNetworkConnected(int networkId) {
		SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
		if (socialNetwork.isConnected()) return true;
		else return false;
	}


	private Session.StatusCallback mStatusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
						 Exception exception) {
			if (state.isOpened()) {
				Log.d("FacebookSampleActivity", "Facebook session opened");
			} else if (state.isClosed()) {
				Log.d("FacebookSampleActivity", "Facebook session closed");
			}
		}
	};

	public void logoutSocialNetwork() {
		int networkId = FacebookSocialNetwork.ID;
		SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
		if (socialNetwork.isConnected()) {
			socialNetwork.logout();
		}
	}
*/

	public boolean isNetworkStatusAvialable(Context context) {
		if(true) return true;

		if (connectivityManager == null)
			connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager != null) {
			NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
			if (netInfos != null)
				if (netInfos.isConnected() && bNetwork)
					return true;
				else return false;
		}
		return false;
	}

	public void loadWebPage(Bundle savedInstanceState, String url) {
		if (isNetworkStatusAvialable(context)) {
				webView.loadUrl(url);
		} else {
				String html = getReloadURL(url);
				webView.loadData(html, "text/html; charset=utf-8", "utf-8");

		}
	}

	public void loadWebPage() {
		if (isNetworkStatusAvialable(context)) {
				webView.loadUrl(g_url);
		} else {
				String html = getReloadURL(g_url);
				webView.loadData(html, "text/html; charset=utf-8", "utf-8");
		}
	}


	public String getReloadURL(String url) {
		String file = null;
		try {
			URL aURL = new URL(url);
			String uri = aURL.getPath();
			file = Config.HTTPS_SERVER_ROOT + uri;
		} catch (Exception e) {
			file = Config.HTTPS_SERVER_ROOT + "/";
		}
		return "<html><head><meta charset=\"UTF-8\"><script> function retry() { window.location.href=\"" + file + "\"; }</script></head><body><b>錯誤 - 沒有互聯網連接</b><br><br><form><input type=\"button\" value=\"請按這裡重試\" onClick=\"retry()\"></form></body></html>";
	}

	public void playVideo(String video_id) {
		Intent intent = null;
		intent = YouTubeStandalonePlayer.createVideoIntent(
				getActivity(), DeveloperKey.DEVELOPER_KEY, video_id, 0, true, false);

		if (intent != null) {
			if (canResolveIntent(intent)) {
				startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
			} else {
				// Could not resolve the intent - must need to install or update the YouTube API service.
				YouTubeInitializationResult.SERVICE_MISSING
						.getErrorDialog(getActivity(), REQ_RESOLVE_SERVICE_MISSING).show();
			}
		}
	}

	public void playPrivateVideo(String path) {

		Intent videoPlaybackActivity = new Intent(getActivity(), MyVideoPlayer.class);
		videoPlaybackActivity.putExtra("video_url", Config.HTTPS_SERVER_ROOT + path);
		startActivity(videoPlaybackActivity);
	}


	private boolean canResolveIntent(Intent intent) {
		List<ResolveInfo> resolveInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
		return resolveInfo != null && !resolveInfo.isEmpty();
	}

	public class WebViewJavaScriptInterface {

		private Context context;

		/*
         * Need a reference to the context in order to sent a post message
         */
		public WebViewJavaScriptInterface(Context context) {
			this.context = context;
		}

        /* 
         * This method can be called from Android. @JavascriptInterface 
         * required after SDK version 17. 
         */


		@JavascriptInterface
		public void cmenu(final String jsonStr) {
			((MainActivity) getActivity()).createCMenuFromJS(jsonStr);
		}

		@JavascriptInterface
		public void showhideactionbar(final int i) {
			mUiHandler1.post(new Runnable() {
				@Override
				public void run() {
					if (i == 1) ((MainActivity) getActivity()).showActionBar();
					else ((MainActivity) getActivity()).hideActionBar();
				}
			});
		}


		@JavascriptInterface
		public void enabledisablepullrefresh(final int i) {
			mUiHandler1.post(new Runnable() {
				@Override
				public void run() {
					if(i==1) swipe.setEnabled(true);
					else swipe.setEnabled(false);
				}
			});
		}

		@JavascriptInterface
		public void setmsguseronshow(final String uid) {
			((MainActivity) getActivity()).store_msg_user_onshow(uid);
			/*
				mUiHandler1.post(new Runnable() {
					@Override
					public void run() {
						store_msg_user_onshow(uid);
					}
				});
			*/
		}



		@JavascriptInterface
		public void showErrorDialog(final String err, final String m1, final String m3, final String m6, final String m12) {
			mUiHandler1.post(new Runnable() {
				@Override
				public void run() {
					if (Integer.parseInt(err) == -1)
						CustomDialog.LoginCancelDialog(getActivity());
					else if (Integer.parseInt(err) == 0) { // show only the pay membership
						getCookie();
						CustomDialog.BuyMembershipDialog(getActivity(), 0, Integer.parseInt(m1), Integer.parseInt(m3), Integer.parseInt(m6), Integer.parseInt(m12));
					}
				}
			});
		}



		@JavascriptInterface
		public void showPortfolio(String type) {
			Intent intent = new Intent(mainactivity, SwipeActivity.class);
			if(Integer.parseInt(type)==1) {
				intent.putExtra("uri1", "growthconfirm.html");
				intent.putExtra("uri2", "growthobserve.html");
				intent.putExtra("title", getResources().getString(R.string.growthportfolio));
			}
			else {
				intent.putExtra("uri1", "interestconfirm.html");
				intent.putExtra("uri2", "interestobserve.html");
				intent.putExtra("title", getResources().getString(R.string.interestportfolio));
			}
			mainactivity.startActivity(intent);
		}


		@JavascriptInterface
		public void doMapping(final String uid, final String name, final String msg) {

			getCookie();
			// reset memorycach for all files under /member/
			ACaching.removeSpecialCaches(position);

			mUiHandler1.post(new Runnable() {
				@Override
				public void run() {
					AlertDialog alert = new AlertDialog.Builder(mainactivity).create();
					alert.setTitle(Html.fromHtml("<font color='#FFFFFF'>登入成功</font>"));
					//	alert.setTitle("登入成功");
					alert.setMessage(msg);
					alert.setButton("確定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							//	webView.reload();
							((MainActivity) getActivity()).storeUserInfo(((MainActivity) getActivity()).context, uid, name);
							((MainActivity) getActivity()).showHomePageAfterLogin(name);
						}
					});
					alert.show();
				}
			});


//        	if (TextUtils.isEmpty(suid) || !suid.equals(uid)) {
//        	if(((MainActivity)getActivity()).bGCMRegisterSuccess) {
			if (((MainActivity) getActivity()).regId != null && uid != null) {
				Log.i(TAG, "Page " + position + " bGCMRegisterSuccess is true, so to mapuser");
				((MainActivity) getActivity()).bGCMRegisterSuccess = false;
				String r = "uid=" + uid + "&nid=" + ((MainActivity) getActivity()).regId + "&device=android";
				((MainActivity) getActivity()).mapUserInBackground(r);
				((MainActivity) getActivity()).storeUserInfo(((MainActivity) getActivity()).context, uid, name);
			}
		}


		@JavascriptInterface
		public void showInfoDialog(final String title, final String message) {
			showDialog1(title, message);
		}


		@JavascriptInterface
		public void showbrdialog() {
			CustomDialog.RedeemCancelDialog(getActivity(), 0, g_cookie);
		}


		@JavascriptInterface
		public void showeuladialog() {
			new SimpleEula(mainactivity, webView).show1();
		}

		@JavascriptInterface
		public void toShare(final String socialnetwork, final String iid, final String imguri) {

			if (!((MainActivity) getActivity()).isLogoned()) {
				//	showAlertDialog("<font color='#FFFFFF'>請先登錄</font>", "Before you can share to social network platform, please login the app first. If you don't have account, please create an account first");
				mUiHandler1.post(new Runnable() {
					@Override
					public void run() {
						CustomDialog.LoginCancelDialog_share(getActivity());
					}
				});
				return;
			}

			getCookie();

			g_shareimguri = imguri;

			JsonHttpUtil json = new JsonHttpUtil(context, new FragmentJsonCallback() {
				@Override
				public void onTaskDone(String rv) {
					sharetoken = GetShareToken_Callback(rv);

					if (!sharetoken.error.equals("")) {
						((MainActivity) getActivity()).showToast(getResources().getString(R.string.shareerror));
					} else {
//						mUiHandler1.post(new Runnable() {
//							@Override
//							public void run() {


						if(socialnetwork.equals("1")) { // facebook
							final String textshare = sharetoken.share_message;

							String link0 = "";
							if(sharetoken!=null) {
								//		link0 = Config.HTTPS_SERVER_ROOT+"/"+util.MD5(""+sharetoken.id).toLowerCase()+sharetoken.share_uri;
								link0 = Config.HTTP_SERVER_SHARE_ROOT+"/"+util.MD5(""+sharetoken.id).toLowerCase()+sharetoken.share_uri;
							}

							final String link = link0;

							if (ShareDialog.canShow(ShareLinkContent.class)) {
									ShareLinkContent linkContent = new ShareLinkContent.Builder()
											.setContentTitle(getResources().getString(R.string.adiai))
											.setContentDescription(textshare)
											.setContentUrl(Uri.parse(link0))
											.setImageUrl(Uri.parse(Config.HTTP_SERVER_SHARE_ROOT + g_shareimguri))
											.build();

									shareDialog.show(linkContent);
							}
						}
						else {
							Intent intent = new Intent(getActivity(), EditTextActivity.class);
							intent.putExtra("defaulttext", sharetoken.share_message);
							intent.putExtra("socialnetwork", String.valueOf(socialnetwork));
							intent.putExtra("imguri", imguri);
							startActivityForResult(intent, REQ_SOCIAL_NETWORK_SHARE);
						}
//							}});

					}
				}
			}, 1);

			json.execute(Config.HTTPS_SERVER_ROOT + "/invest/servlet/sharegentoken", g_cookie, "iid=" + iid + "&media=" + Config.socialnetworkmediaids[Integer.valueOf(socialnetwork).intValue()]);


		}

		@JavascriptInterface
		public void visitURL(String url) {
			webView.loadUrl(Config.HTTPS_SERVER_ROOT + url);
		}

		@JavascriptInterface
		public void test(String msg) {
			Log.i(TAG, "Page " + position + ">>" + msg);
		}

		@JavascriptInterface
		public void doLogout(String uid) {
			ACaching.removeSpecialCaches(position);

			getCookie();

			final String username = uid;
			mUiHandler1.post(new Runnable() {
				@Override
				public void run() {
					AlertDialog alert = new AlertDialog.Builder(mainactivity).create();
					alert.setTitle("登出");
					alert.setMessage("您已成功登出, 謝謝使用!");
					alert.setButton("確定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							loadOrRefetchURL(Config.HTTPS_SERVER_ROOT + "/Index.html");
						}
					});
					alert.show();
				}
			});


		}

		@JavascriptInterface
		public void showInNewWindow(final String page) {
			Intent intent = new Intent(mainactivity, WebViewActivity.class);

			boolean bABCDEF = (page.indexOf("abcdef/")==0)?true:false;
			boolean bProperty = (page.indexOf("property/")==0)?true:false;

			if(bProperty || page.indexOf("/notification/")==0 || page.indexOf("http") == 0|| bABCDEF || page.indexOf("services.html")==0 || page.indexOf("timetable.html")==0) {
				if (page.indexOf("http") == 0)
					intent.putExtra("message", page);
				else intent.putExtra("message", Config.HTTPS_SERVER_ROOT + "/" + page);


				if(bABCDEF) {
					String title = "";
					char c = page.charAt(7);
					if(c=='a') title = getResources().getString(R.string.a_readerhtml);
					if(c=='b') title = getResources().getString(R.string.b_adiaiwordshtml);
					if(c=='c') title = getResources().getString(R.string.c_newuserhtml);
					if(c=='d') title = getResources().getString(R.string.d_famouspeoplehtml);
					if(c=='e') title = getResources().getString(R.string.e_smallstoryhtml);
					if(c=='f') title = getResources().getString(R.string.f_hinthtml);
					intent.putExtra("title", title);
				}
				else if(bProperty) {
					intent.putExtra("title", getResources().getString(R.string.property));
				}
				else if(page.indexOf("services.html")==0 || page.indexOf("timetable.html")==0) {
					intent.putExtra("title", getResources().getString(R.string.course));
				}

				mainactivity.startActivity(intent);
				return;
			}

			String s = page.replace(".","");
			int resID = getResources().getIdentifier(s, "string", mainactivity.getPackageName());
			final String title = getResources().getString(resID);

			mainactivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mainactivity.setTitle(title);
					webView.loadUrl(Config.HTTPS_SERVER_ROOT + "/" + page);
				}
			});

		}


		@JavascriptInterface
		public void showInNewBrowser(String page) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(page));
			mainactivity.startActivity(browserIntent);
		}

		@JavascriptInterface
		public void showPage(String page) {
			int num = 0;
			for (int i = 0; i < ((MainActivity) getActivity()).urls.length; i++) {
				if (((MainActivity) getActivity()).urls[i].equals(page)) {
					num = i;
					break;
				}
			}
			((MainActivity) getActivity()).viewPager.setCurrentItem(num);
		}

		@JavascriptInterface
		public void playyoutube(String video_id) {
			playVideo(video_id);
		}

		@JavascriptInterface
		public void playvideo(String path) {
			playPrivateVideo(path);
		}

		@JavascriptInterface
		public void setUid(String uid) {
			jsUid = uid;
		}

		@JavascriptInterface
		public void setHtmlSource(String html) {
			Log.i(TAG, "Page " + position + " sethtmlsource");
//        	synchronized(lock) {
			String rv[] = html.split("--adiaponglist2018--");
			if (rv.length == 2 && !rv[1].equals("")) {
				html_source = "<!DOCTYPE html><html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">" + rv[1] + "</html>";
				ACaching.updateCacheIfNotFound(position, rv[0], html_source);
				Log.i(TAG, "Page " + position + " memorycache added with key " + rv[0]);
				//lock.notify();
			}
			getCookie();
		}

		@JavascriptInterface
		public void downloadFile(String file) {
			try {
				String url = Config.HTTPS_SERVER_ROOT + file;
				CookieManager cookieManager = CookieManager.getInstance();
				String cookie = cookieManager.getCookie(new URL(url).getHost());
				Log.i(TAG, "Page " + position + " downloadFile cookiemanager cookie=" + cookie);
				int i = url.lastIndexOf(".");
				if (i == -1) return;
				String ext = url.substring(i + 1);
				if (ext.indexOf("?") > 0) ext = ext.substring(0, ext.indexOf("?"));
				if (ext.equals("pptx") || ext.equals("xlsx") || ext.equals("docx") || ext.equals("ppt") || ext.equals("xls") || ext.equals("doc") || ext.equals("pdf") || ext.equals("mp44")
						|| ext.equals("mp33")) {
//			       downloadFileInBackground(url, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), cookie);
					downloadFileInBackground(url, getActivity().getCacheDir().getAbsolutePath(), cookie);

//			       downloadFileInBackground(url, doc_store_folder, cookie);
				}

			} catch (Exception e) {
				e.printStackTrace(System.out);
				Log.e("Adia", e.toString());

			}
		}


		@JavascriptInterface
		public void newwin(final String url, final String jsfunc) {
			Intent intent = new Intent(mainactivity, WebViewActivity.class);

			intent.putExtra("url", url);
			intent.putExtra("jsfunc", jsfunc);
			intent.putExtra("title", getResources().getString(R.string.home_title));
			mainactivity.startActivityForResult(intent, Config.COMMAND_BACKWIN);
		}

		@JavascriptInterface
		public void changeprofile(final String jsonStr) { // i=1, no login, i=2, show logoned profile
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);
				final int i = jsonObj.getInt("status");
				final String uri = jsonObj.getString("uri");
				final String name = jsonObj.getString("name");


				mUiHandler1.post(new Runnable() {
				@Override
				public void run() {
					((MainActivity) getActivity()).changeProfile(i, uri, name);
				}
				});
			}
			catch(Exception e) {}
		}

	}

	private void showDialog1(final String title, final String message) {

		mUiHandler1.post(new
								 Runnable() {
									 @Override
									 public void run() {
										 AlertDialog alert = new AlertDialog.Builder(mainactivity).create();
										 alert.setTitle(Html.fromHtml("<font color='#FFFFFF'>" + title + "</font>"));
										 alert.setMessage(Html.fromHtml("<font color='#FFFFFF'>" + message + "</font>"));
										 alert.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
											 public void onClick(DialogInterface dialog, int which) {

											 }
										 });
										 alert.show();
									 }
								 }
		);
	}
	private AlertDialog.Builder alertDialogInit(String title, String message){
		AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
		ad.setTitle(Html.fromHtml("<font color='#FFFFFF'>"+title+"</font>"));
		ad.setMessage(message);
		ad.setCancelable(true);
		return ad;
	}
/*
	private OnPostingCompleteListener postingComplete = new OnPostingCompleteListener() {
		@Override
		public void onPostSuccessfully(int socialNetworkID) {
			MainActivity.hideProgress();
			if(sharetoken!=null) {
				getCookie();

				JsonHttpUtil json = new JsonHttpUtil(context, new FragmentJsonCallback() {
					@Override
					public void onTaskDone(String rv) {
						sharetoken = Share_Callback(rv);
						sharetoken = null;
					}
				}, 2);

//				json.execute(Config.HTTPS_SERVER_ROOT + "/invest/servlet/shareshare", g_cookie, "id=" + sharetoken.id + "&source=" + sharetoken.utm_source + "&medium=" + sharetoken.utm_medium + "&campaign=" + sharetoken.utm_campaign);
				json.execute(Config.HTTPS_SERVER_ROOT + "/invest/servlet/shareshare", g_cookie, "id=" + sharetoken.id + "&source=" + sharetoken.utm_source + "&medium=" + sharetoken.utm_medium + "&campaign=" + sharetoken.utm_campaign);

			}

			Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
			Toast.makeText(getActivity(), "分享失敗: " + errorMessage, Toast.LENGTH_LONG).show();
		}
	};
*/

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


		if(requestCode == Config.COMMAND_BACKWIN) {
			Bundle extras = data.getExtras();
			String jsfunc = extras.getString("jsfunc");
			if(jsfunc != null && !jsfunc.equals("")) {

			}

		}
		else if (requestCode == REQ_SOCIAL_NETWORK_SHARE) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle extras = data.getExtras();

				String socialnetwork = extras.getString("socialnetwork");
				final String textshare = extras.getString("textshare");

				String link0 = "";
				if(sharetoken!=null) {
			//		link0 = Config.HTTPS_SERVER_ROOT+"/"+util.MD5(""+sharetoken.id).toLowerCase()+sharetoken.share_uri;
					link0 = Config.HTTP_SERVER_SHARE_ROOT+"/"+util.MD5(""+sharetoken.id).toLowerCase()+sharetoken.share_uri;

				}

				final String link = link0;

				if (socialnetwork.equals("1")) {

					if (ShareDialog.canShow(ShareLinkContent.class)) {
							ShareLinkContent linkContent = new ShareLinkContent.Builder()
								.setContentTitle(getResources().getString(R.string.adiai))
								.setContentDescription(textshare)
								.setContentUrl(Uri.parse(link0))
								.setImageUrl(Uri.parse(Config.HTTP_SERVER_SHARE_ROOT + g_shareimguri))
								.build();

						shareDialog.show(linkContent);
					}

/*
					final String message = "測試鏈接 - " + textshare;
					int networkId = FacebookSocialNetwork.ID;
					final SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);

					if (socialNetwork.isConnected()) {
					//	{
						AlertDialog.Builder ad = alertDialogInit(getResources().getString(R.string.sharefb), textshare + "\n\n" + link);
						ad.setPositiveButton("分享鏈接", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Bundle postParams = new Bundle();
								postParams.putString(SocialNetwork.BUNDLE_NAME, textshare);
								postParams.putString(SocialNetwork.BUNDLE_LINK, link);
								postParams.putString(SocialNetwork.BUNDLE_PICTURE, Config.HTTP_SERVER_SHARE_ROOT+g_shareimguri);


								if(sharetoken!=null) {
									getCookie();

									JsonHttpUtil json = new JsonHttpUtil(context, new FragmentJsonCallback() {
										@Override
										public void onTaskDone(String rv) {
											sharetoken = Share_Callback(rv);
											sharetoken = null;
										}
									}, 2);

								//	json.execute(Config.HTTPS_SERVER_ROOT + "/invest/servlet/shareshare", g_cookie, "id=" + sharetoken.id + "&source=" + sharetoken.utm_source + "&medium=" + sharetoken.utm_medium + "&campaign=" + sharetoken.utm_campaign);

								//	share2Facebook(textshare + "\n\n" + link);
								//	socialNetwork.requestPostLink(postParams, message, postingComplete);
									MainActivity.showProgress(getResources().getString(R.string.fbsharing));
									socialNetwork.requestPostLink(postParams, textshare, postingComplete);
								}
							}
						});
						ad.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int i) {
								dialog.cancel();
							}
						});
						ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
							public void onCancel(DialogInterface dialog) {
								dialog.cancel();
							}
						});
						ad.create().show();
					}
					else {
						sn_action = SN_SHARE;
						((MainActivity) getActivity()).storeSocialNetworkAction(((MainActivity) getActivity()).context, SN_SHARE);
						socialNetwork.requestLogin();
					//	MainActivity.showProgress(getResources().getString(R.string.fbsdk2login));
					}
*/

				} else if(socialnetwork.equals("2")){
					AlertDialog.Builder ad = alertDialogInit(getResources().getString(R.string.sharewhatsapp), textshare + "\n\n" + link);
					ad.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int i) {
							dialog.cancel();
						}
					});

					ad.setPositiveButton("分享鏈接", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							if (sharetoken != null) {
								getCookie();
						/*
								JsonHttpUtil json = new JsonHttpUtil(context, new FragmentJsonCallback() {
									@Override
									public void onTaskDone(String rv) {
										sharetoken = Share_Callback(rv);
										sharetoken = null;
									}
								}, 2);

								json.execute(Config.HTTPS_SERVER_ROOT + "/invest/servlet/shareshare", g_cookie, "id="+sharetoken.id+"&source=" + sharetoken.utm_source + "&medium=" + sharetoken.utm_medium + "&campaign=" + sharetoken.utm_campaign);
						*/
								share2WhatsApp(textshare + "\n\n" + link);
							}

						}
					});
					ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
						public void onCancel(DialogInterface dialog) {
							dialog.cancel();
						}
					});
					ad.create().show();
				}
				else if(socialnetwork.equals("3")) {
					AlertDialog.Builder ad = alertDialogInit(getResources().getString(R.string.sharewechat), textshare + "\n\n" + link);
					ad.setPositiveButton("分享鏈接", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							if(sharetoken!=null) {
								getCookie();
						/*
								JsonHttpUtil json = new JsonHttpUtil(context, new FragmentJsonCallback() {
									@Override
									public void onTaskDone(String rv) {
										sharetoken = Share_Callback(rv);
										sharetoken = null;
									}
								},2);

								json.execute(Config.HTTPS_SERVER_ROOT + "/invest/servlet/shareshare", g_cookie, "id="+sharetoken.id+"&source=" + sharetoken.utm_source + "&medium=" + sharetoken.utm_medium + "&campaign=" + sharetoken.utm_campaign);
						*/
								share2Wechat(textshare + "\n\n" + link);
							}

						}
					});
					ad.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int i) {
							dialog.cancel();
						}
					});
					ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
						public void onCancel(DialogInterface dialog) {
							dialog.cancel();
						}
					});
					ad.create().show();
				}
			}
		}
		else if(requestCode==FILECHOOSER_RESULTCODE || requestCode==INPUT_FILE_REQUEST_CODE)
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
					super.onActivityResult(requestCode, resultCode, data);
					return;
				}
				Uri[] results = null;
				String t = null;
				// Check that the response is a good one
				if (resultCode == Activity.RESULT_OK) {
					if (data == null) {
						// If there is not data, then we may have taken a photo
						if (mCameraPhotoPath != null) {
							results = new Uri[]{Uri.parse(mCameraPhotoPath)};
							t = mCameraPhotoPath;
						}
					} else {
						String dataString = data.getDataString();
						if (dataString != null) {
							results = new Uri[]{Uri.parse(dataString)};
							t = dataString;
						}
					}
				}





				if (t == null) { // cancel by user
					photoFile.delete();
					photoFile = null;
					videoFile.delete();
					videoFile = null;
				}
				else if(t.indexOf("MP4_")>=0) {
					photoFile.delete();
					photoFile = null;
				}
				else if(t.indexOf("JPEG_")>=0) {
					videoFile.delete();
					videoFile = null;
				}
				else { // select photo/video
					photoFile.delete();
					photoFile = null;
					videoFile.delete();
					videoFile = null;
				}

				mFilePathCallback.onReceiveValue(results);
				mFilePathCallback = null;
			} else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
				if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
					super.onActivityResult(requestCode, resultCode, data);
					return;
				}
				if (requestCode == FILECHOOSER_RESULTCODE) {
					if (null == this.mUploadMessage) {
						return;
					}
					Uri result = null;
					try {
						if (resultCode != Activity.RESULT_OK) {
							result = null;
						} else {
							// retrieve from the private variable if the intent is null
							result = data == null ? mCapturedImageURI : data.getData();
						}
					} catch (Exception e) {
						Toast.makeText(mainactivity, "activity :" + e,
								Toast.LENGTH_LONG).show();
					}
					mUploadMessage.onReceiveValue(result);
					mUploadMessage = null;
				}
			}


		}
		else {
			callbackManager.onActivityResult(requestCode, resultCode, data);

//			uiHelper.onActivityResult(requestCode, resultCode, data, null);
/*
			if(requestCode==11111 && resultCode == 1) {
				getCookie();
				JsonHttpUtil json = new JsonHttpUtil(context, new FragmentJsonCallback() {
					@Override
					public void onTaskDone(String rv) {
						LikeAction a = Share_Like(rv);
						((MainActivity)getActivity()).storeLike();
					}
				}, 3);

				json.execute(Config.HTTPS_SERVER_ROOT + "/invest/servlet/sharelike", g_cookie, "action=like&device_id=");
			}
*/
		}

	}


	private void downloadFileInBackground(final String s_url, final String s_dir, final String s_cookie) {
		new AsyncTask<Void, Void, String>() {                  
			@Override
		    protected void onPreExecute() {
		        super.onPreExecute();
//		        showDialog(progress_bar_type);
		        if(pDialog==null) {
			        pDialog = new ProgressDialog(getActivity());
			        pDialog.setMessage("請等候...");
			        pDialog.setIndeterminate(false);
			        pDialog.setMax(100);
			        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			        pDialog.setCancelable(true);
		        }
		        else {
		        	pDialog.setMessage("請等候...");
		        	pDialog.setMax(100);
			        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);		        	
		        }
		     //   pDialog.show();

				showProgressSpinner();

            	Log.i(TAG, "Page "+position+" Show888 progress bar dialog");		        
		    }			
		    @Override
		    protected String doInBackground(Void... sUrl) {     
		        try {
		           // String url = sUrl[0];
		            String dir = s_dir;
		            String cookie = s_cookie;
		            String fileName = "";
		            
		            File myDir = new File(dir);
	                if (!myDir.exists()) {
	                	Log.i(TAG, "Page "+position+" Folder "+dir+ "not found, so creating");
	                	myDir.mkdir();     
	                }


					fileName = s_url;
					int t = fileName.indexOf("?");
					if(t>0) fileName = fileName.substring(0, t);
					t = fileName.lastIndexOf(".");
					if(t>0) {
						fileName = util.urlHash(fileName.substring(0, t))+fileName.substring(t);
					}
					else fileName = util.urlHash(fileName);

	                String saveFilePath = dir+File.separator+fileName;
	                Log.i(TAG, "Page "+position+" SaveDocumentPath="+saveFilePath);
	                File ff = new File(saveFilePath);
	               // ff.delete();
	                if(!ff.exists()) {
//		           if(true) {
		       //     URL url = new URL(s_url+"?fb=1");
		       //     HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
					   HttpsURLConnection httpConn = util.setUpHttpsConnection(mainactivity, s_url+"?fb=1");
		            if(cookie!=null) httpConn.setRequestProperty("Cookie",cookie);
		            httpConn.setDoOutput(true);         
		            httpConn.connect();           
		            int responseCode = httpConn.getResponseCode();
		     
		            // always check HTTP response code first
		            if (responseCode == HttpURLConnection.HTTP_OK) {
		                String disposition = httpConn.getHeaderField("Content-Disposition");
		                String contentType = httpConn.getContentType();
					//	contentType="application/json";
		                int contentLength = httpConn.getContentLength();
		     /*
		                if (disposition != null) {
		                    // extracts file name from header field
		                    int index = disposition.indexOf("filename=");
		                    if (index > 0) {
		                        fileName = disposition.substring(index + 10,
		                                disposition.length() - 1);
		                    }
		                } else {           
		                    // extracts file name from URL
		                	if(contentType.equals("text/html")) fileName = "err.html";
		                	else fileName = s_url.substring(s_url.lastIndexOf("/") + 1,
		                			s_url.length());
		                }
		     */

							if(contentType.indexOf("application/json")>=0) {
								hideProgressSpinner();

								BufferedReader in = new BufferedReader(new InputStreamReader(
										httpConn.getInputStream(), "UTF-8"));
								String inputLine;
								StringBuilder a = new StringBuilder();
								while ((inputLine = in.readLine()) != null)
									a.append(inputLine);
								in.close();

								Gson gson = new Gson();
								final ViewItemError vie = gson.fromJson(a.toString(), ViewItemError.class);
							//	final ViewItemError vie = gson.fromJson("{\"errnum\":4,\"iid\":1}", ViewItemError.class);

								mUiHandler1.post(new Runnable() {
									@Override
									public void run() {
										if(vie.errnum==2 || vie.errnum==3)
											CustomDialog.LoginCancelDialog(getActivity());
										/*
										else if(vie.errnum==4){
											getCookie();
											CustomDialog.RedeemCancelDialog(getActivity(), vie.iid, g_cookie);
										}
										else if(vie.errnum==5){
											getCookie();
//											CustomDialog.ShareCancelDialog(getActivity(), vie.iid, vie.thumbnail, g_cookie);
											ShareCancelDialog(getActivity(), vie.iid, vie.thumbnail, g_cookie);
										}
										*/
										else if(vie.errnum==9){ // show only the pay membership
											getCookie();
											CustomDialog.BuyMembershipDialog(getActivity(),vie.iid ,vie.m1price, vie.m3price, vie.m6price, vie.m12price);
										}
										else if(vie.errnum==10){ // show both the pay membership and the research report
											getCookie();
											CustomDialog.BuyMembershipReportDialog(getActivity(), vie.rdesc, vie.iid, vie.rprice, vie.m1price, vie.m3price, vie.m6price, vie.m12price);
											//ShareCancelDialog(getActivity(), vie.iid, vie.thumbnail, g_cookie);
										}

									}
								});
								return "donedone";
							}
							else if(contentType.indexOf("text/html")>=0) {
								hideProgressSpinner();
		                	BufferedReader in = new BufferedReader(new InputStreamReader(
		                            httpConn.getInputStream(), "UTF-8"));
		                    String inputLine;
		                    StringBuilder a = new StringBuilder();
		                    while ((inputLine = in.readLine()) != null)
		                        a.append(inputLine);
		                    in.close();


		                    final String err = a.toString().replaceAll("<br>", "\n").replaceAll("<h3>", "").replaceAll("</h3>", "").replaceAll("<b>", "").replaceAll("</b>", "");
		                    
		                    mUiHandler1.post(new Runnable() {
			                    @Override
			                    public void run() {
				                    AlertDialog alert = new AlertDialog.Builder(mainactivity).create();
									alert.setTitle(Html.fromHtml("<font color='#FFFFFF'>"+getResources().getString(R.string.error)+"</font>"));
				    		//		alert.setTitle("錯誤 ");
				    				alert.setMessage(err);
				    				alert.setButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
				    				public void onClick(DialogInterface dialog, int which) {
				    				}
				    				});
				    				alert.show();
			                    }
			                });
		                    
		                    return "donedone";
		                    
		                }
		                else {		                
			                InputStream inputStream = httpConn.getInputStream();
			                
			                File file = new File(saveFilePath);
			                if (!file.exists()) {
			    				file.createNewFile();
			    			}
			                 
			                // opens an output stream to save into file
			                FileOutputStream outputStream = new FileOutputStream(saveFilePath);
			     
			                int bytesRead = -1;
			                int total = 0;
			                byte[] buffer = new byte[BUFFER_SIZE];
			                while ((bytesRead = inputStream.read(buffer)) != -1) {
			                    outputStream.write(buffer, 0, bytesRead);
			                    total += bytesRead;
			                 //   pDialog.setProgress(Integer.parseInt(""+(int)((total*100)/contentLength)));
			                 //   //publishProgress(""+(int)((total*100)/contentLength));
			                }
			     
			                outputStream.close();
			                inputStream.close(); 
		                }
		                
		                //System.out.println("File downloaded");
		            } else {
		            	saveFilePath = "Error response code is "+responseCode;
		                //System.out.println("No file to download. Server replied HTTP code: " + responseCode);
		            }
		            httpConn.disconnect();
		            
		     //       pDialog.dismiss();
					   hideProgressSpinner();

	                } // end download file
	                else {
	                	Log.i(TAG, "Page "+position+" Docx is found, just read from - "+saveFilePath);
	                }
		     		
		            final String localfile = saveFilePath;
		            
		            if(localfile.indexOf("Error ")==0) {
		            	// print error
		            	Log.i(TAG, "Page "+position+" Error local file");
		            	
		            }
		            /*
		            synchronized(lock) {
		            	lock.notify();
		            	
		            }
		            */
		            
		            
		            int i = localfile.lastIndexOf(".");
		            String ext = localfile.substring(i+1).toLowerCase();

					if(ext.equals("pdf")) {
						openPDFViewer(localfile);
						return "donedone";
					}

					File f = new File(localfile);
		            f.setReadable(true, false);
					Uri location = Uri.fromFile(f);
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(location, mimes.get(ext));

					Intent chooser = Intent.createChooser(intent,  "請選擇開啟程式");

		    		// Verify the intent will resolve to at least one activity
		    		if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
		    		    startActivity(chooser);
		    		}
		    		else {
		    			 mUiHandler1.post(new Runnable() {
			                    @Override
			                    public void run() {
			                    	AlertDialog alertDialog = new AlertDialog.Builder(mainactivity).create();
									alertDialog.setTitle(Html.fromHtml("<font color='#FFFFFF'>錯誤</font>"));
			        			//	alertDialog.setTitle("錯誤 ");
			        				alertDialog.setMessage("請先安裝 Microsoft office mobile in Google play store");
			        				alertDialog.setButton("確定", new DialogInterface.OnClickListener() {
			        				public void onClick(DialogInterface dialog, int which) {
			        				}
			        				});
				    				alertDialog.show();
			                    }
			                });
		    			
		    		}    
		      
		        } catch (MalformedURLException e) {
		             e.printStackTrace();
		             Log.i(TAG, "Error page "+position+" "+e.toString());
		        } catch (IOException e) {
		             e.printStackTrace();
		             Log.i(TAG, e.toString());
		        }
		        Log.i(TAG, "Page "+position+" downloadFileInBackground done");
		        return "donedone";
		    }			

		    
		    protected void onProgressUpdate(String... progress) {
		        // setting progress percentage
		        pDialog.setProgress(Integer.parseInt(progress[0]));
		   }
		 
			@Override
		    protected void onPostExecute(String msg) {
				// dismiss the dialog after the file was downloaded
				Log.i(TAG, "Page " + position + " Close 11 progress bar box");
////		        dismissDialog(progress_bar_type);
				//	pDialog.dismiss();
				hideProgressSpinner();
            	Log.i(TAG, "Page "+position+" Close progress bar box");

		 
		       // String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
		    }		    
		    		}.execute(null, null, null);
	}

	public void showProgressSpinner() {
		mUiHandler1.post(new Runnable() {
			@Override
			public void run() {

				View v = getView();
				if (v != null) {
					v.findViewById(R.id.progress1).setVisibility(View.VISIBLE);
				}
			}
		});
	}


	public void hideProgressSpinner() {
		mUiHandler1.post(new Runnable() {
			@Override
			public void run() {

				View v = getView();
				if (v != null) {
					v.findViewById(R.id.progress1).setVisibility(View.GONE);
				}
			}});
	}
/*
	public void showAlertDialog(String title, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mainactivity);
		builder.setTitle(Html.fromHtml(title));
		builder.setItems(new CharSequence[]
						{"確定", "登錄", "注冊", "了解績分計劃"},
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// The 'which' argument contains the index position
						// of the selected item
						switch (which) {
							case 0:
								Toast.makeText(context, "clicked 1", 0).show();
								break;
							case 1:
								Toast.makeText(context, "clicked 2", 0).show();
								break;
							case 2:
								Toast.makeText(context, "clicked 3", 0).show();
								break;
							case 3:
								Toast.makeText(context, "clicked 4", 0).show();
								break;
						}
					}
				});
		builder.create().show();

	}
*/
    public GenToken GetShareToken_Callback(String rv) {
		Gson gson = new Gson();
		GenToken t = gson.fromJson(rv, GenToken.class);
		return t;
	}

	public GenToken Share_Callback(String rv) {
		Gson gson = new Gson();
		GenToken t = gson.fromJson(rv, GenToken.class);
		return t;
	}

	public LikeAction Share_LikeBefore(String rv) {
		Gson gson = new Gson();
		LikeAction t = gson.fromJson(rv, LikeAction.class);
		return t;
	}

	public LikeAction Share_Like(String rv) {
		Gson gson = new Gson();
		LikeAction t = gson.fromJson(rv, LikeAction.class);
		return t;
	}

	public void ShareCancelDialog(final Context context, final int iid, final String thumbnail, final String cookie) {

		final Dialog dialog = new Dialog(context, R.style.cust_dialog);
		dialog.setContentView(R.layout.sharecanceldialog);
		dialog.setTitle(context.getResources().getString(R.string.paidservice));

		TextView tv = (TextView) dialog.findViewById(R.id.desc);
		tv.setText(context.getResources().getString(R.string.sharemessage));

		Button button1 = (Button) dialog.findViewById(R.id.button1);
		button1.setText(context.getResources().getString(R.string.understandshare));
		// if button is clicked, close the custom dialog
		button1.setOnClickListener(new Button.OnClickListener() {
									   @Override
									   public void onClick(View v) {
										   ((MainActivity) context).showPage("/scheme/scheme.html");
										   dialog.dismiss();
									   }
								   }
		);

		Button button2 = (Button) dialog.findViewById(R.id.button2);
		button2.setText(context.getResources().getString(R.string.cancel));
		// if button is clicked, close the custom dialog
		button2.setOnClickListener(new Button.OnClickListener() {
									   @Override
									   public void onClick(View v) {
										   dialog.dismiss();
									   }
								   }
		);


		Button facebook = (Button) dialog.findViewById(R.id.share_fb);
		facebook.setText(context.getResources().getString(R.string.buttonsharefb));
		// if button is clicked, close the custom dialog
		facebook.setOnClickListener(new Button.OnClickListener() {
										@Override
										public void onClick(View v) {
											tosharedialog(iid, "1", thumbnail, cookie);
											dialog.dismiss();
										}
									}
		);

		Button whatapps = (Button) dialog.findViewById(R.id.share_whatapps);
		whatapps.setText(context.getResources().getString(R.string.buttonsharewhatapps));
		whatapps.setOnClickListener(new Button.OnClickListener() {
										@Override
										public void onClick(View v) {
											tosharedialog(iid, "2", thumbnail, cookie);
											dialog.dismiss();
										}
									}
		);


		Button wechat = (Button) dialog.findViewById(R.id.share_wechat);
		wechat.setText(context.getResources().getString(R.string.buttonsharewechat));
		wechat.setOnClickListener(new Button.OnClickListener() {
									  @Override
									  public void onClick(View v) {
										  tosharedialog(iid, "3", thumbnail, cookie);
										  dialog.dismiss();
									  }
								  }
		);

		dialog.show();
	}



	private void tosharedialog(final int iid, final String socialnetwork, final String imguri, final String cookie) {

		JsonHttpUtil json = new JsonHttpUtil(context, new FragmentJsonCallback() {
			@Override
			public void onTaskDone(String rv) {
				sharetoken = GetShareToken_Callback(rv);

				if(!sharetoken.error.equals("")) {
					((MainActivity)getActivity()).showToast(getResources().getString(R.string.shareerror));
				}
				else {


					//       Handler mUiHandler = new Handler();
					//       mUiHandler.post(new Runnable() {
					//          @Override
					//          public void run() {
					//    ((MainActivity) context).startShareEditActivity(context, t.share_message, socialnetwork, imguri);
				}
				//      });
				Intent intent = new Intent(getActivity(), EditTextActivity.class);
//					intent.putExtra("defaulttext", getResources().getString(R.string.defaulttext));
				intent.putExtra("defaulttext", sharetoken.share_message);
				intent.putExtra("socialnetwork", String.valueOf(socialnetwork));
				intent.putExtra("imguri", imguri);
				startActivityForResult(intent, WebViewFragment.REQ_SOCIAL_NETWORK_SHARE);

			}
		},1);

		json.execute(Config.HTTPS_SERVER_ROOT + "/invest/servlet/sharegentoken", cookie, "iid=" + iid + "&media=" + Config.socialnetworkmediaids[Integer.valueOf(socialnetwork).intValue()]);
	}

	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);
		File imageFile = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);
		return imageFile;
	}
	private File createVideoFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "MP4_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES);
		File imageFile = File.createTempFile(
				imageFileName,  /* prefix */
				".mp4",         /* suffix */
				storageDir      /* directory */
		);
		return imageFile;
	}


	public void goToAnchor(String anchor) {
		webView.loadUrl("javascript:goto(\""+anchor+"\")");
	}

	public void notifyToken(String token) {
		if(bPageFinished)
			webView.loadUrl("javascript:external_call(\"notify_token\", \""+token+"\")");
	}
}
