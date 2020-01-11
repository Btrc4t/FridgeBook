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

    /**
     * Getter for the singleton {@link FridgeContentsDatabase}
     *
     * @return the singleton {@link FridgeContentsDatabase}
     */
    public FridgeContentsDatabase getDatabase() {
        return FridgeContentsDatabase.getInstance(this, mAppExecutors);
    }

    /**
     * Getter for the singleton {@link FridgeRepository}
     *
     * @return the singleton {@link FridgeRepository}
     */
    public FridgeRepository getRepository() {
        return FridgeRepository.getInstance(getDatabase());
    }

    /**
     * Getter for the {@link AppExecutors}
     *
     * @return the {@link AppExecutors} used throughout the application to execute background tasks
     */
    public AppExecutors getAppExecutors(){
        return mAppExecutors;
    }
}

