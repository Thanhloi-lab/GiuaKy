package com.example.giuaky.worker;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class UpdateWorkerFragment extends Fragment {

    Button btnBack;
    Button btnUpdate;
    EditText etFirstName;
    EditText etLastName;
    Spinner spnFactoryId;
    TextView tvTitle;
    ArrayList<Integer> factoryIds = new ArrayList<>();

    public void getFactoryIds() {
        for(int i=1; i<=10; i++)
        {
            factoryIds.add(i);
        }
    }

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
        getFactoryIds();

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
                    if(etFirstName.getText().toString()==null||etFirstName.getText().toString().equals("")||etLastName.getText().toString()==null||etLastName.getText().toString().equals(""))
                    {
                        openDialog(Gravity.CENTER,view);
                    }
                    else {
                        db.add(getWorker());
                        bundle.putString(Constant.UPDATE_STATUS, Constant.CREATE_COMPLETED);
                        Navigation.findNavController(view).navigate(R.id.action_updateWorker_to_listWorker, bundle);
                    }

                }
                else if(updatePage.getBtnStyle().equals(Constant.PAGE_EIDT_WORKER))
                {
                    if(etFirstName.getText().toString()==null||etFirstName.getText().toString().equals("")||etLastName.getText().toString()==null||etLastName.getText().toString().equals(""))
                    {
                        openDialog(Gravity.CENTER,view);
                    }
                    else {
                        db.edit(getWorker(), maCN);
                        bundle.putString(Constant.UPDATE_STATUS, Constant.UPDATE_COMPLETED);
                        Navigation.findNavController(view).navigate(R.id.action_updateWorker_to_listWorker, bundle);
                    }
                }
            }
        });
    }

    private void openDialog(int gravity,View view)
    {
        final Dialog dialog=new Dialog(view.getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.message_box);

        Window window=dialog.getWindow();
        if(window==null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        WindowManager.LayoutParams winLayoutParams=window.getAttributes();
        winLayoutParams.gravity=gravity;
        window.setAttributes(winLayoutParams);

        Button btnOK =dialog.findViewById(R.id.btnCancelAlert);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private Worker getWorker(){
        Worker worker = new Worker();
        String temp=etFirstName.getText().toString();
        temp=temp.trim().replaceAll(" +", " ");;
        worker.setHoCN(temp);
        temp=etLastName.getText().toString();
        temp=temp.trim().replaceAll(" +", " ");
        worker.setTenCN(temp);
        worker.setPhanXuong(Integer.parseInt(spnFactoryId.getSelectedItem().toString()));
        return worker;
    }

    private void setWorker(){
        if(bundle.containsKey(Constant.WORKER)) {
            wk = (Worker) bundle.getSerializable(Constant.WORKER);
            maCN = wk.getMaCN();
            etFirstName.setText(wk.getHoCN());
            etLastName.setText(wk.getTenCN());
            spnFactoryId.setSelection(getPosistion(wk.getPhanXuong()));
        }
    }

    private int getPosistion(int id) {
        return factoryIds.indexOf(id);
    }

    private void initPage() {

        if(updatePage.getBtnStyle().equals(Constant.PAGE_CREATE_WORKER))
        {
            btnUpdate.setBackgroundResource(R.drawable.btn_save);
            btnUpdate.setText("L??u");
            tvTitle.setText("TH??M C??NG NH??N");
            wk = new Worker();

        }
        if(updatePage.getBtnStyle().equals(Constant.PAGE_EIDT_WORKER))
        {
            btnUpdate.setBackgroundResource(R.drawable.btn_edit);
            btnUpdate.setText("S???a");
            tvTitle.setText("S???A C??NG NH??N");
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
        spnFactoryId = viewNow.findViewById(R.id.spnFactoryId);
        tvTitle = viewNow.findViewById(R.id.tvTitleUpdateWorker);


        final List<String> list = new ArrayList<String>();
        for (int item : factoryIds) {
            list.add(item+"");
        }

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnFactoryId.setAdapter(adp1);
    }
}