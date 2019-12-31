package com.buttercat.fridgebook.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.buttercat.fridgebook.R;

@Database(entities = {FridgeListItem.class}, version = 1, exportSchema = false)
public abstract class FridgeContentsDatabase extends RoomDatabase {

    public abstract FridgeItemDao fridgeItemDao();

    private static volatile FridgeContentsDatabase sInstance;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static FridgeContentsDatabase buildDatabase(final Context appContext,
                                                        final AppExecutors executors) {

        return Room.databaseBuilder(appContext, FridgeContentsDatabase.class,
                appContext.getResources().getString(R.string.database_name))
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Generate the data for pre-population
                            FridgeContentsDatabase database = FridgeContentsDatabase.getInstance(appContext, executors);
                            database.setDatabaseCreated();
                        });
                    }
                })
                .build();
    }

    static FridgeContentsDatabase getInstance(final Context context, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (FridgeContentsDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(context.getResources().getString(R.string.database_name)).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
