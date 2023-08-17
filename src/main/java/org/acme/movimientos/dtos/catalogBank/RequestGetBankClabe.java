package org.acme.movimientos.dtos.catalogBank;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestGetBankClabe implements Serializable{

	private static final long serialVersionUID = 1L;
	private String network;
	private String clabe;
}

