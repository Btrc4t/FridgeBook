package com.buttercat.fridgebook.model.apisource.model;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * An ingredient as defined in the Spoontacular API
 */
public class Ingredient {

    @Json(name = "image")
    private String image;
    @Json(name = "name")
    private String name;
    @Json(name = "id")
    private int id;
    @Json(name = "aisle")
    private String aisle;
    @Json(name = "possibleUnits")
    private List<String> possibleUnits;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

}
