package com.example.binusezyfoody2;

import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class RestaurantListCardAdapter
        extends RecyclerView.Adapter<RestaurantListCardAdapter.ViewHolder> {

    private int[] restaurantsImageIds;
    private String[] restaurantsName;
    private String[] restaurantsAddress;
    private int[] restaurantsDistanceRelativeUser;
    private int[] restaurantIds;
    private Listener listener;

    interface Listener{
        void onClick(int post);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

    public RestaurantListCardAdapter(int[] restaurantsIds, int[] restaurantsImageIds, String[] restaurantsName, String[] restaurantsAddress, int[] restaurantsDistanceRelativeUser) {
        this.restaurantsImageIds = restaurantsImageIds;
        this.restaurantsName = restaurantsName;
        this.restaurantsAddress = restaurantsAddress;
        this.restaurantsDistanceRelativeUser = restaurantsDistanceRelativeUser;
        this.restaurantIds = restaurantsIds;
    }

    @NonNull
    @Override
    public RestaurantListCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_card_list, parent, false);

        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListCardAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        // Image Card
        ImageView imageView = (ImageView) cardView.findViewById(R.id.restaurant_image);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), restaurantsImageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(restaurantsName[position]);

        // Restaurant title
        TextView nameText = (TextView) cardView.findViewById(R.id.restaurant_name);
        nameText.setText(restaurantsName[position]);

        // Restaurant address
        TextView addressText = (TextView) cardView.findViewById(R.id.restaurant_location);
        addressText.setText(restaurantsAddress[position]);

        // Distance
        TextView distanceText = (TextView) cardView.findViewById(R.id.restaurant_distance);
        distanceText.setText("Jarak dari kamu: " + String.valueOf(restaurantsDistanceRelativeUser[position]));

        cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(restaurantIds[position]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantsName.length;
    }
}
