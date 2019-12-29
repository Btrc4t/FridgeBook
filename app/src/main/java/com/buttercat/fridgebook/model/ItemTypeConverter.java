package com.buttercat.fridgebook.model;

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
