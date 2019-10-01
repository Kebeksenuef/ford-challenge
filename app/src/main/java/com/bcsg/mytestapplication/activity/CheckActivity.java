package com.bcsg.mytestapplication.activity;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.dao.TarefaConsultarItens;

public class CheckActivity extends AppCompatActivity {

    private static final String TAG = "CheckFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

        Context context = getApplicationContext();
        RecyclerView recyclerView = findViewById(R.id.recycle_view_itens);

        TarefaConsultarItens tarefaConsultarItens = new TarefaConsultarItens(context, recyclerView);
        tarefaConsultarItens.execute();
    }
}
