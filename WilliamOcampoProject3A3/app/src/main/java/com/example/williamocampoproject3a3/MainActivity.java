package com.example.williamocampoproject3a3;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.williamocampoproject3a3.VacationListFragment.ListSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ListSelectionListener {

    // Intent filter action
    public static final String INTENT_ACTION= "com.example.williamocampoproject3a3";

    public int currIndex = -1; // Sent to A1 for processing

    public static String[] mListArray;
    public static ArrayList<Integer> mImageArray =
            new ArrayList<Integer>(Arrays.asList(
                    R.drawable.paris, R.drawable.londonnight, R.drawable.tokyo, R.drawable.rome,
                    R.drawable.york
            ));

    private final ImageFragment mImageFragment = new ImageFragment();

    // UB 2/24/2019 -- Android Pie twist: Original FragmentManager class is now deprecated.
    private FragmentManager fragmentManager;

    private FrameLayout mListFrameLayout, mImageFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ": entered onCreate()");

        super.onCreate(savedInstanceState);

        // Get the string arrays with the vacation spots
        mListArray = getResources().getStringArray(R.array.VacationSpots);

        setContentView(R.layout.activity_main);

        // Get references to the VacationListFragment and to the ImageFragment
        mListFrameLayout = (FrameLayout) findViewById(R.id.list_fragment_container);
        mImageFrameLayout = (FrameLayout) findViewById(R.id.image_fragment_container);

        // Get reference to the SupportFragmentManager instead of original FragmentManager
        fragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction with backward compatibility
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // Add the VacationListFragment to the layout
        // UB: 10/2/2016 Changed add() to replace() to avoid overlapping fragments
        fragmentTransaction.replace(
                R.id.list_fragment_container,
                new VacationListFragment());

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        // Add a OnBackStackChangedListener to reset the layout when the back stack changes
        // This can happen either (1) because the user made a selection before the
        // image fragment was shown or (2) because the user moved back after the image
        // fragment was shown.  These two cases are handled separately in setLayout() below.
        fragmentManager.addOnBackStackChangedListener(
                // UB 2/24/2019 -- Use support version of Listener
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });
    }

    private void setLayout() {
        // Determine whether the ImageFragment has been added
        if (!mImageFragment.isAdded()) {

            // Make the VacationListFragment occupy the entire layout
            mListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            mImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
        } else {
            int rotation = getWindowManager().getDefaultDisplay().getRotation();

            // PORTRAIT MODE
            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
                mListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
                mImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            } else { // LANDSCAPE MODE
                mListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
                mImageFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
            }
        }
    }

    // Implement Java interface ListSelectionListener defined in VacationListFragment
    // Called by VacationListFragment when the user selects an item in the VacationListFragment
    @Override
    public void onListSelection(int index) {
        // If the ImageFragment has not been added, add it now
        if (!mImageFragment.isAdded()) {

            // Start a new Fragment Transaction
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Add the ImageFragment to the layout
            fragmentTransaction.add(R.id.image_fragment_container, mImageFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            fragmentManager.executePendingTransactions();
        }

        // Tell the ImageFragment to show the quote string at position index
        mImageFragment.showQuoteAtIndex(index);

        // Store current index
        currIndex = index; // 0-4
    }

    // Set up options menu (called once only by OS)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    // Handle user interactions
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle item selection
        switch(item.getItemId()) {
            // Launch applications A1 and A2
            case R.id.launch:
                launchA1A2();
                return true;
            // Exit A3
            case R.id.exit:
                Toast.makeText(this, "Terminating...", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Broadcast a single, ordered intent to start A1 and A2
    public void launchA1A2() {
        Intent i = new Intent(INTENT_ACTION);
        i.putExtra("INDEX", currIndex);
        sendOrderedBroadcast(i, null);
    }

    // Handle change in config
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLayout();
    }

    // Add toolbar (otherwise buggy if added in onCreate)
    // Source: https://forums.xamarin.com/discussion/125522/toolbar-findviewbyid-resource-id-toolbar-toolbar-is-always-null
    @Override
    protected void onResume() {
        // Get reference to toolbar
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) this.findViewById(R.id.toolbar);
        if (toolbar != null) {

            // Set toolbar
            setSupportActionBar(toolbar);

            // Set toolbar image
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.sheep);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        super.onResume();
    }
}