/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.buttercat.fridgebook.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buttercat.fridgebook.databinding.FridgeListFragmentBinding;
import com.buttercat.fridgebook.viewmodel.FridgeListViewModel;

public class FridgeListFragment extends Fragment {

    private FridgeListViewModel mViewModel;

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
        mViewModel = new ViewModelProvider(this).get(FridgeListViewModel.class);
        fridgeListFragmentBinding.setLifecycleOwner(this);
        fridgeListFragmentBinding.setModel(mViewModel);
    }
}
