package ru.wildberries.analytics.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "products")
public class Product {

    @Id
    private String id;

    @Indexed(unique = true)
    private int wbId;

    private String name;

    private String brand;

    private int brandId;

    private String entity;

    private String supplier;

    private int supplierId;

    private double supplierRating;

    private int pics;

    private double reviewRating;

    private int feedbacks;

    private List<ProductSize> sizes;

    private int totalQuantity;

    private List<PriceState> priceHistory;

    public String getId() {
        return id;
    }

    public int getWbId() {
        return wbId;
    }

    public void setWbId(int wbId) {
        this.wbId = wbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public double getSupplierRating() {
        return supplierRating;
    }

    public void setSupplierRating(double supplierRating) {
        this.supplierRating = supplierRating;
    }

    public int getPics() {
        return pics;
    }

    public void setPics(int pics) {
        this.pics = pics;
    }

    public double getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(double reviewRating) {
        this.reviewRating = reviewRating;
    }

    public int getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(int feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<ProductSize> getSizes() {
        return sizes;
    }

    public void setSizes(List<ProductSize> sizes) {
        this.sizes = sizes;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public List<PriceState> getPriceHistory() {
        return priceHistory;
    }

    public void setPriceHistory(List<PriceState> priceHistory) {
        this.priceHistory = priceHistory;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", wbId=" + wbId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", brandId=" + brandId +
                ", entity='" + entity + '\'' +
                ", supplier='" + supplier + '\'' +
                ", supplierId=" + supplierId +
                ", supplierRating=" + supplierRating +
                ", pics=" + pics +
                ", reviewRating=" + reviewRating +
                ", feedbacks=" + feedbacks +
                ", sizes=" + sizes +
                ", totalQuantity=" + totalQuantity +
                ", priceHistory=" + priceHistory +
                '}';
    }
}
