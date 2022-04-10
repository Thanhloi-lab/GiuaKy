package com.example.giuaky.time_keeping;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.TimeKeepingDatabase;
import com.example.giuaky.Database.TimeKeepingDetailDatabase;
import com.example.giuaky.R;

import java.util.ArrayList;

public class TimeKeepingDetailAdapter extends ArrayAdapter<TimeKeepingDetailViewModel>  {
    Context context;
    int resource;
    Button btnDelete, btnEdit;
    TextView tvProductName, tvFinishProduct,tvWasteProduct;
    View view;
    TimeKeepingDetailViewModel item;




    ArrayList<TimeKeepingDetailViewModel> data;
    public TimeKeepingDetailAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TimeKeepingDetailViewModel> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        view = convertView;
        setControl();
        item = data.get(position);
        initPage();
        setEvent();

        return convertView;
    }
    private void setControl()
    {
        tvProductName = view.findViewById(R.id.tvProductNametkd);
        tvFinishProduct = view.findViewById(R.id.tvFinishtkd);
        tvWasteProduct = view.findViewById(R.id.tvWastetkd);
        btnDelete  = view.findViewById(R.id.btnDeleteTimekeepingDetail);
        btnEdit  = view.findViewById(R.id.btnEditTimekeepingDetail);
    }
    private void initPage()
    {
        tvFinishProduct.setText(item.getFinish_product() + "");
        tvWasteProduct.setText(item.getWaste() + "");
        tvProductName.setText(item.getProduct_name());
    }

    private void setEvent()
    {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeleteDialog();
            }
        });
    }
    public void openDeleteDialog()
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_time_keeeping_detail);
        Window window = dialog.getWindow();
        if(window == null)
            return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);

        dialog.setCancelable(true);

        TextView tvProductNametkd  = dialog.findViewById(R.id.tvProductNamedtkd);

        Button  btnConfirmDelete = dialog.findViewById(R.id.btnConfirmDeletetkd);

        tvProductNametkd.setText(item.getProduct_name());
        TimeKeepingDetailDatabase timeKeepingDetailDatabase = new TimeKeepingDetailDatabase(getContext());

        btnConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeKeepingDetailDatabase.delete(item.getTime_keeping_id(),item.getProduct_id());
                dialog.dismiss();

                for (TimeKeepingDetailViewModel timeKeepingViewModel: data
                     ) {
                    if(timeKeepingViewModel.getTime_keeping_id() == item.getTime_keeping_id()
                        && timeKeepingViewModel.getProduct_id() == item.getProduct_id())
                        data.remove(timeKeepingViewModel);
                }

                notifyDataSetChanged();
                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();

    }
}
