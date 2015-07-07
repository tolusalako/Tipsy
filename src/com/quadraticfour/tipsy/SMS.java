package com.quadraticfour.tipsy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.SmsManager;
import android.util.Log;


public class SMS extends BroadcastReceiver
{
	String MESSAGE = "";
	
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		if (bundle != null)
		{
			Object[] pdus = (Object[])bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for(int i=0; i<msgs.length; i++)
			{
				msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				str += "SMS from "+ msgs[i].getOriginatingAddress();
				str += " :";
				str += msgs[i].getMessageBody().toString();
				str += "\n";
			}	
			}
			MESSAGE = str;		
	}
	
	
	
	public void send(String number, String msg){	
		SmsManager sms = SmsManager.getDefault();
		number = number.replace(" ", "");
		number = number.replace("+", "");
		number = number.replace("(", "");
		number = number.replace(")", "");
		
		sms.sendTextMessage(number, null, msg, null, null);
		}
	public void send(String number, String msg, String msg2){	
	SmsManager sms = SmsManager.getDefault();
	number = number.replace(" ", "");
	number = number.replace("+", "");
	number = number.replace("(", "");
	number = number.replace(")", "");
	
	sms.sendTextMessage(number, null, msg, null, null);
	sms.sendTextMessage(number, null, msg2, null, null);
	}
	
	
	}