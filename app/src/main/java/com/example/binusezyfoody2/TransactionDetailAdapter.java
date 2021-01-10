package com.example.binusezyfoody2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TransactionDetailAdapter extends RecyclerView.Adapter<TransactionDetailAdapter.ViewHolder>{
    private static final String TAG = "TransactionDetailAdapter" ;
    private ArrayList<OrderList> orders;

    interface Listener{
        void deleteButtonClicked(int pos);
    }

    public TransactionDetailAdapter(ArrayList<OrderList> orders){
        this.orders = orders;
    }

    @NonNull
    @Override
    public TransactionDetailAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_detail_card, parent, false);

        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionDetailAdapter.ViewHolder holder, final int position) {
        final CardView cv = holder.cardView;

        SQLiteOpenHelper helper = new DatabaseHelper(cv.getContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        OrderList order = orders.get(position);
        int productId = order.getProductId();

        Cursor cursor = db.rawQuery("SELECT PRODUCT_DETAIL_ID FROM PRODUCT WHERE _id = ?", new String[]{String.valueOf(productId)});
        cursor.moveToFirst();
        int productDetailId = cursor.getInt(cursor.getColumnIndex("PRODUCT_DETAIL_ID"));
        cursor.moveToNext();
        cursor.close();

        cursor = db.rawQuery("SELECT NAME, IMAGE_RESOURCE_ID, PRICE FROM PRODUCT_DETAIL WHERE _id = ?", new String[]{String.valueOf(productDetailId)});
        cursor.moveToFirst();
        String prodName = cursor.getString(cursor.getColumnIndex("NAME"));
        int prodImgSrc = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));
        int price = cursor.getInt(cursor.getColumnIndex("PRICE"));
        cursor.moveToNext();
        cursor.close();
        db.close();

        ImageView imageView = (ImageView) cv.findViewById(R.id.productImg);
        Drawable drawable = ContextCompat.getDrawable(cv.getContext(), prodImgSrc);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(prodName);

        TextView textView = (TextView) cv.findViewById(R.id.productName);
        textView.setText(prodName);

        TextView textView1 = (TextView) cv.findViewById(R.id.productPrice);
        textView1.setText("Rp. " + price);

        TextView textView2 = (TextView) cv.findViewById(R.id.qty);
        textView2.setText("Qty: " + orders.get(position).getQty());

        TextView textView3 = (TextView) cv.findViewById(R.id.totalPrice);
        textView3.setText("Total Price: " + order.getSubTotalPrice());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CardView cardView;
        private TransactionDetailAdapter.Listener listener;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
