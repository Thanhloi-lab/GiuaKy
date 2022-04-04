package com.example.giuaky.time_keeping;

import java.util.Date;

public class TimeKeepingViewModel {
    private int id ;
    private Date date ;
    private int worker_id;
    private String worker_name;

    public TimeKeepingViewModel() {
    }

    public TimeKeepingViewModel(int id, Date date, int worker_id, String worker_name) {
        this.id = id;
        this.date = date;
        this.worker_id = worker_id;
        this.worker_name = worker_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
