package com.longye.forwardmessage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.longye.forwardmessage.com.longye.forwardmessage.email.SimpleMailSender;


public class MainActivity extends AppCompatActivity {

    public static final String FILE_NAME = "settings";
    public static final String KEY_ACCOUNT = "account";
    public static final String KEY_PASSWORD = "pwd";
    public static final String KEY_FILTER = "filter";

    private SharedPreferences mPref;
    private EditText mAccount;
    private EditText mPassword;
    private EditText mFilter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText) findViewById(R.id.password);
        mFilter = (EditText) findViewById(R.id.filter);

        mPref = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        String account = mPref.getString(KEY_ACCOUNT, "");
        mAccount.setText(account);
        String pwd = mPref.getString(KEY_PASSWORD, "");
        mPassword.setText(pwd);
        String filter = mPref.getString(KEY_FILTER, "");
        mFilter.setText(filter);
    }

    public void onClick(View v) {
        String account = mAccount.getText().toString();
        String pwd = mPassword.getText().toString();
        if (v.getId() == R.id.save_setting) {
            String filter = mFilter.getText().toString();
            mPref.edit().putString(KEY_ACCOUNT, account)
                    .putString(KEY_PASSWORD, pwd)
                    .putString(KEY_FILTER, filter)
                    .apply();
        } else if (v.getId() == R.id.send_test) {
            SimpleMailSender.sendMail(account, pwd, "测试主题", "测试数据");
        }
    }
}
