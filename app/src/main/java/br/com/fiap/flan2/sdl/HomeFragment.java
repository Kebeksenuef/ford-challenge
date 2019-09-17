package com.bcsg.mytestapplication;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bcsg.mytestapplication.globalvariables.Config;
import com.bcsg.mytestapplication.sdl.SdlReceiver;
import com.bcsg.mytestapplication.sdl.SdlService;
import com.google.android.gms.plus.PlusOneButton;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

/**
 * A fragment with a Google +1 button.
 */
public class HomeFragment extends Fragment {

    // The request code must be 0 or greater.
    //private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    //private final String PLUS_ONE_URL = "http://developer.android.com";
    //private PlusOneButton mPlusOneButton;

    private Button btnMostrar;
    private TextView txtStatus, txtKm,txtAviso;
    private static final String TAG = "MainActivity";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Find the +1 button
        //mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);
        //button?

        btnMostrar = (Button) view.findViewById(R.id.btnMostrar);
        txtStatus = (TextView) view.findViewById(R.id.txtStatus);
        txtKm = (TextView) view.findViewById(R.id.txtKm);
        txtAviso = (TextView) view.findViewById(R.id.txtAviso);

        btnMostrar.setOnClickListener(new View.OnClickListener() {
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
                                PRNDL prndl = ((GetVehicleDataResponse) response).getPrndl();

                                //TelematicsCollector tc = new TelematicsCollector();
                                //txtKm.setText("Modelo CARRO: "+tc.getVehicleType().getModel());
                                //VehicleType dado =
                                //        ((RegisterAppInterfaceResponse) response).getVehicleType();
                                //txtKm.setText("MODELO: CARRO: "+dado);
                                //System.out.println("MODELO CARRO: "+dado);
                                //HMIScreenManager.getInstance().showAlert("PRNDL status: " + prndl.toString());
                                //Integer km = ((GetVehicleDataResponse) response).getOdometer();
                                //txtKm.setText("KM atual: " + km.toString());
                                txtStatus.setText("PRNDL Status: "+ prndl.toString());
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
                    txtAviso.setText("A conexão com o SYNC não foi estabelecida");
                    //Toast.makeText(getApplicationContext(), "Conexão com o SYNC não foi estabelecida", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Refresh the state of the +1 button each time the activity receives focus.
        //mPlusOneButton.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE);
    }


}
