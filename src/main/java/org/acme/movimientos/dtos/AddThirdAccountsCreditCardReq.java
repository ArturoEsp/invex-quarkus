package org.acme.movimientos.dtos;

public class AddThirdAccountsCreditCardReq {

	private String idAccount;
	private String creditCard;
	private String bankId;
	private String curpRfc;
	public AddThirdAccountsCreditCardReq() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getIdAccount() {
		return idAccount;
	}
	public void setIdAccount(String idAccount) {
		this.idAccount = idAccount;
	}
	public String getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getCurpRfc() {
		return curpRfc;
	}
	public void setCurpRfc(String curpRfc) {
		this.curpRfc = curpRfc;
	}
	
	
}
