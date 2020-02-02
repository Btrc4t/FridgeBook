package com.buttercat.fridgebook.view.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.buttercat.fridgebook.databinding.ItemRowBinding;
import com.buttercat.fridgebook.model.Ingredient;
import com.buttercat.fridgebook.viewmodel.FridgeListViewModel;

import java.util.ArrayList;
import java.util.List;


public class FridgeListViewAdapter
        extends RecyclerView.Adapter<FridgeListViewAdapter.FridgeItemViewHolder>
        implements FridgeItemClickListener {

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

        /*package*/ void bind(Ingredient fridgeListItem) {
            itemRowBinding.setItem(fridgeListItem);
            itemRowBinding.executePendingBindings();
        }
    }

    /**
     * Method called when a {@link Ingredient} is clicked, showing a {@link Toast} as feedback
     * to the user
     *
     * @param fridgeListItem the {@link Ingredient} which was clicked
     */
    public void fridgeItemClicked(Ingredient fridgeListItem) {
        Toast.makeText(context, "You clicked " + fridgeListItem.getFridgeItemName(),
                Toast.LENGTH_SHORT).show();
    }
}

