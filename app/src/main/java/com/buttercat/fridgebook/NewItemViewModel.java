/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.buttercat.fridgebook;

import android.app.Application;

import com.buttercat.fridgebook.model.AppExecutors;
import com.buttercat.fridgebook.model.BasicApp;
import com.buttercat.fridgebook.model.FridgeListItem;
import com.buttercat.fridgebook.model.FridgeListViewAdapter;
import com.buttercat.fridgebook.model.FridgeRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NewItemViewModel extends AndroidViewModel {

    private final AppExecutors executors;
    private FridgeRepository repository;

    public NewItemViewModel(Application application) {
        super(application);
        repository = ((BasicApp) application).getRepository();
        executors = ((BasicApp) application).getAppExecutors();
    }

    public void insert(FridgeListItem fridgeListItem) {
        executors.diskIO().execute(() -> repository.insert(fridgeListItem));
    }
}
