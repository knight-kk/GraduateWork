package cn.edu.abc.graduatework.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.abc.graduatework.GlideApp;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.entity.Article;

public class NewPublishAdapter extends BaseAdapter<NewPublishAdapter.MyViewHolder, Article> {


    public NewPublishAdapter(Context context, ArrayList<Article> list) {
        super(context, list);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_article, viewGroup, false));
    }

    @Override
    protected void onBindData(@NonNull MyViewHolder viewHolder, int position) {
        Article article = mList.get(position);
        if (article.getImgUrl() == null || article.getImgUrl().equals("")) {
            viewHolder.mImg.setVisibility(View.GONE);
        } else {
            viewHolder.mImg.setVisibility(View.VISIBLE);
            GlideApp.with(mContext).load(article.getImgUrl())
                    .placeholder(R.drawable.default_img)
                    .error(R.drawable.error_img)
                    .fitCenter()
                    .into(viewHolder.mImg);
        }
        if (article.getTitle() != null) {
            viewHolder.mTvTitle.setText(article.getTitle());
        }
        if (article.getAuthorName() != null) {
            viewHolder.mTvAuthor.setText(article.getAuthorName());
        }
        if (article.getContent() != null) {
            Pattern compile = Pattern.compile("<[^>]*>");
            String content = article.getContent();
            Matcher matcher = compile.matcher(content);
            while (matcher.find()) {
                content=content.replace(matcher.group(), "");
            }

            viewHolder.mTvContent.setText(content );
        }
    }


    class MyViewHolder extends BaseViewHolder {

        @BindView(R.id.tv_title)
        AppCompatTextView mTvTitle;
        @BindView(R.id.tv_content)
        AppCompatTextView mTvContent;
        @BindView(R.id.img)
        ImageView mImg;
        @BindView(R.id.tv_date)
        AppCompatTextView mTvDate;
        @BindView(R.id.tv_author)
        AppCompatTextView mTvAuthor;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
