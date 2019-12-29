package com.buttercat.fridgebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.buttercat.fridgebook.ui.main.FridgeListFragment;

public class MainActivity extends AppCompatActivity {

    //TODO Add Javadoc and logs for all the classes

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

    public void addButtonClicked(View view) {
        //TODO change fragment to a new fragment which creates an entry for the DB
    }
}
