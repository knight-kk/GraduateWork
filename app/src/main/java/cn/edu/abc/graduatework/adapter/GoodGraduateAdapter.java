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
import cn.edu.abc.graduatework.entity.Graduate;

public class GoodGraduateAdapter extends BaseAdapter<GoodGraduateAdapter.MyViewHolder, Graduate> {


    public GoodGraduateAdapter(Context context, ArrayList<Graduate> list) {
        super(context, list);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_good_graduate, viewGroup, false));
        return viewHolder;
    }


    @Override
    protected void onBindData(@NonNull MyViewHolder viewHolder,int position) {
        Graduate graduate = mList.get(position);
        GlideApp.with(mContext).load(graduate.getImgUrl())
                .placeholder(R.drawable.default_img)
                .error(R.drawable.error_img)
                .fitCenter()
                .into(viewHolder.mImageView);

        if (graduate.getUserName() != null) {
            viewHolder.mTvTitle.setText(graduate.getUserName());
        }
        if(graduate.getChoolName()!=null){
            viewHolder.mTvContent.setText(graduate.getChoolName());
        }


    }

    public class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.imageView)
        public ImageView mImageView;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
