package com.example.binusezyfoody2;

public class MenuList {
    private String menuName;
    private int imageResourceId;

    public static final MenuList[] menuLists = {
            new MenuList("Drink", R.drawable.drink1),
            new MenuList("Food", R.drawable.food2),
            new MenuList("Snack", R.drawable.snack3),
    };

    public MenuList(String menuName, int imageResourceId) {
        this.menuName = menuName;
        this.imageResourceId = imageResourceId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
