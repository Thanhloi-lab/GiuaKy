package com.example.giuaky.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.giuaky.R;
import com.example.giuaky.widget.CustomViewPager;
import com.example.giuaky.worker.ListWorkerFragment;
import com.example.giuaky.worker.WorkerViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class WorkerFragment extends Fragment {


    public WorkerFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker, container, false);
    }
}