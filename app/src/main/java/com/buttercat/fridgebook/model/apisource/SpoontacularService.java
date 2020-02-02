package com.buttercat.fridgebook.model.apisource;


import com.buttercat.fridgebook.model.Ingredient;

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
                                                   @Query("apiKey") String apiKey);

}
