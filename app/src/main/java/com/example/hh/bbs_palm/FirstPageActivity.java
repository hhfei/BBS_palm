package com.example.hh.bbs_palm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class FirstPageActivity extends AppCompatActivity implements Runnable{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 无标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.firstpage_activity);

		//   运行多线程
	     new Thread(FirstPageActivity.this).start();
	}


	//监控Activity窗口是否加载完毕
		@Override
	    public void onWindowFocusChanged(boolean hasFocus){
	    	super.onWindowFocusChanged(hasFocus);
	    }

	//完善生命周期
		public  void onResume(){
			super.onResume();
		}
		
		public void onStart(){
			super.onStart();
		}
		
		public void onPause(){
			super.onPause();
		}
		
		public void onRestart(){
			super.onRestart();
		}
		
		public void onStop(){
			super.onRestart();
		}
		
		public void onDestroy(){
			super.onDestroy();
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Thread.sleep(3000);
			    FirstPageActivity.this.finish();

			    Intent intent=new Intent();
			    intent.setClass(FirstPageActivity.this, LoginActivity.class);
			    startActivity(intent);
			
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}

}
