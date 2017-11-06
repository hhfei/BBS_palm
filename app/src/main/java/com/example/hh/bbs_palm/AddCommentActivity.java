package com.example.hh.bbs_palm;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Help.NetUtils;
import com.example.util.Tools;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class AddCommentActivity extends AppCompatActivity {


	private int post_id;
	private Button but_sure = null;
	private EditText et_content = null;

	private String strContent = null;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_comment_activity);
		but_sure = (Button) findViewById(R.id.but_addCom_sure);
		et_content = (EditText) findViewById(R.id.et_addCom);

		Intent intent = getIntent();
		post_id = intent.getIntExtra("post_id", 0);
//		if(ss != null){
//			post_id  = Integer.parseInt(ss);
//		}else{
//			post_id = 0;
//		}
//		

		but_sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				strContent = et_content.getText().toString();
				if (!strContent.equals("")) {
					new MyTask().execute();
				} else {
					Toast.makeText(AddCommentActivity.this, "请输入你要发表的说说˵", Toast.LENGTH_SHORT).show();
				}
			}

		});
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	public Action getIndexApiAction() {
		Thing object = new Thing.Builder()
				.setName("AddComment Page") // TODO: Define a title for the content shown.
				// TODO: Make sure this auto-generated URL is correct.
				.setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
				.build();
		return new Action.Builder(Action.TYPE_VIEW)
				.setObject(object)
				.setActionStatus(Action.STATUS_TYPE_COMPLETED)
				.build();
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		AppIndex.AppIndexApi.start(client, getIndexApiAction());
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		AppIndex.AppIndexApi.end(client, getIndexApiAction());
		client.disconnect();
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
					String isAdd = json.getString("isAddComment");
					if (isAdd.equals("yes")) {
						Toast.makeText(AddCommentActivity.this, "发表成功！", Toast.LENGTH_SHORT).show();
						setResult(1001);
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

		// 这里是服务器的IP
		String address = "http://"+Tools.myIp+"/BBS_Server/as";
// // 封装JSON对象
		JSONObject jsonObj = new JSONObject();
		String info = null;
		try {
			String t_user = LoginActivity.pref.getString("nowUser", "0000");
			jsonObj.put("Action", "AddComment");
			jsonObj.put("post_id", post_id);//
			jsonObj.put("author_name", t_user);
			jsonObj.put("content", strContent);
			jsonObj.put("date", Tools.getNowTime());
			//

			info = NetUtils.post(address, jsonObj.toString());

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return info;

	}


}
