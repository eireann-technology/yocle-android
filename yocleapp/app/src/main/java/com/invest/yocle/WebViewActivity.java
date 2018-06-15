package com.invest.yocle;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
*/
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;


import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.net.http.SslError;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import static com.invest.yocle.MainActivity.USER_COOKIE;


public class WebViewActivity extends ActionBarActivity {
	WebView webView = null;

	private static final int REQ_START_STANDALONE_PLAYER = 1;
	private static final int REQ_RESOLVE_SERVICE_MISSING = 2;
	public String jsfunc = null;
	WebViewActivity webviewactivity;
	AssetManager assetManager = null;
	HashMap<String, String> mimes;
	private Handler mUiHandler1 = new Handler();
	
	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewactivity);

		ProgressBar spinner = (ProgressBar)findViewById(R.id.progress1);
		spinner.getIndeterminateDrawable().setColorFilter(Color.parseColor("#80DAEB"), android.graphics.PorterDuff.Mode.MULTIPLY);

		assetManager = getAssets();
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


		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		String myURL = "https://yocle.net";
		android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		cookieManager.acceptCookie();
		cookieManager.setAcceptFileSchemeCookies(true);
		cookieManager.getInstance().setAcceptCookie(true);
		cookieManager.getCookie(myURL);
		restoreLogonSession(cookieManager);
	//	String c = "email=alantypoon@gmail.com; pwd=1234; reset_pwd=; remember=0; login=1; io=RcC3WM3NZHbXdxjFAAqM";
	//	cookieManager.setCookie(myURL, c);



		webviewactivity = this;
		webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

		webView.setWebChromeClient(new WebChromeClient() {
			public boolean onConsoleMessage(ConsoleMessage cm) {

				return true;
			}
				/*
					@Override
					public boolean onJsAlert(WebView view, String url, String message,           JsResult result) {
				                //Required functionality here
				                return super.onJsAlert(view, url, message, result);
				       }
*/
		});
        
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
				return checkAsset(url);
			}

			@SuppressLint("NewApi")
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
				if (request == null || request.getUrl() == null) return null;
				String url = request.getUrl().toString();
				return checkAsset(url);
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
				//	Log.i("yocleppp", "Asset found " + u);
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
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				webView.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				findViewById(R.id.progress1).setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				findViewById(R.id.progress1).setVisibility(View.GONE);

				webView.loadUrl("javascript:"+jsfunc);
			}
/*
			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				super.onReceivedSslError(view, handler, error);
				// this will ignore the Ssl error and will go forward to your site
				handler.proceed();
			}
*/
		});
		
		 webView.addJavascriptInterface(new WebViewJavaScriptInterface(this), "app");

		//      webView.getSettings().setRenderPriority(RenderPriority.HIGH);

		webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);


		if (Build.VERSION.SDK_INT >= 19) {
			webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		}
		else if(Build.VERSION.SDK_INT >=11 && Build.VERSION.SDK_INT < 19) {
			webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}

		String url = getIntent().getStringExtra("url");
		this.jsfunc = getIntent().getStringExtra("jsfunc");
		String title = getIntent().getStringExtra("title");

		if(url!=null) {
			webView.loadUrl(Config.HTTPS_SERVER_ROOT+"/"+url+"?platform=android");
			if(title!=null) {
				TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
				mToolBarTextView.setText(title);
			}
		}

		String noturl = getIntent().getStringExtra("message");
		if(noturl!=null) {
			webView.loadUrl(noturl);
			if(title!=null) {
				TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
				mToolBarTextView.setText(title);
			}
		}

    }

	@Override
	protected void onStart() {
	    super.onStart();
	}

	@Override
	protected void onResume() {
	    super.onResume();
	    if (webView != null) {
	    	webView.onResume();
	    }
	}
	
	
	@Override
	protected void onStop() {
	    super.onStop();
//	    finish();
	}
	
	
	@Override
	protected void onPause() {
	    super.onPause();
	    if (webView != null) {
//	    	g_url = webView.getUrl();
	    	webView.onPause();
	    }
	}
	
	@Override
	protected void onDestroy() {
		store_msg_user_onshow("");
	    super.onDestroy();
	}	

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
      }
    	
	
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);    
    	webView.saveState(outState);    	
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state); 
        webView.restoreState(state);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
				if(webView.getUrl().indexOf("paydollar.com/")>0) finish();
                if(webView.canGoBack()){
                	webView.goBack();
                	//webView.reload();
                }else{
					Intent intent=new Intent();
					intent.putExtra("jsfunc", "");
					setResult(Config.COMMAND_BACKWIN, intent);
					finish();
				}
			//	finish();
				Intent intent=new Intent();
				intent.putExtra("jsfunc", "");
				setResult(Config.COMMAND_BACKWIN, intent);
				finish();

                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
	
    private boolean canResolveIntent(Intent intent) {
	    List<ResolveInfo> resolveInfo = getPackageManager().queryIntentActivities(intent, 0);
	    return resolveInfo != null && !resolveInfo.isEmpty();
	}	
    
    public void playVideo(String video_id) {
		Intent intent = null;
	    intent = YouTubeStandalonePlayer.createVideoIntent(
	        this, DeveloperKey.DEVELOPER_KEY, video_id, 0, true, false);

	    if (intent != null) {
	        if (canResolveIntent(intent)) {
	          startActivityForResult(intent, REQ_START_STANDALONE_PLAYER);
	        } else {
	          // Could not resolve the intent - must need to install or update the YouTube API service.
	          YouTubeInitializationResult.SERVICE_MISSING
	              .getErrorDialog(this, REQ_RESOLVE_SERVICE_MISSING).show();
	        }	    
	    }    	
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == android.R.id.home) {
			Intent intent=new Intent();
			intent.putExtra("jsfunc", "");
			setResult(Config.COMMAND_BACKWIN, intent);
			finish();
		}

		return super.onOptionsItemSelected(item);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == Config.COMMAND_BACKWIN) {
			Bundle extras = data.getExtras();
			String jsfunc = extras.getString("jsfunc");
			if (jsfunc != null && !jsfunc.equals("")) {
				webView.loadUrl("javascript:" + jsfunc);
			}

		}
	}

		public class WebViewJavaScriptInterface{

        private Context context;

        /*
         * Need a reference to the context in order to sent a post message
         */
        public WebViewJavaScriptInterface(Context context){
            this.context = context;
        }


        @JavascriptInterface
        public void close(){
        	finish();
        }

        
        @JavascriptInterface
        public void playyoutube(String video_id) {
        	playVideo(video_id);
        }

        @JavascriptInterface
        public void setUid(String uid){
        //	jsUid = uid;        	
        }


			@JavascriptInterface
			public void newwin(final String url, final String jsfunc) {
				Intent intent = new Intent(webviewactivity, WebViewActivity.class);

				intent.putExtra("url", url);
				intent.putExtra("jsfunc", jsfunc);
				intent.putExtra("title", getResources().getString(R.string.home_title));
				startActivityForResult(intent, Config.COMMAND_BACKWIN);
			}


			@JavascriptInterface
		public void backwin(final String jsfunc) {
			Intent intent=new Intent();
			intent.putExtra("jsfunc", jsfunc);
			setResult(Config.COMMAND_BACKWIN, intent);
			finish();
		}
			@JavascriptInterface
			public void changeprofile(final String jsonStr) { // i=1, no login, i=2, show logoned profile

			}

			@JavascriptInterface
			public void cmenu(final String jsonStr) {

			}

			@JavascriptInterface
			public void showhideactionbar(final int i) {
				mUiHandler1.post(new Runnable() {
					@Override
					public void run() {
						if(i==1) showActionBar();
						else hideActionBar();
					}
				});
			}

			@JavascriptInterface
			public void setmsguseronshow(final String uid) {
				store_msg_user_onshow(uid);
			/*
				mUiHandler1.post(new Runnable() {
					@Override
					public void run() {
						store_msg_user_onshow(uid);
					}
				});
			*/
			}

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

}
