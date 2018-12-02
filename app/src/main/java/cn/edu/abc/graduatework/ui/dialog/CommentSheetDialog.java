package cn.edu.abc.graduatework.ui.dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.BaseAdapter;
import cn.edu.abc.graduatework.adapter.CommentAdapter;
import cn.edu.abc.graduatework.entity.Comment;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.net.RetrofitClient;
import cn.edu.abc.graduatework.util.ScreenUtil;
import cn.edu.abc.graduatework.util.SpUtil;
import cn.edu.abc.graduatework.util.StringUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentSheetDialog extends BottomSheetDialogFragment {


    private BottomSheetBehavior<View> mBehavior;
    private IconTextView tvClose;
    private RecyclerView recyclerView;
    private CardView cardView;
    private EditText commentContent;
    private ImageButton commentReleaseBtn;
    private ArrayList<Comment> mComments;
    private String articleId;
    private CommentAdapter mCommentAdapter;

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_comment, null);
        CardView.LayoutParams layoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT,
                ScreenUtil.getScreenHeightNotStatusBar(getContext()));
        view.setLayoutParams(layoutParams);

        initView(view);
        initData();
        initEvent();
        dialog.setContentView(view);
        FrameLayout frameLayout = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        frameLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(0);
        mBehavior.setSkipCollapsed(true);

        return dialog;
    }

    private void initEvent() {
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        commentReleaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtil.isEmpty(commentContent.getText())) {
                    Toast.makeText(getContext(), "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                Comment comment = new Comment();
                comment.setArticleId(articleId);
                comment.setContent(commentContent.getText().toString());
                comment.setUserId(SpUtil.getUserId(getContext()));
                RetrofitClient.getApiService().addComment(comment).enqueue(new Callback<Result<Comment>>() {
                    @Override
                    public void onResponse(Call<Result<Comment>> call, Response<Result<Comment>> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getCode() == ResultEnum.SUCCESS.getCode()) {
                                mComments.add(0, response.body().getValue());
                                mCommentAdapter.notifyItemInserted(0);
                                Toast.makeText(getContext(), "发布成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "发布失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Result<Comment>> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void initData() {
        mComments = new ArrayList<>();
//        comments.add(new Comment((int)((Math.random()*120))+"","好文",new User("chf","https://upload.jianshu.io/users/upload_avatars/13192523/1b8905e6-3ff0-4e93-bad2-c875dce6e98e.png?imageMogr2/auto-orient/strip|imageView2/1/w/96/h/96")));
//        comments.add(new Comment((int)((Math.random()*120))+"","不错，分享朋友看看",new User("小彤花园","http://owf9dklao.bkt.clouddn.com/Fn3QQ8q-OSNU6KP7Y3r9e4AfEi36")));
//        comments.add(new Comment((int)((Math.random()*120))+"","有意思",new User("阑珊","https://cdn2.jianshu.io/assets/default_avatar/1-04bbeead395d74921af6a4e8214b4f61.jpg?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240")));
//        comments.add(new Comment((int)((Math.random()*120))+"","。。。。。",new User("慕容千语","https://upload.jianshu.io/users/upload_avatars/196894/99323ae8-5924-4730-b73f-9d0d284ff243.png?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240")));
//        comments.add(new Comment((int)((Math.random()*120))+"","好文",new User("chf","http://owf9dklao.bkt.clouddn.com/Fn3QQ8q-OSNU6KP7Y3r9e4AfEi36")));
//
        RetrofitClient.getApiService().getCommentList(articleId).enqueue(new Callback<Result<ArrayList<Comment>>>() {
            @Override
            public void onResponse(Call<Result<ArrayList<Comment>>> call, Response<Result<ArrayList<Comment>>> response) {
                if (response.isSuccessful()) {
                    Result<ArrayList<Comment>> comments = response.body();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    mComments = comments.getValue();
                    if (mComments == null) {
                        mComments = new ArrayList<>();
                    }
                    mCommentAdapter = new CommentAdapter(getContext(), mComments);
                    recyclerView.setAdapter(mCommentAdapter);
                    recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                    mCommentAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
//                startActivity(new Intent(SelectSchoolActivity.this, MainActivity.class));
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Result<ArrayList<Comment>>> call, Throwable t) {

            }
        });


    }

    private void initView(View view) {
        tvClose = view.findViewById(R.id.tv_close);
        recyclerView = view.findViewById(R.id.recyclerView);
        cardView = view.findViewById(R.id.cardView);
        commentContent = view.findViewById(R.id.comment_content);
        commentReleaseBtn = view.findViewById(R.id.comment_release_btn);
    }


    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);//全屏展开
    }
}
