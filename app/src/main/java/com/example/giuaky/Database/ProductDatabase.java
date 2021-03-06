package com.example.giuaky.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.giuaky.product.Product;
import com.example.giuaky.statistic.TimeKeepingDetail;

import java.util.ArrayList;

public class ProductDatabase extends SQLiteOpenHelper {
    public ProductDatabase(@Nullable Context context) {
        super(context, "CHAMCONG", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "Create table SANPHAM (MASP integer primary key autoincrement, TENSP text, DONGIA real)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addProduct(Product product){
        String sql = "insert into SANPHAM values(?,?,?)";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{null,product.getTenSP(),  product.getGia()+""});
        database.close();
    }



    public void editProduct(Product product, int maSP){
        String sql = "update SANPHAM set TENSP=?, DONGIA=? where MASP=?";
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql, new String[]{product.getTenSP(), product.getGia()+"", maSP+""});
        database.close();
    }

    public int deleteProduct(Product product){
        if(cancelDeleteProduct(product.getMaSP())!=0)
        {
            return 1;
        }
        else {
            String sql = "delete from SANPHAM where MASP=?";
            SQLiteDatabase database = getWritableDatabase();
            database.execSQL(sql, new String[]{product.getMaSP() + ""});
            database.close();
            return 2;
        }
    }

    public Product getProductById(int maSP){
        String sql = "select * from SANPHAM where MASP = ?";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, new String[]{maSP+""});
        Product product=new Product();
        if(cursor.moveToFirst()){
            product.setMaSP(cursor.getInt(0));
            product.setTenSP(cursor.getString(1));
            product.setGia(cursor.getFloat(2));

        }
        database.close();
        return product;
    }

    public ArrayList<Product> readProduct(){
        ArrayList<Product> data = new ArrayList<>();
        String sql = "select * from SANPHAM";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Product product=new Product();
                product.setMaSP(cursor.getInt(0));
                product.setTenSP(cursor.getString(1));
                product.setGia(cursor.getFloat(2));
                data.add(product);
            }while (cursor.moveToNext());
        }
        database.close();
        return data;
    }


    public int cancelDeleteProduct(int maSP){

        String sql = "select * from CHITIETCC where CHITIETCC.MASP= "+maSP+" ";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        int i=0;
        if(cursor.moveToFirst()){
            do{
               i++;
            }while (cursor.moveToNext());
        }
        database.close();
        return i;
    }
}
