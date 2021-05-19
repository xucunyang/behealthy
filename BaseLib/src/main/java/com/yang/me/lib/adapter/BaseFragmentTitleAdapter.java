package com.yang.me.lib.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class BaseFragmentTitleAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments;
    private final List<String> mTitles;

    public BaseFragmentTitleAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
        super(fm);
        this.mTitles = titles;
        this.mFragments = fragments;
    }

    public CharSequence getPageTitle(int position) {
        return (position >= mTitles.size()) ? "" : mTitles.get(position);
    }

    public int getCount() {
        return this.mFragments.size();
    }

    public Fragment getItem(int i) {
        return (Fragment) this.mFragments.get(i);
    }
}