package cn.edu.abc.graduatework.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.abc.graduatework.GlideApp;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.entity.Topic;

public class FollowTopicAdapter extends BaseAdapter<BaseViewHolder, Topic> {

    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_FOOTER = 2;

    public FollowTopicAdapter(Context context, ArrayList<Topic> list) {
        super(context, list);
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.hearder_follow_topic, parent, false));
        } else if (viewType == VIEW_TYPE_FOOTER) {
            return new FooterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.footer_follow_topic, parent, false));
        } else {
            return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_follow_topic, parent, false));
        }
    }


    @Override
    protected void onBindData(@NonNull BaseViewHolder viewHolder, int position) {


        if (position == 0) {

        } else if (position > 0 && position == mList.size() + 1) {

        } else {
            if (viewHolder instanceof MyViewHolder) {
                MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
                Topic topic = mList.get(position - 1);
                myViewHolder.mTvTitle.setText(topic.getTitle());
                GlideApp.with(mContext).load(topic.getImgUrl())
                        .placeholder(R.drawable.default_img)
                        .error(R.drawable.error_img)
                        .fitCenter()
                        .into(myViewHolder.mImageView);
                if (topic.getIsNew() == 1) {
                    myViewHolder.mTvIsRead.setVisibility(View.VISIBLE);
                } else {
                    myViewHolder.mTvIsRead.setVisibility(View.INVISIBLE);
                }
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (position > 0 && position == mList.size() + 1) {
            return VIEW_TYPE_FOOTER;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + 2;
    }


    static class HeaderViewHolder extends BaseViewHolder {
        @BindView(R.id.header_title)
        AppCompatTextView mHeaderTitle;

        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class MyViewHolder extends BaseViewHolder {
        @BindView(R.id.imageView)
        AppCompatImageView mImageView;
        @BindView(R.id.tv_is_read)
        IconTextView mTvIsRead;
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class FooterViewHolder extends BaseViewHolder {
        @BindView(R.id.tv_footer)
        IconTextView mTvFooter;

        FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
