package cn.edu.abc.graduatework.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public abstract class BaseAdapter<VH extends BaseViewHolder, T> extends RecyclerView.Adapter<VH> {


    protected Context mContext;

    protected ArrayList<T> mList;

    public BaseAdapter(Context context, ArrayList<T> list) {
        mContext = context;
        mList = list;
    }

    public BaseAdapter() {
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }


    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }


    @Override
    public void onBindViewHolder(@NonNull final VH baseViewHolder, int position) {
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, baseViewHolder.getAdapterPosition());
                }
            }
        });
        baseViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(v, baseViewHolder.getAdapterPosition());
                }
                return true;
            }
        });

        onBindData(baseViewHolder, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected abstract void onBindData(@NonNull VH viewHolder, int position);


}
