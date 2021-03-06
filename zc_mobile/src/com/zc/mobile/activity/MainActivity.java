package com.zc.mobile.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zc.mobile.R;
import com.zc.mobile.common.Constants;
/**
 * 登录Activity
 * @author admin
 *
 */
public class MainActivity extends Activity implements OnClickListener {
	private EditText txt_userName;
	private EditText txt_passWord;
	private CheckBox ck_remember;
	private Button bt_submit;
	
	private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    
	protected static final int GET_SERVER_RESOPONSE = 1;
	protected static final int ERROR = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bt_submit = (Button) this.findViewById(R.id.btn_submit);
		txt_userName = (EditText) this.findViewById(R.id.txt_userName);
		txt_passWord = (EditText) this.findViewById(R.id.txt_passWord);
		ck_remember = (CheckBox) this.findViewById(R.id.ck_remember);
		pref= PreferenceManager.getDefaultSharedPreferences(this);
//		txt_userName.setText("admin");
//		txt_passWord.setText("888888");
		boolean isRemenber=pref.getBoolean("remember_password",false);
		if(isRemenber){
			 String account=pref.getString("account","");
             String password=pref.getString("password","");
             txt_userName.setText(account);
             txt_passWord.setText(password);
             ck_remember.setChecked(true);
		}
		this.bt_submit.setOnClickListener(this);

	}

	/**
	 */
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_submit:
			submitLoginVolleyJsonObject(view);
			break;

		default:
			break;
		}
	}
	
	/**
	 */
	private void toHomeActivity(){
		HomeActivity.login=1;
		Intent intent=new Intent(this,HomeActivity.class);
		this.startActivity(intent);
		finish();
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}


	
	
	private void submitLoginVolleyJsonObject(View view) {
		 String userName = this.txt_userName.getText().toString().trim();
		final String passWord = this.txt_passWord.getText().toString().trim();
		 String remember = this.ck_remember.getText().toString().trim();
		 String url = Constants.URL + "?userName=" + userName + "&password="
				+ passWord + "&centro=7d6f6f97-a988-45a2-a3de-zc8888centro";
		JSONObject jsonObject=null;
		JsonObjectRequest request=new JsonObjectRequest(url, jsonObject, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				Message msg = Message.obtain();
				msg.what=GET_SERVER_RESOPONSE;
				int ret = response.optInt("ret");
				if(ret==1){
					msg.obj="登录成功!";
					HomeActivity.loginUserInfo.put("id", response.optString("id"));
					HomeActivity.loginUserInfo.put("userName", response.optString("userName"));
					 editor=pref.edit();
					if(ck_remember.isChecked()){
						editor.putBoolean("remember_password",true);
                        editor.putString("account",response.optString("userName"));
                        editor.putString("password",passWord);
					}else{
						editor.clear();
					}
					editor.apply();
					toHomeActivity();
				}else{
					msg.obj="登录失败!";
				}
				handler.sendMessage(msg);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Message msg = Message.obtain();
				msg.what=GET_SERVER_RESOPONSE;
				msg.obj=error.getMessage();
				handler.sendMessage(msg);
			}
		});
		RequestQueue queue=Volley.newRequestQueue(MainActivity.this);
		
		queue.add(request);
		
	}
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GET_SERVER_RESOPONSE:
				Toast.makeText(getApplicationContext(), (String) msg.obj, 0)
						.show();
				break;

			case ERROR:
				Toast.makeText(getApplicationContext(), "请求失败!", 0)
						.show();
				break;
			}
		};
	};
	


}
