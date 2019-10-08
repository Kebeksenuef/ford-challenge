package com.bcsg.mytestapplication.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisao {
    private int codigo;
    private int codigoModelo;
    private String descricao;
    private int prazoMeses;
    private int limiteQuilometragem;
    private float valorVista;
    private float valorParcela;
    private int quantidadeParcelas;
    private boolean realizada;
    private List<ItemRevisao> itens;

    public Revisao() {
        super();

        this.itens = new ArrayList<>();
    }

    public Revisao(int codigo) {
        this();

        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoModelo() {
        return codigoModelo;
    }

    public void setCodigoModelo(int codigoModelo) {
        this.codigoModelo = codigoModelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(int prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    public int getLimiteQuilometragem() {
        return limiteQuilometragem;
    }

    public void setLimiteQuilometragem(int limiteQuilometragem) {
        this.limiteQuilometragem = limiteQuilometragem;
    }

    public float getValorVista() {
        return valorVista;
    }

    public void setValorVista(float valorVista) {
        this.valorVista = valorVista;
    }

    public float getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(float valorParcela) {
        this.valorParcela = valorParcela;
    }

    public int getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(int quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public boolean getRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    public List<ItemRevisao> getItens() {
        return itens;
    }

    public void setItens(List<ItemRevisao> itens) {
        this.itens = itens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Revisao revisao = (Revisao) o;
        return codigo == revisao.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}