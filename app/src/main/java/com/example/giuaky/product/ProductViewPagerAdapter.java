package com.example.giuaky.product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ProductViewPagerAdapter extends FragmentStatePagerAdapter {

    public ProductViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ListProductFragment();
//            case 1:
//                return new UpdateWorkerFragment();
        }
        return new ListProductFragment();
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
                return "Danh sach san pham";
            }
            case 1:{
                return "Cap nhat san pham";
            }
        }
        return "Danh sach san pham";
    }


}
