package com.buttercat.fridgebook.model.apisource;

import com.buttercat.fridgebook.model.apisource.model.Ingredient;
import com.squareup.moshi.Moshi;

import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Class used to create a {@link Retrofit} object for the Spoontacular API
 */
public class SpoontacularApi {

    /**
     * The API key to use with the Spoontacular API
     */
    private static final String API_KEY = "775ce838b2404828a205665367995c52";
    /**
     * Singleton instance of this class
     */
    private static Retrofit retrofitInstance = null;
    /**
     * Singleton instance of this class
     */
    private static SpoontacularApi sInstance = null;
    /**
     * {@link Retrofit} interface defining available queries for this API
     */
    private SpoontacularService mSpoontacularService = null;

    /**
     * Constructor for the Spoontacular API which creates a {@link Retrofit} object
     */
    private SpoontacularApi(ExecutorService executorService) {
        if (retrofitInstance != null) {
            return;
        }

        Moshi moshi = new Moshi.Builder().build();

        retrofitInstance = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .callbackExecutor(executorService)
                .build();

        mSpoontacularService = retrofitInstance.create(SpoontacularService.class);
    }

    /**
     * @return the singleton instance of this class
     */
    public static SpoontacularApi getInstance(ExecutorService executorService) {
        if (sInstance == null) {
            sInstance = new SpoontacularApi(executorService);
        }
        return sInstance;
    }

    /**
     * Obtain a {@link List<Ingredient>} asynchronously, with a query and set a {@link Callback}
     * that will be called when the list is obtained by {@link retrofit2.Retrofit}
     *
     * @param query              the {@link String} used to search ingredients
     * @param responseLimit      the maximum size of the {@link List}
     * @param ingredientCallback a {@link Callback} which is called when the query has completed
     */
    public void fetchIngredientsList(String query, int responseLimit,
                                     Callback<List<Ingredient>> ingredientCallback) {
        mSpoontacularService.autocompleteIngredients(query, responseLimit, API_KEY)
                .enqueue(ingredientCallback);
    }
}