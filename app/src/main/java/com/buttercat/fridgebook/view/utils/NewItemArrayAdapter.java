package com.buttercat.fridgebook.view.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.buttercat.fridgebook.model.Ingredient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Custom Array adapter for a {@link android.widget.AutoCompleteTextView} which is purpose-built to
 * show {@link com.buttercat.fridgebook.model.apisource.SpoontacularApi} {@link Ingredient} objects
 */
public class NewItemArrayAdapter extends ArrayAdapter<Ingredient> {

    /**
     * Class name TAG used for logging
     */
    private static final String TAG = NewItemArrayAdapter.class.getSimpleName();
    /**
     * {@link Context} used by the {@link LayoutInflater} to inflate one item's view
     */
    private Context mContext;
    /**
     * The internal list of {@link Ingredient} objects to show as suggestions
     */
    private List<Ingredient> mIngredientList;
    /**
     * The last {@link Ingredient} processed by this adapter when an item is clicked in the
     * {@link android.widget.AutoCompleteTextView}
     */
    private Ingredient mLastSelectedIngredient;
    private IngredientCallback mIngredientCallback;

    /**
     * Constructor which uses a basic Android layout to show a dropdown of suggestions
     *
     * @param context {@link Context} used by the {@link LayoutInflater} to inflate one item's view
     * @param objects {@link List<Ingredient>} to be shown as suggestions
     */
    public NewItemArrayAdapter(IngredientCallback ingredientCallback, @NonNull Context context,
                               @NonNull List<Ingredient> objects) {
        super(context, android.R.layout.simple_dropdown_item_1line, objects);
        mContext = context;
        mIngredientList = objects;
        mIngredientCallback = ingredientCallback;
        setNotifyOnChange(true);
    }

    /**
     * @return the number of {@link Ingredient} objects in {@link #mIngredientList}
     */
    public int getCount() {
        return mIngredientList.size();
    }

    /**
     * Get an {@link Ingredient} from the internal list of ingredients
     *
     * @param position the position of the desired {@link Ingredient}
     *                 from the {@link #mIngredientList}
     * @return the {@link Ingredient} object found in {@link #mIngredientList} at the input position
     */
    public Ingredient getItem(int position) {
        return mIngredientList.get(position);
    }

    /**
     * Get an {@link Ingredient} ID from the internal list of ingredients
     *
     * @param position the position of the desired {@link Ingredient}
     *                 from the {@link #mIngredientList}
     * @return the {@link Ingredient} ID found in {@link #mIngredientList} at the input position
     */
    public long getItemId(int position) {
        return mIngredientList.get(position).getId();
    }

    /**
     * Set the internal list of items to be shown as {@link Ingredient} suggestions
     *
     * @param items the new {@link List<Ingredient>} to be copied by {@link #mIngredientList}
     */
    public void setItems(@NonNull Collection<Ingredient> items) {
        mIngredientList.clear();
        mIngredientList.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(
                    android.R.layout.simple_dropdown_item_1line, parent, false);
        }
        try {
            Ingredient ingredient = getItem(position);
            TextView name = convertView.findViewById(android.R.id.text1);
            name.setText(ingredient != null ? ingredient.getFridgeItemName() : "");
        } catch (Exception e) {
            Log.e(TAG, "getView: failed to process ingredient", e);
        }
        return convertView;
    }

    public Ingredient getTheLastSelectedIngredient() {
        return mLastSelectedIngredient;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Ingredient> ingredientSuggestions = new ArrayList<>();
                if (constraint != null) {
                    for (Ingredient ingredient : mIngredientList) {
                        if (ingredient.getFridgeItemName().toLowerCase()
                                .startsWith(constraint.toString().toLowerCase())) {
                            ingredientSuggestions.add(ingredient);
                        }
                    }
                    filterResults.values = ingredientSuggestions;
                    filterResults.count = ingredientSuggestions.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (constraint != null && results != null && results.count > 0) {
                    mIngredientList.clear();
                    for (Object object : (List<?>) results.values) {
                        if (object instanceof Ingredient) {
                            mIngredientList.add((Ingredient) object);
                        }
                    }
                    notifyDataSetChanged();
                } else {
                    // no filter, keep original list
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                mLastSelectedIngredient = (Ingredient) resultValue;
                mIngredientCallback.onIngredientSelected(mLastSelectedIngredient);
                return ((Ingredient) resultValue).getFridgeItemName();
            }
        };
    }
}
