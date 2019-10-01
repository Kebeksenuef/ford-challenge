package com.bcsg.mytestapplication.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.dao.AzureConnection;
import com.bcsg.mytestapplication.dao.TarefaConsultarModelos;
import com.bcsg.mytestapplication.dto.Modelo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class Start extends AppCompatActivity {

    private static final String TAG = "Start";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Context context = getApplicationContext();
        Spinner spinner = findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("EcoSport")) {
                    Intent intent = new Intent(view.getContext(), MainActivity.class);

                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        TarefaConsultarModelos tarefaConsultarModelos = new TarefaConsultarModelos(context, spinner);
        tarefaConsultarModelos.execute();
    }
}

