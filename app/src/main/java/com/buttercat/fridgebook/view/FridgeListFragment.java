package com.buttercat.fridgebook.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.buttercat.fridgebook.databinding.FridgeListFragmentBinding;
import com.buttercat.fridgebook.model.Ingredient;
import com.buttercat.fridgebook.view.utils.FridgeItemClickListener;
import com.buttercat.fridgebook.view.utils.FridgeListViewAdapter;
import com.buttercat.fridgebook.viewmodel.FridgeListViewModel;

/**
 * A custom {@link Fragment} which shows the
 * {@link com.buttercat.fridgebook.view.utils.FridgeListViewAdapter}
 */
public class FridgeListFragment extends Fragment implements FridgeItemClickListener {
    /**
     * The databinding class which takes care of inflating the fragment
     */
    private FridgeListFragmentBinding fridgeListFragmentBinding;

    /**
     * Method returning a new instance of this class
     *
     * @return a new {@link FridgeListFragment} instance
     */
    /*package*/ static FridgeListFragment newInstance() {
        return new FridgeListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fridgeListFragmentBinding = FridgeListFragmentBinding
                .inflate(inflater, container, false);
        return fridgeListFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FridgeListViewModel mViewModel = new ViewModelProvider(this).get(FridgeListViewModel.class);
        fridgeListFragmentBinding.setLifecycleOwner(this);
        fridgeListFragmentBinding.setModel(mViewModel);
        fridgeListFragmentBinding.executePendingBindings();
        if (fridgeListFragmentBinding.fridgeList.getAdapter() != null) {
            ((FridgeListViewAdapter) fridgeListFragmentBinding.fridgeList.getAdapter())
                    .setFridgeItemClickListener(this);
        }
    }

    @Override
    public void fridgeItemClicked(Ingredient fridgeListItem) {
        Toast.makeText(this.getContext(), "You clicked " + fridgeListItem.getFridgeItemName(),
                Toast.LENGTH_SHORT).show();
        // TODO show ingredient details
    }

    @Override
    public void fridgeItemLongClicked(Ingredient fridgeListItem) {
        Toast.makeText(this.getContext(), "You long clicked " + fridgeListItem.getFridgeItemName(),
                Toast.LENGTH_SHORT).show();
        // TODO provide alter or delete option
    }
}
