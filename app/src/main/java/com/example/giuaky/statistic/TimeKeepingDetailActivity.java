package com.example.giuaky.statistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giuaky.Database.StatisticDatabase;
import com.example.giuaky.R;

import java.util.ArrayList;

public class TimeKeepingDetailActivity extends AppCompatActivity {
    private Button backButton;
    private ListView workerTimeKeepingListView;
    private TextView tenCongNhanTextView, ngayChamCongTextView;
    private ArrayList<TimeKeepingDetail> data;
    private TimeKeepingDetailAdapter adapter;
    StatisticDatabase statisticDatabase;
    private int maCongNhan;
    private int maChamCong;
    private String tenCongNhan;
    private String ngayChamCong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        maCongNhan = intent.getIntExtra("maCongNhan",-1);
        maChamCong = intent.getIntExtra("maChamCong",-1);
        tenCongNhan = intent.getStringExtra("tenCongNhan");
        ngayChamCong = intent.getStringExtra("ngayChamCong");

        setContentView(R.layout.activity_time_keeping_detail);
        initData();
        setControl();
        setEvent();

    }


    private void setControl(){
        workerTimeKeepingListView = findViewById(R.id.timekeeping_detail_list_view);
        tenCongNhanTextView = findViewById(R.id.tenCongNhanTextView);
        ngayChamCongTextView = findViewById(R.id.ngayChamCongTextView);
        backButton = findViewById(R.id.backButton);
    }

    private void setEvent(){
        adapter = new TimeKeepingDetailAdapter(this,R.layout.statistic_time_keeping_detail_item, data);
        workerTimeKeepingListView.setAdapter(adapter);
        tenCongNhanTextView.setText(tenCongNhan);
        ngayChamCongTextView.setText(ngayChamCong);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData(){
        data = new ArrayList<>();
        statisticDatabase = new StatisticDatabase(this);
        data.addAll(statisticDatabase.getTimeKeepingDetail(maCongNhan,maChamCong));
    }
}