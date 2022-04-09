package com.example.giuaky.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.giuaky.time_keeping.TimeKeepingDetail;
import com.example.giuaky.time_keeping.TimeKeepingDetailViewModel;
import com.example.giuaky.time_keeping.TimeKeepingViewModel;
import com.example.giuaky.time_keeping.Timekeeping;

import java.util.ArrayList;

public class TimeKeepinDetailDatabase extends SQLiteOpenHelper {
    public TimeKeepinDetailDatabase(@Nullable Context context) {
        super(context,"CHITIETCC", null, 1 );
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table CHITIETCC (MACC integer ,MASP text, SOTP int, SOPP int,  PRIMARY KEY (MACC, MASP))";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void add(TimeKeepingDetail timekeeping)
    {
        String sql = "insert into CHITIETCC(MACC,MASP,SOTP,SOPP) values(?,?,?,?)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{timekeeping.getTime_keeping_id() + "", timekeeping.getProduct_id() + "",
                        timekeeping.getFinish_product() + "", timekeeping.getWaste() + ""});
        database.close();
    }
    public void update(TimeKeepingDetail timekeeping)
    {
        String sql = "Update CHITIETCC set SOTP = ? ,SOPP = ? where MACC = ? and MASP = ? ";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{timekeeping.getFinish_product() + "", timekeeping.getWaste() + "",
                timekeeping.getTime_keeping_id() + "", timekeeping.getProduct_id() + ""});
        database.close();
    }
    public void delete(int timeKeeping_id , int product_id)
    {
        String sql = "delete from  CHITIETCC where MACC = ? and MASP = ? ";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{
                timeKeeping_id + "", product_id + ""});
        database.close();
    }

//    public ArrayList<TimeKeepingDetailViewModel> read(String date, int workerId){
//        ArrayList<TimeKeepingViewModel> data = new ArrayList<>();
//
//        String sql = "select cn.MACN , cn.HOCN , cn.TENCN, cc.MACC, cc.NGAYCC , cn.phanXuong from CONGNHAN as cn,CHAMCONG as cc where cn.MACN = cc.MACN "
//                + String.format("and (cn.MACN = %d or %d = 0) and (cc.NGAYCC = '%s' or '%s' = '' )",workerId,workerId,date,date );
//        SQLiteDatabase database = getReadableDatabase();
//        Cursor cursor = database.rawQuery(sql,
//                null
//
//        );
//
//        if(cursor.moveToFirst()){
//            do{
//                TimeKeepingViewModel timeKeepingViewModel = new TimeKeepingViewModel();
//                timeKeepingViewModel.setWorker_id(cursor.getInt(0));
//                timeKeepingViewModel.setWorker_name(cursor.getString(1) + " " + cursor.getString(2));
//                timeKeepingViewModel.setId(cursor.getInt(3));
//                timeKeepingViewModel.setDate(cursor.getString(4));
//                timeKeepingViewModel.setFactory_id(cursor.getString(5));
//                data.add(timeKeepingViewModel);
//            }while (cursor.moveToNext());
//        }
//        database.close();
//        return data;
//    }

}
