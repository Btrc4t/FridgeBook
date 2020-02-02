package com.buttercat.fridgebook.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * An item present in the user's refrigerator (presumably) which has a name, a type (liquid/solid)
 * and a quantity (stored in grams/milliliters by default) and others,
 * as defined in the Spoontacular API
 */
@Entity(tableName = "fridge_items")
public class Ingredient {

    /**
     * The Spoontacular API id for this item
     */
    @PrimaryKey
    @ColumnInfo(name = "id")
    @Json(name = "id")
    private int id;
    /**
     * The name of the item
     */
    @Json(name = "name")
    @ColumnInfo(name = "fridgeItemName")
    private String fridgeItemName;
    /**
     * The quantity of the item (stored in grams/milliliters by default)
     */
    @ColumnInfo(name = "quantity")
    private float quantity;
    /**
     * The aisle for this ingredient
     */
    @ColumnInfo(name = "aisle")
    @Json(name = "aisle")
    private String aisle;

    /**
     * The possbile units for this ingredient
     */
    @Ignore
    @Json(name = "possibleUnits")
    private List<String> possibleUnits;
    /**
     * The set unit (for database storage) for this ingredient
     */
    @ColumnInfo(name = "unit")
    private String unit;
    /**
     * The image URL for this ingredient
     */
    @ColumnInfo(name = "image")
    @Json(name = "image")
    private String image;


    public Ingredient(float quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getFridgeItemName() {
        return fridgeItemName;
    }

    public void setFridgeItemName(@NonNull String fridgeItemName) {
        this.fridgeItemName = fridgeItemName;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public List<String> getPossibleUnits() {
        return possibleUnits;
    }

    public void setPossibleUnits(List<String> possibleUnits) {
        this.possibleUnits = possibleUnits;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

