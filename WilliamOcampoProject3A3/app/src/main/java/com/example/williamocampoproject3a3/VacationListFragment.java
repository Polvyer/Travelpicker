package com.example.williamocampoproject3a3;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class VacationListFragment extends ListFragment {
    private static final String TAG = "VacationListFragment";
    private ListSelectionListener mListener = null;

    // Callback interface that allows this Fragment to notify the MainActivity when
    // user clicks on a List Item
    public interface ListSelectionListener {
        public void onListSelection(int index);
    }

    // Called when the user selects an item from the list
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {

        // Indicates the selected item has been checked
        getListView().setItemChecked(position, true);

        // Inform the MainActivity that the item in position position has been selected
        mListener.onListSelection(position);
    }

    // Save the containing activity in a fragment field
    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        try {

            // Set the ListSelectionListener for communicating with the MainActivity
            // Try casting the containing activity to a ListSelectionListener
            mListener = (ListSelectionListener) activity;

        } catch (ClassCastException e) {
            // Cast failed: This is not going to work because containing activity may not
            // have implemented onListSelection() method
            throw new ClassCastException(activity.toString()
                    + " must implement onArticleSelectedListener");
        }
    }

    // Android calls when fragment created
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Prevent fragments from getting deleted when a configuration change occurs
        setRetainInstance(true);
    }

    // Called when fragment must generate its visual display for the first time (return a View)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set the list adapter for the ListView
        // Discussed in more detail in the user interface classes lesson
        setListAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.vacation_list_item, MainActivity.mListArray));

        // Set list choice mode to allow only one selection at a time
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
    }

    @Override
    public void onStart() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onStop()");
        super.onStop();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDetach()");
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, getClass().getSimpleName() + ":entered onDestroyView()");
        super.onDestroyView();
    }
}
