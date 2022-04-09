package com.example.giuaky.time_keeping;


import java.io.Serializable;

public class TimeKeepingViewModel implements Serializable{
    private int id ;
    private String date ;
    private int worker_id;
    private String worker_name;
    private String factory_id;

    public TimeKeepingViewModel() {
    }

    public TimeKeepingViewModel(int id, String date, int worker_id, String worker_name, String factory_id) {
        this.id = id;
        this.date = date;
        this.worker_id = worker_id;
        this.worker_name = worker_name;
        this.factory_id = factory_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(int worker_id) {
        this.worker_id = worker_id;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public String getFactory_id() {
        return factory_id;
    }

    public void setFactory_id(String factory_id) {
        this.factory_id = factory_id;
    }
}
