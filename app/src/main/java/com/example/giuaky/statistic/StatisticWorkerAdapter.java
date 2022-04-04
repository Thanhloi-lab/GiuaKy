package com.example.giuaky.statistic;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.print.PDFPrint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giuaky.Database.StatisticDatabase;
import com.example.giuaky.Database.WorkerDatabase;
import com.example.giuaky.MainActivity;
import com.example.giuaky.R;
import com.example.giuaky.worker.Worker;
import com.tejpratapsingh.pdfcreator.utils.PDFUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticWorkerAdapter extends RecyclerView.Adapter<StatisticWorkerAdapter.WorkerViewHolder> implements Filterable {
    Context context;
    ArrayList<Worker> data;
    ArrayList<Worker> dataOld;
    WorkerViewHolder holder;
    StatisticDatabase statisticDatabase;

    public class WorkerViewHolder extends RecyclerView.ViewHolder{

        public TextView tvMaCN;
        public TextView tvHo ;
        public TextView tvTen;
        public TextView tvPhanXuong ;

        public Button printSalaryButton;
        public Button salaryDetailButton;


        public WorkerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaCN = (TextView)itemView.findViewById(R.id.tvMaCN);
            tvHo = (TextView)itemView.findViewById(R.id.tvFirstName);
            tvTen = (TextView)itemView.findViewById(R.id.tvLastName);
            tvPhanXuong = (TextView)itemView.findViewById(R.id.tvFactoryId);

            printSalaryButton = itemView.findViewById(R.id.printSalaryButton);
            salaryDetailButton = itemView.findViewById(R.id.salaryDetailButton);


        }

    }

    public StatisticWorkerAdapter(Context context, ArrayList<Worker> data) {
        this.data = data;
        this.dataOld = data;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statistic_worker_info_item, parent, false);
        holder = new WorkerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerViewHolder holder, int position) {
        Worker wk = data.get(position);
        if(wk==null) return;

        holder.tvMaCN.setText(wk.getMaCN()+"");
        holder.tvHo.setText(wk.getHoCN());
        holder.tvTen.setText(wk.getTenCN());
        holder.tvPhanXuong.setText(wk.getPhanXuong()+"");
        holder.printSalaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeleteDialog(Gravity.CENTER,holder.getAdapterPosition());
            }
        });
        holder.salaryDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WorkerTimekeepingActivity.class);
                String tenCongNhan = data.get(holder.getAdapterPosition()).getHoCN() + " " + data.get(holder.getAdapterPosition()).getTenCN();
                intent.putExtra("maCongNhan", data.get(holder.getAdapterPosition()).getMaCN());
                intent.putExtra("tenCongNhan", tenCongNhan);
                context.startActivity(intent);
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

    private boolean checkPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(context, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions((MainActivity)context, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }

        return true;
    }

    private void openDeleteDialog(int gravity,int position)
    {
        final Dialog dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.print_report_dialog);

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

        TextView maCongNhanTextView=dialog.findViewById(R.id.maCongNhanTextView);
        maCongNhanTextView.setText(data.get(position).getMaCN() + "");

        Button cancelButton = dialog.findViewById(R.id.cancel_print_button);
        Button printButton = dialog.findViewById(R.id.print_report_button);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        printButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermissions()){
                    makePdf(position);
                    Toast.makeText(context, "Pdf đã được tạo",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(context, "Xin hãy cấp quyền",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void makePdf(int position){

        Worker worker = data.get(position);
        File file = new File(Environment.getExternalStorageDirectory(),"Bảng lương công nhân "+ worker.getMaCN() + "-" + new Date().toString() + ".pdf");

        String thongTin = String.format(" <div>\n" +
                "        <p>Mã công nhân : %s</p>\n" +
                "        <p>Họ và tên: %s</p>\n" +
                "        <p>Phân xưởng: %s</p>\n" +
                "    </div>\n",worker.getMaCN(), worker.getHoCN() + " " + worker.getTenCN(),worker.getPhanXuong());

        ArrayList<WorkerProductReport> reports = getProductReport(worker.getMaCN());

        String reportString = "";
        double tongTien = 0;
        for (WorkerProductReport report:reports
             ) {
            double thanhTien = (report.getTienCong()*report.getSoThanhPham())-((report.getTienCong()/2)*report.getSoPhePham());
            tongTien += thanhTien;
            reportString += String.format(
                    "<tr>\n" +
                    "    <td>%s</td>\n" +
                    "    <td>%s</td>\n" +
                    "    <td>%s</td>\n" +
                    "    <td>%s</td>\n" +
                    "    <td>%s</td>\n" +
                    "    <td>%s</td>\n" +
                    "    <td>%s</td>\n" +
                    "  </tr>\n",
                    report.getMaChamCong(),
                    report.getNgayChamCong(),
                    report.getTenSanPham(),
                    report.getSoPhePham(),
                    report.getSoThanhPham(),
                    report.getTienCong(),
                    thanhTien
            );
        }

        String table = "<table>\n" +
                "        <tr>\n" +
                "            <th>Mã chấm công</th>\n" +
                "            <th>Ngày chấm công</th>\n" +
                "            <th>Tên sảm phẩm</th>\n" +
                "            <th>Số Thành Phẩm</th>\n" +
                "            <th>Số Phế phẩm</th>\n" +
                "            <th>Tiền công</th>\n" +
                "            <th>Thành tiền</th>\n" +
                "        </tr>\n" +
                reportString +
                "    </table>\n";

        String tongTienText = String.format("<div>\n" +
                "<p>Tổng tiền: %s</p>"+
                "</div>\n",tongTien);

        PDFUtil.generatePDFFromHTML(context.getApplicationContext(), file, " <!DOCTYPE html>\n" +
                "<html>\n" +
                "<body style=\"width: 1000px; transform: rotate(90deg); margin-top:500px; \">\n" +
                "\n" +
                "<h1>Bảng tính lương</h1>\n" +
                thongTin +
                table +
                tongTienText +
                "\n" +

                "</body>\n" +
                "</html> ", new PDFPrint.OnPDFPrintListener() {
            @Override
            public void onSuccess(File file) {

            }

            @Override
            public void onError(Exception exception) {
                exception.printStackTrace();
            }
        });



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

    private ArrayList<WorkerProductReport> getProductReport(int maCongNhan){
        ArrayList<WorkerProductReport> workerProductReports = new ArrayList<>();
        statisticDatabase = new StatisticDatabase(context);
        workerProductReports.addAll(statisticDatabase.getWorkerProductReport(maCongNhan));

        return  workerProductReports;
    }

}
