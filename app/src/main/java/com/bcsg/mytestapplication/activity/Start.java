package com.bcsg.mytestapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.bcsg.mytestapplication.AppSession;
import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.dao.TarefaConsultarModelos;
import com.bcsg.mytestapplication.dto.EnumMockInfoVeiculo;
import com.bcsg.mytestapplication.dto.Modelo;

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
                Modelo modeloSelecionado = (Modelo)parent.getItemAtPosition(position);
                EnumMockInfoVeiculo mockInfo = EnumMockInfoVeiculo.get(modeloSelecionado);

                AppSession.setModelo(modeloSelecionado, mockInfo);

                Intent intent;

                switch (AppSession.getModelo().getMockInfo()) {
                    case FOCUS:
                    case ECOSPORT:
                        intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);

                        break;
                    case FUSION:
                        break;
                    case KA:
                        break;
                    case FIESTA:
                        break;
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

