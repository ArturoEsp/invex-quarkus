package org.acme.movimientos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

//@Entity
//@Table(name = "saldos")
public class Saldos {
    //@Id
    //@Column(name = "cui")
    private String cui;

    //@Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    //@Column(name = "cuentas")
    private String cuentas;

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }
}
