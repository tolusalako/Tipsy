package com.quadraticfour.tipsy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;

public class Welcome1 extends Activity {
	SharedPreferences settings;
	Editor editor;
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(
        	      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_welcome);
        settings = this.getSharedPreferences("data", 0);
        
    }
    

    public void button_clicked(View v)
    {
    	CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);
    	

    	editor = settings.edit();
    	if(cb.isChecked() == true)
    	{
    		editor.putBoolean("TOS Accepted", true);
    	}else{
    		editor.putBoolean("TOS Accepted", false);
    		
    	}
    	
    	editor.commit();
    	
    	if(settings.getBoolean("TOS Accepted", false) == false){
			this.finish();
		}else{
		
				
			Intent WelcomeTwo = new Intent(this, Welcome2.class);
			if (WelcomeTwo.resolveActivity(getPackageManager()) != null) {
			startActivity(WelcomeTwo);
			}}
    	this.finish();
    }
	
}
