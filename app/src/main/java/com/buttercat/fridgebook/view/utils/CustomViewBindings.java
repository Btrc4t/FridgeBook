package com.buttercat.fridgebook.view.utils;

import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;

/**
 * Class used to create custom {@link BindingAdapter} to particularize the functionality of databinding
 */
public class CustomViewBindings {
    /**
     * Changes some properties for the {@link RecyclerView}
     * and sets the appropiate {@link androidx.recyclerview.widget.RecyclerView.Adapter}
     *
     * @param recyclerView a {@link RecyclerView} which will have it's properties changed
     * @param adapter TODO
     */
    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    /**
     * TODO comment
     */
    @BindingAdapter("setAdapter")
    public static void bindAutoCompleteTextViewAdapter(AutoCompleteTextView autoCompleteTextView, NewItemArrayAdapter adapter) {
        autoCompleteTextView.setAdapter(adapter);
    }

    @BindingAdapter("android:text")
    public static void setDouble(TextView view, double value) {
        if (Double.isNaN(value)) {
            view.setText("");
        } else {
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setMaximumFractionDigits(3);
            view.setText(decimalFormat.format(value));
        }
    }
}
