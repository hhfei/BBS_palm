package com.example.hh.bbs_palm;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.Help.NetUtils;
import com.example.util.Tools;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPostActivity extends AppCompatActivity {

	private int college_id;
	private Button but_sure = null;
	private EditText et_content = null;
	private EditText et_title = null;

	private String strContent = null;
	private String strTitle = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_post_activity);
		but_sure = (Button) findViewById(R.id.but_addPost_sure);
		et_content = (EditText) findViewById(R.id.et_addPost_content);
		et_title = (EditText) findViewById(R.id.et_addPost_title);

		Intent intent = getIntent();
		college_id = intent.getIntExtra("college_id",0);

		but_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				strContent = et_content.getText().toString();
				strTitle = et_title.getText().toString();
				if (!strContent.equals("") && !strTitle.equals("")) {
					new MyTask().execute();
				} else if(strTitle.equals("")){
					Toast.makeText(AddPostActivity.this, "请输入标题",
							Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(AddPostActivity.this, "请输入内容",
							Toast.LENGTH_SHORT).show();
				}
			}

		});
	}

	private class MyTask extends AsyncTask<String, Void, String> {

		MyTask() {

		}

		@Override
		protected String doInBackground(String... params) {
			//
			String rs = connServerForResult();
			return rs;

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				try {
					JSONObject json = new JSONObject(result);
					String isAdd = json.getString("isAddPost");
					if (isAdd.equals("yes")) {
						Toast.makeText(AddPostActivity.this, "发表成功！",
								Toast.LENGTH_SHORT).show();
						setResult(2001);
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//
	private String connServerForResult() {

		String address = "http://"+Tools.myIp+"/BBS_Server/as";

		// 封装JSON对象
		JSONObject jsonObj = new JSONObject();
		String info = null;
		try {
			String t_user = LoginActivity.pref.getString("nowUser", "0000");
			jsonObj.put("Action", "AddPost");
			jsonObj.put("title", strTitle);
			jsonObj.put("college_id", college_id);//
			jsonObj.put("author_name", t_user);
			jsonObj.put("content", strContent);
			String time = Tools.getNowTime();
			jsonObj.put("last_update_date", time);
			jsonObj.put("date", time);

			//
			info = NetUtils.post(address,jsonObj.toString());
			Log.e("post------",jsonObj.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return info;

	}

}
