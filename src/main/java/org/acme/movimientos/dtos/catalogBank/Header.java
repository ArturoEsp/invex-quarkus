package org.acme.movimientos.dtos.catalogBank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Header implements Serializable {
    private static final long serialVersionUID = -6660932022215781912L;

    private String transactionId;
    private String requestSystem;

    public Header(String transactionId, String requestSystem) {
        this.transactionId = transactionId;
        this.requestSystem = requestSystem;
    }
}
