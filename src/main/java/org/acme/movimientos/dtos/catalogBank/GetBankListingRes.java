package org.acme.movimientos.dtos.catalogBank;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GetBankListingRes implements Serializable {
    private static final long serialVersionUID = 6727467619290995479L;

    public List<DataBank> dataBank = new ArrayList<>();

}
