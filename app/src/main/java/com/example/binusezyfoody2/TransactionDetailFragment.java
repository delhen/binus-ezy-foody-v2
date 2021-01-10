package com.example.binusezyfoody2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionDetailFragment extends Fragment {
    public static final String TAG = "TrDetailFragment";
    public static final String EXTRA_TRANSACTION_ID = "TransactionId";

    private ArrayList<OrderList> orders = new ArrayList<>();
    private long transaction_id;

    public TransactionDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView trListRecycler = (RecyclerView) inflater.inflate(
                R.layout.transaction_detail_fragment, container, false
        );

        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle != null){
            transaction_id = bundle.getLong(EXTRA_TRANSACTION_ID);
            Toast.makeText(getContext(), "TR_ID: " + transaction_id, Toast.LENGTH_SHORT).show();

            SQLiteOpenHelper helper = new DatabaseHelper(getActivity());
            SQLiteDatabase db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM PRODUCT_TRANSACTION WHERE TRANSACTION_ID = ?", new String[]{String.valueOf(transaction_id)});
            if(cursor.getCount() != 0){
                Log.d(TAG, "onCreateView: Query found with id = " + transaction_id);
                cursor.moveToFirst();
                while(!cursor.isAfterLast()){
                    int productId = cursor.getInt(cursor.getColumnIndex("PRODUCT_ID"));
                    int qty = cursor.getInt(cursor.getColumnIndex("QTY"));
                    int subtotal = cursor.getInt(cursor.getColumnIndex("SUBTOTAL"));

                    OrderList order = new OrderList(0, productId, qty, subtotal);
                    orders.add(order);
                    cursor.moveToNext();
                }

                cursor.close();

                TransactionDetailAdapter adapter = new TransactionDetailAdapter(orders);
                trListRecycler.setAdapter(adapter);

                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                trListRecycler.setLayoutManager(layoutManager);
            }else{
                Log.d(TAG, "onCreateView: Query not found with id " + transaction_id);
            }

        }

        return trListRecycler;
    }
}
