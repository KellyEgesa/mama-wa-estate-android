package com.mamawaestate.android;


import android.annotation.SuppressLint;

import android.content.res.Resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ShoppingCartHelper {

    public static final String PRODUCT_INDEX = "PRODUCT_INDEX";

    private static List<Product> catalog;
    private static Map<Product, ShoppingCartEntry> cartMap = new HashMap<Product, ShoppingCartEntry>();

    @SuppressLint("UseCompatLoadingForDrawables")

    public static List<Product> getCatalog(Resources res) {
        if (catalog == null) {
            catalog = new Vector<Product>();

            catalog.add(new Product("iphone 11", 1, "Electronics", res
                    .getDrawable(R.drawable.iphone),
                    "Has liquid Retina  HD display", 103000));
            catalog.add(new Product("Asics", 2, "Clothing", res
                    .getDrawable(R.drawable.shoe2),
                    "Mens gel venture 7 trail running shoes.", 3000));
            catalog.add(new Product("Headphones", 3, "Electronics", res
                    .getDrawable(R.drawable.pace),
                    "High quality pace headphones.", 4000));
            catalog.add(new Product("Adidas", 4, "Clothing", res
                    .getDrawable(R.drawable.shoe3),
                    "Mens air max excee sneaker.", 2400));
            catalog.add(new Product("Laptop", 5, "Electronics", res
                    .getDrawable(R.drawable.laptop),
                    "6th generations computers with 6 months warranty.", 49000));
            catalog.add(new Product("Brooks", 6, "Clothing", res
                    .getDrawable(R.drawable.shoe),
                    "Mens range running shoe", 1500));
            catalog.add(new Product("watch", 7, "Clothing", res
                    .getDrawable(R.drawable.shoe2),
                    "Generic stylish women quartz leather PU casual wristwatch.", 500));
        }

        return catalog;
    }

    public static void setQuantity(Product product, int quantity) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        // If the quantity is zero or less, remove the products
        if (quantity <= 0) {
            if (curEntry != null)
                removeProduct(product);
            return;
        }

        // If a current cart entry doesn't exist, create one
        if (curEntry == null) {
            curEntry = new ShoppingCartEntry(product, quantity);
            cartMap.put(product, curEntry);
            return;
        }

        // Update the quantity
        curEntry.setQuantity(quantity);
    }

    public static int getProductQuantity(Product product) {
        // Get the current cart entry
        ShoppingCartEntry curEntry = cartMap.get(product);

        if (curEntry != null)
            return curEntry.getQuantity();

        return 0;
    }

    public static void removeProduct(Product product) {
        cartMap.remove(product);
    }

    public static List<Product> getCartList() {
        List<Product> cartList = new Vector<Product>(cartMap.keySet().size());
        for (Product p : cartMap.keySet()) {
            cartList.add(p);
        }

        return cartList;
    }


}