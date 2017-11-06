package com.example.hh.bbs_palm;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.example.Help.NetUtils;
import com.example.Help.RxJavaNetHelper;
import com.example.util.Tools;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

public class LoginActivity extends AppCompatActivity {

	private EditText et_user = null;
	private EditText et_password = null;
	private Button but_login = null;
	private Button but_register = null;

	private String username = null;
	private String password = null;

	public static SharedPreferences pref;
	public static SharedPreferences.Editor editor;

	// Button but_register = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		pref = getSharedPreferences("palmBBSApp", MODE_PRIVATE);
		editor = pref.edit();

		et_user = (EditText) findViewById(R.id.et_user);
		et_password = (EditText) findViewById(R.id.et_password);
		but_login = (Button) findViewById(R.id.but_login);
		but_register = (Button)findViewById(R.id.but_loginAc_register);

		but_login.setOnClickListener(new LoginOnClickListener());
		but_register.setOnClickListener(new RegisterOnClickListener());

	}

	class LoginOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			username = et_user.getText().toString();
			password = et_password.getText().toString();
//			new MyTask().execute();
			executeTask();
		}


	}
	class RegisterOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intent);
		}

	}

//
//	private String connServerForResult() {
//
//		String address = "http://"+ Tools.myIp+"/BBS_Server/as";
//		// 封装JSON对象
//		JSONObject jsonObj = new JSONObject();
//		String info = null;
//		try {
//			jsonObj.put("Action", "Login");
//			jsonObj.put("name", username);
//			jsonObj.put("en_password", password);
//			// 输入url和请求参数调用post方法
//			info = NetUtils.post(address,jsonObj.toString());
//
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		return info;
//	}



	//调用rxjava 请求服务器，获取数据并更新UI
	private void executeTask(){

		Handler hand = new Handler(){};

		String address = "http://"+ Tools.myIp+"/BBS_Server/as";
		// 封装JSON对象
		JSONObject jsonObj = new JSONObject();
		String info = null;
		try {
			jsonObj.put("Action", "Login");
			jsonObj.put("name", username);
			jsonObj.put("en_password", password);
			// 输入url和请求参数调用post方法
			RxJavaNetHelper.doNetworkAndUpdate(address, jsonObj.toString(), new Action1<String>() {

				@Override
				public void call(String s) {
					if (s != null) {
						try {
							JSONObject json1 = new JSONObject(s);
							String isLogin = json1.getString("isLogin");
							if (isLogin != null && isLogin.equals("yes")) {
								editor.putString("nowUser", username);
								editor.commit();

								Intent intent = new Intent(LoginActivity.this,
										MainActivity.class);
								startActivity(intent);
							} else {
//						Toast.makeText(LoginActivity.this, "登录失败！",
//								Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			});

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

//	//
//	private class MyTask extends AsyncTask<String, Void, String> {
//
//		MyTask() {
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			String rs = connServerForResult();
//			return rs;
//		}
//
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//			if (result != null) {
//				// et_user.setText(result);
//				try {
//					JSONObject json1 = new JSONObject(result);
//					String isLogin = json1.getString("isLogin");
//					if (isLogin != null && isLogin.equals("yes")) {
//						editor.putString("nowUser", username);
//						editor.commit();
//
//						Intent intent = new Intent(LoginActivity.this,
//								MainActivity.class);
//						startActivity(intent);
//					} else {
////						Toast.makeText(LoginActivity.this, "登录失败！",
////								Toast.LENGTH_SHORT).show();
//					}
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//
//			}
//		}
//	}

}
