package com.example.giuaky.worker;

import java.io.Serializable;

public class Worker implements Serializable {
    private int maCN;
    private String hoCN;
    private String tenCN;
    private int phanXuong;

    public Worker(int maCN, String hoCN, String tenCN, int phanXuong) {
        this.maCN = maCN;
        this.hoCN = hoCN;
        this.tenCN = tenCN;
        this.phanXuong = phanXuong;
    }

    public Worker() {
    }

    public int getMaCN() {
        return maCN;
    }

    public void setMaCN(int maCN) {
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

    public int getPhanXuong() {
        return phanXuong;
    }

    public void setPhanXuong(int phanXuong) {
        this.phanXuong = phanXuong;
    }
}
