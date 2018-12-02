package cn.edu.abc.graduatework.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.abc.graduatework.GlideApp;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.entity.Graduate;

public class GraduateAdapter extends BaseAdapter<GraduateAdapter.MyViewHolder, Graduate> {


    public GraduateAdapter(Context context, ArrayList<Graduate> list) {
        super(context, list);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_graduate, viewGroup, false));
    }


    @Override
    protected void onBindData(@NonNull MyViewHolder viewHolder, int position) {
        Graduate graduate = mList.get(position);
        GlideApp.with(mContext).load(graduate.getImgUrl())
                .placeholder(R.drawable.default_img)
                .error(R.drawable.error_img)
                .fitCenter()
                .into(viewHolder.mImgAvatar);
        if (graduate.getUserName() != null) {
            viewHolder.mTvName.setText(graduate.getUserName());
        }
        if (graduate.getMajor() != null) {
            viewHolder.mTvMajor.setText(graduate.getMajor());
        }

        if (graduate.getCompany() != null) {
            viewHolder.mTvCompany.setText(graduate.getCompany());
        }

        if (graduate.getJob() != null) {
            viewHolder.mTvJob.setText(graduate.getJob());
        }

    }

    public class MyViewHolder extends BaseViewHolder {


        @BindView(R.id.img_avatar)
        ImageView mImgAvatar;
        @BindView(R.id.tv_name)
        AppCompatTextView mTvName;
        @BindView(R.id.tv_major)
        AppCompatTextView mTvMajor;
        @BindView(R.id.tv_company)
        AppCompatTextView mTvCompany;
        @BindView(R.id.tv_job)
        AppCompatTextView mTvJob;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
