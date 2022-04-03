package com.example.giuaky.worker;

import static android.app.PendingIntent.getActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.MainActivity;
import com.example.giuaky.R;
import com.example.giuaky.fragment.WorkerFragment;
import com.example.giuaky.template.UpdatePage;

import java.util.ArrayList;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder> implements Filterable {
    Context context;
    int resource;
    ArrayList<Worker> data;
    ArrayList<Worker> dataOld;
    private WorkerDatabase db;
    ViewGroup parent;
    WorkerAdapter adapter;

    public class WorkerViewHolder extends RecyclerView.ViewHolder{

        public TextView tvMaCN;
        public TextView tvHo ;
        public TextView tvTen;
        public TextView tvPhanXuong ;

        public Button btnSua ;
        public Button btnXoa ;

        Context context;

        public WorkerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaCN = (TextView)itemView.findViewById(R.id.tvMaCN);
            tvHo = (TextView)itemView.findViewById(R.id.tvFirstName);
            tvTen = (TextView)itemView.findViewById(R.id.tvLastName);
            tvPhanXuong = (TextView)itemView.findViewById(R.id.tvFactoryId);

            btnSua = (Button)itemView.findViewById(R.id.btnEditWorker);
            btnXoa = (Button)itemView.findViewById(R.id.btnDeleteWorker);


        }
    }

    public WorkerAdapter(Context context, int resource, ArrayList<Worker> data) {
        this.data = data;
        this.dataOld = data;
        this.context=context;
        this.resource=resource;

    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_info_item, parent, false);
        this.parent=parent;
        return new WorkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {

        Worker wk = data.get(position);
        if(wk==null) return;

        holder.tvMaCN.setText(wk.getMaCN()+"");
        holder.tvHo.setText(wk.getHoCN());
        holder.tvTen.setText(wk.getTenCN());
        holder.tvPhanXuong.setText(wk.getPhanXuong()+"");

        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(view.getContext(),UpdateWorkerActivity.class);
                UpdatePage editPage = new UpdatePage("SỬA CÔNG NHÂN", "Sửa", Constant.PAGE_EIDT_WORKER);
                intent.putExtra("page", editPage);
                intent.putExtra("MACN", String.valueOf(wk.getMaCN()));
                ((MainActivity)context).startActivityForResult(intent,2);
            }
        });

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openDeleteDialog(Gravity.CENTER,wk);

            }
        });
    }
    private void openDeleteDialog(int gravity,Worker worker)
    {
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_worker);

        Window window=dialog.getWindow();
        if(window==null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
        WindowManager.LayoutParams winLayoutParams=window.getAttributes();
        winLayoutParams.gravity=gravity;
        window.setAttributes(winLayoutParams);

        TextView etAlertWorker=dialog.findViewById(R.id.tvAlertWorkerDelete);
        etAlertWorker.setText("Bạn có chắc chắn muốn xóa công nhân với id="+worker.getMaCN()+" ?");
        Button btnKhong=dialog.findViewById(R.id.btnCancelWorker);
        Button btnCo =dialog.findViewById(R.id.btnCheckDeleteWorker);

        btnKhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db=new WorkerDatabase(parent.getContext());
                db.delete(worker);
                data=dataOld=db.read();
                notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @Override
    public int getItemCount() {
        if(data!=null){
            return  data.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if(strSearch.isEmpty()){
                    data = dataOld;
                }
                else{
                    ArrayList<Worker> list = new ArrayList<>();
                    for(Worker worker : dataOld){
                        if(worker.getTenCN().toLowerCase().contains(strSearch.toLowerCase()) ||
                            worker.getHoCN().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(worker);
                        }
                    }
                    data=list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data ;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<Worker>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
