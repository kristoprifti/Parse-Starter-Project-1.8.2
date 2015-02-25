package com.parse.starter;

/**
 * Created by Kristi-PC on 2/20/2015.
 */
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseUser;

public class LogoutFragment extends Fragment {

    public LogoutFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ParseUser.logOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
        return null;
    }
}

