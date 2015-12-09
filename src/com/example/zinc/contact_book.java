package com.example.zinc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.zinc.export.export_data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class contact_book extends Activity implements OnQueryTextListener {
	private ListView mainListView ;  
	  private ArrayAdapter<String> listAdapter ;
	  String user;
	  String pas;
	  SearchView mtxt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_book);
		mainListView = (ListView) findViewById( R.id.ListView1);
		
		
		// Create ArrayAdapter using the planet list.  
		listAdapter = new ArrayAdapter<String>(this, R.layout.tv);  

		new export_data().execute();
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
	    user = settings.getString("zinc_username", "");
	    pas = settings.getString("zinc_password", "");
	    mainListView.setOnItemClickListener(new OnItemClickListener()
		{

		        

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					// TODO Auto-generated method stub
					String text = (String) (((ArrayAdapter)parent.getAdapter()).getItem(position));
					Intent mainIntent = new Intent(contact_book.this,view_contact.class);
					mainIntent.putExtra("data", text);
					startActivity(mainIntent);
			    	//finish();
					//Toast.makeText(getBaseContext(),text, Toast.LENGTH_LONG).show();
				}
		});
	    
	   /* mtxt = (SearchView) findViewById(R.id.searchView1);
	    mtxt.setOnQueryTextListener(new OnQueryTextListener()
	    {
	        public boolean onQueryTextSubmit(String query) {
	        	List <String> listClone = new ArrayList<String>(); 
	            for (String string : listAdapter) {
	                if(string.matches("(?i)(bea).*")){
	                    listClone.add(string);
	                }
	            }
				return false;
	            //Do whatever you want with your Cursor here
	        }

			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
	    });*/
		}
	
	class export_data extends AsyncTask<String, Integer, Long> {	
	     protected void onProgressUpdate(Integer... progress) {
	         //setProgressPercent(progress[0]);
	     }
	     AbsoluteLayout linlaHeaderProgress = (AbsoluteLayout) findViewById(R.id.linlaHeaderProgress);
			@Override
			protected void onPreExecute() {    
			    // SHOW THE SPINNER WHILE LOADING FEEDS
				mainListView.setVisibility(View.INVISIBLE);
			    linlaHeaderProgress.setVisibility(View.VISIBLE);
			}
	     protected void onPostExecute(Long result) {
	    	 mainListView.setVisibility(View.VISIBLE);
	     }
	     Context context = getApplicationContext();
	    	

		@Override
		protected Long doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			try {
			HttpClient httpclient = new DefaultHttpClient();
	        HttpPost httppost = new HttpPost("http://touchme.co.in/zinc/read.php");
	       
         // add an HTTP variable and value pair
	//nameValuePairs.add(new BasicNameValuePair("mydata", arg0.toString()));
	
		String json2;
		List nameValuePairs = new ArrayList();
		
				nameValuePairs.add(new BasicNameValuePair("us", user));
				nameValuePairs.add(new BasicNameValuePair("password", pas));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				
				//Log.e("log_tag", "pass"+pas);
			    //Log.e("log_tag", "usr"+user);
				//nameValuePairs.add(new BasicNameValuePair("value", myArr.get(0)));
				HttpResponse response = httpclient.execute(httppost);
				
				if (response.getStatusLine().getStatusCode() == 200)
				{
				    HttpEntity entity = response.getEntity();
				    //json = EntityUtils.toString(entity);
				    json2=EntityUtils.toString(entity);// some response object
				    String result = json2;
				    
				    
				    Log.e("log_tag", "response"+json2);
				    if(json2.equals("0"))
				    {
					// CONVERT RESPONSE STRING TO JSON ARRAY
					
					}
				    else
				    {
				    	JSONObject ja = new JSONObject(result);
						for(int i=1;i<=ja.length();i++)
						{
			           JSONObject jsonObject=ja.getJSONObject(""+i+"");
			          final String name= jsonObject.getString("name");
			          final String mnumber= jsonObject.getString("number");
			          //Log.e("log_tag", "response"+name);
			          runOnUiThread(new Runnable(){

				          @Override
				          public void run(){
				            //update ui here
				            // display toast here
				        	  listAdapter.add( name + " - " + mnumber );
				          }
				       });
			             
			        
				    }
				          runOnUiThread(new Runnable(){

					          @Override
					          public void run(){
					            //update ui here
					            // display toast here
					        	  mainListView.setAdapter( listAdapter );
					          }
					       });	
					
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
	public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
