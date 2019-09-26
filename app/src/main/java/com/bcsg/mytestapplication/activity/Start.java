package com.bcsg.mytestapplication.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.dao.AzureConnection;
import com.bcsg.mytestapplication.dto.Modelo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Start extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        /*new Thread(new Runnable(){
            @Override
            public void run() {
                List<Modelo> modelos = AzureConnection.consultarModelos();
                for (int i = 0; i< modelos.size(); i++){
                    Log.i(TAG, modelos.get(i).getNome());
                }
            }
        }).start();*/
        /*List<Modelo> modelos = AzureConnection.consultarModelos();
        for (int j = 0; j< modelos.size(); j++) {
            Log.i(TAG, modelos.get(j).getNome());
        }*/

        //LongTask log = new LongTask();
        //log.execute();

        //try {
            /*List<Modelo> modelos = new LongTask().execute().get();

            String [] vetorModelo = new String[3];
            System.out.println(modelos);
            for (int i = 0; i<modelos.size(); i++) {
                vetorModelo[i] = modelos.get(i).getNome();
            }*/

        Spinner spinner = findViewById(R.id.spinner);
        String[] cars = new String[]{"FUSION", "FORDKA", "ECOSPORT"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,cars);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString() == "ECOSPORT"){
                    Intent intenrt = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intenrt);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        /*} catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }

    public class LongTask extends AsyncTask<Void, Void, List<Modelo> >{

        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected List<Modelo> doInBackground(Void... voids) {
            List<Modelo> modelos = AzureConnection.consultarModelos();
            /*for (int j = 0; j< modelos.size(); j++) {
                //Log.i(TAG, "Modelos"+modelos.get(j).getNome());
                modelos.get(j).getNome();
            }*/
            return modelos;
        }
        protected void onPostExecute(List<Modelo> modelos) {
            super.onPreExecute();
            //System.out.println(modelos);
            //delegate.processFinish(modelos);
        }
    }

}

