package com.example.hh.bbs_palm;


import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.util.Tools;
import com.finddreams.adbanner.ImagePagerAdapter;
import com.finddreams.bannerview.CircleFlowIndicator;
import com.finddreams.bannerview.ViewFlow;

public class MainActivity extends AppCompatActivity {

    private GridView gv_main;

    private ViewFlow mViewFlow;
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<Integer> imageID = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gv_main = (GridView) findViewById(R.id.gv_main);
        initView();
        imageID.add(R.mipmap.p1);
        imageID.add(R.mipmap.p2);
        imageID.add(R.mipmap.p3);
        imageID.add(R.mipmap.p4);
        imageID.add(R.mipmap.p5);
        imageID.add(R.mipmap.p6);

        initBanner(imageID);

//		GridViewAdapter adapter = new GridViewAdapter(MainActivity.this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.gridview_item,Tools.allColleges);
        gv_main.setAdapter(adapter);
        gv_main.setOnItemClickListener(new MainOnItemClickListener());


    }



    private class MainOnItemClickListener implements OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            Intent intent = new Intent(MainActivity.this, PostActivity.class);
            intent.putExtra("position", arg2);
            startActivity(intent);
        }

    }


    private void initView() {
        mViewFlow = (ViewFlow) findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) findViewById(R.id.viewflowindic);

    }

    private void initBanner(ArrayList<Integer> imageUrlList) {

        mViewFlow.setAdapter(new ImagePagerAdapter(this, imageID).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放
    }


}
