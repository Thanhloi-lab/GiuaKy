package com.example.giuaky.time_keeping;

import java.util.Date;

public class TimeKeepingDetailViewModel {
    private int time_keeping_id ;
    private int product_id;
    private int waste;
    private int finish_product;

    private String product_name;
    private String worker_name;
    private int worker_id;
    private Date date;

    public TimeKeepingDetailViewModel() {
    }

    public TimeKeepingDetailViewModel(int time_keeping_id, int product_id, int waste, int finish_product, String product_name, String worker_name, int worker_id, Date date) {
        this.time_keeping_id = time_keeping_id;
        this.product_id = product_id;
        this.waste = waste;
        this.finish_product = finish_product;
        this.product_name = product_name;
        this.worker_name = worker_name;
        this.worker_id = worker_id;
        this.date = date;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
