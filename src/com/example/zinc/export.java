package com.example.zinc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
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
import org.json.JSONTokener;

import com.example.zinc.register.server_register;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class export extends Activity {
	String username;
	String password;
	WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.export);
		wv = (WebView) findViewById(R.id.webView1);
		wv.setBackgroundColor(0x00000000);
		wv.loadUrl("file:///android_asset/logging.GIF");
		
		new export_data().execute();
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
	    username = settings.getString("zinc_username", "");
	    password = settings.getString("zinc_password", "");
	}
	//private String jsonResult;
	public class export_data extends AsyncTask<String, Integer, Long> {	
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
	        HttpPost httppost = new HttpPost("http://10.0.2.2/zinc/read.php");
	       
          // add an HTTP variable and value pair
	//nameValuePairs.add(new BasicNameValuePair("mydata", arg0.toString()));
	
		String json2;
		List<BasicNameValuePair> nameValuePairs = new ArrayList();
		nameValuePairs.add(new BasicNameValuePair("us", username));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
				
				if (response.getStatusLine().getStatusCode() == 200)
				{
				    HttpEntity entity = response.getEntity();
				    //json = EntityUtils.toString(entity);
				    json2=EntityUtils.toString(entity);// some response object
				    String result = json2;
				    Log.e("log_tag", "response"+json2);
				    if(json2 != "0")
				    {
					// CONVERT RESPONSE STRING TO JSON ARRAY
					JSONObject ja = new JSONObject(result);
					for(int i=1;i<=ja.length();i++)
					{
		           JSONObject jsonObject=ja.getJSONObject(""+i+"");
		          String name= jsonObject.getString("name");
		          String mnumber= jsonObject.getString("number");
		          //Log.e("log_tag", "response"+name);
		          addContact(name,mnumber);
		  		
					}
						// Asking the Contact provider to create a new contact                 
				    }
		          
					}
					
				    //Toast.makeText(context,json, Toast.LENGTH_LONG).show();
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
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
	
private void addContact(String name, String phone) {
    ContentValues values = new ContentValues();
    values.put(People.NUMBER, phone);
    values.put(People.TYPE, Phone.TYPE_CUSTOM);
    values.put(People.LABEL, name);
    values.put(People.NAME, name);
    Uri dataUri = getContentResolver().insert(People.CONTENT_URI, values);
    Uri updateUri = Uri.withAppendedPath(dataUri, People.Phones.CONTENT_DIRECTORY);
    values.clear();
    values.put(People.Phones.TYPE, People.TYPE_MOBILE);
    values.put(People.NUMBER, phone);
    updateUri = getContentResolver().insert(updateUri, values);
  }
}
