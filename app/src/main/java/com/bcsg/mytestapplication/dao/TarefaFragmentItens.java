package com.bcsg.mytestapplication.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcsg.mytestapplication.adapter.ItensManutencaoAdapter;
import com.bcsg.mytestapplication.dto.ItemRevisao;
import com.bcsg.mytestapplication.dto.Modelo;

import java.util.List;

public class TarefaFragmentItens extends AsyncTask<Void, Void, List<ItemRevisao>> {

    private Context context;
    private RecyclerView recyclerView;

    public TarefaFragmentItens(Context context, RecyclerView recyclerView) {
        super();
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute(){
        Log.i("TarefaConsultarItens", "Pre Execute - Thread: " + Thread.currentThread().getName());
    }

    @Override
    protected List<ItemRevisao> doInBackground(Void... voids) {
        Log.i("TarefaConsultarItens", "Consultando modelos... Thread: " + Thread.currentThread().getName());
        List<ItemRevisao> entidades = AzureConnection.consultarItens();
        return entidades;
    }

    @Override
    protected void onPostExecute(List<ItemRevisao> itens) {
        Log.i("TarefaConsultarItens", "Post Execute - Thread: " + Thread.currentThread().getName());

        if (this.recyclerView != null) {
            LinearLayoutManager llm =  new LinearLayoutManager(this.context);
            ItensManutencaoAdapter adapter = new ItensManutencaoAdapter(itens);
            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setLayoutManager(llm);
            this.recyclerView.setAdapter(adapter);
        }
    }
}
