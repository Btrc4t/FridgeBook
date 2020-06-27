package com.buttercat.fridgebook.model.apisource;


import com.buttercat.fridgebook.model.Ingredient;
import com.buttercat.fridgebook.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Interface containing the methods of interest from the Spoontacular API
 */
interface SpoontacularService {

    @GET("/food/ingredients/autocomplete?")
    Call<List<Ingredient>> autocompleteIngredients(@Query("query") String query,
                                                   @Query("number") int number,
                                                   @Query("metaInformation") boolean meta,
                                                   @Query("apiKey") String apiKey);

    @GET("/recipes/findByIngredients?")
    Call<List<Recipe>> getRecipesWithIngredients(@Query("ingredients") String ingredients,
                                                 @Query("number") int number,
                                                 @Query("limitLicense") boolean limitLicense,
                                                 @Query("ranking") int ranking,
                                                 @Query("ignorePantry") boolean ignorePantry,
                                                 @Query("apiKey") String apiKey);

}
