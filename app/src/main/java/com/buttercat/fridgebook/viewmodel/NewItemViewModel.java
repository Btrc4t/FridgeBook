package com.buttercat.fridgebook.viewmodel;

import android.app.Application;

import com.buttercat.fridgebook.model.AppExecutors;
import com.buttercat.fridgebook.BasicApp;
import com.buttercat.fridgebook.model.database.FridgeListItem;
import com.buttercat.fridgebook.model.FridgeRepository;

import androidx.lifecycle.AndroidViewModel;

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
}
