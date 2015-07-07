package com.quadraticfour.tipsy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class Main extends Activity {
	SharedPreferences settings;
	Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mainone);
		
		settings = this.getSharedPreferences("data", 0);
		
		//First Launch
		if (settings.getBoolean("First Launch", true) == true){
			RunIntents();
		}else{
			Intent main = new Intent(this, MainPage.class);
			if (main.resolveActivity(getPackageManager()) != null) {
			startActivity(main);
			}
		}
    	
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void RunIntents(){
		
		Intent Welcome = new Intent(this, Welcome1.class);
		if (Welcome.resolveActivity(getPackageManager()) != null) {
		startActivity(Welcome);
		}
		this.finish();
		
		}
	}
