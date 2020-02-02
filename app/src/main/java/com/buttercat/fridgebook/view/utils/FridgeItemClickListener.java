package com.buttercat.fridgebook.view.utils;

import com.buttercat.fridgebook.model.Ingredient;

/**
 * Interface used by the {@link FridgeListViewAdapter}
 * to receive a click event on one of the {@link Ingredient}
 */
public interface FridgeItemClickListener {
    /**
     * Method called when a {@link Ingredient} is clicked
     *
     * @param fridgeListItem the {@link Ingredient} which was clicked
     */
    void fridgeItemClicked(Ingredient fridgeListItem);
}
