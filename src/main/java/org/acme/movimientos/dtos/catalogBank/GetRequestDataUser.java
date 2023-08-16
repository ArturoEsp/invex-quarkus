package org.acme.movimientos.dtos.catalogBank;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GetRequestDataUser implements Serializable {
    private static final long serialVersionUID = -6661081912998513456L;

    @NotBlank
    private String network;
}
