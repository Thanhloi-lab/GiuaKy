package com.example.giuaky.worker;

import java.io.Serializable;

public class Worker implements Serializable {
    private String maCN;
    private String hoCN;
    private String tenCN;
    private String phanXuong;

    public Worker(String maCN, String hoCN, String tenCN, String phanXuong) {
        this.maCN = maCN;
        this.hoCN = hoCN;
        this.tenCN = tenCN;
        this.phanXuong = phanXuong;
    }

    public Worker() {
    }

    public String getMaCN() {
        return maCN;
    }

    public void setMaCN(String maCN) {
        this.maCN = maCN;
    }

    public String getHoCN() {
        return hoCN;
    }

    public void setHoCN(String hoCN) {
        this.hoCN = hoCN;
    }

    public String getTenCN() {
        return tenCN;
    }

    public void setTenCN(String tenCN) {
        this.tenCN = tenCN;
    }

    public String getPhanXuong() {
        return phanXuong;
    }

    public void setPhanXuong(String phanXuong) {
        this.phanXuong = phanXuong;
    }
}
