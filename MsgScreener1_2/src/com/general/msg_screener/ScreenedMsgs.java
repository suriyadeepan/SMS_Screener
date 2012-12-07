package com.general.msg_screener;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ScreenedMsgs extends Activity 
{
	
	// UI components
	TextView timeField,dateField,msgField,idField;
	TextView pageField;
	Button backButton,nextButton,previousButton;
	
	// Shared Preferences 
		SharedPreferences sharedPrefObj;
		static String fileName="keys";
		static final String prefKey_Date="100";
		static final String prefKey_Time="101";
		static final String prefKey_Msg="102";
		static final String prefKey_Id="103";

		// logger 
		
		private static final String TAG="Message_Screener.ScreenedMsgs";
		
		// misc
		// page number
        int index=0;
        int maxIndex;
		
		
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i(TAG, "OnCreate - entered");
        
        	// setup fullscreen
        /***************************************************/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*************************************************/
        
        Log.i(TAG, "OnCreate - full screen");
        
        setContentView(R.layout.screened_msgs);
        
        Log.i(TAG, "OnCreate - Set layout");
        
        setupViews();
        
        Log.i(TAG, "OnCreate - Set Views");
        
        Log.i(TAG, "OnCreate - Entering retreiveDataFromDB");
        
        retreiveDataFromDB();
        
        
        
    }// end of onCreate() method...
    
    
    public void retreiveDataFromDB()
    {
    	Log.i(TAG, "retreiveDataFromDB - entered");
    	
        sharedPrefObj=getSharedPreferences(fileName,0);
        
        Log.i(TAG, "retreiveDataFromDB - sharedPref obj created");
        
        
    	String[] dateAtDB=sharedPrefObj.getString(prefKey_Date, "Unable to retreive keys!").split("~");
    	String[] timeAtDB=sharedPrefObj.getString(prefKey_Time, "Unable to retreive ID's!").split("~");
    	String[] idAtDB=sharedPrefObj.getString(prefKey_Id, "Unable to retreive ID's!").split("~");
    	String[] msgAtDB=sharedPrefObj.getString(prefKey_Msg, "Unable to retreive ID's!").split("~");
    	
    	Log.i(TAG, "retreiveDataFromDB - variables");
    	Log.i(TAG,"dateAtDB: \n"+dateAtDB[maxIndex-index]);
    	Log.i(TAG,"timeAtDB: \n"+timeAtDB[maxIndex-index]);
    	Log.i(TAG,"idAtDB: \n"+idAtDB[maxIndex-index]);
    	Log.i(TAG,"msgAtDB: \n"+msgAtDB[maxIndex-index]);
    	Log.i(TAG,"index: \n "+(maxIndex-index));
    	
    	
    	maxIndex=dateAtDB.length-1;
    	
    	Log.i(TAG,"maxIndex: "+Integer.toString(maxIndex));
    	
    	timeField.setText(timeAtDB[maxIndex-index]);
    	dateField.setText(" "+dateAtDB[maxIndex-index]+" ");
    	 msgField.setText(" "+msgAtDB[maxIndex-index]);
    	  idField.setText(" "+idAtDB[maxIndex-index]+" ");
    	
    	
        
    }
  

    
	private void setupViews()
    {
		    	
    	timeField=(TextView)findViewById(R.id.t_time);
    	dateField=(TextView)findViewById(R.id.t_date);
    	msgField=(TextView)findViewById(R.id.t_msg_content);
    	idField=(TextView)findViewById(R.id.t_msg_id);
    	pageField=(TextView)findViewById(R.id.t_page);
    	
    	// setup next button
    	nextButton=(Button)findViewById(R.id.b_next);
    	nextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) 
			{
				if(index>=0 && index < maxIndex)
				{
					index++;
					pageField.setText("Page: "+Integer.toString(index+1));
					
					retreiveDataFromDB();
				}
				
				else
				Toast.makeText(ScreenedMsgs.this, "Dont push your LIMITS!!",Toast.LENGTH_SHORT).show();
			
				
			}
		});
		
    	
    	previousButton=(Button)findViewById(R.id.b_prev);
    	previousButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(index > 0 && index <= maxIndex)
				{
					index--;
					pageField.setText("Page: "+Integer.toString(index+1));
					retreiveDataFromDB();
					
				}
				
				else
				Toast.makeText(ScreenedMsgs.this, "Dont push your LIMITS!!",Toast.LENGTH_SHORT).show();
			
				
			}
		});
    	
    	
    	// setup back button
    	backButton=(Button)findViewById(R.id.b_home);
    	
    	backButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
		
		
	}// end of setupViews() method



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.screened_msgs, menu);
        return true;
    }



	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) 
	{
		
		switch(item.getItemId())
		{
		
		case R.id.m_quit:
		finish();
		return true;
		
		
		default:
		return super.onMenuItemSelected(featureId, item);
		
		}
	}
	
	
	  
	

	
	
}
