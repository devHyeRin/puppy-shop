package com.shop.constant;

public enum ItemSellStatus {
    SELL, SOLD_OUT;

    public boolean isSell(){
        return this == SELL;
    }
}
