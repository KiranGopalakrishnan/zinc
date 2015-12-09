package com.example.zinc;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class reset_login_credentials extends Activity {
	WebView wv,wv2;
	Button button2;
	Button button1;
	String mnumber;
	String password_text;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset_login_credentials);
		wv = (WebView) findViewById(R.id.webView1);
		wv.setBackgroundColor(0x00000000);
		wv.setVisibility(View.INVISIBLE);
		wv2 = (WebView) findViewById(R.id.webView2);
		wv2.setBackgroundColor(0x00000000);
		wv2.setVisibility(View.INVISIBLE);
		button1 = (Button)findViewById(R.id.button1);
	    // Registers the onClick listener with the implementation above
	    button1.setOnClickListener(reset);
	    button2 = (Button)findViewById(R.id.button2);
	    // Registers the onClick listener with the implementation above
	    button2.setOnClickListener(no);
	    Bundle bundle = getIntent().getExtras();
	 	   mnumber=bundle.getString("usrnm");
	 	   password_text=bundle.getString("password");
	 		
	   }
	private OnClickListener no = new OnClickListener() {
	    public void onClick(View v) {
	    	button2.setVisibility(View.INVISIBLE);
			wv2.loadUrl("file:///android_asset/logging1.GIF");
	    	wv2.setVisibility(View.VISIBLE);
	    	Intent mainIntent = new Intent(reset_login_credentials.this,MainActivity.class);
	    	startActivity(mainIntent);
	    	finish();
	    }
	};
	
	private OnClickListener reset = new OnClickListener() {
	    
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			button1.setVisibility(View.INVISIBLE);
			wv.loadUrl("file:///android_asset/logging1.GIF");
	    	wv.setVisibility(View.VISIBLE);
	    	 SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	 		 SharedPreferences.Editor editor = settings.edit(); 
	 		                         editor.putString("zinc_username", mnumber );
	 		                         editor.putString("zinc_password", password_text);
	 		                         editor.commit();
	     	Intent mainIntent = new Intent(reset_login_credentials.this,MainActivity.class);
	     	startActivity(mainIntent);
	     	finish();
		}
	};
	
}
