package cn.edu.abc.graduatework.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.edu.abc.graduatework.R;
import cn.edu.abc.graduatework.adapter.BaseAdapter;
import cn.edu.abc.graduatework.adapter.SchoolAdapter;
import cn.edu.abc.graduatework.entity.MessageEvent;
import cn.edu.abc.graduatework.entity.School;
import cn.edu.abc.graduatework.viewmodel.SelectSchoolViewModel;

public class SelectSchoolActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.rv_school)
    RecyclerView mRvSchool;
    @BindView(R.id.swipeRl)
    SwipeRefreshLayout mSwipeRl;
    private SchoolAdapter mSchoolAdapter;
    private SearchView mSearchView;
    private SelectSchoolViewModel mSchoolViewModel;
    private ArrayList<School> mSchools;
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mSchoolViewModel.fetch();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_school);
        ButterKnife.bind(this);
        initView();
        mSchoolViewModel = ViewModelProviders.of(this).get(SelectSchoolViewModel.class);

        mSchoolViewModel.getListMutableLiveData().observe(this, new Observer<ArrayList<School>>() {
            @Override
            public void onChanged(@Nullable ArrayList<School> schools) {
                mSwipeRl.setRefreshing(false);
                mSchools = schools;
                updateUI(schools);
            }
        });

        mSwipeRl.setRefreshing(true);
        mOnRefreshListener.onRefresh();

    }

    private void updateUI(ArrayList<School> schools) {
        mSchoolAdapter = new SchoolAdapter(this, schools);
        mRvSchool.setAdapter(mSchoolAdapter);
        mSchoolAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //startActivity(new Intent(SelectSchoolActivity.this, MainActivity.class));//
                EventBus.getDefault().post(new MessageEvent<School>("", mSchools.get(position)));
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mToolbar.inflateMenu(R.menu.select_school_menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) menuItem.getActionView();
        initSearchView(mSearchView);
        return super.onCreateOptionsMenu(menu);
    }

    private void initSearchView(SearchView searchView) {
        searchView.setQueryHint("学校名称");
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSearchView.isIconified()) {
                    mSearchView.setIconified(true);
                } else {
                    finish();
                }
            }
        });

        mSwipeRl.setColorSchemeResources(R.color.google_blue, R.color.google_green, R.color.google_red, R.color.google_yellow);
        mSwipeRl.setOnRefreshListener(mOnRefreshListener);

        mRvSchool.setLayoutManager(new LinearLayoutManager(this));
        mRvSchool.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }
}
