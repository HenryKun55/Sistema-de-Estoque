package model;

import javafx.beans.property.StringProperty;

import java.util.Objects;

public class SaidaProdutos {

    private StringProperty idProduto;
    private StringProperty nomeProduto;
    private StringProperty nomeCliente;
    private StringProperty unitProduto;
    private StringProperty catProduto;
    private StringProperty refProduto;
    private StringProperty quantidade;

    public StringProperty idProdutoProperty() { return idProduto; }

    public StringProperty nomeClienteProperty() { return nomeCliente; }

    public StringProperty unitProdutoProperty() { return unitProduto; }

    public StringProperty catProdutoProperty() { return catProduto; }

    public StringProperty refProdutoProperty() { return refProduto; }

    public StringProperty nomeProdutoProperty() { return nomeProduto; }

    public StringProperty quantidadeProperty() { return quantidade; }

    public void setIdProduto(StringProperty idProduto) { this.idProduto = idProduto; }

    public void setNomeCliente(StringProperty nomeCliente) { this.nomeCliente = nomeCliente; }

    public void setUnitProduto(StringProperty unitProduto) {
        this.unitProduto = unitProduto;
    }

    public void setCatProduto(StringProperty catProduto) {
        this.catProduto = catProduto;
    }

    public void setRefProduto(StringProperty refProduto) {
        this.refProduto = refProduto;
    }

    public void setNomeProduto(StringProperty nomeProduto) { this.nomeProduto = nomeProduto; }

    public void setQuantidade(StringProperty quantidade) { this.quantidade = quantidade; }

    public SaidaProdutos() { }

    @Override
    public String toString() {
        return "SaidaProdutos{" +
                "idProduto=" + idProduto +
                ", nomeProduto=" + nomeProduto +
                ", nomeCliente=" + nomeCliente +
                ", unitProduto=" + unitProduto +
                ", catProduto=" + catProduto +
                ", refProduto=" + refProduto +
                ", quantidade=" + quantidade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaidaProdutos that = (SaidaProdutos) o;
        return Objects.equals(idProduto, that.idProduto) &&
                Objects.equals(nomeProduto, that.nomeProduto) &&
                Objects.equals(nomeCliente, that.nomeCliente) &&
                Objects.equals(unitProduto, that.unitProduto) &&
                Objects.equals(catProduto, that.catProduto) &&
                Objects.equals(refProduto, that.refProduto) &&
                Objects.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, nomeProduto, nomeCliente, unitProduto, catProduto, refProduto, quantidade);
    }
}
