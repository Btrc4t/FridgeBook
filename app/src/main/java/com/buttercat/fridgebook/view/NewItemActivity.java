package com.buttercat.fridgebook.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.buttercat.fridgebook.R;
import com.buttercat.fridgebook.databinding.ActivityNewItemBinding;
import com.buttercat.fridgebook.model.apisource.model.Ingredient;
import com.buttercat.fridgebook.model.database.FridgeListItem;
import com.buttercat.fridgebook.model.database.ItemType;
import com.buttercat.fridgebook.view.utils.IngredientCallback;
import com.buttercat.fridgebook.view.utils.NewItemArrayAdapter;
import com.buttercat.fridgebook.viewmodel.NewItemViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity which gathers input from the user to create a new {@link FridgeListItem} and
 * store it in the database.
 */
public class NewItemActivity extends AppCompatActivity implements IngredientCallback {

    private static final String TAG = NewItemActivity.class.getSimpleName();
    /**
     * An {@link androidx.lifecycle.AndroidViewModel} for the activity
     */
    private NewItemViewModel mNewItemViewModel;
    /**
     * The databinding class which takes care of inflating the activity
     */
    private ActivityNewItemBinding mBinding;
    /**
     * A {@link android.widget.ArrayAdapter} used to show {@link Ingredient} suggestions
     */
    private NewItemArrayAdapter mSuggestionsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_item);
        mNewItemViewModel = new ViewModelProvider(this).get(NewItemViewModel.class);
        mSuggestionsAdapter = new NewItemArrayAdapter(this, this,
                new ArrayList<>());
        mBinding.editIngredient.setAdapter(mSuggestionsAdapter);
        mBinding.editIngredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < 2) return; // Don't query the API with less than 2 characters
                // Fetch 5 ingredients
                // TODO remove magic numbers
                mNewItemViewModel.fetchIngredientsWithQuery(s.toString(), 5,
                        new Callback<List<Ingredient>>() {
                    @Override
                    public void onResponse(Call<List<Ingredient>> call,
                                           Response<List<Ingredient>> response) {
                        if (response.body() == null) {
                            Log.i(TAG, "onResponse: null body");
                            return;
                        }
                        runOnUiThread(() -> mSuggestionsAdapter.setItems(response.body()));
                    }

                    @Override
                    public void onFailure(Call<List<Ingredient>> call, Throwable t) {
                        Log.e(TAG, "onFailure: failed to obtain a list of ingredients",t);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    /**
     * Method which is called when the save button is clicked, saves the user input in a new
     * {@link FridgeListItem} and inserts it into the database then closes the activity.
     *
     * @param view the {@link View} which was clicked
     */
    public void saveButtonClicked(View view) {
        String selectedIngredient = mBinding.editIngredient.getText().toString();
        String selectedQuantity = mBinding.editQuantity.getText().toString();
        if (TextUtils.isEmpty(selectedIngredient)) {
            //TODO alert user
            return;
        } else if (TextUtils.isEmpty(selectedQuantity)) {
            //TODO alert user
            return;
        }
        if (!mSuggestionsAdapter.getTheLastSelectedIngredient().getName()
                .contentEquals(selectedIngredient)) {
            //TODO alert user (for the first version we can't store unknown ingredients)
            return;
        }

        String itemName = mBinding.editIngredient.getText().toString();
        String quantity = mBinding.editQuantity.getText().toString();
        mNewItemViewModel.insert(new FridgeListItem(itemName, quantity, ItemType.SOLID));
        this.finish();
    }

    @Override
    public void onIngredientSelected(Ingredient ingredientSelected) {
        //TODO modify activity elements to match ingredient e.g. unit type
    }
}