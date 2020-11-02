package com.example.williamocampoproject3a3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {

    private static final String TAG = "ImageFragment";

    private ImageView mQuoteView = null;
    private int mCurrIdx = -1;
    private int mQuoteArrLen;

    int getShownIndex() {
        return mCurrIdx;
    }

    // Show the Image at position newIndex
    void showQuoteAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mQuoteArrLen)
            return;
        mCurrIdx = newIndex;
        mQuoteView.setImageResource(MainActivity.mImageArray.get(mCurrIdx));
    }

    @Override
    public void onAttach(Context activity) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onAttach()");
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreate()");
        super.onCreate(savedInstanceState);

        // Prevent fragments from getting deleted when a configuration change occurs
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onCreateView()");

        // Inflate the layout defined in vacation_image_item.xml
        // The last parameter is false because the returned view does not need to be
        // attached to the container ViewGroup
        return inflater.inflate(R.layout.vacation_image_item, container, false);
    }

    // Set up some information about the mQuoteView ImageView
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, getClass().getSimpleName() + ":entered onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        mQuoteView = (ImageView) getActivity().findViewById(R.id.quoteView);
        mQuoteArrLen = MainActivity.mImageArray.size();
    }
}
