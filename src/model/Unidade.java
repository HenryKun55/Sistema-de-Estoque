package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Unidade extends RecursiveTreeObject<Unidade> {

    private StringProperty id;
    private StringProperty nome;
    private StringProperty date_in;

    public StringProperty idProperty() {
        return id;
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty date_inProperty() {
        return date_in;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public void setNome(StringProperty nome) {
        this.nome = nome;
    }

    public void setDate_in(StringProperty date_in) {
        this.date_in = date_in;
    }

    public Unidade(String nome) {
        this.nome = new SimpleStringProperty(nome);
    }

    public Unidade() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unidade unidade = (Unidade) o;
        return Objects.equals(id, unidade.id) &&
                Objects.equals(nome, unidade.nome) &&
                Objects.equals(date_in, unidade.date_in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, date_in);
    }

    @Override
    public String toString() {
        return nome.getValue();
    }
}



