package com.example.binusezyfoody2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MenuListActivity extends AppCompatActivity {

    public static final String EXTRA_RESTAURANT_ID = "Restaurant";
    private static final String TAG = "[MenuListActivity]";

    private String selectedRestaurant = "";
    private int restaurantId;

    public int getRestaurantId() {
        return restaurantId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundles = getIntent().getExtras();
        if(bundles != null){
            restaurantId = bundles.getInt(EXTRA_RESTAURANT_ID);

            SQLiteOpenHelper helper = new DatabaseHelper(this);
            SQLiteDatabase db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT NAME FROM RESTAURANT WHERE _id = " + restaurantId, null);
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    selectedRestaurant = cursor.getString(cursor.getColumnIndex("NAME"));
                    Log.d(TAG, "selectedRestaurant: " + selectedRestaurant);
                    cursor.moveToNext();
                }
            }

            cursor.close();
            db.close();
        }

        TextView text = (TextView) findViewById(R.id.chosen_restaurant);
        text.setText(selectedRestaurant);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
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