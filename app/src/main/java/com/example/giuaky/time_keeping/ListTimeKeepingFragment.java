package com.example.giuaky.time_keeping;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
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

import com.example.giuaky.Database.TimeKeepingDatabase;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.worker.Worker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListTimeKeepingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListTimeKeepingFragment extends Fragment {


    public ListTimeKeepingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListTimeKeepingFragment.
     */

    Calendar dateSelected = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    TimeKeepingAdapter adapter ;
    ArrayList<TimeKeepingViewModel> data = new ArrayList<TimeKeepingViewModel>();
    ListView listView;
    Spinner spFactory, spWorker;
    EditText txtDate;
    WorkerDatabase workerDatabase ;
    TimeKeepingDatabase timeKeepingDatabase;
    Button btnAdd,tbnOpenCalenderftk;
    View view;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

    // TODO: Rename and change types and number of parameters
    public static ListTimeKeepingFragment newInstance(String param1, String param2) {
        ListTimeKeepingFragment fragment = new ListTimeKeepingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        navController.clearBackStack(R.id.listTimekeepingFragment);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_list_time_keeping, container, false);
        setControl();
        setEvent();
        return view;
    }
    public void setControl()
    {
        listView = view.findViewById(R.id.lvTimeKeepingftk);
        spFactory = view.findViewById(R.id.spFactoroyftk);
        spWorker = view.findViewById(R.id.spWorkerftk);
        txtDate = view.findViewById(R.id.tvDateftk);
        btnAdd = view.findViewById(R.id.addTimekeeping);
        tbnOpenCalenderftk = view.findViewById((R.id.openCalenderftk));
        timeKeepingDatabase = new TimeKeepingDatabase(getContext());
        LoadTimekeeping();

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

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDateTimeField(txtDate) ;
            }
        });
        spFactory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<Worker> workers = workerDatabase.read();
                int phanXuong = Integer.parseInt(spFactory.getSelectedItem().toString());
                ArrayList<Worker> newWorker  = new ArrayList<Worker>();
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
        txtDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                LoadTimekeeping();
            }
        });
        tbnOpenCalenderftk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar newCalendar = dateSelected;

                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        dateSelected.set(year, monthOfYear, dayOfMonth, 0, 0);
                        txtDate.setText(dateFormatter.format(dateSelected.getTime()));
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
                txtDate.setText(dateFormatter.format(dateSelected.getTime()));
            }
        });
        spWorker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                LoadTimekeeping();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void LoadTimekeeping()
    {
        int workerId =  getCurrentWorkerId();
        data.clear();
        data.addAll(timeKeepingDatabase.read(txtDate.getText().toString(),workerId));
        adapter = new TimeKeepingAdapter(getContext(),R.layout.layout_timekeeping_detail,data);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }
    private int getCurrentWorkerId()
    {
        Worker worker = (Worker)spWorker.getSelectedItem();
        if(worker != null)
            return  worker.getMaCN();
        return 0;
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
                LoadTimekeeping();

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