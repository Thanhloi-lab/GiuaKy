package com.example.giuaky.product;

import java.io.Serializable;
import java.util.Comparator;

public class Product implements Serializable {
    private int maSP;
    private String tenSP;
    private Float gia;

    public Product() {
    }

    public Product(int maSP, String tenSP, Float gia) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
    }

    public static Comparator<Product> ProductNameComparator = new Comparator<Product>() {
        public int compare(Product p1, Product p2) {

            String name1 = p1.getTenSP().toUpperCase();
            String name2 = p2.getTenSP().toUpperCase();
            return name1.compareTo(name2);
        }
    };

    public static Comparator<Product> ProductPriceComparator = new Comparator<Product>() {
        public int compare(Product p1, Product p2) {

            Float price1 = p1.getGia();
            Float price2 = p2.getGia();
            return price1.compareTo(price2);
        }
    };

    public static Comparator<Product> ProductIdComparator = new Comparator<Product>() {
        public int compare(Product p1, Product p2) {

            Integer temp1 = p1.getMaSP();
            Integer temp2 = p2.getMaSP();
            return temp1.compareTo(temp2);
        }
    };

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public Float getGia() {
        return gia;
    }

    public void setGia(Float gia) {
        this.gia = gia;
    }
}
