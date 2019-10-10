package com.bcsg.mytestapplication;

import com.bcsg.mytestapplication.dto.EnumMockInfoVeiculo;
import com.bcsg.mytestapplication.dto.Modelo;

public class AppSession {
    private static Modelo modelo;

    public static void setModelo(Modelo modelo, EnumMockInfoVeiculo mockInfo) {
        modelo.setMockInfo(mockInfo);

        AppSession.modelo = modelo;
    }

    public static Modelo getModelo() {
        return AppSession.modelo;
    }
}
