package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Usuario extends RecursiveTreeObject<Usuario> {

    private StringProperty id;
    private StringProperty name;
    private StringProperty password;
    private StringProperty created_at;
    private StringProperty updated_at;

    public Usuario() { }

    public StringProperty idProperty() { return id; }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public StringProperty nameProperty() { return name; }

    public void setName(StringProperty name) {
        this.name = name;
    }

    public StringProperty passwordProperty() { return password; }

    public void setPassword(StringProperty password) {
        this.password = password;
    }

    public StringProperty created_atProperty() { return created_at; }

    public void setCreated_at(StringProperty created_at) {
        this.created_at = created_at;
    }

    public StringProperty updated_atProperty() { return updated_at; }

    public void setUpdated_at(StringProperty updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name=" + name +
                ", password=" + password +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
                Objects.equals(name, usuario.name) &&
                Objects.equals(password, usuario.password) &&
                Objects.equals(created_at, usuario.created_at) &&
                Objects.equals(updated_at, usuario.updated_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, created_at, updated_at);
    }

    public Usuario(String name, String password) {
        this.name = new SimpleStringProperty(name);
        this.password = new SimpleStringProperty(password);
    }
}
