package com.emilly.desafiobackend.model;

public class SaleDetails {

    private Integer itemId;
    private Integer itemQuantity;
    private Double itemPrice;
    private Integer saleId;
    private String salesmanName;

    public SaleDetails(Integer itemId, Integer itemQuantity, Double itemPrice, String salesmanName, Integer saleId) {
        this.itemId = itemId;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.saleId = saleId;
        this.salesmanName = salesmanName;
    }

    public SaleDetails() {
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(String salesmanName) {
        this.salesmanName = salesmanName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    @Override
    public String toString() {
        return "SaleDetails{" +
                "itemId=" + itemId +
                ", itemQuantity=" + itemQuantity +
                ", itemPrice=" + itemPrice +
                ", saleId=" + saleId +
                ", salesmanName='" + salesmanName + '\'' +
                '}';
    }
}
