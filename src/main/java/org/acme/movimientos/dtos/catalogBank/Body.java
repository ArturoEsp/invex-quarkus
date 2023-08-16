package org.acme.movimientos.dtos.catalogBank;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Body implements Serializable {
    private static final long serialVersionUID = -1174292357868637976L;

    private GetBankListingReq getBankListingReq;
}
