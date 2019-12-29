package com.buttercat.fridgebook.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buttercat.fridgebook.databinding.FridgeListFragmentBinding;
import com.buttercat.fridgebook.model.FridgeListItem;
import com.buttercat.fridgebook.model.FridgeListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class FridgeListFragment extends Fragment {

    private MainViewModel mViewModel;

    private FridgeListFragmentBinding fridgeListFragmentBinding;

    public static FridgeListFragment newInstance() {
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
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
        populateData();
    }

    //TODO remove when database is implemented
    private void populateData() {
        List<FridgeListItem> dataModelList = new ArrayList<>();

        dataModelList.add(new FridgeListItem("Oreo", "81g"));
        dataModelList.add(new FridgeListItem("Pie", "90g"));
        dataModelList.add(new FridgeListItem("Nougat", "70g"));
        dataModelList.add(new FridgeListItem("Marshmallow", "60g"));

        FridgeListViewAdapter myRecyclerViewAdapter = new FridgeListViewAdapter(dataModelList, getContext());
        fridgeListFragmentBinding.fridgeList.setLayoutManager(new LinearLayoutManager(getContext()));
        fridgeListFragmentBinding.fridgeList.setAdapter(myRecyclerViewAdapter);
    }

}
