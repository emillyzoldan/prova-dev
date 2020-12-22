package com.emilly.desafiobackend.model;

public class Costumer {

    private final Integer COSTUMER_FORMAT_ID = 002;
    private long cnpj;
    private String name;
    private String businessArea;

    public Costumer() {
    }

    public long getCnpj() {
        return cnpj;
    }

    public void setCnpj(long cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    @Override
    public String toString() {
        return "Costumer{" +
                "COSTUMER_FORMAT_ID=" + COSTUMER_FORMAT_ID +
                ", cnpj=" + cnpj +
                ", name='" + name + '\'' +
                ", businessArea='" + businessArea + '\'' +
                '}';
    }
}
