package com.general.msg_screener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver 
{
	
	static String fileName="keys";
	
    public MyReceiver() {
    }

    @Override
    // called when an Intent broadcast is received...
    public void onReceive(Context context, Intent intent) 
    {
    	
    	// get datea associated with intent in 
    	//  the form of a bundle
    	//   which is converted into message later
    	Bundle bundle = intent.getExtras();
    	
    	// get an obj array from the bundle
    	Object[] msgs = (Object[])bundle.get("pdus");
    	
    	// initialize an SmsMessage array 
    	SmsMessage[] sms = new SmsMessage[msgs.length];
    	
    	
    	// convert object array into sms-msg array
    	for(int i=0;i<sms.length;i++)
    	{
    		sms[i]=SmsMessage.createFromPdu( (byte[])msgs[i] );
    		    		
    	}
    	
    	// get the keys stored in preferences file
    	SharedPreferences sharedPrefObj=context.getSharedPreferences(fileName,0);
    	String keysAtDb=sharedPrefObj.getString("34", "Unable to retreive keys!");
    	String idsAtDb=sharedPrefObj.getString("43", "Unable to retreive ID's!");
    	
    	
    	
    	// update the text box in the main layout
    	//  by calling the static function in the
    	//   main activity
    	for(SmsMessage msg:sms)
    	{
    		String senderId=msg.getOriginatingAddress();
    		String msgContent=msg.getMessageBody();
    		
    		
    		String[] keys=keysAtDb.split(" ");
    		String[] ids=idsAtDb.split(" ");
    		
    		boolean msgStatus=false;
    		
    		
    		// check if the 
    		for(int i=0;i<keys.length;i++)
    		{
    			if(msgContent.contains(keys[i]))
    				msgStatus=true;
    		}
    		
    		for(int i=0;i<ids.length;i++)
    		{
    			if(senderId.contains(ids[i]))
    				msgStatus=true;
    		}
    		
    		
    		
    		//if((storedMsgId!=null || storedMsgKeys!=null))
    		//{
    		//if(msgContent.contains(storedMsgKeys) || senderId.contains(storedMsgId) )
    		if(msgStatus)
    		{
    			
    			Toast.makeText(context,"SMS from \""+senderId+"\" screened! Stored in Spam!",Toast.LENGTH_LONG).show();
    			
    			
    			Date d=new Date();
    			
    			try {
					saveMsg(context,"!"+senderId+"@"+d.toString()+"#"+msgContent+"!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			abortBroadcast();
    			break;
    		}
    		
    		
    	}// end of for(SmsMessage msg:sms)
    	
    	

    	        
    }// end of onReceive method
    
    
    public void saveMsg(Context context, String data) throws IOException 
    {

        FileOutputStream FoutS = null;
        OutputStreamWriter outSW = null;
        	
				FoutS = context.openFileOutput("screened-msgs.dat", Context.MODE_APPEND);
				
			
				outSW = new OutputStreamWriter(FoutS);
	    
				
				outSW.append(data);
				
			
		        outSW.flush();
        
    }
    
}
