package com.example.android.todolist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static java.nio.file.Paths.get;

public class CustomBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action=intent.getAction();
        if(action.equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle b=intent.getExtras();

            

            SmsMessage[] sms=null;
            String msgfrom;
            String msgbody="";
            if(b!=null){
                try {
                    Object[] pdus;
                    if (Build.VERSION.SDK_INT >= 19) { //KITKAT
                        SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);

                        for(int i=0;i<msgs.length;i++){
                            msgbody=msgbody+msgs[i].getMessageBody();
                        }
                    } else {
                        pdus = (Object[]) b.get("pdus");

                        sms=new SmsMessage[pdus.length];
                        for(int i=0;i<pdus.length;i++){
                            sms[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                            msgbody=msgbody+sms[i].getMessageBody();
                        }
                    }


                    Toast.makeText(context,msgbody, Toast.LENGTH_SHORT).show();
                    Log.d("Message","yes");
                }
                catch (Exception e){

                }

            }
        }
    }
}
