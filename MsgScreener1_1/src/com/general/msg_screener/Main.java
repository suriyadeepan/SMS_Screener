package com.general.msg_screener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
		
		default:
			return super.onOptionsItemSelected(item);
		
		}
		
		
		
	}
	
	
}
