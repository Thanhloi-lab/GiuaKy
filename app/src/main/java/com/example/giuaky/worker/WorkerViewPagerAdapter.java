package com.example.giuaky.worker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.giuaky.fragment.ProductFragment;
import com.example.giuaky.fragment.StatisticFragment;
import com.example.giuaky.fragment.TimeKeepingFragment;
import com.example.giuaky.fragment.WorkerFragment;

public class WorkerViewPagerAdapter extends FragmentStatePagerAdapter {

    public WorkerViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ListWorkerFragment();
            case 1:
                return new UpdateWorkerFragment();
        }
        return new ListWorkerFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:{
                return "Danh sach cong nhan";
            }
            case 1:{
                return "Cap nhat cong nhan";
            }
        }
        return "Danh sach cong nhan";
    }


}
