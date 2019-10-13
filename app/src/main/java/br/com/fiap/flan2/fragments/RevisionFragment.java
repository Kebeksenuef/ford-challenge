package br.com.fiap.flan2.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import br.com.fiap.flan2.R;
import br.com.fiap.flan2.dao.TarefaManutencoesFeitas;
import br.com.fiap.flan2.dao.TarefaRevisoesFeitas;

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
        RecyclerView recyclerViewRevisoes = view.findViewById(R.id.recycle_view_revisao);
        RecyclerView recyclerViewManutencoes = view.findViewById(R.id.recycle_view_manutencao);

        TarefaRevisoesFeitas tarefaRevisoesFeitas = new TarefaRevisoesFeitas(context, recyclerViewRevisoes);
        tarefaRevisoesFeitas.execute();

        TarefaManutencoesFeitas tarefaManutencoesFeitas = new TarefaManutencoesFeitas(context, recyclerViewManutencoes);
        tarefaManutencoesFeitas.execute();

        return view;
    }

}
