package com.example.giuaky.worker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;

import java.util.ArrayList;

public class ListWorkerFragment extends Fragment {
    RecyclerView rDanhSach;
    Spinner spnSort;
    SearchView svWorker;
    Button btnAdd;
    View view;

    WorkerAdapter adapter;
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
        view = inflater.inflate(R.layout.fragment_list_worker, container, false);
        setControl(view);
        setEvent(view);
        init(view);
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

        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spnSort.getSelectedItem().toString()){
                    case "MÃ CÔNG NHÂN":{
                        data.sort(Worker.WorkerIdComparator);
                        break;
                    }
                    case "HỌ":{
                        data.sort(Worker.WorkerFirstNameComparator);
                        break;
                    }
                    case "TÊN":{
                        data.sort(Worker.WorkerLastNameComparator);
                        break;
                    }
                    case "PHÂN XƯỞNG":{
                        data.sort(Worker.WorkerFactoryIdComparator);
                        break;
                    }
                    default:{
                        break;
                    }
                }
                adapter = new WorkerAdapter(data);
                rDanhSach.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapter = new WorkerAdapter(dataOld);
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

        adapter = new WorkerAdapter(data);

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