package cn.edu.abc.graduatework.ui.fragment.topic;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.TopicAdapter;
import cn.edu.abc.graduatework.entity.Result;
import cn.edu.abc.graduatework.entity.ResultEnum;
import cn.edu.abc.graduatework.entity.Topic;
import cn.edu.abc.graduatework.net.RetrofitClient;
import cn.edu.abc.graduatework.util.SpUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCreateTopicFragment extends Fragment {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_empty)
    TextView mTvEmpty;
    Unbinder unbinder;

    public MyCreateTopicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_create_topic, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RetrofitClient.getApiService().getMyCreateTop(SpUtil.getUserId(getContext())).enqueue(new Callback<Result<ArrayList<Topic>>>() {
            @Override
            public void onResponse(Call<Result<ArrayList<Topic>>> call, Response<Result<ArrayList<Topic>>> response) {
                if (response.isSuccessful()) {
                    Result<ArrayList<Topic>> result = response.body();
                    if (result.getCode() == ResultEnum.SUCCESS.getCode()) {
                        mTvEmpty.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        ArrayList<Topic> value = result.getValue();
                        mRecyclerView.setAdapter(new TopicAdapter(getContext(), value));
                    }
                }
            }

            @Override
            public void onFailure(Call<Result<ArrayList<Topic>>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
