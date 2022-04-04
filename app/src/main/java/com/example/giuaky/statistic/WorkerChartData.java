package com.example.giuaky.statistic;

public class WorkerStatistic {
    private String id;
    private String name;
    private int amountOfProduct;

    public WorkerStatistic(String id, String name, int amountOfProduct) {
        this.id = id;
        this.name = name;
        this.amountOfProduct = amountOfProduct;
    }

    public WorkerStatistic() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
