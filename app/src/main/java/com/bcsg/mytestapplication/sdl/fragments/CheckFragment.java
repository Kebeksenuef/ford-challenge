package com.bcsg.mytestapplication.sdl.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.adapter.RecycleAdapter;
import com.bcsg.mytestapplication.dao.AzureConnection;
import com.bcsg.mytestapplication.dto.ItemRevisao;
import com.bcsg.mytestapplication.dto.Modelo;

import java.util.List;

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

        List<ItemRevisao> itemRevisaos = AzureConnection.consutlarItens();

        if (view instanceof RecyclerView){
            Context context = view.getContext();
            RecyclerView rv = view.findViewById(R.id.recycle_view);
            rv.setHasFixedSize(true);
            LinearLayoutManager llm =  new LinearLayoutManager(context);
            rv.setLayoutManager(llm);
            RecycleAdapter ra = new RecycleAdapter(itemRevisaos);
            rv.setAdapter(ra);
        }

        // Inflate the layout for this fragment
        return view;
    }

}
