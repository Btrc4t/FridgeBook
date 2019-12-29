package com.buttercat.fridgebook.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.buttercat.fridgebook.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {FridgeListItem.class}, version = 1, exportSchema = false)
public abstract class FridgeContentsDatabase extends RoomDatabase {

    public abstract FridgeItemDao fridgeItemDao();
    //TODO remove this callback once database entries can be added from the app
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                FridgeItemDao dao = INSTANCE.fridgeItemDao();
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
    private static volatile FridgeContentsDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 1;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static FridgeContentsDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FridgeContentsDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FridgeContentsDatabase.class,
                            //TODO remove this callback once database entries can be added from the app
                            context.getResources().getString(R.string.database_name)).addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
