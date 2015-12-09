package com.example.zinc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.zinc.login.server_login;
import com.example.zinc.register.server_register;


import com.google.ads.*;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener  {
	String username;
    String password;
    WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId("ca-app-pub-8429573372209071/7400646147");*/
		setContentView(R.layout.activity_main);
		/*AdView adView = (AdView)this.findViewById(R.id.adView1);
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);*/
		//AdRequest request = new AdRequest().
		/* AdView adView1 = new AdView(this, AdSize.BANNER,
			    "ca-app-pub-8429573372209071/7400646147");*/
		Button button = (Button)findViewById(R.id.button4);
	    // Registers the onClick listener with the implementation above
	    button.setOnClickListener(but1);
	    Button button2 = (Button)findViewById(R.id.button5);
	    // Registers the onClick listener with the implementation above
	    button2.setOnClickListener(but2);
	    
	     
		wv = (WebView) findViewById(R.id.webView1);
		wv.setBackgroundColor(0x00000000);
		wv.loadUrl("file:///android_asset/logging1.GIF");
		 SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		    password = settings.getString("zinc_password", "");
		    username = settings.getString("zinc_username", "");
    	Context context = getApplicationContext();
    	 Calendar cal = Calendar.getInstance();
         cal.add(Calendar.SECOND, 10);
		Intent intent = new Intent(this, active_sync.class);
	      
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
       
        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //for 30 mint 60*60*1000
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                     1*60*1000, pintent);
    	
    	//getContentResolver().registerContentObserver(
          //      ContactsContract.Contacts.CONTENT_URI, true,new MyCOntentObserver());   

    	Cursor cursor = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);

    	while (cursor.moveToNext()) {
    	name.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

    	number.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
    	email.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
    	}
    	cursor.close();
        lc=String.valueOf(name.size());
        Toast.makeText(context,lc,Toast.LENGTH_LONG).show();
        if(name.size()==0 && number.size()==0)
        {
        	Toast.makeText(context,"No Contacts In Contact List",Toast.LENGTH_LONG).show();
        }
        else
        {
        Toast.makeText(context,lc,Toast.LENGTH_LONG).show(); 
        new server_contact().execute();
        }
			}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	private OnClickListener but1 = new OnClickListener() {
	    public void onClick(View v) {
	    	 Intent mainIntent = new Intent(MainActivity.this,contact_book.class);
		    	startActivity(mainIntent);
		    	finish();
	    }
	};
	private OnClickListener but2 = new OnClickListener() {
	    public void onClick(View v) {
	    	Intent mainIntent = new Intent(MainActivity.this,export.class);
	    	startActivity(mainIntent);
	    	finish();
	    }
	};	    
	//String name;
	//String phoneNumber;
	List<BasicNameValuePair> nameValuePairs = new ArrayList();
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> number = new ArrayList<String>();
	ArrayList<String> email = new ArrayList<String>();
	String lc;
	private OnClickListener mCorkyListener = new OnClickListener() {
	    public void onClick(View v) {

	    	
            	  }
	};
	public class server_contact extends AsyncTask<String, Integer, Long> {	
	     protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }

	     protected void onPostExecute(Long result) {
	    	 wv = (WebView) findViewById(R.id.webView1);
	 		wv.setVisibility(View.INVISIBLE);
	 		 TextView vv = (TextView) findViewById(R.id.textView1);
	 		 vv.setText("Sync Complete");
	 		 vv.setPadding(0, 20, 0, 20);
	     }
	     Context context = getApplicationContext();
	    	

		@Override
		protected Long doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
			HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://touchme.co.in/zinc/sync.php");
	       
            // add an HTTP variable and value pair
	//nameValuePairs.add(new BasicNameValuePair("mydata", arg0.toString()));
	
	        String json2;
			for(int i=0;i<number.size();i++)
			{
				nameValuePairs.add(new BasicNameValuePair("name"+i, name.get(i)));
				nameValuePairs.add(new BasicNameValuePair("number"+i, number.get(i)));
				nameValuePairs.add(new BasicNameValuePair("email"+i, email.get(i)));
			}
			
				nameValuePairs.add(new BasicNameValuePair("list_count",lc));
				nameValuePairs.add(new BasicNameValuePair("username",username));
				nameValuePairs.add(new BasicNameValuePair("password",password));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				//nameValuePairs.add(new BasicNameValuePair("value", myArr.get(0)));
				HttpResponse response = httpclient.execute(httppost);
				
				if (response.getStatusLine().getStatusCode() == 200)
				{
				    HttpEntity entity = response.getEntity();
				    //json = EntityUtils.toString(entity);
				    json2=EntityUtils.toString(entity);
				   // JSONObject user_account_data = new JSONObject(json2);
				    //user_account_data.get("email");
				    Log.e("log_tag", "response"+json2);
				    name.clear();
					number.clear();
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
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
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
