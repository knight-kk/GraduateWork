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
import cn.edu.abc.graduatework.entity.Topic;

public class HotTopicAdapter extends BaseAdapter<HotTopicAdapter.MyViewHolder, Topic> {


    public HotTopicAdapter(Context context, ArrayList<Topic> list) {
        super(context, list);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hot_topic, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindData(@NonNull MyViewHolder viewHolder, int position) {
        Topic topic = mList.get(position);
        GlideApp.with(mContext).load(topic.getImgUrl())
                .placeholder(R.drawable.default_img)
                .error(R.drawable.error_img)
                .fitCenter()
                .into(viewHolder.mImageView);

        if (topic.getTitle() != null) {
            viewHolder.mTvTitle.setText(topic.getTitle());
        }


    }

    class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.imageView)
        ImageView mImageView;
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
