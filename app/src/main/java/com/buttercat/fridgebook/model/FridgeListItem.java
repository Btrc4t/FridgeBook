package com.buttercat.fridgebook.model;

public class FridgeListItem {

    private String itemName;

    public String getItemName() {
        return itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    private String quantity;

    public FridgeListItem(String itemName, String quantity) {

        this.itemName = itemName;
        this.quantity = quantity;
    }
}
