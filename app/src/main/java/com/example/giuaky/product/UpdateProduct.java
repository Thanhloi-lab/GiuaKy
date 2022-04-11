package com.example.giuaky.product;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.ProductDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;


public class UpdateProduct extends AppCompatActivity {

    Button btnBackSP;
    Button btnUpdateSP;
    EditText etProductName;
    EditText etProductPrice;
    TextView tvTitle;

    private int maSP;
    private UpdatePage updatePage;
    private ProductDatabase db;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        setControl();
        updatePage = (UpdatePage)getIntent().getSerializableExtra("pageSP");
        initPage();
        setEvent();
    }

    private void setEvent() {
        btnBackSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(0, intent);
                finish();
            }
        });

        btnUpdateSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(updatePage.getBtnStyle().equals(Constant.PAGE_CREATE_PRODUCT))
                {
                    if(etProductName.getText().toString()==null||etProductName.getText().toString().equals("")||etProductPrice.getText().toString()==null||etProductPrice.getText().toString().equals(""))
                    {
                        openDialog(Gravity.CENTER,view);
                    }
                    else
                    {
                    db.addProduct(getProduct());
                    Intent intent = new Intent();
                    setResult(3, intent);
                    finish();}
                }
                if(updatePage.getBtnStyle().equals(Constant.PAGE_EDIT_PRODUCT))
                {
                    if(etProductName.getText().toString()==null||etProductName.getText().toString().equals("")||etProductPrice.getText().toString()==null||etProductPrice.getText().toString().equals(""))
                    {
                        openDialog(Gravity.CENTER,view);
                    }
                    else
                    {
                    db.editProduct(getProduct(), maSP);
                    Intent intent = new Intent();
                    setResult(4, intent);
                    finish();}
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

    private Product getProduct(){
        Product product = new Product();
        product.setTenSP(etProductName.getText().toString());
        product.setGia(Float.parseFloat(etProductPrice.getText().toString()));
        return product;
    }

    private void initPage() {

        if(updatePage.getBtnStyle().equals(Constant.PAGE_CREATE_PRODUCT))
        {
            btnUpdateSP.setBackgroundResource(R.drawable.btn_save);
            btnUpdateSP.setText("Lưu");
            tvTitle.setText("THÊM SẢN PHẨM");


        }
        if(updatePage.getBtnStyle().equals(Constant.PAGE_EDIT_PRODUCT))
        {
            btnUpdateSP.setBackgroundResource(R.drawable.btn_edit);
            btnUpdateSP.setText("Sửa");
            tvTitle.setText("SỬA SẢN PHẨM");
            product = new Product();
            Bundle extras = getIntent().getExtras();
            if(extras.containsKey("MASP")) {
                String a =getIntent().getStringExtra("MASP");
                maSP=Integer.parseInt(a);
                product = db.getProductById(maSP);
                etProductName.setText(product.getTenSP());

                etProductPrice.setText(product.getGia()+"");
            }
        }
    }


    private void setControl(){
        db = new ProductDatabase(this);
        btnBackSP = findViewById(R.id.btnBack_to_listProduct);
        btnUpdateSP = findViewById(R.id.btnUpdateProduct);
        etProductName = findViewById(R.id.etProductName);
        etProductPrice= findViewById(R.id.etPriceProduct);
        tvTitle = findViewById(R.id.tvTitleUpdateProduct);
    }
}