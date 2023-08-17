package org.acme.movimientos.dtos;

import java.io.Serializable;




public class CEPVoucherInquiryReqDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String assambleCEPLink;               //Ensamblar enlace CEP                      Obligatorio     Ejemplo: true
	private String trackingKey;                   //Llave de seguimiento                      Obligatorio     Ejemplo:"230601077774505454I"
	private String searchCriterion;               //Criterio de búsqueda                      Obligatorio 	  Ejemplo: "T" puede llevar T(tanto para SPEI y SPID) o R para numero de referencia
	private String beneficiaryAccoun;             //Cuenta de Beneficiario                    Obligatorio	  Ejemplo:"5468044005268109"
	private String issuing;           			  //Emisor                     				  Obligatorio     Ejemplo:"40127"
	private String operationDate;                 //Fecha de Operación                        Obligatorio     Ejemplo:"2023-05-31T23:01:24"
	private String origin;        				  //Origen                                    Obligatorio     Ejemplo:"SPEI"
	private String operationAmmount;              //Monto de la Operación                     Obligatorio     Ejemplo:"36"
	private String receiver;        			  //Receptor                    			  Obligatorio     Ejemplo:"40059"
	
	
	
	public CEPVoucherInquiryReqDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public CEPVoucherInquiryReqDto(String assambleCEPLink, String trackingKey, String searchCriterion,
			String beneficiaryAccoun, String issuing, String operationDate, String origin, String operationAmmount,
			String receiver) {
		super();
		this.assambleCEPLink = assambleCEPLink;
		this.trackingKey = trackingKey;
		this.searchCriterion = searchCriterion;
		this.beneficiaryAccoun = beneficiaryAccoun;
		this.issuing = issuing;
		this.operationDate = operationDate;
		this.origin = origin;
		this.operationAmmount = operationAmmount;
		this.receiver = receiver;
	}



	public String getAssambleCEPLink() {
		return assambleCEPLink;
	}



	public void setAssambleCEPLink(String assambleCEPLink) {
		this.assambleCEPLink = assambleCEPLink;
	}



	public String getTrackingKey() {
		return trackingKey;
	}



	public void setTrackingKey(String trackingKey) {
		this.trackingKey = trackingKey;
	}



	public String getSearchCriterion() {
		return searchCriterion;
	}



	public void setSearchCriterion(String searchCriterion) {
		this.searchCriterion = searchCriterion;
	}



	public String getBeneficiaryAccoun() {
		return beneficiaryAccoun;
	}



	public void setBeneficiaryAccoun(String beneficiaryAccoun) {
		this.beneficiaryAccoun = beneficiaryAccoun;
	}



	public String getIssuing() {
		return issuing;
	}



	public void setIssuing(String issuing) {
		this.issuing = issuing;
	}



	public String getOperationDate() {
		return operationDate;
	}



	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}



	public String getOrigin() {
		return origin;
	}



	public void setOrigin(String origin) {
		this.origin = origin;
	}



	public String getOperationAmmount() {
		return operationAmmount;
	}



	public void setOperationAmmount(String operationAmmount) {
		this.operationAmmount = operationAmmount;
	}



	public String getReceiver() {
		return receiver;
	}



	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
