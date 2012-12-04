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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity 
{
	
	/************************************************/
	// Variable Declaration
	
	// UI components
	Button quitButton;
	Button updateButton;
	EditText msgId,msgKeys;
	
	// Temporary storage of ID and filter Keywords
	static String smsId=null;
	static String smsKeys=null;
	
	
	// Shared Preferences 
	SharedPreferences sharedPrefObj;
	static String fileName="keys";
	static String firstKey="zombie";
	
    /***********************************************/
	
	
	
	// onCreate Method - called once the app is started
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        

        // setup fullscreen
        /***************************************************/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*************************************************/
        
        
        setContentView(R.layout.activity_main);
        
        
        // UI components - initialization and adding listeners...
        setupViews();
        
        
        // checks if the application is running for the first time
        // if so, initializes the preferences
        initPreferenceFile();
        
    }// end of onCreate() method...
    
    

    private void initPreferenceFile() 
    {
    	// instantiate shared preferences object with the filename
    	sharedPrefObj=getSharedPreferences(fileName, 0);
        
    	// check if data exists @ keys 34,43
    	// if not store dummy String @ 34,43
        if(!sharedPrefObj.contains("34") || !sharedPrefObj.contains("43"))
        {
        	Toast.makeText(Main.this, "Preference File doesn't exist! Initialization taking place...", Toast.LENGTH_SHORT).show();
        	
        	SharedPreferences.Editor editor=sharedPrefObj.edit();
        	editor.putString("34",firstKey);
        	editor.commit();
        	editor.putString("43",firstKey);
        	editor.commit();
        }
		
		
	}// end of initPreferenceFile() method...

    
    
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
				
				// if atleast anyone of the Edittexts is not empty
				//  proceed
				if( !msgId.getText().toString().equals("") || !msgKeys.getText().toString().equals("") )
				{
					
				// get the text @ Edittexts
				smsId=msgId.getText().toString();
	        	smsKeys=msgKeys.getText().toString();
	        	
	        	
	        	// removing empty spaces
	        	smsKeys.replace(" ","");
	        	smsId.replace(" ","");
	        	
	        	// append sms key to preference file
	        	// instatntiate sharedPrefObj
	        	sharedPrefObj=getSharedPreferences(fileName, 0);
	        	
	        	
	        	// this method appends current smsKey to the pref file
	        	updateDB(sharedPrefObj,"34",smsKeys);
	        	
	        	// update smsID
	        	updateDB(sharedPrefObj,"43",smsId);
	        	
	        	 
	        	 
	        	/* 
	        	String keysAtDb=sharedPrefObj.getString("34", "Unable to retreive keys!");
	        	SharedPreferences.Editor editor=sharedPrefObj.edit();
	        	editor.putString("34",keysAtDb+" "+smsKeys);
	        	editor.commit();
	        	
	        	// append sms ID to preference file
	        	editor.putString("43", sharedPrefObj.getString("43", "Unable to retreive IDs!")+ " "+smsId);
	        	editor.commit();
	        	
	        	*/
	        	
	        	// inform the user that the keyword/ID is updated in DB
	        	Toast.makeText(Main.this, "Keyword/Sender ID updated in the database!", Toast.LENGTH_LONG).show();
	        	
	        	
				}
				
				// if both fiels are empty, inform the user that it is so
				else
					Toast.makeText(Main.this, "Verify your Keyword/Sender ID!", Toast.LENGTH_LONG).show();
				
			}
			
		});// end of listener of updateButton
    	
    	
    	
		// setup quit button
    	quitButton = (Button)findViewById(R.id.b_quit);
		
    	quitButton.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) 
			{
				
				// show confirmation dialog if quit button
				//  is pressed
				showDialog("Are you Sure?");
			
				
			}
		});
    	// end of quit button section
    	
	}// end of method "setupViews()"


	// this method updates the filter keywords or Sender ID @
	//  the preference file
	private void updateDB(SharedPreferences sharedPrefObj, String key,
			String dataString){
	
		// retreive the data present in the pref file with key 
		//  append it to the current String and update data @
		//   the pref file
		
		String dataAtDb=sharedPrefObj.getString(key, "Unable to retreive keys!");
    	SharedPreferences.Editor editor=sharedPrefObj.edit();
    	
    	editor.putString(key,dataAtDb+" "+dataString);
    	editor.commit();
		
		
	}// end of updateDB() method...
    
	
	// this method creates a confirmation dialogue 
	//  and shows it
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

    	
    }// end of showDialog() method...
    
    
    
    
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	// this method is called when a MENU item is selected 
	//  by the user
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		
		// a switch case for selecting the operation 
		//  based on id of menu item
		switch(item.getItemId())
		{
		
			// menu item => SPAM
		case R.id.m_open_scr_msgs:
			startActivity(new Intent(this,ScreenedMsgs.class));
			return true;
			
			// menu item => QUIT
		case R.id.m_quit:
			showDialog("Are you Sure?");
			return true;
			
			// menu item => DATABASE
		case R.id.m_db:
			sharedPrefObj=getSharedPreferences(fileName, 0);
			String keysAtDb=sharedPrefObj.getString("34", "Couldn't get son!");
			String idsAtDb=sharedPrefObj.getString("43", "Couldn't get son!");
			Toast.makeText(Main.this,"Keys @ DB: "+keysAtDb+"\n IDs @ DB: "+idsAtDb, Toast.LENGTH_LONG).show();
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		
		}
		
		
		
	}// end of method onOptionsItemSelected()...
	
	
}// END OF ACTIVITY..
