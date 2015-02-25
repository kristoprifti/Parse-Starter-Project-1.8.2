package com.parse.starter;

/**
 * Created by Kristi-PC on 2/20/2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BookingsFragment extends Fragment {

    public BookingsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bookings, container, false);

        return rootView;
    }
}

