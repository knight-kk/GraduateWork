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


public class TopicAdapter extends BaseAdapter<TopicAdapter.MyViewHolder, Topic> {


    public TopicAdapter(Context context, ArrayList<Topic> list) {
        super(context, list);
    }

    private boolean isGrid;

    public void setGrid(boolean grid) {
        isGrid = grid;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        if (isGrid) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_topic_grid, parent, false);
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_topic, parent, false);
        }


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

        viewHolder.mTvTitle.setText(topic.getTitle());
        viewHolder.mTvContent.setText(topic.getContent());
        viewHolder.mTvCount.setText("人数"+(int)(Math.random()*20+3)+"");

    }

    class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.imageView)
        ImageView mImageView;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_count)
        TextView mTvCount;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
