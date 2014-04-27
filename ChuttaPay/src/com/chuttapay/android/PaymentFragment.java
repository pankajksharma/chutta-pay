package com.chuttapay.android;



import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import static com.chuttapay.android.Constants.*;

public class PaymentFragment extends Fragment implements OnClickListener
{
	private String from_id, to_id, amount, password = null;
	private EditText edt_to_id, edt_amount, edt_password;
	private Button btn_pay, btn_history, btn_account;
	

	@Override
	public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
	{
		final View rootView = inflater.inflate( R.layout.payment, container, false );
		 SharedPreferences settings = getActivity().getApplicationContext().getSharedPreferences("user_id", 0);
	     from_id = settings.getString("user_id", null);
	       
		edt_to_id = (EditText) rootView.findViewById(R.id.edt_user_id);
		edt_amount = (EditText) rootView.findViewById(R.id.edt_amount);
		edt_password = (EditText) rootView.findViewById(R.id.edt_password);
		
		btn_pay = (Button) rootView.findViewById(R.id.btn_submit);
		btn_account = (Button) rootView.findViewById(R.id.btn_account);
		btn_history = (Button) rootView.findViewById(R.id.btn_transactions);
		btn_account.setOnClickListener(this);
		btn_history.setOnClickListener(this);
		btn_pay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				to_id = edt_to_id.getText().toString();
				amount = edt_amount.getText().toString();
				password = edt_password.getText().toString();
				
				try{
					int amount_ = Integer.parseInt(amount);
				}
				catch(NumberFormatException ex){
					Toast.makeText(getActivity(), "Amount seems invalid.", Toast.LENGTH_LONG).show();
					return;
				}
				
				if(to_id == null || to_id.length() < 4){
					Toast.makeText(getActivity(), "Invalid Chutta ID.", Toast.LENGTH_LONG).show();
				}
				else if(password == null || password.length() < 6){
					Toast.makeText(getActivity(), "Password should be atleast of 6 characters.", Toast.LENGTH_LONG).show();
				}
				else if(amount == null){
					Toast.makeText(getActivity(), "Amount can't be Blank.", Toast.LENGTH_LONG).show();
				}
				else{
					JSONObject params = new JSONObject();
					try {
						params.put("from_user_id", from_id);
						params.put("to_user_id", to_id);
						params.put("amount", amount);
						params.put("passwd", password);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					AsyncRequest request = new AsyncRequest(getActivity(), SERVER_ADDRESS+"api/transaction/send", "POST", params, new OnTaskCompleted() {
						
						@Override
						public void onTaskCompleted(JSONObject object) {
							if(object != null){
								String status = null;
								try {
									JSONObject items = (JSONObject) object.getJSONArray("items").get(0);
									status = items.getString("status");
//									Toast.makeText(getActivity(), status, Toast.LENGTH_LONG).show();
									if(status.equalsIgnoreCase("ok")){
										Toast.makeText(getActivity(), "Sent Chutte Successfully!", Toast.LENGTH_LONG).show();
										getActivity().getSupportFragmentManager().beginTransaction()
							                .add(R.id.container, new PaymentFragment())
							                .commit();
									}
									else{
										Toast.makeText(getActivity(), "Failed to send Chutte.", Toast.LENGTH_LONG).show();
									}
									} catch (JSONException e) {
									e.printStackTrace();
								}
								
							}
							else{
								//send via message
							}
						}
					});
					request.execute(SERVER_ADDRESS+"api/transaction/send");
				}
			}
		});
		return rootView;
	}


	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		if(v.getId() == R.id.btn_account){
			intent.setClass(getActivity(), AccountActivity.class);
		}
		else{
			intent.setClass(getActivity(), TransactionActivity.class);
		}
		startActivity(intent);
	}	
	
}