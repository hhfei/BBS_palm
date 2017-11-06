package com.example.hh.bbs_palm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Help.NetUtils;
import com.example.util.Tools;

public class RegisterActivity extends AppCompatActivity {

	private EditText et_name = null;
	private EditText et_password = null;
	private EditText et_college = null;
	private Button but_register = null;

	private int collegeId = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		et_name = (EditText) findViewById(R.id.et_registerAc_name);
		et_password = (EditText) findViewById(R.id.et_registerAc_password);
		et_college = (EditText) findViewById(R.id.et_registerAc_college);
		but_register = (Button) findViewById(R.id.but_registerAC_zhuce);

		but_register.setOnClickListener(new MyOnClickListener());
		et_college.setOnClickListener(new CollegeOnClickListener());
	}

	// 填写学院的监听事件
	class CollegeOnClickListener implements OnClickListener{

		@Override
		public void onClick(View arg0) {
			actionAlertDialog();
		}

	}

	// 注册按钮的监听事件
	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			String name = et_name.getText().toString();
			String pass = et_password.getText().toString();
			String xueyuan = et_college.getText().toString();
//			int college = 0;


			if (!name.equals("") && !pass.equals("") && !xueyuan.equals("")) {
				new MyTask(name, pass, collegeId).execute();
			} else if (name.equals("")) {
				Toast.makeText(RegisterActivity.this, "请输入用户名",
						Toast.LENGTH_SHORT).show();
			} else if (pass.equals("")) {
				Toast.makeText(RegisterActivity.this, "请输入密码",
						Toast.LENGTH_SHORT).show();
			}else if (xueyuan.equals("")) {
				Toast.makeText(RegisterActivity.this, "请选择学院",
						Toast.LENGTH_SHORT).show();
			}

		}

	}

	private String connServerForResult(String name, String password,
									   int college_id) {
		String address = "http://"+Tools.myIp+"/BBS_Server/rs";
		// 封装JSON对象
		JSONObject jsonObj = new JSONObject();
		String info = null;
		try {
			jsonObj.put("college_id", college_id);
			jsonObj.put("name", name);
			jsonObj.put("password", password);
			jsonObj.put("date", Tools.getNowTime());

			info = NetUtils.post(address, jsonObj.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return info;

	}

	//
	private class MyTask extends AsyncTask<String, Void, String> {
		private String name;
		private String password;
		private int college;

		public MyTask(String name, String password, int college) {
			super();
			this.name = name;
			this.password = password;
			this.college = college;
		}

		@Override
		protected String doInBackground(String... params) {
			String rs = connServerForResult(name, password, college);
			return rs;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				try {
					JSONObject json = new JSONObject(result);
					int response = Integer.parseInt(json.getString("response"));
					if (response == 1) {
						Toast.makeText(RegisterActivity.this, "恭喜你，注册成功！！！",
								Toast.LENGTH_SHORT).show();
						finish();
					}
					if (response == -1) {
						Toast.makeText(RegisterActivity.this, "用户名已存在！",
								Toast.LENGTH_SHORT).show();
					}
					if (response == 0) {
						Toast.makeText(RegisterActivity.this, "注册失败！",
								Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
	}

	//
	protected void actionAlertDialog(){
		AlertDialog.Builder builder;
//        final AlertDialog alertDialog;
		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.alert_dialog, (ViewGroup)findViewById(R.id.layout_alertDialog));
		ListView myListView = (ListView) layout.findViewById(R.id.lv_AlertDialog);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(RegisterActivity.this, R.layout.lv_item_alert_dialog,Tools.allColleges);
		myListView.setAdapter(adapter);
		builder = new AlertDialog.Builder(RegisterActivity.this);
		builder.setView(layout);
		final AlertDialog alertDialog = builder.create();
		alertDialog.show();

		myListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
				et_college.setText(Tools.allColleges[arg2]);
				collegeId = arg2;
				alertDialog.cancel();
			}

		});

	}


}
