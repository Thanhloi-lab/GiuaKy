package com.example.giuaky.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new WorkerFragment();
            case 1:
                return new ProductFragment();
            case 2:
                return new TimeKeepingFragment();
            case 3:
                return new StatisticFragment();
        }
        return  new WorkerFragment();
    }

    @Override
    public int getCount() {
        return 4;
    }
}
