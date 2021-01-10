package com.example.binusezyfoody2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MenuListFragment extends Fragment {

    private int[] labelsId;
    private String[] labels;
    private int[] imageIds;
    private int index = 0;

    public static final String EXTRA_RESTAURANT_ID = "Restaurant";
    public static final String EXTRA_MENU_LIST_ID = "MenuList";

    public MenuListFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView menuRecycler = (RecyclerView) inflater.inflate(
                R.layout.menu_list_fragment, container, false
        );

        SQLiteOpenHelper helper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM MENU_CATEGORY", null);

        int menu_len = cursor.getCount();

        labelsId = new int[menu_len];
        labels = new String[menu_len];
        imageIds = new int[menu_len];

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                labelsId[index] = cursor.getInt(cursor.getColumnIndex("_id"));
                labels[index] = cursor.getString(cursor.getColumnIndex("NAME"));
                imageIds[index] = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));

                index++;
                cursor.moveToNext();
            }
        }

        db.close();
        cursor.close();

        MenuListCardAdapter adapter = new MenuListCardAdapter(labelsId, labels, imageIds);
        menuRecycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        menuRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new MenuListCardAdapter.Listener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(getContext(), ProductListActivity.class);
                MenuListActivity activity = (MenuListActivity) getActivity();

                intent.putExtra(EXTRA_RESTAURANT_ID, activity.getRestaurantId());
                intent.putExtra(EXTRA_MENU_LIST_ID, pos);

                startActivity(intent);
            }
        });

        return menuRecycler;
    }
}
