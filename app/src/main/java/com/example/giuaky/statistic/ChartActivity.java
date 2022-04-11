package com.example.giuaky.statistic;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.giuaky.Database.StatisticDatabase;
import com.example.giuaky.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    // variable for our bar chart
    BarChart barChart;

    // variable for our bar data.
    BarData barData;

    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;

    ArrayList<WorkerChartData> workerChartData;

    Button backButton;

    StatisticDatabase statisticDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_chart);
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        // calling method to get bar entries.
        getBarEntries();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "Thống kê số thành phẩm");

        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.
        barChart.setData(barData);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);


        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);


        XAxis xAxis = barChart.getXAxis();
        xAxis.setLabelCount(workerChartData.size());
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return workerChartData.get((int)value).getName();
            }
        });

    }

    private void getBarEntries() {
        barEntriesArrayList = new ArrayList<>();
        getWorkerData();
        for(int i = 0; i< workerChartData.size(); i++){

            barEntriesArrayList.add(new BarEntry((float)i, workerChartData.get(i).getAmountOfProduct()));
        }
    }

    private void getWorkerData(){
        workerChartData = new ArrayList<>();
        statisticDatabase = new StatisticDatabase(this);
        workerChartData.addAll(statisticDatabase.getCharData());

    }


}