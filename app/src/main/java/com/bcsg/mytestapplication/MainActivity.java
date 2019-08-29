package com.bcsg.mytestapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

public class MainActivity extends AppCompatActivity {

    private Button btnShow;
    private TextView txtDado;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //If we are connected to a module we want to start our SdlService
        if(BuildConfig.TRANSPORT.equals("MULTI") || BuildConfig.TRANSPORT.equals("MULTI_HB")) {
            SdlReceiver.queryForConnectedService(this);
            Log.i(TAG,"Conectando por MULTI_HB");
        }else if(BuildConfig.TRANSPORT.equals("TCP")) {
            Intent proxyIntent = new Intent(this, SdlService.class);
            startService(proxyIntent);
        }

        txtDado = findViewById(R.id.txtDado);
        btnShow = findViewById(R.id.btnShow);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //você só consegue pegar os dados se a conexão com o SYNC for estabelecida pelo SdlService
                if (Config.sdlServiceIsActive) {
                    //exemplo de chamada normal
                    //TelematicsCollector.getInstance().getVehicleData();

                    //exemplo de chamada passando o rpcListener como parametro
                    TelematicsCollector.getInstance().getVehicleData(new OnRPCResponseListener() {
                        @Override
                        public void onResponse(int correlationId, RPCResponse response) {
                            Log.i(TAG,"Houve uma resposta RPC!");

                            if(response.getSuccess()){
                                //PRNDL status: parado, dirigindo...
                                PRNDL prndl = ((GetVehicleDataResponse) response).getPrndl();
                                //Log.i("SdlService", "PRNDL STATUS: " + prndl.toString());
                                HMIScreenManager.getInstance().showAlert("PRNDL status: " + prndl.toString());
                                //showTest("PRNDL status: " + prndl.toString());
                                //Double fuel = ((GetVehicleDataResponse) response).getFuelLevel();
                                //txtDado.setText("Fuel level: " + fuel.toString());
                                txtDado.setText("PRNDL Status: "+ prndl.toString());
                            }else{
                                Log.i("SdlService", "GetVehicleData was rejected.");
                            }
                        }
                        @Override
                        public void onError(int correlationId, Result resultCode, String info){
                            Log.e(TAG, "onError: "+ resultCode+ " | Info: "+ info );
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Conexão com o SYNC não foi estabelecida", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
