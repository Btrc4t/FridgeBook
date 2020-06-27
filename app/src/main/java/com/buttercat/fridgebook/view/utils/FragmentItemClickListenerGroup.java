package com.buttercat.fridgebook.view.utils;

import com.buttercat.fridgebook.ListItem;
import com.buttercat.fridgebook.model.Ingredient;
import com.buttercat.fridgebook.model.Recipe;

/**
 * Interface used by the {@link FridgeListViewAdapter} and {@link RecipesListViewAdapter}
 * to receive a click event on one {@link Ingredient} or {@link Recipe}
 */
public interface FragmentItemClickListenerGroup {
    /**
     * Method called when an item is clicked
     *
     * @param item the item which was clicked
     */
    void itemClicked(ListItem item);

    /**
     * Method called when an item is long clicked
     *
     * @param item the item which was long clicked
     */
    void itemLongClicked(ListItem item);
}
