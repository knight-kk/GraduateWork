package cn.edu.abc.graduatework.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.abc.graduatework.GlideApp;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.entity.News;

public class NewsAdapter extends BaseAdapter<NewsAdapter.MyViewHolder, News> {


    public NewsAdapter(Context context, ArrayList<News> list) {
        super(context, list);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_news, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    protected void onBindData(@NonNull MyViewHolder viewHolder, int position) {
        News news = mList.get(position);
//
        GlideApp.with(mContext).load(news.getImgUrl())
                .placeholder(R.drawable.default_img)
                .error(R.drawable.error_img)
                .fitCenter()
                .into(viewHolder.mImageView);

        viewHolder.mTvTitle.setText(news.getTitle());
        viewHolder.mTvCount.setText("评论:"+news.getCommentCount());
        viewHolder.mTvTime.setText(news.getCreateTime().split("T")[0]);


    }

    class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.imageView)
        ImageView mImageView;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_count)
        TextView mTvCount;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
