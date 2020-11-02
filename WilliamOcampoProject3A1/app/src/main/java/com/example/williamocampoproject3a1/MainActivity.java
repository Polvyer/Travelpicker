package com.example.williamocampoproject3a1;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // Register a receiver dynamically
    private A1Receiver mReceiver;

    // Receiver registered?
    public boolean receiverRegistered = false;

    // Create an intent filter
    private IntentFilter filter;

    // Intent filter action
    public static final String INTENT_ACTION = "com.example.williamocampoproject3a3";

    // A2 package name
    public static final String A2_PACKAGE_NAME = "com.example.williamocampoproject3a2";

    // A3 package name
    public static final String A3_PACKAGE_NAME = "com.example.williamocampoproject3a3";

    // Permission name
    public static final String PERMISSION = "edu.uic.cs478.f20.kaboom";

    // Browser opened?
    public static boolean webIntentStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "A1 created", Toast.LENGTH_LONG);

        // Only runs if A1 already has permission (from previous grant)
        int permissionCheck = ContextCompat.checkSelfPermission(this, PERMISSION);
        if (PackageManager.PERMISSION_GRANTED == permissionCheck) {

            // Create and register receiver
            createAndRegisterReceiver();
        }

        // Set action bar image
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.sheep);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Only runs if A1 already has permission (from previous grant)
        int permissionCheck = ContextCompat.checkSelfPermission(this, PERMISSION);
        if (PackageManager.PERMISSION_GRANTED == permissionCheck) {

            // Create and register receiver
            createAndRegisterReceiver();


            // Only runs if A1's second activity ran recently
            if (webIntentStarted == true) {
                //Toast.makeText(this, "Returning to A3", Toast.LENGTH_SHORT).show();
                webIntentStarted = false;
                Intent intent = getPackageManager().getLaunchIntentForPackage(A3_PACKAGE_NAME);
                startActivity(intent);
            }

        }
    }

    // Check whether app A1 has obtained dangerous permission edu.uic.cs478.f20.kaboom
    public void checkPermission(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, PERMISSION);
        if (PackageManager.PERMISSION_GRANTED == permissionCheck) {
            createAndRegisterReceiver();
            launchA2();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{ PERMISSION }, 1);
        }
    }

    // Manage request results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    createAndRegisterReceiver();
                    launchA2();
                } else {
                    Toast.makeText(this, "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // Create and register its broadcast receiver programmatically
    public void createAndRegisterReceiver() {

        // Check if receiver is registered already
        if (receiverRegistered == false) {

            // Instantiate receiver
            mReceiver = new A1Receiver();

            // Instantiate filter
            filter = new IntentFilter();

            // Specify the types of intents a component can receive
            filter.addAction(INTENT_ACTION);

            // Set filter priority (A2 > A1)
            filter.setPriority(50); // A1

            // Register the receiver using the activity context
            this.registerReceiver(mReceiver, filter);
            receiverRegistered = true;
        }
    }

    // Launch app A2
    public void launchA2 () {
        Intent intent = getPackageManager().getLaunchIntentForPackage(A2_PACKAGE_NAME);
        startActivity(intent);
    }

    // Unregister the receiver
    @Override
    protected void onDestroy() {
        /*
        this.unregisterReceiver(mReceiver);
        receiverRegistered = false;
        */
        super.onDestroy();
    }
}