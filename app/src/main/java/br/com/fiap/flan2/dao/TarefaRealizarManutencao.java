package br.com.fiap.flan2.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import java.util.Collection;
import java.util.UUID;

import br.com.fiap.flan2.AppSession;
import br.com.fiap.flan2.dto.ItemRevisao;

public class TarefaRealizarManutencao extends AsyncTask<String, Void, Long> {

    private Context context;
    private AppCompatButton button;
    private Collection<ItemRevisao> itens;

    public TarefaRealizarManutencao(Context context, AppCompatButton button, Collection<ItemRevisao> itens) {
        super();
        this.context = context;
        this.button = button;
        this.itens = itens;
    }

    @Override
    protected void onPreExecute(){
        Log.i("TarefaRealizarManutencao", "Pre Execute - Thread: " + Thread.currentThread().getName());
    }

    @Override
    protected Long doInBackground(String... descricao) {
        Log.i("TarefaRealizarManutencao", "Consultando modelos... Thread: " + Thread.currentThread().getName());

        String descricaoManutencao = String.format("Manutenção [%s]", UUID.randomUUID().toString());

        if (descricao.length > 0) {
            descricaoManutencao = descricao[0];
        }

        Long id = AzureConnection.realizarManutencao(AppSession.getModelo().getMockInfo().getChassi(), descricaoManutencao, itens);

        return id;
    }

    @Override
    protected void onPostExecute(Long retorno) {
        Log.i("TarefaRealizarManutencao", "Post Execute - Thread: " + Thread.currentThread().getName());

        if (retorno > 0L) {
            button.setClickable(false);
            button.setText("Manutenção realizada!");
        } else {
            Toast.makeText(context, "Não foi possível realizar a manutenção", Toast.LENGTH_LONG);
        }
    }
}
