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
import com.google.ads.AdRequest;
import com.google.ads.InterstitialAd;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class active_sync extends Service {
	private static final String TAG = "MyService";
	Context context;
	//MediaPlayer player;
	String last_read;
	Activity act;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	String username;
	String password;
	@Override
	public void onCreate() {
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
	    password = settings.getString("zinc_password", "");
	    username = settings.getString("zinc_username", "");
		//Toast.makeText(this, "Contact Syncing Activated", Toast.LENGTH_LONG).show();
		context=getBaseContext();
	
	}
	
	@Override
	public void onDestroy() {
		//Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		Log.d(TAG, "onDestroy");
	}
	
	@Override
	public void onStart(Intent intent, int startid) {
		//Toast.makeText(this, "My Service contact", Toast.LENGTH_LONG).show();
		//getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true,new MyCOntentObserver());
		
		
	}
	@Override  
	public int onStartCommand(Intent intent, int flags, int startId) { 
		//MediaPlayer mp = MediaPlayer.create(active_sync.this, R.raw.notif);  
		//mp.start();
		getContentResolver().registerContentObserver(ContactsContract.Contacts.CONTENT_URI, true,new MyCOntentObserver());
	    return START_STICKY;    
	} 
	public class MyCOntentObserver extends ContentObserver{
        public MyCOntentObserver() {
            super(null);
        }
        @SuppressLint("NewApi")
		@Override
        public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        boolean chk = isPhoneContactAppIsUp(context);
        if(chk == true)
        {
        	/*NotificationCompat.Builder mBuilder =
        	        new NotificationCompat.Builder(active_sync.this)
        	        .setSmallIcon(R.drawable.backit)
        	        .setContentTitle("My notification")
        	        .setContentText("Hello World!");
        	// Creates an explicit intent for an Activity in your app
        	Intent resultIntent = new Intent(active_sync.this, MainActivity.class);

        	// The stack builder object will contain an artificial back stack for the
        	// started Activity.
        	// This ensures that navigating backward from the Activity leads out of
        	// your application to the Home screen.
        	TaskStackBuilder stackBuilder = TaskStackBuilder.create(active_sync.this);
        	// Adds the back stack for the Intent (but not the Intent itself)
        	stackBuilder.addParentStack(MainActivity.class);
        	// Adds the Intent that starts the Activity to the top of the stack
        	stackBuilder.addNextIntent(resultIntent);
        	PendingIntent resultPendingIntent =
        	        stackBuilder.getPendingIntent(
        	            0,
        	            PendingIntent.FLAG_UPDATE_CURRENT
        	        );
        	mBuilder.setContentIntent(resultPendingIntent);
        	NotificationManager mNotificationManager =
        	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        	int mId = 1;
			// mId allows you to update the notification later on.
        	mNotificationManager.notify(mId, mBuilder.build());*/
            //Log.e("","~~~~~~-----------------"+selfChange);
        	    //Toast.makeText(getApplicationContext(), getString(R.string.change_toast), Toast.LENGTH_LONG).show();	  
        Cursor cursor = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null, null);

    	while (cursor.moveToNext()) {
    	name.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));

    	number.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
    	email.add(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
    	}
    	cursor.close();
        if(name.size()==0 && number.size()==0)
        {
        	//Toast.makeText(context,"No Contacts In Contact List",Toast.LENGTH_LONG).show();
        }
        else
        {
        //Toast.makeText(context,lc,Toast.LENGTH_LONG).show(); 
        new server_contact().execute();
        Toast.makeText(context, "Zinc Contact Base Updated", Toast.LENGTH_LONG).show();	
        }
      }

        }  

        @Override
        public boolean deliverSelfNotifications() {
            return false;
        }
    }
	
	public static boolean isPhoneContactAppIsUp(Context context) {
        try {
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (!tasks.isEmpty()) {
                ComponentName topActivity = tasks.get(0).topActivity;
                String string = topActivity.getPackageName();
                Log.v("pacage name", "packagename=" + string);
                if (string.equals("com.android.contacts")) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
	
	
	List<BasicNameValuePair> nameValuePairs = new ArrayList();
	ArrayList<String> name = new ArrayList<String>();
	ArrayList<String> number = new ArrayList<String>();
	ArrayList<String> email = new ArrayList<String>();
	
	public class server_contact extends AsyncTask<String, Integer, Long> {	
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
	        HttpPost httppost = new HttpPost("http://touchme.co.in/zinc/sync2.php");
	       
           // add an HTTP variable and value pair
	//nameValuePairs.add(new BasicNameValuePair("mydata", arg0.toString()));
	
	        String json2;
			for(int i=0;i<number.size();i++)
			{
				nameValuePairs.add(new BasicNameValuePair("name"+i, name.get(i)));
				nameValuePairs.add(new BasicNameValuePair("number"+i, number.get(i)));
				nameValuePairs.add(new BasicNameValuePair("email"+i, email.get(i)));
			}
			
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

}
