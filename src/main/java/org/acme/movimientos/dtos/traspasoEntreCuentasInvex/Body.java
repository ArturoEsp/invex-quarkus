package org.acme.movimientos.dtos.traspasoEntreCuentasInvex;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Body implements Serializable {
    private static final long serialVersionUID = 1L;
    private TransferAccountReq transferAccountReq;
    
}
