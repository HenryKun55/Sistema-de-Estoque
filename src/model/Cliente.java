package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Cliente extends RecursiveTreeObject<Cliente> {

    private StringProperty id;
    private StringProperty nome;
    private StringProperty date_in;
    private StringProperty modify_date;

    public StringProperty idProperty() { return id;};

    public StringProperty nomeProperty() {
        return nome;
    }

    public StringProperty date_inProperty() { return date_in; }

    public StringProperty modify_dateProperty() { return modify_date; }

    public void setId(StringProperty id) { this.id = id;}

    public void setNome(StringProperty nome) { this.nome = nome; }

    public void setDate_in(StringProperty date_in) { this.date_in = date_in;}

    public void setModify_date(StringProperty modify_date) { this.modify_date = modify_date;}

    public Cliente(String nome){
        this.nome = new SimpleStringProperty(nome);
    }

    public Cliente() { }

    @Override
    public String toString() {
        return "ID: "+id.getValue()+" Nome: "+nome.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nome, cliente.nome) &&
                Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, id);
    }
}
