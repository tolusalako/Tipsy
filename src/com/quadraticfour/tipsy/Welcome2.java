package com.quadraticfour.tipsy;

import java.util.ArrayList;
import java.util.Collections;
import android.util.Pair;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


public class Welcome2 extends Activity {
	Pair <String,String> contact;
	ArrayList<Pair> contacts = new ArrayList<Pair>();
	ArrayList<String> contact_added = new ArrayList<String>();
	ArrayList<String> sorted_names = new ArrayList<String>();
	ArrayList<String> unsorted_names = new ArrayList<String>();
	SharedPreferences settings;
	Editor editor;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_setup);
        settings = this.getSharedPreferences("data", 0);
        ContactPopulate();
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


    public void ContactPopulate() {
    	Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
    	while (phones.moveToNext())
    	{
    	  String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
    	  String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
    	  contact = new Pair<String,String>(name, phoneNumber);
    	  contacts.add(contact);
    	}
    	
    	phones.close();
    	
    	
    	for (int i = 0; i < contacts.size() ; i++){
    		sorted_names.add((String) contacts.get(i).first);
    		unsorted_names.add((String) contacts.get(i).first);
    	}
    	Collections.sort(sorted_names);
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sorted_names);
    	Spinner spinny = (Spinner) findViewById(R.id.spinner1);
    	spinny.setAdapter(adapter);
    }
    
    public void Add_Contact(View v){
    	LinearLayout base = (LinearLayout) findViewById(R.id.contact_base);
    	if (base.getChildCount() == 5){
    		Spinner spinny = (Spinner) findViewById(R.id.spinner1);
        	spinny.setVisibility(View.GONE);
        	v.setVisibility(View.GONE);
        	
        	EditText msg = (EditText) findViewById(R.id.editText2);
        		msg.setEnabled(true);
        	Button cont = (Button) findViewById(R.id.button1);
        		cont.setEnabled(true);
    	}else{
    	Spinner spinny = (Spinner) findViewById(R.id.spinner1);
    	String name = (String) spinny.getSelectedItem();
    	contact_added.add(name);
    	TextView tv = new TextView(this);
    	tv.setText(name);
    	tv.setTextAppearance(this, android.R.style.TextAppearance_Large);
    	base.addView(tv);
    	}
    }
    
    public void cont(View v){
    	editor = settings.edit();
    	EditText name = (EditText) findViewById(R.id.editText1);
		editor.putString("User_Name", name.getText().toString());
    	
    	for (int i = 0; i < contact_added.size() ; i++){
    		int j = unsorted_names.indexOf(contact_added.get(i));
    		
    		
    		
    		editor.putString("Contact"+i, (String) contacts.get(j).first);
    		editor.putString("Number"+i, (String) contacts.get(j).second);
    		editor.commit();
    	}
    	EditText message = (EditText) findViewById(R.id.editText2);
    	editor.putString("Message", message.getText().toString());
    	editor.putBoolean("Contacts Added", true);
		editor.putBoolean("First Launch", false);
		editor.commit();
		
	//Restart Application
	Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	startActivity(i);
    }
   

    }
   
