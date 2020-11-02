package com.example.williamocampoproject3a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class A2Receiver extends BroadcastReceiver {

    private ArrayList<String> vacation_messages =
            new ArrayList<String>(Arrays.asList(
                    "Paris", "London", "Tokyo",
                    "Rome", "New York City"
            ));

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String intentAction = intent.getAction();

        if (intentAction != null) {
            String toastMessage = "Unknown intent action";
            switch (intentAction) {
                case MainActivity.INTENT_ACTION:
                    // Extract index
                    int index = intent.getIntExtra("INDEX", -1);

                    // Check if it's a valid index
                    if (index != -1) {
                        // Get toast message
                        toastMessage = vacation_messages.get(index);

                        // Display toast message
                        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                    } else {
                        toastMessage = "Please select a vacation spot first";
                        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                    }

                    break;
                default:
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
