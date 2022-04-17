package com.example.giuaky.product;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.ProductDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;


public class UpdateProduct extends Fragment {
    Button btnBackSP;
    Button btnUpdateSP;
    EditText etProductName;
    EditText etProductPrice;
    TextView tvTitle;
    View viewNow;
    Bundle bundle;


    private UpdatePage updatePage;
    private ProductDatabase db;
    private Product product;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewNow = inflater.inflate(R.layout.activity_update_product, container, false);
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
        btnBackSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(viewNow).popBackStack();
            }
        });

        btnUpdateSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if(updatePage.getBtnStyle().equals(Constant.PAGE_CREATE_PRODUCT))
                {
                    if(etProductName.getText().toString()==null||etProductName.getText().toString().equals("")||etProductPrice.getText().toString()==null||etProductPrice.getText().toString().equals(""))
                    {
                        openDialog(Gravity.CENTER,view,1);
                    }
                    else if(Integer.parseInt(etProductPrice.getText().toString().trim())<1000)
                    {
                        openDialog(Gravity.CENTER,view,2);
                    }
                    else
                    {
                        db.addProduct(getProduct());
                        bundle.putString(Constant.UPDATE_STATUS, Constant.CREATE_COMPLETED);
                        Navigation.findNavController(view).navigate(R.id.updateProduct_to_list, bundle);
                    }
                }
                if(updatePage.getBtnStyle().equals(Constant.PAGE_EDIT_PRODUCT))
                {
                    if(etProductName.getText().toString()==null||etProductName.getText().toString().equals("")||etProductPrice.getText().toString()==null||etProductPrice.getText().toString().equals(""))
                    {
                        openDialog(Gravity.CENTER,view,1);
                    }
                    else if(Float.parseFloat(etProductPrice.getText().toString().trim())<1000)
                    {
                        openDialog(Gravity.CENTER,view,2);
                    }
                    else {
                        db.editProduct(getProduct(), product.getMaSP());
                        bundle.putString(Constant.UPDATE_STATUS, Constant.UPDATE_COMPLETED);
                        Navigation.findNavController(view).navigate(R.id.updateProduct_to_list, bundle);
                    }
                }
            }
        });
    }

    private void openDialog(int gravity,View view,int i)
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
        if(i==2)
        {
            TextView text=dialog.findViewById(R.id.tv_info_alert);
            text.setText("Gía tiền phải lớn hơn 1000!!!");
        }

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
        String temp=etProductName.getText().toString();
        temp=temp.trim().replaceAll(" +", " ");;
        product.setTenSP(temp);
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
            if(bundle.containsKey(Constant.PRODUCT)) {
                product = (Product)bundle.getSerializable(Constant.PRODUCT);
                etProductName.setText(product.getTenSP());
                etProductPrice.setText(product.getGia()+"");
            }
        }
    }


    private void setControl(){
        db = new ProductDatabase(viewNow.getContext());
        btnBackSP = viewNow.findViewById(R.id.btnBack_to_listProduct);
        btnUpdateSP = viewNow.findViewById(R.id.btnUpdateProduct);
        etProductName = viewNow.findViewById(R.id.etProductName);
        etProductPrice= viewNow.findViewById(R.id.etPriceProduct);
        tvTitle = viewNow.findViewById(R.id.tvTitleUpdateProduct);
    }
}