package com.example.giuaky.statistic;

public class WorkerTimeKeeping {
    private int maChamCong;
    private String ngayChamCong;
    private double tongTienLuong;

    public WorkerTimeKeeping(int maChamCong, String ngayChamCong, double tongTienLuong) {
        this.maChamCong = maChamCong;
        this.ngayChamCong = ngayChamCong;
        this.tongTienLuong = tongTienLuong;
    }

    public WorkerTimeKeeping() {
    }

    public int getMaChamCong() {
        return maChamCong;
    }

    public void setMaChamCong(int maChamCong) {
        this.maChamCong = maChamCong;
    }

    public String getNgayChamCong() {
        return ngayChamCong;
    }

    public void setNgayChamCong(String ngayChamCong) {
        this.ngayChamCong = ngayChamCong;
    }

    public double getTongTienLuong() {
        return tongTienLuong;
    }

    public void setTongTienLuong(double tongTienLuong) {
        this.tongTienLuong = tongTienLuong;
    }
}
