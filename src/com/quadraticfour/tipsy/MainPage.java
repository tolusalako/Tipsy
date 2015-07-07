package com.quadraticfour.tipsy;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainPage extends Activity {

	SharedPreferences settings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().setSoftInputMode(
			      WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		settings = getSharedPreferences("data", 0);
		
		try{
		Intent ans_page = getIntent();
		String answers = ans_page.getExtras().getString("Score");
		String[] ans = answers.split("/");
		grading(Double.parseDouble(ans[0])/Double.parseDouble(ans[1]), Double.parseDouble(ans[3])/1000);
		}catch (Exception E){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Do you plan to drink today?").setPositiveButton("Yes", dialogClickListener2)
			    .setNegativeButton("No", dialogClickListener2).show();
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void switchInstance(View v){
		
		
		
	}
	private void grading(double grade, double time ){
		Toast.makeText(this, String.valueOf(grade), Toast.LENGTH_LONG).show();
		Toast.makeText(this, String.valueOf(time), Toast.LENGTH_LONG).show();
		if (grade > .75) {
			
		}else if(grade == .75 && time > 15 ){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Do you need help getting home?").setPositiveButton("Yes", dialogClickListener)
			    .setNegativeButton("No", dialogClickListener).show();
			
			
		}else if(grade < .75){
			
			
		}
		
	}
	public void drankpop() {

		// custom dialog
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.my_dialog);
		dialog.setTitle("Drink Timer");

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText("So we are turning up I see...");
		ImageView image = (ImageView) dialog.findViewById(R.id.image);
		image.setImageResource(R.drawable.logo);
		TimePicker tp = (TimePicker) dialog.findViewById(R.id.timePicker1);
		
		
		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				Intent questions = new Intent(getApplicationContext(), Questions.class);
				startActivity(questions);
			}
		});

		dialog.show();
	  }
	DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	            //Yes, they plan to drink on this wonderful day.
	        	drankpop();
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	        	
	            break;
	        }
	    }
	};
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
	    @Override
	    public void onClick(DialogInterface dialog, int which) {
	        switch (which){
	        case DialogInterface.BUTTON_POSITIVE:
	            //Yes button clicked
	        	TipsySave((View) findViewById(R.id.button1));
	            break;

	        case DialogInterface.BUTTON_NEGATIVE:
	            //No button clicked
	        	
	            break;
	        }
	    }
	};
	
	Pair<String,String> getphone(int i){
		String name = settings.getString("Contact"+i, "");
		String number = settings.getString("Number"+i, "");
		return (new Pair<String,String>(name, number));
	}
	
	public void Log(String msg){
		EditText log = (EditText) findViewById(R.id.log);
		log.setText(log.getText() + "\n" + msg);
	}
	
	public void TipsySave(View v){
		Time now = new Time();
		now.setToNow();
			TextView tv = (TextView) findViewById(R.id.timeView);
			
			final SMS sms = new SMS();
			//Makes the text
		
			Pair<String,String> result = getphone(0);
			String name = result.first; final String number = result.second;
			String msg = "Hi " + name + ", " + settings.getString("Message", "") + "(Tipsy)";
			String msg2 = "This message was sent on behalf of " + settings.getString("User_Name", "") +
			" he/she might not be safe. Please reply using Yes or No if you are willing to pick up this person. (Tipsy)";
			String msg3 = "Here is my current position:\n" + gps() + "\nThank You! (Tipsy)";
			Log.v("phoneNumber",number);
			Log.v("MEssage",msg);
		
					sms.send(number, msg, msg2);
					sms.send(number, msg3);
					Log("Texted " + name + " at " + now.hour + ":" + now.minute);
					Log("If he/she does not reply in 5 minutes (" + now.hour + ":" + ((int) (Integer.valueOf(now.minute) + 5) +"), we will text someone else."));
					tv.setText(now.hour + ":" + ((int) (Integer.valueOf(now.minute) + 5)));				
	}
	


	public String gps(){
		try{
			GPSTracker gps = new GPSTracker(this);
			if(gps.canGetLocation()){ 
				double lat = gps.getLatitude(); // returns latitude
				double lon = gps.getLongitude(); // returns longitude
				String link = String.format("https://maps.google.com/maps?q=loc:%s,%s+(%s)&rlz=1Y1TXLS_enDE543DE543",String.valueOf(lat), String.valueOf(lon), "Your Drunk Friend");
				return link;
			}else{
				gps.showSettingsAlert();  //To turn Gps on
				return "gps not on";
			}
			//gps.stopUsingGPS(); Stop GPS
		
		}catch (Exception E){
			return "Location not found!";
		}
	}
}
