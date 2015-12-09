package com.example.zinc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
//import android.widget.Button;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity {
	Button bt;
	WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		Button button = (Button)findViewById(R.id.logn);
		button.setOnClickListener(submit);
		// Registers the onClick listener with the implementation above
	     bt=(Button) findViewById(R.id.logn);
	     wv = (WebView) findViewById(R.id.webView1);
		wv.setBackgroundColor(0x00000000);
    	wv.setVisibility(View.INVISIBLE);
    	
	}
	    
	private OnClickListener submit = new OnClickListener() {
	    public void onClick(View v) {
	    	bt.setVisibility(View.INVISIBLE);
	    	wv.setBackgroundColor(0x00000000);
			wv.loadUrl("file:///android_asset/logging1.GIF");
	    	wv.setVisibility(View.VISIBLE);
	    	
	    	new server_login().execute();
	    }
	};
	
	
	String username_to_pass;
	String password_to_pass;
	String login_status;
	public class server_login extends AsyncTask<String, Integer, Long> {	
	     protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(Long result) {
	        
	     }
	     Context context = getApplicationContext();
	    	

		@SuppressWarnings("unchecked")
		@Override
		protected Long doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
			HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://touchme.co.in/zinc/login.php");
	       
           // add an HTTP variable and value pair
	//nameValuePairs.add(new BasicNameValuePair("mydata", arg0.toString()));
	
		String json2;
		ArrayList<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
		EditText username = (EditText)findViewById(R.id.username);
		EditText password = (EditText)findViewById(R.id.password); 
		String username_text=username.getText().toString().trim();
		String password_text=password.getText().toString().trim();
		nameValuePairs.add(new BasicNameValuePair("number", username_text));
		nameValuePairs.add(new BasicNameValuePair("password", password_text));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				//nameValuePairs.add(new BasicNameValuePair("value", myArr.get(0)));
				HttpResponse response = httpclient.execute(httppost);
				
				if (response.getStatusLine().getStatusCode() == 200)
				{
				    HttpEntity entity = response.getEntity();
				    //json2=entity;
				    json2 = EntityUtils.toString(entity).toString();
				   // json2=EntityUtils.toString(entity);
				    Log.e("log_tag",json2);
				  if(json2.equals("1"))
				   {
					   //login_status=json2.toString();
					    //Log.e("log_tag", "response"+json2);
						  SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
						   String password_st = settings.getString("zinc_password", "");
						   String username_st = settings.getString("zinc_username", "");
						   if(password_text.equals(password_st) && username_text.equals(username_st))
						   {
							   //Toast.makeText(context,"false", Toast.LENGTH_LONG).show();
							   
							    Intent mainIntent = new Intent(login.this,MainActivity.class);
						    	startActivity(mainIntent);
						    	finish();
						   }
						   else
						   {
							   Intent mainIntent = new Intent(login.this,reset_login_credentials.class);
							   mainIntent.putExtra("usrnm", username_text);
							   mainIntent.putExtra("passwrd", password_text);
						    	startActivity(mainIntent);
						    	finish();
						   }
				    //Toast.makeText(context,json, Toast.LENGTH_LONG).show();
				   }
				   else
				   {
					   Log.e("log_tag", "response"+json2);
					   runOnUiThread(new Runnable(){

					          @Override
					          public void run(){
					            //update ui here
					            // display toast here
					        	  Toast.makeText(context,"No User Found", Toast.LENGTH_LONG).show();
					        	  bt.setVisibility(View.VISIBLE);
					  			wv.setVisibility(View.INVISIBLE);
					          }
					       });
					    }
				}
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return null;
		}
}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	    if ((keyCode == KeyEvent.KEYCODE_BACK))
	    {
	        finish();
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
