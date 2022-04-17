package com.example.giuaky.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.ProductDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;

import java.util.ArrayList;
import java.util.Collections;

public class ListProductFragment extends Fragment {
    RecyclerView rvDanhSachSP;
    Spinner spnSortSP;
    SearchView svSearchSP;
    Button btnAddProduct;
    ProductAdapter adapterSP;

    View viewNow;
    ArrayList<Product> dataSP=new ArrayList<>();
    private ProductDatabase dbSP;

    private UpdatePage createPage = new UpdatePage("THÊM SẢN PHẨM", "Lưu", Constant.PAGE_CREATE_PRODUCT);

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        viewNow = inflater.inflate(R.layout.fragment_list_product, container, false);
        setControl(viewNow);
        setEvent(viewNow);
        init(viewNow);
        return viewNow;

    }

    private void init(View view) {
        dbSP = new ProductDatabase(view.getContext());
        dataSP = new ArrayList<>();
        dataSP = dbSP.readProduct();


    }

    private void setEvent(View view) {
        spnSortSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (spnSortSP.getSelectedItem().toString()){
                    case "MÃ SẢN PHẨM":{
                        dataSP=dbSP.readProduct();
                        Collections.sort(dataSP, Product.ProductIdComparator);
                        adapterSP=new ProductAdapter(viewNow.getContext(), R.layout.product_info_item,dataSP);
                        rvDanhSachSP.setAdapter(adapterSP);
                        break;
                    }
                    case "TÊN SẢN PHẨM":{
                        dataSP=dbSP.readProduct();
                        Collections.sort(dataSP, Product.ProductNameComparator);
                        adapterSP=new ProductAdapter(viewNow.getContext(), R.layout.product_info_item,dataSP);
                        rvDanhSachSP.setAdapter(adapterSP);
                        break;
                    }
                    case "ĐƠN GIÁ":{
                        dataSP=dbSP.readProduct();
                        Collections.sort(dataSP, Product.ProductPriceComparator);
                        adapterSP=new ProductAdapter(viewNow.getContext(), R.layout.product_info_item,dataSP);
                        rvDanhSachSP.setAdapter(adapterSP);
                        break;
                    }
                    default:{
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterSP = new ProductAdapter(viewNow.getContext(), R.layout.product_info_item, dataSP);
                rvDanhSachSP.setAdapter(adapterSP);
            }
        });

        svSearchSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                svSearchSP.setIconified(false);
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), UpdateProduct.class);
//                intent.putExtra("pageSP", createPage);
//                startActivityForResult(intent, 3);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.PAGE, createPage);
                Navigation.findNavController(viewNow).navigate(R.id.listProduct_to_update, bundle);
            }
        });



        svSearchSP.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapterSP.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapterSP.getFilter().filter(s);
                return false;
            }
        });
        adapterSP = new ProductAdapter(view.getContext(), R.layout.product_info_item, dataSP);
        rvDanhSachSP.setAdapter(adapterSP);
    }

    private void setControl(View view) {
        btnAddProduct = (Button) view.findViewById(R.id.btnAddProduct);
        spnSortSP = (Spinner) view.findViewById(R.id.spnSortProduct);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rvDanhSachSP = (RecyclerView) view.findViewById(R.id.rlProduct);
        rvDanhSachSP.setLayoutManager(linearLayoutManager);
        spnSortSP = (Spinner) view.findViewById(R.id.spnSortProduct);
        svSearchSP = (SearchView) view.findViewById(R.id.sv_sort_product);
    }

    private void showList() {
        dataSP.clear();
        dataSP.addAll(dbSP.readProduct());
        adapterSP=new ProductAdapter(viewNow.getContext(), R.layout.product_info_item,dataSP);
        rvDanhSachSP.setAdapter(adapterSP);


    }

}
