package com.yang.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.yang.myapplication.databinding.ActivitySecBinding;
import com.yang.myapplication.event.dispatch.MyFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SecActivity extends BaseActivity<ActivitySecBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sec;
    }

    @Override
    protected void init() {
        List<Fragment> mFragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mFragments.add(MyFragment.newInstance(String.format(Locale.CHINA, "第%1$s个fragment", i)));
        }
        mViewBinding.viewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), getLifecycle(), mFragments));
    }

    static class MyPagerAdapter extends FragmentStateAdapter {
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragments.size();
        }

        private List<Fragment> mFragments;

        public MyPagerAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle, List<Fragment> fragments) {
            super(fm, lifecycle);
            mFragments = fragments;
        }
    }
}
