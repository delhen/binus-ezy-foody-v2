package com.example.binusezyfoody2;

import android.content.Intent;
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

public class ProductListFragment extends Fragment {
    private static final String TAG = "ProductListFragment";

    private int[] productsIds;
    private String[] productsName;
    private int[] productsPrice;
    private String[] productsDesc;
    private int[] productsImgs;

    private int index = 0;

    public static final String EXTRA_RESTAURANT_ID = "Restaurant";
    public static final String EXTRA_MENU_LIST_ID = "MenuList";
    public static final String EXTRA_PRODUCT_ID = "ProductId";

    private int restaurantId;
    private int menuListId;
    private int productId = -1;

    public ProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView productListRecycler = (RecyclerView) inflater.inflate(
                R.layout.product_list_fragment, container, false
        );

        ProductListActivity activity = (ProductListActivity) getActivity();
        Bundle bundles = activity.getIntent().getExtras();

        SQLiteOpenHelper helper = new DatabaseHelper(activity);
        SQLiteDatabase db = helper.getReadableDatabase();

        if(bundles != null){
            restaurantId = bundles.getInt(EXTRA_RESTAURANT_ID);
            menuListId = bundles.getInt(EXTRA_MENU_LIST_ID);
        }

//        Cursor cursor = db.rawQuery("SELECT * FROM PRODUCT WHERE RESTAURANT_ID = ?", new String[]{String.valueOf(restaurantId)});
//        Log.d(TAG, "onCreateView: RESTAURANT " + restaurantId + " TABLE found: " + cursor.getCount() + " data");

        Cursor cursor = db.rawQuery("SELECT * FROM PRODUCT_DETAIL " +
                "INNER JOIN PRODUCT ON PRODUCT_DETAIL._id = PRODUCT.PRODUCT_DETAIL_ID " +
                "WHERE PRODUCT_TYPE_ID = ? AND RESTAURANT_ID = ?", new String[]{String.valueOf(menuListId), String.valueOf(restaurantId)});

        int product_len = cursor.getCount();

        if(product_len != 0){
            productsIds = new int[product_len];
            productsName = new String[product_len];
            productsPrice = new int[product_len];
            productsDesc = new String[product_len];
            productsImgs = new int[product_len];

            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                productsIds[index] = cursor.getInt(cursor.getColumnIndex("PRODUCT_DETAIL_ID"));
                productsName[index] = cursor.getString(cursor.getColumnIndex("NAME"));
                productsPrice[index] = cursor.getInt(cursor.getColumnIndex("PRICE"));
                productsDesc[index] = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                productsImgs[index] = cursor.getInt(cursor.getColumnIndex("IMAGE_RESOURCE_ID"));
                index++;
                cursor.moveToNext();
            }
        }

        index = 0;
        cursor.close();
        db.close();

        ProductListCardAdapter adapter = new ProductListCardAdapter(productsIds, productsName, productsDesc, productsPrice, productsImgs);
        productListRecycler.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        productListRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new ProductListCardAdapter.Listener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                intent.putExtra(EXTRA_RESTAURANT_ID, restaurantId);
                intent.putExtra(EXTRA_PRODUCT_ID, pos);
                startActivity(intent);
//                Log.d(TAG, "onClick: You clicked product Id: " + pos + " and restaurantId: " + restaurantId);
            }
        });

        return productListRecycler;
    }
}
