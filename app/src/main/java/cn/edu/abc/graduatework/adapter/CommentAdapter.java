package cn.edu.abc.graduatework.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.abc.graduatework.GlideApp;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.entity.Comment;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends BaseAdapter<CommentAdapter.MyViewHolder, Comment> {

    public CommentAdapter(Context context, ArrayList<Comment> list) {
        super(context, list);
    }

    @Override
    protected void onBindData(@NonNull MyViewHolder viewHolder, int position) {
        final Comment comment = mList.get(position);
        GlideApp.with(mContext).load(comment.getUserImg())
                .placeholder(R.drawable.default_img)
                .error(R.drawable.error_img)
                .fitCenter()
                .into(viewHolder.mCircleImageView);
        viewHolder.mTvUserName.setText(comment.getUserName());
        viewHolder.mTvContent.setText(comment.getContent());
        viewHolder.mCkGood.setText("("+comment.getGoodNumber()+")");
        viewHolder.mCkGood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    comment.setGoodNumber("1");
                }else{
                    comment.setGoodNumber("0");
                }
                notifyDataSetChanged();
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_comment, viewGroup, false));
    }

    class MyViewHolder extends BaseViewHolder {
        @BindView(R.id.circleImageView)
        CircleImageView mCircleImageView;
        @BindView(R.id.tv_user_name)
        AppCompatTextView mTvUserName;
        @BindView(R.id.tv_content)
        AppCompatTextView mTvContent;
        @BindView(R.id.tv_date)
        AppCompatTextView mTvDate;
        @BindView(R.id.tv_reply)
        AppCompatTextView mTvReply;
        @BindView(R.id.ck_good)
        CheckBox mCkGood;
        @BindView(R.id.img_reply)
        ImageView mImgReply;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }


}
