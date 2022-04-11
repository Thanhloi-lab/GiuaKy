package com.example.giuaky.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.giuaky.time_keeping.TimeKeepingDetail;
import com.example.giuaky.time_keeping.TimeKeepingViewModel;
import com.example.giuaky.time_keeping.Timekeeping;
import com.example.giuaky.worker.Worker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TimeKeepingDatabase extends SQLiteOpenHelper {
    public TimeKeepingDatabase(@Nullable Context context) {
        super(context,"CHAMCONG", null, 1 );
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table CHAMCONG (MACC integer primary key autoincrement, NGAYCC text, MACN integer,FOREIGN KEY (MACN) REFERENCES CONGNHAN(MACN))";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void Add(Timekeeping timekeeping)
    {
        String sql = "insert into CHAMCONG(NGAYCC,MACN) values(?,?)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{timekeeping.getNgayCC(), timekeeping.getMaCN() + ""});
//        try
//        {
//
//        }catch (Exception e)
//        {
//            System.out.println(e);
//        }

        database.close();
    }
    public ArrayList<TimeKeepingViewModel> read(String date, int workerId){
        ArrayList<TimeKeepingViewModel> data = new ArrayList<>();

        String sql = "select cn.MACN , cn.HOCN , cn.TENCN, cc.MACC, cc.NGAYCC , cn.phanXuong from CONGNHAN as cn,CHAMCONG as cc where cn.MACN = cc.MACN "
                + String.format("and (cn.MACN = %d or %d = 0) and (cc.NGAYCC = '%s' or '%s' = '' )",workerId,workerId,date,date );
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,
                null

        );

        if(cursor.moveToFirst()){
            do{
                TimeKeepingViewModel timeKeepingViewModel = new TimeKeepingViewModel();
                timeKeepingViewModel.setWorker_id(cursor.getInt(0));
                timeKeepingViewModel.setWorker_name(cursor.getString(1) + " " + cursor.getString(2));
                timeKeepingViewModel.setId(cursor.getInt(3));
                timeKeepingViewModel.setDate(cursor.getString(4));
                timeKeepingViewModel.setFactory_id(cursor.getString(5));
                data.add(timeKeepingViewModel);
            }while (cursor.moveToNext());
        }
        database.close();
        return data;
    }
    public void delete(int id)
    {
        String sql = "delete from CHAMCONG  where MACC = ? ";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{id + ""});
//
        database.close();
    }
}
