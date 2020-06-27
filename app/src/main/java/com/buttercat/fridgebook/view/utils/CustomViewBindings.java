package com.buttercat.fridgebook.view.utils;

import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.buttercat.fridgebook.model.apisource.SpoontacularApi;
import com.squareup.picasso.Picasso;

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
     * Automatically assigns the {@link NewItemArrayAdapter} for a {@link AutoCompleteTextView}
     *
     * @param autoCompleteTextView any {@link AutoCompleteTextView}
     * @param adapter the {@link NewItemArrayAdapter} which will be assigned as an adapter
     */
    @BindingAdapter("setAdapter")
    public static void bindAutoCompleteTextViewAdapter(AutoCompleteTextView autoCompleteTextView, NewItemArrayAdapter adapter) {
        autoCompleteTextView.setAdapter(adapter);
    }

    /**
     * Automatically downloads an image from an URL, creates a rounded bitmap and
     * places it into the view
     *
     * @param view {@link ImageView} which needs the image from the imageUrl
     * @param imageUrl the URL where the image can be found
     */
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.get().load(SpoontacularApi.generateImageUrlForItem250px(imageUrl))
                .transform(new CircleTransform())
                .into(view);
    }

    /**
     * Automatically converts double values into text. The double value will have 3 fraction digits
     *
     * @param view {@link TextView} which will show the double value
     * @param value the value which will be formatted and shown
     */
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
