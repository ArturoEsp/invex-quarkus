package org.acme.movimientos.dtos.catalogBank;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CanonicalInvexMessage implements Serializable {
    private static final long serialVersionUID = -8690246217352886087L;

    private Header header;
    private Body body;
}
