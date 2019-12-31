package com.buttercat.fridgebook.model;

import com.buttercat.fridgebook.model.database.FridgeContentsDatabase;
import com.buttercat.fridgebook.model.database.FridgeItemDao;
import com.buttercat.fridgebook.model.database.FridgeListItem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

public class FridgeRepository {

    private static FridgeRepository sInstance;
    private FridgeContentsDatabase mDatabase;

    private FridgeItemDao fridgeItemDao;
    private MediatorLiveData<List<FridgeListItem>> liveFridgeList;

    private FridgeRepository(final FridgeContentsDatabase fridgeContentsDatabase) {
        mDatabase = fridgeContentsDatabase;
        fridgeItemDao = mDatabase.fridgeItemDao();
        liveFridgeList = new MediatorLiveData<>();
        liveFridgeList.addSource(fridgeItemDao.getFridgeContentsLiveData(), productEntities -> {
            if (mDatabase.getDatabaseCreated().getValue() == true) {
                liveFridgeList.postValue(productEntities);
            }
        });
    }

    public static FridgeRepository getInstance(final FridgeContentsDatabase database) {
        if (sInstance == null) {
            synchronized (FridgeRepository.class) {
                if (sInstance == null) {
                    sInstance = new FridgeRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Room executes all queries on a separate thread.
     * Observed LiveData will notify the observer when the data has changed.
     */
    public LiveData<List<FridgeListItem>> getFridgeContentsLiveData() {
        return liveFridgeList;
    }

    /**
     * You must call this on a non-UI thread or your app will throw an exception. Room ensures
     * that you're not doing any long running operations on the main thread, blocking the UI.
     *
     * @param fridgeItem the {@link FridgeListItem} to be inserted into the database
     */
    public void insert(FridgeListItem fridgeItem) {

            fridgeItemDao.insert(fridgeItem);
    }

}
