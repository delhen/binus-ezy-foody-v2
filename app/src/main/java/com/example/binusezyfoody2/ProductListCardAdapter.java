package com.example.binusezyfoody2;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListCardAdapter
        extends RecyclerView.Adapter<ProductListCardAdapter.ViewHolder>{
    private int[] productsIds;
    private String[] productsName;
    private int[] productsPrice;
    private String[] productsDesc;
    private int[] productsImgs;
    private Listener listener;

    interface Listener{
        void onClick(int pos);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public ProductListCardAdapter(int[] productsIds, String[] names, String[] descs, int[] prices, int[] imgs){
        this.productsIds = productsIds;
        this.productsName = names;
        this.productsDesc = descs;
        this.productsPrice = prices;
        this.productsImgs = imgs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_card_list, parent, false);

        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CardView cv = holder.cardView;

        TextView textView = (TextView) cv.findViewById(R.id.product_name);
        textView.setText(productsName[position]);

        Log.d("[ProductListAdapter]", "onBindViewHolder: Image resource with id " + productsIds[position] + ": " + productsImgs[position]);
        ImageView imageView = (ImageView) cv.findViewById(R.id.product_image);
        Drawable drawable = ContextCompat.getDrawable(cv.getContext(), productsImgs[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(productsName[position]);

        TextView textView1 = (TextView) cv.findViewById(R.id.product_desc);
        textView1.setText(productsDesc[position].substring(0, 40) + "...");

        TextView textView2 = (TextView) cv.findViewById(R.id.product_price);
        textView2.setText("Rp. " + productsPrice[position]);

        cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(productsIds[position]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(@NonNull CardView v) {
            super(v);
            cardView = v;
        }
    }
}
