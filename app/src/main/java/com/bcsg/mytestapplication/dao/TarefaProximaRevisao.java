package com.bcsg.mytestapplication.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bcsg.mytestapplication.AppSession;
import com.bcsg.mytestapplication.adapter.RecycleAdapter;
import com.bcsg.mytestapplication.dto.Revisao;

import java.text.NumberFormat;
import java.util.Locale;

public class TarefaProximaRevisao extends AsyncTask<Void, Void, Revisao> {

    private Context context;
    private RecyclerView recyclerView;
    private TextView textView;

    public TarefaProximaRevisao(Context context, RecyclerView recyclerView, TextView textView) {
        super();
        this.context = context;
        this.recyclerView = recyclerView;
        this.textView = textView;
    }

    @Override
    protected void onPreExecute(){
        Log.i("TarefaConsultarItens", "Pre Execute - Thread: " + Thread.currentThread().getName());
    }

    @Override
    protected Revisao doInBackground(Void... voids) {
        Log.i("TarefaConsultarItens", "Consultando modelos... Thread: " + Thread.currentThread().getName());

        Revisao proximaRevisao = AzureConnection.consultarProximaRevisao(AppSession.getModelo().getMockInfo().getChassi(),
                AppSession.getModelo().getCodigo());

        return proximaRevisao;
    }

    @Override
    protected void onPostExecute(Revisao revisao) {
        Log.i("TarefaConsultarItens", "Post Execute - Thread: " + Thread.currentThread().getName());

        if (this.recyclerView != null) {
            LinearLayoutManager llm =  new LinearLayoutManager(this.context);
            RecycleAdapter adapter = new RecycleAdapter(revisao.getItens());

            this.recyclerView.setHasFixedSize(true);
            this.recyclerView.setLayoutManager(llm);
            this.recyclerView.setAdapter(adapter);
        }

        String valorVista = formatarValor(revisao.getValorVista());
        String valorParcela = formatarValor(revisao.getValorParcela());

        this.textView.setText(String.format("À Vista R$ %s ou %dX de R$ %s", valorVista, revisao.getQuantidadeParcelas(), valorParcela));
    }

    private String formatarValor(float valor) {
        NumberFormat formatador = NumberFormat.getNumberInstance(new Locale( "pt", "BR" ));
        formatador.setMinimumFractionDigits(2);
        formatador.setMaximumFractionDigits(2);

        return formatador.format(valor);
    }
}