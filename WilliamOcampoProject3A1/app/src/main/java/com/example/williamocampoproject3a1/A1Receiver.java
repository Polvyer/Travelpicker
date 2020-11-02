package com.example.williamocampoproject3a1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;

public class A1Receiver extends BroadcastReceiver {

    private ArrayList<String> vacation_urls =
            new ArrayList<String>(Arrays.asList(
                    "https://en.parisinfo.com/", "https://www.visitlondon.com/", "https://www.japan-guide.com/e/e2164.html",
                    "https://www.rome.net/", "https://www.iloveny.com/places-to-go/new-york-city/"
            ));

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String intentAction = intent.getAction();

        if (intentAction != null) {
            //String toastMessage = "Unknown intent action";
            switch (intentAction) {
                case MainActivity.INTENT_ACTION:
                    // Extract index
                    int index = intent.getIntExtra("INDEX", -1);

                    // Check if it's a valid index
                    if (index != -1) {
                        // Get url
                        String url = vacation_urls.get(index);

                        // Set the data for the intent as the site url
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));

                        MainActivity.webIntentStarted = true; // Browser has been opened

                        // Start the activity with the intent
                        context.startActivity(i);
                    }

                    break;
                default:
                    //Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
