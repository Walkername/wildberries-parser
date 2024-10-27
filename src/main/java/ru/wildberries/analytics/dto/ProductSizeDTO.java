package ru.wildberries.analytics.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSizeDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("origName")
    private String origName;

    @JsonProperty("price")
    private ProductPriceDTO price;

    @JsonProperty("rank")
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

    public ProductPriceDTO getPrice() {
        return price;
    }

    public void setPrice(ProductPriceDTO price) {
        this.price = price;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "ProductSizeDTO{" +
                "name='" + name + '\'' +
                ", origName='" + origName + '\'' +
                ", price=" + price +
                ", rank=" + rank +
                '}';
    }
}
