package br.com.fiap.flan2.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.fiap.flan2.AppSession;
import br.com.fiap.flan2.adapter.ManutencoesFeitasAdapter;
import br.com.fiap.flan2.dto.Manutencao;

public class TarefaManutencoesFeitas extends AsyncTask<Void, Void, List<Manutencao>> {

    private Context context;
    private RecyclerView recyclerView;

    public TarefaManutencoesFeitas(Context context, RecyclerView recyclerView) {
        super();
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute(){
        Log.i("TarefaManutencoesFeitas", "Pre Execute - Thread: " + Thread.currentThread().getName());
    }

    @Override
    protected List<Manutencao> doInBackground(Void... voids) {
        Log.i("TarefaManutencoesFeitas", "Consultando modelos... Thread: " + Thread.currentThread().getName());

        List<Manutencao> entidades = AzureConnection.consultarManutencoes(AppSession.getModelo().getMockInfo().getChassi());

        return entidades;
    }

    @Override
    protected void onPostExecute(List<Manutencao> manutencoes) {
        Log.i("TarefaManutencoesFeitas", "Post Execute - Thread: " + Thread.currentThread().getName());

        if (this.recyclerView != null) {
            LinearLayoutManager llm =  new LinearLayoutManager(this.context);
            ManutencoesFeitasAdapter adapter = new ManutencoesFeitasAdapter(manutencoes);
            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setLayoutManager(llm);
            this.recyclerView.setAdapter(adapter);
        }
    }
}
