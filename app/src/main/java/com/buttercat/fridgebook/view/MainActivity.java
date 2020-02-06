package com.buttercat.fridgebook.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.buttercat.fridgebook.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FridgeListFragment.newInstance())
                    .commitNow();
        }
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
