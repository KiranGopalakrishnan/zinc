package com.example.zinc;

import com.example.zinc.login.server_login;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class view_contact extends Activity {
	String data;
	TextView number;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_contact);
		Bundle bundle = getIntent().getExtras();
		data = bundle.getString("data");
		String contact_info[]=data.split("-");
		TextView name = (TextView) findViewById(R.id.textView2);
		 number = (TextView) findViewById(R.id.textView3);
		name.setText(contact_info[0]);
		number.setText(contact_info[1]);
		ImageView call = (ImageView) findViewById(R.id.imageView2);
		call.setOnClickListener(startcall);
		
	}
	OnClickListener startcall = new OnClickListener() {
	    public void onClick(View v) {
	    	String ntc=number.getText().toString();
	    	Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ntc));
	    	startActivity(intent);
	    }
	};
	
}
