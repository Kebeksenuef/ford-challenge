package br.com.fiap.flan2.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import br.com.fiap.flan2.AppSession;

public class TarefaRealizarRevisao extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private AppCompatButton button;
    private int codigoRevisao;

    public TarefaRealizarRevisao(Context context, AppCompatButton button, int codigoRevisao) {
        super();
        this.context = context;
        this.button = button;
        this.codigoRevisao = codigoRevisao;
    }

    @Override
    protected void onPreExecute(){
        Log.i("TarefaRealizarRevisao", "Pre Execute - Thread: " + Thread.currentThread().getName());
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Log.i("TarefaRealizarRevisao", "Consultando modelos... Thread: " + Thread.currentThread().getName());

        boolean sucesso = AzureConnection.realizarRevisao(AppSession.getModelo().getMockInfo().getChassi(), codigoRevisao);

        return sucesso;
    }

    @Override
    protected void onPostExecute(Boolean retorno) {
        Log.i("TarefaRealizarRevisao", "Post Execute - Thread: " + Thread.currentThread().getName());

        if (retorno) {
            button.setClickable(false);
            button.setText("Revisão confirmada!");
        } else {
            Toast.makeText(context, "Não foi possível realizar a revisão", Toast.LENGTH_LONG);
        }
    }

}
