package br.com.fiap.flan2.dao;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import br.com.fiap.flan2.AppSession;
import br.com.fiap.flan2.dto.Revisao;
import br.com.fiap.flan2.fragments.HomeFragment;

public class TarefaProximaRevisao extends AsyncTask<Void, Void, Revisao> {

    private HomeFragment fragment;
    private TextView textViewKm;

    public TarefaProximaRevisao(HomeFragment fragment, TextView textViewKm) {
        super();
        this.fragment = fragment;
        this.textViewKm = textViewKm;
    }

    @Override
    protected void onPreExecute(){
        Log.i("TarefaProximaRevisao", "Pre Execute - Thread: " + Thread.currentThread().getName());
    }

    @Override
    protected Revisao doInBackground(Void... voids) {
        Log.i("TarefaProximaRevisao", "Consultando modelos... Thread: " + Thread.currentThread().getName());

        Revisao proximaRevisao = AzureConnection.consultarProximaRevisao(AppSession.getModelo().getMockInfo().getChassi(),
                AppSession.getModelo().getCodigo(), AppSession.getModelo().getMockInfo().getQuilometragem());

        return proximaRevisao;
    }

    @Override
    protected void onPostExecute(Revisao revisao) {
        Log.i("TarefaProximaRevisao", "Post Execute - Thread: " + Thread.currentThread().getName());

        this.fragment.setProximaRevisao(revisao);

        if (revisao != null) {
            int quilometragemProximaRevisao = revisao.getLimiteQuilometragem();
            int quilometragemAtual = AppSession.getModelo().getMockInfo().getQuilometragem();

            int quilometragemRestante = quilometragemProximaRevisao - quilometragemAtual;

            if (quilometragemRestante > 0) {
                textViewKm.setText(String.format("%s Km", quilometragemRestante));
            } else {
                textViewKm.setText("Não há revisão prevista");
            }
        }
    }

    private String formatarQuilometragem(int quilometragem) {
        NumberFormat formatador = NumberFormat.getNumberInstance(new Locale( "pt", "BR" ));
        formatador.setMinimumFractionDigits(0);
        formatador.setMaximumFractionDigits(0);

        return formatador.format(quilometragem);
    }
}