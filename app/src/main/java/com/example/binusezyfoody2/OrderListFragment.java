package com.example.binusezyfoody2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderListFragment extends Fragment {
    public static final String TAG = "OrderListFragment";
    private ArrayList<OrderList> orders = new ArrayList<>();

    public OrderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView orderListRecycler = (RecyclerView) inflater.inflate(
                R.layout.order_list_fragment, container, false
        );

        SQLiteOpenHelper helper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USER_CART", null);
        if(cursor.getCount() != 0){
            Log.d(TAG, "onCreateView: Query found!");
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                int orderId = cursor.getInt(cursor.getColumnIndex("_id"));
                int productId = cursor.getInt(cursor.getColumnIndex("PRODUCT_ID"));
                int qty = cursor.getInt(cursor.getColumnIndex("QTY"));
                int subtotal = cursor.getInt(cursor.getColumnIndex("SUBTOTAL_PRICE"));

                OrderList order = new OrderList(orderId, productId, qty, subtotal);
                orders.add(order);

                cursor.moveToNext();
            }

            cursor.close();

            OrderListCardAdapter adapter = new OrderListCardAdapter(orders);
            orderListRecycler.setAdapter(adapter);

            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
            orderListRecycler.setLayoutManager(layoutManager);
        }else{
            Log.d(TAG, "onCreateView: Query not found!");
        }

        return orderListRecycler;
    }
}
