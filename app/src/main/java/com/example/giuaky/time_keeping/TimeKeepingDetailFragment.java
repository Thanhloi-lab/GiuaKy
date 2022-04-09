package com.example.giuaky.time_keeping;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.giuaky.Constant;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeKeepingDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeKeepingDetailFragment extends Fragment {

    TimeKeepingViewModel timeKeeping;
    Bundle bundle;
    TextView tvFactory, tvDate, tvWorker;

    public TimeKeepingDetailFragment() {
        // Required empty public constructor
    }
    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_time_keeping_detail, container, false);
        bundle = this.getArguments();
        timeKeeping = (TimeKeepingViewModel) bundle.getSerializable(Constant.TIME_KEEPING);
        setControl();
        initPage();

        return view;
    }
    private void setControl()
    {
        tvDate = view.findViewById(R.id.tvDatetdtk);
        tvFactory = view.findViewById(R.id.tvFactoroytdtk);
        tvWorker = view.findViewById(R.id.tvWorkertdtk);
    }
    private void initPage()
    {
        tvDate.setText(timeKeeping.getDate());
        tvWorker.setText(timeKeeping.getWorker_name());
        tvFactory.setText(timeKeeping.getFactory_id());
    }
}