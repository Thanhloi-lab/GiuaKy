package com.example.giuaky.worker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.giuaky.R;

import java.util.ArrayList;

public class WorkerAdapter extends ArrayAdapter<Worker> {
    Context context;
    int resource;
    ArrayList<Worker> data;

    public WorkerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Worker> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.data = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tvMaCN = convertView.findViewById(R.id.tvMaCN);
        TextView tvHo = convertView.findViewById(R.id.tvFirstName);
        TextView tvTen = convertView.findViewById(R.id.tvLastName);
        TextView tvPhanXuong = convertView.findViewById(R.id.tvFactoryId);

        Button btnSua = convertView.findViewById(R.id.btnEditWorker);
        Button btnXoa = convertView.findViewById(R.id.btnDeleteWorker);

        Worker cn = data.get(position);
        tvMaCN.setText(cn.getMaCN()+"");
        tvHo.setText(cn.getHoCN());
        tvTen.setText(cn.getTenCN());
        tvPhanXuong.setText(cn.getPhanXuong()+"");

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return convertView;
    }
}
