package com.bcsg.mytestapplication.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Revisao {
    private int codigoRevisao;
    private String codigoCarro;
    private LocalDateTime agendamento;
    private LocalDateTime inicio;
    private LocalDateTime termino;
    private List<ItemRevisao> itens;

    public Revisao() {
        super();

        this.itens = new ArrayList<>();
    }

    public int getCodigoRevisao() {

        return codigoRevisao;
    }

    public void setCodigoRevisao(int codigoRevisao) {
        this.codigoRevisao = codigoRevisao;
    }

    public String getCodigoCarro() {
        return codigoCarro;
    }

    public void setCodigoCarro(String codigoCarro) {
        this.codigoCarro = codigoCarro;
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
}