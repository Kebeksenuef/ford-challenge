package com.bcsg.mytestapplication.sdl.fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.adapter.RecycleAdapter;
import com.bcsg.mytestapplication.dao.AzureConnection;
import com.bcsg.mytestapplication.dao.TarefaConsultarItens;
import com.bcsg.mytestapplication.dao.TarefaFragmentItens;
import com.bcsg.mytestapplication.dto.ItemRevisao;
import com.bcsg.mytestapplication.dto.Modelo;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends Fragment {

    private static final String TAG = "CheckFragment";

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_check, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_itens_frag);

        TarefaFragmentItens tarefaFragmentItens = new TarefaFragmentItens(context, recyclerView);
        tarefaFragmentItens.execute();

        // Inflate the layout for this fragment
        return view;
    }
}
