package com.parse.starter;

/**
 * Created by Kristi-PC on 2/20/2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoritesFragment extends Fragment {

    public FavoritesFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);

        return rootView;
    }
}
