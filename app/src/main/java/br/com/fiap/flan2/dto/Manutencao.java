package br.com.fiap.flan2.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Manutencao {
    private int codigo;
    private String descricao;
    private Date dataRealizacao;
    private List<ItemRevisao> itens;

    public Manutencao() {
        super();

        this.itens = new ArrayList<>();
    }

    public Manutencao(int codigo) {
        this();

        this.codigo = codigo;
    }

    public Manutencao(int codigo, String descricao) {
        this(codigo);

        this.descricao = descricao;
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

    public Date getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(Date dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
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
        Manutencao revisao = (Manutencao) o;
        return codigo == revisao.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}