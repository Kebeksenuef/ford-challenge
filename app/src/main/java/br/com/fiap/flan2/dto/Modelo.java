package br.com.fiap.flan2.dto;

public class Modelo {
    private int codigo;
    private String nome;
    private EnumMockInfoVeiculo mockInfo;

    public Modelo() {
        super();
    }

    public Modelo(int codigo, String nome) {
        this();

        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EnumMockInfoVeiculo getMockInfo() {
        return mockInfo;
    }

    public void setMockInfo(EnumMockInfoVeiculo mockInfo) {
        this.mockInfo = mockInfo;
    }

    @Override
    public String toString() {
        return nome;
    }
}
