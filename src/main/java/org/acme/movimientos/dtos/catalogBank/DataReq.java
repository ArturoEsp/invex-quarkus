package org.acme.movimientos.dtos.catalogBank;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DataReq implements Serializable {
    private static final long serialVersionUID = 7869391819887046696L;

    private CanonicalInvexMessage canonicalInvexMessage;
}
