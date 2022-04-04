package com.example.giuaky.statistic;

public class TimeKeepingDetail {
    private int maSanPham;
    private String tenSanPham;
    private double donGia;
    private int soThanhPham;
    private int soPhePham;

    public TimeKeepingDetail(int maSanPham, String tenSanPham, double donGia, int soThanhPham, int soPhePham) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.donGia = donGia;
        this.soThanhPham = soThanhPham;
        this.soPhePham = soPhePham;
    }

    public TimeKeepingDetail() {
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
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
}
