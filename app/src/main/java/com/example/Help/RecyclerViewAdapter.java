package com.example.Help;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hh.bbs_palm.R;

import java.util.List;

/**
 * Created by hh on 2017/8/3.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.PostViewHolder> implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener{

    private List<PostInfo> posts;
    private Context context;
    private OnItemClickListener mOnItemClickListener = null;
    private OnItemLongClickListener mOnItemLongClickListener = null;

    //事件的位置
    private float mRawX = 0;
    private float mRawY = 0;

    public float getmRawX() {
        return mRawX;
    }

    public float getmRawY() {
        return mRawY;
    }



//    //define interface
//    public interface OnItemClickListener {
//        void onItemClick(View view , int position);
//    }

    public RecyclerViewAdapter(List<PostInfo> posts,Context context) {
        this.posts = posts;
        this.context=context;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mRawX = event.getRawX();
        mRawY = event.getRawY();
        return false;
    }


    //自定义ViewHolder类
    static class PostViewHolder extends RecyclerView.ViewHolder{

        TextView tv_author;
        TextView tv_title;
        TextView tv_content;
        TextView tv_date;

        public PostViewHolder(final View itemView) {
            super(itemView);
            tv_author = (TextView)itemView.findViewById(R.id.cardview_tv_author);
            tv_title = (TextView)itemView.findViewById(R.id.cardview_tv_title);
            tv_content = (TextView)itemView.findViewById(R.id.cardview_tv_content);
            tv_date = (TextView)itemView.findViewById(R.id.cardview_tv_date);
        }
    }
    //
    @Override
    public RecyclerViewAdapter.PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(context).inflate(R.layout.cardview_postac,viewGroup,false);
        PostViewHolder nvh=new PostViewHolder(v);
        //将创建的view注册监听事件
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        v.setOnTouchListener(this);
        return nvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.PostViewHolder personViewHolder, int i) {
        final int j=i;

        personViewHolder.tv_author.setText(posts.get(i).getAuthor());
        personViewHolder.tv_title.setText(posts.get(i).getTitle());
        personViewHolder.tv_content.setText(posts.get(i).getContent());
        personViewHolder.tv_date.setText(posts.get(i).getDate());

        // //将position保存在itemView的Tag中，以便点击时进行获取
        personViewHolder.itemView.setTag(i);

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    //点击监听事件
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    //长按事件
    @Override
    public boolean onLongClick(View v) {
        if (mOnItemLongClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemLongClickListener.onItemLongClick(v,(int)v.getTag());
        }
        return true;
    }

    //暴露监听接口
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }
}