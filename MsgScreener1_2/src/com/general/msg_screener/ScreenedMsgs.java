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
        
        
        
        	// setup fullscreen
        /***************************************************/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*************************************************/
        
        setContentView(R.layout.screened_msgs);
        
        setupViews();
        
        
        retreiveDataFromDB();
        
        /*************************************************************/
        
        // testing Shared pref's
        
        /*
        
        sharedPrefObj=getSharedPreferences(fileName,0);
    	String[] dateAtDB=sharedPrefObj.getString(prefKey_Date, "Unable to retreive keys!").split("~");
    	String[] timeAtDB=sharedPrefObj.getString(prefKey_Time, "Unable to retreive ID's!").split("~");
    	String[] idAtDB=sharedPrefObj.getString(prefKey_Id, "Unable to retreive ID's!").split("~");
    	String[] msgAtDB=sharedPrefObj.getString(prefKey_Msg, "Unable to retreive ID's!").split("~");
    	
    	timeField.setText("  Time      : "+timeAtDB[index]);
    	dateField.setText("  Date      : "+dateAtDB[index]);
    	 msgField.setText("  Message   : "+msgAtDB[index]);
    	  idField.setText("  Sender ID : "+idAtDB[index]);
    	
    	Log.i(TAG, "onCreate - Variables\n Time= "+timeAtDB[1]+"\n Date= "+dateAtDB[1]+
    			"\n Msg = "+msgAtDB+"\n Id = "+idAtDB[1]);
        
        
        */
        
        
        /*************************************************************/
        
        
        /*
        try {
        	
			readFromFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        */
        
    }// end of onCreate() method...
    
    
    public void retreiveDataFromDB()
    {
        
        sharedPrefObj=getSharedPreferences(fileName,0);
    	String[] dateAtDB=sharedPrefObj.getString(prefKey_Date, "Unable to retreive keys!").split("~");
    	String[] timeAtDB=sharedPrefObj.getString(prefKey_Time, "Unable to retreive ID's!").split("~");
    	String[] idAtDB=sharedPrefObj.getString(prefKey_Id, "Unable to retreive ID's!").split("~");
    	String[] msgAtDB=sharedPrefObj.getString(prefKey_Msg, "Unable to retreive ID's!").split("~");
    	
    	maxIndex=dateAtDB.length-1;
    	
    	timeField.setText("  Time      : "+timeAtDB[index]);
    	dateField.setText("  Date      : "+dateAtDB[index]);
    	 msgField.setText("  Message   : "+msgAtDB[index]);
    	  idField.setText("  Sender ID : "+idAtDB[index]);
    	
    	Log.i(TAG, "onCreate - Variables\n Time= "+timeAtDB[1]+"\n Date= "+dateAtDB[1]+
    			"\n Msg = "+msgAtDB+"\n Id = "+idAtDB[1]);
        
        
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
		
    	
    	previousButton=(Button)findViewById(R.id.b_previous);
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
	
	
	  
	/*
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
	    	    
	    	    // START COMMENT HERE
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
	    	    // END COMMENT HERE
	    	    
	    	    
	    	  
	    	    
	    	  } catch (java.io.FileNotFoundException e) {
	    	    // do something if the myfilename.txt does not exits
	    	  }
			
			
		}*/

	
	
}
