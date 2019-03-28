package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Objects;

public class Entrada extends RecursiveTreeObject<Entrada> {

    private StringProperty id;
    private StringProperty id_entrada;
    private StringProperty product_id;
    private StringProperty storage_in;
    private StringProperty date_in;

    public StringProperty idProperty() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public StringProperty idEntradaProperty() {
        return id_entrada;
    }

    public void setId_entrada(StringProperty id_entrada) {
        this.id_entrada = id_entrada;
    }

    public StringProperty product_idProperty() {
        return product_id;
    }

    public void setProduct_id(StringProperty product_id) {
        this.product_id = product_id;
    }

    public StringProperty date_inProperty() {
        return date_in;
    }

    public void setDate_in(StringProperty date_in) {
        this.date_in = date_in;
    }

    public StringProperty storage_inProperty() {
        return storage_in;
    }

    public void setStorage_in(StringProperty storage_in) {
        this.storage_in = storage_in;
    }

    public Entrada(String product_id, String storage_in) {
        this.product_id = new SimpleStringProperty(product_id);
        this.storage_in = new SimpleStringProperty(storage_in);
    }

    public Entrada() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrada entrada = (Entrada) o;
        return Objects.equals(id, entrada.id) &&
                Objects.equals(id_entrada, entrada.id_entrada) &&
                Objects.equals(product_id, entrada.product_id) &&
                Objects.equals(storage_in, entrada.storage_in) &&
                Objects.equals(date_in, entrada.date_in);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, id_entrada, product_id, storage_in, date_in);
    }
}
