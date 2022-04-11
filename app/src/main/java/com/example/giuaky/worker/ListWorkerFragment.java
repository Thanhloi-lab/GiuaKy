package com.example.giuaky.worker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
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
import android.widget.Toast;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ListWorkerFragment extends Fragment {
    RecyclerView rDanhSach;
    Spinner spnSort;
    SearchView svWorker;
    Button btnAdd;
    View view;

    WorkerAdapter adapter;
    ArrayList<Worker> data = new ArrayList<>();
    private WorkerDatabase db;

    private UpdatePage createPage = new UpdatePage("SỬA CÔNG NHÂN", "Lưu", Constant.PAGE_CREATE_WORKER);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list_worker, container, false);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            if(bundle.containsKey("message")){
                String message = bundle.getString("message");
                Toast.makeText(view.getContext(), message, Toast.LENGTH_LONG).show();
            }
        }
        init(view);
        setControl(view);
        setEvent(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        navController.clearBackStack(R.id.listWorkerFragment);
    }

    private void setEvent(View view) {
        spnSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spnSort.getSelectedItem().toString()){
                    case "MÃ CÔNG NHÂN":{
                        data = db.read();
                        data.sort(Worker.WorkerIdComparator);
//                        adapter = new WorkerAdapter(data);
                        rDanhSach.setAdapter(adapter);
                        break;
                    }
                    case "HỌ":{
                        data = db.read();
                        data.sort(Worker.WorkerFirstNameComparator);
//                        adapter = new WorkerAdapter(data);
                        rDanhSach.setAdapter(adapter);
                        break;
                    }
                    case "TÊN":{
                        data = db.read();
                        data.sort(Worker.WorkerLastNameComparator);
//                        adapter = new WorkerAdapter(data);
                        rDanhSach.setAdapter(adapter);
                        break;
                    }
                    case "PHÂN XƯỞNG":{
                        data = db.read();
                        data.sort(Worker.WorkerFactoryIdComparator);
//                        adapter = new WorkerAdapter(data);
                        rDanhSach.setAdapter(adapter);
                        break;
                    }
                    default:{
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.PAGE, createPage);
                Navigation.findNavController(view).navigate(R.id.action_listWorker_to_updateWorker, bundle);
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
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.VISIBLE);

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