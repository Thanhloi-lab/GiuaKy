package com.example.giuaky.worker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;

public class UpdateWorkerActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_worker);
        setControl();
        updatePage = (UpdatePage)getIntent().getSerializableExtra("page");
        initPage();
        setEvent();
    }

    private void setEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //intent.putExtra("data", getWorker());
                setResult(0, intent);
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updatePage.getBtnStyle().equals(Constant.PAGE_CREATE_WORKER))
                {
                    if(etLastName.getText().toString()==null||etLastName.getText().toString().equals("")||etFirstName.getText().toString()==null||etFirstName.getText().toString().equals("")||etFactoryId.getText().toString()==null||etFactoryId.getText().toString().equals(""))
                    {
                        openDialog(Gravity.CENTER,view);
                    }
                    else
                    {
                        db.add(getWorker());
                        Intent intent = new Intent();
                        setResult(1, intent);
                        finish();
                    }

                }
                if(updatePage.getBtnStyle().equals(Constant.PAGE_EIDT_WORKER))
                {
                    if(etLastName.getText().toString()==null||etLastName.getText().toString().equals("")||etFirstName.getText().toString()==null||etFirstName.getText().toString().equals("")||etFactoryId.getText().toString()==null||etFactoryId.getText().toString().equals(""))
                    {
                        openDialog(Gravity.CENTER,view);
                    }
                    else
                    {
                    db.edit(getWorker(), maCN);
                    Intent intent = new Intent();
                    setResult(2, intent);
                    finish();
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
        worker.setHoCN(etFirstName.getText().toString());
        worker.setTenCN(etLastName.getText().toString());
        worker.setPhanXuong(Integer.parseInt(etFactoryId.getText().toString()));
        return worker;
    }

    private void initPage() {

        if(updatePage.getBtnStyle().equals(Constant.PAGE_CREATE_WORKER))
        {
            btnUpdate.setBackgroundResource(R.drawable.btn_save);
            btnUpdate.setText("Lưu");
            tvTitle.setText("THÊM CÔNG NHÂN");


        }
        if(updatePage.getBtnStyle().equals(Constant.PAGE_EIDT_WORKER))
        {
            btnUpdate.setBackgroundResource(R.drawable.btn_edit);
            btnUpdate.setText("Sửa");
            tvTitle.setText("SỬA CÔNG NHÂN");
            wk = new Worker();
            Bundle extras = getIntent().getExtras();
            if(extras.containsKey("MACN")) {
                String a =getIntent().getStringExtra("MACN");
                maCN=Integer.parseInt(a);
                wk = db.getById(maCN);
                etFirstName.setText(wk.getHoCN());
                etLastName.setText(wk.getTenCN());
                etFactoryId.setText(wk.getPhanXuong()+"");
            }
        }
    }


    private void setControl(){
        db = new WorkerDatabase(this);
        btnBack = findViewById(R.id.btnBack_to_listWorker);
        btnUpdate = findViewById(R.id.btnUpdateWorker);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etFactoryId = findViewById(R.id.etFactoryId);
        tvTitle = findViewById(R.id.tvTitleUpdateWorker);
    }
}