package com.buttercat.fridgebook.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.buttercat.fridgebook.BasicApp;
import com.buttercat.fridgebook.model.database.FridgeListItem;
import com.buttercat.fridgebook.view.utils.FridgeListViewAdapter;
import com.buttercat.fridgebook.model.FridgeRepository;

import java.util.List;

/**
 * An {@link AndroidViewModel} for the {@link com.buttercat.fridgebook.view.FridgeListFragment}
 */
public class FridgeListViewModel extends AndroidViewModel {

    /**
     * A singleton instance of the {@link FridgeRepository}
     */
    private FridgeRepository repository;
    /**
     * {@link LiveData<List<FridgeListItem>>} with {@link FridgeListItem} objects in the database
     */
    private LiveData<List<FridgeListItem>> fridgeContents;
    /**
     * An adapter used by the {@link androidx.recyclerview.widget.RecyclerView} through
     * databinding, using {@link #getAdapter()}
     */
    private FridgeListViewAdapter myRecyclerViewAdapter;

    public FridgeListViewModel(Application application) {
        super(application);
        repository = ((BasicApp) application).getRepository();
        fridgeContents = repository.getFridgeContentsLiveData();
        myRecyclerViewAdapter = new FridgeListViewAdapter(this, application);
    }

    public LiveData<List<FridgeListItem>> getFridgeContents() {
        return fridgeContents;
    }

    public FridgeListViewAdapter getAdapter() {
            return myRecyclerViewAdapter;
    }
}
