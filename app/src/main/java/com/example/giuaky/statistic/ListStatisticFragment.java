package com.example.giuaky.statistic;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;
import com.example.giuaky.worker.Worker;

import java.util.ArrayList;


public class ListStatistic extends Fragment {

    RecyclerView rDanhSach;

    SearchView svWorker;
    Button chartButton;
    View view;

    StatisticWorkerAdapter adapter;
    ArrayList<Worker> data = new ArrayList<>();
    ArrayList<Worker> dataOld = new ArrayList<>();
    private WorkerDatabase db;
    private UpdatePage editPage = new UpdatePage("THÊM CÔNG NHÂN", "Sửa", Constant.PAGE_EIDT_WORKER);
    private UpdatePage createPage = new UpdatePage("SỬA CÔNG NHÂN", "Lưu", Constant.PAGE_CREATE_WORKER);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_statistic, container, false);
        setControl(view);
        setEvent(view);
//        init(view);
        return view;
    }

    private void setEvent(View view) {
        data.add(new Worker(1, "G", "Loi", 1));
        data.add(new Worker(2, "H", "Loi1", 2));
        data.add(new Worker(3, "B", "Loi2", 3));
        data.add(new Worker(4, "X", "Loi3", 10));
        data.add(new Worker(5, "W", "Loi4", 5));
        data.add(new Worker(6, "A", "Loi5", 6));

        dataOld.addAll(data);
        adapter = new StatisticWorkerAdapter(getActivity(),data);

        svWorker.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
        chartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChartActivity.class);
                startActivity(intent);
            }
        });

        rDanhSach.setAdapter(adapter);
    }



    private void setControl(View view){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rDanhSach = (RecyclerView) view.findViewById(R.id.rListWorker);
        rDanhSach.setLayoutManager(linearLayoutManager);
        chartButton = (Button) view.findViewById(R.id.chartButton);
        svWorker = (SearchView) view.findViewById(R.id.sv_sort_worker);

    }

    void init(View view){
        db = new WorkerDatabase(view.getContext());
        data = new ArrayList<>();
        data = db.read();
    }

    private void show(){
        data.clear();
        data.addAll(db.read());
        adapter.notifyDataSetChanged();
    }

    private String getStringResource(int string){
        return getResources().getString(string);
    }


}