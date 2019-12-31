/*
 * Copyright (c) 2019. Parrot Faurecia Automotive S.A.S. All rights reserved.
 */

package com.buttercat.fridgebook.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.buttercat.fridgebook.R;

public class MainActivity extends AppCompatActivity {

    //TODO Add Javadoc and logs for all the classes
    //TODO move classes around to respect MVVM architecture

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Log.i("INST", "onCreate: "+"savedInstance null "+(savedInstanceState == null));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, FridgeListFragment.newInstance())
                    .commitNow();
        }
    }

    public void addButtonClicked(View view) {
        startActivity(new Intent(this, NewItemActivity.class));
    }
}
