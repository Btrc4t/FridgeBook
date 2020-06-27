package com.buttercat.fridgebook.model.apisource;

import android.util.Log;

import com.buttercat.fridgebook.BuildConfig;
import com.buttercat.fridgebook.model.Ingredient;
import com.buttercat.fridgebook.model.Recipe;
import com.buttercat.fridgebook.model.RecipeRankings;
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
     * Base URL used to access the API
     */
    private static final String BASE_URL = "https://api.spoonacular.com";
    /**
     * Base URL used to access an {@link Ingredient} image from the API
     */
    private static final String INGREDIENT_IMAGE_BASE_URL =
            "https://spoonacular.com/cdn/ingredients_250x250/";
    /**
     * String used to match a {@link Recipe}
     */
    private static final String RECIPE_IMAGE_LINK_MATCHER =
            "recipeImages";

    /**
     * Constructor for the Spoontacular API which creates a {@link Retrofit} object
     */
    private SpoontacularApi(ExecutorService executorService) {
        if (retrofitInstance != null) {
            return;
        }

        Moshi moshi = new Moshi.Builder().build();

        retrofitInstance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
        mSpoontacularService.autocompleteIngredients(query, responseLimit, true, BuildConfig.API_KEY)
                .enqueue(ingredientCallback);
    }

    /**
     * Obtain a {@link List<Recipe>} asynchronously, with a query and set a {@link Callback}
     * that will be called when the list is obtained by {@link retrofit2.Retrofit}
     *
     * @param ingredients                 the ingredients used to search a recipe
     * @param responseLimit               the number of recipes the search will return
     * @param showOnlyOpenLicensedRecipes true if only recipes with an open license should be shown
     * @param recipeRanking               the {@link RecipeRankings} configuration desired, used to sort results
     * @param ignorePantry                true if regular items such as salt should be considered as included
     * @param recipesCallback             a {@link Callback} which is called when the query has completed
     */
    public void fetchRecipesUsingIngredients(List<Ingredient> ingredients, int responseLimit,
                                             boolean showOnlyOpenLicensedRecipes, RecipeRankings recipeRanking,
                                             boolean ignorePantry, Callback<List<Recipe>> recipesCallback) {
        StringBuilder commaSeparatedIngredients = new StringBuilder();

        for (Ingredient ingredient : ingredients) {
            commaSeparatedIngredients.append(ingredient.getFridgeItemName()).append(",");
        }

        mSpoontacularService.getRecipesWithIngredients(commaSeparatedIngredients.toString(),
                responseLimit, showOnlyOpenLicensedRecipes,
                recipeRanking.getRanking(), ignorePantry, BuildConfig.API_KEY).enqueue(recipesCallback);
    }

    /**
     * Generates a download URL for a given impartial image URL from an {@link Ingredient}
     *
     * @param item an item from {@link com.buttercat.fridgebook.model}
     * @return a full URL with the API key included
     */
    public static String generateImageUrlForItem250px(String item) {
        if (item.contains(RECIPE_IMAGE_LINK_MATCHER)) {
            return item + "?" + BuildConfig.API_KEY;
        } else {
            return INGREDIENT_IMAGE_BASE_URL + item + "?" + BuildConfig.API_KEY;
        }
    }
}