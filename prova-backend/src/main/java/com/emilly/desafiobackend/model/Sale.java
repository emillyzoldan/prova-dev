package com.emilly.desafiobackend.model;

import java.util.ArrayList;
import java.util.List;

public class Sale {

    private final Integer SALE_FORMAT_ID = 003;
    private Integer saleId;
    private List<SaleDetails> sales = new ArrayList<>();
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SaleDetails> getSales() {
        return sales;
    }

    public void setSales(List<SaleDetails> sales) {
        this.sales = sales;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "SALE_FORMAT_ID=" + SALE_FORMAT_ID +
                ", saleId=" + saleId +
                ", sales=" + sales +
                ", name='" + name + '\'' +
                '}';
    }
}
