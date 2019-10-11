package br.com.fiap.flan2.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import br.com.fiap.flan2.AppSession;
import br.com.fiap.flan2.R;
import br.com.fiap.flan2.dao.TarefaProximaRevisao;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifyFragment extends Fragment {

    private static final String TAG = "NotifyFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notify, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_itens);
        TextView textView = view.findViewById(R.id.txtValorRevisao);

        AppCompatImageView imagemModelo = view.findViewById(R.id.imgImagemVeiculo);

        switch (AppSession.getModelo().getMockInfo()){
            case FOCUS:
                imagemModelo.setImageResource(R.drawable.focus);
                break;
            case ECOSPORT:
                imagemModelo.setImageResource(R.drawable.ecosport);
                break;
            case FUSION:
                imagemModelo.setImageResource(R.drawable.fusion);
                break;
            case KA:
                imagemModelo.setImageResource(R.drawable.ka);
                break;
            case FIESTA:
                imagemModelo.setImageResource(R.drawable.fiesta);
                break;
        }

        TarefaProximaRevisao tarefaProximaRevisao = new TarefaProximaRevisao(context,recyclerView, textView);
        tarefaProximaRevisao.execute();

        // Inflate the layout for this fragment
        return view;
    }

}
