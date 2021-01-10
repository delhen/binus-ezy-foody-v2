package com.example.binusezyfoody2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amitshekhar.DebugDB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Semaphore;

public class RestaurantFragment extends Fragment {

    private int index = 0; // Iterator index for snapshot
    int[] restaurantIds;
    int[] restaurantsImageIds;
    String[] restaurantsName;
    String[] restaurantsAddress;
    int[] restaurantsDistanceRelativeUser;

    public static final String TAG = "[RestaurantFragment]";
    public static final String EXTRA_RESTAURANT_ID = "Restaurant";

    public RestaurantFragment(){

    }

    private String getAddress(double lat, double lng){
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addressList;

        try {
            addressList = geocoder.getFromLocation(lat, lng, 1);
            String completeAddress = addressList.get(0).getAddressLine(0);

            return completeAddress;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Tidak diketahui";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView restaurantRecycler = (RecyclerView)inflater.inflate(
                R.layout.restaurant_fragment, container,false);

        Log.d(TAG, "IP Address Database: " + DebugDB.getAddressLog());

        SQLiteOpenHelper helper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM RESTAURANT", null);

        int restaurant_len = cursor.getCount();

        restaurantIds = new int[restaurant_len];
        restaurantsName = new String[restaurant_len];
        restaurantsAddress = new String[restaurant_len];
        restaurantsImageIds = new int[restaurant_len];
        restaurantsDistanceRelativeUser = new int[restaurant_len];

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                restaurantIds[index] = cursor.getInt(cursor.getColumnIndex("_id"));
                restaurantsName[index] = cursor.getString(cursor.getColumnIndex("NAME"));
                restaurantsImageIds[index] = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));
                double lat = cursor.getDouble(cursor.getColumnIndex("LATITUDE"));
                double lng = cursor.getDouble(cursor.getColumnIndex("LONGITUDE"));
                restaurantsAddress[index] = getAddress(lat, lng);
                restaurantsDistanceRelativeUser[index] = 3;

                index++;
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        RestaurantListCardAdapter adapter = new RestaurantListCardAdapter(restaurantIds, restaurantsImageIds, restaurantsName, restaurantsAddress, restaurantsDistanceRelativeUser);
        restaurantRecycler.setAdapter(adapter);


        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        restaurantRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new RestaurantListCardAdapter.Listener() {
            @Override
            public void onClick(int post) {
                Intent intent = new Intent(getContext(), MenuListActivity.class);
                intent.putExtra(EXTRA_RESTAURANT_ID, post);
                startActivity(intent);
            }
        });

        return restaurantRecycler;
    }
}
