package com.bcsg.mytestapplication;

import android.util.Log;

import com.bcsg.mytestapplication.sdl.globalvariables.Config;
import com.bcsg.mytestapplication.sdl.multimidiacenter.HMIScreenManager;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

public class TelematicsCollector {
    private static final String TAG 					= "TelematicsCollector";
    //Singleton
    private static TelematicsCollector INSTANCE;

    public static TelematicsCollector getInstance() {
        if (INSTANCE == null)
            INSTANCE = new TelematicsCollector();
        return INSTANCE;
    }

    public void getVehicleData(){
        //só para garantir que o getVehicleData será executado apenas se a conexão com o SYNC for estabelecida

        if (!Config.sdlServiceIsActive) {
            return;
        }
        GetVehicleData vdRequest = new GetVehicleData();
        vdRequest.setPrndl(true);
        //Aqui vc seta todos os dados que vc quer que a chamada retorne
        /*
        vdRequest.setVin(true);
        vdRequest.setFuelLevel_State(true);
        vdRequest.setOdometer(true);
        vdRequest.setSpeed(true);
        vdRequest.setRpm(true);
        vdRequest.setPrndl(true);
        vdRequest.setGps(true);
        vdRequest.setTirePressure(true);
        vdRequest.setFuelLevel(true);
        vdRequest.setInstantFuelConsumption(true);
        vdRequest.setExternalTemperature(true);
         */
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Log.i(TAG,"Houve uma resposta RPC!");
                if(response.getSuccess()){
                    //PRNDL status: parado, dirigindo...
                    PRNDL prndl = ((GetVehicleDataResponse) response).getPrndl();
                    Log.i("SdlService", "PRNDL STATUS: " + prndl.toString());
                    HMIScreenManager.getInstance().showAlert("PRNDL status: " + prndl.toString());
                    //showTest("PRNDL status: " + prndl.toString());
                    System.out.println("PRNDL status: "+prndl.toString());
                }else{
                    Log.i("SdlService", "GetVehicleData was rejected.");
                }
            }
            @Override
            public void onError(int correlationId, Result resultCode, String info){
                Log.e(TAG, "onError: "+ resultCode+ " | Info: "+ info );
            }
        });
        Config.sdlManager.sendRPC(vdRequest);
    }

    //você pode ate criar um método getVehicleData recebendo um OnRPCResponseListener como parametro
    //Ex:
    public void getVehicleData(OnRPCResponseListener rpcResponseListener){
        //só para garantir que o getVehicleData será executado apenas se a conexão com o SYNC for estabelecida
        if (!Config.sdlServiceIsActive) {
            return;
        }
        GetVehicleData vdRequest = new GetVehicleData();
        vdRequest.setPrndl(true);
        //vdRequest.setOdometer(true);
        //Aqui vc seta todos os dados que vc quer que a chamada retorne
        /*
        vdRequest.setVin(true);
        vdRequest.setFuelLevel_State(true);
        vdRequest.setOdometer(true);
        vdRequest.setSpeed(true);
        vdRequest.setRpm(true);
        vdRequest.setPrndl(true);
        vdRequest.setGps(true);
        vdRequest.setTirePressure(true);
        vdRequest.setFuelLevel(true);
        vdRequest.setInstantFuelConsumption(true);
        vdRequest.setExternalTemperature(true);
         */
        vdRequest.setOnRPCResponseListener(rpcResponseListener);
        Config.sdlManager.sendRPC(vdRequest);
    }

    //capturar dados do carro
    public VehicleType getVehicleType() {
        if (!Config.sdlServiceIsActive) {
            return null;
        }
        RegisterAppInterfaceResponse registerAppInterfaceResponse = Config.sdlManager.getRegisterAppInterfaceResponse();
        if (registerAppInterfaceResponse == null) {
            System.out.println("HOUVE UM ERRO AQUI!!!");
            return null;
        }
        return registerAppInterfaceResponse.getVehicleType();
    }

    public String getSdlVersion(){
        RegisterAppInterfaceResponse registerAppInterfaceResponse =
                Config.sdlManager.getRegisterAppInterfaceResponse();
        if (registerAppInterfaceResponse == null){
            throw new UnsupportedOperationException("NAO FOI POSSIVEL OBTER INFORMACOES DO VEICULO");
        }
        return String.valueOf(Log.i(TAG,"VERSÃO SDL: "+
                registerAppInterfaceResponse.getSdlVersion()));
    }
}
