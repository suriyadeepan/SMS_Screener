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
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver 
{
	
	// Shared Preferences 
		SharedPreferences sharedPrefObj;
		static String fileName="keys";
		static final String prefKey_Date="100";
		static final String prefKey_Time="101";
		static final String prefKey_Msg="102";
		static final String prefKey_Id="103";
	
	
	
	
	// Logger
	public static final String TAG = "Message_Screener.MyReceiver";
	
    public MyReceiver() 
    {
    	
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
    		
    		
    		// check if the message or senderID contains
    		//  filter keywords/Id's
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
    			
    			// Add info about msg (data,time,msg body,msgId) to Preference file...
    			/******************************************************************/
    			
    			
    			// updating time and date
    			String[] timeOfArrival=splitTimeData();
    			
    			updateDB(sharedPrefObj, prefKey_Date, timeOfArrival[0]);
    			updateDB(sharedPrefObj, prefKey_Time, timeOfArrival[1]);
    			
    			// updating msg content and sender ID
    			updateDB(sharedPrefObj, prefKey_Msg, msgContent);
    			updateDB(sharedPrefObj,prefKey_Id,senderId);
    			
    			
    			Log.i(TAG,"onReceive - Variables: \n"+timeOfArrival[0]+"\n"+timeOfArrival[1]+
    					"\n"+msgContent+"\n"+senderId+"\n");
    		
    			
    			
    			    			
    			
    			/*******************************************************************/
    			
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
    
    
    
    private void updateDB(SharedPreferences sharedPrefObj, String key,
			String dataString){
	
		//Log.i(TAG, "updateDB- entered");
		
		// retreive the data present in the pref file with key 
		//  append it to the current String and update data @
		//   the pref file
		
		String dataAtDb=sharedPrefObj.getString(key, "Unable to retreive keys!");
    	SharedPreferences.Editor editor=sharedPrefObj.edit();
    	
    	editor.putString(key,dataAtDb+dataString+"~");
    	editor.commit();
		
		Log.i(TAG,"updateDB- Variables: \ndataAtDb = "+dataAtDb);
    	
    	
	}// end of updateDB() method...
    
    
    
    
    
    // this method gets Date obj from system and
    //  splits it to extract Date and Time
    //   returns a String Array with (0 - date) (1 - time)
    private String[] splitTimeData()
    {
    	// get Data obj from system
    	Date d=new Date();
    	
    	Log.i(TAG,"splitTimeData - Variables: \n d = "+d.toString());
    	
    	// split it with " " as delimiter
		String[] timeDataArr=d.toString().split(" ");
		
		// modify it to get data @ 0 and time @ 1
		String temp=timeDataArr[0].concat(" "+timeDataArr[1]).concat(" "+timeDataArr[2]).concat(" "+timeDataArr[5]);
		timeDataArr[0]=temp;
		timeDataArr[1]=timeDataArr[3];
		
		// remove elements at other indices
		for(int i=2;i<timeDataArr.length;i++)
		timeDataArr[i]=null;
    	
		return timeDataArr;
	}

    
    
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
