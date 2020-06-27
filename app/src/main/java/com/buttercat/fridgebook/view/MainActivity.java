package com.buttercat.fridgebook.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.buttercat.fridgebook.R;
import com.buttercat.fridgebook.databinding.MainActivityBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Map;

/**
 * The activity shown first on the screen, housing and managing all of the other {@link Fragment}s
 */
public class MainActivity extends AppCompatActivity {

    /**
     * A map with entries having a resource ID from {@link R.menu#navigation} as a key and the
     * corresponding {@link Fragment}
     */
    private Map<Integer, Fragment> menuFragmentsMapping = new ArrayMap<>();
    /**
     * A key used to save the currently selected menu item from {@link R.menu#navigation} when
     * {@link #onSaveInstanceState(Bundle)} is called
     */
    private static final String NAVIGATION_MENU = "nav_id";
    /**
     * The last selected menu item from {@link R.menu#navigation}
     */
    private int lastSelectedItem = R.id.nav_ingredients_list;

    /**
     * {@link R.id#navigation} listener which changes the shown fragment based
     * on the selected item
     */
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationListener
            = item -> {
        lastSelectedItem = item.getItemId();
        FragmentTransaction menuChangeTransaction = getSupportFragmentManager().beginTransaction();
        for (Map.Entry<Integer, Fragment> entry : menuFragmentsMapping.entrySet()) {
            Integer resId = entry.getKey();
            Fragment fragment = entry.getValue();
            if (resId == item.getItemId()) {
                menuChangeTransaction.show(fragment);
            } else {
                menuChangeTransaction.hide(fragment);
            }
        }
        menuChangeTransaction.commit();
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivityBinding mainActivityBinding = DataBindingUtil
                .setContentView(this, R.layout.main_activity);
        menuFragmentsMapping.put(R.id.nav_ingredients_list, FridgeListFragment.newInstance());
        menuFragmentsMapping.put(R.id.nav_recipes_list, RecipesListFragment.newInstance());
        if (savedInstanceState != null) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            getSupportFragmentManager().executePendingTransactions();
            lastSelectedItem = savedInstanceState.getInt(NAVIGATION_MENU);
        }
        initAllFragments(lastSelectedItem);
        mainActivityBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationListener);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(NAVIGATION_MENU, lastSelectedItem);
        super.onSaveInstanceState(outState);
    }

    /**
     * Initializes all of the fragments shown in the application in background, keeping only one
     * visible
     *
     * @param idOfInitFragment Startup menu item from {@link R.menu#navigation}
     *                         for which the corresponding {@link Fragment} is kept visible
     */
    private void initAllFragments(int idOfInitFragment) {
        FragmentTransaction initFragmentTransaction = getSupportFragmentManager().beginTransaction();

        for (Map.Entry<Integer, Fragment> entry : menuFragmentsMapping.entrySet()) {
            Integer resId = entry.getKey();
            Fragment fragment = entry.getValue();
            initFragmentTransaction.add(R.id.container, fragment, String.valueOf(resId));
            if (resId != idOfInitFragment) {
                initFragmentTransaction.hide(fragment);
            }
        }
        initFragmentTransaction.commit();
    }

    /**
     * Method called when the floating button used to add an item is clicked.
     * Starts a {@link NewItemActivity}
     *
     * @param view the floating button clicked
     */
    public void addButtonClicked(View view) {
        startActivity(new Intent(this, NewItemActivity.class));
    }
}
