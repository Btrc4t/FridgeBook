package com.buttercat.fridgebook.ui.main;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.buttercat.fridgebook.model.BasicApp;
import com.buttercat.fridgebook.model.FridgeListItem;
import com.buttercat.fridgebook.model.FridgeListViewAdapter;
import com.buttercat.fridgebook.model.FridgeRepository;

import java.util.List;

public class FridgeListViewModel extends AndroidViewModel {

    private FridgeRepository repository;

    private LiveData<List<FridgeListItem>> fridgeContents;

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
