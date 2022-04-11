package com.example.giuaky.time_keeping;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.ConsumerIrManager;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.TimeKeepingDatabase;
import com.example.giuaky.Database.TimeKeepingDetailDatabase;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.worker.Worker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TimeKeepingAdapter extends ArrayAdapter<TimeKeepingViewModel>  {
    Context context;
    int resource;
    Button btnDelete;


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
        btnDelete  = convertView.findViewById(R.id.btnDeletetbnEditTimekeeping);
        LinearLayout llTimeKeeping = convertView.findViewById(R.id.llTimekeepingItem);

        TimeKeepingViewModel timekeeping = data.get(position);
        tvDate.setText(timekeeping.getDate());
        tvName.setText(timekeeping.getWorker_name());
        tvWorkId.setText( String.valueOf(timekeeping.getWorker_id()));

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeleteDialog(timekeeping);
            }
        });
        llTimeKeeping.setOnClickListener(new View.OnClickListener() {
            Bundle bundle = new Bundle();


            @Override
            public void onClick(View view) {
                bundle.putSerializable(Constant.TIME_KEEPING,timekeeping);
                Navigation.findNavController(parent).navigate(R.id.action_listTimeKeeping_to_ListTimeKeepingDatail, bundle);
            }
        });

        return convertView;
    }
    private void setEvent()
    {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void openDeleteDialog(TimeKeepingViewModel timeKeeping)
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_time_keeeping);
        Window window = dialog.getWindow();
        if(window == null)
            return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);

        dialog.setCancelable(true);

        TextView tvName  = dialog.findViewById(R.id.tvWorkerdtk);
        TextView tvDate = dialog.findViewById(R.id.tvDatedtk);
        Button  btnConfirmDelete = dialog.findViewById(R.id.btnConfirmDeleteTimeKeeping);
        Button btnCancelDelete = dialog.findViewById(R.id.btnCancelDeleteTimeKeeping);
        tvName.setText(timeKeeping.getWorker_name());
        tvDate.setText(timeKeeping.getDate());
        TimeKeepingDatabase timeKeepingDatabase = new TimeKeepingDatabase(getContext());
        TimeKeepingDetailDatabase timeKeepingDetailDatabase = new TimeKeepingDetailDatabase(getContext());

        btnConfirmDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(timeKeepingDetailDatabase.read(timeKeeping.getId()).size() == 0)
                {
                    timeKeepingDatabase.delete(timeKeeping.getId());
                    dialog.dismiss();

                    for (TimeKeepingViewModel timeKeepingViewModel: data
                    ) {
                        if(timeKeepingViewModel.getId() == timeKeeping.getId())
                            data.remove(timeKeepingViewModel);
                    }

                    notifyDataSetChanged();
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(context, "Không thể xóa", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}
