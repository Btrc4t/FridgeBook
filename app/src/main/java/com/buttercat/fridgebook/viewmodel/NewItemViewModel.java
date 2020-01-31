package com.buttercat.fridgebook.viewmodel;

import android.app.Application;

import com.buttercat.fridgebook.model.AppExecutors;
import com.buttercat.fridgebook.BasicApp;
import com.buttercat.fridgebook.model.apisource.model.Ingredient;
import com.buttercat.fridgebook.model.database.FridgeListItem;
import com.buttercat.fridgebook.model.FridgeRepository;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import retrofit2.Callback;

/**
 * An {@link AndroidViewModel} to be used with the {@link com.buttercat.fridgebook.view.NewItemActivity}
 */
public class NewItemViewModel extends AndroidViewModel {

    /**
     * {@link AppExecutors} used to perform database operations asynchronously
     */
    private final AppExecutors executors;
    /**
     * {@link FridgeRepository} providing access to database methods
     */
    private FridgeRepository repository;

    /**
     * @param application {@link BasicApp} which provides {@link AppExecutors} and {@link FridgeRepository}
     */
    public NewItemViewModel(Application application) {
        super(application);
        repository = ((BasicApp) application).getRepository();
        executors = ((BasicApp) application).getAppExecutors();
    }

    /**
     * Inserts a {@link FridgeListItem} into the {@link com.buttercat.fridgebook.model.database.FridgeContentsDatabase}
     *
     * @param fridgeListItem the item to be inserted into {@link com.buttercat.fridgebook.model.database.FridgeContentsDatabase}
     */
    public void insert(FridgeListItem fridgeListItem) {
        executors.diskIO().execute(() -> repository.insert(fridgeListItem));
    }

    /**
     * Obtain a {@link List<Ingredient>} asynchronously, with a query and set a {@link Callback}
     * that will be called when the list is obtained by {@link retrofit2.Retrofit}
     *
     * @param query        the {@link String} used to search ingredients
     * @param limit        the maximum size of the {@link List}
     * @param respCallback a {@link Callback} which is called when the query has completed
     */
    public void fetchIngredientsWithQuery(String query, int limit, Callback<List<Ingredient>> respCallback) {
       repository.fetchIngredientsWithQuery(query, limit, respCallback);
    }
}
