package com.example.binusezyfoody2;

import android.util.Log;

import java.util.Random;

public class Product {
    private int product_id;
    private int restaurant_id;
    private int stock;

    public static Product[] productsList = {
            new Product(1, 1, 0),
            new Product(2, 1, 16),
            new Product(3, 1, 3),
            new Product(4, 1, 22),
            new Product(5, 1, 23),
            new Product(6, 1, 8),
            new Product(7, 1, 0),
            new Product(8, 1, 49),
            new Product(9, 1, 36),
            new Product(1, 2, 22),
            new Product(2, 2, 17),
            new Product(3, 2, 7),
            new Product(4, 2, 36),
            new Product(5, 2, 25),
            new Product(6, 2, 7),
            new Product(7, 2, 13),
            new Product(8, 2, 35),
            new Product(9, 2, 12),
            new Product(1, 3, 46),
            new Product(2, 3, 16),
            new Product(3, 3, 45),
            new Product(4, 3, 0),
            new Product(5, 3, 32),
            new Product(6, 3, 24),
            new Product(7, 3, 11),
            new Product(8, 3, 2),
            new Product(9, 3, 1),
            new Product(1, 4, 18),
            new Product(2, 4, 21),
            new Product(3, 4, 48),
            new Product(4, 4, 30),
            new Product(5, 4, 39),
            new Product(6, 4, 35),
            new Product(7, 4, 6),
            new Product(8, 4, 12),
            new Product(9, 4, 34)
    };

    public Product(int product_id, int restaurant_id, int stock) {
        this.product_id = product_id;
        this.restaurant_id = restaurant_id;
        this.stock = stock;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public int getStock() {
        return stock;
    }
}
