package com.example.giuaky.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.giuaky.time_keeping.Timekeeping;

import java.text.SimpleDateFormat;

public class TimeKeepingDatabase extends SQLiteOpenHelper {
    public TimeKeepingDatabase(@Nullable Context context) {
        super(context, "CHAMCONG", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table CHAMCONG (MACC integer primary key autoincrement, NGAYCC text, MACN integer)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void Add(Timekeeping timekeeping)
    {
        String sql = "insert into CHAMCONG(NGAYCC,MACN) values(?,?)";
        SQLiteDatabase database = getWritableDatabase();
        try
        {
            database.execSQL(sql, new String[]{timekeeping.getNgayCC(), timekeeping.getMaCN() + ""});
        }catch (Exception e)
        {
            System.out.println(e);
        }

        database.close();
    }
}
