package cn.edu.abc.graduatework.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ShareCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.net.RetrofitClient;
import cn.edu.abc.graduatework.ui.dialog.CommentSheetDialog;
import cn.edu.abc.graduatework.util.SpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewInfoActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.swipeRl)
    SwipeRefreshLayout mSwipeRl;
    @BindView(R.id.tv_comment)
    TextView mTvComment;
    @BindView(R.id.tv_collection)
    TextView mTvCollection;
    @BindView(R.id.tv_share)
    TextView mTvShare;

    private WebSettings mWebViewSettings;
    public static final String TEXT_HTML = "text_html";
    public static final String ARTICLE_ID = "article_id";
    public static final String IS_COLLECTION = "is_collection";
    private static final String MY_STYLE_CSS = "file:///android_asset/my_style.css";
    private String mDataHtml = "";
    private String mArticleId;
    private CommentSheetDialog mCommentSheetDialog;
    private String mUrl = "https://mp.weixin.qq.com/s?__biz=MzA5MzI3NjE2MA==&mid=2650244106&idx=1&sn=1b7d69e63fb42b7cb5b7f3ebcab844c9&chksm=88637565bf14fc73587691803380bf236b5ddf0df0e8e0f16e79c1720782cbd1011f8198c9ac&scene=38#wechat_redirect";

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener= new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mWebView.loadUrl(mDataHtml);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            mDataHtml = getIntent().getStringExtra(TEXT_HTML);
            mArticleId = getIntent().getStringExtra(ARTICLE_ID);
            if (getIntent().getIntExtra(IS_COLLECTION, 0) == 0) {
                is_collection = false;
            } else {
                is_collection = true;
            }
        }

        initView();
        settingWebView();
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mSwipeRl.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSwipeRl.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRl.setRefreshing(true);
                mOnRefreshListener.onRefresh();
            }
        });
    }


    private void settingWebView() {
        mWebView.setBackgroundColor(Color.WHITE);
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSwipeRl.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        mSwipeRl.setSlingshotDistance(100);

        mSwipeRl.setOnRefreshListener(mOnRefreshListener);
        mSwipeRl.setRefreshing(true);
    }


    boolean is_collection = false;

    @OnClick({R.id.tv_comment, R.id.tv_collection, R.id.tv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_comment:
                if (mCommentSheetDialog == null) {
                    mCommentSheetDialog = new CommentSheetDialog();
                    mCommentSheetDialog.setArticleId(mArticleId);
                }
                mCommentSheetDialog.show(getSupportFragmentManager(), "dialog");
                break;
            case R.id.tv_collection:
                RetrofitClient.getApiService().articleCollection(mArticleId, SpUtil.getUserId(this)).enqueue(new Callback<Result<String>>() {
                    @Override
                    public void onResponse(Call<Result<String>> call, Response<Result<String>> response) {
                        if (response.isSuccessful()) {
                            Result<String> result = response.body();
                            if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                                if (!is_collection) {
                                    mTvCollection.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_star_red_24dp), null, null);
                                    mTvCollection.setTextColor(Color.parseColor("#d81e06"));
                                    is_collection = true;
                                    Toast.makeText(NewInfoActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    mTvCollection.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.ic_star_black_24dp), null, null);
                                    mTvCollection.setTextColor(getResources().getColor(R.color.text_color));
                                    is_collection = false;
                                    Toast.makeText(NewInfoActivity.this, "取消收藏", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(NewInfoActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Result<String>> call, Throwable t) {
                        Toast.makeText(NewInfoActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });


                break;
            case R.id.tv_share:
                Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                        .setText("我在高校毕业生阅读到一篇很不错的文章，快来看看吧！！！")
                        .setType("text/plain")
                        .createChooserIntent();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                } else {
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                }
                startActivity(shareIntent);
                break;

        }
    }
}
