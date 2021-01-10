package com.example.binusezyfoody2;

public class OrderList {
    private int orderId;
    private int productId;
    private int qty;
    private int subTotalPrice;

    public OrderList(int orderId, int productId, int qty, int subTotalPrice) {
        this.orderId = orderId;
        this.productId = productId;
        this.qty = qty;
        this.subTotalPrice = subTotalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQty() {
        return qty;
    }

    public int getSubTotalPrice() {
        return subTotalPrice;
    }
}
