package com.buttercat.fridgebook.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.buttercat.fridgebook.R;
import com.buttercat.fridgebook.databinding.ActivityNewItemBinding;
import com.buttercat.fridgebook.model.Ingredient;
import com.buttercat.fridgebook.view.utils.NewItemArrayAdapter;
import com.buttercat.fridgebook.viewmodel.NewItemViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * An activity which gathers input from the user to create a new {@link Ingredient} and
 * store it in the database.
 */
public class NewItemActivity extends AppCompatActivity {

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
    /**
     * A {@link android.widget.ArrayAdapter} used to show {@link Ingredient}'s possible units
     */
    private ArrayAdapter<CharSequence> mUnitsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_item);
        mNewItemViewModel = new ViewModelProvider(this).get(NewItemViewModel.class);
        mSuggestionsAdapter = new NewItemArrayAdapter(this,
                new ArrayList<>());
        mBinding.editIngredient.setAdapter(mSuggestionsAdapter);

        mUnitsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());
        mBinding.possibleUnits.setAdapter(mUnitsAdapter);

        mBinding.editIngredient.setOnItemClickListener((parent, view, position, id) -> {
            Ingredient selectedIngredient = mSuggestionsAdapter.getItem(position);
            if (selectedIngredient == null) return;
            onIngredientSelected(selectedIngredient);
        });

        mBinding.editIngredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (before > count && mBinding.possibleUnits.getVisibility() == View.VISIBLE) {
                    mBinding.possibleUnits.setVisibility(View.GONE);
                }
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
                                Log.e(TAG, "onFailure: failed to obtain a list of ingredients", t);
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * Method which is called when the save button is clicked, saves the user input in a new
     * {@link Ingredient} and inserts it into the database then closes the activity.
     *
     * @param view the {@link View} which was clicked
     */
    public void saveButtonClicked(View view) {

        String selectedIngredientName = mBinding.editIngredient.getText().toString();
        String selectedQuantity = mBinding.editQuantity.getText().toString();
        if (TextUtils.isEmpty(selectedIngredientName)) {
            //TODO alert user
            return;
        } else if (TextUtils.isEmpty(selectedQuantity)) {
            //TODO alert user
            return;
        }
        if (!mSuggestionsAdapter.getTheLastSelectedIngredient().getFridgeItemName()
                .contentEquals(selectedIngredientName)) {
            //TODO alert user (for the first version we can't store unknown ingredients)
            return;
        }


        Ingredient selectedIngredient = mSuggestionsAdapter.getTheLastSelectedIngredient();
        String quantity = mBinding.editQuantity.getText()
                + " " + mBinding.possibleUnits.getSelectedItem().toString();
        selectedIngredient.setQuantity(quantity);
        mNewItemViewModel.insert(selectedIngredient);
        this.finish();
    }

    private void onIngredientSelected(@NonNull Ingredient ingredientSelected) {
        if (!ingredientSelected.getFridgeItemName()
                .equalsIgnoreCase(mBinding.editIngredient.getText().toString())) return;
        if (mBinding.possibleUnits.getVisibility() == View.GONE) {
            mBinding.possibleUnits.setVisibility(View.VISIBLE);
        }
        if (ingredientSelected.getPossibleUnits() != null) {
            mUnitsAdapter.clear();
            mUnitsAdapter.addAll(ingredientSelected.getPossibleUnits());
            mUnitsAdapter.notifyDataSetChanged();
        }
    }
}