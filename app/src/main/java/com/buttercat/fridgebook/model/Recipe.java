package com.buttercat.fridgebook.model;

import com.buttercat.fridgebook.ListItem;
import com.squareup.moshi.Json;

import java.util.List;

public class Recipe extends ListItem {

    @Json(name = "id")
    private Integer id;
    @Json(name = "image")
    private String image;
    @Json(name = "imageType")
    private String imageType;
    @Json(name = "likes")
    private Integer likes;
    @Json(name = "missedIngredientCount")
    private Integer missedIngredientCount;
    @Json(name = "missedIngredients")
    private List<Ingredient> missedIngredients = null;
    @Json(name = "title")
    private String title;
    @Json(name = "unusedIngredients")
    private List<Object> unusedIngredients = null;
    @Json(name = "usedIngredientCount")
    private Integer usedIngredientCount;
    @Json(name = "usedIngredients")
    private List<Ingredient> usedIngredients = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public void setMissedIngredientCount(Integer missedIngredientCount) {
        this.missedIngredientCount = missedIngredientCount;
    }

    public List<Ingredient> getMissedIngredients() {
        return missedIngredients;
    }

    public void setMissedIngredients(List<Ingredient> missedIngredients) {
        this.missedIngredients = missedIngredients;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Object> getUnusedIngredients() {
        return unusedIngredients;
    }

    public void setUnusedIngredients(List<Object> unusedIngredients) {
        this.unusedIngredients = unusedIngredients;
    }

    public Integer getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public void setUsedIngredientCount(Integer usedIngredientCount) {
        this.usedIngredientCount = usedIngredientCount;
    }

    public List<Ingredient> getUsedIngredients() {
        return usedIngredients;
    }

    public void setUsedIngredients(List<Ingredient> usedIngredients) {
        this.usedIngredients = usedIngredients;
    }

}