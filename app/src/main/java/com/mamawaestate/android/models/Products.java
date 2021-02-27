
package com.mamawaestate.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Products {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("marked_price")
    @Expose
    private Integer markedPrice;
    @SerializedName("selling_price")
    @Expose
    private Integer sellingPrice;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("warranty")
    @Expose
    private String warranty;
    @SerializedName("return_policy")
    @Expose
    private Object returnPolicy;
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;
    @SerializedName("category")
    @Expose
    private Integer category;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Products() {
    }

    /**
     * 
     * @param image
     * @param sellingPrice
     * @param returnPolicy
     * @param description
     * @param warranty
     * @param id
     * @param viewCount
     * @param title
     * @param markedPrice
     * @param category
     * @param slug
     */
    public Products(Integer id, String title, String slug, String image, Integer markedPrice, Integer sellingPrice, String description, String warranty, Object returnPolicy, Integer viewCount, Integer category) {
        super();
        this.id = id;
        this.title = title;
        this.slug = slug;
        this.image = image;
        this.markedPrice = markedPrice;
        this.sellingPrice = sellingPrice;
        this.description = description;
        this.warranty = warranty;
        this.returnPolicy = returnPolicy;
        this.viewCount = viewCount;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getMarkedPrice() {
        return markedPrice;
    }

    public void setMarkedPrice(Integer markedPrice) {
        this.markedPrice = markedPrice;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public Object getReturnPolicy() {
        return returnPolicy;
    }

    public void setReturnPolicy(Object returnPolicy) {
        this.returnPolicy = returnPolicy;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

}
