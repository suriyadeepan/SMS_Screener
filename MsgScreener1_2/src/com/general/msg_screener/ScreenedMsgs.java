package com.general.msg_screener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ScreenedMsgs extends Activity 
{
	TextView scrMsgs;
	Button backButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        	// setup fullscreen
        /***************************************************/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*************************************************/
        
        setContentView(R.layout.screened_msgs);
        
        setupViews();
        
        try {
        	
			readFromFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    

    private void readFromFile() throws IOException 
    {
    	String fileContent="";
    	StringBuffer fileContentBuffer=new StringBuffer(fileContent);
    	
    	try {
    	    // open the file for reading
    	    InputStream instream = openFileInput("screened-msgs.dat");
    	 
    	    // if file the available for reading
    	    if (instream!=null) {
    	      // prepare the file for reading
    	      InputStreamReader inputreader = new InputStreamReader(instream);
    	      BufferedReader buffreader = new BufferedReader(inputreader);
    	                 
    	      String line;
    	 
    	      scrMsgs.setText("\n\n\n");
    	      
    	      // read every line of the file into the line-variable, on line at the time
    	      while (( line = buffreader.readLine()) != null) {
    	        // do something with the settings from the file
    	    	  // scrMsgs.append(line);
    	    	  fileContentBuffer.append(line);
    	    	  
    	      }
    	 
    	    } else{
    	    	scrMsgs.setText("No such file dude!");
    	    }
    	     
    	    // close the file again       
    	    instream.close();
    	
    	    
    	    
    	    
    	    fileContent=fileContentBuffer.toString();
    	    
    	    String temp=fileContent;
    	    
    	    scrMsgs.append(fileContent);
    	    /*
    	    int msgNum=temp.length()-temp.replace("@","").length();
    	    
    	    int offset = 0;
    	    
    	    for(int i=0;i<msgNum;i++)
    	    {
    	    
    	    int pos1=fileContent.indexOf('!',offset);
    	    int pos2=fileContent.indexOf('!',pos1+1);
    	    
    	    String singleMsg=fileContent.substring(pos1+1,pos2-1);
    	    
    	    scrMsgs.setText(singleMsg);
    	    scrMsgs.setText("\n\n");
    	    
    	    offset=pos2;
    	    
    	    }
    	    
    	    */
    	    
    	  } catch (java.io.FileNotFoundException e) {
    	    // do something if the myfilename.txt does not exits
    	  }
		
		
	}


    
	private void setupViews()
    {
    	
    	scrMsgs=(TextView)findViewById(R.id.t_screened_msgs);
		
    	
    	// setup back button
    	backButton=(Button)findViewById(R.id.b_back);
    	
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
