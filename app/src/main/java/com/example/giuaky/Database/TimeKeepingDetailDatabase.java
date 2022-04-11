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

public class TimeKeepingDetailDatabase extends SQLiteOpenHelper {
    public TimeKeepingDetailDatabase(@Nullable Context context) {
        super(context,"CHAMCONG", null, 1 );
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table CHITIETCC (MACC integer ,MASP text, SOTP int, SOPP int,  PRIMARY KEY (MACC, MASP),FOREIGN KEY (MACC) REFERENCES CHAMCONG(MACC),FOREIGN KEY (MASP) REFERENCES SANPHAM(MASP))";
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

    public boolean checkExist(int timeKeeping_id , int product_id )
    {
        String sql = "select * from  CHITIETCC as cc where cc.MASP = ? and cc.MACC = ?";
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(sql,
                new String[]{ product_id  + "", timeKeeping_id + ""}

        );

        if(cursor.moveToFirst()){
            {
                database.close();
                return true;
            }}
        else
        {
            database.close();
            return false;
        }


    }

    public ArrayList<TimeKeepingDetailViewModel> read(int timekeepingId){
        ArrayList<TimeKeepingDetailViewModel> data = new ArrayList<>();

        String sql = "select sp.MASP , sp.TENSP , cc.SOTP, cc.SOPP , cc.MACC from SANPHAM as sp, CHITIETCC as cc where cc.MASP = sp.MASP and cc.MACC = ?";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,
                new String[]{timekeepingId  + ""}

        );

        if(cursor.moveToFirst()){
            do{
                TimeKeepingDetailViewModel timeKeepingViewModel = new TimeKeepingDetailViewModel();
                timeKeepingViewModel.setProduct_id(cursor.getInt(0));
                timeKeepingViewModel.setProduct_name(cursor.getString(1));
                timeKeepingViewModel.setFinish_product(cursor.getInt(2));
                timeKeepingViewModel.setWaste(cursor.getInt(3));
                timeKeepingViewModel.setTime_keeping_id(cursor.getInt(4));
                data.add(timeKeepingViewModel);
            }while (cursor.moveToNext());
        }
        database.close();
        return data;
    }

}
