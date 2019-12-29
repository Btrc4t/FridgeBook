package com.buttercat.fridgebook.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buttercat.fridgebook.databinding.ItemRowBinding;

import java.util.List;


public class FridgeListViewAdapter
        extends RecyclerView.Adapter<FridgeListViewAdapter.FridgeItemViewHolder>
        implements FridgeItemClickListener {

    private List<FridgeListItem> fridgeListItems;
    private Context context;

    public FridgeListViewAdapter(List<FridgeListItem> fridgeListItems, Context context) {
        this.fridgeListItems = fridgeListItems;
        this.context = context;
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
            itemRowBinding.setModel(fridgeListItem);
            itemRowBinding.executePendingBindings();
        }
    }

    public void fridgeItemClicked(FridgeListItem f) {
        Toast.makeText(context, "You clicked " + f.getItemName(),
                Toast.LENGTH_SHORT).show();
    }
}

