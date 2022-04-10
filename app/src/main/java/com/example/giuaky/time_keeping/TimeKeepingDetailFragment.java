package com.example.giuaky.time_keeping;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.TimeKeepingDatabase;
import com.example.giuaky.Database.TimeKeepingDetailDatabase;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;
import com.example.giuaky.worker.Worker;

import java.util.ArrayList;


public class TimeKeepingDetailFragment extends Fragment {

    TimeKeepingViewModel timeKeeping;
    Bundle bundle;
    TextView tvFactory, tvDate, tvWorker;
    ListView lvTimeKeepingDetail;
    ArrayList<TimeKeepingDetailViewModel> data =  new ArrayList<>();
    TimeKeepingDetailAdapter adapter;
    TimeKeepingDetailDatabase database;
    Button btnAdd;

    public TimeKeepingDetailFragment() {
        // Required empty public constructor
    }
    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_time_keeping_detail, container, false);
        bundle = this.getArguments();
        timeKeeping = (TimeKeepingViewModel) bundle.getSerializable(Constant.TIME_KEEPING);
        setControl();
        initPage();
        setEvent();

        return view;
    }
    private void setControl()
    {
        tvDate = view.findViewById(R.id.tvDatetdtk);
        tvFactory = view.findViewById(R.id.tvFactoroytdtk);
        tvWorker = view.findViewById(R.id.tvWorkertdtk);
        lvTimeKeepingDetail  = view.findViewById(R.id.lvTimeKeepingDetail);
        btnAdd = view.findViewById(R.id.addTimekeepingDetail);

    }
    private void initPage()
    {
        tvDate.setText(timeKeeping.getDate());
        tvWorker.setText(timeKeeping.getWorker_name());
        tvFactory.setText(timeKeeping.getFactory_id());

        database = new TimeKeepingDetailDatabase(getContext());
        data = database.read(timeKeeping.getId());
        adapter = new TimeKeepingDetailAdapter(getContext(),R.layout.time_keeping_detail_item,data);
        lvTimeKeepingDetail.setAdapter(adapter);

    }
    private void setEvent()
    {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog();
            }
        });
    }
    public void openAddDialog()
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_time_keeping_detail);
        Window window = dialog.getWindow();
        if(window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);

        dialog.setCancelable(true);

        Spinner spFactory, spWorker;
        TextView tvDateAdd;
        Button btnsave;
        spFactory = dialog.findViewById(R.id.spFactoroyatk);
        spWorker = dialog.findViewById(R.id.spWorkeratk);
        tvDateAdd = dialog.findViewById(R.id.tvDateatk);
        btnsave = dialog.findViewById(R.id.saveatk);





//        ArrayList<Worker> workers = workerDatabase.read();
//        ArrayAdapter workerAdater =  new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,workers);
//        spWorker.setAdapter(workerAdater);
//
//        ArrayAdapter factoryAdpater  = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,getFactoroy(workers));
//        spFactory.setAdapter(factoryAdpater);
//
//        tvDateAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setDateTimeField(tvDateAdd) ;
//            }
//        });
//
//        btnsave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Timekeeping timekeeping = new Timekeeping();
//                Worker worker = (Worker)spWorker.getSelectedItem();
//                timekeeping.setMaCN(worker.getMaCN());
//                try {
//                    timekeeping.setNgayCC(tvDateAdd.getText().toString());
//                }catch (Exception e)
//                {
//                    return;
//                }
//
//                timeKeepingDatabase.Add(timekeeping);
//                dialog.dismiss();
//                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
//                LoadTimekeeping();
//
//            }
//        });


        dialog.show();


    }

}