package org.acme.movimientos.dtos.catalogBank;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DataBank implements Serializable {
    private static final long serialVersionUID = -1038972266340732732L;

    private String bankName;
    private String bankId;

    public DataBank(String bankName, String bankId) {
        this.bankName = bankName;
        this.bankId = bankId;
    }
}
