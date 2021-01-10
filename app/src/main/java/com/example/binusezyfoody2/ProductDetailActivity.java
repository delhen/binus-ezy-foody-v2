package com.example.binusezyfoody2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailActivity extends AppCompatActivity {
    public static final String EXTRA_RESTAURANT_ID = "Restaurant";
    public static final String EXTRA_PRODUCT_ID = "ProductId";

    private int product_id;
    private int product_detail_id;
    private int restaurant_id;

    private String name;
    private String desc;
    private int imageSrc;
    private int price;
    private int stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Bundle bundles = getIntent().getExtras();
        if(bundles != null){
            product_detail_id = bundles.getInt(EXTRA_PRODUCT_ID);
            restaurant_id = bundles.getInt(EXTRA_RESTAURANT_ID);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT PRODUCT._id, STOCK, NAME, IMAGE_RESOURCE_ID, DESCRIPTION, PRICE FROM PRODUCT_DETAIL " +
                "INNER JOIN PRODUCT ON PRODUCT_DETAIL._id = PRODUCT.PRODUCT_DETAIL_ID " +
                "WHERE RESTAURANT_ID=? AND PRODUCT_DETAIL_ID=?", new String[]{String.valueOf(restaurant_id), String.valueOf(product_detail_id)});

        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            product_id = cursor.getInt(cursor.getColumnIndex("_id"));
            name = cursor.getString(cursor.getColumnIndex("NAME"));
            desc = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
            imageSrc = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));
            price = cursor.getInt(cursor.getColumnIndex("PRICE"));
            stock = cursor.getInt(cursor.getColumnIndex("STOCK"));

            cursor.moveToNext();
            cursor.close();

            TextView productName = (TextView) findViewById(R.id.productName);
            productName.setText(name);

            TextView productDesc = (TextView) findViewById(R.id.productDesc);
            productDesc.setText(desc);

            ImageView imageView = (ImageView) findViewById(R.id.productImage);
            Drawable drawable = ContextCompat.getDrawable(this, imageSrc);
            imageView.setImageDrawable(drawable);
            imageView.setContentDescription(name);

            TextView productPrice = (TextView) findViewById(R.id.productPrice);
            productPrice.setText("Rp. " + price);

            TextView productStock = (TextView) findViewById(R.id.productStock);
            productStock.setText("Stok hari ini: " + stock);
        }else{
            Toast.makeText(this, "Data ga ada nicc", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void goBack(View v){
        finish();
    }

    public void orderProduct(View v){
        TextView textViewQty = (TextView) findViewById(R.id.productQty);
        int qty = Integer.parseInt(textViewQty.getText().toString());

        if(qty > 0 && qty <= stock){
            SQLiteOpenHelper helper = new DatabaseHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("PRODUCT_ID", product_id);
            values.put("QTY", qty);
            values.put("SUBTOTAL_PRICE", qty * price);

            long key = db.insert("USER_CART", null, values);
            db.close();

            Toast.makeText(this, "Order added! with key = " + key, Toast.LENGTH_SHORT).show();
            finish();
        }else{
            if(stock == 0){
                Toast.makeText(this, "Stok habis!", Toast.LENGTH_SHORT).show();
                finish();
            }else if(qty <= 0){
                Toast.makeText(this, "Minimal satu pemesanan", Toast.LENGTH_SHORT).show();
            }else if(qty > stock){
                Toast.makeText(this, "Pemesanan melebihi stok", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.history_action:
                Toast.makeText(this, "You clicked History!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, OrderActivity.class);
//                startActivity(intent);
                return true;
            case R.id.order_list_action:
                Toast.makeText(this, "You clicked Order List!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(this, OrderActivity.class);
//                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}