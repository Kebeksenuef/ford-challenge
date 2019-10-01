package com.bcsg.mytestapplication.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bcsg.mytestapplication.dto.Modelo;

import java.util.List;

public class TarefaConsultarModelos extends AsyncTask<Void, Void, String[]> {

    private Context context;
    private Spinner spinner;

    public TarefaConsultarModelos(Context context, Spinner spinner) {
        super();

        this.context = context;
        this.spinner = spinner;
    }

    @Override
    protected void onPreExecute(){
        Log.i("TarefaConsultarModelo", "Pre Execute - Thread: " + Thread.currentThread().getName());
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        Log.i("TarefaConsultarModelo", "Consultando modelos... Thread: " + Thread.currentThread().getName());

        List<Modelo> entidades = AzureConnection.consultarModelos();
        String[] modelos = entidades.stream().map(x -> x.getNome()).toArray(String[]::new);

        return modelos;
    }

    @Override
    protected void onPostExecute(String[] modelos) {
        Log.i("TarefaConsultarModelo", "Post Execute - Thread: " + Thread.currentThread().getName());

        if (this.spinner != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context, android.R.layout.simple_spinner_dropdown_item, modelos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            this.spinner.setAdapter(adapter);
        }
    }
}