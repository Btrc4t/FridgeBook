/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.buttercat.fridgebook.model.database.converter;

import com.buttercat.fridgebook.model.database.ItemType;

import androidx.room.TypeConverter;

public class ItemTypeConverter {

    @TypeConverter
    public static ItemType toItemType(int type) {
        if (type > ItemType.LIQUID.getType()) {
            throw new IllegalArgumentException("Could not recognize type");
        }
        return ItemType.fromInt(type);
    }

    @TypeConverter
    public static Integer toInteger(ItemType itemType) {
        return itemType.getType();
    }
}
