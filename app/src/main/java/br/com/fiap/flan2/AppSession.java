package br.com.fiap.flan2;

import br.com.fiap.flan2.dto.EnumMockInfoVeiculo;
import br.com.fiap.flan2.dto.Modelo;

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
