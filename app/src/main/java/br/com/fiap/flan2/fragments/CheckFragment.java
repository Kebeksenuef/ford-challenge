package br.com.fiap.flan2.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Set;

import br.com.fiap.flan2.AppSession;
import br.com.fiap.flan2.R;
import br.com.fiap.flan2.adapter.ItensManutencaoAdapter;
import br.com.fiap.flan2.dao.TarefaFragmentItens;
import br.com.fiap.flan2.dao.TarefaRealizarManutencao;
import br.com.fiap.flan2.dto.ItemRevisao;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends Fragment {

    private static final String TAG = "CheckFragment";
    private static final int READ_REQUEST_CODE = 42;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_check, container, false);

        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.recycle_view_itens_frag);

        /*AppCompatButton btnSalvar = view.findViewById(R.id.btnSalvar);
        btnSalvar.setClickable(true);
        btnSalvar.setOnClickListener(b ->{
            //escolhendo arquivo (imagem):
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, READ_REQUEST_CODE);
        });*/

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

        AppCompatButton botaoConfirmar = view.findViewById(R.id.btnConfirmarMenutencao);
        botaoConfirmar.setOnClickListener(x -> {
            ItensManutencaoAdapter adapter = (ItensManutencaoAdapter)recyclerView.getAdapter();
            Set<ItemRevisao> itensSelecionados = adapter.getItensSelecionados();

            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, READ_REQUEST_CODE);

            TarefaRealizarManutencao tarefaRealizarManutencao = new TarefaRealizarManutencao(context, botaoConfirmar, itensSelecionados);
            tarefaRealizarManutencao.execute("Teste" /* TODO: COLOCAR CAMPO NO FRAGMENTO PARA DESCRICAO */);
        });

        TarefaFragmentItens tarefaFragmentItens = new TarefaFragmentItens(context, recyclerView);
        tarefaFragmentItens.execute();

        // Inflate the layout for this fragment
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData){
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri uri = null;
            if (resultData != null){
                uri = resultData.getData();
                Log.i(TAG,"Uri da imagem: "+uri.getPath());
            }
        }
    }

}
