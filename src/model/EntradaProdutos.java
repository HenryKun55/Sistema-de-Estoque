package model;

import javafx.beans.property.StringProperty;

import java.util.Objects;

public class EntradaProdutos {

    private StringProperty idProduto;
    private StringProperty nomeProduto;
    private StringProperty unitProduto;
    private StringProperty catProduto;
    private StringProperty refProduto;
    private StringProperty quantidade;


    public StringProperty idProdutoProperty() { return idProduto; }

    public StringProperty nomeProdutoProperty() { return nomeProduto; }

    public StringProperty unitProdutoProperty() { return unitProduto; }

    public StringProperty catProdutoProperty() { return catProduto; }

    public StringProperty refProdutoProperty() { return refProduto; }

    public StringProperty quantidadeProperty() { return quantidade; }

    public void setIdProduto(StringProperty idProduto) { this.idProduto = idProduto; }

    public void setNomeProduto(StringProperty nomeProduto) { this.nomeProduto = nomeProduto; }

    public void setUnitProduto(StringProperty unitProduto) {
        this.unitProduto = unitProduto;
    }

    public void setCatProduto(StringProperty catProduto) {
        this.catProduto = catProduto;
    }

    public void setRefProduto(StringProperty refProduto) {
        this.refProduto = refProduto;
    }

    public void setQuantidade(StringProperty quantidade) { this.quantidade = quantidade; }

    public EntradaProdutos() { }

    @Override
    public String toString() {
        return "EntradaProdutos{" +
                "idProduto=" + idProduto +
                ", nomeProduto=" + nomeProduto +
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
        EntradaProdutos that = (EntradaProdutos) o;
        return Objects.equals(idProduto, that.idProduto) &&
                Objects.equals(nomeProduto, that.nomeProduto) &&
                Objects.equals(unitProduto, that.unitProduto) &&
                Objects.equals(catProduto, that.catProduto) &&
                Objects.equals(refProduto, that.refProduto) &&
                Objects.equals(quantidade, that.quantidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, nomeProduto, unitProduto, catProduto, refProduto, quantidade);
    }
}
