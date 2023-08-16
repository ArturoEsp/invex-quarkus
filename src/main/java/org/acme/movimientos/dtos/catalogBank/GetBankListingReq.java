package org.acme.movimientos.dtos.catalogBank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class GetBankListingReq implements Serializable {
    private static final long serialVersionUID = 2973687900876102723L;

    private String network;

    public GetBankListingReq(String network) {
        this.network = network;
    }
}
