package com.bcsg.mytestapplication.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.bcsg.mytestapplication.R;
import com.bcsg.mytestapplication.activity.MainActivity;
import com.bcsg.mytestapplication.dao.AzureConnection;
import com.bcsg.mytestapplication.dto.Modelo;
import java.util.ArrayList;
import java.util.List;

public class Start extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Thread(new Runnable(){
            @Override
            public void run() {
                AzureConnection.getConnection();
            }
        }).start();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        //List<Modelo> modelos = azc.consultarModelos();

        String[] cars = new String[]{"Fusion", "Ford Ka", "Ecosport"};
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < cars.length; i++) {
            list.add(cars[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,cars);
        //adapter.addAll(String.valueOf(modelos));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString() == "Ecosport"){
                    Intent intenrt = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intenrt);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}

