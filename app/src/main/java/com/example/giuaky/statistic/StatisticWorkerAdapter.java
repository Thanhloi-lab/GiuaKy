package com.example.giuaky.worker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuaky.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorkerAdapter extends RecyclerView.Adapter<WorkerAdapter.WorkerViewHolder> implements Filterable {
    Context context;
    int resource;
    ArrayList<Worker> data;
    ArrayList<Worker> dataOld;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worker_info_item, parent, false);
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
