package com.bcsg.mytestapplication.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bcsg.mytestapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RevisionFragment extends Fragment {

    private static final String TAG = "RevisionFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revision, container, false);

        Context context = getContext();

        // Inflate the layout for this fragment
        return view;
    }

}
