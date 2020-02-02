package com.buttercat.fridgebook.model.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.buttercat.fridgebook.R;
import com.buttercat.fridgebook.model.AppExecutors;
import com.buttercat.fridgebook.model.Ingredient;

/**
 * The {@link RoomDatabase} containing {@link Ingredient} entities
 */
@Database(entities = {Ingredient.class}, version = 2, exportSchema = false)
public abstract class FridgeContentsDatabase extends RoomDatabase {
    /**
     * A {@link Room} DAO used to perform predefined database operations
     */
    public abstract FridgeItemDao fridgeItemDao();
    /**
     * Singleton instance of the database
     */
    private static volatile FridgeContentsDatabase sInstance;
    /**
     * A {@link MutableLiveData<Boolean>} storing a flag which is true when the database was created
     */
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    /**
     * {@link Room} Migration from version 1 to version 2. Deleting all data.
     */
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE `fridge_items`");
            database.execSQL("CREATE TABLE `fridge_items` (`id` INTEGER NOT NULL, " +
                    "`fridgeItemName` TEXT, `quantity` REAL NOT NULL, `aisle` TEXT, `unit` TEXT, " +
                    "`image` TEXT, PRIMARY KEY(`id`))");
        }
    };



    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     *
     * @param appContext a {@link Context} used to obtain a {@link FridgeContentsDatabase} instance
     * @param executors {@link AppExecutors} used to obtain the executor handling database
     *                                      operations
     *
     * @return a singleton instance of the {@link FridgeContentsDatabase}
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
                            FridgeContentsDatabase database = FridgeContentsDatabase.getInstance(appContext, executors);
                            database.setDatabaseCreated();
                        });
                    }
                })
                .addMigrations(MIGRATION_1_2)
                .build();
    }

    /**
     * @param context a {@link Context} used to obtain a {@link FridgeContentsDatabase} instance
     * @param executors {@link AppExecutors} used to obtain the executor handling database operations
     *
     * @return the singleton instance of {@link FridgeContentsDatabase}
     */
    public static FridgeContentsDatabase getInstance(final Context context,
                                                     AppExecutors executors) {
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
     *
     * @param context a {@link Context} used to obtain a string resource
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(context.getResources().getString(R.string.database_name)).exists()) {
            setDatabaseCreated();
        }
    }

    /**
     * Posts to the internal {@link #mIsDatabaseCreated} a value which is true, indicating that
     * the database was created.
     */
    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    /**
     * Provides information about the creation status of the database
     *
     * @return {@link LiveData<Boolean>} which stores a flag that is true when the database was
     * created
     */
    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
