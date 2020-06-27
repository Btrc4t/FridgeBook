package com.buttercat.fridgebook.view.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.buttercat.fridgebook.databinding.RecipeRowBinding;
import com.buttercat.fridgebook.model.Recipe;
import com.buttercat.fridgebook.viewmodel.RecipesListViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecipesListViewAdapter
        extends RecyclerView.Adapter<RecipesListViewAdapter.RecipeItemViewHolder> {

    private static Picasso myPicasso;
    /**
     * The {@link androidx.lifecycle.AndroidViewModel} used to obtain a list of {@link Ingredient}
     */
    private RecipesListViewModel mainViewModel;
    /**
     * The list of {@link Ingredient} objects to be shown in this adapter
     */
    private List<Recipe> recipeListItems = new ArrayList<>();

    private FragmentItemClickListenerGroup mFragmentItemClickListenerGroup;

    /**
     * Constructor which creates an {@link java.util.Observer} for the list of {@link Ingredient}
     *
     * @param viewModel a reference for the {@link androidx.lifecycle.AndroidViewModel} which provides
     *                  the list of {@link Ingredient} objects
     */
    public RecipesListViewAdapter(RecipesListViewModel viewModel) {
        mainViewModel = viewModel;
        mainViewModel.getRecipes().observe(ProcessLifecycleOwner.get(), fetchedRecipes -> {
            recipeListItems = fetchedRecipes;
            this.notifyDataSetChanged(); //TODO use DiffUtils
        });

    }

    @NonNull
    @Override
    public RecipeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeRowBinding binding = RecipeRowBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RecipeItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecipeItemViewHolder holder, int position) {
        Recipe recipeItem = recipeListItems.get(position);
        if (recipeItem == null) return;
        holder.bind(recipeItem);
    }

    @Override
    public int getItemCount() {
        return recipeListItems != null ? recipeListItems.size() : 0;
    }

    /*package*/ class RecipeItemViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {
        /*package*/ RecipeRowBinding recipeRowBinding;

        /*package*/ RecipeItemViewHolder(RecipeRowBinding recipeRowBinding) {
            super(recipeRowBinding.getRoot());
            this.recipeRowBinding = recipeRowBinding;
            recipeRowBinding.getRoot().setOnClickListener(this::onClick);
            recipeRowBinding.getRoot().setOnLongClickListener(this::onLongClick);
        }

        /*package*/ void bind(Recipe recipe) {
            recipeRowBinding.setRecipe(recipe);
            recipeRowBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            mFragmentItemClickListenerGroup.itemClicked(recipeRowBinding.getRecipe());
        }

        @Override
        public boolean onLongClick(View v) {
            mFragmentItemClickListenerGroup.itemLongClicked(recipeRowBinding.getRecipe());
            return true;
        }
    }

    /**
     * Method used to set a click listener callback which will return the {@link Recipe}
     * which was clicked or long clicked by the user
     *
     * @param fragmentItemClickListenerGroup the {@link FragmentItemClickListenerGroup} callback
     */
    public void setRecipeClickListeners(FragmentItemClickListenerGroup fragmentItemClickListenerGroup) {
        mFragmentItemClickListenerGroup = fragmentItemClickListenerGroup;
    }
}

