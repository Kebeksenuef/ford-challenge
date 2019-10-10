package com.bcsg.mytestapplication.dao;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bcsg.mytestapplication.dto.Modelo;

import java.util.List;

public class TarefaConsultarModelos extends AsyncTask<Void, Void, Modelo[]> {

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
    protected Modelo[] doInBackground(Void... voids) {
        Log.i("TarefaConsultarModelo", "Consultando modelos... Thread: " + Thread.currentThread().getName());

        List<Modelo> entidades = AzureConnection.consultarModelos();
        Modelo[] modelos = entidades.stream().toArray(Modelo[]::new);

        return modelos;
    }

    @Override
    protected void onPostExecute(Modelo[] modelos) {
        Log.i("TarefaConsultarModelo", "Post Execute - Thread: " + Thread.currentThread().getName());

        if (this.spinner != null) {
            ArrayAdapter<Modelo> adapter = new ArrayAdapter<>(this.context, android.R.layout.simple_spinner_dropdown_item, modelos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            this.spinner.setAdapter(adapter);
        }
    }
}