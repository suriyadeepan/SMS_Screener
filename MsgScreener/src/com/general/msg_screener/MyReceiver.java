package com.general.msg_screener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
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
    	
    	// update the text box in the main layout
    	//  by calling the static function in the
    	//   main activity
    	for(SmsMessage msg:sms)
    	{
    		String senderId=msg.getOriginatingAddress();
    		String msgContent=msg.getMessageBody();
    		
    		String storedMsgId=Main.getMsgId();
    		String storedMsgKeys=Main.getMsgKeys();
    		
    		if((storedMsgId!=null || storedMsgKeys!=null))
    		{
    		if(msgContent.contains(storedMsgKeys) || senderId.equalsIgnoreCase(storedMsgId) )
    		{
    			/*
    			try {
					saveMsg(context,senderId+"\n"+msgContent+"\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
    			
    			abortBroadcast();
    			break;
    		}
    		}
    	}
    	
    	

    	        
    }// end of onReceive method
    
    
    public void saveMsg(Context context, String message) throws IOException 
    {

        FileOutputStream FoutS = null;
        OutputStreamWriter outSW = null;
        	
				FoutS = context.openFileOutput("/mnt/sdcard/my-file.txt", Context.MODE_PRIVATE);
			
				outSW = new OutputStreamWriter(FoutS);
	    
				outSW.write(message);
			
		        outSW.flush();
        
    }
    
}
