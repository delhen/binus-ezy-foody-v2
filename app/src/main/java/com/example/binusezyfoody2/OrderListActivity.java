package com.example.binusezyfoody2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Date;

public class OrderListActivity extends AppCompatActivity {

    int currBalance = 0;
    int subtotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SQLiteOpenHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER_CART", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                subtotal += cursor.getInt(cursor.getColumnIndex("SUBTOTAL_PRICE"));
                cursor.moveToNext();
            }

            TextView subTotalText = (TextView) findViewById(R.id.orderTotalPrice);
            subTotalText.setText("Rp. " + subtotal);
            cursor.close();
        }

        Cursor getValue = db.rawQuery("SELECT VALUE FROM MY_BALANCE WHERE _id = 1", null);
        getValue.moveToFirst();
        currBalance = getValue.getInt(getValue.getColumnIndex("VALUE"));
        getValue.close();
        db.close();

        TextView balance = (TextView) findViewById(R.id.currBalance);
        balance.setText("Your balance: " + currBalance);

        Button topUpBtn = (Button) findViewById(R.id.process_topup);
        topUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText topUpText = (EditText) findViewById(R.id.topUpEditText);
                int topUp = Integer.parseInt(topUpText.getText().toString());
                if(topUp < 5000){
                    Toast.makeText(OrderListActivity.this, "Top up must at least 5000", Toast.LENGTH_SHORT).show();
                }else{
                    currBalance += topUp;

                    SQLiteOpenHelper helper = new DatabaseHelper(OrderListActivity.this);
                    SQLiteDatabase db = helper.getWritableDatabase();

                    ContentValues cv = new ContentValues();
                    cv.put("VALUE", currBalance);
                    db.update("MY_BALANCE", cv, "_id=?", new String[]{"1"});

                    TextView balance = (TextView) findViewById(R.id.currBalance);
                    balance.setText("Your balance: " + currBalance);

                    db.close();

                    Toast.makeText(OrderListActivity.this, "Top up success!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button processOrderBtn = (Button) findViewById(R.id.process_product);
        processOrderBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.orderTotalPrice);
                int totalPrice = Integer.parseInt(textView.getText().toString().split(" ")[1]);

                if(totalPrice > 0){
                    if(totalPrice > currBalance){
                        Toast.makeText(OrderListActivity.this, "Insufficient funds!", Toast.LENGTH_SHORT).show();
                    }else{
                        SQLiteOpenHelper helper = new DatabaseHelper(OrderListActivity.this);
                        SQLiteDatabase db = helper.getWritableDatabase();

                        ContentValues tr = new ContentValues();
                        Timestamp ts = new Timestamp(System.currentTimeMillis());
                        String ts2date = ts.toString();
                        tr.put("DATE", ts2date);
                        tr.put("TOTAL_PRICE", totalPrice);
                        long tr_id = db.insert("MY_TRANSACTION", null, tr);

                        Cursor carts = db.rawQuery("SELECT * FROM USER_CART", null);

                        if(carts.moveToFirst()){
                            while(!carts.isAfterLast()){
                                int curr_id = carts.getInt(carts.getColumnIndex("_id"));
                                int productId = carts.getInt(carts.getColumnIndex("PRODUCT_ID"));
                                int qty = carts.getInt(carts.getColumnIndex("QTY"));
                                int subtotal_price = carts.getInt(carts.getColumnIndex("SUBTOTAL_PRICE"));

                                ContentValues curr_tr = new ContentValues();
                                curr_tr.put("PRODUCT_ID", productId);
                                curr_tr.put("QTY", qty);
                                curr_tr.put("SUBTOTAL", subtotal_price);
                                curr_tr.put("TRANSACTION_ID", tr_id);

                                Cursor updateProduct = db.rawQuery("SELECT STOCK FROM PRODUCT WHERE _id = ?", new String[]{String.valueOf(productId)});
                                updateProduct.moveToFirst();
                                int currStock = updateProduct.getInt(updateProduct.getColumnIndex("STOCK"));
                                updateProduct.moveToNext();
                                updateProduct.close();

                                currStock -= qty;
                                ContentValues cv = new ContentValues();
                                cv.put("STOCK", currStock);
                                db.update("PRODUCT", cv, "_id=?", new String[]{String.valueOf(productId)});

                                db.insert("PRODUCT_TRANSACTION", null, curr_tr);
                                db.delete("USER_CART", "_id=?", new String[]{String.valueOf(curr_id)});

                                carts.moveToNext();
                            }
                        }

                        currBalance -= totalPrice;

                        ContentValues newBalance = new ContentValues();
                        newBalance.put("VALUE", currBalance);

                        db.update("MY_BALANCE", newBalance, "_id=?", new String[]{"1"});
                        db.close();
                        carts.close();

                        Toast.makeText(OrderListActivity.this, "Balance success!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(OrderListActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(OrderListActivity.this, "You have no order!" , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}