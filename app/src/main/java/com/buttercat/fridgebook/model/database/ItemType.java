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
    /**
     * Field containing the value of this enum
     */
    private int type;

    /**
     * Creates an object using an input value
     *
     * @param value to be associated with an item type
     */
    ItemType(int value) {
        this.type = value;
    }

    /**
     * Returns an object created from an input value.
     *
     * @param value to be associated with an item type
     * @return object for the input value
     */
    public static ItemType fromInt(int value) {
        for (ItemType item : ItemType.values()) {
            if (item.type == value) {
                return item;
            }
        }
        return null;
    }

    /**
     * Returns the value associated with the item
     *
     * @return value associated with the item
     */
    public int getType() {
        return type;
    }
}
