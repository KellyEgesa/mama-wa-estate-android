package com.mamawaestate.android;

import android.graphics.drawable.Drawable;

public class Product {
    public int index;
    public String title;
    public Drawable productImage;
    public String description;
    public double price;
    public String category;
    public boolean selected;

    public Product(String title, int index, String category, Drawable productImage, String description,
                   double price) {
        this.index = index;
        this.title = title;
        this.category = category;
        this.productImage = productImage;
        this.description = description;
        this.price = price;
    }

}
