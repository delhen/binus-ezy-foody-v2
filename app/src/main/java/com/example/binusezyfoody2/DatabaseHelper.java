package com.example.binusezyfoody2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "ezyfoody-final";
    public static final int DB_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE RESTAURANT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "LATITUDE REAL," +
                "LONGITUDE REAL," +
                "NAME TEXT," +
                "IMAGE_RESOURCE_ID INTEGER);");

        db.execSQL("CREATE TABLE MENU_CATEGORY (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "IMAGE_RESOURCE_ID INTEGER);");

        db.execSQL("CREATE TABLE PRODUCT_DETAIL (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT," +
                "IMAGE_RESOURCE_ID INTEGER," +
                "DESCRIPTION TEXT," +
                "PRICE INTEGER," +
                "PRODUCT_TYPE_ID INTEGER);");

        db.execSQL("CREATE TABLE PRODUCT(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "PRODUCT_DETAIL_ID INTEGER," +
                "RESTAURANT_ID INTEGER," +
                "STOCK INTEGER);");

        db.execSQL("CREATE TABLE USER_CART(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "PRODUCT_ID INTEGER," +
                "QTY INTEGER," +
                "SUBTOTAL_PRICE INTEGER)");

        db.execSQL("CREATE TABLE MY_BALANCE(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "VALUE INTEGER);");

        db.execSQL("CREATE TABLE MY_TRANSACTION(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DATE TEXT," +
                "TOTAL_PRICE INTEGER);");

        db.execSQL("CREATE TABLE PRODUCT_TRANSACTION(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "PRODUCT_ID INTEGER," +
                "TRANSACTION_ID INTEGER," +
                "QTY INTEGER," +
                "SUBTOTAL INTEGER);");

        initBalanceValue(db);

        for(Restaurant restaurant: Restaurant.restaurants){
            insertRestaurant(db, restaurant);
        }

        for(MenuList menuList: MenuList.menuLists){
            insertMenuCategory(db, menuList);
        }

        for(ProductDetail productDetail: ProductDetail.productsDetail){
            insertProductDetails(db, productDetail);
        }

        for(int i=0; i<Product.productsList.length; i++){
            insertProducts(db, Product.productsList[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private static void initBalanceValue(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("VALUE", 500000);

        db.insert("MY_BALANCE", null, values);
    }

    private static void insertRestaurant(SQLiteDatabase db, Restaurant restaurant){
        ContentValues values = new ContentValues();
        values.put("NAME", restaurant.getName());
        values.put("LATITUDE", restaurant.getLatitude());
        values.put("LONGITUDE", restaurant.getLongitude());
        values.put("IMAGE_RESOURCE_ID", restaurant.getImageResourceId());

        db.insert("RESTAURANT", null, values);
    }

    private static void insertMenuCategory(SQLiteDatabase db, MenuList menu){
        ContentValues values = new ContentValues();
        values.put("NAME", menu.getMenuName());
        values.put("IMAGE_RESOURCE_ID", menu.getImageResourceId());

        db.insert("MENU_CATEGORY", null, values);
    }

    private static void insertProductDetails(SQLiteDatabase db, ProductDetail product){
        ContentValues values = new ContentValues();
        values.put("NAME", product.getName());
        values.put("IMAGE_RESOURCE_ID", product.getImageResourceId());
        values.put("DESCRIPTION", product.getDescription());
        values.put("PRICE", product.getPrice());
        values.put("PRODUCT_TYPE_ID", product.getProduct_type_id());

        db.insert("PRODUCT_DETAIL", null, values);
    }

    private static void insertProducts(SQLiteDatabase db, Product product){
        ContentValues values = new ContentValues();
        values.put("PRODUCT_DETAIL_ID", product.getProduct_id());
        values.put("RESTAURANT_ID", product.getRestaurant_id());
        values.put("STOCK", product.getStock());

        db.insert("PRODUCT", null, values);
    }
}
