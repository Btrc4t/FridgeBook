package com.buttercat.fridgebook.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FridgeRepository {

        private FridgeItemDao fridgeItemDao;
        private LiveData<List<FridgeListItem>> liveFridgeList;

        // Note that in order to unit test the WordRepository, you have to remove the Application
        // dependency. This adds complexity and much more code, and this sample is not about testing.
        // See the BasicSample in the android-architecture-components repository at
        // https://github.com/googlesamples
        public FridgeRepository(Application application) {
            FridgeContentsDatabase db = FridgeContentsDatabase.getDatabase(application);
            fridgeItemDao = db.fridgeItemDao();
            liveFridgeList = fridgeItemDao.getFridgeContentsLiveData();
        }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
        public LiveData<List<FridgeListItem>> getFridgeContentsLiveData() {
            return liveFridgeList;
        }

        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        public void insert(FridgeListItem fridgeItem) {
            FridgeContentsDatabase.databaseWriteExecutor.execute(() -> {
                fridgeItemDao.insert(fridgeItem);
            });
        }

}
