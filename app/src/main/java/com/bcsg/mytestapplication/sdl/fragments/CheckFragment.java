package com.bcsg.mytestapplication.sdl.fragments;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bcsg.mytestapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends Fragment {


    public CheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_check, container, false);

        final Activity activity = getActivity();


        // Inflate the layout for this fragment
        return view;
    }

}
