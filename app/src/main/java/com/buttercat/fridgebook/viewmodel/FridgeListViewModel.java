package com.buttercat.fridgebook.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.buttercat.fridgebook.BasicApp;
import com.buttercat.fridgebook.model.Ingredient;
import com.buttercat.fridgebook.view.FridgeListFragment;
import com.buttercat.fridgebook.view.utils.FridgeListViewAdapter;
import com.buttercat.fridgebook.model.FridgeRepository;

import java.util.List;

/**
 * An {@link AndroidViewModel} for the {@link FridgeListFragment}
 */
public class FridgeListViewModel extends AndroidViewModel {

    /**
     * {@link LiveData<List< Ingredient >>} with {@link Ingredient} objects in the database
     */
    private LiveData<List<Ingredient>> fridgeContents;
    /**
     * An adapter used by the {@link RecyclerView} through
     * databinding, using {@link #getAdapter()}
     */
    private FridgeListViewAdapter myRecyclerViewAdapter;

    /**
     * Constructor for this {@link AndroidViewModel} which has an instance of the {@link FridgeRepository}
     * and creates an adapter for the {@link FridgeListFragment} {@link RecyclerView}
     *
     * @param application instance used to obtain the {@link FridgeRepository}
     *                    and create the {@link FridgeListViewAdapter}
     */
    public FridgeListViewModel(Application application) {
        super(application);
        FridgeRepository repository = ((BasicApp) application).getRepository();
        fridgeContents = repository.getFridgeContentsLiveData();
        myRecyclerViewAdapter = new FridgeListViewAdapter(this, application);
    }

    /**
     * Method which returns all of the {@link Ingredient} objects in the database
     *
     * @return an observable list of {@link Ingredient} objects
     */
    public LiveData<List<Ingredient>> getFridgeContents() {
        return fridgeContents;
    }

    /**
     * Provides the adapter for databinding
     *
     * @return the {@link FridgeListViewAdapter} used in the {@link FridgeListFragment}
     */
    public FridgeListViewAdapter getAdapter() {
            return myRecyclerViewAdapter;
    }
}
