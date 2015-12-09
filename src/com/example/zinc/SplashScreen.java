package com.example.zinc;

import android.app.Activity;
//import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Color;
//import android.graphics.PorterDuff.Mode;
import android.os.CountDownTimer;
//import android.os.Handler;
//import android.os.Message;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.webkit.WebView;
import android.widget.ProgressBar;
//import android.widget.Toast;

public class SplashScreen extends Activity {
	int myProgress = 0;
	ProgressBar myProgressBar;
	CountDownTimer countDownTimer;
	String username;
	String password;
	WebView wv;
	int length_in_milliseconds=4000;
	 final SplashScreen sPlashScreen = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//myProgressBar.getProgressDrawable().setColorFilter(Color.WHITE, Mode.SRC_IN);
		setContentView(R.layout.splash);
		wv = (WebView) findViewById(R.id.webView3);
		wv.setBackgroundColor(0x00000000);
		wv.loadUrl("file:///android_asset/blue_small.GIF");
		 SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		    password = settings.getString("zinc_password", "");
		    username = settings.getString("zinc_username", ""); 
		countDownTimer = new CountDownTimer(5000,001) {
	        private boolean warned = false;
	        @Override
	        public void onTick(long millisUntilFinished_) {
	        	
	        }

	        @Override
	        public void onFinish() {
	        	/*Context context= getApplicationContext();
	        	Toast.makeText(context,"finished", Toast.LENGTH_LONG).show();*/
	        	if(username==""&password=="")
	        	{
	        		Intent mainIntent = new Intent(SplashScreen.this,register.class);
		        	startActivity(mainIntent);
		        	finish();
	        	}
	        	else
	        	{
	        		Intent mainIntent = new Intent(SplashScreen.this,login.class);
		        	startActivity(mainIntent);
		        	finish();
	        	}
	        	// do whatever when the bar is full
	        }
	    }.start();
		//new Thread(myThread).start();
	}

}
