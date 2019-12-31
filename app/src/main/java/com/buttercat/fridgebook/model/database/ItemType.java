/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.buttercat.fridgebook.model.database;

/**
 * The type of an item (liquid/solid)
 */
public enum ItemType {

    /**
     * Solid item, measured in grams by default
     */
    SOLID(0),
    /**
     * Liquid item, measured in milliliters by default
     */
    LIQUID(1);

    private int type;

    ItemType(int value) {
        this.type = value;
    }

    public static ItemType fromInt(int value) {
        for (ItemType item : ItemType.values()) {
            if (item.type == value) {
                return item;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }
}
