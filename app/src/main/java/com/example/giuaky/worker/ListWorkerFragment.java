package com.example.giuaky.worker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;

import java.util.ArrayList;

public class ListWorkerFragment extends Fragment {
    ListView lvDanhSach;
    Spinner spnSort;
    EditText etSearch;
    Button btnAdd;

    WorkerAdapter adapter;
    ArrayList<Worker> data = new ArrayList<>();
    private WorkerDatabase db;
    private UpdatePage editPage = new UpdatePage("THÊM CÔNG NHÂN", "Sửa", Constant.PAGE_EIDT_WORKER);
    private UpdatePage createPage = new UpdatePage("SỬA CÔNG NHÂN", "Lưu", Constant.PAGE_CREATE_WORKER);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_worker, container, false);
        setControl(view);
        setEvent(view);
        init(view);
        return view;
    }

    private void setEvent(View view) {
//        data.add(new Worker(1, "Cao", "Loi", 1));
//        data.add(new Worker(2, "Cao1", "Loi1", 2));
//        data.add(new Worker(3, "Cao2", "Loi2", 3));
//        data.add(new Worker(4, "Cao3", "Loi3", 4));
//        data.add(new Worker(5, "Cao4", "Loi4", 5));
//        data.add(new Worker(6, "Cao5", "Loi5", 6));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateWorkerActivity.class);
                intent.putExtra("page", createPage);
                startActivityForResult(intent, 1);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.equals("")){
//                    filter worker
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        adapter = new WorkerAdapter(view.getContext(), R.layout.worker_info_item, data);
        lvDanhSach.setAdapter(adapter);
    }

    private void setControl(View view){
        lvDanhSach = (ListView) view.findViewById(R.id.lvListWorker);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        spnSort = (Spinner) view.findViewById(R.id.spnSortWorker);
        etSearch = (EditText) view.findViewById(R.id.et_sort_worker);
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