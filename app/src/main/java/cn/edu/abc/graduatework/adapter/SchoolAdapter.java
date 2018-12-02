package cn.edu.abc.graduatework.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.edu.abc.graduatework.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.abc.graduatework.entity.School;

public class SchoolAdapter extends BaseAdapter<SchoolAdapter.MyViewHolder, School> {


    public SchoolAdapter(Context context, ArrayList<School> list) {
        super(context, list);
    }

    @Override
    protected void onBindData(@NonNull MyViewHolder viewHolder, int position) {

        School school = mList.get(position);
        viewHolder.mTvName.setText(school.getName());


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_school, viewGroup, false));
    }

    class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_name)
        TextView mTvName;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
