package com.buttercat.fridgebook.view.utils;

import com.buttercat.fridgebook.model.database.FridgeListItem;

/**
 * Interface used by the {@link FridgeListViewAdapter} to receive a click event on one of the {@link FridgeListItem}
 */
public interface FridgeItemClickListener {
    /**
     * Method called when a {@link FridgeListItem} is clicked
     *
     * @param fridgeListItem the {@link FridgeListItem} which was clicked
     */
    void fridgeItemClicked(FridgeListItem fridgeListItem);
}
