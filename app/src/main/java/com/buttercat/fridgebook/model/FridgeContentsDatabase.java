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
        //TODO remove this callback once database entries can be added from the app
        RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);

                // If you want to keep data through app restarts,
                // comment out the following block
                executors.diskIO().execute(() -> {
                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    FridgeItemDao dao = sInstance.fridgeItemDao();
                    dao.deleteAll();

                    dao.insert(new FridgeListItem("Oreo", "81g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Pie", "90g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Nougat", "70g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Marshmallow", "60g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Oreo2", "81g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Pie2", "90g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Nougat2", "70g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Marshmallow2", "60g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Oreo3", "81g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Pie3", "90g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Nougat3", "70g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Marshmallow3", "60g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Oreo4", "81g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Pie4", "90g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Nougat4", "70g", ItemType.SOLID));
                    dao.insert(new FridgeListItem("Marshmallow4", "60g", ItemType.SOLID));
                });
            }
        };

        return Room.databaseBuilder(appContext, FridgeContentsDatabase.class,
                appContext.getResources().getString(R.string.database_name))
                //TODO remove this callback once database entries can be added from the app
                .addCallback(sRoomDatabaseCallback)
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
