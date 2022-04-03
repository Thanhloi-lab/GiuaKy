package com.example.giuaky.worker;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;

import java.util.ArrayList;
import java.util.Collections;

public class ListWorkerFragment extends Fragment {
    public RecyclerView rDanhSach;
    Spinner spnSort;
    SearchView svWorker;
    Button btnAdd;
    View view;


    WorkerAdapter adapter;
    ArrayList<Worker> data = new ArrayList<>();
    private WorkerDatabase db;

    private UpdatePage createPage = new UpdatePage("THÊM CÔNG NHÂN", "Lưu", Constant.PAGE_CREATE_WORKER);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_worker, container, false);
        setControl(view);
        setEvent(view);
        init(view);
        show();
        adapter.notifyDataSetChanged();

        return view;
    }


    private void setEvent(View view) {

        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spnSort.getSelectedItem().toString()){
                    case "MÃ CÔNG NHÂN":{
                        data=db.read();
                        Collections.sort(data,Worker.WorkerIdComparator);

                        adapter = new WorkerAdapter(view.getContext(),R.layout.worker_info_item,data);
                        rDanhSach.setAdapter(adapter);
                        break;
                    }
                    case "HỌ":{
                        data=db.read();
                        Collections.sort(data,Worker.WorkerFirstNameComparator);

                        adapter = new WorkerAdapter(view.getContext(),R.layout.worker_info_item,data);
                        rDanhSach.setAdapter(adapter);
                        break;
                    }
                    case "TÊN":{
                        data=db.read();
                        Collections.sort(data,Worker.WorkerLastNameComparator);

                        adapter = new WorkerAdapter(view.getContext(),R.layout.worker_info_item,data);
                        rDanhSach.setAdapter(adapter);
                        break;
                    }
                    case "PHÂN XƯỞNG":{
                        data=db.read();
                        Collections.sort(data,Worker.WorkerFactoryIdComparator);

                        adapter = new WorkerAdapter(view.getContext(),R.layout.worker_info_item,data);
                        rDanhSach.setAdapter(adapter);
                        break;
                    }
                    default:{

                        break;
                    }
                }
                adapter = new WorkerAdapter(view.getContext(), R.layout.worker_info_item, data);
                rDanhSach.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapter = new WorkerAdapter(view.getContext(), R.layout.worker_info_item, data);
                rDanhSach.setAdapter(adapter);
            }
        });

        svWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svWorker.setIconified(false);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateWorkerActivity.class);
                intent.putExtra("page", createPage);
                startActivityForResult(intent, 1);
            }
        });



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
        adapter = new WorkerAdapter(view.getContext(), R.layout.worker_info_item, data);
        rDanhSach.setAdapter(adapter);
    }

    private void setControl(View view){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rDanhSach = (RecyclerView) view.findViewById(R.id.rListWorker);
        rDanhSach.setLayoutManager(linearLayoutManager);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        spnSort = (Spinner) view.findViewById(R.id.spnSortWorker);
        svWorker = (SearchView) view.findViewById(R.id.sv_sort_worker);
    }

    void init(View view){
        db = new WorkerDatabase(view.getContext());
        data = new ArrayList<>();
        data = db.read();
        adapter.notifyDataSetChanged();

    }

    private void show(){
        data.clear();
        data.addAll(db.read());
        adapter=new WorkerAdapter(view.getContext(), R.layout.worker_info_item,data);
        rDanhSach.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        show();
        adapter.notifyDataSetChanged();

    }
}