package com.bekaim.highping.utils;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SetupViewPagerAdapter extends FragmentPagerAdapter {


    private static final String TAG = "SetupViewPagerAdapter";
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public SetupViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public  Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }



}
