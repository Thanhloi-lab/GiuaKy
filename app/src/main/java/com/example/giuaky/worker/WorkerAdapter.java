package com.example.giuaky.worker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuaky.Constant;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.MainActivity;
import com.example.giuaky.R;
import com.example.giuaky.template.UpdatePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder> implements Filterable {
    Context context;
    int resource;
    ArrayList<Worker> data;
    ArrayList<Worker> dataOld;
    View viewNow;
    ViewGroup parent;

    private UpdatePage editPage = new UpdatePage("THÊM CÔNG NHÂN", "Sửa", Constant.PAGE_EIDT_WORKER);

    public class WorkerViewHolder extends RecyclerView.ViewHolder{

        public TextView tvMaCN;
        public TextView tvHo ;
        public TextView tvTen;
        public TextView tvPhanXuong ;

        public Button btnSua ;
        public Button btnXoa ;

        public WorkerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaCN = (TextView)itemView.findViewById(R.id.tvMaCN);
            tvHo = (TextView)itemView.findViewById(R.id.tvFirstName);
            tvTen = (TextView)itemView.findViewById(R.id.tvLastName);
            tvPhanXuong = (TextView)itemView.findViewById(R.id.tvFactoryId);

            btnSua = (Button) itemView.findViewById(R.id.btnEditWorker);
            btnXoa = (Button)itemView.findViewById(R.id.btnDeleteWorker);
        }
    }

    public WorkerAdapter(ArrayList<Worker> data) {
        this.data = data;
        this.dataOld = data;
    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewNow = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_info_item, parent, false);
        this.parent = parent;
        return new WorkerViewHolder(viewNow);
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
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.WORKER, wk);
                bundle.putSerializable(Constant.PAGE, editPage);
                Navigation.findNavController(viewNow).navigate(R.id.action_listWorker_to_updateWorker, bundle);
            }
        });



        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(viewNow.getContext());
//                builder.setMessage("sign out");
//                builder.setTitle("sign out");
//                builder.setCancelable(false);
//                builder.setView(R.layout.delete_worker_dialog);

                final Dialog alert=new Dialog(viewNow.getContext());
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alert.setContentView(R.layout.delete_worker_dialog);

                Window window=alert.getWindow();
                if(window==null)
                {
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                WindowManager.LayoutParams winLayoutParams=window.getAttributes();
                winLayoutParams.gravity = Gravity.CENTER;
                window.setAttributes(winLayoutParams);

                Button btnCancel = alert.findViewById(R.id.btn_dialog_cancel_worker);
                Button btnDelete = alert.findViewById(R.id.btn_dialog_delete_worker);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alert.dismiss();
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(parent.getContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                        WorkerDatabase db = new WorkerDatabase(parent.getContext());
                        db.delete(wk.getMaCN());
                        dataOld.remove(wk);
                        data = dataOld;
                        notifyDataSetChanged();
                        alert.dismiss();
                    }
                });

                alert.show();
            }
        });
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
