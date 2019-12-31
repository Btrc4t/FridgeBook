/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.buttercat.fridgebook.model.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FridgeItemDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(FridgeListItem fridgeListItem);

        @Query("DELETE FROM fridge_items")
        void deleteAll();

        @Query("SELECT * from fridge_items ORDER BY itemType ASC")
        List<FridgeListItem> getFridgeContents();

        @Query("SELECT * from fridge_items ORDER BY itemType ASC")
        LiveData<List<FridgeListItem>> getFridgeContentsLiveData();
}
