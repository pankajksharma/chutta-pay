package com.chuttapay.android;

import static com.chuttapay.android.Constants.SERVER_ADDRESS;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class AccountActivity extends ActionBarActivity {
	private String user_id = null;
	private TextView tvUserId, tvBalance;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        
        SharedPreferences settings = getApplicationContext().getSharedPreferences("user_id", 0);
        user_id = settings.getString("user_id", null);
        tvUserId = (TextView) findViewById(R.id.txt_user_id);
        tvUserId.setText(user_id);
        
        tvBalance = (TextView) findViewById(R.id.txt_balance);
        
        JSONObject params = new JSONObject();
        try {
			params.put("user_id", user_id);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
        
        AsyncRequest request = new AsyncRequest(this, SERVER_ADDRESS+"api/user/amount", "POST", params, new OnTaskCompleted() {
			
			@Override
			public void onTaskCompleted(JSONObject object) {
				if(object != null){
					String balance = null;
					try {
						JSONObject items = (JSONObject) object.getJSONArray("items").get(0);
						balance = items.getString("balance");				
						tvBalance.setText(balance);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				else{
					//send via message
				}
			}
		});
		request.execute(SERVER_ADDRESS+"api/user/amount");
		
    }
}
