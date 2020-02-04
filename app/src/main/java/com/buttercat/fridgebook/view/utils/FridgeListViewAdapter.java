package com.buttercat.fridgebook.view.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.buttercat.fridgebook.databinding.ItemRowBinding;
import com.buttercat.fridgebook.model.Ingredient;
import com.buttercat.fridgebook.viewmodel.FridgeListViewModel;

import java.util.ArrayList;
import java.util.List;


public class FridgeListViewAdapter
        extends RecyclerView.Adapter<FridgeListViewAdapter.FridgeItemViewHolder> {

    /**
     * The {@link androidx.lifecycle.AndroidViewModel} used to obtain a list of {@link Ingredient}
     */
    private FridgeListViewModel mainViewModel;
    /**
     * A {@link Context} used to show a toast TODO remove this
     */
    private Context context;
    /**
     * The list of {@link Ingredient} objects to be shown in this adapter
     */
    private List<Ingredient> fridgeListItems = new ArrayList<>();

    private FridgeItemClickListener mFridgeItemClickListener;

    /**
     * Constructor which creates an {@link java.util.Observer} for the list of {@link Ingredient}
     *
     * @param viewModel a reference for the {@link androidx.lifecycle.AndroidViewModel} which provides
     *                  the list of {@link Ingredient} objects
     * @param context a reference for the {@link Context} used to show a toast TODO remove this
     */
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
        Ingredient fridgeListItem = fridgeListItems.get(position);
        if (fridgeListItem == null) return;
        holder.bind(fridgeListItem);
    }


    @Override
    public int getItemCount() {
        return fridgeListItems.size();
    }

    /*package*/ class FridgeItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        /*package*/ ItemRowBinding itemRowBinding;

        /*package*/ FridgeItemViewHolder(ItemRowBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.itemRowBinding = itemRowBinding;
            itemRowBinding.getRoot().setOnClickListener(this::onClick);
            itemRowBinding.getRoot().setOnLongClickListener(this::onLongClick);
        }

        /*package*/ void bind(Ingredient fridgeListItem) {
            itemRowBinding.setItem(fridgeListItem);
            itemRowBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            mFridgeItemClickListener.fridgeItemClicked(itemRowBinding.getItem());
        }

        @Override
        public boolean onLongClick(View v) {
            mFridgeItemClickListener.fridgeItemLongClicked(itemRowBinding.getItem());
            return true;
        }
    }

    /**
     * Method used to set a click listener callback which will return the {@link Ingredient}
     * which was clicked or long clicked by the user
     *
     * @param fridgeItemClickListener the {@link FridgeItemClickListener} callback
     */
    public void setFridgeItemClickListener(FridgeItemClickListener fridgeItemClickListener) {
        mFridgeItemClickListener = fridgeItemClickListener;
    }
}

