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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class register extends Activity {
	String mnumber;
	String email;
	TextView txt;
	WebView wv;
	Button bt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		Context mAppContext=getApplicationContext();
		//TelephonyManager tmgr=(TelephonyManager)mAppContext.getSystemService(Context.TELEPHONY_SERVICE);
		//mnumber=tmgr.getLine1Number().toString();
		//UserEmailFetcher ml = new UserEmailFetcher();
		//email=ml.getEmail(mAppContext).toString();
		Button button = (Button)findViewById(R.id.register);
		txt = (TextView)findViewById(R.id.textView1);
		wv = (WebView) findViewById(R.id.webView1);
	    bt=(Button) findViewById(R.id.register);
	    wv.setVisibility(View.INVISIBLE);
	    // Registers the onClick listener with the implementation above
	    button.setOnClickListener(submit);
	    txt.setOnClickListener(txtclk);
	}
	private OnClickListener submit = new OnClickListener() {
	    public void onClick(View v) {
	    	bt.setVisibility(View.INVISIBLE);
	    	wv.setVisibility(View.VISIBLE);
			wv.setBackgroundColor(0x00000000);
			wv.loadUrl("file:///android_asset/logging1.GIF");
	    	new server_register().execute();
	    }
	};
	
	private OnClickListener txtclk = new OnClickListener() {
	    public void onClick(View v) {
	    	Intent mainIntent = new Intent(register.this,login.class);
		    	startActivity(mainIntent);
		    	finish();
	    }
	};
	public class server_register extends AsyncTask<String, Integer, Long> {	
	     protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(Long result) {
	        
	     }
	     Context context = getApplicationContext();
	    	

		@Override
		protected Long doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
			HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://touchme.co.in/zinc/register.php");
	       
           // add an HTTP variable and value pair
	//nameValuePairs.add(new BasicNameValuePair("mydata", arg0.toString()));
	
		String json2;
		List nameValuePairs = new ArrayList();
		EditText mn = (EditText)findViewById(R.id.number);
		String mnumber=mn.getText().toString();
		
		EditText password = (EditText)findViewById(R.id.password);
		String password_text=password.getText().toString();
		nameValuePairs.add(new BasicNameValuePair("password", password_text));
		//nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("number", mnumber));	
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				//nameValuePairs.add(new BasicNameValuePair("value", myArr.get(0)));
				HttpResponse response = httpclient.execute(httppost);
				
				if (response.getStatusLine().getStatusCode() == 200)
				{
				    HttpEntity entity = response.getEntity();
				    //json = EntityUtils.toString(entity);
				    json2=EntityUtils.toString(entity).toString();
				    Log.e("log_tag", "response"+ json2);
				    if(json2.equalsIgnoreCase("1"))
				    {
				    	 SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
						 SharedPreferences.Editor editor = settings.edit(); 
						                         editor.putString("zinc_username", mnumber );
						                         editor.putString("zinc_password", password_text);
						                         editor.commit();
				    	Intent mainIntent = new Intent(register.this,MainActivity.class);
			        	startActivity(mainIntent);
			        	finish();
				    }
				    else
				    {
				    	//txt.setText(json2);
				    	Intent mainIntent = new Intent(register.this,login.class);
			        	startActivity(mainIntent);
				    	finish();
			        	//Toast.makeText(context,"Registration Failed", Toast.LENGTH_LONG).show();
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
