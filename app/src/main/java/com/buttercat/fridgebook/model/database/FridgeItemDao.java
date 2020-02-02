package com.buttercat.fridgebook.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.buttercat.fridgebook.model.Ingredient;

import java.util.List;

/**
 * {@link androidx.room.Room} DAO for the fridge_items table
 */
@Dao
public interface FridgeItemDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(Ingredient fridgeListItem);

        @Query("DELETE FROM fridge_items")
        void deleteAll();

        @Query("SELECT * from fridge_items ORDER BY id ASC")
        List<Ingredient> getFridgeContents();

        @Query("SELECT * from fridge_items ORDER BY id ASC")
        LiveData<List<Ingredient>> getFridgeContentsLiveData();
}
