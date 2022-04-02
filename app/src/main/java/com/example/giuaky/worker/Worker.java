package com.example.giuaky.worker;

import java.io.Serializable;
import java.util.Comparator;

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

    public int compareFactoryIdTo(Worker compareFruit) {

        int compareId = ((Worker) compareFruit).getPhanXuong();

        //ascending order
        return this.phanXuong - compareId;

        //descending order
        //return compareQuantity - this.quantity;

    }

    public static Comparator<Worker> WorkerFirstNameComparator = new Comparator<Worker>() {
        public int compare(Worker worker1, Worker worker2) {

            String workerName1 = worker1.getHoCN().toUpperCase();
            String workerName2 = worker2.getHoCN().toUpperCase();
            return workerName1.compareTo(workerName2);
        }
    };

    public static Comparator<Worker> WorkerLastNameComparator = new Comparator<Worker>() {
        public int compare(Worker worker1, Worker worker2) {

            String workerName1 = worker1.getTenCN().toUpperCase();
            String workerName2 = worker2.getTenCN().toUpperCase();
            return workerName1.compareTo(workerName2);
        }
    };

    public static Comparator<Worker> WorkerFactoryIdComparator = new Comparator<Worker>() {
        public int compare(Worker worker1, Worker worker2) {
            Integer factoryId1 = worker1.getPhanXuong();
            Integer factoryId2 = worker2.getPhanXuong();
            return factoryId1.compareTo(factoryId2);
        }
    };

    public static Comparator<Worker> WorkerIdComparator = new Comparator<Worker>() {
        public int compare(Worker worker1, Worker worker2) {
            Integer workerId1 = worker1.getMaCN();
            Integer workerId2 = worker2.getMaCN();
            return workerId1.compareTo(workerId2);
        }
    };

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
