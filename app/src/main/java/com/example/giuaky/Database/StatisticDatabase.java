package com.example.giuaky.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.giuaky.statistic.TimeKeepingDetail;
import com.example.giuaky.statistic.WorkerChartData;
import com.example.giuaky.statistic.WorkerProductReport;
import com.example.giuaky.statistic.WorkerTimeKeeping;
import com.example.giuaky.worker.Worker;

import java.util.ArrayList;

public class StatisticDatabase extends SQLiteOpenHelper {
    public StatisticDatabase(@Nullable Context context) {
        super(context, "CHAMCONG", null, 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table CONGNHAN (MACN integer primary key autoincrement, HOCN text, TENCN text, PHANXUONG integer)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void add(Worker worker){
        String sql = "insert into CONGNHAN values(?,?,?)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{worker.getHoCN(), worker.getTenCN(), worker.getPhanXuong()+""});
        database.close();
    }


    public ArrayList<WorkerChartData> getCharData(){
        ArrayList<WorkerChartData> data = new ArrayList<>();
        String sql = "SELECT HOCN, TENCN , SUM(SOTP)\n" +
                "from CHAMCONG, CHITIETCC, SANPHAM, CONGNHAN " +
                "WHERE CHAMCONG.MACC = CHITIETCC.MACC AND CHITIETCC.MASP = SANPHAM.MASP AND CHAMCONG.MACN = CONGNHAN.MACN " +
                " GROUP BY CONGNHAN.MACN";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                WorkerChartData chartData = new WorkerChartData();
                chartData.setName(cursor.getString(0) + " " + cursor.getString(1));
                chartData.setAmountOfProduct(cursor.getInt(2));
                data.add(chartData);
            }while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<WorkerProductReport> getWorkerProductReport(int maCongNhan){
        ArrayList<WorkerProductReport> data = new ArrayList<>();
        String sql = "SELECT CHAMCONG.MACC, NGAYCC, TENSP, SOTP, SOPP, DONGIA " +
                "from CHAMCONG, CHITIETCC, SANPHAM " +
                "WHERE CHAMCONG.MACC = CHITIETCC.MACC AND CHITIETCC.MASP = SANPHAM.MASP AND CHAMCONG.MACN = " + maCongNhan;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                WorkerProductReport workerProductReport = new WorkerProductReport();
                workerProductReport.setMaChamCong(cursor.getInt(0));
                workerProductReport.setNgayChamCong(cursor.getString(1));
                workerProductReport.setTenSanPham(cursor.getString(2));
                workerProductReport.setSoThanhPham(cursor.getInt(3));
                workerProductReport.setSoPhePham(cursor.getInt(4));
                workerProductReport.setTienCong(cursor.getInt(5));
                data.add(workerProductReport);
            }while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

    public ArrayList<WorkerTimeKeeping> getWorkerTimeKeeping(int maCongNhan){
        ArrayList<WorkerTimeKeeping> data = new ArrayList<>();
        String sql = "SELECT CHAMCONG.MACC, NGAYCC,  SUM(DONGIA*SOTP-DONGIA/2*SOPP) as TONGTIEN " +
                " from CHAMCONG, CHITIETCC, SANPHAM " +
                " WHERE CHAMCONG.MACC = CHITIETCC.MACC AND CHITIETCC.MASP = SANPHAM.MASP AND CHAMCONG.MACN = " + maCongNhan + " " +
                "GROUP BY CHAMCONG.MACC" ;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                WorkerTimeKeeping workerTimeKeeping = new WorkerTimeKeeping();
                workerTimeKeeping.setMaChamCong(cursor.getInt(0));
                workerTimeKeeping.setNgayChamCong(cursor.getString(1));
                workerTimeKeeping.setTongTienLuong(cursor.getInt(2));
                data.add(workerTimeKeeping);
            }while (cursor.moveToNext());
        }
        database.close();
        return data;
    }
    public ArrayList<TimeKeepingDetail> getTimeKeepingDetail(int maCongNhan, int maChamCong){
        ArrayList<TimeKeepingDetail> data = new ArrayList<>();
        String sql = "SELECT SANPHAM.MASP, SANPHAM.TENSP, SANPHAM.DONGIA, CHITIETCC.SOTP, CHITIETCC.SOPP " +
                "    from CHAMCONG, CHITIETCC, SANPHAM" +
                "    WHERE CHAMCONG.MACC = CHITIETCC.MACC AND CHITIETCC.MASP = SANPHAM.MASP " +
                "    AND CHAMCONG.MACN = " + maCongNhan + " " +
                "    AND CHAMCONG.MACC = " + maChamCong ;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                TimeKeepingDetail timeKeepingDetail = new TimeKeepingDetail();
                timeKeepingDetail.setMaSanPham(cursor.getInt(0));
                timeKeepingDetail.setTenSanPham(cursor.getString(1));
                timeKeepingDetail.setDonGia(cursor.getInt(2));
                timeKeepingDetail.setSoThanhPham(cursor.getInt(3));
                timeKeepingDetail.setSoPhePham(cursor.getInt(4));
                data.add(timeKeepingDetail);
            }while (cursor.moveToNext());
        }
        database.close();
        return data;
    }


}
