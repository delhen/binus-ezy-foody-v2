package com.example.binusezyfoody2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class TransactionListActivity extends AppCompatActivity {

    public static final String EXTRA_TRANSACTION_ID = "TransactionId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SQLiteOpenHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MY_TRANSACTION", null);

        //Cursor Adapter
        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1, cursor, new String[]{"DATE"}, new int[] {android.R.id.text1}, 0);

        ListView listTrans = (ListView) findViewById(R.id.list_transactions);
        listTrans.setAdapter(listAdapter);

        //Configure AdapterView that will be added to ListView
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TransactionListActivity.this, "You clicked " + id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TransactionListActivity.this, TransactionDetailActivity.class);
                intent.putExtra(EXTRA_TRANSACTION_ID, id);
                startActivity(intent);
            }
        };

        listTrans.setOnItemClickListener(itemClickListener);
    }
}