package com.bcsg.mytestapplication.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcsg.mytestapplication.AppSession;
import com.bcsg.mytestapplication.adapter.ItensManutencaoAdapter;
import com.bcsg.mytestapplication.adapter.RevisoesFeitasAdapter;
import com.bcsg.mytestapplication.dto.ItemRevisao;
import com.bcsg.mytestapplication.dto.Revisao;

import java.util.List;

public class TarefaRevisoesFeitas extends AsyncTask<Void, Void, List<Revisao>> {

    private Context context;
    private RecyclerView recyclerView;

    public TarefaRevisoesFeitas(Context context, RecyclerView recyclerView) {
        super();
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute(){
        Log.i("TarefaConsultarItens", "Pre Execute - Thread: " + Thread.currentThread().getName());
    }

    @Override
    protected List<Revisao> doInBackground(Void... voids) {
        Log.i("TarefaConsultarItens", "Consultando modelos... Thread: " + Thread.currentThread().getName());
        List<Revisao> entidades = AzureConnection.consultarRevisoes(AppSession.getModelo().getMockInfo().getChassi(),
                AppSession.getModelo().getCodigo());
        return entidades;
    }

    @Override
    protected void onPostExecute(List<Revisao> revisoes) {
        Log.i("TarefaConsultarItens", "Post Execute - Thread: " + Thread.currentThread().getName());

        if (this.recyclerView != null) {
            LinearLayoutManager llm =  new LinearLayoutManager(this.context);
            RevisoesFeitasAdapter adapter = new RevisoesFeitasAdapter(revisoes);
            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setLayoutManager(llm);
            this.recyclerView.setAdapter(adapter);
        }
    }

}
