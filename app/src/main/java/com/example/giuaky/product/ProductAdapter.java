package com.example.giuaky.product;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuaky.Constant;

import com.example.giuaky.Database.ProductDatabase;
import com.example.giuaky.MainActivity;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;


import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> implements Filterable {
    Context context;
    int resource;
    ArrayList<Product> data;
    ArrayList<Product> dataOld;
    private ProductDatabase db;

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        public TextView tvMaSP;
        public TextView tvTenSP;
        public TextView tvGia ;

        public Button btnSuaSP ;
        public Button btnXoaSP ;

        Context context;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaSP= (TextView)itemView.findViewById(R.id.tvMaSP);
            tvTenSP = (TextView)itemView.findViewById(R.id.tvProductName);
            tvGia = (TextView)itemView.findViewById(R.id.tvProductPrice);

            btnSuaSP = (Button)itemView.findViewById(R.id.btnEditProduct);
            btnXoaSP = (Button)itemView.findViewById(R.id.btnDeleteProduct);


        }
    }

    public ProductAdapter(Context context, int resource, ArrayList<Product> data) {
        this.data = data;
        this.dataOld = data;
        this.context=context;
        this.resource=resource;

    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_info_item, parent, false);

        return new ProductAdapter.ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {

        Product product = data.get(position);
        if(product==null) return;

        holder.tvMaSP.setText(product.getMaSP()+"");
        holder.tvTenSP.setText(product.getTenSP());
        holder.tvGia.setText(product.getGia()+"");

        holder.btnSuaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(view.getContext(), UpdateProduct.class);
                UpdatePage editPage = new UpdatePage("SỬA SẢN PHẨM", "Sửa", Constant.PAGE_EDIT_PRODUCT);
                intent.putExtra("pageSP", editPage);
                intent.putExtra("MASP", String.valueOf(product.getMaSP()));
                ((MainActivity)context).startActivityForResult(intent,4);
            }
        });

        holder.btnXoaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeleteDialog(Gravity.CENTER,product);
            }
        });
    }
    private void openDeleteDialog(int gravity,Product product)
    {
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_product);

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

        TextView etAlertProduct=dialog.findViewById(R.id.tvAlertProductDelete);
        etAlertProduct.setText("Bạn có chắc chắn muốn xóa sản phẩm với id="+product.getMaSP()+" ?");
        Button btnKhong=dialog.findViewById(R.id.btnCancelProduct);
        Button btnCo =dialog.findViewById(R.id.btnCheckDeleteProduct);

        btnKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db=new ProductDatabase(context);
                db.deleteProduct(product);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @Override
    public int getItemCount() {
        if(data!=null){
            return  data.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    data = dataOld;
                }
                else{
                    ArrayList<Product> list = new ArrayList<>();
                    for(Product product : dataOld){
                        if(product.getTenSP().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(product);
                        }
                    }
                    data=list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data ;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<Product>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
