package com.general.msg_screener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScreenedMsgs extends Activity 
{
	TextView scrMsgs;
	Button backButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screened_msgs);
        
        setupViews();
        
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
}
