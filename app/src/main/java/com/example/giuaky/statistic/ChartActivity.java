package com.example.giuaky.statistic;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.giuaky.R;
import com.example.giuaky.statistic.WorkerStatistic;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
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

    ArrayList<WorkerStatistic> workerStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        // initializing variable for bar chart.
        barChart = findViewById(R.id.idBarChart);


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
        xAxis.setLabelCount(workerStatistics.size());
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                System.out.println(value);
                System.out.println(workerStatistics.get((int)value).getName());
                return workerStatistics.get((int)value).getName();
            }
        });

    }

    private void getBarEntries() {
        barEntriesArrayList = new ArrayList<>();
        getWorkerData();
        for(int i = 0; i<workerStatistics.size(); i++){

            barEntriesArrayList.add(new BarEntry((float)i, workerStatistics.get(i).getAmountOfProduct()));
        }
    }

    private void getWorkerData(){
        workerStatistics = new ArrayList<>();
        // TODO: get data from database
        workerStatistics.add(new WorkerStatistic("1","Khang",20));
        workerStatistics.add(new WorkerStatistic("2","Loi",15));
        workerStatistics.add(new WorkerStatistic("3","Duy",10));
        workerStatistics.add(new WorkerStatistic("4","Hien",5));
    }


}