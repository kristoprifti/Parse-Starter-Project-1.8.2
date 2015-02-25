package com.parse.starter;

/**
 * Created by Kristi-PC on 2/20/2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SalonsFragment extends Fragment {

    public SalonsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_salons, container, false);

        return rootView;
    }
}
