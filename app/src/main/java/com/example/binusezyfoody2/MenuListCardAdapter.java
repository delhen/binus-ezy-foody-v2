package com.example.binusezyfoody2;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MenuListCardAdapter
        extends RecyclerView.Adapter<MenuListCardAdapter.ViewHolder> {

    private String[] labels;
    private int[] imageIds;
    private int[] labelsId;
    private Listener listener;

    interface Listener{
        void onClick(int pos);
    }

    public MenuListCardAdapter(int[] labelsId, String[] labels, int[] imageIds){
        this.labels = labels;
        this.imageIds = imageIds;
        this.labelsId = labelsId;
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_card_list, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final CardView cardView = holder.cardView;

        ImageView imageView = (ImageView) cardView.findViewById(R.id.menu_picture);
        Drawable drawable = ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(labels[position]);

        TextView textView = (TextView) cardView.findViewById(R.id.menu_title);
        textView.setText(labels[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(labelsId[position]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return labels.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(@NonNull CardView v) {
            super(v);
            cardView = v;
        }
    }
}

