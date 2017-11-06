package com.example.hh.bbs_palm;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.Help.NetUtils;
import com.example.util.Tools;

public class CommentActivity extends AppCompatActivity {

	private int post_id;

	private String[] authors = null;
	private String[] contents = null;
	private String[] dates = null;

	private TextView title = null;
	private TextView content = null;
	private TextView author = null;
	private TextView date = null;

	private ListView listView = null;

	private Button ib_addcom = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_activity);

		title = (TextView) findViewById(R.id.tv_comAc_title);
		content = (TextView) findViewById(R.id.tv_comAc_content);
		author = (TextView) findViewById(R.id.tv_comAc_author);
		date = (TextView) findViewById(R.id.tv_comAc_date);
		listView = (ListView) findViewById(R.id.lv_comAC);
		ib_addcom = (Button)findViewById(R.id.but_addCom);


		Intent intent = getIntent();
		String infor = intent.getStringExtra("infor");
		try {
			JSONObject json = new JSONObject(infor);
			title.setText(json.getString("title"));
			content.setText(json.getString("content"));
			author.setText(json.getString("author_name"));
			date.setText(json.getString("date").substring(0,16));
			post_id = json.getInt("post_id");
			new MyTask().execute();

		} catch (JSONException e) {
			e.printStackTrace();
		}

		ib_addcom.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(CommentActivity.this,AddCommentActivity.class);
				intent.putExtra("post_id", post_id);
//				Log.e("CCCC-post_id",post_id+"");
				startActivityForResult(intent, 1000);
			}

		});



	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==1000 && resultCode == 1001){
			new MyTask().execute();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	// 为lisView写适配器
	private SimpleAdapter createSimpleAdapter() {
		SimpleAdapter sa = null;
		ArrayList<HashMap<String, Object>> arraylist = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < authors.length; i++) {
			HashMap<String, Object> hm = new HashMap<String, Object>();
			hm.put("author", authors[i]);
			hm.put("content", contents[i]);
			hm.put("date", dates[i]);
			arraylist.add(hm);
		}
		sa = new SimpleAdapter(this, arraylist,// 数据来源
				R.layout.lv_item_comment,
				// 动态数组与ImageItem对应的子项
				new String[] { "author", "content", "date" }, new int[] {
				R.id.tv_item_comAc_author, R.id.tv_item_comAc_comment,
				R.id.tv_item_comAc_date });
		return sa;
	}

	private String connServerForResult() {

		// 这里是服务器的IP
		String address = "http://"+ Tools.myIp+"/BBS_Server/acs";

		// 封装JSON对象
		JSONObject jsonObj = new JSONObject();
		String info = null;
		try {
			jsonObj.put("post_id", post_id);

			info = NetUtils.post(address,jsonObj.toString());

		}  catch (JSONException e) {
			e.printStackTrace();
		}
		return info;

	}

	//
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
				// et_user.setText(result);
				try {
					JSONObject json = new JSONObject(result);
					int count = Integer.parseInt(json.getString("count"));

					contents = new String[count];
					authors = new String[count];
					dates = new String[count];

					JSONObject t = null;
					for (int i = 0; i < count; i++) {
						t = json.getJSONObject("json" + i);
						contents[i] = t.getString("content");
						authors[i] = t.getString("author_name");
						dates[i] = t.getString("date").substring(0,16);
					}
					listView.setAdapter(createSimpleAdapter());

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
