package com.buttercat.fridgebook;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.buttercat.fridgebook.databinding.ActivityNewItemBinding;
import com.buttercat.fridgebook.model.FridgeListItem;
import com.buttercat.fridgebook.model.ItemType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

public class NewItemActivity extends AppCompatActivity {

    private NewItemViewModel mNewItemViewModel;
    private ActivityNewItemBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_new_item);
        mNewItemViewModel = new ViewModelProvider(this).get(NewItemViewModel.class);
    }

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