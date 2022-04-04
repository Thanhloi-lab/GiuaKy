package com.example.giuaky.time_keeping;

public class TimeKeepingDetail {
    private int time_keeping_id ;
    private int product_id;
    private int waste;
    private int finish_product;

    public TimeKeepingDetail() {
    }

    public TimeKeepingDetail(int time_keeping_id, int product_id, int waste, int finish_product) {
        this.time_keeping_id = time_keeping_id;
        this.product_id = product_id;
        this.waste = waste;
        this.finish_product = finish_product;
    }

    public int getTime_keeping_id() {
        return time_keeping_id;
    }

    public void setTime_keeping_id(int time_keeping_id) {
        this.time_keeping_id = time_keeping_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getWaste() {
        return waste;
    }

    public void setWaste(int waste) {
        this.waste = waste;
    }

    public int getFinish_product() {
        return finish_product;
    }

    public void setFinish_product(int finish_product) {
        this.finish_product = finish_product;
    }
}
