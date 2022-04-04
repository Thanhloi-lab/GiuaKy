package com.example.giuaky.statistic;

public class WorkerProductReport {
    private int maChamCong;
    private String ngayChamCong;
    private String maSanPham;
    private String tenSanPham;
    private int soThanhPham;
    private int soPhePham;
    private double tienCong;

    public WorkerProductReport(int maChamCong, String ngayChamCong, String maSanPham, String tenSanPham, int soThanhPham, int soPhePham, double tienCong) {
        this.maChamCong = maChamCong;
        this.ngayChamCong = ngayChamCong;
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.soThanhPham = soThanhPham;
        this.soPhePham = soPhePham;
        this.tienCong = tienCong;
    }

    public WorkerProductReport() {
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

    public String getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoThanhPham() {
        return soThanhPham;
    }

    public void setSoThanhPham(int soThanhPham) {
        this.soThanhPham = soThanhPham;
    }

    public int getSoPhePham() {
        return soPhePham;
    }

    public void setSoPhePham(int soPhePham) {
        this.soPhePham = soPhePham;
    }

    public double getTienCong() {
        return tienCong;
    }

    public void setTienCong(double tienCong) {
        this.tienCong = tienCong;
    }
}
