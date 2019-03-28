package model;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.StringProperty;

public class Saida extends RecursiveTreeObject<Saida> {

    private StringProperty id;
    private StringProperty id_saida;
    private StringProperty date_out;
    private StringProperty storage_out;
    private StringProperty product_id;
    private StringProperty client_id;

    public StringProperty idProperty() {
        return id;
    }

    public void setId(StringProperty id) {
        this.id = id;
    }

    public StringProperty id_saidaProperty() {
        return id_saida;
    }

    public void setId_saida(StringProperty id_saida) {
        this.id_saida = id_saida;
    }

    public StringProperty date_outProperty() {
        return date_out;
    }

    public void setDate_out(StringProperty date_out) {
        this.date_out = date_out;
    }

    public StringProperty storage_outProperty() {
        return storage_out;
    }

    public void setStorage_out(StringProperty storage_out) {
        this.storage_out = storage_out;
    }

    public StringProperty product_idProperty() {
        return product_id;
    }

    public StringProperty client_idProperty() {
        return client_id;
    }

    public void setProduct_id(StringProperty product_id) {
        this.product_id = product_id;
    }

    public void setClient_id(StringProperty client_id) {
        this.client_id = client_id;
    }
}
