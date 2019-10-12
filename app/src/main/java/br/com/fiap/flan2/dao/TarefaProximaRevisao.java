package br.com.fiap.flan2.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.fiap.flan2.AppSession;
import br.com.fiap.flan2.adapter.RecycleAdapter;
import br.com.fiap.flan2.dto.Revisao;

public class TarefaProximaRevisao extends AsyncTask<Void, Void, Revisao> {

    private Context context;
    private RecyclerView recyclerView;
    private TextView textViewValor;

    public TarefaProximaRevisao(Context context, RecyclerView recyclerView, TextView textViewValor) {
        super();
        this.context = context;
        this.recyclerView = recyclerView;
        this.textViewValor = textViewValor;
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

        this.textViewValor.setTag(revisao.getCodigo());
        this.textViewValor.setText(String.format("À Vista R$ %s ou %dX de R$ %s", valorVista, revisao.getQuantidadeParcelas(), valorParcela));
    }

    private String formatarValor(float valor) {
        NumberFormat formatador = NumberFormat.getNumberInstance(new Locale( "pt", "BR" ));
        formatador.setMinimumFractionDigits(2);
        formatador.setMaximumFractionDigits(2);

        return formatador.format(valor);
    }
}