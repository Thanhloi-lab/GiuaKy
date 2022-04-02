package com.example.giuaky.worker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
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
                intent.putExtra("data", getWorker());
                setResult(1, intent);
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updatePage.getBtnStyle().equals(Constant.PAGE_CREATE_WORKER))
                {
                    db.add(getWorker());
                    Intent intent = new Intent();
                    setResult(1, intent);
                    finish();
                }
                if(updatePage.getBtnStyle().equals(Constant.PAGE_EIDT_WORKER))
                {
                    db.edit(getWorker(), maCN);
                    Intent intent = new Intent();
                    setResult(2, intent);
                    finish();
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

    private void initPage() {

        if(updatePage.getBtnStyle().equals(Constant.PAGE_CREATE_WORKER))
        {
            btnUpdate.setBackgroundResource(R.drawable.btn_save);
            btnUpdate.setText("Lưu");
            tvTitle.setText("THÊM CÔNG NHÂN");

            Bundle extras = getIntent().getExtras();
            if(extras.containsKey("MACN")) {
                maCN = Integer.parseInt(getIntent().getStringExtra("MACN"));
                wk = db.getById(maCN);
                etFirstName.setText(wk.getHoCN());
                etLastName.setText(wk.getTenCN());
                etFactoryId.setText(wk.getPhanXuong());
            }
        }
        if(updatePage.getBtnStyle().equals(Constant.PAGE_EIDT_WORKER))
        {
            btnUpdate.setBackgroundResource(R.drawable.btn_edit);
            btnUpdate.setText("Sửa");
            tvTitle.setText("SỬA CÔNG NHÂN");
            wk = new Worker();
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