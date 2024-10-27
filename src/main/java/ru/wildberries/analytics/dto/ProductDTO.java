package ru.wildberries.analytics.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("brandId")
    private int brandId;

    @JsonProperty("entity")
    private String entity;

    @JsonProperty("supplier")
    private String supplier;

    @JsonProperty("supplierId")
    private int supplierId;

    @JsonProperty("supplierRating")
    private double supplierRating;

    @JsonProperty("pics")
    private int pics;

    @JsonProperty("reviewRating")
    private double reviewRating;

    @JsonProperty("feedbacks")
    private int feedbacks;

    @JsonProperty("sizes")
    private List<ProductSizeDTO> sizes;

    @JsonProperty("totalQuantity")
    private int totalQuantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(String feedbacks) {
        this.feedbacks = Integer.parseInt(feedbacks);
    }

    public double getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(String reviewRating) {
        this.reviewRating = Double.parseDouble(reviewRating);
    }

    public int getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = Integer.parseInt(pics);
    }

    public double getSupplierRating() {
        return supplierRating;
    }

    public void setSupplierRating(String supplierRating) {
        this.supplierRating = Double.parseDouble(supplierRating);
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = Integer.parseInt(supplierId);
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setSupplierRating(double supplierRating) {
        this.supplierRating = supplierRating;
    }

    public void setPics(int pics) {
        this.pics = pics;
    }

    public void setReviewRating(double reviewRating) {
        this.reviewRating = reviewRating;
    }

    public void setFeedbacks(int feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<ProductSizeDTO> getSizes() {
        return sizes;
    }

    public void setSizes(List<ProductSizeDTO> sizes) {
        this.sizes = sizes;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
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
                '}';
    }
}
