package com.example.giuaky.time_keeping;

import java.util.Date;

public class Timekeeping {
    private int maCC;
    private String ngayCC;
    private int maCN;
    public Timekeeping() {
    }

    public Timekeeping(int id, String date, int worker_id) {
        this.maCC = id;
        this.ngayCC = date;
        this.maCN = worker_id;
    }

    public int getMaCC() {
        return maCC;
    }

    public void setMaCC(int maCC) {
        this.maCC = maCC;
    }

    public String getNgayCC() {
        return ngayCC;
    }

    public void setNgayCC(String ngayCC) {
        this.ngayCC = ngayCC;
    }

    public int getMaCN() {
        return maCN;
    }

    public void setMaCN(int maCN) {
        this.maCN = maCN;
    }
}
