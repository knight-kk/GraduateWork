package cn.edu.abc.graduatework.adapter;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;

    private String[] tabs;

    public void setTabs(String[] tabs) {
        this.tabs = tabs;
    }


    public MyViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] tabs) {
        super(fm);
        mFragments = fragments;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
