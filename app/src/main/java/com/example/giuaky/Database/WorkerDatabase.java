package com.example.giuaky.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.giuaky.worker.Worker;

import java.util.ArrayList;

public class WorkerDatabase extends SQLiteOpenHelper {


    public WorkerDatabase(@Nullable Context context) {
        super(context, "CHAMCONG", null, 1);
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

    public void edit(Worker worker, int maCN){
        String sql = "update CONGNHAN set HOCN=?, TENCN=?, PHANXUONG=? where MACN=?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{worker.getHoCN(), worker.getTenCN(), worker.getPhanXuong()+"", maCN+""});
        database.close();
    }

    public void delete(Worker worker){
        String sql = "delete from tbMonHoc where MACN=?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{worker.getMaCN()+""});
        database.close();
    }

    public Worker getById(int maCN){
        String sql = "select * from CONGNHAN where MACN = ?";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{maCN+""});
        Worker worker = new Worker();
        if(cursor.moveToFirst()){
            worker.setMaCN(cursor.getInt(0));
            worker.setHoCN(cursor.getString(1));
            worker.setTenCN(cursor.getString(2));
            worker.setPhanXuong(cursor.getInt(3));
        }
        database.close();
        return worker;
    }

    public ArrayList<Worker> read(){
        ArrayList<Worker> data = new ArrayList<>();
        String sql = "select * from CONGNHAN";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Worker worker = new Worker();
                worker.setMaCN(cursor.getInt(0));
                worker.setHoCN(cursor.getString(1));
                worker.setTenCN(cursor.getString(2));
                worker.setPhanXuong(cursor.getInt(3));
                data.add(worker);
            }while (cursor.moveToNext());
        }
        database.close();
        return data;
    }
}
