package com.buttercat.fridgebook;

import android.app.Application;

import com.buttercat.fridgebook.model.AppExecutors;
import com.buttercat.fridgebook.model.FridgeRepository;
import com.buttercat.fridgebook.model.database.FridgeContentsDatabase;

/**
 * Android Application class. Used for accessing singletons.
 */
public class BasicApp extends Application {

    /**
     * {@link java.util.concurrent.Executor} objects to be used for the whole application
     */
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public FridgeContentsDatabase getDatabase() {
        return FridgeContentsDatabase.getInstance(this, mAppExecutors);
    }

    public FridgeRepository getRepository() {
        return FridgeRepository.getInstance(getDatabase());
    }

    public AppExecutors getAppExecutors(){
        return mAppExecutors;
    }
}

