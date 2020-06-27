package com.buttercat.fridgebook.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.buttercat.fridgebook.ListItem;
import com.buttercat.fridgebook.databinding.RecipesListFragmentBinding;
import com.buttercat.fridgebook.model.Recipe;
import com.buttercat.fridgebook.view.utils.FragmentItemClickListenerGroup;
import com.buttercat.fridgebook.view.utils.FridgeListViewAdapter;
import com.buttercat.fridgebook.view.utils.RecipesListViewAdapter;
import com.buttercat.fridgebook.viewmodel.RecipesListViewModel;

/**
 * A custom {@link Fragment} which shows the
 * {@link FridgeListViewAdapter}
 */
public class RecipesListFragment extends Fragment implements FragmentItemClickListenerGroup {

    /**
     * Tag used to identify this class
     */
    public static final String TAG = "RecipesListFragment";
    /**
     * The databinding class which takes care of inflating the fragment
     */
    private RecipesListFragmentBinding recipesListFragmentBinding;

    /**
     * Method returning a new instance of this class
     *
     * @return a new {@link RecipesListFragment} instance
     */
    /*package*/
    static RecipesListFragment newInstance() {
        return new RecipesListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        recipesListFragmentBinding = RecipesListFragmentBinding
                .inflate(inflater, container, false);
        return recipesListFragmentBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("DCHG", "onCreate: created Recipes list");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecipesListViewModel mViewModel = new ViewModelProvider(this).get(RecipesListViewModel.class);
        recipesListFragmentBinding.setLifecycleOwner(this);
        recipesListFragmentBinding.setModel(mViewModel);
        recipesListFragmentBinding.executePendingBindings();
        if (recipesListFragmentBinding.recipesList.getAdapter() != null) {
            ((RecipesListViewAdapter) recipesListFragmentBinding.recipesList.getAdapter())
                    .setRecipeClickListeners(this);
        }
    }

    @Override
    public void itemClicked(ListItem item) {
        Toast.makeText(this.getContext(), "You clicked " + ((Recipe) item).getTitle(),
                Toast.LENGTH_SHORT).show();
        // TODO show recipe details
    }

    @Override
    public void itemLongClicked(ListItem item) {
        Toast.makeText(this.getContext(), "You long clicked " + ((Recipe) item).getTitle(),
                Toast.LENGTH_SHORT).show();
        // TODO provide alter or delete option
    }
}
