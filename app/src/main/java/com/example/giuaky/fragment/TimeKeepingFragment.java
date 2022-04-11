package com.example.giuaky.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.giuaky.Database.TimeKeepingDatabase;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.time_keeping.TimeKeepingAdapter;
import com.example.giuaky.time_keeping.TimeKeepingViewModel;
import com.example.giuaky.time_keeping.Timekeeping;
import com.example.giuaky.worker.Worker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimeKeepingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimeKeepingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Calendar dateSelected = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    TimeKeepingAdapter adapter ;
    ArrayList<TimeKeepingViewModel> data;
    ListView listView;
    Spinner spFactory, spWorker;
    TextView tvDate;
    WorkerDatabase workerDatabase ;
    TimeKeepingDatabase timeKeepingDatabase;
    Button btnAdd ;
    View view;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimeKeepingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimeKeepingFragment newInstance(String param1, String param2) {
        TimeKeepingFragment fragment = new TimeKeepingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private ArrayList<TimeKeepingViewModel> getData()
    {
        ArrayList<TimeKeepingViewModel> arrayList = new ArrayList<>();
        arrayList.add(new TimeKeepingViewModel(1,new Date(),1,"Nguyen Thanh Duy"));
        arrayList.add(new TimeKeepingViewModel(2,new Date(),2,"Tran Quan Duc"));
        return arrayList
;    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_time_keeping, container, false);
        setControl();
        setEvent();
        return view;
    }
    public void setControl()
    {
        listView = view.findViewById(R.id.lvTimeKeepingftk);
        spFactory = view.findViewById(R.id.spFactoroyftk);
        spWorker = view.findViewById(R.id.spWorkerftk);
        tvDate = view.findViewById(R.id.tvDateftk);
        btnAdd = view.findViewById(R.id.addTimekeeping);

        adapter = new TimeKeepingAdapter(getContext(),R.layout.layout_timekeeping_detail,getData());
        listView.setAdapter(adapter);
        workerDatabase = new WorkerDatabase(getContext());
        ArrayList<Worker> workers = workerDatabase.read();
        ArrayAdapter workerAdater =  new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,workers);
        spWorker.setAdapter(workerAdater);

        ArrayAdapter factoryAdpater  = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,getFactoroy(workers));
        spFactory.setAdapter(factoryAdpater);
    }
    private void setEvent()
    {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddDialog(Gravity.CENTER);
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTimeField(tvDate) ;
            }
        });
        spFactory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<Worker> workers = workerDatabase.read();
                int phanXuong = Integer.parseInt(spFactory.getSelectedItem().toString());
                ArrayList<Worker> newWorker  = new ArrayList<>();
                for (Worker worker: workers
                     ) {
                    if(worker.getPhanXuong() == phanXuong)
                        newWorker.add(worker);
                }

                ArrayAdapter workerAdater =  new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,newWorker);
                spWorker.setAdapter(workerAdater);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    private void setDateTimeField(TextView tv) {
        Calendar newCalendar = dateSelected;

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0);
                tv.setText(dateFormatter.format(dateSelected.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        tv.setText(dateFormatter.format(dateSelected.getTime()));

    }

    public void openAddDialog(int gravity)
    {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_time_keeping);
        Window window = dialog.getWindow();
        if(window == null)
            return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        dialog.setCancelable(true);

        Spinner spFactory, spWorker;
        TextView tvDateAdd;
        Button btnsave;
        spFactory = dialog.findViewById(R.id.spFactoroyatk);
        spWorker = dialog.findViewById(R.id.spWorkeratk);
        tvDateAdd = dialog.findViewById(R.id.tvDateatk);
        btnsave = dialog.findViewById(R.id.saveatk);


        timeKeepingDatabase = new TimeKeepingDatabase(getContext());
        workerDatabase = new WorkerDatabase(getContext());

        ArrayList<Worker> workers = workerDatabase.read();
        ArrayAdapter workerAdater =  new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,workers);
        spWorker.setAdapter(workerAdater);

        ArrayAdapter factoryAdpater  = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,getFactoroy(workers));
        spFactory.setAdapter(factoryAdpater);

        tvDateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTimeField(tvDateAdd) ;
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timekeeping timekeeping = new Timekeeping();
                Worker worker = (Worker)spWorker.getSelectedItem();
                timekeeping.setMaCN(worker.getMaCN());
                try {
                    timekeeping.setNgayCC(tvDateAdd.getText().toString());
                }catch (Exception e)
                {
                    return;
                }

                timeKeepingDatabase.Add(timekeeping);
                dialog.dismiss();
                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();

            }
        });


        dialog.show();

    }

    ArrayList<String> getFactoroy(ArrayList<Worker> workers)
    {
        Set<String> arrayList = new HashSet<String>();
        for (Worker element : workers) {
            arrayList.add(String.valueOf(element.getPhanXuong()));
        }
        return new ArrayList<String>(arrayList);
    }

}