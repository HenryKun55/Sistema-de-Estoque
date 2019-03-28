package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Produto extends RecursiveTreeObject<Produto> {

    private StringProperty id;
    private StringProperty category_id;
    private StringProperty nome;
    private StringProperty unit_id;
    private StringProperty reference;
    private StringProperty quantidade;
    private StringProperty photo_url;
    private StringProperty entrada;
    private StringProperty saida;
    private StringProperty created_at;
    private StringProperty updated_at;

    public StringProperty idProperty() { return id; }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public StringProperty categoryIdProperty() { return category_id; }

    public void setCategory_id(StringProperty category_id) { this.category_id = category_id; }

    public StringProperty createdAtProperty() {
        return created_at;
    }

    public void setCreated_at(StringProperty created_at) {
        this.created_at = created_at;
    }

    public StringProperty updatedAtProperty() {
        return updated_at;
    }

    public void setUpdated_at(StringProperty updated_at) {
        this.updated_at = updated_at;
    }

    public StringProperty photo_UrlProperty() {
        return photo_url;
    }

    public void setPhoto_url(StringProperty photo_url) {
        this.photo_url = photo_url;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(StringProperty nome) {
        this.nome = nome;
    }

    public StringProperty unit_idProperty() {
        return unit_id;
    }

    public void setUnit_id(StringProperty unit_id) {
        this.unit_id = unit_id;
    }

    public StringProperty referenceProperty() {
        return reference;
    }

    public void setReference(StringProperty reference) {
        this.reference = reference;
    }

    public StringProperty quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(StringProperty quantidade) {
        this.quantidade = quantidade;
    }

    public StringProperty entradaProperty() { return entrada; }

    public void setEntrada(StringProperty entrada) {
        this.entrada = entrada;
    }

    public StringProperty saidaProperty() {
        return saida;
    }

    public void setSaida(StringProperty saida) {
        this.saida = saida;
    }

    public Produto(String id, String category_id, String nome) {
        this.id = new SimpleStringProperty(id);
        this.category_id = new SimpleStringProperty(category_id);
        this.nome = new SimpleStringProperty(nome);
    }

    public Produto(String id, String category_id, String nome, String reference, String unit_id) {
        this.id = new SimpleStringProperty(id);
        this.category_id = new SimpleStringProperty(category_id);
        this.nome = new SimpleStringProperty(nome);
        this.reference = new SimpleStringProperty(reference);
        this.unit_id = new SimpleStringProperty(unit_id);
    }

    public Produto() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id) &&
                Objects.equals(category_id, produto.category_id) &&
                Objects.equals(photo_url, produto.photo_url) &&
                Objects.equals(nome, produto.nome) &&
                Objects.equals(reference, produto.reference) &&
                Objects.equals(quantidade, produto.quantidade) &&
                Objects.equals(entrada, produto.entrada) &&
                Objects.equals(saida, produto.saida) &&
                Objects.equals(created_at, produto.created_at) &&
                Objects.equals(updated_at, produto.updated_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category_id, photo_url, nome, reference, quantidade, entrada, saida, created_at, updated_at);
    }

    public String allString() {
        return "Produto{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", nome=" + nome +
                ", unit_id=" + unit_id +
                ", reference=" + reference +
                ", quantidade=" + quantidade +
                ", photo_url=" + photo_url +
                ", entrada=" + entrada +
                ", saida=" + saida +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    @Override
    public String toString() {
        return "ID: "+id.getValue()+" Produto: "+nome.getValue();
    }
}
