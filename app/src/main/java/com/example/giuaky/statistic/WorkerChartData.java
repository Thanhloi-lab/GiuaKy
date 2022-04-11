package com.example.giuaky.statistic;

public class WorkerChartData {
    private String name;
    private int amountOfProduct;

    public WorkerChartData(String id, String name, int amountOfProduct) {
        this.name = name;
        this.amountOfProduct = amountOfProduct;
    }

    public WorkerChartData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmountOfProduct() {
        return amountOfProduct;
    }

    public void setAmountOfProduct(int amountOfProduct) {
        this.amountOfProduct = amountOfProduct;
    }
}
