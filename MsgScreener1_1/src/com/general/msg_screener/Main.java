package com.general.msg_screener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity 
{
	
	Button quitButton;
	Button updateButton;
	
	EditText msgId,msgKeys;
	
	
	static String smsId=null;
	static String smsKeys=null;
	
	SharedPreferences sharedPrefObj;
	static String fileName="keys";
	static String firstKey="zombie";
	
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setupViews();
        
        sharedPrefObj=getSharedPreferences(fileName, 0);
        
        if(!sharedPrefObj.contains("34") || !sharedPrefObj.contains("43"))
        {
        	Toast.makeText(Main.this, "Preference File doesn't exist! Initialization taking place...", Toast.LENGTH_SHORT).show();
        	
        	SharedPreferences.Editor editor=sharedPrefObj.edit();
        	editor.putString("34",firstKey);
        	editor.commit();
        	editor.putString("43",firstKey);
        	editor.commit();
        }
        
        
    }

    // contents of the page - Views are
    //  setup here
    private void setupViews() 
    {
    	// setup the Edittexts
    	msgId=(EditText)findViewById(R.id.t_msg_id);
    	msgKeys=(EditText)findViewById(R.id.t_keywords);
    
    	// setup update button
    	updateButton=(Button)findViewById(R.id.b_update);
    	
    	updateButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) 
			{
				if( !msgId.getText().toString().equals("") || !msgKeys.getText().toString().equals("") )
				{
					
				smsId=msgId.getText().toString();
	        	smsKeys=msgKeys.getText().toString();
	        	
	        	
	        	// removing spaces
	        	smsKeys.replace(" ","");
	        	smsId.replace(" ","");
	        	
	        	// append sms key to preference file
	        	sharedPrefObj=getSharedPreferences(fileName, 0);
	        	String keysAtDb=sharedPrefObj.getString("34", "Unable to retreive keys!");
	        	SharedPreferences.Editor editor=sharedPrefObj.edit();
	        	editor.putString("34",keysAtDb+" "+smsKeys);
	        	editor.commit();
	        	
	        	// append sms ID to preference file
	        	editor.putString("43", sharedPrefObj.getString("43", "Unable to retreive IDs!")+ " "+smsId);
	        	editor.commit();
	        	
	        	Toast.makeText(Main.this, "Keyword/Sender ID updated in the database!", Toast.LENGTH_LONG).show();
				}
				
				else
					Toast.makeText(Main.this, "Verify your Keyword/Sender ID!", Toast.LENGTH_LONG).show();
				
			}
		});
    	
    	
    	
		// setup quit button
    	quitButton = (Button)findViewById(R.id.b_quit);
		
    	quitButton.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) 
			{
				
				// end app if quit button is pressed
				showDialog("Are you Sure?");
			
				
			}
		});
    	// end of quit button section
    	
	}// end of method "setupViews()"


    
    
    public static String getMsgId()
    {
		return smsId;
    		
    }
    
    public static String getMsgKeys()
    {
    	return smsKeys;
    	
    }
    
    public void showDialog(String dialogTitle)
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setCancelable(true);
    	builder.setIcon(R.drawable.about_ic);
    	builder.setTitle(dialogTitle);
    	builder.setInverseBackgroundForced(true);
    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    	  @Override
    	  public void onClick(DialogInterface dialog, int which) {
    	    finish();
    	  }
    	});
    	builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
    	  @Override
    	  public void onClick(DialogInterface dialog, int which) {
    	    dialog.dismiss();
    	  }
    	});
    	AlertDialog alert = builder.create();
    	alert.show();

    	
    }
    
    
    
    
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		switch(item.getItemId())
		{
		case R.id.m_open_scr_msgs:
			startActivity(new Intent(this,ScreenedMsgs.class));
			return true;
			
		case R.id.m_quit:
			showDialog("Are you Sure?");
			return true;
			
		case R.id.m_db:
			sharedPrefObj=getSharedPreferences(fileName, 0);
			String keysAtDb=sharedPrefObj.getString("34", "Couldn't get son!");
			String idsAtDb=sharedPrefObj.getString("43", "Couldn't get son!");
			Toast.makeText(Main.this,"Keys @ DB: "+keysAtDb+"\n IDs @ DB: "+idsAtDb, Toast.LENGTH_LONG).show();
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		
		}
		
		
		
	}
	
	
}
