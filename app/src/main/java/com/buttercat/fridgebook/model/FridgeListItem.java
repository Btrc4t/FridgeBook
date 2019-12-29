package com.buttercat.fridgebook.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

/**
 * An item present in the user's refrigerator (presumably) which has a name, a type (liquid/solid) and
 * a quantity (stored in grams/milliliters by default)
 */
@Entity(tableName = "fridge_items")
public class FridgeListItem {

    /**
     * The name of the item
     */
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "fridgeItemName")
    private String fridgeItemName;
    /**
     * The quantity of the item (stored in grams/milliliters by default)
     */
    @ColumnInfo(name = "quantity")
    private String quantity;
    /**
     * The {@link ItemType} (liquid/solid)
     */
    @ColumnInfo(name = "itemType")
    @TypeConverters(ItemTypeConverter.class)
    private ItemType itemType;

    public FridgeListItem(@NonNull String fridgeItemName, String quantity, ItemType itemType) {
        this.fridgeItemName = fridgeItemName;
        this.quantity = quantity;
        this.itemType = itemType;
    }

    @NonNull
    public String getFridgeItemName() {
        return fridgeItemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setFridgeItemName(@NonNull String fridgeItemName) {
        this.fridgeItemName = fridgeItemName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
