package com.example.giuaky.statistic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giuaky.Database.StatisticDatabase;
import com.example.giuaky.R;

import java.util.ArrayList;

public class WorkerTimekeepingActivity extends AppCompatActivity {
    private Button backButton;
    private ListView workerTimeKeepingListView;
    private TextView tenCongNhanTextView;
    private ArrayList<WorkerTimeKeeping> data;
    private WorkerTimeKeepingAdapter adapter;
    private StatisticDatabase statisticDatabase;
    private int maCongNhan;
    private String tenCongNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_worker_timekeeping);
        maCongNhan = getIntent().getIntExtra("maCongNhan",-1);
        tenCongNhan = getIntent().getStringExtra("tenCongNhan");
        initData();
        setControl();
        setEvent();
    }


    private void setControl(){
        workerTimeKeepingListView = findViewById(R.id.worker_timekeeping_list_view);
        tenCongNhanTextView = findViewById(R.id.tenCongNhanTextView);
        backButton = findViewById(R.id.backButton);
    }

    private void setEvent(){
        adapter = new WorkerTimeKeepingAdapter(this,R.layout.statistic_time_keeping_info_item, data, maCongNhan, tenCongNhan);
        workerTimeKeepingListView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tenCongNhanTextView.setText(tenCongNhan);
    }

    private void initData(){
        data = new ArrayList<>();
        statisticDatabase = new StatisticDatabase(this);
        data.addAll(statisticDatabase.getWorkerTimeKeeping(maCongNhan));
    }
}