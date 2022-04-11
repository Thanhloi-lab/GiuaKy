package com.example.giuaky.time_keeping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.giuaky.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TimeKeepingAdapter extends ArrayAdapter<TimeKeepingViewModel>  {
    Context context;
    int resource;
    ArrayList<TimeKeepingViewModel> data;
    public TimeKeepingAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TimeKeepingViewModel> data) {
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
        TextView tvDate = convertView.findViewById(R.id.tvDatetkd1);
        TextView tvName = convertView.findViewById(R.id.tvNametkd1);
        TextView tvWorkId = convertView.findViewById(R.id.tvWorkerIdtkd1);

        TimeKeepingViewModel timekeeping = data.get(position);
        System.out.println(tvDate.getText() + "alo");
//
        tvDate.setText(new SimpleDateFormat("dd-MM-yyyy").format( timekeeping.getDate()).toString());
        tvName.setText(timekeeping.getWorker_name());
        tvWorkId.setText( String.valueOf(timekeeping.getWorker_id()));

        return convertView;
    }
}
