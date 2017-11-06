package com.example.hh.bbs_palm;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.Help.NetUtils;
import com.example.Help.PopupList;
import com.example.Help.PostInfo;

import com.example.Help.RecyclerViewAdapter;
import com.example.util.Tools;

public class PostActivity extends AppCompatActivity {

	private int college_id;

//	private ListView listView = null;
	private RecyclerView recyclerView;
	private List<PostInfo> postList;
	private RecyclerViewAdapter recyclerViewAdapter;
	LinearLayoutManager linearLayoutManager;

//	private String[] titles = null;
//	private String[] authors = null;
//	private String[] dates = null;
//	private int[] commentNumbers = null;
//	private int[] postIds = null;
	private int clickPostId;

	private JSONObject json = null;// 服务器端返回的所有帖子

	private List<String> popupMenuItemList = new ArrayList<>();



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_activity);
		Intent intent = getIntent();
		college_id = intent.getIntExtra("position", 0);

		recyclerView = (RecyclerView) findViewById(R.id.rv_psotAc);
		linearLayoutManager=new LinearLayoutManager(this);


		new MyTask().execute();

		//删除 弹出框
		popupMenuItemList.add("删除");

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 2000 && resultCode == 2001) {
			new MyTask().execute();
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.postactivity, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_addPost:
				addPost();
				return true;
			case R.id.menu_refresh:
				refresh();
				return true;

		}
		return super.onOptionsItemSelected(item);
	}

	private void addPost() {
		Intent intent = new Intent(PostActivity.this, AddPostActivity.class);
		intent.putExtra("college_id", college_id);
		startActivityForResult(intent, 2000);
	}

	//
	private void refresh() {
		new MyTask().execute();
	}



	private String connServerForResult() {

		// 这里是服务器的IP
		String address = "http://"+ Tools.myIp+"/BBS_Server/aps";

		// 封装JSON对象
		JSONObject jsonObj = new JSONObject();
		String info = null;
		try {
			jsonObj.put("college_id", college_id);
			info =  NetUtils.post(address, jsonObj.toString());

		} catch (JSONException e) {
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
			String rs = connServerForResult();
			return rs;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				try {
					json = new JSONObject(result);
					int count = Integer.parseInt(json.getString("count"));

					postList = new ArrayList<>();
					String author, title, content, date;
					int postid;
					JSONObject t = null;
					for (int i = 0; i < count; i++) {
						t = json.getJSONObject("json" + i);
						title = t.getString("title");
						author = t.getString("author_name");
						date = t.getString("date");
						content = t.getString("content");
						postid = t.getInt("post_id");
						//截取日期
						date = date.substring(0,16);
						postList.add(new PostInfo(author, title,content,date,postid));
					}
					//为recyclerView设置适配器
					recyclerViewAdapter = new RecyclerViewAdapter(postList, PostActivity.this);

					//设置item点击事件
					recyclerViewAdapter.setOnItemClickListener(new com.example.Help.OnItemClickListener() {
						@Override
						public void onItemClick(View view, int position) {
							Intent intent = new Intent(PostActivity.this,
									CommentActivity.class);
							try {
								String str = json.getJSONObject("json" + position).toString();
								intent.putExtra("infor", str);
								startActivity(intent);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});



					//设置长按事件
					recyclerViewAdapter.setOnItemLongClickListener(new com.example.Help.OnItemLongClickListener() {
						@Override
						public void onItemLongClick(View view, int position) {

							String t_user = LoginActivity.pref.getString("nowUser", "0000");
							String t_author = postList.get(position).getAuthor();
							clickPostId = postList.get(position).getPostId();
							if(t_user.equals(t_author)){
								//弹出删除框
								PopupList popupList = new PopupList(view.getContext());
								popupList.showPopupListWindow(view, position, recyclerViewAdapter.getmRawX(), recyclerViewAdapter.getmRawY(), popupMenuItemList, new PopupList.PopupListListener() {
									@Override
									public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
										return true;
									}
									@Override
									public void onPopupListClick(View contextView, int contextPosition, int position) {
										//执行删除操作
										new DeleteTask(clickPostId).execute();
									}
								});
							}

						}
					});

					recyclerView.setHasFixedSize(true);
					recyclerView.setLayoutManager(linearLayoutManager);
					recyclerView.setAdapter(recyclerViewAdapter);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
	}




	// 删除
	private class DeleteTask extends AsyncTask<String, Void, String> {

		int post_id;

		DeleteTask(int post_id) {
			this.post_id = post_id;
		}

		@Override
		protected String doInBackground(String... params) {
			//
			String rs = DeleteconnServerForResult(post_id);
			return rs;

		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				try {
					JSONObject json = new JSONObject(result);
					String isAdd = json.getString("isDeletePost");
					if (isAdd.equals("yes")) {
						Toast.makeText(PostActivity.this, "删除成功！",
								Toast.LENGTH_SHORT).show();
						refresh();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//
	private String DeleteconnServerForResult(int post_id) {

		String address = "http://"+Tools.myIp+"/BBS_Server/as";

		// 封装JSON对象
		JSONObject jsonObj = new JSONObject();
		String info = null;
		try {
//			String t_user = LoginActivity.pref.getString("nowUser", "0000");
			jsonObj.put("Action", "DeletePost");
			jsonObj.put("post_id", post_id);
			info = NetUtils.post(address, jsonObj.toString());

		}  catch (JSONException e) {
			e.printStackTrace();
		}
		return info;

	}

}
