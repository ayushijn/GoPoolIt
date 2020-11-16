package com.example.gopoolit.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gopoolit.Fragments.TabOneFragment;
import com.example.gopoolit.Fragments.TabTwoFragment;

public class TabAdapter extends FragmentPagerAdapter {
    Context ct;
    int noOfTabs;

    public TabAdapter(Context ct, FragmentManager fm, int noOfTabs) {
        super(fm);
        this.ct = ct;
        this.noOfTabs = noOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            TabOneFragment tabOneFragment = new TabOneFragment();
            return tabOneFragment;
        } else {
            TabTwoFragment tabTwoFragment = new TabTwoFragment();
            return tabTwoFragment;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
