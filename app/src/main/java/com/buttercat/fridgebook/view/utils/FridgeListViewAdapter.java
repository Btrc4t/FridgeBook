/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.buttercat.fridgebook.view.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.buttercat.fridgebook.databinding.ItemRowBinding;
import com.buttercat.fridgebook.model.database.FridgeListItem;
import com.buttercat.fridgebook.viewmodel.FridgeListViewModel;

import java.util.ArrayList;
import java.util.List;


public class FridgeListViewAdapter
        extends RecyclerView.Adapter<FridgeListViewAdapter.FridgeItemViewHolder>
        implements FridgeItemClickListener {

    private FridgeListViewModel mainViewModel;
    private Context context;
    private List<FridgeListItem> fridgeListItems = new ArrayList<>();

    public FridgeListViewAdapter(FridgeListViewModel viewModel, Context context) {
        this.mainViewModel = viewModel;
        this.context = context;
        mainViewModel.getFridgeContents().observe(ProcessLifecycleOwner.get(), newFridgeItems -> {
            fridgeListItems = newFridgeItems;
            this.notifyDataSetChanged(); //TODO use DiffUtils
        });

    }

    @NonNull
    @Override
    public FridgeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowBinding binding = ItemRowBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FridgeItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FridgeItemViewHolder holder, int position) {
        FridgeListItem fridgeListItem = fridgeListItems.get(position);
        if (fridgeListItem == null) return;
        holder.bind(fridgeListItem);
        holder.itemRowBinding.setItemClickListener(this);
    }


    @Override
    public int getItemCount() {
        return fridgeListItems.size();
    }

    /*package*/ class FridgeItemViewHolder extends RecyclerView.ViewHolder {
        /*package*/ ItemRowBinding itemRowBinding;

        /*package*/ FridgeItemViewHolder(ItemRowBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
        }

        /*package*/ void bind(FridgeListItem fridgeListItem) {
            itemRowBinding.setItem(fridgeListItem);
            itemRowBinding.executePendingBindings();
        }
    }

    public void fridgeItemClicked(FridgeListItem f) {
        Toast.makeText(context, "You clicked " + f.getFridgeItemName(),
                Toast.LENGTH_SHORT).show();
    }
}

