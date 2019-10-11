package br.com.fiap.flan2.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import br.com.fiap.flan2.AppSession;
import br.com.fiap.flan2.R;
import br.com.fiap.flan2.dao.TarefaConsultarModelos;
import br.com.fiap.flan2.dto.EnumMockInfoVeiculo;
import br.com.fiap.flan2.dto.Modelo;

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
                    case FUSION:
                    case KA:
                    case FIESTA:
                        intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);

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

