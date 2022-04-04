package com.example.giuaky.statistic;

import android.content.Context;
import android.content.Intent;
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

public class WorkerTimeKeepingAdapter extends ArrayAdapter<WorkerTimeKeeping> {
    Context context;
    int resource;
    ArrayList<WorkerTimeKeeping> data;
    private int maCongNhan;
    private String tenCongNhan;

    public WorkerTimeKeepingAdapter(@NonNull Context context, int resource, @NonNull ArrayList<WorkerTimeKeeping> data, int maCongNhan, String tenCongNhan) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.maCongNhan = maCongNhan;
        this.tenCongNhan = tenCongNhan;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView textViewMaMonHoc = convertView.findViewById(R.id.maChamCongTextView);
        TextView textViewTenMonHoc = convertView.findViewById(R.id.ngayChamCongTextView);
        TextView textViewSoTiet = convertView.findViewById(R.id.tongLuongTextView);
        Button goToDetailButton  = convertView.findViewById(R.id.time_keeping_detail_button);

        WorkerTimeKeeping workerTimeKeeping = data.get(position);

        textViewMaMonHoc.setText(workerTimeKeeping.getMaChamCong() + "");
        textViewTenMonHoc.setText(workerTimeKeeping.getNgayChamCong());
        textViewSoTiet.setText(workerTimeKeeping.getTongTienLuong() + "");

        goToDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TimeKeepingDetailActivity.class);
                intent.putExtra("maCongNhan", maCongNhan);
                intent.putExtra("maChamCong", data.get(position).getMaChamCong());
                intent.putExtra("tenCongNhan", tenCongNhan);
                intent.putExtra("ngayChamCong", data.get(position).getNgayChamCong());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
