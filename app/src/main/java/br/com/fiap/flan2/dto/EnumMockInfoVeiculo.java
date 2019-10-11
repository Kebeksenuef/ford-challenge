package br.com.fiap.flan2.dto;

public enum EnumMockInfoVeiculo {

    FOCUS("CHASSI-FOCUS", 10324),
    ECOSPORT("CHASSI-ECO", 5720),
    FUSION("CHASSI-FUSION", 4982),
    KA("CHASSI-KA", 15098),
    FIESTA("CHASSI-FIESTA", 21412),
    SELECIONE("CHASSI-NADA",0);

    private String chassi;
    private float quilometragem;

    EnumMockInfoVeiculo(String chassi, float quilometragem) {
        this.chassi = chassi;
        this.quilometragem = quilometragem;
    }

    public static EnumMockInfoVeiculo get(Modelo modelo) {
        if (modelo.getCodigo() == 1) {
            return EnumMockInfoVeiculo.FOCUS;
        } else if (modelo.getCodigo() == 2) {
            return EnumMockInfoVeiculo.ECOSPORT;
        } else if (modelo.getCodigo() == 3) {
            return EnumMockInfoVeiculo.FUSION;
        } else if (modelo.getCodigo() == 4) {
            return EnumMockInfoVeiculo.KA;
        } else if (modelo.getCodigo() == 5) {
            return EnumMockInfoVeiculo.FIESTA;
        }else if (modelo.getCodigo() == 6) {
            return EnumMockInfoVeiculo.SELECIONE;
        }
        return null;
    }

    public String getChassi() {
        return chassi;
    }

    public float getQuilometragem() {
        return quilometragem;
    }
}
