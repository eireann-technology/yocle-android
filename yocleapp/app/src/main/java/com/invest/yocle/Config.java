package com.invest.yocle;

public interface Config {

//	static final String HTTPS_SERVER_ROOT = "https://videoboard.hk:8081/dev";
//	static final String HTTPS_SERVER_ROOT = "https://dev.adiai.com:8441";

	//static final String HTTP_SERVER_SHARE_ROOT = "http://m.adiai.com";
	//static final String HTTP_SERVER_ROOT = "http://m.adiai.com";
	//static final String HTTPS_SERVER_ROOT = "https://yolofolio2.cetl.hku.hk:18443/dev";

	static final String HTTP_SERVER_SHARE_ROOT = "http://yocle.net";
	static final String HTTP_SERVER_ROOT = "http://yocle.net";
	static final String HTTPS_SERVER_ROOT = "https://yocle.net";

	//static final String HOME_URL = HTTPS_SERVER_ROOT + "/anchortest.php";
	static final String HOME_URL = HTTPS_SERVER_ROOT + "/index.php";


	// used to share GCM regId with application server - using php app server
	static final String APP_SERVER_URL = HTTPS_SERVER_ROOT +"/invest/servlet/notregister";
	static final String MAP_SERVER_URL = HTTPS_SERVER_ROOT +"/invest/servlet/mapuser";
	static final String LOGOUT_URL = HTTPS_SERVER_ROOT +"/invest/servlet/logout";

	// GCM server using java
	// static final String APP_SERVER_URL =
	// "http://192.168.1.17:8080/GCM-App-Server/GCMNotification?shareRegId=1";

	// Google Project Number
	static final String GOOGLE_PROJECT_ID = "714825122861";
	static final String MESSAGE_KEY = "message";

	static String socialnetworkmediaids[] = {"-", "fb", "wa", "we"};

	static int COMMAND_NEWWIN = 1001;
	static int COMMAND_BACKWIN = 1002;

}
