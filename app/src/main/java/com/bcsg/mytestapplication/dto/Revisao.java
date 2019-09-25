package com.bcsg.mytestapplication.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisao {
    private int codigo;
    private String descricao;
    private String codigoVeiculo;
    private LocalDateTime agendamento;
    private LocalDateTime inicio;
    private LocalDateTime termino;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigoVeiculo() {
        return codigoVeiculo;
    }

    public void setCodigoVeiculo(String codigoVeiculo) {
        this.codigoVeiculo = codigoVeiculo;
    }

    public LocalDateTime getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(LocalDateTime agendamento) {
        this.agendamento = agendamento;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getTermino() {
        return termino;
    }

    public void setTermino(LocalDateTime termino) {
        this.termino = termino;
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