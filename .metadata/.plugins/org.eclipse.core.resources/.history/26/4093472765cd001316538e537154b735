package com.chuttapay.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class TransactionActivity extends ActionBarActivity {
	private String user_id = null;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SharedPreferences settings = getApplicationContext().getSharedPreferences("user_id", 0);
        user_id = settings.getString("user_id", null);
        
		if (savedInstanceState == null) {
			if(user_id == null){
				getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new RegistrationFragment())
                    .commit();
			}
			else{
				getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new PaymentFragment())
                .commit();
			}
        
		}
    }
}
