package com.example.giuaky.time_keeping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuaky.Constant;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;

public class TimeKeepingDetailActivity extends AppCompatActivity {
    TimeKeepingViewModel timeKeeping;
    Bundle bundle;
    TextView tvFactory, tvDate, tvWorker;


    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_worker);

        setControl();
        timeKeeping = (TimeKeepingViewModel) getIntent().getSerializableExtra(Constant.TIME_KEEPING);
        initPage();

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
