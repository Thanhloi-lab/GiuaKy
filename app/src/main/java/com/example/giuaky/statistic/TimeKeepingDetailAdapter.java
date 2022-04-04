package com.example.giuaky.statistic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.giuaky.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TimeKeepingDetailAdapter extends ArrayAdapter<TimeKeepingDetail> {
    Context context;
    int resource;
    ArrayList<TimeKeepingDetail> data;

    public TimeKeepingDetailAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TimeKeepingDetail> data) {
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
        TextView maSanPhamTextView = convertView.findViewById(R.id.maSanPhamTextView);
        TextView tenSanPhamTextView = convertView.findViewById(R.id.tenSanPhamTextView);
        TextView donGiaTextView = convertView.findViewById(R.id.donGiaTextView);
        TextView soThanhPhamTextView = convertView.findViewById(R.id.thanhPhamTextView);
        TextView soPhePhamTextView = convertView.findViewById(R.id.phePhamTextView);
        TextView tongTienTextView = convertView.findViewById(R.id.tongTienTextView);



        TimeKeepingDetail timeKeepingDetail = data.get(position);
        double tongTien = timeKeepingDetail.getDonGia()* timeKeepingDetail.getSoThanhPham() - (timeKeepingDetail.getDonGia()/2*timeKeepingDetail.getSoPhePham());


        maSanPhamTextView.setText(timeKeepingDetail.getMaSanPham()+"");
        tenSanPhamTextView.setText(timeKeepingDetail.getTenSanPham());
        donGiaTextView.setText(timeKeepingDetail.getDonGia() + "");
        soPhePhamTextView.setText(timeKeepingDetail.getSoPhePham()+"");
        soThanhPhamTextView.setText(timeKeepingDetail.getSoThanhPham()+"");
        tongTienTextView.setText(tongTien +"");

        return convertView;
    }
}
