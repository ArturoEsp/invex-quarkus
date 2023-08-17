package org.acme.movimientos.dtos.orderspei;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSPEIOrderReqDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String counterpartCode;               //Codigo contraparte                      Obligatorio
	private String paymentAmount;                 //Monto                                   Obligatorio
	private String idAccount;                     // payerAccount/ Cuenta ordenante         Obligatorio  tabla sesion visto como idAccount
	private String payerName;                     //Nombre ordenante                        Obligatorio
	private String beneficiaryAccuount;           //Cuenta beneficiario                     NO Obligatorio
	private String paymentSubject;                //Concepto de pago                        Obligatorio
	private String paymentReferenceNumber;        //Numero de referencia                    Obligatorio
		
}
