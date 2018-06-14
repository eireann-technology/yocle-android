package com.invest.yocle;

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


import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
		import android.annotation.SuppressLint;
		import android.content.Context;
		import android.content.Intent;
		import android.content.res.Configuration;
import android.graphics.Bitmap;
		import android.os.Bundle;
		import android.view.KeyEvent;
		import android.view.View;
		import android.view.View.OnClickListener;
		import android.widget.Button;
import android.widget.EditText;
		import android.widget.ImageView;
		import android.widget.Toast;


public class EditTextActivity extends ActionBarActivity {
    Context context;
	String socialnetwork;

	@SuppressLint("NewApi") @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edittextactivity);


		context = this;

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		EditText edittext = (EditText) findViewById(R.id.editText);
		Button confirmbutton = (Button) findViewById(R.id.confirmButton);
		Button cancelbutton = (Button) findViewById(R.id.cancelButton);
		final ImageView img = (ImageView) findViewById(R.id.imageView2);


		Bundle extras = getIntent().getExtras();
		String defaulttext;
		String imageuri = "";

		if (extras != null) {
			defaulttext = extras.getString("defaulttext");
			socialnetwork = extras.getString("socialnetwork");
			imageuri = extras.getString("imguri");
			defaulttext = defaulttext.trim();
			edittext.setText(defaulttext);
		}


		FetchImage fetch = new FetchImage(getApplicationContext(), new BitmapCallback() {
			@Override
			public void onTaskDone(Bitmap rv) {
				img.setImageBitmap(rv);

			}
		});
		fetch.execute(Config.HTTPS_SERVER_ROOT + imageuri);

		confirmbutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub

				EditText edittext = (EditText) findViewById(R.id.editText);
				String t = edittext.getText().toString().trim();
				if(t.length()==0) {
					Toast.makeText(context, getResources().getString(R.string.pls_enter_share_txt), 0).show();
                    return;
				}

				Intent intent = new Intent();
				intent.putExtra("textshare", t);
				intent.putExtra("socialnetwork", socialnetwork);
				setResult(RESULT_OK, intent);
				finish();
			}
		});


		cancelbutton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
	}


		@Override
	protected void onStart() {
	    super.onStart();
	}

	@Override
	protected void onResume() {
	    super.onResume();

	}
	
	
	@Override
	protected void onStop() {
		super.onStop();
//	    finish();
	}
	
	
	@Override
	protected void onPause() {
	    super.onPause();
	}
	
	@Override
	protected void onDestroy() {
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
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN){
            switch(keyCode)
            {
            case KeyEvent.KEYCODE_BACK:
				Intent intent = new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
	

	
}
