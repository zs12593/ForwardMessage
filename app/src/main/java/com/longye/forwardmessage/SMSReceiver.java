package com.longye.forwardmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.longye.forwardmessage.com.longye.forwardmessage.email.SimpleMailSender;

public class SMSReceiver extends BroadcastReceiver {

    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] msgs = new SmsMessage[pdus.length];
                SharedPreferences pref = context.getSharedPreferences(MainActivity.FILE_NAME, Context.MODE_PRIVATE);
                String account = pref.getString(MainActivity.KEY_ACCOUNT, "");
                String pwd = pref.getString(MainActivity.KEY_PASSWORD, "");
                String filterStr = pref.getString(MainActivity.KEY_FILTER, "");
                for (int i = 0; i < pdus.length; i++) {
                    SmsMessage msg = SmsMessage.createFromPdu((byte[]) pdus[i]);

                    if (filter(msg.getMessageBody(), filterStr)) {
                        SimpleMailSender.sendMail(account, pwd, "短信",
                                "From: " + msg.getDisplayOriginatingAddress() + "\nTo" + msg.getMessageBody());
                    }
                }
            }
        }
    }

    private boolean filter(String src, String filter) {
        if (src == null) return false;

        if (filter.equals("")) return true;

        String[] fs = filter.split(" ");
        for (String s : fs) {
            if (src.contains(s)) return true;
        }

        return false;
    }
}
