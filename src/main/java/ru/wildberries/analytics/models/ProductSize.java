package ru.wildberries.analytics.models;

public class ProductSize {

    private String name;

    private String origName;

    private int basicPrice;

    private int discountPrice;

    private int rank;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigName() {
        return origName;
    }

    public void setOrigName(String origName) {
        this.origName = origName;
    }

    public int getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(int basicPrice) {
        this.basicPrice = basicPrice;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "ProductSize{" +
                "name='" + name + '\'' +
                ", origName='" + origName + '\'' +
                ", basicPrice=" + basicPrice +
                ", discountPrice=" + discountPrice +
                ", rank=" + rank +
                '}';
    }
}
