package br.com.fiap.flan2.dto;

public class ItemRevisao {
    private int codigoItem;
    private float preco;

    public ItemRevisao() {
        super();
    }

    public int getCodigoItem() {
        return codigoItem;
    }

    public void setCodigoItem(int codigoItem) {
        this.codigoItem = codigoItem;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
}
