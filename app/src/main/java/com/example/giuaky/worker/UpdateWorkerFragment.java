package com.example.giuaky.worker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class UpdateWorkerFragment extends Fragment {

    Button btnBack;
    Button btnUpdate;
    EditText etFirstName;
    EditText etLastName;
    EditText etFactoryId;
    TextView tvTitle;

    private int maCN;
    private UpdatePage updatePage;
    private WorkerDatabase db;
    private Worker wk;
    View viewNow;
    Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewNow = inflater.inflate(R.layout.activity_update_worker, container, false);
        bundle = this.getArguments();

        setControl();
        setEvent();
        if(bundle!=null){
            if(bundle.containsKey(Constant.PAGE)){
                updatePage = (UpdatePage)bundle.getSerializable(Constant.PAGE);
                initPage();
            }
        }
        return viewNow;
    }

    private void setEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(viewNow);
//                getParentFragmentManager().setFragmentResult("back", bundle);
                navController.popBackStack();
//                Navigation.findNavController(viewNow).navigate(R.id.action_updateWorker_to_listWorker);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if(updatePage.getBtnStyle().equals(Constant.PAGE_CREATE_WORKER))
                {
                    Worker createItem = getWorker();
                    int max = db.getMaxId()+1;
                    createItem.setMaCN(max);
                    db.add(createItem);
                    bundle.putString(Constant.UPDATE_STATUS, Constant.CREATE_COMPLETED);
                    Navigation.findNavController(view).navigate(R.id.action_updateWorker_to_listWorker, bundle);
                }
                else if(updatePage.getBtnStyle().equals(Constant.PAGE_EIDT_WORKER))
                {
                    db.edit(getWorker(), maCN);
                    bundle.putString(Constant.UPDATE_STATUS, Constant.UPDATE_COMPLETED);
                    Navigation.findNavController(view).navigate(R.id.action_updateWorker_to_listWorker, bundle);
                }
            }
        });
    }

    private Worker getWorker(){
        Worker worker = new Worker();
        worker.setHoCN(etFirstName.getText().toString());
        worker.setTenCN(etLastName.getText().toString());
        worker.setPhanXuong(Integer.parseInt(etFactoryId.getText().toString()));
        return worker;
    }

    private void setWorker(){
        if(bundle.containsKey(Constant.WORKER)) {
            wk = (Worker) bundle.getSerializable(Constant.WORKER);
            maCN = wk.getMaCN();
            etFirstName.setText(wk.getHoCN());
            etLastName.setText(wk.getTenCN());
            etFactoryId.setText(wk.getPhanXuong()+"");
        }
    }

    private void initPage() {

        if(updatePage.getBtnStyle().equals(Constant.PAGE_CREATE_WORKER))
        {
            btnUpdate.setBackgroundResource(R.drawable.btn_save);
            btnUpdate.setText("Lưu");
            tvTitle.setText("THÊM CÔNG NHÂN");
            wk = new Worker();

        }
        if(updatePage.getBtnStyle().equals(Constant.PAGE_EIDT_WORKER))
        {
            btnUpdate.setBackgroundResource(R.drawable.btn_edit);
            btnUpdate.setText("Sửa");
            tvTitle.setText("SỬA CÔNG NHÂN");
            setWorker();
        }
    }

    private void setControl(){
        BottomNavigationView navBar = getActivity().findViewById(R.id.bottom_navigation);
        navBar.setVisibility(View.GONE);
        db = new WorkerDatabase(viewNow.getContext());

        btnBack = viewNow.findViewById(R.id.btnBack_to_listWorker);
        btnUpdate = viewNow.findViewById(R.id.btnUpdateWorker);
        etFirstName = viewNow.findViewById(R.id.etFirstName);
        etLastName = viewNow.findViewById(R.id.etLastName);
        etFactoryId = viewNow.findViewById(R.id.etFactoryId);
        tvTitle = viewNow.findViewById(R.id.tvTitleUpdateWorker);
    }
}