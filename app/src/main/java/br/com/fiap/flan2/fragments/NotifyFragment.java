package br.com.fiap.flan2.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.fiap.flan2.AppSession;
import br.com.fiap.flan2.R;
import br.com.fiap.flan2.adapter.RecycleAdapter;
import br.com.fiap.flan2.dao.TarefaRealizarRevisao;
import br.com.fiap.flan2.dto.Revisao;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifyFragment extends Fragment {

    private static final String TAG = "NotifyFragment";

    private Revisao proximaRevisao;

    public NotifyFragment(Revisao proximaRevisao) {
        this.proximaRevisao = proximaRevisao;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_notify, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_itens);
        TextView textViewValor = view.findViewById(R.id.txtValorRevisao);

        AppCompatButton botaoRealizarRevisao = view.findViewById(R.id.btnRealizarRevisao);
        botaoRealizarRevisao.setClickable(true);
        botaoRealizarRevisao.setText("Já fiz a revisão!");
        botaoRealizarRevisao.setOnClickListener(b -> {
            int codigoRevisao = (int)textViewValor.getTag();

            TarefaRealizarRevisao tarefaRealizarRevisao = new TarefaRealizarRevisao(context, botaoRealizarRevisao, codigoRevisao);
            tarefaRealizarRevisao.execute();
        });

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

        if (proximaRevisao != null) {
            LinearLayoutManager llm =  new LinearLayoutManager(context);
            RecycleAdapter adapter = new RecycleAdapter(proximaRevisao.getItens());

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(llm);
            recyclerView.setAdapter(adapter);

            String valorVista = formatarValor(proximaRevisao.getValorVista());
            String valorParcela = formatarValor(proximaRevisao.getValorParcela());

            textViewValor.setTag(proximaRevisao.getCodigo());
            textViewValor.setText(String.format("À Vista R$ %s ou %dX de R$ %s", valorVista, proximaRevisao.getQuantidadeParcelas(), valorParcela));
        } else {
            textViewValor.setText("Não há revisão");
        }

        // Inflate the layout for this fragment
        return view;
    }

    private String formatarValor(float valor) {
        NumberFormat formatador = NumberFormat.getNumberInstance(new Locale( "pt", "BR" ));
        formatador.setMinimumFractionDigits(2);
        formatador.setMaximumFractionDigits(2);

        return formatador.format(valor);
    }
}
