package org.acme.movimientos.dtos.traspasoEntreCuentasInvex;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferAccountReq implements Serializable {
    private static final long serialVersionUID = 1L;
    private String originAccount;
    private String ammount;
    private String destinationAccount;
    private String currency;
}
