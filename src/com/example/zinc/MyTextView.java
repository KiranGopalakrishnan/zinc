package com.example.zinc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView
{
	public MyTextView(Context context) {
		super(context);
		setMyText(context);
	}
	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setMyText(context,attrs);
	}
	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setMyText(context,attrs);
	}
	
	//Implementation of setMyText
	private void setMyText(Context context)
	{
		this.setText("Hello there");
	}
	private void setMyText(Context context, AttributeSet attrs)
	{
		this.setText(getFromAttrs(context,attrs));
	}
	//Function to read the custom attribute
	//Stubbed out for now
	private String getFromAttrs(Context ctx, AttributeSet attrs)
	{
	    String myText ="Custom Text";
		return myText;
	}
}
