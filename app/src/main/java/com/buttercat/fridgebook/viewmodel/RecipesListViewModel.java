package com.buttercat.fridgebook.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.buttercat.fridgebook.BasicApp;
import com.buttercat.fridgebook.model.FridgeRepository;
import com.buttercat.fridgebook.model.Ingredient;
import com.buttercat.fridgebook.model.Recipe;
import com.buttercat.fridgebook.model.RecipeRankings;
import com.buttercat.fridgebook.view.RecipesListFragment;
import com.buttercat.fridgebook.view.utils.RecipesListViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An {@link AndroidViewModel} for the {@link RecipesListFragment}
 */
public class RecipesListViewModel extends AndroidViewModel {

    private static final String TAG = RecipesListViewModel.class.getSimpleName();
    /**
     * {@link LiveData<List<Recipe>>} fetched using {@link Ingredient} objects as a filter
     */
    private MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    ;

    /**
     * An adapter used by the {@link RecyclerView} through
     * databinding, using {@link #getAdapter()}
     */
    private RecipesListViewAdapter myRecyclerViewAdapter;

    /**
     * Constructor for this {@link AndroidViewModel} which has an instance of the {@link FridgeRepository}
     * and creates an adapter for the {@link RecipesListFragment} {@link RecyclerView}
     *
     * @param application instance used to obtain the {@link FridgeRepository}
     *                    and create the {@link RecipesListViewAdapter}
     */
    public RecipesListViewModel(Application application) {
        super(application);
        FridgeRepository repository = ((BasicApp) application).getRepository();
        repository.getFridgeContentsLiveData().observe(ProcessLifecycleOwner.get(), (ingredients) -> {
            //TODO remove responseLimit magic number, first check if API supports pagination
            repository.fetchRecipesUsingIngredients(ingredients, 3, true, RecipeRankings.MIN_MISSING, true, new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    recipes.postValue(response.body());
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Log.e(TAG, "onFailure: could not fetch a list of recipes using ingredients", t);
                }
            });

        });
        myRecyclerViewAdapter = new RecipesListViewAdapter(this);
    }

    /**
     * Provides the adapter for databinding
     *
     * @return the {@link RecipesListViewAdapter} used in the {@link RecipesListFragment}
     */
    public RecipesListViewAdapter getAdapter() {
        return myRecyclerViewAdapter;
    }

    /**
     * Obtain a {@link LiveData} list of {@link Recipe} items
     *
     * @return {@link Recipe} items
     */
    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
