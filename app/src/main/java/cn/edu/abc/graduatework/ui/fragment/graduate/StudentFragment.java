package cn.edu.abc.graduatework.ui.fragment.graduate;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.BaseAdapter;
import cn.edu.abc.graduatework.adapter.GraduateAdapter;
import cn.edu.abc.graduatework.entity.Graduate;
import cn.edu.abc.graduatework.entity.MessageEvent;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.School;
import cn.edu.abc.graduatework.net.RetrofitClient;
import cn.edu.abc.graduatework.ui.activity.GraduateActivity;
import cn.edu.abc.graduatework.ui.fragment.BaseFragment;
import cn.edu.abc.graduatework.viewmodel.StudentViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentFragment extends BaseFragment {

    private static final String SCHOOL_ID = "school_id";
    @BindView(R.id.rv_graduate)
    RecyclerView mRvGraduate;
    @BindView(R.id.swipeRl)
    SwipeRefreshLayout mSwipeRl;
    Unbinder unbinder;
    private StudentViewModel mViewModel;
    private GraduateAdapter mGraduateAdapter;
    private ArrayList<Graduate> mGraduates;
    private String mSchoolId = "1";



    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            RetrofitClient.getApiService().getGraduateList(mSchoolId).enqueue(new Callback<Result<ArrayList<Graduate>>>() {
                @Override
                public void onResponse(Call<Result<ArrayList<Graduate>>> call, Response<Result<ArrayList<Graduate>>> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getCode() == ResultEnum.SUCCESS.getCode()) {
                            mSwipeRl.setRefreshing(false);
                            updateUI(response.body().getValue());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Result<ArrayList<Graduate>>> call, Throwable t) {

                }
            });
        }
    };


    public static StudentFragment newInstance() {
        return new StudentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent<School> event) {
        if (event != null) {
            mSchoolId = event.getValue().getSchoolId();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        initView();
    }
    @Override
    public void onResume() {
        super.onResume();
        mSwipeRl.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRl.setRefreshing(true);
                mOnRefreshListener.onRefresh();

            }
        });

    }

    private void updateUI(final ArrayList<Graduate> graduates) {

        mGraduateAdapter = new GraduateAdapter(getContext(), graduates);
        mGraduateAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                if (graduates != null) {
                    bundle.putSerializable(GraduateActivity.KEY_GRADUATE, graduates.get(position));
                }
                openActivity(GraduateActivity.class, bundle);
            }
        });
        mRvGraduate.setAdapter(mGraduateAdapter);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void initView() {
        mSwipeRl.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        mSwipeRl.setSlingshotDistance(100);
        mSwipeRl.setOnRefreshListener(mOnRefreshListener);

        mRvGraduate.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvGraduate.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
