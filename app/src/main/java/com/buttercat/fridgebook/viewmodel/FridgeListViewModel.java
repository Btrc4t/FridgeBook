/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.buttercat.fridgebook.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.buttercat.fridgebook.BasicApp;
import com.buttercat.fridgebook.model.database.FridgeListItem;
import com.buttercat.fridgebook.view.utils.FridgeListViewAdapter;
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
