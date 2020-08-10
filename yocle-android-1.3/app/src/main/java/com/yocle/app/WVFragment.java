package com.yocle.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WVFragment extends Fragment {

    private String url;
    private WebView webView;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = Config.HTTPS_SERVER_ROOT + "/" + getArguments().getString("url");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("SwA", "WVF onCreateView");
        View v = inflater.inflate(R.layout.fragment_swipe, container, false);
        if (url != null) {
            //String myURL = "https://yocle.net";
            android.webkit.CookieManager cookieManager = android.webkit.CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.acceptCookie();
            cookieManager.setAcceptFileSchemeCookies(true);
            cookieManager.getInstance().setAcceptCookie(true);
            //cookieManager.getCookie(myURL);
            cookieManager.getCookie(Config.HTTPS_SERVER_ROOT);

            webView = (WebView) v.findViewById(R.id.webView);

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
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    webView.loadUrl(url);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }
/*
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
  //                  super.onReceivedSslError(view, handler, error);
                    // this will ignore the Ssl error and will go forward to your site
                    handler.proceed();
                }
*/
            });

            webView.getSettings().setJavaScriptEnabled(true);

            webView.getSettings().setAppCacheEnabled(false);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

            webView.addJavascriptInterface(new WebViewJavaScriptInterface(getActivity()), "app");

            //      webView.getSettings().setRenderPriority(RenderPriority.HIGH);

            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);


            webView.loadUrl(url);

        }
        return v;
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
            //finish();
        }

        @JavascriptInterface
        public void setUid(String uid){
            //	jsUid = uid;
        }

    }

    public void searchhkex(String stocknum) {
        int i = url.indexOf("?");
        if(i>0) url = url.substring(0, i)+"?no="+stocknum;
        else url += "?no="+stocknum;
        webView.loadUrl(url);
    }



}