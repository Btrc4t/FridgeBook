package com.buttercat.fridgebook.model.database;

import com.buttercat.fridgebook.model.database.converter.ItemTypeConverter;

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

    /**
     * Getter for the item's name
     */
    @NonNull
    public String getFridgeItemName() {
        return fridgeItemName;
    }

    /**
     * Getter for the item's quantity
     */
    public String getQuantity() {
        return quantity;
    }

    /**
     * Getter for the item's type
     */
    /*package*/ ItemType getItemType() {
        return itemType;
    }

    /**
     * Setter for the item's name
     *
     * @param fridgeItemName the item's name
     */
    public void setFridgeItemName(@NonNull String fridgeItemName) {
        this.fridgeItemName = fridgeItemName;
    }

    /**
     * Setter for the item's quantity
     *
     * @param quantity the item's quantity
     */
    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    /**
     * Setter for the item's type
     *
     * @param itemType the item's type
     */
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }
}
