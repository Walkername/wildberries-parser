package ru.wildberries.analytics.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductPriceDTO {

    @JsonProperty("basic")
    private int basic;

    @JsonProperty("product")
    private int product;

    public int getBasic() {
        return basic;
    }

    public void setBasic(int basic) {
        this.basic = basic;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "ProductPriceDTO{" +
                "basic=" + basic +
                ", product=" + product +
                '}';
    }
}
