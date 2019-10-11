package com.bcsg.mytestapplication.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bcsg.mytestapplication.AppSession;
import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.dao.TarefaConsultarItens;
import com.bcsg.mytestapplication.dao.TarefaFragmentItens;
import com.bcsg.mytestapplication.dao.TarefaProximaRevisao;
import com.bcsg.mytestapplication.dao.TarefaRevisoesFeitas;

/**
 * A simple {@link Fragment} subclass.
 */
public class RevisionFragment extends Fragment {

    private static final String TAG = "RevisionFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_revision, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_revisao);

        TarefaRevisoesFeitas tarefaRevisoesFeitas = new TarefaRevisoesFeitas(context, recyclerView);
        tarefaRevisoesFeitas.execute();

        return view;
    }

}
