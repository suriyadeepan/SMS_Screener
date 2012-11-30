package com.general.msg_screener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

public class Main extends Activity 
{
	
	Button quitButton;
	EditText msgId,msgKeys;
	ToggleButton serviceStatus;
	
	static String smsId=null;
	static String smsKeys=null;
	
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setupViews();
        
    }

    // contents of the page - Views are
    //  setup here
    private void setupViews() 
    {
    	// setup the Edittexts
    	msgId=(EditText)findViewById(R.id.t_msg_id);
    	msgKeys=(EditText)findViewById(R.id.t_keywords);
    	
    	// setup togglebutton
    	serviceStatus=(ToggleButton)findViewById(R.id.toggle_service_status);
    	
    	serviceStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
    	    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	        if (isChecked) 
    	        {
    	            // The toggle is enabled
    	        	smsId=msgId.getText().toString();
    	        	smsKeys=msgKeys.getText().toString();
    	        	
    	        } 
    	        else 
    	        {
    	        	// toggle is disabled
    	        	smsId=null;
    	        	smsKeys=null;
    	            
    	        }
    	    }
    	});
    	
    	
		// setup quit button
    	quitButton = (Button)findViewById(R.id.b_quit);
		
    	quitButton.setOnClickListener(new View.OnClickListener() {
			
			
			public void onClick(View arg0) 
			{
				
				// end app if quit button is pressed
				finish();
			
				
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
    
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
