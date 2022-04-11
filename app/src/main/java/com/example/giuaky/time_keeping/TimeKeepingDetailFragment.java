package com.example.giuaky.time_keeping;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import com.example.giuaky.Database.ProductDatabase;
import com.example.giuaky.Database.TimeKeepingDatabase;
import com.example.giuaky.Database.TimeKeepingDetailDatabase;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.R;
import com.example.giuaky.product.Product;
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
    ProductDatabase productDatabase;
    Button btnAdd, btnBack;


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
        btnBack = view.findViewById(R.id.btnBack_to_TimeKeeping);

    }
    private void initPage()
    {
        tvDate.setText(timeKeeping.getDate());
        tvWorker.setText(timeKeeping.getWorker_name());
        tvFactory.setText(timeKeeping.getFactory_id());

        database = new TimeKeepingDetailDatabase(getContext());
        productDatabase = new ProductDatabase(getContext());

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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            }
        });
    }
    public void LoadTimeKeepingDetail()
    {
        data.clear();
        data.addAll(database.read(timeKeeping.getId()));
        adapter.notifyDataSetChanged();

    }
    public ArrayList<Product> getRemainProduct()
    {
        ArrayList<Product> products = productDatabase.readProduct();
        ArrayList<Product> remainProduct = new ArrayList<>();
        ArrayList<TimeKeepingDetailViewModel> timeKeepingDetailViewModels =  database.read(timeKeeping.getId());
        for (Product product : products
        ){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if(timeKeepingDetailViewModels.stream().filter((x) -> x.getProduct_id() == product.getMaSP()).toArray().length == 0)
                {
                    remainProduct.add(product);
                }
            }
        }
        return remainProduct;
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

        Spinner spProduct;
        TextView txtFinishProduct, txtWasteProduct;
        Button btnSave;

        spProduct = dialog.findViewById(R.id.spProductatkpd);
        txtFinishProduct = dialog.findViewById(R.id.txtFinishProductatkpd);
        txtWasteProduct = dialog.findViewById(R.id.txtWasteProductatkpd);
        btnSave = dialog.findViewById(R.id.saveatkpd);

        ArrayList<Product> products = getRemainProduct();
        ArrayAdapter productAdater =  new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,products);
        spProduct.setAdapter(productAdater);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeKeepingDetail timeKeepingDetail = new TimeKeepingDetail();
                Product product = (Product) spProduct.getSelectedItem();

                timeKeepingDetail.setTime_keeping_id(timeKeeping.getId());
                timeKeepingDetail.setFinish_product(Integer.parseInt(txtFinishProduct.getText().toString()));
                timeKeepingDetail.setWaste((Integer.parseInt(txtWasteProduct.getText().toString())));
                timeKeepingDetail.setProduct_id(product.getMaSP());

                database.add(timeKeepingDetail);
                dialog.dismiss();
                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                LoadTimeKeepingDetail();


            }
        });


        dialog.show();


    }

}