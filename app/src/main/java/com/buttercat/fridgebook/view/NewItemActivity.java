package com.buttercat.fridgebook.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.buttercat.fridgebook.R;
import com.buttercat.fridgebook.databinding.ActivityNewItemBinding;
import com.buttercat.fridgebook.model.database.FridgeListItem;
import com.buttercat.fridgebook.model.database.ItemType;
import com.buttercat.fridgebook.viewmodel.NewItemViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

/**
 * An activity which gathers input from the user to create a new {@link FridgeListItem} and
 * store it in the database.
 */
public class NewItemActivity extends AppCompatActivity {

    /**
     * An {@link androidx.lifecycle.AndroidViewModel} for the activity
     */
    private NewItemViewModel mNewItemViewModel;
    /**
     * The databinding class which takes care of inflating the activity
     */
    private ActivityNewItemBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_item);
        mNewItemViewModel = new ViewModelProvider(this).get(NewItemViewModel.class);
    }

    /**
     * Method which is called when the save button is clicked, saves the user input in a new
     * {@link FridgeListItem} and inserts it into the database then closes the activity.
     *
     * @param view the {@link View} which was clicked
     */
    public void saveButtonClicked(View view) {
        if (TextUtils.isEmpty(mBinding.editIngredient.getText().toString())) {
            //TODO alert user
            return;
        }
        if (TextUtils.isEmpty(mBinding.editQuantity.getText().toString())) {
            //TODO alert user
            return;
        }

        String itemName = mBinding.editIngredient.getText().toString();
        String quantity = mBinding.editQuantity.getText().toString();
        mNewItemViewModel.insert(new FridgeListItem(itemName, quantity, ItemType.SOLID));
        this.finish();
    }
}