package com.invest.yocle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.webkit.CookieManager;

import javax.net.ssl.HttpsURLConnection;

public class ACaching {	
	protected static ConcurrentHashMap<String, String> g_memorycache = new ConcurrentHashMap<String, String>();
	protected static ConcurrentHashMap<String, Date> g_memorycache_time = new ConcurrentHashMap<String, Date>();
	protected static HashMap<String, String> g_assetcache = new HashMap<String, String>();
	protected static HashMap<String, String> g_html_source = new HashMap<String, String>();
	protected static String g_cookie;
	protected static String TAG = "ADIAPONG";
    protected static Context g_context = null;
    protected static boolean g_bNetwork = true;
    protected static ConnectivityManager connectivityManager = null;
    protected Integer lock = new Integer(1);
    protected static File g_cache_folder = null;
    protected static AssetManager g_assetManager = null;
    protected WebViewFragment fragment = null;
    
    public ACaching(WebViewFragment fragment) {
    	this.fragment = fragment;
    }
    
    public static synchronized void setContext(Context context) {
    	g_context = context;
    }

    public static synchronized void setCacheFolder(File cachefolder) {
    	g_cache_folder = cachefolder;
    }
    
    public static synchronized void setAssetManager(AssetManager assetmanager) {
    	g_assetManager = assetmanager;
    }
    
    public static boolean hasAssetCache(String url) {
    	return g_assetcache.containsKey(url);
    }

    public static boolean hasMemoryCache(String url) {
    	return g_memorycache.containsKey(url);
    }

    public static boolean isCacheValid(String url) {
    	String u = url;
   //     u = u.replace(Config.HTTP_SERVER_ROOT + "/", "");
		u = util.urlHash(u);

        if(g_memorycache.containsKey(u)) {
			if(!isCacheExpired(u)) return true;
        }
        
        return false;    
    }
    
	public String loadfromOrUpdateCache(final String page, final String url, boolean bForce2Fetch) {
		String u = url;

		u = util.urlHash(u);

//        u = u.replace(Config.HTTP_SERVER_ROOT+"/", "");
//        if(u.equals("")) return null;
		
		if(g_memorycache.containsKey(u)) {
			if(isCacheExpired(u) || bForce2Fetch) {
				  synchronized(lock) {
					try { 
						String content =null;
						/*				
										if(Looper.getMainLooper().getThread() != Thread.currentThread()) {				
											sendWebRequest(url, page);
							        		g_html_source.put(page, content);
											lock.wait();
										}
										else 
						*/				
										{
											new Thread(new Runnable() {
												   public void run() {
													   try {
														  synchronized(lock) {
														String cont = loadFromWeb(url);
														g_html_source.put(page, cont);
														lock.notify();
														  }
													   }
													   catch(Exception ee) {
														   Log.i(TAG, "ERROR ERROR");
													   }
												   }                        
												}).start();
											lock.wait();
										}
										
										
										
										
										g_memorycache.put(u,  g_html_source.get(page));
										g_memorycache_time.put(u,  new Date());
							    		Log.i(TAG, "ACache caller page = "+page+" getAsciifile from web "+url);
										return g_html_source.get(page);

					}
					catch(Exception e) {
						e.printStackTrace();
					}
				  }							
			}
			else {
	    		Log.i(TAG, "ACache caller page = "+page+" loadfromorupdatecache from cache "+url);
				return g_memorycache.get(u);
			}
		}
								
		return null;
	}

	public String getCacheText(String u) {
		return g_memorycache.get(u);
	}

	public static void removeCache(String u) {
		g_memorycache.remove(u);
		g_memorycache_time.remove(u);
	}


	public String getAsciiFile(final String page, final String url, boolean bForce2Fetch) {
		String u = url;
		u = util.urlHash(u);
//        u = u.replace(Config.HTTP_SERVER_ROOT+"/", "");
//        if(u.equals("")) return null;

		if(g_memorycache.containsKey(u) && !isCacheExpired(u) && !bForce2Fetch) { // load from cache
    		Log.i(TAG, "ACache caller page = "+page+" getAsciifile from cache "+url);

			return g_memorycache.get((u));
		}
		else { // get from web
		  synchronized(lock) {
			try { 
				String content =null;
/*				
				if(Looper.getMainLooper().getThread() != Thread.currentThread()) {				
					sendWebRequest(url, page);
	        		g_html_source.put(page, content);
					lock.wait();
				}
				else 
*/				
				{
					new Thread(new Runnable() {
						   public void run() {
							   try {
								  synchronized(lock) {
								String cont = loadFromWeb(url);
								g_html_source.put(page, cont);
								lock.notify();
								  }
							   }
							   catch(Exception ee) {
								   Log.i(TAG, "ERROR ERROR");
							   }
						   }                        
						}).start();
					lock.wait();
				}
				
				g_memorycache.put(u,  g_html_source.get(page));
				g_memorycache_time.put(u,  new Date());
	    		Log.i(TAG, "ACache caller page = "+page+" getAsciifile from web "+url);
				return g_html_source.get(page);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		  }			
		}
		return null;
	}
	
	public static void updateCacheIfNotFound(String page, String u, String content) {
		if(!g_memorycache.containsKey(u)) {
			g_memorycache.put(u,  content);
			g_memorycache_time.put(u,  new Date());		
    		Log.i(TAG, "ACache caller page = "+page+" updatecacheifnotfound "+u);			
		}
	}
	
	public static void removeSpecialCaches(String page) {
/*
		ArrayList<String> list = new ArrayList<String>();
		list.add("notification.html");
		for(String key: g_memorycache.keySet()){
            if(key.indexOf("member/")==0) list.add(key);
            else if(key.indexOf("adiai.js")>=0) list.add(key);
        }
*/
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		Date date1 = cal.getTime();
//    	for (String str : list) {
		for(String str: g_memorycache.keySet()){
				Log.i(TAG, "ACache caller page = " + page + " To reset expiry time at login/logout: " + str);
				//g_memorycache.remove(str);
				//g_memorycache_time.remove(str);
			    if(str.charAt(0)=='p'||str.charAt(0)=='n')
					g_memorycache_time.put(str, date1);
		}
	}
	
	public static void removeAllCache() {
		g_memorycache.clear();
		g_memorycache_time.clear();
	}
	
	
	private void sendWebRequest(final String url, final String page) {
		new AsyncTask<String, Void, String>() {
			@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	        }
			@Override
			protected String doInBackground(String... params) {
				String url = params[0];
	        	String rv = "";
	        	try {
		        		String content = loadFromWeb(url);
		        		return content;
	        	}
	        	catch(Exception e) {
	        		Log.i(TAG, "EEE-"+e.toString());
	        		e.printStackTrace();
	        	}
	        	return "";        	
			}

	        @Override
	        protected void onPostExecute(String result) {
	        	try {
	        		synchronized(lock) {
	        			g_html_source.put(page, result);
	        			lock.notify();
	        		}
	        	}
	        	catch(Exception e) {
	        		e.printStackTrace();
	        	}
	        }  

		}.execute(url);
	}

	public String loadFromWeb(String url) throws IOException {
		CookieManager cookieManager = CookieManager.getInstance();
		
        String cookie = null;
        if(url.indexOf(".html")<0) cookie = g_cookie; // should be calling from webview.shouldInterceptRequest
        else cookie = cookieManager.getCookie(new URL(url).getHost());
        if(cookie!=null && cookie.equals("")) cookie = null;
        
        String ts = String.valueOf(new Date().getTime());
 //       URL u = new URL(url);

       Log.i(TAG, "ACaching.loadFromWeb Refetching url "+url+" with cookie="+cookie);
//        URLConnection uc = u.openConnection();

		HttpsURLConnection uc = util.setUpHttpsConnection(g_context, url);
        if(cookie!=null) uc.setRequestProperty("Cookie", cookie);
        uc.setConnectTimeout(30000);
        uc.setReadTimeout(30000);

		uc.connect();
		int responseCode = uc.getResponseCode();
        
        InputStream is = uc.getInputStream();
        String r_cookie = uc.getHeaderField("Set-Cookie");
        
        if(r_cookie!=null && !r_cookie.equals("")) {
        	g_cookie = r_cookie;        	
        	int i = 0;
        	i = g_cookie.indexOf(";");
        	if(i>0) g_cookie = g_cookie.substring(0, i+1);
        	
            cookieManager.setCookie(url, g_cookie);                	
            Log.i(TAG, "Cookie updated from web="+g_cookie);
        }
                		
        ByteArrayOutputStream outputDoc = new ByteArrayOutputStream();
        byte buf[]=new byte[1024];
        int len;
        while((len=is.read(buf))>0)
        {
         outputDoc.write(buf,0, len);
        }
        
        byte[] content = outputDoc.toByteArray();
        outputDoc.close();
        
        String c = new String(content, "UTF-8");
        return c;
    }

	public static boolean isCacheExpired(String key) {
		// if(key.indexOf("Index.html")>=0) return true;
		// else return false;
		 if(!isNetworkStatusAvialable(g_context)) return false; // use cache copies when no network
		 if(!g_bNetwork) return false;
		 
		 long diffInMillies = new Date().getTime() - g_memorycache_time.get(key).getTime();

		 if(key.charAt(0)=='m' || key.charAt(0)=='n' || key.charAt(0)=='p') {
			 if(diffInMillies/1000 > 60) return true; // 1 min expiry
		//	 if (diffInMillies / 1000 > 86400 * 3) return true; // 3 day expiry
			 else return false;
		 }
		 else if(key.charAt(0)=='h' || key.charAt(0)=='w') {
			 if(diffInMillies/1000 > 60) return true; // 1 min expiry
		//	 if (diffInMillies / 1000 > 86400 * 3) return true; // 3 day expiry
			 else return false;
		 }
		 else if(key.charAt(0)=='r') {
//			 if(diffInMillies/1000 > 60) {
			 if (diffInMillies / 1000 > 3600 * 3) { // 3 hours expiry
				 return true; // 1 min expiry
			 }
			 else {
				 return false;
			 }
		 }
         else {
			 if (diffInMillies / 1000 > 86400 * 3) return true; // 3 day expiry
			 else return false;
		 }
	 }
	
	public static boolean isNetworkStatusAvialable (Context context) {
		if(connectivityManager==null) connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
	    if (connectivityManager != null) 
	    {
	        NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
	        if(netInfos != null)
	        if(netInfos.isConnected() && g_bNetwork) 
	            return true;
	        else return false;
	    }
	    return false;
	}

    public static void saveMemoryCacheToCacheFolder(String page) {
    try {
    	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String folder = g_cache_folder.getPath();  
//    	String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
    	String filename = "";
		String d = "";
		BufferedWriter writer = null;
		File f = null;
		ByteArrayOutputStream outputDoc = null;
        byte buf[]=new byte[1024];
	    int len;
	    byte[] content = null;
	    
	    f = new File(folder);
	    if(!f.exists()) {
	    	f.mkdir();
	    }
	    f = null;
	        
    	for(String key: g_memorycache.keySet()){
    		//filename = key.replaceAll("/","_ap_");
			filename = key;
    		    f = new File(folder+File.separator+filename);
    		//	writer = new BufferedWriter( new OutputStreamWriter(
             //                  new FileOutputStream(f),"UTF8"));
    			
    			
   // 			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(folder+File.separator+filename), "UTF8"));
      		      		    
    			d = df.format(g_memorycache_time.get(key));

   // 			StringBuilder sb = new StringBuilder();
    			String header = "cachedate="+d+"\n";
    			Log.i(TAG, "ACache page= "+page+" onStop Save memory cache to "+folder+File.separator+filename);
    			
//    			outputDoc = new ByteArrayOutputStream();
//    		    outputDoc.write(header.getBytes("UTF-8"),0, header.getBytes("UTF-8").length);
//    		    outputDoc.write(memorycache.get(key).getBytes("UTF-8"),0, memorycache.get(key).getBytes("UTF-8").length);    		        
//    		    content = outputDoc.toByteArray();
//    		    outputDoc.close();    			
 //   			writer.write(new String(content, "UTF-8"));
 //   			writer.close();
 //   			writer = null;
 //   			outputDoc = null;
    			
    			FileOutputStream fos = null;
    				fos = new FileOutputStream(f);    				
    				fos.write(header.getBytes("UTF-8"));
    				fos.write(g_memorycache.get(key).getBytes("UTF-8"));
    			fos.close();
    			f = null;
    				
    			content = null;
   			
    	}
    	Log.i(TAG, "ACache page= "+page+" onStop Save memory cache completed");
       }
    catch(Exception e) {
    	Log.i(TAG, "ACache page="+page+" Errr in saving memcache - "+e.toString());
    	e.printStackTrace();
    }
    }
    
    public static void saveAdiaJs(String s) {
        try {
        	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    		String folder = g_cache_folder.getPath();  
 //       	String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        	String filename = "";
    		String d = "";
    		BufferedWriter writer = null;
    		File f = null;
    		
    		 f = new File(folder);
    		 if(!f.exists()) f.mkdir();
    		 f = null;
    		    
    		{
    			String key = Config.HTTP_SERVER_ROOT+"/scripts/adiai.js";
				filename = key;
    			//filename = key.replaceAll("/","_ap_");
        		    f = new File(folder+File.separator+filename);
        		    d = df.format(g_memorycache_time.get(key));
        		    String header = "cachedate="+d+"\n";        		    
        		    FileOutputStream fos = null;
    				fos = new FileOutputStream(f);    				
    				fos.write(header.getBytes("UTF-8"));
    				fos.write(g_memorycache.get(key).getBytes("UTF-8"));
    			fos.close();
    			f = null;        		    
/*        		    
        			writer = new BufferedWriter( new OutputStreamWriter(
                                   new FileOutputStream(f),"UTF8"));
        			
        			writer.write(s);
        			writer.close();
        			writer = null;
*/        			
        		//	Log.i(TAG, "Save key "+key+" memory cache to "+folder+File.separator+filename);
        	}    
           }
        catch(Exception e) {
        }
        }

    protected static void getSaveCookie(String page, int action) {
    	try {
    		File f = null;   
    		String folder = g_cache_folder.getPath();  
    		String filename = util.urlHash("_ap_cookie.txt");
    		if(action==1) {        		     		
        		 f = new File(folder);
        		 if(!f.exists()) f.mkdir();
        		 f = null;        		    
        		
        		f = new File(folder+File.separator+filename);
            	FileOutputStream fos = null;
        		fos = new FileOutputStream(f);    				
        		if(g_cookie==null) g_cookie = "";
        		fos.write(g_cookie.getBytes("UTF-8"));
        		fos.close();
        		f = null;        		    
    			Log.i(TAG, "ACache page="+page+" GetSaveCookie cookie saved = "+g_cookie);        		
    		}
    		else {
    			f = new File(folder+File.separator+filename);
    			InputStream is = new FileInputStream(f);
                ByteArrayOutputStream outputDoc = new ByteArrayOutputStream();
                byte buf[]=new byte[100];
                for(int i=0; i<100; i++) {
                	buf[i] = 0;
                }
                int len;
                while((len=is.read(buf))>0)
                {
                 outputDoc.write(buf,0, len);
                }
                
                byte[] cc = outputDoc.toByteArray();
    			g_cookie = new String(cc, "UTF8");
    			Log.i(TAG, "ACache page="+page+" GetSaveCookie cookie restored = "+g_cookie);
    			outputDoc.close();
    			is.close();
    		}
    	}
    	catch(Exception e) {
    		g_cookie = "";
    		Log.i(TAG, "ACache page="+page+" Error getSaveCookie = "+e.toString());
       }
    }
    
    
    
    public static void loadMemoryCacheFromCacheFolder(String page) {
    try {
    	SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    	BufferedReader in = null;
    	File path = g_cache_folder;
  /*
    	String foldername = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

    	File f = new File(foldername);
		 if(!f.exists()) {
			 f.mkdir();
		 }
//		 f = null;   	
    	File path = new File(foldername);
 */
        File [] files = path.listFiles();
        String str = "";
        String content = "";
        String key = "";
        int j = 0;
        Date date = null;
        for (int i = 0; i < files.length; i++){
        	content = "";
            if (files[i].isFile()){ //this line weeds out other directories/folders
            	key = files[i].getAbsolutePath();
            //	if(key.indexOf(".html")==-1 && key.indexOf(".js")==-1) continue;
            	key = key.substring(key.lastIndexOf("/")+1);
           		if(key.indexOf("com.android.opengl.shaders_cache")>=0) continue;

            //	key = key.replaceAll("_ap_","/");

            	
/*            	
            	in = new BufferedReader(
                   new InputStreamReader(
                              new FileInputStream(files[i]), "UTF8"));
                j=0;
                while ((str = in.readLine()) != null) {
                	if(++j==1 && str.indexOf("cachedate=")==0) {
                		date = df.parse(str.substring("cachedate=".length()));
                		memorycache_time.put(key, date);
                	}
                	else content += str;
                }

                in.close();
                in = null;
  */
            	
            	InputStream is = new FileInputStream(files[i]);
                ByteArrayOutputStream outputDoc = new ByteArrayOutputStream();
                byte buf[]=new byte[1024];
                int len;
                while((len=is.read(buf))>0)
                {
                 outputDoc.write(buf,0, len);
                }
                
                byte[] cc = outputDoc.toByteArray();
                outputDoc.close();
                is.close();
                
                content = new String(cc, "UTF-8");            	
            	
                if(!content.equals("")) {
                	str = content.substring(0, 29);
                	str = str.trim();
                	content = content.substring(30);
                	date = df.parse(str.substring("cachedate=".length()));
            		g_memorycache_time.put(key, date);
                	                	
                //	Log.i(TAG, "Restore key "+key+" to memory cache from "+files[i].getAbsolutePath());
                	g_memorycache.put(key,  content);
                	Log.i(TAG, "ACAche page="+page+" Add to memorycache in onCreate - "+key);
                }
                
            }
        }
       }
    catch(Exception e) {
    	Log.i(TAG, "ACache page="+page+" Read cache onCreate error - "+e.toString());
    	}
    }
    
    public static void readAssetCache(String folder) {
    	try {	
        	String[] filelist = g_assetManager.list(folder);
        	if (filelist == null) {
				// dir does not exist or is not a directory
        	} 
        	else {
        		for (int i=0; i<filelist.length; i++) {
        			// Get filename of file or directory
        			g_assetcache.put(folder+"/"+filelist[i], "1");
        		}
        	}
        }
        catch(Exception e) {}	
    }

	public static void readAssetRootCache() {
		try {
			String[] filelist = g_assetManager.list("");
			if (filelist == null) {
				// dir does not exist or is not a directory
			}
			else {
				for (int i=0; i<filelist.length; i++) {
					// Get filename of file or directory
					g_assetcache.put(filelist[i], "1");
				}
			}
		}
		catch(Exception e) {}
	}

}
